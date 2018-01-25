package com.gt.game.core.service.scratch.impl;

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
import com.gt.game.core.bean.eggs.req.EggsPrizeSetReq;
import com.gt.game.core.bean.eggs.res.EggsCountActivityRes;
import com.gt.game.core.bean.eggs.res.EggsGetWinningRes;
import com.gt.game.core.bean.eggs.res.EggsListRes;
import com.gt.game.core.bean.eggs.res.EggsPrizeTypeListRes;
import com.gt.game.core.bean.qixi.req.*;
import com.gt.game.core.bean.qixi.res.*;
import com.gt.game.core.bean.scratch.req.*;
import com.gt.game.core.bean.scratch.res.*;
import com.gt.game.core.bean.turntable.req.ScratchStartReq;
import com.gt.game.core.bean.turntable.req.ScratchStopReq;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.qixi.QixiCashPrizeApplyDAO;
import com.gt.game.core.dao.qixi.QixiMainDAO;
import com.gt.game.core.dao.scratch.ScratchMainDAO;
import com.gt.game.core.dao.scratch.ScratchWinningDAO;
import com.gt.game.core.entity.dragonboat.*;
import com.gt.game.core.entity.eggs.EggsDetail;
import com.gt.game.core.entity.eggs.EggsMain;
import com.gt.game.core.entity.eggs.EggsWinning;
import com.gt.game.core.entity.luck.LuckMain;
import com.gt.game.core.entity.qixi.*;
import com.gt.game.core.entity.scratch.ScratchDetail;
import com.gt.game.core.entity.scratch.ScratchMain;
import com.gt.game.core.entity.scratch.ScratchWinning;
import com.gt.game.core.exception.dragonboat.DragonboatException;
import com.gt.game.core.exception.eggs.EggsException;
import com.gt.game.core.exception.luck.LuckException;
import com.gt.game.core.exception.qixi.QixiException;
import com.gt.game.core.exception.scratch.ScratchException;
import com.gt.game.core.service.qixi.*;
import com.gt.game.core.service.scratch.ScratchDetailService;
import com.gt.game.core.service.scratch.ScratchMainService;
import com.gt.game.core.service.scratch.ScratchService;
import com.gt.game.core.service.scratch.ScratchWinningService;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 刮刮乐 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
@Service
public class ScratchServiceImpl implements ScratchService {

    @Autowired
    ScratchMainService scratchMainService;

    @Autowired
    ScratchDetailService scratchDetailService;

    @Autowired
    ScratchWinningService scratchWinningService;

    @Autowired
    ScratchWinningDAO scratchWinningDAO;

    @Autowired
    ApplyProperties applyProperties;

    /**
     * 获取手机端链接
     * @param loginPbUser
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(WxPublicUsers loginPbUser, MobileUrlReq mobileUrlReq) {

        String url = applyProperties.getMobileBaseUrl() + "scratch/"+loginPbUser.getId()+"/"+ mobileUrlReq.getMainId() + "/-100"+"/79B4DE7C/userGrant.do";
        return new MobileUrlRes(url);
    }

    /**
     * 分页获取刮刮乐活动列表
     * @param loginPbUser
     * @param scratchListReq
     * @return
     */
    @Override
    public ResponseDTO<List<ScratchListRes>> getScratchList(WxPublicUsers loginPbUser, ScratchListReq scratchListReq) {

        EntityWrapper<ScratchMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("scr_wx_userid",loginPbUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(scratchListReq.getName()), "scr_name", scratchListReq.getName());
        if (scratchListReq.getStatus() != -1) {   // TODO -1 全部 0 未开始 1 进行中 2 已结束 3.已暂停

            if (scratchListReq.getStatus() == 0) {
                entityWrapper.where("scr_beginTime > {0}", new Date());
            }
            if (scratchListReq.getStatus() == 1) {
                entityWrapper.where("scr_beginTime <= {0}", new Date());
                entityWrapper.and();
                entityWrapper.where("scr_endTime > {0}", new Date());
            }
            if (scratchListReq.getStatus() == 2) {
                entityWrapper.where("scr_endTime <= {0}", new Date());
            }
            if(scratchListReq.getStatus()==3){    //TODO   已暂停
                entityWrapper.eq("scr_status", 2);
            }
        }
        Page<ScratchMain> page = new Page<>(scratchListReq.getCurrent(), scratchListReq.getSize());
        List<ScratchMain> scratchMainList = scratchMainService.selectPage(page, entityWrapper).getRecords();

        List<ScratchListRes> scratchListResList = new ArrayList<>();
        for (ScratchMain scratchMain : scratchMainList) {
            ScratchListRes scratchListRes = new ScratchListRes();
            scratchListRes.setId(scratchMain.getId());
            scratchListRes.setName(scratchMain.getScrName());
            scratchListRes.setActivityBeginTime(scratchMain.getScrBeginTime());
            scratchListRes.setActivityEndTime(scratchMain.getScrEndTime());

            if(scratchMain.getScrStatus()==2){    //TODO   已暂停
                scratchListRes.setStatus(3);
            }else {
                Date date = new Date();
                if (scratchMain.getScrBeginTime().getTime() > date.getTime()) {
                    scratchListRes.setStatus(0);
                } else if (scratchMain.getScrBeginTime().getTime() <= date.getTime() && scratchMain.getScrEndTime().getTime() >= date.getTime()) {
                    scratchListRes.setStatus(1);
                } else if (scratchMain.getScrEndTime().getTime() < date.getTime()) {
                    scratchListRes.setStatus(2);
                }
            }
            scratchListResList.add(scratchListRes);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取刮刮乐活动列表成功", scratchListResList, pageDTO);
    }

    /**
     * 统计刮刮乐活动总数
     * @param loginPbUser
     * @param scratchCountActivityReq
     * @return
     */
    @Override
    public ScratchCountActivityRes countScratch(WxPublicUsers loginPbUser, ScratchCountActivityReq scratchCountActivityReq) {

        EntityWrapper<ScratchMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("scr_wx_userid",loginPbUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(scratchCountActivityReq.getName()), "scr_name", scratchCountActivityReq.getName());

        List<ScratchMain> scratchMainList = scratchMainService.selectList(entityWrapper);

        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        int count5 = 0;

        for (ScratchMain scratchMain : scratchMainList) {
            if(scratchMain.getScrStatus()==2){    //TODO   已暂停
                count5++;
            }else {
                Date date = new Date();
                if (scratchMain.getScrBeginTime().getTime() > date.getTime()) {
                    count2++;
                } else if (scratchMain.getScrBeginTime().getTime() <= date.getTime() && scratchMain.getScrEndTime().getTime() >= date.getTime()) {
                    count3++;
                } else if (scratchMain.getScrEndTime().getTime() < date.getTime()) {
                    count4++;
                }
            }
        }

        ScratchCountActivityRes scratchCountActivityRes = new ScratchCountActivityRes();
        scratchCountActivityRes.setCount2(count2);
        scratchCountActivityRes.setCount3(count3);
        scratchCountActivityRes.setCount4(count4);
        scratchCountActivityRes.setCount5(count5);
        scratchCountActivityRes.setCount1(count2+count3+count4+count5);  //  TODO 全部
        return  scratchCountActivityRes;
    }

    /**
     * 新增刮刮乐活动
     * @param loginPbUser
     * @param scratchAddReq
     */
    @Override
    public void addScratch(BusUser busUser ,WxPublicUsers loginPbUser, ScratchAddReq scratchAddReq) {

        ScratchMain scratchMain = new ScratchMain();
        scratchMain.setScrWxUserid(loginPbUser.getId());

        //TODO  基础设置
        scratchMain.setScrName(scratchAddReq.getScrName());
        scratchMain.setScrBeginTime(scratchAddReq.getScrBeginTime());
        scratchMain.setScrEndTime(scratchAddReq.getScrEndTime());
        scratchMain.setScrPartaker(scratchAddReq.getScrPartaker());
        if(scratchAddReq.getScrPartaker()==2){
            scratchMain.setScrPway(scratchAddReq.getScrPway());
            if(scratchAddReq.getScrPway()==2){
                scratchMain.setScrMan(scratchAddReq.getScrMan());
            }else if(scratchAddReq.getScrPway()==3){
                scratchMain.setScrKou(scratchAddReq.getScrKou());
            }else if(scratchAddReq.getScrPway()==4){
                scratchMain.setScrMan(scratchAddReq.getScrMan());
                scratchMain.setScrKou(scratchAddReq.getScrKou());
            }
        }
        scratchMain.setScrPicture(scratchAddReq.getScrPicture());
        scratchMain.setScrDescribe(scratchAddReq.getScrDescribe());
        scratchMain.setScrBeforeTxt(scratchAddReq.getScrBeforeTxt());
        scratchMain.setScrBgmName(scratchAddReq.getScrBgmName());
        scratchMain.setScrBgm(scratchAddReq.getScrBgm());

        //TODO  规则设置
        scratchMain.setScrCountOfAll(scratchAddReq.getScrCountOfAll());
        scratchMain.setScrCountOfDay(scratchAddReq.getScrCountOfDay());

        //TODO  兑奖设置
        scratchMain.setScrCashDay(scratchAddReq.getScrCashDay());
        scratchMain.setScrAddress(scratchAddReq.getScrAddress());
        scratchMain.setScrCashWay(scratchAddReq.getScrCashWay());
        scratchMain.setScrWinningTxt(scratchAddReq.getScrWinningTxt());
        scratchMain.setScrWinningNotice(scratchAddReq.getScrWinningNotice());

        scratchMainService.insert(scratchMain);

        //TODO   奖项设置
        Double fenbi = 0.0;
        for(ScratchPrizeSetReq scratchPrizeSetReq:scratchAddReq.getPrizeSetList()){
            if (scratchPrizeSetReq.getScrPrizeType() == 1) {
                fenbi += scratchPrizeSetReq.getScrPrizeNums();
            }
            ScratchDetail scratchDetail = new ScratchDetail();
            scratchDetail.setScrId(scratchMain.getId());
            scratchDetail.setScrPrizeType(scratchPrizeSetReq.getScrPrizeType());
            scratchDetail.setScrPrizeLimit(scratchPrizeSetReq.getScrPrizeLimit());
            scratchDetail.setScrPrizeName(scratchPrizeSetReq.getScrPrizeName());
            scratchDetail.setScrPrizeNums(scratchPrizeSetReq.getScrPrizeNums());
            scratchDetail.setScrPrizeChance(scratchPrizeSetReq.getScrPrizeChance());
            scratchDetail.setNickname(scratchPrizeSetReq.getNickname());

            scratchDetailService.insert(scratchDetail);
        }

        if(fenbi > 0){//冻结粉币
            // 判断账户中的粉币是否足够
            if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                throw new ScratchException(ResponseEnums.SCRATCH_HAS7);
            }
            //构建冻结信息
            FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, scratchMain.getId(), 3, 1, "刮刮乐活动支出", 0);
            // 保存冻结信息
            if(ffr!=null){
                FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                if(axisResult.getCode() != 0){
                    throw new ScratchException(ResponseEnums.SCRATCH_HAS8);
                }
            }
        }
    }

    /**
     * 通过活动id查询刮刮乐活动
     * @param busUser
     * @param scratchGetActivityReq
     * @return
     */
    @Override
    public ScratchGetActivityRes getActivityById(BusUser busUser, ScratchGetActivityReq scratchGetActivityReq) {

        ScratchMain scratchMain = scratchMainService.selectById(scratchGetActivityReq.getId());
        ScratchGetActivityRes scratchGetActivityRes = new ScratchGetActivityRes();

        //TODO  基础设置
        scratchGetActivityRes.setScrName(scratchMain.getScrName());
        scratchGetActivityRes.setScrBeginTime(scratchMain.getScrBeginTime());
        scratchGetActivityRes.setScrEndTime(scratchMain.getScrEndTime());
        scratchGetActivityRes.setScrPartaker(scratchMain.getScrPartaker());
        if (scratchMain.getScrPartaker() == 2) {
            scratchGetActivityRes.setScrPway(scratchMain.getScrPway());
            if (scratchMain.getScrPway() == 2) {
                scratchGetActivityRes.setScrMan(scratchMain.getScrMan());
            } else if (scratchMain.getScrPway() == 3) {
                scratchGetActivityRes.setScrKou(scratchMain.getScrKou());
            } else if (scratchMain.getScrPway() == 4) {
                scratchGetActivityRes.setScrMan(scratchMain.getScrMan());
                scratchGetActivityRes.setScrKou(scratchMain.getScrKou());
            }
        }
        scratchGetActivityRes.setScrPicture(scratchMain.getScrPicture());
        scratchGetActivityRes.setScrDescribe(scratchMain.getScrDescribe());
        scratchGetActivityRes.setScrBeforeTxt(scratchMain.getScrBeforeTxt());
        scratchGetActivityRes.setScrBgmName(scratchMain.getScrBgmName());
        scratchGetActivityRes.setScrBgm(scratchMain.getScrBgm());

        //TODO  规则设置
        scratchGetActivityRes.setScrCountOfAll(scratchMain.getScrCountOfAll());
        scratchGetActivityRes.setScrCountOfDay(scratchMain.getScrCountOfDay());

        //TODO  兑奖设置
        scratchGetActivityRes.setScrCashDay(scratchMain.getScrCashDay());
        scratchGetActivityRes.setScrAddress(scratchMain.getScrAddress());
        scratchGetActivityRes.setScrCashWay(scratchMain.getScrCashWay());
        scratchGetActivityRes.setScrWinningTxt(scratchMain.getScrWinningTxt());
        scratchGetActivityRes.setScrWinningNotice(scratchMain.getScrWinningNotice());

        //TODO   奖项设置
        EntityWrapper<ScratchDetail> entityWrapper = new EntityWrapper();
        entityWrapper.eq("scr_id",scratchGetActivityReq.getId());
        List<ScratchDetail> scratchDetailList = scratchDetailService.selectList(entityWrapper);

        List<ScratchPrizeSetReq> list = new ArrayList<>();
        for(ScratchDetail scratchDetail:scratchDetailList){

            ScratchPrizeSetReq scratchPrizeSetReq = new ScratchPrizeSetReq();
            scratchPrizeSetReq.setScrPrizeType(scratchDetail.getScrPrizeType());
            scratchPrizeSetReq.setScrPrizeLimit(scratchDetail.getScrPrizeLimit());
            scratchPrizeSetReq.setScrPrizeName(scratchDetail.getScrPrizeName());
            scratchPrizeSetReq.setScrPrizeNums(scratchDetail.getScrPrizeNums());
            scratchPrizeSetReq.setScrPrizeChance(scratchDetail.getScrPrizeChance());
            scratchPrizeSetReq.setNickname(scratchDetail.getNickname());
            list.add(scratchPrizeSetReq);
        }
        scratchGetActivityRes.setPrizeSetList(list);

        return  scratchGetActivityRes;

    }

    /**
     * 编辑刮刮乐活动设置
     * @param busUser
     * @param scratchModfiyReq
     */
    @Override
    public void modfiyScratch(BusUser busUser, ScratchModfiyReq scratchModfiyReq) {

        ScratchMain scratchMain = scratchMainService.selectById(scratchModfiyReq.getId());
        if(CommonUtil.isEmpty(scratchMain)){
            throw new ScratchException(ResponseEnums.SCRATCH_HAS5);
        }
        if(scratchMain.getScrBeginTime().getTime() < new Date().getTime()){
            throw new ScratchException(ResponseEnums.SCRATCH_HAS10);
        }

        //TODO  基础设置
        scratchMain.setScrName(scratchModfiyReq.getScrName());
        scratchMain.setScrBeginTime(scratchModfiyReq.getScrBeginTime());
        scratchMain.setScrEndTime(scratchModfiyReq.getScrEndTime());
        scratchMain.setScrPartaker(scratchModfiyReq.getScrPartaker());
        if(scratchModfiyReq.getScrPartaker()==2){
            scratchMain.setScrPway(scratchModfiyReq.getScrPway());
            if(scratchModfiyReq.getScrPway()==2){
                scratchMain.setScrMan(scratchModfiyReq.getScrMan());
            }else if(scratchModfiyReq.getScrPway()==3){
                scratchMain.setScrKou(scratchModfiyReq.getScrKou());
            }else if(scratchModfiyReq.getScrPway()==4){
                scratchMain.setScrMan(scratchModfiyReq.getScrMan());
                scratchMain.setScrKou(scratchModfiyReq.getScrKou());
            }
        }
        scratchMain.setScrPicture(scratchModfiyReq.getScrPicture());
        scratchMain.setScrDescribe(scratchModfiyReq.getScrDescribe());
        scratchMain.setScrBeforeTxt(scratchModfiyReq.getScrBeforeTxt());
        scratchMain.setScrBgmName(scratchModfiyReq.getScrBgmName());
        scratchMain.setScrBgm(scratchModfiyReq.getScrBgm());

        //TODO  规则设置
        scratchMain.setScrCountOfAll(scratchModfiyReq.getScrCountOfAll());
        scratchMain.setScrCountOfDay(scratchModfiyReq.getScrCountOfDay());

        //TODO  兑奖设置
        scratchMain.setScrCashDay(scratchModfiyReq.getScrCashDay());
        scratchMain.setScrAddress(scratchModfiyReq.getScrAddress());
        scratchMain.setScrCashWay(scratchModfiyReq.getScrCashWay());
        scratchMain.setScrWinningTxt(scratchModfiyReq.getScrWinningTxt());
        scratchMain.setScrWinningNotice(scratchModfiyReq.getScrWinningNotice());

        scratchMainService.updateById(scratchMain);



        Double fenbi = 0.0;
        Double num   = 0.0;
        int f = 0;
        if(scratchModfiyReq.getPrizeSetList().size()>0){

            EntityWrapper<ScratchDetail> entityWrapper5 = new EntityWrapper();
            entityWrapper5.eq("scr_id",scratchModfiyReq.getId());
            List<ScratchDetail> scratchDetailList = scratchDetailService.selectList(entityWrapper5);
            if(scratchDetailList.size() > 0) {
                for (ScratchDetail scratchDetail : scratchDetailList) {
                    if (scratchDetail.getScrPrizeType() == 1) {
                        num += scratchDetail.getScrPrizeNums();
                        f = 1;
                    }
                }
            }


            // TODO  清空奖品设置
            EntityWrapper<ScratchDetail> entityWrapper = new EntityWrapper();
            entityWrapper.eq("scr_id",scratchModfiyReq.getId());
            scratchDetailService.delete(entityWrapper);


            //TODO   添加奖项设置

            for(ScratchPrizeSetReq scratchPrizeSetReq:scratchModfiyReq.getPrizeSetList()){
                if (scratchPrizeSetReq.getScrPrizeType() == 1) {
                    fenbi += scratchPrizeSetReq.getScrPrizeNums();
                }
                ScratchDetail scratchDetail = new ScratchDetail();
                scratchDetail.setScrId(scratchMain.getId());
                scratchDetail.setScrPrizeType(scratchPrizeSetReq.getScrPrizeType());
                scratchDetail.setScrPrizeLimit(scratchPrizeSetReq.getScrPrizeLimit());
                scratchDetail.setScrPrizeName(scratchPrizeSetReq.getScrPrizeName());
                scratchDetail.setScrPrizeNums(scratchPrizeSetReq.getScrPrizeNums());
                scratchDetail.setScrPrizeChance(scratchPrizeSetReq.getScrPrizeChance());
                scratchDetail.setNickname(scratchPrizeSetReq.getNickname());

                scratchDetailService.insert(scratchDetail);
            }
        }

        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if ((fenbi - num) <= (0 - num)) {
                    throw new ScratchException(ResponseEnums.SCRATCH_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi - num)) {
                    throw new ScratchException(ResponseEnums.SCRATCH_HAS7);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(scratchMain.getId());
                updateFenbiReduceReq.setFreType(3);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi - num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if (axisResult.getCode() != 0) {
                    throw new ScratchException(ResponseEnums.SCRATCH_HAS8);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new ScratchException(ResponseEnums.SCRATCH_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, scratchMain.getId(), 3, 1, "刮刮乐活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new ScratchException(ResponseEnums.SCRATCH_HAS8);
                    }
                }
            }
        }
    }

    /**
     * 刮刮乐 暂停/开始活动
     * @param busUser
     * @param scratchStopIdReq
     * @return
     */
    @Override
    public ResponseDTO stopScratch(WxPublicUsers busUser, ScratchStopIdReq scratchStopIdReq) {

        ScratchMain scratchMain = scratchMainService.selectById(scratchStopIdReq.getId());
        if(CommonUtil.isNotEmpty(scratchMain)){
            if(scratchMain.getScrWxUserid().intValue() != busUser.getId().intValue()){
                throw new ScratchException(ResponseEnums.DIFF_USER);
            }
            Date date = new Date();
            if(scratchMain.getScrBeginTime().getTime() > date.getTime()){
                throw new ScratchException(ResponseEnums.SCRATCH_HAS15);
            }
            if(scratchMain.getScrEndTime().getTime() < date.getTime()){
                throw new ScratchException(ResponseEnums.SCRATCH_HAS16);
            }
            scratchMain.setScrStatus(scratchStopIdReq.getStatus());
            scratchMainService.updateById(scratchMain);
        }
        return ResponseDTO.createBySuccess("操作成功");
    }

    /**
     * 删除刮刮乐活动
     * @param busUser
     * @param scratchDelReq
     */
    @Override
    public void delScratch(BusUser busUser, ScratchDelReq scratchDelReq) {

        ScratchMain scratchMain = scratchMainService.selectById(scratchDelReq.getId());
        if(CommonUtil.isNotEmpty(scratchMain)) {
            if (scratchMain.getScrBeginTime().getTime() < new Date().getTime() && scratchMain.getScrEndTime().getTime() > new Date().getTime()) {
                throw new ScratchException(ResponseEnums.SCRATCH_HAS11);
            }

            List<ScratchWinning> scratchWinningList = scratchWinningService.selectList(
                    new EntityWrapper<ScratchWinning>().eq("win_act_id", scratchDelReq.getId()).eq("win_status", 3));
            if (scratchWinningList.size() > 0) {
                throw new ScratchException(ResponseEnums.SCRATCH_HAS13);

            }
        }
        //TODO  删除活动
        boolean b = scratchMainService.deleteById(scratchDelReq.getId());
        if(b==false){
            throw  new ScratchException(ResponseEnums.SCRATCH_HAS6);
        }

        EntityWrapper<ScratchDetail> entityWrapper3 = new EntityWrapper<>();
        entityWrapper3.eq("scr_id",scratchDelReq.getId());
        List<ScratchDetail> scratchDetailList = scratchDetailService.selectList(entityWrapper3);

        boolean ff = false;
        if(scratchDetailList.size() > 0){
            for(ScratchDetail scratchDetail : scratchDetailList){
                if(scratchDetail.getScrPrizeType() == 1){
                    ff = true;
                }
            }
        }

        //TODO  删除奖品设置
        EntityWrapper<ScratchDetail> entityWrapper5 = new EntityWrapper<>();
        entityWrapper5.eq("scr_id",scratchDelReq.getId());
        scratchDetailService.delete(entityWrapper5);


        //删除冻结信息
        if(ff){
            FenbiSurplus fenbiSurplus = new FenbiSurplus();
            fenbiSurplus.setBusId(busUser.getId());
            fenbiSurplus.setFkId(scratchMain.getId());
            fenbiSurplus.setFre_type(3);
            fenbiSurplus.setRec_type(1);
            AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
            if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                // 获取冻结信息中粉币剩余量
                FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                fenbiSurplus1.setBusId(busUser.getId());
                fenbiSurplus1.setFkId(scratchMain.getId());
                fenbiSurplus1.setRec_type(1);
                fenbiSurplus1.setFre_type(3);
                AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                if(axisResult.getCode() != 0){
                    throw new ScratchException(ResponseEnums.SCRATCH_HAS14);
                }
            }
        }
    }

    /**
     * 分页获取刮刮乐中奖记录列表
     * @param busUser
     * @param scratchGetWinningReq
     * @return
     */
    @Override
    public ResponseDTO<List<ScratchGetWinningRes>> getWinningList(BusUser busUser, ScratchGetWinningReq scratchGetWinningReq) {

        Page<ScratchGetWinningRes> page = new Page<>(scratchGetWinningReq.getCurrent(),scratchGetWinningReq.getSize());
        List<ScratchGetWinningRes> scratchGetWinningResList = scratchWinningDAO.queryRecodList(page,scratchGetWinningReq);

        //TODO  获取用户名
        if(scratchGetWinningResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < scratchGetWinningResList.size(); i++) {
                ids.append(scratchGetWinningResList.get(i).getMemberId());
                if (scratchGetWinningResList.size() > 1 && i < scratchGetWinningResList.size() - 1) {
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
            for (int i = 0; i < scratchGetWinningResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == scratchGetWinningResList.get(i).getMemberId().intValue()) {
                        scratchGetWinningResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(scratchGetWinningResList.get(i).getMemberName())) {
                    scratchGetWinningResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取刮刮乐活动中奖记录列表成功",scratchGetWinningResList,pageDTO);
    }

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param scratchEditApplyReq
     * @return
     */
    @Override
    public ResponseDTO editScratchApply(BusUser busUser, ScratchEditApplyReq scratchEditApplyReq) {

        ScratchWinning scratchWinning = scratchWinningService.selectById(scratchEditApplyReq.getId());
        if(CommonUtil.isNotEmpty(scratchWinning)){
            ScratchMain scratchMain = scratchMainService.selectById(scratchWinning.getWinActId());
            ScratchDetail scratchDetail = scratchDetailService.selectById(scratchWinning.getWinPrizeId());
            if(scratchDetail.getScrPrizeType()!= 4){    //非兑奖
                if (DateTimeKit.laterThanNow(scratchMain.getScrBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new ScratchException(ResponseEnums.SCRATCH_HAS1);
                } else if (!DateTimeKit.laterThanNow(scratchMain.getScrEndTime())) {
                    //""已过兑奖时间！";
                    throw  new ScratchException(ResponseEnums.SCRATCH_HAS2);
                }
            }
            if (scratchWinning.getWinStatus() == 3) {
                // 更改记录状态
                scratchWinning.setWinStatus(2);
                scratchWinning.setWinCreateTime(new Date());
                scratchWinningService.updateById(scratchWinning);
            } else if (scratchWinning.getWinStatus() == 2){//已兑奖
                throw  new ScratchException(ResponseEnums.SCRATCH_HAS3);
            }else{//还未提交
                throw  new ScratchException(ResponseEnums.SCRATCH_HAS4);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }

    /**
     * 导出刮刮乐中奖记录
     * @param params
     * @param busUser
     * @return
     */
    @Override
    public Map<String, Object> exportScratch(Map<String, Object> params, BusUser busUser) {


        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            ScratchMain scratchMain = scratchMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = scratchWinningDAO.findExports(params);
            String title = "活动名称：" + scratchMain.getScrName() + "，开始时间：" + sdf.format(scratchMain.getScrBeginTime()) + "结束时间："
                    + sdf.format(scratchMain.getScrEndTime());
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("win_memberId"));
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
            msg.put("fileName", scratchMain.getScrName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "刮刮乐活动中奖记录导出excel失败！");
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
        createCell(wb, row1, 4, "中奖人联系方式", font1);
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
            createCell(wb, rowData, 1, delWithColumn(map.get("egg_prize_name")), font1);
            String turPriType = delWithColumn(map.get("egg_prize_type"));
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
            createCell(wb, rowData, 2, priTypeName + "/" + delWithColumn(map.get("egg_prize_limit")) + priTypeUnit, font1);

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
     * 批量删除刮刮乐活动中奖记录
     * @param busUser
     * @param scratchDelWinningReq
     */
    @Override
    public void delScratchWinning(BusUser busUser, ScratchDelWinningReq scratchDelWinningReq) {

        scratchWinningService.deleteBatchIds(scratchDelWinningReq.getId());
    }

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<List<ScratchPrizeTypeListRes>> getScratchPrizeType(BusUser busUser) {

        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ScratchPrizeTypeListRes> scratchPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                ScratchPrizeTypeListRes scratchPrizeTypeListRes = new ScratchPrizeTypeListRes();
                scratchPrizeTypeListRes.setName(dictApiRes.getItemValue());
                scratchPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                scratchPrizeTypeListResList.add(scratchPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",scratchPrizeTypeListResList);
    }




}
