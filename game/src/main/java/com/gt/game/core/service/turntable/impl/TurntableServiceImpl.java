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
import com.gt.axis.bean.wxmp.fenbiflow.UpdateFenbiReduceReq;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.member.MemberServer;
import com.gt.axis.server.wxmp.DictServer;
import com.gt.axis.server.wxmp.FenbiflowServer;
import com.gt.game.common.config.ApplyProperties;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.scratch.req.ScratchPrizeSetReq;
import com.gt.game.core.bean.scratch.res.ScratchCountActivityRes;
import com.gt.game.core.bean.scratch.res.ScratchGetActivityRes;
import com.gt.game.core.bean.scratch.res.ScratchListRes;
import com.gt.game.core.bean.tree.req.*;
import com.gt.game.core.bean.tree.res.*;
import com.gt.game.core.bean.turntable.req.*;
import com.gt.game.core.bean.turntable.res.TurntableCountActivityRes;
import com.gt.game.core.bean.turntable.res.TurntableGetActivityRes;
import com.gt.game.core.bean.turntable.res.TurntableListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.tree.TreeWinningDAO;
import com.gt.game.core.entity.scratch.ScratchDetail;
import com.gt.game.core.entity.scratch.ScratchMain;
import com.gt.game.core.entity.tree.TreeDetail;
import com.gt.game.core.entity.tree.TreeMain;
import com.gt.game.core.entity.tree.TreeWinning;
import com.gt.game.core.entity.turntable.TurntableDetail;
import com.gt.game.core.entity.turntable.TurntableMain;
import com.gt.game.core.exception.scratch.ScratchException;
import com.gt.game.core.exception.tree.TreeException;
import com.gt.game.core.exception.turntable.TurntableException;
import com.gt.game.core.service.tree.TreeDetailService;
import com.gt.game.core.service.tree.TreeMainService;
import com.gt.game.core.service.tree.TreeService;
import com.gt.game.core.service.tree.TreeWinningService;
import com.gt.game.core.service.turntable.TurntableDetailService;
import com.gt.game.core.service.turntable.TurntableMainService;
import com.gt.game.core.service.turntable.TurntableService;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
}