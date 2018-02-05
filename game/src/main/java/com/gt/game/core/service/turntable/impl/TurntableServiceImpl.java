package com.gt.game.core.service.turntable.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.axis.bean.wxmp.dict.DictApiReq;
import com.gt.axis.bean.wxmp.dict.DictApiRes;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiFlowRecord;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiFlowRecordReq;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiSurplus;
import com.gt.axis.bean.wxmp.fenbiflow.UpdateFenbiReduceReq;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.member.MemberServer;
import com.gt.axis.server.wxmp.DictServer;
import com.gt.axis.server.wxmp.FenbiflowServer;
import com.gt.game.common.config.ApplyProperties;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.turntable.req.*;
import com.gt.game.core.bean.turntable.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.turntable.WinningRecordDAO;
import com.gt.game.core.entity.turntable.TurntableDetail;
import com.gt.game.core.entity.turntable.TurntableMain;
import com.gt.game.core.entity.turntable.WinningRecord;
import com.gt.game.core.exception.turntable.TurntableException;
import com.gt.game.core.service.turntable.TurntableDetailService;
import com.gt.game.core.service.turntable.TurntableMainService;
import com.gt.game.core.service.turntable.TurntableService;
import com.gt.game.core.service.turntable.WinningRecordService;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 大转盘 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class TurntableServiceImpl implements TurntableService {

    @Autowired
    TurntableMainService turntableMainService;

    @Autowired
    TurntableDetailService turntableDetailService;

    @Autowired
    WinningRecordService winningRecordService;

    @Autowired
    WinningRecordDAO winningRecordDAO;

    @Autowired
    ApplyProperties applyProperties;

    /**
     * 获取手机端链接
     *
     * @param loginPbUser
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(WxPublicUsers loginPbUser, MobileUrlReq mobileUrlReq) {

        String url = applyProperties.getMobileBaseUrl() + "turntable/"+loginPbUser.getId()+"/"+ mobileUrlReq.getMainId() + "/-100"+"/79B4DE7C/userGrant.do";
        return new MobileUrlRes(url);
    }

    /**
     * 分页获取大转盘活动列表
     * @param loginPbUser
     * @param turntableListReq
     * @return
     */
    @Override
    public ResponseDTO<List<TurntableListRes>> getTurntableList(WxPublicUsers loginPbUser, TurntableListReq turntableListReq) {

        EntityWrapper<TurntableMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_wx_userid", loginPbUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(turntableListReq.getName()), "act_name", turntableListReq.getName());
        if (turntableListReq.getStatus() != -1) {   // TODO -1 全部 0 未开始 1 进行中 2 已结束 3.已暂停

            if (turntableListReq.getStatus() == 0) {
                entityWrapper.where("act_beginTime > {0}", new Date());
            }
            if (turntableListReq.getStatus() == 1) {
                entityWrapper.where("act_beginTime <= {0}", new Date());
                entityWrapper.and();
                entityWrapper.where("act_endTime > {0}", new Date());
            }
            if (turntableListReq.getStatus() == 2) {
                entityWrapper.where("act_endTime <= {0}", new Date());
            }
            if (turntableListReq.getStatus() == 3) {    //TODO   已暂停
                entityWrapper.eq("scr_status", 2);
            }
        }
        Page<TurntableMain> page = new Page<>(turntableListReq.getCurrent(), turntableListReq.getSize());
        List<TurntableMain> turntableMainList = turntableMainService.selectPage(page, entityWrapper).getRecords();

        List<TurntableListRes> turntableListResList = new ArrayList<>();
        for (TurntableMain turntableMain : turntableMainList) {

            if(turntableListReq.getStatus() == -1 ||turntableListReq.getStatus()== 3) {   // TODO  全部
                TurntableListRes turntableListRes = new TurntableListRes();
                turntableListRes.setId(turntableMain.getId());
                turntableListRes.setName(turntableMain.getActName());
                turntableListRes.setActivityBeginTime(turntableMain.getActBeginTime());
                turntableListRes.setActivityEndTime(turntableMain.getActEndTime());

                if (turntableMain.getActStatus() == 2) {    //TODO   已暂停
                    turntableListRes.setStatus(3);
                } else {
                    Date date = new Date();
                    if (turntableMain.getActBeginTime().getTime() > date.getTime()) {
                        turntableListRes.setStatus(0);
                    } else if (turntableMain.getActBeginTime().getTime() <= date.getTime() && turntableMain.getActEndTime().getTime() >= date.getTime()) {
                        turntableListRes.setStatus(1);
                    } else if (turntableMain.getActEndTime().getTime() < date.getTime()) {
                        turntableListRes.setStatus(2);
                    }
                }
                   turntableListResList.add(turntableListRes);
               } else {
                if (turntableMain.getActStatus() != 2) {
                    TurntableListRes turntableListRes = new TurntableListRes();
                    turntableListRes.setId(turntableMain.getId());
                    turntableListRes.setName(turntableMain.getActName());
                    turntableListRes.setActivityBeginTime(turntableMain.getActBeginTime());
                    turntableListRes.setActivityEndTime(turntableMain.getActEndTime());
                    Date date = new Date();
                    if (turntableMain.getActBeginTime().getTime() > date.getTime()) {
                        turntableListRes.setStatus(0);
                    } else if (turntableMain.getActBeginTime().getTime() <= date.getTime() && turntableMain.getActEndTime().getTime() >= date.getTime()) {
                        turntableListRes.setStatus(1);
                    } else if (turntableMain.getActEndTime().getTime() < date.getTime()) {
                        turntableListRes.setStatus(2);
                    }
                    turntableListResList.add(turntableListRes);
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取大转盘活动列表成功", turntableListResList, pageDTO);
    }

    /**
     * 统计大转盘活动总数
     * @param loginPbUser
     * @param turntableCountActivityReq
     * @return
     */
    @Override
    public TurntableCountActivityRes countTurntable(WxPublicUsers loginPbUser, TurntableCountActivityReq turntableCountActivityReq) {

        EntityWrapper<TurntableMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_wx_userid",loginPbUser.getId());
        entityWrapper.like(CommonUtil.isNotEmpty(turntableCountActivityReq.getName()), "act_name", turntableCountActivityReq.getName());

        List<TurntableMain> turntableMainList = turntableMainService.selectList(entityWrapper);

        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;

        for (TurntableMain turntableMain : turntableMainList) {
            if(turntableMain.getActStatus()==2){    //TODO   已暂停
                count5++;
            }else {
                Date date = new Date();
                if (turntableMain.getActBeginTime().getTime() > date.getTime()) {
                    count2++;
                } else if (turntableMain.getActBeginTime().getTime() <= date.getTime() && turntableMain.getActEndTime().getTime() >= date.getTime()) {
                    count3++;
                } else if (turntableMain.getActEndTime().getTime() < date.getTime()) {
                    count4++;
                }
            }
        }
        TurntableCountActivityRes turntableCountActivityRes = new TurntableCountActivityRes();
        turntableCountActivityRes.setCount2(count2);
        turntableCountActivityRes.setCount3(count3);
        turntableCountActivityRes.setCount4(count4);
        turntableCountActivityRes.setCount5(count5);
        turntableCountActivityRes.setCount1(count2+count3+count4+count5);  //  TODO 全部
        return  turntableCountActivityRes;
    }

    /**
     * 新增大转盘活动
     * @param loginPbUser
     * @param turntableAddReq
     */
    @Override
    public void addScratch(BusUser busUser,WxPublicUsers loginPbUser, TurntableAddReq turntableAddReq) {

        TurntableMain turntableMain = new TurntableMain();
        turntableMain.setActWxUserid(loginPbUser.getId());

        //TODO  基础设置
        turntableMain.setActName(turntableAddReq.getActName());
        turntableMain.setActBeginTime(turntableAddReq.getActBeginTime());
        turntableMain.setActEndTime(turntableAddReq.getActEndTime());
        turntableMain.setActOverdescribe(turntableAddReq.getActOverdescribe());
        turntableMain.setActPartaker(turntableAddReq.getActPartaker());
        if(turntableAddReq.getActPartaker()==2){
            turntableMain.setActPway(turntableAddReq.getActPway());
            if(turntableAddReq.getActPway()==2){
                turntableMain.setActMan(turntableAddReq.getActMan());
            }else if(turntableAddReq.getActPway()==3){
                turntableMain.setActKou(turntableAddReq.getActKou());
            }else if(turntableAddReq.getActPway()==4){
                turntableMain.setActMan(turntableAddReq.getActMan());
                turntableMain.setActKou(turntableAddReq.getActKou());
            }
        }
        turntableMain.setActBgmName(turntableAddReq.getActBgmName());
        turntableMain.setActBgm(turntableAddReq.getActBgm());

        //TODO  规则设置
        turntableMain.setActTotalOfAct(turntableAddReq.getActTotalOfAct());
        turntableMain.setActCountOfDay(turntableAddReq.getActCountOfDay());
        turntableMain.setActDescribe(turntableAddReq.getActDescribe());

        //TODO  兑奖设置
        turntableMain.setActCashday(turntableAddReq.getActCashday());
        turntableMain.setActAddress(turntableAddReq.getActAddress());
        turntableMain.setActCashWay(turntableAddReq.getActCashWay());
        turntableMain.setActCashtext(turntableAddReq.getActCashtext());
        turntableMain.setActWinningNotice(turntableAddReq.getActWinningNotice());

        turntableMainService.insert(turntableMain);

        //TODO   奖项设置
        Double fenbi = 0.0;
        for(TurntablePrizeSetReq turntablePrizeSetReq:turntableAddReq.getPrizeSetList()){
            if (turntablePrizeSetReq.getTurPrizeType() == 1) {
                fenbi += turntablePrizeSetReq.getTurPrizeNums();
            }
            TurntableDetail turntableDetail = new TurntableDetail();
            turntableDetail.setActId(turntableMain.getId());
            turntableDetail.setTurPrizeType(turntablePrizeSetReq.getTurPrizeType().toString());
            turntableDetail.setTurPrizeLimit(turntablePrizeSetReq.getTurPrizeLimit());
            turntableDetail.setTurPrizeName(turntablePrizeSetReq.getTurPrizeName());
            turntableDetail.setTurPrizeNums(turntablePrizeSetReq.getTurPrizeNums());
            turntableDetail.setTurPrizeChance(turntablePrizeSetReq.getTurPrizeChance());
            turntableDetail.setNickname(turntablePrizeSetReq.getNickname());

            turntableDetailService.insert(turntableDetail);
        }

        if(fenbi > 0){//冻结粉币
            // 判断账户中的粉币是否足够
            if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                throw new TurntableException(ResponseEnums.TURNTABLE_HAS7);
            }
            //构建冻结信息
            FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, turntableMain.getId(), 1, 1, "大转盘活动支出", 0);
            // 保存冻结信息
            if(ffr!=null){
                FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                if(axisResult.getCode() != 0){
                    throw new TurntableException(ResponseEnums.TURNTABLE_HAS8);
                }
            }
        }
    }

    /**
     * 通过活动id查询大转盘活动
     * @param busUser
     * @param turntableGetActivityReq
     * @return
     */
    @Override
    public TurntableGetActivityRes getActivityById(BusUser busUser, TurntableGetActivityReq turntableGetActivityReq) {

        TurntableMain turntableMain = turntableMainService.selectById(turntableGetActivityReq.getId());
        TurntableGetActivityRes turntableGetActivityRes = new TurntableGetActivityRes();

        //TODO  基础设置
        turntableGetActivityRes.setActName(turntableMain.getActName());
        turntableGetActivityRes.setActBeginTime(turntableMain.getActBeginTime());
        turntableGetActivityRes.setActEndTime(turntableMain.getActEndTime());
        turntableGetActivityRes.setActOverdescribe(turntableMain.getActOverdescribe());
        turntableGetActivityRes.setActPartaker(turntableMain.getActPartaker());
        if (turntableMain.getActPartaker() == 2) {
            turntableGetActivityRes.setActPway(turntableMain.getActPway());
            if (turntableMain.getActPway() == 2) {
                turntableGetActivityRes.setActMan(turntableMain.getActMan());
            } else if (turntableMain.getActPway() == 3) {
                turntableGetActivityRes.setActKou(turntableMain.getActKou());
            } else if (turntableMain.getActPway() == 4) {
                turntableGetActivityRes.setActMan(turntableMain.getActMan());
                turntableGetActivityRes.setActKou(turntableMain.getActKou());
            }
        }
        turntableGetActivityRes.setActBgmName(turntableMain.getActBgmName());
        turntableGetActivityRes.setActBgm(turntableMain.getActBgm());

        //TODO  规则设置
        turntableGetActivityRes.setActTotalOfAct(turntableMain.getActTotalOfAct());
        turntableGetActivityRes.setActCountOfDay(turntableMain.getActCountOfDay());
        turntableGetActivityRes.setActDescribe(turntableMain.getActDescribe());

        //TODO  兑奖设置
        turntableGetActivityRes.setActCashday(turntableMain.getActCashday());
        turntableGetActivityRes.setActAddress(turntableMain.getActAddress());
        turntableGetActivityRes.setActCashWay(turntableMain.getActCashWay());
        turntableGetActivityRes.setActCashtext(turntableMain.getActCashtext());
        turntableGetActivityRes.setActWinningNotice(turntableMain.getActWinningNotice());

        //TODO   奖项设置
        EntityWrapper<TurntableDetail> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_id", turntableGetActivityReq.getId());
        List<TurntableDetail> turntableDetailList = turntableDetailService.selectList(entityWrapper);

        List<TurntablePrizeSetReq> list = new ArrayList<>();
        for (TurntableDetail turntableDetail : turntableDetailList) {

            TurntablePrizeSetReq turntablePrizeSetReq = new TurntablePrizeSetReq();
            turntablePrizeSetReq.setTurPrizeType(CommonUtil.toInteger(turntableDetail.getTurPrizeType()));
            turntablePrizeSetReq.setTurPrizeLimit(turntableDetail.getTurPrizeLimit());
            turntablePrizeSetReq.setTurPrizeName(turntableDetail.getTurPrizeName());
            turntablePrizeSetReq.setTurPrizeNums(turntableDetail.getTurPrizeNums());
            turntablePrizeSetReq.setTurPrizeChance(turntableDetail.getTurPrizeChance());
            turntablePrizeSetReq.setNickname(turntableDetail.getNickname());
            list.add(turntablePrizeSetReq);
        }
        turntableGetActivityRes.setPrizeSetList(list);

        return turntableGetActivityRes;
    }

    /**
     * 编辑大转盘活动设置
     * @param busUser
     * @param turntableModfiyReq
     */
    @Override
    public void modfiyTurntable(BusUser busUser, TurntableModfiyReq turntableModfiyReq) {

        TurntableMain turntableMain = turntableMainService.selectById(turntableModfiyReq.getId());
        if(CommonUtil.isEmpty(turntableMain)){
            throw new TurntableException(ResponseEnums.TURNTABLE_HAS5);
        }
        if(turntableMain.getActBeginTime().getTime() < new Date().getTime()){
            throw new TurntableException(ResponseEnums.TURNTABLE_HAS10);
        }

        //TODO  基础设置
        turntableMain.setActName(turntableModfiyReq.getActName());
        turntableMain.setActBeginTime(turntableModfiyReq.getActBeginTime());
        turntableMain.setActEndTime(turntableModfiyReq.getActEndTime());
        turntableMain.setActOverdescribe(turntableModfiyReq.getActOverdescribe());
        turntableMain.setActPartaker(turntableModfiyReq.getActPartaker());
        if(turntableModfiyReq.getActPartaker()==2){
            turntableMain.setActPway(turntableModfiyReq.getActPway());
            if(turntableModfiyReq.getActPway()==2){
                turntableMain.setActMan(turntableModfiyReq.getActMan());
            }else if(turntableModfiyReq.getActPway()==3){
                turntableMain.setActKou(turntableModfiyReq.getActKou());
            }else if(turntableModfiyReq.getActPway()==4){
                turntableMain.setActMan(turntableModfiyReq.getActMan());
                turntableMain.setActKou(turntableModfiyReq.getActKou());
            }
        }
        turntableMain.setActBgmName(turntableModfiyReq.getActBgmName());
        turntableMain.setActBgm(turntableModfiyReq.getActBgm());

        //TODO  规则设置
        turntableMain.setActTotalOfAct(turntableModfiyReq.getActTotalOfAct());
        turntableMain.setActCountOfDay(turntableModfiyReq.getActCountOfDay());
        turntableMain.setActDescribe(turntableModfiyReq.getActDescribe());

        //TODO  兑奖设置
        turntableMain.setActCashday(turntableModfiyReq.getActCashday());
        turntableMain.setActAddress(turntableModfiyReq.getActAddress());
        turntableMain.setActCashWay(turntableModfiyReq.getActCashWay());
        turntableMain.setActCashtext(turntableModfiyReq.getActCashtext());
        turntableMain.setActWinningNotice(turntableModfiyReq.getActWinningNotice());

        turntableMainService.updateById(turntableMain);

        //TODO   奖项设置
        Double fenbi = 0.0;
        Double num   = 0.0;
        int f = 0;
        if(turntableModfiyReq.getPrizeSetList().size()>0){
            EntityWrapper<TurntableDetail> entityWrapper5 = new EntityWrapper();
            entityWrapper5.eq("act_id",turntableModfiyReq.getId());
            List<TurntableDetail> turntableDetailList = turntableDetailService.selectList(entityWrapper5);
            if(turntableDetailList.size() > 0) {
                for (TurntableDetail turntableDetail : turntableDetailList) {
                    if (turntableDetail.getTurPrizeType() == "1") {
                        num += turntableDetail.getTurPrizeNums();
                        f = 1;
                    }
                }
            }

            // TODO  清空奖品设置
            EntityWrapper<TurntableDetail> entityWrapper = new EntityWrapper();
            entityWrapper.eq("act_id",turntableModfiyReq.getId());
            turntableDetailService.delete(entityWrapper);

            //TODO  添加奖品设置
            for(TurntablePrizeSetReq turntablePrizeSetReq:turntableModfiyReq.getPrizeSetList()){
                if (turntablePrizeSetReq.getTurPrizeType() == 1) {
                    fenbi += turntablePrizeSetReq.getTurPrizeNums();
                }
                TurntableDetail turntableDetail = new TurntableDetail();
                turntableDetail.setActId(turntableMain.getId());
                turntableDetail.setTurPrizeType(turntablePrizeSetReq.getTurPrizeType().toString());
                turntableDetail.setTurPrizeLimit(turntablePrizeSetReq.getTurPrizeLimit());
                turntableDetail.setTurPrizeName(turntablePrizeSetReq.getTurPrizeName());
                turntableDetail.setTurPrizeNums(turntablePrizeSetReq.getTurPrizeNums());
                turntableDetail.setTurPrizeChance(turntablePrizeSetReq.getTurPrizeChance());
                turntableDetail.setNickname(turntablePrizeSetReq.getNickname());

                turntableDetailService.insert(turntableDetail);
            }

            if(fenbi > 0){//冻结粉币
                if( f > 0){
                    if ((fenbi - num) <= (0 - num)) {
                        throw new TurntableException(ResponseEnums.TURNTABLE_HAS9);
                    }
                    // 判断账户中的粉币是否足够
                    if (busUser.getFansCurrency().doubleValue() < (fenbi - num)) {
                        throw new TurntableException(ResponseEnums.TURNTABLE_HAS7);
                    }
                    UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                    updateFenbiReduceReq.setBusId(busUser.getId());
                    updateFenbiReduceReq.setFkId(turntableMain.getId());
                    updateFenbiReduceReq.setFreType(1);
                    updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi - num));
                    AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                    if (axisResult.getCode() != 0) {
                        throw new TurntableException(ResponseEnums.TURNTABLE_HAS8);
                    }
                }else {
                    // 判断账户中的粉币是否足够
                    if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                        throw new TurntableException(ResponseEnums.TURNTABLE_HAS7);
                    }
                    //构建冻结信息
                    FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, turntableMain.getId(), 1, 1, "大转盘活动支出", 0);
                    // 保存冻结信息
                    if(ffr!=null){
                        FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                        BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                        AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                        if(axisResult.getCode() != 0){
                            throw new TurntableException(ResponseEnums.TURNTABLE_HAS8);
                        }
                    }
                }
            }
        }
    }

    /**
     * 大转盘活动暂停/开始活动
     * @param busUser
     * @param turntableStopIdReq
     * @return
     */
    @Override
    public ResponseDTO stopTurntable(WxPublicUsers busUser, TurntableStopIdReq turntableStopIdReq) {

        TurntableMain turntableMain = turntableMainService.selectById(turntableStopIdReq.getId());
        if(CommonUtil.isNotEmpty(turntableMain)){
            if(turntableMain.getActWxUserid().intValue() != busUser.getId().intValue()){
                throw new TurntableException(ResponseEnums.DIFF_USER);
            }
            Date date = new Date();
            if(turntableMain.getActBeginTime().getTime() > date.getTime()){
                throw new TurntableException(ResponseEnums.TURNTABLE_HAS15);
            }
            if(turntableMain.getActEndTime().getTime() < date.getTime()){
                throw new TurntableException(ResponseEnums.TURNTABLE_HAS16);
            }
            turntableMain.setActStatus(turntableStopIdReq.getStatus());
            turntableMainService.updateById(turntableMain);
        }
        return ResponseDTO.createBySuccess("操作成功");
    }

    /**
     * 删除大转盘活动
     * @param busUser
     * @param turntableDelReq
     */
    @Override
    public void delTurntable(BusUser busUser, TurntableDelReq turntableDelReq) {

        TurntableMain turntableMain = turntableMainService.selectById(turntableDelReq.getId());
        if(CommonUtil.isNotEmpty(turntableMain)) {
            if (turntableMain.getActBeginTime().getTime() < new Date().getTime() && turntableMain.getActEndTime().getTime() > new Date().getTime()) {
                throw new TurntableException(ResponseEnums.TURNTABLE_HAS11);
            }

            List<WinningRecord> winningRecordList = winningRecordService.selectList(
                    new EntityWrapper<WinningRecord>().eq("win_act_id", turntableDelReq.getId()).eq("win_status", 3));
            if (winningRecordList.size() > 0) {
                throw new TurntableException(ResponseEnums.TURNTABLE_HAS13);
            }
        }
        //TODO  删除活动
        boolean b = turntableMainService.deleteById(turntableDelReq.getId());
        if(b==false){
            throw  new TurntableException(ResponseEnums.TURNTABLE_HAS6);
        }

        EntityWrapper<TurntableDetail> entityWrapper3 = new EntityWrapper<>();
        entityWrapper3.eq("act_id",turntableDelReq.getId());
        List<TurntableDetail> turntableDetailList = turntableDetailService.selectList(entityWrapper3);

        boolean ff = false;
        if(turntableDetailList.size() > 0){
            for(TurntableDetail turntableDetail : turntableDetailList){
                if(turntableDetail.getTurPrizeType()== "1"){
                    ff = true;
                }
            }
        }

        //TODO  删除奖品设置
        EntityWrapper<TurntableDetail> entityWrapper5 = new EntityWrapper<>();
        entityWrapper5.eq("act_id",turntableDelReq.getId());
        turntableDetailService.delete(entityWrapper5);

        //TODO  删除中奖信息
        EntityWrapper<WinningRecord> entityWrapper4 = new EntityWrapper<>();
        entityWrapper4.eq("win_act_id",turntableDelReq.getId());
        winningRecordService.delete(entityWrapper4);


        //删除冻结信息
        if(ff){
            FenbiSurplus fenbiSurplus = new FenbiSurplus();
            fenbiSurplus.setBusId(busUser.getId());
            fenbiSurplus.setFkId(turntableMain.getId());
            fenbiSurplus.setFre_type(1);
            fenbiSurplus.setRec_type(1);
            AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
            if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                // 获取冻结信息中粉币剩余量
                FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                fenbiSurplus1.setBusId(busUser.getId());
                fenbiSurplus1.setFkId(turntableMain.getId());
                fenbiSurplus1.setRec_type(1);
                fenbiSurplus1.setFre_type(1);
                AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                if(axisResult.getCode() != 0){
                    throw new TurntableException(ResponseEnums.TURNTABLE_HAS14);
                }
            }
        }
    }

    /**
     * 分页获取大转盘中奖记录列表
     * @param busUser
     * @param turntableGetWinningReq
     * @return
     */
    @Override
    public ResponseDTO<List<TurntableGetWinningRes>> getWinningList(BusUser busUser, TurntableGetWinningReq turntableGetWinningReq) {

        Page<TurntableGetWinningRes> page = new Page<>(turntableGetWinningReq.getCurrent(),turntableGetWinningReq.getSize());
        List<TurntableGetWinningRes> turntableGetWinningResList = winningRecordDAO.queryRecodList(page,turntableGetWinningReq);

        //TODO  获取用户名
        if(turntableGetWinningResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < turntableGetWinningResList.size(); i++) {
                ids.append(turntableGetWinningResList.get(i).getMemberId());
                if (turntableGetWinningResList.size() > 1 && i < turntableGetWinningResList.size() - 1) {
                    ids.append(",");
                }
            }
            MemberReq memberReq = new MemberReq();
            memberReq.setBusId(busUser.getId());
            memberReq.setIds(ids.toString());
            AxisResult<List<MemberRes>> axisResult = null;
            try {
                axisResult = MemberServer.findMemberByIds(memberReq);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<MemberRes> memberResList = null;
            if (CommonUtil.isNotEmpty(axisResult)) {
                memberResList = axisResult.getData();
            } else {
                memberResList = new ArrayList<>();
            }
            for (int i = 0; i < turntableGetWinningResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == turntableGetWinningResList.get(i).getMemberId().intValue()) {
                        turntableGetWinningResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(turntableGetWinningResList.get(i).getMemberName())) {
                    turntableGetWinningResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取大转盘活动中奖记录列表成功",turntableGetWinningResList,pageDTO);
    }

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param turntableEditApplyReq
     * @return
     */
    @Override
    public ResponseDTO editTurntableApply(BusUser busUser, TurntableEditApplyReq turntableEditApplyReq) {

        WinningRecord winningRecord = winningRecordService.selectById(turntableEditApplyReq.getId());
        if(CommonUtil.isNotEmpty(winningRecord)){
            TurntableMain turntableMain = turntableMainService.selectById(winningRecord.getWinActId());
            TurntableDetail turntableDetail = turntableDetailService.selectById(winningRecord.getWinPrizeId());
            if(turntableDetail.getTurPrizeType()!= "4"){    //非兑奖
                if (DateTimeKit.laterThanNow(turntableMain.getActBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new TurntableException(ResponseEnums.TURNTABLE_HAS1);
                } else if (!DateTimeKit.laterThanNow(turntableMain.getActEndTime())) {
                    //""已过兑奖时间！";
                    throw  new TurntableException(ResponseEnums.TURNTABLE_HAS2);
                }
            }
            if (winningRecord.getWinStatus() == 3) {
                // 更改记录状态
                winningRecord.setWinStatus(2);
                winningRecord.setWinCreatetime(new Date());
                winningRecordService.updateById(winningRecord);
            } else if (winningRecord.getWinStatus() == 2){//已兑奖
                throw  new TurntableException(ResponseEnums.TURNTABLE_HAS3);
            }else{//还未提交
                throw  new TurntableException(ResponseEnums.TURNTABLE_HAS4);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }

    /**
     * 导出大转盘中奖记录
     * @param params
     * @param busUser
     * @return
     */
    @Override
    public Map<String, Object> exportTurntable(Map<String, Object> params, BusUser busUser) {

        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            TurntableMain turntableMain = turntableMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = winningRecordDAO.findExports(params);
            String title = "活动名称：" + turntableMain.getActName()+ "，开始时间：" + sdf.format(turntableMain.getActBeginTime()) + "结束时间："
                    + sdf.format(turntableMain.getActEndTime());
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("win_wx_member_id"));
                    if (list.size() > 1 && i < list.size() - 1) {
                        ids.append(",");
                    }
                }
                MemberReq memberReq = new MemberReq();
                memberReq.setBusId(busUser.getId());
                memberReq.setIds(ids.toString());
                AxisResult<List<MemberRes>> axisResult = null;
                try {
                    axisResult = MemberServer.findMemberByIds(memberReq);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<MemberRes> memberResList = null;
                if (CommonUtil.isNotEmpty(axisResult)) {
                    memberResList = axisResult.getData();
                } else {
                    memberResList = new ArrayList<>();
                }
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < memberResList.size(); j++) {
                        if (memberResList.get(j).getId().intValue() == CommonUtil.toInteger(list.get(i).get("win_memberId")).intValue()) {
                            list.get(i).put("nickname",memberResList.get(j).getNickname());
                        }
                    }
                    if (CommonUtil.isEmpty(list.get(i).get("nickname"))) {
                        list.get(i).put("nickname","游客");
                    }
                }
            }
            HSSFWorkbook book = exportExcelForRecoding(list, title);
            msg.put("book", book);
            msg.put("fileName", turntableMain.getActName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "大转盘活动中奖记录导出excel失败！");
            e.printStackTrace();
        } finally {
            msg.put("result", result);
            msg.put("message", message);
        }
        return msg;
    }

    private HSSFWorkbook exportExcelForRecoding(List<Map<String, Object>> list, String title) {
        // 创建excel文件对象
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个张表
        Sheet sheet = wb.createSheet();
        // 创建第一行
        Row row = sheet.createRow(0);
        // 设置行高
        row.setHeight((short) 500);
        // 创建第二行
        Row row1 = sheet.createRow(1);
        // 处理时间
        // 设置列的宽度
        sheet.setColumnWidth((short) (2), (short) 4000);
        sheet.setColumnWidth((short) (3), (short) 4000);
        sheet.setColumnWidth((short) (4), (short) 8000);
        sheet.setColumnWidth((short) (5), (short) 8000);
        sheet.setColumnWidth((short) (6), (short) 6000);
        sheet.setColumnWidth((short) (7), (short) 6000);
        sheet.setColumnWidth((short) (8), (short) 6000);
        // 文件头字体
        Font font0 = createFonts(wb, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
        Font font1 = createFonts(wb, Font.BOLDWEIGHT_NORMAL, "宋体", false, (short) 200);
        // 合并第一行的单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 6));
        // 设置第一列的文字
        createCell(wb, row, 1, title, font0);
        // 给第二行添加文本
        createCell(wb, row1, 0, "序号", font1);
        createCell(wb, row1, 1, "奖品名称", font1);
        createCell(wb, row1, 2, "奖品", font1);
        createCell(wb, row1, 3, "中奖时间", font1);
        createCell(wb, row1, 4, "兑奖人联系方式", font1);
        createCell(wb, row1, 5, "中奖人昵称", font1);
        createCell(wb, row1, 6, "状态", font1);
        createCell(wb, row1, 7, "兑奖码", font1);
        // 第三行表示
        int l = 2;
        String priTypeName = "";
        String priTypeUnit = "";
        // 这里将的信息存入到表格中
        for (int i = 0; i < list.size(); i++) {
            // 创建一行
            Row rowData = sheet.createRow(l++);
            Map<String, Object> map = list.get(i);
            createCell(wb, rowData, 0, String.valueOf(i + 1), font1);
            createCell(wb, rowData, 1, delWithColumn(map.get("tur_prize_name")), font1);
            String turPriType = delWithColumn(map.get("tur_prize_type"));
            if ("4".equals(turPriType)) {// 实物
                priTypeName = "实体物品";
                priTypeUnit = "份";
            } else {
                if ("1".equals(turPriType)) {// 粉币
                    priTypeName = "粉币";
                    priTypeUnit = "个";
                }
                if ("2".equals(turPriType)) {// 流量
                    priTypeName = "流量";
                    priTypeUnit = "M";
                }
                if ("3".equals(turPriType)) {// 话费
                    priTypeName = "话费";
                    priTypeUnit = "元";
                }
            }
            createCell(wb, rowData, 2, priTypeName + "/" + delWithColumn(map.get("tur_prize_limit")) + priTypeUnit, font1);

            createCell(wb, rowData, 3,
                    delWithColumn(CommonUtil.isEmpty(map.get("cashTime")) ? ""
                            : DateTimeKit.format(
                            DateTimeKit.parseDate(map.get("cashTime").toString(), "yyyy/MM/dd HH:mm:ss"),
                            "yyyy-MM-dd HH:mm")),
                    font1);
            createCell(wb, rowData, 4, delWithColumn(map.get("win_phone")), font1);
            createCell(wb, rowData, 5,
                    CommonUtil.isEmpty(map.get("nickname")) ? "游客" : map.get("nickname").toString(),
                    font1);
            if ("1".equals(delWithColumn(map.get("win_status")).toString())) {
                createCell(wb, rowData, 6, "未兑奖", font1);
            } else if ("2".equals(delWithColumn(map.get("status")).toString())) {
                createCell(wb, rowData, 6, "已兑奖", font1);
            } else {
                createCell(wb, rowData, 6, "已提交", font1);
            }
            createCell(wb, rowData, 7, delWithColumn(map.get("win_exchangeCode")), font1);

        }
        return wb;
    }

    /**
     * 设置字体
     *
     * @param wb
     * @return
     */
    public static Font createFonts(Workbook wb, short bold, String fontName, boolean isItalic, short hight) {
        Font font = wb.createFont();
        font.setFontName(fontName);
        font.setBoldweight(bold);
        font.setItalic(isItalic);
        font.setFontHeight(hight);
        return font;
    }

    /**
     * 创建单元格并设置样式,值
     *
     * @param wb
     * @param row
     * @param column
     * @param
     * @param
     * @param value
     */
    public static void createCell(Workbook wb, Row row, int column, String value, Font font) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    private static String delWithColumn(Object obj) {
        if (CommonUtil.isEmpty(obj)) {
            return "";
        } else {
            return obj.toString();
        }
    }

    /**
     *  批量删除中奖信息记录
     * @param busUser
     * @param turntableDelWinningReq
     */
    @Override
    public void delTurntableWinning(BusUser busUser, TurntableDelWinningReq turntableDelWinningReq) {

        winningRecordService.deleteBatchIds(turntableDelWinningReq.getId());
    }

    /**
     * 获取全部奖品类型
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<List<TurntablePrizeTypeListRes>> getTurntablePrizeType(BusUser busUser) {

        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<TurntablePrizeTypeListRes> turntablePrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                TurntablePrizeTypeListRes turntablePrizeTypeListRes = new TurntablePrizeTypeListRes();
                turntablePrizeTypeListRes.setName(dictApiRes.getItemValue());
                turntablePrizeTypeListRes.setValue(dictApiRes.getItemKey());
                turntablePrizeTypeListResList.add(turntablePrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",turntablePrizeTypeListResList);
    }
}