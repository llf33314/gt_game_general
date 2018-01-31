package com.gt.game.core.service.goldtree.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gt.api.bean.session.BusUser;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.axis.bean.wxmp.applet.MsgTemplateRes;
import com.gt.axis.bean.wxmp.dict.DictApiReq;
import com.gt.axis.bean.wxmp.dict.DictApiRes;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiFlowRecord;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiFlowRecordReq;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiSurplus;
import com.gt.axis.bean.wxmp.fenbiflow.UpdateFenbiReduceReq;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.member.MemberServer;
import com.gt.axis.server.wxmp.AppletServer;
import com.gt.axis.server.wxmp.DictServer;
import com.gt.axis.server.wxmp.FenbiflowServer;
import com.gt.game.common.config.ApplyProperties;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.goldtree.req.*;
import com.gt.game.core.bean.goldtree.res.*;
import com.gt.game.core.bean.raiseflag.res.RaiseflagTemplateRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.goldtree.GoldtreeMainDAO;
import com.gt.game.core.dao.goldtree.GoldtreePrizeApplyDAO;
import com.gt.game.core.entity.goldtree.*;
import com.gt.game.core.exception.goldtree.GoldtreeException;
import com.gt.game.core.service.goldtree.*;
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
 * 摇钱树设置表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Service
public class GoldtreeServiceImpl implements GoldtreeService {

    @Autowired
    GoldtreeAdService goldtreeAdService;

    @Autowired
    GoldtreeAddressService goldtreeAddressService;

    @Autowired
    GoldtreeAuthorityService goldtreeAuthorityService;

    @Autowired
    GoldtreeMainService goldtreeMainService;

    @Autowired
    GoldtreePlayRecordService goldtreePlayRecordService;

    @Autowired
    GoldtreePrizeApplyService goldtreePrizeApplyService;

    @Autowired
    GoldtreePrizeService goldtreePrizeService;

    @Autowired
    GoldtreePrizeImgService goldtreePrizeImgService;

    @Autowired
    GoldtreeMainDAO goldtreeMainDAO;

    @Autowired
    GoldtreePrizeApplyDAO goldtreePrizeApplyDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "goldtreeMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
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
        String url = applyProperties.getMobileBaseUrl() + "goldtreeMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }
    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<GoldtreeCountRes> getGoldtreeCount(BusUser busUser) {
        GoldtreeCountRes GoldtreeCountRes = new GoldtreeCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = goldtreeMainDAO.getCount(params);
        GoldtreeCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        GoldtreeCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        GoldtreeCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        GoldtreeCountRes.setCount1(GoldtreeCountRes.getCount2()+GoldtreeCountRes.getCount3()+GoldtreeCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",GoldtreeCountRes);
    }
    /**
     * 分页获取活动列表
     *
     * @param GoldtreeListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<GoldtreeListRes>> getGoldtreeList(BusUser busUser, GoldtreeListPageReq GoldtreeListPageReq) {
        Page<GoldtreeListRes> page = new Page<>(GoldtreeListPageReq.getCurrent(),GoldtreeListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",GoldtreeListPageReq.getStatus());
        map.put("name",GoldtreeListPageReq.getName());
        map.put("busId",busUser.getId());
        List<GoldtreeListRes> GoldtreeListResList = goldtreeMainDAO.getGoldtreeList(page,map);
        for (GoldtreeListRes GoldtreeListRes : GoldtreeListResList){
            if(GoldtreeListRes.getActivityBeginTime().getTime() > date.getTime()){
                GoldtreeListRes.setIsEdit(1);
                GoldtreeListRes.setStatus(0);
            }else if(GoldtreeListRes.getActivityEndTime().getTime() < date.getTime()){
                GoldtreeListRes.setIsEdit(0);
                GoldtreeListRes.setStatus(2);
            }else {
                GoldtreeListRes.setIsEdit(0);
                GoldtreeListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",GoldtreeListResList,pageDTO);
    }
    /**
     * 分页获取中奖记录列表
     * @param GoldtreeApplyListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<GoldtreeApplyListRes>> getGoldtreeApplyList(BusUser busUser, GoldtreeApplyListPageReq GoldtreeApplyListPageReq) {
        Page<GoldtreeApplyListRes> page = new Page<>(GoldtreeApplyListPageReq.getCurrent(),GoldtreeApplyListPageReq.getSize());
        List<GoldtreeApplyListRes> GoldtreeApplyListResList = goldtreePrizeApplyDAO.queryRecodList(page,GoldtreeApplyListPageReq);
        if(GoldtreeApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < GoldtreeApplyListResList.size(); i++) {
                ids.append(GoldtreeApplyListResList.get(i).getMemberId());
                if (GoldtreeApplyListResList.size() > 1 && i < GoldtreeApplyListResList.size() - 1) {
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
            for (int i = 0; i < GoldtreeApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == GoldtreeApplyListResList.get(i).getMemberId().intValue()) {
                        GoldtreeApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(GoldtreeApplyListResList.get(i).getNickname())) {
                    GoldtreeApplyListResList.get(i).setNickname("未知用户");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",GoldtreeApplyListResList,pageDTO);
    }
    /**
     * 中奖记录发放奖品
     * @param GoldtreeApplyIdReq
     * @return
     */
    @Override
    public ResponseDTO editGoldtreeApply(BusUser busUser, GoldtreeApplyIdReq GoldtreeApplyIdReq) {
        GoldtreePrizeApply GoldtreeCashPrizeApply = goldtreePrizeApplyService.selectById(GoldtreeApplyIdReq.getId());
        if(CommonUtil.isNotEmpty(GoldtreeCashPrizeApply)){
            GoldtreeMain main = goldtreeMainService.selectById(GoldtreeCashPrizeApply.getActId());
            GoldtreePrize GoldtreePrize = goldtreePrizeService.selectById(GoldtreeCashPrizeApply.getPrizeId());
            if(GoldtreePrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(main.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new GoldtreeException(ResponseEnums.GOLDTREE_HAS3);
                } else if (!DateTimeKit.laterThanNow(main.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new GoldtreeException(ResponseEnums.GOLDTREE_HAS4);
                }
            }
            if (GoldtreeCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                GoldtreeCashPrizeApply.setStatus(2);
                GoldtreeCashPrizeApply.setCashTime(new Date());
                goldtreePrizeApplyService.updateById(GoldtreeCashPrizeApply);
            } else if (GoldtreeCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new GoldtreeException(ResponseEnums.GOLDTREE_HAS1);
            }else{//还未提交
                throw  new GoldtreeException(ResponseEnums.GOLDTREE_HAS2);
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
    public ResponseDTO<GoldtreeRes> getGoldtree(BusUser busUser, Integer id) {
        GoldtreeMain GoldtreeMain = goldtreeMainService.selectById(id);
        GoldtreeRes GoldtreeRes = new GoldtreeRes();
        if (CommonUtil.isNotEmpty(GoldtreeMain)) {
            BeanUtils.copyProperties(GoldtreeMain, GoldtreeRes);
            GoldtreeRes.setMsgTemplateId(CommonUtil.isNotEmpty(GoldtreeMain.getMsgTemplateId())?GoldtreeMain.getMsgTemplateId().toString():"");
            //奖品
            List<GoldtreePrize> GoldtreePrizes = goldtreePrizeService.selectList(new EntityWrapper<GoldtreePrize>().eq("act_id", id));
            List<GoldtreePrizeReq> GoldtreePrizeReqs = new ArrayList<>();
            if (GoldtreePrizes.size() > 0) {
                for (GoldtreePrize GoldtreePrize : GoldtreePrizes) {
                    GoldtreePrizeReq GoldtreePrizeReq = new GoldtreePrizeReq();
                    BeanUtils.copyProperties(GoldtreePrize, GoldtreePrizeReq);
                    GoldtreePrizeReq.setPrizeUnit(GoldtreePrize.getNum());
                    GoldtreePrizeReq.setNum(GoldtreePrize.getTotal());
                    List<GoldtreePrizeImg> GoldtreePrizeImgs = goldtreePrizeImgService.selectList(new EntityWrapper<GoldtreePrizeImg>().eq("prize_id", GoldtreePrize.getId()));
                    List<GoldtreePrizeImgReq> GoldtreePrizeImgReqs = new ArrayList<>();
                    for (GoldtreePrizeImg GoldtreePrizeImg : GoldtreePrizeImgs) {
                        GoldtreePrizeImgReq GoldtreePrizeImgReq = new GoldtreePrizeImgReq();
                        BeanUtils.copyProperties(GoldtreePrizeImg, GoldtreePrizeImgReq);
                        GoldtreePrizeImgReqs.add(GoldtreePrizeImgReq);
                    }
                    GoldtreePrizeReq.setGoldtreePrizeImgReqs(GoldtreePrizeImgReqs);
                    GoldtreePrizeReqs.add(GoldtreePrizeReq);
                }
            }
            GoldtreeRes.setGoldtreePrizeReqs(GoldtreePrizeReqs);
            //兑奖地址
            List<GoldtreeAddress> GoldtreeAddresses = goldtreeAddressService.selectList(new EntityWrapper<GoldtreeAddress>().eq("act_id", id));
            List<GoldtreeAddressReq> GoldtreeAddressReqs = new ArrayList<>();
            if (GoldtreeAddresses.size() > 0) {
                for (GoldtreeAddress GoldtreeAddress : GoldtreeAddresses) {
                    GoldtreeAddressReq GoldtreeAddressReq = new GoldtreeAddressReq();
                    BeanUtils.copyProperties(GoldtreeAddress, GoldtreeAddressReq);
                    GoldtreeAddressReqs.add(GoldtreeAddressReq);
                }
            }
            GoldtreeRes.setGoldtreeAddressReqs(GoldtreeAddressReqs);
            //广告轮播图
            List<GoldtreeAd> GoldtreeAds = goldtreeAdService.selectList(new EntityWrapper<GoldtreeAd>().eq("act_id", id));
            List<GoldtreeAdReq> GoldtreeAdReqs = new ArrayList<>();
            if (GoldtreeAds.size() > 0) {
                for (GoldtreeAd GoldtreeAd : GoldtreeAds) {
                    GoldtreeAdReq GoldtreeAdReq = new GoldtreeAdReq();
                    BeanUtils.copyProperties(GoldtreeAd, GoldtreeAdReq);
                    GoldtreeAdReqs.add(GoldtreeAdReq);
                }
            }
            GoldtreeRes.setGoldtreeAdReqs(GoldtreeAdReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",GoldtreeRes);
    }
    /**
     * 删除活动
     * @param GoldtreeIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeGoldtree(BusUser busUser, GoldtreeIdReq GoldtreeIdReq) {
        GoldtreeMain GoldtreeMain = goldtreeMainService.selectById(GoldtreeIdReq.getId());
        if(CommonUtil.isNotEmpty(GoldtreeMain)){
            if(GoldtreeMain.getActivityBeginTime().getTime() < new Date().getTime() && GoldtreeMain.getActivityEndTime().getTime() > new Date().getTime()){
                throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS10);
            }
            if(GoldtreeMain.getCashPrizeBeginTime().getTime() < new Date().getTime() && GoldtreeMain.getCashPrizeEndTime().getTime() > new Date().getTime()){
                throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS12);
            }
            List<GoldtreePrizeApply> GoldtreeCashPrizeApplies = goldtreePrizeApplyService.selectList(
                    new EntityWrapper<GoldtreePrizeApply>().eq("act_id",GoldtreeIdReq.getId()).eq("status",3));
            if(GoldtreeCashPrizeApplies.size() > 0 ){
                throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS11);

            }
            if(GoldtreeMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new GoldtreeException(ResponseEnums.DIFF_USER);
            }
            goldtreeMainService.deleteById(GoldtreeMain.getId());
            //兑奖地址
            goldtreeAddressService.delete(new EntityWrapper<GoldtreeAddress>().eq("act_id",GoldtreeMain.getId()));
            //广告轮播图
            goldtreeAdService.delete(new EntityWrapper<GoldtreeAd>().eq("act_id",GoldtreeMain.getId()));
            //奖品
            List<GoldtreePrize> GoldtreePrizes = goldtreePrizeService.selectList(new EntityWrapper<GoldtreePrize>().eq("act_id",GoldtreeMain.getId()));
            boolean ff = false;
            if(GoldtreePrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(GoldtreePrize GoldtreePrize : GoldtreePrizes){
                    ids.add(GoldtreePrize.getId());
                    if(GoldtreePrize.getType() == 1){
                        ff = true;
                    }
                }
                goldtreePrizeImgService.delete(new EntityWrapper<GoldtreePrizeImg>().in("prize_id",ids));
            }
            goldtreePrizeService.delete(new EntityWrapper<GoldtreePrize>().eq("act_id",GoldtreeMain.getId()));
            goldtreeAuthorityService.delete(new EntityWrapper<GoldtreeAuthority>().eq("act_id",GoldtreeMain.getId()));
            goldtreePrizeApplyService.delete(new EntityWrapper<GoldtreePrizeApply>().eq("act_id",GoldtreeMain.getId()));
            goldtreePlayRecordService.delete(new EntityWrapper<GoldtreePlayRecord>().eq("act_id",GoldtreeMain.getId()));
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(GoldtreeMain.getId());
                fenbiSurplus.setFre_type(68);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(GoldtreeMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(68);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 保存活动
     * @param GoldtreeSaveReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveGoldtree(BusUser busUser, GoldtreeSaveReq GoldtreeSaveReq) {
        GoldtreeMain GoldtreeMain = null;
        Double num = 0.0;
        int f = 0;
        if(GoldtreeSaveReq.getCashPrizeBeginTime().getTime() < GoldtreeSaveReq.getActivityBeginTime().getTime()){
            throw new GoldtreeException(ResponseEnums.COMMON_HAS16);
        }
        if(GoldtreeSaveReq.getId() == 0){//新增
            GoldtreeMain = new GoldtreeMain();
            BeanUtils.copyProperties(GoldtreeSaveReq,GoldtreeMain);
            GoldtreeMain.setBusId(busUser.getId());
            GoldtreeMain.setCreatetime(new Date());
            GoldtreeMain.setFollowQrCode(GoldtreeMain.getFollowQrCode().split("/upload").length > 1
                    ?GoldtreeMain.getFollowQrCode().split("/upload")[1]:GoldtreeMain.getFollowQrCode());
            GoldtreeMain.setMsgTemplateId(CommonUtil.isNotEmpty(GoldtreeSaveReq.getMsgTemplateId())?CommonUtil.toInteger(GoldtreeSaveReq.getMsgTemplateId()):null);
            goldtreeMainService.insert(GoldtreeMain);
        }else{//编辑
            GoldtreeMain = goldtreeMainService.selectById(GoldtreeSaveReq.getId());
            if(CommonUtil.isEmpty(GoldtreeMain)){
                throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS5);
            }
            if(GoldtreeMain.getActivityBeginTime().getTime() < new Date().getTime()){
                throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS6);
            }
            if(GoldtreeMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new GoldtreeException(ResponseEnums.DIFF_USER);
            }
            BeanUtils.copyProperties(GoldtreeSaveReq,GoldtreeMain);
            GoldtreeMain.setFollowQrCode(GoldtreeMain.getFollowQrCode().split("/upload").length > 1
                    ?GoldtreeMain.getFollowQrCode().split("/upload")[1]:GoldtreeMain.getFollowQrCode());
            GoldtreeMain.setMsgTemplateId(CommonUtil.isNotEmpty(GoldtreeSaveReq.getMsgTemplateId())?CommonUtil.toInteger(GoldtreeSaveReq.getMsgTemplateId()):null);
            goldtreeMainService.updateById(GoldtreeMain);
            //删除
            //兑奖地址
            goldtreeAddressService.delete(new EntityWrapper<GoldtreeAddress>().eq("act_id",GoldtreeMain.getId()));
            //广告轮播图
            goldtreeAdService.delete(new EntityWrapper<GoldtreeAd>().eq("act_id",GoldtreeMain.getId()));
            //奖品
            List<GoldtreePrize> GoldtreePrizes = goldtreePrizeService.selectList(new EntityWrapper<GoldtreePrize>().eq("act_id",GoldtreeMain.getId()));
            if(GoldtreePrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(GoldtreePrize GoldtreePrize : GoldtreePrizes){
                    if(GoldtreePrize.getType() == 1){
                        num += GoldtreePrize.getNum();
                        f = 1;
                    }
                    ids.add(GoldtreePrize.getId());
                }
                goldtreePrizeImgService.delete(new EntityWrapper<GoldtreePrizeImg>().in("prize_id",ids));
            }
            goldtreePrizeService.delete(new EntityWrapper<GoldtreePrize>().eq("act_id",GoldtreeMain.getId()));
        }
        Double fenbi = 0.0;
        //奖品
        if(CommonUtil.isNotEmpty(GoldtreeSaveReq.getGoldtreePrizeReqs())){
            for(GoldtreePrizeReq GoldtreePrizeReq :GoldtreeSaveReq.getGoldtreePrizeReqs()){
                if(GoldtreePrizeReq.getType()==1){
                    fenbi += GoldtreePrizeReq.getNum();
                }
                GoldtreePrize GoldtreePrize = new GoldtreePrize();
                BeanUtils.copyProperties(GoldtreePrizeReq,GoldtreePrize);
                GoldtreePrize.setActId(GoldtreeMain.getId());
                GoldtreePrize.setTotal(GoldtreePrizeReq.getNum());
                GoldtreePrize.setNum(GoldtreePrizeReq.getPrizeUnit());
                goldtreePrizeService.insert(GoldtreePrize);
                if(GoldtreePrizeReq.getType() == 4 && GoldtreePrizeReq.getGoldtreePrizeImgReqs().size() == 0){
                    throw new GoldtreeException(ResponseEnums.COMMON_HAS21);
                }
                if(GoldtreePrizeReq.getType() == 4 && GoldtreePrizeReq.getGoldtreePrizeImgReqs().size() > 5){
                    throw new GoldtreeException(ResponseEnums.COMMON_HAS22);
                }
                if(CommonUtil.isNotEmpty(GoldtreePrizeReq.getGoldtreePrizeImgReqs()) && GoldtreePrizeReq.getGoldtreePrizeImgReqs().size() > 0){
                    for(GoldtreePrizeImgReq GoldtreePrizeImgReq : GoldtreePrizeReq.getGoldtreePrizeImgReqs()){
                        GoldtreePrizeImg GoldtreePrizeImg = new GoldtreePrizeImg();
                        BeanUtils.copyProperties(GoldtreePrizeImgReq,GoldtreePrizeImg);
                        GoldtreePrizeImg.setPrizeId(GoldtreePrize.getId());
                        GoldtreePrizeImg.setImgUrl(GoldtreePrizeImg.getImgUrl().split("/upload").length>1?
                                GoldtreePrizeImg.getImgUrl().split("/upload")[1]:GoldtreePrizeImg.getImgUrl());
                        goldtreePrizeImgService.insert(GoldtreePrizeImg);
                    }
                }
            }
        }
        //兑奖地址
        if(CommonUtil.isNotEmpty(GoldtreeSaveReq.getGoldtreeAddressReqs())){
            for(GoldtreeAddressReq GoldtreeAddressReq :GoldtreeSaveReq.getGoldtreeAddressReqs()){
                GoldtreeAddress GoldtreeAddress = new GoldtreeAddress();
                BeanUtils.copyProperties(GoldtreeAddressReq,GoldtreeAddress);
                GoldtreeAddress.setActId(GoldtreeMain.getId());
                goldtreeAddressService.insert(GoldtreeAddress);
            }
        }
        //广告轮播图
        if(CommonUtil.isNotEmpty(GoldtreeSaveReq.getGoldtreeAdReqs()) && GoldtreeSaveReq.getGoldtreeAdReqs().size() > 0){
            for(GoldtreeAdReq GoldtreeAdReq :GoldtreeSaveReq.getGoldtreeAdReqs()){
                GoldtreeAd GoldtreeAd = new GoldtreeAd();
                BeanUtils.copyProperties(GoldtreeAdReq,GoldtreeAd);
                GoldtreeAd.setActId(GoldtreeMain.getId());
                GoldtreeAd.setUrl(GoldtreeAd.getUrl().split("/upload").length>1?
                        GoldtreeAd.getUrl().split("/upload")[1]:GoldtreeAd.getUrl());
                goldtreeAdService.insert(GoldtreeAd);
            }
        }
        if(fenbi == 0.0 && num > 0.0){
            throw new GoldtreeException(ResponseEnums.COMMON_HAS18);
        }
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS14);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(GoldtreeMain.getId());
                updateFenbiReduceReq.setFreType(68);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS15);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, GoldtreeMain.getId(), 68, 1, "摇钱树活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS8);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    /**
     * 分页获取核销授权
     * @param GoldtreeAuthorityListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<GoldtreeAuthorityListRes>> getGoldtreeAuthorityList(BusUser busUser, GoldtreeAuthorityListPageReq GoldtreeAuthorityListPageReq) {
        Page<GoldtreeAuthority> page = new Page<>(GoldtreeAuthorityListPageReq.getCurrent(),GoldtreeAuthorityListPageReq.getSize());
        List<GoldtreeAuthority> GoldtreeAuthorityList = goldtreeAuthorityService.selectPage(page,
                new EntityWrapper<GoldtreeAuthority>().eq("act_id",GoldtreeAuthorityListPageReq.getActId())).getRecords();
        List<GoldtreeAuthorityListRes> GoldtreeAuthorityListRes = new ArrayList<>();
        for (GoldtreeAuthority GoldtreeAuthority : GoldtreeAuthorityList){
            GoldtreeAuthorityListRes GoldtreeAuthorityListRes1 = new GoldtreeAuthorityListRes();
            BeanUtils.copyProperties(GoldtreeAuthority,GoldtreeAuthorityListRes1);
            GoldtreeAuthorityListRes1.setCreatetime(GoldtreeAuthority.getCreateTime());
            GoldtreeAuthorityListRes.add(GoldtreeAuthorityListRes1);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",GoldtreeAuthorityListRes,pageDTO);
    }
    /**
     * 获取奖品类型列表
     *
     */
    @Override
    public ResponseDTO<List<GoldtreePrizeTypeListRes>> getGoldtreePrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<GoldtreePrizeTypeListRes> GoldtreePrizeTypeListRes = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                GoldtreePrizeTypeListRes goldtreePrizeTypeListRes = new GoldtreePrizeTypeListRes();
                goldtreePrizeTypeListRes.setName(dictApiRes.getItemValue());
                goldtreePrizeTypeListRes.setValue(dictApiRes.getItemKey());
                GoldtreePrizeTypeListRes.add(goldtreePrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",GoldtreePrizeTypeListRes);
    }
    /**
     * 删除核销授权
     * @param GoldtreeAuthorityIdsReq
     * @return
     */
    @Override
    public ResponseDTO removeGoldtreeAuthority(BusUser busUser, GoldtreeAuthorityIdsReq GoldtreeAuthorityIdsReq) {
        GoldtreeMain GoldtreeMain = goldtreeMainService.selectById(GoldtreeAuthorityIdsReq.getActId());
        if(CommonUtil.isNotEmpty(GoldtreeMain)) {
            if (GoldtreeMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new GoldtreeException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new GoldtreeException(ResponseEnums.GOLDTREE_HAS5);
        }
        goldtreeAuthorityService.delete(new EntityWrapper<GoldtreeAuthority>().in("id",GoldtreeAuthorityIdsReq.getIds()));
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     *
     * 获取公众号消息模板列表
     *
     * */
    @Override
    public ResponseDTO<List<RaiseflagTemplateRes>> getTemplateList(BusUser busUser) {
        List<RaiseflagTemplateRes> list = new ArrayList<>();
        AxisResult<List<MsgTemplateRes>> axisResult = AppletServer.selectTempObjByBusId(busUser.getId());
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(MsgTemplateRes msgTemplateRes : axisResult.getData()){
                RaiseflagTemplateRes raiseflagTemplateRes = new RaiseflagTemplateRes();
                BeanUtils.copyProperties(msgTemplateRes,raiseflagTemplateRes);
                list.add(raiseflagTemplateRes);
            }
        }
        return ResponseDTO.createBySuccess("消息模板列表成功",list);
    }
    @Override
    public Map<String, Object> exports(Map<String, Object> params) {
        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            GoldtreeMain main = goldtreeMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = goldtreePrizeApplyDAO.findExports(params);
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
            msg.put("message", "摇钱树活动记录导出excel失败！");
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
        // 设置没列的宽度
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
            Map<String, Object> map = list.get(i);
            // 创建一行
            Row rowData = sheet.createRow(l++);
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
    private static void createCell(Workbook wb, Row row, int column, String value, Font font) {
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
