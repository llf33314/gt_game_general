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
import com.gt.game.core.bean.eggs.res.EggsListRes;
import com.gt.game.core.bean.qixi.req.*;
import com.gt.game.core.bean.qixi.res.*;
import com.gt.game.core.bean.scratch.req.*;
import com.gt.game.core.bean.scratch.res.ScratchCountActivityRes;
import com.gt.game.core.bean.scratch.res.ScratchGetActivityRes;
import com.gt.game.core.bean.scratch.res.ScratchListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.qixi.QixiCashPrizeApplyDAO;
import com.gt.game.core.dao.qixi.QixiMainDAO;
import com.gt.game.core.entity.eggs.EggsDetail;
import com.gt.game.core.entity.eggs.EggsMain;
import com.gt.game.core.entity.qixi.*;
import com.gt.game.core.entity.scratch.ScratchDetail;
import com.gt.game.core.entity.scratch.ScratchMain;
import com.gt.game.core.exception.qixi.QixiException;
import com.gt.game.core.service.qixi.*;
import com.gt.game.core.service.scratch.ScratchDetailService;
import com.gt.game.core.service.scratch.ScratchMainService;
import com.gt.game.core.service.scratch.ScratchService;
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

    /**
     * 获取手机端链接
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {
        return null;
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
                entityWrapper.where("egg_beginTime > {0}", new Date());
            }
            if (scratchListReq.getStatus() == 1) {
                entityWrapper.where("egg_beginTime <= {0}", new Date());
                entityWrapper.and();
                entityWrapper.where("egg_endTime > {0}", new Date());
            }
            if (scratchListReq.getStatus() == 2) {
                entityWrapper.where("egg_endTime <= {0}", new Date());
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
    public void addScratch(WxPublicUsers loginPbUser, ScratchAddReq scratchAddReq) {

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
        for(ScratchPrizeSetReq scratchPrizeSetReq:scratchAddReq.getPrizeSetList()){

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
}
