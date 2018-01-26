package com.gt.game.core.service.loveArrow.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
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
import com.gt.game.core.bean.loveArrow.req.*;
import com.gt.game.core.bean.loveArrow.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.loveArrow.LovearrowCashPrizeApplyDAO;
import com.gt.game.core.dao.loveArrow.LovearrowMainDAO;
import com.gt.game.core.entity.loveArrow.*;
import com.gt.game.core.exception.loveArrow.LoveArrowException;
import com.gt.game.core.service.loveArrow.*;
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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 一箭穿心主表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-06
 */
@Service
public class LovearrowServiceImpl implements LovearrowService {

    @Autowired
    LovearrowMainService lovearrowMainService;

    @Autowired
    LovearrowCashPrizeApplyService lovearrowCashPrizeApplyService;

    @Autowired
    LovearrowAdService lovearrowAdService;

    @Autowired
    LovearrowAddressService lovearrowAddressService;

    @Autowired
    LovearrowAuthorityService lovearrowAuthorityService;

    @Autowired
    LovearrowPlayRecordService lovearrowPlayRecordService;

    @Autowired
    LovearrowPrizeService lovearrowPrizeService;

    @Autowired
    LovearrowPrizeImgService lovearrowPrizeImgService;

    @Autowired
    LovearrowReportService lovearrowReportService;

    @Autowired
    LovearrowMainDAO lovearrowMainDAO;

    @Autowired
    LovearrowCashPrizeApplyDAO lovearrowCashPrizeApplyDAO;

    @Autowired
    ApplyProperties applyProperties;

    /**
     * 获取移动端访问链接
     *
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {
        String url = applyProperties.getMobileBaseUrl() + "loveArrowMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
        return new MobileUrlRes(url);
    }

    /**
     * 获取新增授权链接
     *
     * @param mobileUrlReq
     * @return
     */
    @Override
    public ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {
        String url = applyProperties.getMobileBaseUrl() + "loveArrowMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }
    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<LoveArrowCountRes> getLoveArrowCount(BusUser busUser) {
        LoveArrowCountRes loveArrowCountRes = new LoveArrowCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = lovearrowMainDAO.getCount(params);
        loveArrowCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        loveArrowCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        loveArrowCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        loveArrowCountRes.setCount1(loveArrowCountRes.getCount2()+loveArrowCountRes.getCount3()+loveArrowCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",loveArrowCountRes);
    }
    /**
     * 分页获取活动列表
     *
     * @param loveArrowListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<LoveArrowListRes>> getLoveArrowList(BusUser busUser, LoveArrowListPageReq loveArrowListPageReq) {
        Page<LoveArrowListRes> page = new Page<>(loveArrowListPageReq.getCurrent(),loveArrowListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",loveArrowListPageReq.getStatus());
        map.put("name",loveArrowListPageReq.getName());
        map.put("busId",busUser.getId());
        List<LoveArrowListRes> LoveArrowListResList = lovearrowMainDAO.getLoveArrowList(page,map);
        for (LoveArrowListRes LoveArrowListRes : LoveArrowListResList){
            if(LoveArrowListRes.getActivityBeginTime().getTime() > date.getTime()){
                LoveArrowListRes.setIsEdit(1);
                LoveArrowListRes.setStatus(0);
            }else if(LoveArrowListRes.getActivityEndTime().getTime() < date.getTime()){
                LoveArrowListRes.setIsEdit(0);
                LoveArrowListRes.setStatus(2);
            }else {
                LoveArrowListRes.setIsEdit(0);
                LoveArrowListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",LoveArrowListResList,pageDTO);
    }
    /**
     * 分页获取中奖记录列表
     * @param loveArrowApplyListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<LoveArrowApplyListRes>> getLoveArrowApplyList(BusUser busUser, LoveArrowApplyListPageReq loveArrowApplyListPageReq) {
        Page<LoveArrowApplyListRes> page = new Page<>(loveArrowApplyListPageReq.getCurrent(),loveArrowApplyListPageReq.getSize());
        List<LoveArrowApplyListRes> LoveArrowApplyListResList = lovearrowCashPrizeApplyDAO.queryRecodList(page,loveArrowApplyListPageReq);
        if(LoveArrowApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < LoveArrowApplyListResList.size(); i++) {
                ids.append(LoveArrowApplyListResList.get(i).getMemberId());
                if (LoveArrowApplyListResList.size() > 1 && i < LoveArrowApplyListResList.size() - 1) {
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
            for (int i = 0; i < LoveArrowApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == LoveArrowApplyListResList.get(i).getMemberId().intValue()) {
                        LoveArrowApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(LoveArrowApplyListResList.get(i).getNickname())) {
                    LoveArrowApplyListResList.get(i).setNickname("未知用户");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",LoveArrowApplyListResList,pageDTO);

    }
    /**
     * 中奖记录发放奖品
     * @param loveArrowApplyIdReq
     * @return
     */
    @Override
    public ResponseDTO editLoveArrowApply(BusUser busUser, LoveArrowApplyIdReq loveArrowApplyIdReq) {
        LovearrowCashPrizeApply lovearrowCashPrizeApply = lovearrowCashPrizeApplyService.selectById(loveArrowApplyIdReq.getId());
        if(CommonUtil.isNotEmpty(lovearrowCashPrizeApply)){
            LovearrowMain main = lovearrowMainService.selectById(lovearrowCashPrizeApply.getActId());
            LovearrowPrize lovearrowPrize = lovearrowPrizeService.selectById(lovearrowCashPrizeApply.getPrizeId());
            if(lovearrowPrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(main.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new LoveArrowException(ResponseEnums.LOVEARROW_HAS3);
                } else if (!DateTimeKit.laterThanNow(main.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new LoveArrowException(ResponseEnums.LOVEARROW_HAS4);
                }
            }
            if (lovearrowCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                lovearrowCashPrizeApply.setStatus(2);
                lovearrowCashPrizeApply.setCashTime(new Date());
                lovearrowCashPrizeApplyService.updateById(lovearrowCashPrizeApply);
            } else if (lovearrowCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new LoveArrowException(ResponseEnums.LOVEARROW_HAS1);
            }else{//还未提交
                throw  new LoveArrowException(ResponseEnums.LOVEARROW_HAS2);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }
    /**
     * 获取活动
     * @param id
     * @return
     */
    @Override
    public ResponseDTO<LoveArrowRes> getLoveArrow(BusUser busUser, Integer id) {
        LovearrowMain lovearrowMain = lovearrowMainService.selectById(id);
        LoveArrowRes loveArrowRes = new LoveArrowRes();
        if (CommonUtil.isNotEmpty(lovearrowMain)) {
            BeanUtils.copyProperties(lovearrowMain, loveArrowRes);
            //奖品
            List<LovearrowPrize> LovearrowPrizes = lovearrowPrizeService.selectList(new EntityWrapper<LovearrowPrize>().eq("act_id", id));
            List<LoveArrowPrizeReq> loveArrowPrizeReqs = new ArrayList<>();
            if (LovearrowPrizes.size() > 0) {
                for (LovearrowPrize LovearrowPrize : LovearrowPrizes) {
                    LoveArrowPrizeReq loveArrowPrizeReq = new LoveArrowPrizeReq();
                    BeanUtils.copyProperties(LovearrowPrize, loveArrowPrizeReq);
                    loveArrowPrizeReq.setCardReceiveId(CommonUtil.isNotEmpty(LovearrowPrize.getCardReceiveId())?LovearrowPrize.getCardReceiveId().toString():"");
                    List<LovearrowPrizeImg> lovearrowPrizeImgs = lovearrowPrizeImgService.selectList(new EntityWrapper<LovearrowPrizeImg>().eq("prize_id", LovearrowPrize.getId()));
                    List<LoveArrowPrizeImgReq> loveArrowPrizeImgReqs = new ArrayList<>();
                    for (LovearrowPrizeImg lovearrowPrizeImg : lovearrowPrizeImgs) {
                        LoveArrowPrizeImgReq LoveArrowPrizeImgReq = new LoveArrowPrizeImgReq();
                        BeanUtils.copyProperties(lovearrowPrizeImg, LoveArrowPrizeImgReq);
                        loveArrowPrizeImgReqs.add(LoveArrowPrizeImgReq);
                    }
                    loveArrowPrizeReq.setLoveArrowPrizeImgReqs(loveArrowPrizeImgReqs);
                    loveArrowPrizeReqs.add(loveArrowPrizeReq);
                }
            }
            loveArrowRes.setLoveArrowPrizeReqs(loveArrowPrizeReqs);
            //兑奖地址
            List<LovearrowAddress> lovearrowAddresses = lovearrowAddressService.selectList(new EntityWrapper<LovearrowAddress>().eq("act_id", id));
            List<LoveArrowAddressReq> loveArrowAddressReqs = new ArrayList<>();
            if (lovearrowAddresses.size() > 0) {
                for (LovearrowAddress lovearrowAddress : lovearrowAddresses) {
                    LoveArrowAddressReq loveArrowAddressReq = new LoveArrowAddressReq();
                    BeanUtils.copyProperties(lovearrowAddress, loveArrowAddressReq);
                    loveArrowAddressReqs.add(loveArrowAddressReq);
                }
            }
             loveArrowRes.setLoveArrowAddressReqs(loveArrowAddressReqs);
            //广告轮播图
            List<LovearrowAd> lovearrowAds = lovearrowAdService.selectList(new EntityWrapper<LovearrowAd>().eq("act_id", id));
            List<LoveArrowAdReq> loveArrowAdReqs = new ArrayList<>();
            if (lovearrowAds.size() > 0) {
                for (LovearrowAd lovearrowAd : lovearrowAds) {
                    LoveArrowAdReq loveArrowAdReq = new LoveArrowAdReq();
                    BeanUtils.copyProperties(lovearrowAd, loveArrowAdReq);
                    loveArrowAdReqs.add(loveArrowAdReq);
                }
            }
            loveArrowRes.setLoveArrowAdReqs(loveArrowAdReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",loveArrowRes);
    }
    /**
     * 删除活动
     * @param loveArrowIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeLoveArrow(BusUser busUser, LoveArrowIdReq loveArrowIdReq) {
        LovearrowMain LovearrowMain = lovearrowMainService.selectById(loveArrowIdReq.getId());
        if(CommonUtil.isNotEmpty(LovearrowMain)){
            if(LovearrowMain.getActivityBeginTime().getTime() < new Date().getTime() && LovearrowMain.getActivityEndTime().getTime() > new Date().getTime()){
                throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS10);
            }
            if(LovearrowMain.getCashPrizeBeginTime().getTime() < new Date().getTime() && LovearrowMain.getCashPrizeEndTime().getTime() > new Date().getTime()){
                throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS12);
            }
            List<LovearrowCashPrizeApply> lovearrowCashPrizeApplies = lovearrowCashPrizeApplyService.selectList(
                    new EntityWrapper<LovearrowCashPrizeApply>().eq("act_id",loveArrowIdReq.getId()).eq("status",3));
            if(lovearrowCashPrizeApplies.size() > 0 ){
                throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS11);

            }
            if(LovearrowMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new LoveArrowException(ResponseEnums.DIFF_USER);
            }
            lovearrowMainService.deleteById(LovearrowMain.getId());
            //兑奖地址
            lovearrowAddressService.delete(new EntityWrapper<LovearrowAddress>().eq("act_id",LovearrowMain.getId()));
            //广告轮播图
            lovearrowAdService.delete(new EntityWrapper<LovearrowAd>().eq("act_id",LovearrowMain.getId()));
            //奖品
            List<LovearrowPrize> LovearrowPrizes = lovearrowPrizeService.selectList(new EntityWrapper<LovearrowPrize>().eq("act_id",LovearrowMain.getId()));
            boolean ff = false;
            if(LovearrowPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(LovearrowPrize LovearrowPrize : LovearrowPrizes){
                    ids.add(LovearrowPrize.getId());
                    if(LovearrowPrize.getType() == 1){
                        ff = true;
                    }
                }
                lovearrowPrizeImgService.delete(new EntityWrapper<LovearrowPrizeImg>().in("prize_id",ids));
            }
            lovearrowPrizeService.delete(new EntityWrapper<LovearrowPrize>().eq("act_id",LovearrowMain.getId()));
            lovearrowAuthorityService.delete(new EntityWrapper<LovearrowAuthority>().eq("act_id",LovearrowMain.getId()));
            lovearrowCashPrizeApplyService.delete(new EntityWrapper<LovearrowCashPrizeApply>().eq("act_id",LovearrowMain.getId()));
            lovearrowPlayRecordService.delete(new EntityWrapper<LovearrowPlayRecord>().eq("act_id",LovearrowMain.getId()));
            lovearrowReportService.delete(new EntityWrapper<LovearrowReport>().eq("act_id",LovearrowMain.getId()));
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(LovearrowMain.getId());
                fenbiSurplus.setFre_type(38);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(LovearrowMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(38);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 保存活动
     * @param loveArrowSaveReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveLoveArrow(BusUser busUser, LoveArrowSaveReq loveArrowSaveReq) {
        LovearrowMain LovearrowMain = null;
        Double num = 0.0;
        int f = 0;
        if(loveArrowSaveReq.getId() == 0){//新增
            LovearrowMain = new LovearrowMain();
            BeanUtils.copyProperties(loveArrowSaveReq,LovearrowMain);
            LovearrowMain.setBusId(busUser.getId());
            LovearrowMain.setCreatetime(new Date());
            LovearrowMain.setFollowQrCode(LovearrowMain.getFollowQrCode().split("/upload").length > 1
                    ?LovearrowMain.getFollowQrCode().split("/upload")[1]:LovearrowMain.getFollowQrCode());
            lovearrowMainService.insert(LovearrowMain);
        }else{//编辑
            LovearrowMain = lovearrowMainService.selectById(loveArrowSaveReq.getId());
            if(CommonUtil.isEmpty(LovearrowMain)){
                throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS5);
            }
            if(LovearrowMain.getActivityBeginTime().getTime() < new Date().getTime()){
                throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS6);
            }
            if(LovearrowMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new LoveArrowException(ResponseEnums.DIFF_USER);
            }
            BeanUtils.copyProperties(loveArrowSaveReq,LovearrowMain);
            LovearrowMain.setFollowQrCode(LovearrowMain.getFollowQrCode().split("/upload").length > 1
                    ?LovearrowMain.getFollowQrCode().split("/upload")[1]:LovearrowMain.getFollowQrCode());
            lovearrowMainService.updateById(LovearrowMain);
            //删除
            //兑奖地址
            lovearrowAddressService.delete(new EntityWrapper<LovearrowAddress>().eq("act_id",LovearrowMain.getId()));
            //广告轮播图
            lovearrowAdService.delete(new EntityWrapper<LovearrowAd>().eq("act_id",LovearrowMain.getId()));
            //奖品
            List<LovearrowPrize> LovearrowPrizes = lovearrowPrizeService.selectList(new EntityWrapper<LovearrowPrize>().eq("act_id",LovearrowMain.getId()));
            if(LovearrowPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(LovearrowPrize LovearrowPrize : LovearrowPrizes){
                    if(LovearrowPrize.getType() == 1){
                        num += LovearrowPrize.getNum();
                        f = 1;
                    }
                    ids.add(LovearrowPrize.getId());
                }
                lovearrowPrizeImgService.delete(new EntityWrapper<LovearrowPrizeImg>().in("prize_id",ids));
            }
            lovearrowPrizeService.delete(new EntityWrapper<LovearrowPrize>().eq("act_id",LovearrowMain.getId()));
        }
        Double fenbi = 0.0;
        //奖品
        if(CommonUtil.isNotEmpty(loveArrowSaveReq.getLoveArrowPrizeReqs())){
            for(LoveArrowPrizeReq LoveArrowPrizeReq :loveArrowSaveReq.getLoveArrowPrizeReqs()){
                if(LoveArrowPrizeReq.getType()==1){
                    fenbi += LoveArrowPrizeReq.getNum();
                }
                LovearrowPrize LovearrowPrize = new LovearrowPrize();
                BeanUtils.copyProperties(LoveArrowPrizeReq,LovearrowPrize);
                LovearrowPrize.setCardReceiveId(CommonUtil.isNotEmpty(LoveArrowPrizeReq.getCardReceiveId())?CommonUtil.toInteger(LoveArrowPrizeReq.getCardReceiveId()):null);
                LovearrowPrize.setActId(LovearrowMain.getId());
                lovearrowPrizeService.insert(LovearrowPrize);
                if(LoveArrowPrizeReq.getLoveArrowPrizeImgReqs().size() > 0){
                    for(LoveArrowPrizeImgReq LoveArrowPrizeImgReq : LoveArrowPrizeReq.getLoveArrowPrizeImgReqs()){
                        LovearrowPrizeImg LovearrowPrizeImg = new LovearrowPrizeImg();
                        BeanUtils.copyProperties(LoveArrowPrizeImgReq,LovearrowPrizeImg);
                        LovearrowPrizeImg.setPrizeId(LovearrowPrize.getId());
                        LovearrowPrizeImg.setImgUrl(LovearrowPrizeImg.getImgUrl().split("/upload").length>1?
                                LovearrowPrizeImg.getImgUrl().split("/upload")[1]:LovearrowPrizeImg.getImgUrl());
                        lovearrowPrizeImgService.insert(LovearrowPrizeImg);
                    }
                }
            }
        }
        //兑奖地址
        if(CommonUtil.isNotEmpty(loveArrowSaveReq.getLoveArrowAddressReqs())){
            for(LoveArrowAddressReq LoveArrowAddressReq :loveArrowSaveReq.getLoveArrowAddressReqs()){
                LovearrowAddress LovearrowAddress = new LovearrowAddress();
                BeanUtils.copyProperties(LoveArrowAddressReq,LovearrowAddress);
                LovearrowAddress.setActId(LovearrowMain.getId());
                lovearrowAddressService.insert(LovearrowAddress);
            }
        }
        //广告轮播图
        if(CommonUtil.isNotEmpty(loveArrowSaveReq.getLoveArrowAdReqs())){
            for(LoveArrowAdReq LoveArrowAdReq :loveArrowSaveReq.getLoveArrowAdReqs()){
                LovearrowAd LovearrowAd = new LovearrowAd();
                BeanUtils.copyProperties(LoveArrowAdReq,LovearrowAd);
                LovearrowAd.setActId(LovearrowMain.getId());
                LovearrowAd.setUrl(LovearrowAd.getUrl().split("/upload").length>1?
                        LovearrowAd.getUrl().split("/upload")[1]:LovearrowAd.getUrl());
                lovearrowAdService.insert(LovearrowAd);
            }
        }
        if(fenbi == 0.0 && num > 0.0){
            throw new LoveArrowException(ResponseEnums.COMMON_HAS18);
        }
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS14);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(LovearrowMain.getId());
                updateFenbiReduceReq.setFreType(38);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS15);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, LovearrowMain.getId(), 38, 1, "一箭穿心活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS8);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    /**
     * 分页获取核销授权
     * @param loveArrowAuthorityListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<LoveArrowAuthorityListRes>> getLoveArrowAuthorityList(BusUser busUser, LoveArrowAuthorityListPageReq loveArrowAuthorityListPageReq) {
        Page<LovearrowAuthority> page = new Page<>(loveArrowAuthorityListPageReq.getCurrent(),loveArrowAuthorityListPageReq.getSize());
        List<LovearrowAuthority> LovearrowAuthorityList = lovearrowAuthorityService.selectPage(page,
                new EntityWrapper<LovearrowAuthority>().eq("act_id",loveArrowAuthorityListPageReq.getActId())
                        .eq("delete_status",0)).getRecords();
        List<LoveArrowAuthorityListRes> LoveArrowAuthorityListRes = new ArrayList<>();
        for (LovearrowAuthority LovearrowAuthority : LovearrowAuthorityList){
            LoveArrowAuthorityListRes LoveArrowAuthorityListRes1 = new LoveArrowAuthorityListRes();
            BeanUtils.copyProperties(LovearrowAuthority,LoveArrowAuthorityListRes1);
            LoveArrowAuthorityListRes.add(LoveArrowAuthorityListRes1);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",LoveArrowAuthorityListRes,pageDTO);
    }
    /**
     * 获取奖品类型列表
     *
     */
    @Override
    public ResponseDTO<List<LoveArrowPrizeTypeListRes>> getLoveArrowPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<LoveArrowPrizeTypeListRes> loveArrowPrizeTypeListRes = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                LoveArrowPrizeTypeListRes LoveArrowPrizeTypeListRes = new LoveArrowPrizeTypeListRes();
                LoveArrowPrizeTypeListRes.setName(dictApiRes.getItemValue());
                LoveArrowPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                loveArrowPrizeTypeListRes.add(LoveArrowPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",loveArrowPrizeTypeListRes);
    }
    /**
     * 删除核销授权
     * @param loveArrowAuthorityIdsReq
     * @return
     */
    @Override
    public ResponseDTO removeLoveArrowAuthority(BusUser busUser, LoveArrowAuthorityIdsReq loveArrowAuthorityIdsReq) {
        LovearrowMain LovearrowMain = lovearrowMainService.selectById(loveArrowAuthorityIdsReq.getActId());
        if(CommonUtil.isNotEmpty(LovearrowMain)) {
            if (LovearrowMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new LoveArrowException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new LoveArrowException(ResponseEnums.LOVEARROW_HAS5);
        }
        LovearrowAuthority lovearrowAuthority = new LovearrowAuthority();
        lovearrowAuthority.setDeleteStatus(1);
        lovearrowAuthorityService.update(lovearrowAuthority,new EntityWrapper<LovearrowAuthority>().in("id",loveArrowAuthorityIdsReq.getIds()));
        return ResponseDTO.createBySuccess("删除成功");
    }

    @Override
    public Map<String, Object> exports(Map<String, Object> params) {
        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            LovearrowMain main = lovearrowMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = lovearrowCashPrizeApplyDAO.findExports(params);
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("member_id"));
                    if (list.size() > 1 && i < list.size() - 1) {
                        ids.append(",");
                    }
                }
                MemberReq memberReq = new MemberReq();
                memberReq.setBusId(main.getBusId());
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
                        if (memberResList.get(j).getId().intValue() == CommonUtil.toInteger(list.get(i).get("member_id")).intValue()) {
                            list.get(i).put("nickname",memberResList.get(j).getNickname());
                        }
                    }
                    if (CommonUtil.isEmpty(list.get(i).get("nickname"))) {
                        list.get(i).put("nickname","未知用户");
                    }
                }
            }
            String title = "活动名称：" + main.getName() + "，开始时间：" + sdf.format(main.getActivityBeginTime()) + "结束时间："
                    + sdf.format(main.getActivityEndTime());
            HSSFWorkbook book = exportExcelForRecoding(list, title);
            msg.put("book", book);
            msg.put("fileName", main.getName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "一箭穿心活动记录导出excel失败！");
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
        createCell(wb, row1, 3, "成绩", font1);
        createCell(wb, row1, 4, "兑奖时间", font1);
        createCell(wb, row1, 5, "中奖人联系方式", font1);
        createCell(wb, row1, 6, "中奖人昵称", font1);
        createCell(wb, row1, 7, "状态", font1);
        createCell(wb, row1, 8, "兑奖码", font1);
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
            createCell(wb, rowData, 1, delWithColumn(map.get("prize_name")), font1);
            String turPriType = delWithColumn(map.get("type"));
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
                if ("4".equals(turPriType)) {// 积分
                    priTypeName = "积分";
                    priTypeUnit = "分";
                }
                if ("7".equals(turPriType)) {// 优惠券包
                    priTypeName = "优惠券包";
                    priTypeUnit = "个";
                }

            }
            createCell(wb, rowData, 2, priTypeName + "/" + delWithColumn(map.get("prize_unit")) + priTypeUnit, font1);

            createCell(wb, rowData, 3, delWithColumn(map.get("score")), font1);
            createCell(wb, rowData, 4,
                    delWithColumn(CommonUtil.isEmpty(map.get("cashTime")) ? ""
                            : DateTimeKit.format(
                            DateTimeKit.parseDate(map.get("cashTime").toString(), "yyyy/MM/dd HH:mm:ss"),
                            "yyyy-MM-dd HH:mm")),
                    font1);
            createCell(wb, rowData, 5, delWithColumn(map.get("member_phone")), font1);
            createCell(wb, rowData, 6,
                    CommonUtil.isEmpty(map.get("nickname")) ? "未知用户" :map.get("nickname").toString(),
                    font1);
            if ("1".equals(delWithColumn(map.get("status")).toString())) {
                createCell(wb, rowData, 7, "未兑奖", font1);
            } else if ("2".equals(delWithColumn(map.get("status")).toString())) {
                createCell(wb, rowData, 7, "已兑奖", font1);
            } else {
                createCell(wb, rowData, 7, "已提交", font1);
            }
            createCell(wb, rowData, 8, delWithColumn(map.get("sn_code")), font1);

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
}
