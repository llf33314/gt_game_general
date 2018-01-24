package com.gt.game.core.service.raiseflag.impl;

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
import com.gt.game.core.bean.raiseflag.req.*;
import com.gt.game.core.bean.raiseflag.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.raiseflag.RaiseflagCashPrizeApplyDAO;
import com.gt.game.core.dao.raiseflag.RaiseflagMainDAO;
import com.gt.game.core.entity.raiseflag.*;
import com.gt.game.core.exception.raiseflag.RaiseflagException;
import com.gt.game.core.service.raiseflag.*;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import com.gt.game.core.util.RandomKit;
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
 * 升国旗 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
@Service
public class RaiseflagServiceImpl implements RaiseflagService {

    @Autowired
    RaiseflagAddressService raiseflagAddressService;

    @Autowired
    RaiseflagAuthorityService raiseflagAuthorityService;

    @Autowired
    RaiseflagCashPrizeApplyService raiseflagCashPrizeApplyService;

    @Autowired
    RaiseflagJoinPrizeService raiseflagJoinPrizeService;

    @Autowired
    RaiseflagMainService raiseflagMainService;

    @Autowired
    RaiseflagPlayRecordService raiseflagPlayRecordService;

    @Autowired
    RaiseflagPrizeImgService raiseflagPrizeImgService;

    @Autowired
    RaiseflagPrizeService raiseflagPrizeService;

    @Autowired
    RaiseflagSponsorService raiseflagSponsorService;

    @Autowired
    RaiseflagMainDAO raiseflagMainDAO;

    @Autowired
    RaiseflagCashPrizeApplyDAO raiseflagCashPrizeApplyDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "raiseflagMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
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
        String url = applyProperties.getMobileBaseUrl() + "raiseflagMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
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
    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<RaiseflagCountRes> getRaiseflagCount(BusUser busUser) {
        RaiseflagCountRes RaiseflagCountRes = new RaiseflagCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = raiseflagMainDAO.getCount(params);
        RaiseflagCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        RaiseflagCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        RaiseflagCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        RaiseflagCountRes.setCount1(RaiseflagCountRes.getCount2()+RaiseflagCountRes.getCount3()+RaiseflagCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",RaiseflagCountRes);
    }
    /**
     * 分页获取活动列表
     *
     * @param raiseflagListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<RaiseflagListRes>> getRaiseflagList(BusUser busUser, RaiseflagListPageReq raiseflagListPageReq) {
        Page<RaiseflagListRes> page = new Page<>(raiseflagListPageReq.getCurrent(),raiseflagListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",raiseflagListPageReq.getStatus());
        map.put("name",raiseflagListPageReq.getName());
        map.put("busId",busUser.getId());
        List<RaiseflagListRes> raiseflagList = raiseflagMainDAO.getRaiseflagList(page,map);
        for (RaiseflagListRes RaiseflagListRes : raiseflagList){
            if(RaiseflagListRes.getActivityBeginTime().getTime() > date.getTime()){
                RaiseflagListRes.setIsEdit(1);
                RaiseflagListRes.setStatus(0);
            }else if(RaiseflagListRes.getActivityEndTime().getTime() < date.getTime()){
                RaiseflagListRes.setIsEdit(0);
                RaiseflagListRes.setStatus(2);
            }else {
                RaiseflagListRes.setIsEdit(0);
                RaiseflagListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",raiseflagList,pageDTO);
    }
    /**
     * 分页获取中奖记录列表
     * @param raiseflagApplyListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<RaiseflagApplyListRes>> getRaiseflagApplyList(BusUser busUser, RaiseflagApplyListPageReq raiseflagApplyListPageReq) {
        Page<RaiseflagApplyListRes> page = new Page<>(raiseflagApplyListPageReq.getCurrent(),raiseflagApplyListPageReq.getSize());
        List<RaiseflagApplyListRes> RaiseflagApplyListResList = raiseflagCashPrizeApplyDAO.queryRecodList(page,raiseflagApplyListPageReq);
        if(RaiseflagApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < RaiseflagApplyListResList.size(); i++) {
                ids.append(RaiseflagApplyListResList.get(i).getMemberId());
                if (RaiseflagApplyListResList.size() > 1 && i < RaiseflagApplyListResList.size() - 1) {
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
            for (int i = 0; i < RaiseflagApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == RaiseflagApplyListResList.get(i).getMemberId().intValue()) {
                        RaiseflagApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(RaiseflagApplyListResList.get(i).getNickname())) {
                    RaiseflagApplyListResList.get(i).setNickname("未知用户");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",RaiseflagApplyListResList,pageDTO);
    }

    /**
     * 中奖记录发放奖品
     * @param RaiseflagApplyIdReq
     * @return
     */
    @Override
    public ResponseDTO editRaiseflagApply(BusUser busUser, RaiseflagApplyIdReq RaiseflagApplyIdReq) {
        RaiseflagCashPrizeApply RaiseflagCashPrizeApply = raiseflagCashPrizeApplyService.selectById(RaiseflagApplyIdReq.getId());
        if(CommonUtil.isNotEmpty(RaiseflagCashPrizeApply)){
            RaiseflagMain main = raiseflagMainService.selectById(RaiseflagCashPrizeApply.getActId());
            RaiseflagPrize RaiseflagPrize = raiseflagPrizeService.selectById(RaiseflagCashPrizeApply.getPrizeId());
            if(RaiseflagPrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(main.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new RaiseflagException(ResponseEnums.RAISEFLAG_HAS3);
                } else if (!DateTimeKit.laterThanNow(main.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new RaiseflagException(ResponseEnums.RAISEFLAG_HAS4);
                }
            }
            if (RaiseflagCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                RaiseflagCashPrizeApply.setStatus(2);
                RaiseflagCashPrizeApply.setCashTime(new Date());
                raiseflagCashPrizeApplyService.updateById(RaiseflagCashPrizeApply);
            } else if (RaiseflagCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new RaiseflagException(ResponseEnums.RAISEFLAG_HAS1);
            }else{//还未提交
                throw  new RaiseflagException(ResponseEnums.RAISEFLAG_HAS2);
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
    public ResponseDTO<RaiseflagRes> getRaiseflag(BusUser busUser, Integer id) {
        RaiseflagMain RaiseflagMain = raiseflagMainService.selectById(id);
        RaiseflagRes RaiseflagRes = new RaiseflagRes();
        if (CommonUtil.isNotEmpty(RaiseflagMain)) {
            BeanUtils.copyProperties(RaiseflagMain, RaiseflagRes);
            //奖品
            List<RaiseflagPrize> RaiseflagPrizes = raiseflagPrizeService.selectList(new EntityWrapper<RaiseflagPrize>().eq("act_id", id));
            List<RaiseflagPrizeReq> RaiseflagPrizeReqs = new ArrayList<>();
            if (RaiseflagPrizes.size() > 0) {
                for (RaiseflagPrize RaiseflagPrize : RaiseflagPrizes) {
                    RaiseflagPrizeReq RaiseflagPrizeReq = new RaiseflagPrizeReq();
                    BeanUtils.copyProperties(RaiseflagPrize, RaiseflagPrizeReq);
                    List<RaiseflagPrizeImg> RaiseflagPrizeImgs = raiseflagPrizeImgService.selectList(new EntityWrapper<RaiseflagPrizeImg>().eq("prize_id", RaiseflagPrize.getId()));
                    List<RaiseflagPrizeImgReq> RaiseflagPrizeImgReqs = new ArrayList<>();
                    for (RaiseflagPrizeImg RaiseflagPrizeImg : RaiseflagPrizeImgs) {
                        RaiseflagPrizeImgReq RaiseflagPrizeImgReq = new RaiseflagPrizeImgReq();
                        BeanUtils.copyProperties(RaiseflagPrizeImg, RaiseflagPrizeImgReq);
                        RaiseflagPrizeImgReqs.add(RaiseflagPrizeImgReq);
                    }
                    RaiseflagPrizeReq.setRaiseflagPrizeImgReqs(RaiseflagPrizeImgReqs);
                    RaiseflagPrizeReqs.add(RaiseflagPrizeReq);
                }
            }
            RaiseflagRes.setRaiseflagPrizeReqs(RaiseflagPrizeReqs);
            //兑奖地址
            List<RaiseflagAddress> RaiseflagAddresses = raiseflagAddressService.selectList(new EntityWrapper<RaiseflagAddress>().eq("act_id", id));
            List<RaiseflagAddressReq> RaiseflagAddressReqs = new ArrayList<>();
            for (RaiseflagAddress RaiseflagAddress : RaiseflagAddresses) {
                RaiseflagAddressReq RaiseflagAddressReq = new RaiseflagAddressReq();
                BeanUtils.copyProperties(RaiseflagAddress, RaiseflagAddressReq);
                RaiseflagAddressReqs.add(RaiseflagAddressReq);
            }
            RaiseflagRes.setRaiseflagAddressReqs(RaiseflagAddressReqs);
            //赞助商
            List<RaiseflagSponsorReq> raiseflagSponsorReqs = new ArrayList<>();
            List<RaiseflagSponsor> list = raiseflagSponsorService.selectList(new EntityWrapper<RaiseflagSponsor>().eq("act_id", id));
            for(RaiseflagSponsor raiseflagSponsor : list){
                RaiseflagSponsorReq raiseflagSponsorReq = new RaiseflagSponsorReq();
                BeanUtils.copyProperties(raiseflagSponsor,raiseflagSponsorReq);
                raiseflagSponsorReqs.add(raiseflagSponsorReq);
            }
            RaiseflagRes.setRaiseflagSponsorReqs(raiseflagSponsorReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",RaiseflagRes);
    }
    /**
     * 删除活动
     * @param RaiseflagIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeRaiseflag(BusUser busUser, RaiseflagIdReq RaiseflagIdReq) {
        RaiseflagMain RaiseflagMain = raiseflagMainService.selectById(RaiseflagIdReq.getId());
        if(CommonUtil.isNotEmpty(RaiseflagMain)){
            if(RaiseflagMain.getActivityBeginTime().getTime() < new Date().getTime() && RaiseflagMain.getActivityEndTime().getTime() > new Date().getTime()){
                throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS10);
            }
            if(RaiseflagMain.getCashPrizeBeginTime().getTime() < new Date().getTime() && RaiseflagMain.getCashPrizeEndTime().getTime() > new Date().getTime()){
                throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS12);
            }
            List<RaiseflagCashPrizeApply> RaiseflagCashPrizeApplies = raiseflagCashPrizeApplyService.selectList(
                    new EntityWrapper<RaiseflagCashPrizeApply>().eq("act_id",RaiseflagIdReq.getId()).eq("status",3));
            if(RaiseflagCashPrizeApplies.size() > 0 ){
                throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS11);

            }
            if(RaiseflagMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new RaiseflagException(ResponseEnums.DIFF_USER);
            }
            raiseflagMainService.deleteById(RaiseflagMain.getId());
            //兑奖地址
            raiseflagAddressService.delete(new EntityWrapper<RaiseflagAddress>().eq("act_id",RaiseflagMain.getId()));
            //奖品
            List<RaiseflagPrize> RaiseflagPrizes = raiseflagPrizeService.selectList(new EntityWrapper<RaiseflagPrize>().eq("act_id",RaiseflagMain.getId()));
            boolean ff = false;
            if(RaiseflagPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(RaiseflagPrize RaiseflagPrize : RaiseflagPrizes){
                    ids.add(RaiseflagPrize.getId());
                    if(RaiseflagPrize.getType() == 1){
                        ff = true;
                    }
                }
                raiseflagPrizeImgService.delete(new EntityWrapper<RaiseflagPrizeImg>().in("prize_id",ids));
            }
            raiseflagSponsorService.delete(new EntityWrapper<RaiseflagSponsor>().eq("act_id",RaiseflagMain.getId()));
            raiseflagJoinPrizeService.delete(new EntityWrapper<RaiseflagJoinPrize>().eq("act_id",RaiseflagMain.getId()));
            raiseflagPrizeService.delete(new EntityWrapper<RaiseflagPrize>().eq("act_id",RaiseflagMain.getId()));
            raiseflagAuthorityService.delete(new EntityWrapper<RaiseflagAuthority>().eq("main_id",RaiseflagMain.getId()));
            raiseflagCashPrizeApplyService.delete(new EntityWrapper<RaiseflagCashPrizeApply>().eq("act_id",RaiseflagMain.getId()));
            raiseflagPlayRecordService.delete(new EntityWrapper<RaiseflagPlayRecord>().eq("act_id",RaiseflagMain.getId()));
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(RaiseflagMain.getId());
                fenbiSurplus.setFre_type(31);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(RaiseflagMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(31);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");
    }

    /**
     * 保存活动
     * @param RaiseflagSaveReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveRaiseflag(BusUser busUser, RaiseflagSaveReq RaiseflagSaveReq) {
        RaiseflagMain RaiseflagMain = null;
        Double num = 0.0;
        int f = 0;
        if(RaiseflagSaveReq.getId() == 0){//新增
            RaiseflagMain = new RaiseflagMain();
            BeanUtils.copyProperties(RaiseflagSaveReq,RaiseflagMain);
            RaiseflagMain.setBusId(busUser.getId());
            RaiseflagMain.setCreatetime(new Date());
            RaiseflagMain.setRfStatus(1);
            RaiseflagMain.setAuthoritySign(RandomKit.getRandomString(6, 2));
            RaiseflagMain.setFollowQrCode(RaiseflagMain.getFollowQrCode().split("/upload").length > 1
                    ?RaiseflagMain.getFollowQrCode().split("/upload")[1]:RaiseflagMain.getFollowQrCode());
            raiseflagMainService.insert(RaiseflagMain);
        }else{//编辑
            RaiseflagMain = raiseflagMainService.selectById(RaiseflagSaveReq.getId());
            if(CommonUtil.isEmpty(RaiseflagMain)){
                throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS5);
            }
            if(RaiseflagMain.getActivityBeginTime().getTime() < new Date().getTime()){
                throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS6);
            }
            if(RaiseflagMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new RaiseflagException(ResponseEnums.DIFF_USER);
            }
            BeanUtils.copyProperties(RaiseflagSaveReq,RaiseflagMain);
            RaiseflagMain.setFollowQrCode(RaiseflagMain.getFollowQrCode().split("/upload").length > 1
                    ?RaiseflagMain.getFollowQrCode().split("/upload")[1]:RaiseflagMain.getFollowQrCode());
            raiseflagMainService.updateById(RaiseflagMain);
            //删除
            //兑奖地址
            raiseflagAddressService.delete(new EntityWrapper<RaiseflagAddress>().eq("act_id",RaiseflagMain.getId()));
            //赞助商
            raiseflagSponsorService.delete(new EntityWrapper<RaiseflagSponsor>().eq("act_id",RaiseflagMain.getId()));
            //奖品
            List<RaiseflagPrize> RaiseflagPrizes = raiseflagPrizeService.selectList(new EntityWrapper<RaiseflagPrize>().eq("act_id",RaiseflagMain.getId()));
            if(RaiseflagPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(RaiseflagPrize RaiseflagPrize : RaiseflagPrizes){
                    if(RaiseflagPrize.getType() == 1){
                        num += RaiseflagPrize.getNum();
                        f = 1;
                    }
                    ids.add(RaiseflagPrize.getId());
                }
                raiseflagPrizeImgService.delete(new EntityWrapper<RaiseflagPrizeImg>().in("prize_id",ids));
            }
            raiseflagPrizeService.delete(new EntityWrapper<RaiseflagPrize>().eq("act_id",RaiseflagMain.getId()));
        }
        Double fenbi = 0.0;
        //奖品
        if(CommonUtil.isNotEmpty(RaiseflagSaveReq.getRaiseflagPrizeReqs())){
            for(RaiseflagPrizeReq RaiseflagPrizeReq :RaiseflagSaveReq.getRaiseflagPrizeReqs()){
                if(RaiseflagPrizeReq.getType()==1){
                    fenbi += RaiseflagPrizeReq.getNum();
                }
                RaiseflagPrize RaiseflagPrize = new RaiseflagPrize();
                BeanUtils.copyProperties(RaiseflagPrizeReq,RaiseflagPrize);
                RaiseflagPrize.setActId(RaiseflagMain.getId());
                raiseflagPrizeService.insert(RaiseflagPrize);
                if(RaiseflagPrizeReq.getRaiseflagPrizeImgReqs().size() > 0){
                    for(RaiseflagPrizeImgReq RaiseflagPrizeImgReq : RaiseflagPrizeReq.getRaiseflagPrizeImgReqs()){
                        RaiseflagPrizeImg RaiseflagPrizeImg = new RaiseflagPrizeImg();
                        BeanUtils.copyProperties(RaiseflagPrizeImgReq,RaiseflagPrizeImg);
                        RaiseflagPrizeImg.setPrizeId(RaiseflagPrize.getId());
                        RaiseflagPrizeImg.setImgUrl(RaiseflagPrizeImg.getImgUrl().split("/upload").length>1?
                                RaiseflagPrizeImg.getImgUrl().split("/upload")[1]:RaiseflagPrizeImg.getImgUrl());
                        raiseflagPrizeImgService.insert(RaiseflagPrizeImg);
                    }
                }
            }
        }
        Date date = new Date();
        //赞助商
        if(CommonUtil.isNotEmpty(RaiseflagSaveReq.getRaiseflagSponsorReqs())){
            for(RaiseflagSponsorReq raiseflagSponsorReq :RaiseflagSaveReq.getRaiseflagSponsorReqs()){
                RaiseflagSponsor raiseflagSponsor = new RaiseflagSponsor();
                BeanUtils.copyProperties(raiseflagSponsorReq,raiseflagSponsor);
                raiseflagSponsor.setSponsorImgUrl(raiseflagSponsor.getSponsorImgUrl().split("/upload").length > 1 ? raiseflagSponsor.getSponsorImgUrl().split("/upload")[1]:"");
                raiseflagSponsor.setActId(RaiseflagMain.getId());
                raiseflagSponsor.setCreatetime(date);
                raiseflagSponsorService.insert(raiseflagSponsor);
            }
        }
        //兑奖地址
        if(CommonUtil.isNotEmpty(RaiseflagSaveReq.getRaiseflagAddressReqs())){
            for(RaiseflagAddressReq RaiseflagAddressReq :RaiseflagSaveReq.getRaiseflagAddressReqs()){
                RaiseflagAddress RaiseflagAddress = new RaiseflagAddress();
                BeanUtils.copyProperties(RaiseflagAddressReq,RaiseflagAddress);
                RaiseflagAddress.setActId(RaiseflagMain.getId());
                RaiseflagAddress.setCreatetime(date);
                raiseflagAddressService.insert(RaiseflagAddress);
            }
        }
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS14);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(RaiseflagMain.getId());
                updateFenbiReduceReq.setFreType(31);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS15);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, RaiseflagMain.getId(), 31, 1, "升国旗活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS8);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    /**
     * 分页获取核销授权
     * @param RaiseflagAuthorityListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<RaiseflagAuthorityListRes>> getRaiseflagAuthorityList(BusUser busUser, RaiseflagAuthorityListPageReq RaiseflagAuthorityListPageReq) {
        Page<RaiseflagAuthority> page = new Page<>(RaiseflagAuthorityListPageReq.getCurrent(),RaiseflagAuthorityListPageReq.getSize());
        List<RaiseflagAuthority> RaiseflagAuthorityList = raiseflagAuthorityService.selectPage(page,
                new EntityWrapper<RaiseflagAuthority>().eq("main_id",RaiseflagAuthorityListPageReq.getActId())
                        .eq("del_status",0)).getRecords();
        List<RaiseflagAuthorityListRes> RaiseflagAuthorityListRes = new ArrayList<>();
        if(RaiseflagAuthorityList.size() > 0){
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < RaiseflagAuthorityList.size(); i++) {
                ids.append(RaiseflagAuthorityList.get(i).getMemberId());
                if (RaiseflagAuthorityList.size() > 1 && i < RaiseflagAuthorityList.size() - 1) {
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
            for (RaiseflagAuthority RaiseflagAuthority : RaiseflagAuthorityList){
                RaiseflagAuthorityListRes RaiseflagAuthorityListRes1 = new RaiseflagAuthorityListRes();
                BeanUtils.copyProperties(RaiseflagAuthority,RaiseflagAuthorityListRes1);
                RaiseflagAuthorityListRes.add(RaiseflagAuthorityListRes1);
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == RaiseflagAuthority.getMemberId().intValue()) {
                        RaiseflagAuthorityListRes1.setMemberName(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(RaiseflagAuthorityListRes1.getMemberName())) {
                    RaiseflagAuthorityListRes1.setMemberName("未知用户");
                }
            }
        }

        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",RaiseflagAuthorityListRes,pageDTO);
    }
    /**
     * 获取奖品类型列表
     *
     */
    @Override
    public ResponseDTO<List<RaiseflagPrizeTypeListRes>> getRaiseflagPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<RaiseflagPrizeTypeListRes> raiseflagPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                RaiseflagPrizeTypeListRes RaiseflagPrizeTypeListRes = new RaiseflagPrizeTypeListRes();
                RaiseflagPrizeTypeListRes.setName(dictApiRes.getItemValue());
                RaiseflagPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                raiseflagPrizeTypeListResList.add(RaiseflagPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",raiseflagPrizeTypeListResList);
    }
    /**
     * 删除核销授权
     * @param RaiseflagAuthorityIdsReq
     * @return
     */
    @Override
    public ResponseDTO removeRaiseflagAuthority(BusUser busUser, RaiseflagAuthorityIdsReq RaiseflagAuthorityIdsReq) {
        RaiseflagMain RaiseflagMain = raiseflagMainService.selectById(RaiseflagAuthorityIdsReq.getActId());
        if(CommonUtil.isNotEmpty(RaiseflagMain)) {
            if (RaiseflagMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new RaiseflagException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new RaiseflagException(ResponseEnums.RAISEFLAG_HAS5);
        }
        RaiseflagAuthority RaiseflagAuthority = new RaiseflagAuthority();
        RaiseflagAuthority.setDelStatus(1);
        raiseflagAuthorityService.update(RaiseflagAuthority,new EntityWrapper<RaiseflagAuthority>().in("id",RaiseflagAuthorityIdsReq.getIds()));
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
            RaiseflagMain main = raiseflagMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = raiseflagCashPrizeApplyDAO.findExports(params);
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
            createCell(wb, rowData, 6, CommonUtil.isEmpty(map.get("nickname"))?"游客":map.get("nickname").toString(), font1);
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
