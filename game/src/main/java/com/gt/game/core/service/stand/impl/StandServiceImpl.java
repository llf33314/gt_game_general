package com.gt.game.core.service.stand.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.api.bean.session.BusUser;
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
import com.gt.game.core.bean.stand.req.*;
import com.gt.game.core.bean.stand.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.stand.StandtotheendCashPrizeApplyDAO;
import com.gt.game.core.dao.stand.StandtotheendMainDAO;
import com.gt.game.core.entity.stand.*;
import com.gt.game.core.exception.stand.StandException;
import com.gt.game.core.service.stand.*;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 商家活动表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Service
public class StandServiceImpl implements StandService {

    @Autowired
    StandtotheendMainService standtotheendMainService;

    @Autowired
    StandtotheendCashPrizeApplyService standtotheendCashPrizeApplyService;

    @Autowired
    StandtotheendPrizeService standtotheendPrizeService;

    @Autowired
    StandtotheendPrizeImgUrlService standtotheendPrizeImgUrlService;

    @Autowired
    StandtotheendCashAddressService standtotheendCashAddressService;

    @Autowired
    StandtotheendAuthorityService standtotheendAuthorityService;

    @Autowired
    StandtotheendJoinRecordService standtotheendJoinRecordService;

    @Autowired
    StandtotheendJoinDetailService standtotheendJoinDetailService;

    @Autowired
    StandtotheendQuesbankService standtotheendQuesbankService;

    @Autowired
    StandtotheendQuestionService standtotheendQuestionService;

    @Autowired
    StandtotheendCashPrizeApplyDAO standtotheendCashPrizeApplyDAO;

    @Autowired
    StandtotheendMainDAO standtotheendMainDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "standMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
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
        String url = applyProperties.getMobileBaseUrl() + "standMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }

    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<StandCountRes> getStandCount(BusUser busUser) {
        StandCountRes standCountRes = new StandCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = standtotheendMainDAO.getCount(params);
        standCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        standCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        standCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        standCountRes.setCount1(standCountRes.getCount2()+standCountRes.getCount3()+standCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",standCountRes);
    }
    /**
     * 分页获取活动列表
     *
     * @param standListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<StandListRes>> getStandList(BusUser busUser, StandListPageReq standListPageReq) {
        Page<StandListRes> page = new Page<>(standListPageReq.getCurrent(),standListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",standListPageReq.getStatus());
        map.put("name",standListPageReq.getName());
        map.put("busId",busUser.getId());
        List<StandListRes> StandListResList = standtotheendMainDAO.getStandList(page,map);
        for (StandListRes standListRes : StandListResList){
            if(standListRes.getActivityBegintime().getTime() > date.getTime()){
                standListRes.setIsEdit(1);
                standListRes.setStatus(0);
            }else if(standListRes.getActivityEndtime().getTime() < date.getTime()){
                standListRes.setIsEdit(0);
                standListRes.setStatus(2);
            }else {
                standListRes.setIsEdit(0);
                standListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",StandListResList,pageDTO);
    }

    @Override
    public ResponseDTO<List<StandApplyListRes>> getStandApplyList(BusUser busUser, StandApplyListPageReq standApplyListPageReq) {
        Page<StandApplyListRes> page = new Page<>(standApplyListPageReq.getCurrent(),standApplyListPageReq.getSize());
        List<StandApplyListRes> standApplyListResList = standtotheendCashPrizeApplyDAO.getStandApplyList(page,standApplyListPageReq);
        if(standApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < standApplyListResList.size(); i++) {
                ids.append(standApplyListResList.get(i).getMemberId());
                if (standApplyListResList.size() > 1 && i < standApplyListResList.size() - 1) {
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
            for (int i = 0; i < standApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == standApplyListResList.get(i).getMemberId().intValue()) {
                        standApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                        standApplyListResList.get(i).setMemberName(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(standApplyListResList.get(i).getNickname())) {
                    standApplyListResList.get(i).setNickname("未知用户");
                    standApplyListResList.get(i).setMemberName("未知用户");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",standApplyListResList,pageDTO);
    }
    /**
     * 发放奖品
     * @param standApplyIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO editStandApply(BusUser busUser, StandApplyIdReq standApplyIdReq) {
        StandtotheendCashPrizeApply standtotheendCashPrizeApply = standtotheendCashPrizeApplyService.selectById(standApplyIdReq.getId());
        if(CommonUtil.isNotEmpty(standtotheendCashPrizeApply)){
            StandtotheendMain standtotheendMain = standtotheendMainService.selectById(standtotheendCashPrizeApply.getActId());
            StandtotheendPrize standtotheendPrize = standtotheendPrizeService.selectById(standtotheendCashPrizeApply.getPrizeId());
            if(standtotheendPrize.getPrizeType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(standtotheendMain.getActivityBegintime())) {
                    //"未到兑奖时间！"
                    throw  new StandException(ResponseEnums.STAND_HAS3);
                } else if (!DateTimeKit.laterThanNow(DateTimeKit.addDate(standtotheendMain.getActivityEndtime(),standtotheendMain.getStandCashDay()))) {
                    //""已过兑奖时间！";
                    throw  new StandException(ResponseEnums.STAND_HAS4);
                }
            }
            if (standtotheendCashPrizeApply.getApplyStatus() == 3) {
                // 更改记录状态
                standtotheendCashPrizeApply.setApplyStatus(2);
                standtotheendCashPrizeApply.setCashTime(new Date());
                standtotheendCashPrizeApplyService.updateById(standtotheendCashPrizeApply);
            } else if (standtotheendCashPrizeApply.getApplyStatus() == 2){//已兑奖
                throw  new StandException(ResponseEnums.STAND_HAS1);
            }else{//还未提交
                throw  new StandException(ResponseEnums.STAND_HAS2);
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
    public ResponseDTO<StandRes> getStand(BusUser busUser, Integer id) {
        StandtotheendMain StandtotheendMain = standtotheendMainService.selectById(id);
        StandRes StandRes = new StandRes();
        if (CommonUtil.isNotEmpty(StandtotheendMain)) {
            BeanUtils.copyProperties(StandtotheendMain, StandRes);
            //奖品
            List<StandtotheendPrize> StandtotheendPrizes = standtotheendPrizeService.selectList(new EntityWrapper<StandtotheendPrize>().eq("act_id", id));
            List<StandPrizeReq> StandPrizeReqs = new ArrayList<>();
            if (StandtotheendPrizes.size() > 0) {
                for (StandtotheendPrize StandtotheendPrize : StandtotheendPrizes) {
                    StandPrizeReq StandPrizeReq = new StandPrizeReq();
                    BeanUtils.copyProperties(StandtotheendPrize, StandPrizeReq);
                    StandPrizeReq.setType(StandtotheendPrize.getPrizeType());
                    StandPrizeReq.setPrizeUnit(StandtotheendPrize.getPrizePer());
                    StandPrizeReq.setNum(StandtotheendPrize.getPrizeCount());
                    List<StandtotheendPrizeImgUrl> StandtotheendPrizeImgList = standtotheendPrizeImgUrlService.selectList(new EntityWrapper<StandtotheendPrizeImgUrl>().eq("prize_id", StandtotheendPrize.getId()));
                    List<StandPrizeImgReq> StandPrizeImgReqs = new ArrayList<>();
                    for (StandtotheendPrizeImgUrl standtotheendPrizeImgUrl : StandtotheendPrizeImgList) {
                        StandPrizeImgReq StandPrizeImgReq = new StandPrizeImgReq();
                        BeanUtils.copyProperties(standtotheendPrizeImgUrl, StandPrizeImgReq);
                        StandPrizeImgReqs.add(StandPrizeImgReq);
                    }
                    StandPrizeReq.setStandPrizeImgReqs(StandPrizeImgReqs);
                    StandPrizeReqs.add(StandPrizeReq);
                }
            }
            StandRes.setStandPrizeReqs(StandPrizeReqs);
            //兑奖地址
            List<StandtotheendCashAddress> StandtotheendAddresses = standtotheendCashAddressService.selectList(new EntityWrapper<StandtotheendCashAddress>().eq("act_id", id));
            List<StandAddressReq> StandAddressReqs = new ArrayList<>();
            if (StandtotheendAddresses.size() > 0) {
                for (StandtotheendCashAddress standtotheendCashAddress : StandtotheendAddresses) {
                    StandAddressReq StandAddressReq = new StandAddressReq();
                    BeanUtils.copyProperties(standtotheendCashAddress, StandAddressReq);
                    StandAddressReqs.add(StandAddressReq);
                }
            }
            StandRes.setStandAddressReqs(StandAddressReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",StandRes);
        
    }
    /**
     * 删除活动
     * @param standIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeStand(BusUser busUser, StandIdReq standIdReq) {
        StandtotheendMain StandtotheendMain = standtotheendMainService.selectById(standIdReq.getId());
        if(CommonUtil.isNotEmpty(StandtotheendMain)){
            if(StandtotheendMain.getActivityBegintime().getTime() < new Date().getTime() && StandtotheendMain.getActivityEndtime().getTime() > new Date().getTime()){
                throw new StandException(ResponseEnums.STAND_HAS10);
            }
            if(StandtotheendMain.getActivityBegintime().getTime() < new Date().getTime() && DateTimeKit.addDate(StandtotheendMain.getActivityEndtime(),StandtotheendMain.getStandCashDay()).getTime() > new Date().getTime()){
                throw new StandException(ResponseEnums.STAND_HAS12);
            }
            List<StandtotheendCashPrizeApply> StandtotheendCashPrizeApplies = standtotheendCashPrizeApplyService.selectList(
                    new EntityWrapper<StandtotheendCashPrizeApply>().eq("act_id",standIdReq.getId()).eq("apply_status",3));
            if(StandtotheendCashPrizeApplies.size() > 0 ){
                throw new StandException(ResponseEnums.STAND_HAS11);

            }
            if(StandtotheendMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new StandException(ResponseEnums.DIFF_USER);
            }
            standtotheendMainService.deleteById(StandtotheendMain.getId());
            //兑奖地址
            standtotheendCashAddressService.delete(new EntityWrapper<StandtotheendCashAddress>().eq("act_id",StandtotheendMain.getId()));
            //奖品
            List<StandtotheendPrize> StandtotheendPrizes = standtotheendPrizeService.selectList(new EntityWrapper<StandtotheendPrize>().eq("act_id",StandtotheendMain.getId()));
            boolean ff = false;
            if(StandtotheendPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(StandtotheendPrize StandtotheendPrize : StandtotheendPrizes){
                    ids.add(StandtotheendPrize.getId());
                    if(StandtotheendPrize.getPrizeType() == 1){
                        ff = true;
                    }
                }
                standtotheendPrizeImgUrlService.delete(new EntityWrapper<StandtotheendPrizeImgUrl>().in("prize_id",ids));
            }
            standtotheendPrizeService.delete(new EntityWrapper<StandtotheendPrize>().eq("act_id",StandtotheendMain.getId()));
            standtotheendAuthorityService.delete(new EntityWrapper<StandtotheendAuthority>().eq("main_id",StandtotheendMain.getId()));
            standtotheendCashPrizeApplyService.delete(new EntityWrapper<StandtotheendCashPrizeApply>().eq("act_id",StandtotheendMain.getId()));
            List<StandtotheendJoinRecord> standtotheendJoinRecords = standtotheendJoinRecordService.selectList(new EntityWrapper<StandtotheendJoinRecord>().eq("act_id",StandtotheendMain.getId()));
            if(standtotheendJoinRecords.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(StandtotheendJoinRecord standtotheendJoinRecord : standtotheendJoinRecords){
                    ids.add(standtotheendJoinRecord.getId());
                }
                standtotheendJoinDetailService.delete(new EntityWrapper<StandtotheendJoinDetail>().in("record_id",ids));
            }
            standtotheendJoinRecordService.delete(new EntityWrapper<StandtotheendJoinRecord>().eq("act_id",StandtotheendMain.getId()));
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(StandtotheendMain.getId());
                fenbiSurplus.setFre_type(35);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(StandtotheendMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(35);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new StandException(ResponseEnums.STAND_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 保存活动
     * @param standSaveReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveStand(BusUser busUser, StandSaveReq standSaveReq) {
        StandtotheendMain standtotheendMain = null;
        Double num = 0.0;
        int f = 0;
        if(standSaveReq.getId() == 0){//新增
            standtotheendMain = new StandtotheendMain();
            BeanUtils.copyProperties(standSaveReq,standtotheendMain);
            standtotheendMain.setBusId(busUser.getId());
            standtotheendMain.setCreatetime(new Date());
//            standtotheendMain.setFollowQrCode(standtotheendMain.getFollowQrCode().split("/upload").length > 1
//                    ?standtotheendMain.getFollowQrCode().split("/upload")[1]:standtotheendMain.getFollowQrCode());
            standtotheendMainService.insert(standtotheendMain);
        }else{//编辑
            standtotheendMain = standtotheendMainService.selectById(standSaveReq.getId());
            if(CommonUtil.isEmpty(standtotheendMain)){
                throw new StandException(ResponseEnums.STAND_HAS5);
            }
            if(standtotheendMain.getActivityBegintime().getTime() < new Date().getTime()){
                throw new StandException(ResponseEnums.STAND_HAS6);
            }
            if(standtotheendMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new StandException(ResponseEnums.DIFF_USER);
            }
            BeanUtils.copyProperties(standSaveReq,standtotheendMain);
//            standtotheendMain.setFollowQrCode(standtotheendMain.getFollowQrCode().split("/upload").length > 1
//                    ?standtotheendMain.getFollowQrCode().split("/upload")[1]:standtotheendMain.getFollowQrCode());
            standtotheendMainService.updateById(standtotheendMain);
            //删除
            //兑奖地址
            standtotheendCashAddressService.delete(new EntityWrapper<StandtotheendCashAddress>().eq("act_id",standtotheendMain.getId()));
            //奖品
            List<StandtotheendPrize> standtotheendPrizes = standtotheendPrizeService.selectList(new EntityWrapper<StandtotheendPrize>().eq("act_id",standtotheendMain.getId()));
            if(standtotheendPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(StandtotheendPrize StandtotheendPrize : standtotheendPrizes){
                    if(StandtotheendPrize.getPrizeType() == 1){
                        num += StandtotheendPrize.getPrizeCount();
                        f = 1;
                    }
                    ids.add(StandtotheendPrize.getId());
                }
                standtotheendPrizeImgUrlService.delete(new EntityWrapper<StandtotheendPrizeImgUrl>().in("prize_id",ids));
            }
            standtotheendPrizeService.delete(new EntityWrapper<StandtotheendPrize>().eq("act_id",standtotheendMain.getId()));
        }
        Double fenbi = 0.0;
        //奖品
        if(CommonUtil.isNotEmpty(standSaveReq.getStandPrizeReqs())){
            for(StandPrizeReq StandPrizeReq :standSaveReq.getStandPrizeReqs()){
                if(StandPrizeReq.getType()==1){
                    fenbi += StandPrizeReq.getNum();
                }
                StandtotheendPrize standtotheendPrize = new StandtotheendPrize();
                BeanUtils.copyProperties(StandPrizeReq,standtotheendPrize);
                standtotheendPrize.setActId(standtotheendMain.getId());
                standtotheendPrize.setPrizeType(StandPrizeReq.getType());
                standtotheendPrize.setPrizePer(StandPrizeReq.getPrizeUnit());
                standtotheendPrize.setPrizeCount(StandPrizeReq.getNum());
                standtotheendPrizeService.insert(standtotheendPrize);
                if(StandPrizeReq.getStandPrizeImgReqs().size() > 0){
                    for(StandPrizeImgReq StandPrizeImgReq : StandPrizeReq.getStandPrizeImgReqs()){
                        StandtotheendPrizeImgUrl standtotheendPrizeImgUrl = new StandtotheendPrizeImgUrl();
                        BeanUtils.copyProperties(StandPrizeImgReq,standtotheendPrizeImgUrl);
                        standtotheendPrizeImgUrl.setPrizeId(standtotheendPrize.getId());
                        if(CommonUtil.isNotEmpty(standtotheendPrizeImgUrl.getPicUrl())) standtotheendPrizeImgUrl.setPicUrl(standtotheendPrizeImgUrl.getPicUrl().split("/upload").length>1?
                                standtotheendPrizeImgUrl.getPicUrl().split("/upload")[1]:standtotheendPrizeImgUrl.getPicUrl());
                        standtotheendPrizeImgUrlService.insert(standtotheendPrizeImgUrl);
                    }
                }
            }
        }
        //兑奖地址
        if(CommonUtil.isNotEmpty(standSaveReq.getStandAddressReqs())){
            for(StandAddressReq StandAddressReq :standSaveReq.getStandAddressReqs()){
                StandtotheendCashAddress standtotheendCashAddress = new StandtotheendCashAddress();
                BeanUtils.copyProperties(StandAddressReq,standtotheendCashAddress);
                standtotheendCashAddress.setActId(standtotheendMain.getId());
                standtotheendCashAddressService.insert(standtotheendCashAddress);
            }
        }
        if(fenbi == 0.0 && num > 0.0){
            throw new StandException(ResponseEnums.COMMON_HAS18);
        }
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new StandException(ResponseEnums.STAND_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new StandException(ResponseEnums.STAND_HAS7);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(standtotheendMain.getId());
                updateFenbiReduceReq.setFreType(35);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new StandException(ResponseEnums.STAND_HAS8);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new StandException(ResponseEnums.STAND_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, standtotheendMain.getId(), 35, 1, "一站到底活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new StandException(ResponseEnums.STAND_HAS8);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    /**
     * 分页获取核销授权
     * @param standAuthorityListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<StandAuthorityListRes>> getStandAuthorityList(BusUser busUser, StandAuthorityListPageReq standAuthorityListPageReq) {
        Page<StandtotheendAuthority> page = new Page<>(standAuthorityListPageReq.getCurrent(),standAuthorityListPageReq.getSize());
        List<StandtotheendAuthority> standtotheendAuthorityList = standtotheendAuthorityService.selectPage(page,
                new EntityWrapper<StandtotheendAuthority>().eq("main_id",standAuthorityListPageReq.getActId())
                        .eq("state",0)).getRecords();
        List<StandAuthorityListRes> standAuthorityListRes = new ArrayList<>();
        for (StandtotheendAuthority standtotheendAuthority : standtotheendAuthorityList){
            StandAuthorityListRes standAuthorityListRes1 = new StandAuthorityListRes();
            BeanUtils.copyProperties(standtotheendAuthority,standAuthorityListRes1);
            standAuthorityListRes.add(standAuthorityListRes1);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",standAuthorityListRes,pageDTO);
    }
    /**
     * 获取奖品类型列表
     *
     */
    @Override
    public ResponseDTO<List<StandPrizeTypeListRes>> getStandPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<StandPrizeTypeListRes> standPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                StandPrizeTypeListRes standPrizeTypeListRes = new StandPrizeTypeListRes();
                standPrizeTypeListRes.setName(dictApiRes.getItemValue());
                standPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                standPrizeTypeListResList.add(standPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",standPrizeTypeListResList);
    }
    /**
     * 删除核销授权
     * @param standAuthorityIdsReq
     * @return
     */
    @Override
    public ResponseDTO removeStandAuthority(BusUser busUser, StandAuthorityIdsReq standAuthorityIdsReq) {
        StandtotheendMain standtotheendMain = standtotheendMainService.selectById(standAuthorityIdsReq.getActId());
        if(CommonUtil.isNotEmpty(standtotheendMain)) {
            if (standtotheendMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new StandException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new StandException(ResponseEnums.STAND_HAS5);
        }
        StandtotheendAuthority standtotheendAuthority = new StandtotheendAuthority();
        standtotheendAuthority.setState(1);
        standtotheendAuthorityService.update(standtotheendAuthority,new EntityWrapper<StandtotheendAuthority>().in("id",standAuthorityIdsReq.getIds()));
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 导出中奖名单
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> exports(Map<String, Object> params){
        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            StandtotheendMain main = standtotheendMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = standtotheendCashPrizeApplyDAO.findExports(params);
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
                AxisResult<List<MemberRes>> axisResult = MemberServer.findMemberByIds(memberReq);
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
            String title = "活动名称：" + main.getActName() + "，开始时间：" + sdf.format(main.getActivityBegintime()) + "结束时间："
                    + sdf.format(main.getActivityEndtime());
            HSSFWorkbook book = exportExcelForRecoding(list, title);
            msg.put("book", book);
            msg.put("fileName", main.getActName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "一站到底活动记录导出excel失败！");
            e.printStackTrace();
        } finally {
            msg.put("result", result);
            msg.put("message", message);
        }
        return msg;
    }
    /**
     * 获取用户信息列表
     * @param standIdReq
     * @return
     */
    @Override
    public ResponseDTO<List<StandJoinRecordListRes>> getStandJoinRecord(BusUser busUser, StandIdReq standIdReq) {
        List<StandtotheendJoinRecord> standtotheendJoinRecordList = standtotheendJoinRecordService.selectList(
                                                    new EntityWrapper<StandtotheendJoinRecord>().eq("act_id",standIdReq.getId()).orderBy("id",false));
        List<StandJoinRecordListRes> standJoinRecordListResList = new ArrayList<>();
        for(StandtotheendJoinRecord standtotheendJoinRecord :standtotheendJoinRecordList){
            StandJoinRecordListRes standJoinRecordListRes = new StandJoinRecordListRes();
            BeanUtils.copyProperties(standtotheendJoinRecord,standJoinRecordListRes);
            standJoinRecordListResList.add(standJoinRecordListRes);
        }
        return ResponseDTO.createBySuccess("获取成功",standJoinRecordListResList);
    }
    /**
     * 删除用户信息
     * @param standRecordIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeStandJoinRecord(BusUser busUser, StandRecordIdReq standRecordIdReq) {
        standtotheendJoinRecordService.deleteById(standRecordIdReq.getId());
        standtotheendJoinDetailService.delete(new EntityWrapper<StandtotheendJoinDetail>().eq("record_id",standRecordIdReq.getId()));
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 获取答题记录列表
     * @param standRecordIdReq
     * @return
     */
    @Override
    public ResponseDTO<List<StandJoinDetailListRes>> getStandJoinDetail(BusUser busUser, StandRecordIdReq standRecordIdReq) {
        List<StandtotheendJoinDetail> standtotheendJoinDetailList = standtotheendJoinDetailService.selectList(
                new EntityWrapper<StandtotheendJoinDetail>().eq("record_id",standRecordIdReq.getId()).orderBy("id",false));
        List<StandJoinDetailListRes> standJoinDetailListResList = new ArrayList<>();
        for(StandtotheendJoinDetail standtotheendJoinDetail :standtotheendJoinDetailList){
            StandJoinDetailListRes standJoinRecordListRes = new StandJoinDetailListRes();
            BeanUtils.copyProperties(standtotheendJoinDetail,standJoinRecordListRes);
            standJoinDetailListResList.add(standJoinRecordListRes);
        }
        return ResponseDTO.createBySuccess("获取成功",standJoinDetailListResList);
    }
    /**
     * 获取题库列表
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<List<StandQuesbankListRes>> getStandQuesbankList(BusUser busUser) {
        List<StandtotheendQuesbank> standtotheendQuesbankList = standtotheendQuesbankService.selectList(
                new EntityWrapper<StandtotheendQuesbank>().eq("bus_id",busUser.getId()).orderBy("id",false));
        List<StandQuesbankListRes> standQuesbankListResList = new ArrayList<>();
        for(StandtotheendQuesbank standtotheendQuesbank :standtotheendQuesbankList){
            StandQuesbankListRes standJoinRecordListRes = new StandQuesbankListRes();
            BeanUtils.copyProperties(standtotheendQuesbank,standJoinRecordListRes);
            standQuesbankListResList.add(standJoinRecordListRes);
        }
        return ResponseDTO.createBySuccess("获取成功",standQuesbankListResList);
    }
    /**
     * 获取题库
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<StandQuesbankRes> getStandQuesbank(BusUser busUser, StandQuesbankIdReq standQuesbankIdReq) {
        StandQuesbankRes standQuesbankRes = new StandQuesbankRes();
        StandtotheendQuesbank standtotheendQuesbank = standtotheendQuesbankService.selectById(standQuesbankIdReq.getId());
        if(CommonUtil.isNotEmpty(standQuesbankRes)){
            BeanUtils.copyProperties(standtotheendQuesbank,standQuesbankRes);
            List<StandtotheendQuestion> standtotheendQuestionList = standtotheendQuestionService.selectList(new EntityWrapper<StandtotheendQuestion>()
                                                                                            .eq("bank_id",standQuesbankIdReq.getId()).orderBy("id",false));
            List<StandQuestionRes> standQuestionResList = new ArrayList<>();
            for(StandtotheendQuestion standtotheendQuestion : standtotheendQuestionList){
                StandQuestionRes standQuestionRes = new StandQuestionRes();
                BeanUtils.copyProperties(standtotheendQuestion,standQuestionRes);
                standQuestionResList.add(standQuestionRes);
            }
            standQuesbankRes.setStandQuestionResList(standQuestionResList);
        }
        return ResponseDTO.createBySuccess("获取成功",standQuesbankRes);
    }
    /**
     * 保存题库
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<StandQuesbankSaveReq> saveStandQuesbank(BusUser busUser, StandQuesbankSaveReq standQuesbankSaveReq) {
        StandtotheendQuesbank standtotheendQuesbank = null;
        if(standQuesbankSaveReq.getId() == 0){ //新增
            standtotheendQuesbank  =new StandtotheendQuesbank();
            standtotheendQuesbank.setBankName(standQuesbankSaveReq.getBankName());
            standtotheendQuesbank.setCreateTime(new Date());
            standtotheendQuesbank.setBusId(busUser.getId());
            standtotheendQuesbank.setQuesAmount(0);
            standtotheendQuesbankService.insert(standtotheendQuesbank);
            standQuesbankSaveReq.setId(standtotheendQuesbank.getId());
        }else{ //编辑
            isEdit(standQuesbankSaveReq.getId());
            standtotheendQuesbank = standtotheendQuesbankService.selectById(standQuesbankSaveReq.getId());
            if(CommonUtil.isNotEmpty(standtotheendQuesbank)){
                standtotheendQuesbank.setBankName(standQuesbankSaveReq.getBankName());
                standtotheendQuesbankService.updateById(standtotheendQuesbank);
            }
        }
        return ResponseDTO.createBySuccess("保存成功",standQuesbankSaveReq);
    }
    /**
     * 保存题目
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO saveStandQuestion(BusUser busUser, StandQuestionSaveReq standQuestionSaveReq) {
        StandtotheendQuestion standtotheendQuestion = null;
        isEdit(standQuestionSaveReq.getBankId());
        if(standQuestionSaveReq.getId() == 0){ //新增
            standtotheendQuestion  =new StandtotheendQuestion();
            standtotheendQuestion.setCreatetime(new Date());
            BeanUtils.copyProperties(standQuestionSaveReq,standtotheendQuestion);
            standtotheendQuestionService.insert(standtotheendQuestion);
        }else{ //编辑
            standtotheendQuestion = standtotheendQuestionService.selectById(standQuestionSaveReq.getId());
            if(CommonUtil.isNotEmpty(standtotheendQuestion)){
                BeanUtils.copyProperties(standQuestionSaveReq,standtotheendQuestion);
                standtotheendQuestionService.updateById(standtotheendQuestion);
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    public void isEdit(Integer id){
        List<StandtotheendMain> mains = standtotheendMainService.selectList(new EntityWrapper<StandtotheendMain>().eq("bank_id",id));
        Date date = new Date();
        for (StandtotheendMain standtotheendMain : mains){
            if(standtotheendMain.getActivityBegintime().getTime() < date.getTime() && standtotheendMain.getActivityEndtime().getTime() > date.getTime() ){
                throw new StandException(ResponseEnums.STAND_HAS14);
            }
        }
    }
    /**
     * 删除题库
     * @param busUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeStandQuesbank(BusUser busUser, StandQuesbankIdReq standQuesbankIdReq) {
        isEdit(standQuesbankIdReq.getId());
        standtotheendQuesbankService.deleteById(standQuesbankIdReq.getId());
        standtotheendQuestionService.delete(new EntityWrapper<StandtotheendQuestion>().eq("bank_id",standQuesbankIdReq.getId()));
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 删除题目
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO removeStandQuestion(BusUser busUser, StandQuesbankIdReq standQuesbankIdReq) {
        StandtotheendQuestion standtotheendQuestion = standtotheendQuestionService.selectById(standQuesbankIdReq.getId());
        if(CommonUtil.isNotEmpty(standtotheendQuestion)){
            isEdit(standtotheendQuestion.getBankId());
            standtotheendQuestionService.deleteById(standQuesbankIdReq.getId());
        }
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 导入题目
     * @param busUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO uploadStandQuestion(BusUser busUser, MultipartFile file, Integer bankId) {
        isEdit(bankId);
        String last = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        boolean flag=false;
        if(!last.equalsIgnoreCase(".xls")&&!last.equalsIgnoreCase(".xlsx")){
           // strMsg="格式错误！请上传Excel文件，文件后缀为xls或者xlsx。";
            throw new StandException(ResponseEnums.STAND_HAS15);
        }
        //导入
        Workbook workbook=null;
        try {
            if(last.equalsIgnoreCase("xls")){
                workbook = new HSSFWorkbook(file.getInputStream());
            }else{
                workbook = WorkbookFactory.create(file.getInputStream());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(CommonUtil.isEmpty(workbook)){
            throw new StandException(ResponseEnums.STAND_HAS16);
        }
        Sheet sheetAt = workbook.getSheetAt(0);
        int physicalNumberOfRows = sheetAt.getPhysicalNumberOfRows();
        //检查
        for(int i =1;i<physicalNumberOfRows;i++){
            Row row = sheetAt.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();
            List<Cell> list = IteratorUtils.toList(cellIterator);
            if(list.size()!=6){
               // strMsg="导入失败,格式有误！第"+i+1+"行或存在空值！请修正后再尝试导入!";
                throw new StandException(ResponseEnums.STAND_HAS17);
            }
        }
        List<StandtotheendQuestion> questions = new ArrayList<>();
        for(int i =1;i<physicalNumberOfRows;i++){
            Row row = sheetAt.getRow(i);
            Iterator<Cell> cellIterator = row.cellIterator();
            List<HSSFCell> list = IteratorUtils.toList(cellIterator);
            StandtotheendQuestion question = new StandtotheendQuestion();
            question.setBankId(bankId);
            question.setCreatetime(DateTimeKit.getNow());
            for(int j=0;j<list.size();j++){
                Cell cell = list.get(j);
                switch (j) {
                    case 0:
                        question.setQuesTitle(cell.toString().trim());
                        break;
                    case 1:
                        question.setOptionA(cell.toString().trim());
                        break;
                    case 2:
                        question.setOptionB(cell.toString().trim());
                        break;
                    case 3:
                        question.setOptionC(cell.toString().trim());
                        break;
                    case 4:
                        question.setOptionD(cell.toString().trim());
                        break;
                    case 5:
                        question.setRightAnswer(cell.toString().trim());
                        break;
                    default:
                        //strMsg="导入失败,格式有误！";
                        throw new StandException(ResponseEnums.STAND_HAS18);
                }
            }
            //保存
            questions.add(question);
        }
        //保存
        standtotheendQuestionService.insertBatch(questions);
        //更新题库数量
        StandtotheendQuesbank bank = standtotheendQuesbankService.selectById(bankId);
        bank.setQuesAmount(bank.getQuesAmount()+questions.size());
        standtotheendQuesbankService.updateById(bank);
        return ResponseDTO.createBySuccess("导入成功");
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
        sheet.setColumnWidth((short) (9), (short) 6000);
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
        createCell(wb, row1, 9, "兑奖人姓名", font1);
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
            String turPriType = delWithColumn(map.get("prize_type"));
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
            createCell(wb, rowData, 2, priTypeName + "/" + delWithColumn(map.get("prize_per")) + priTypeUnit, font1);

            createCell(wb, rowData, 3, delWithColumn(map.get("total_score")), font1);
            createCell(wb, rowData, 4,
                    delWithColumn(CommonUtil.isEmpty(map.get("cashTime")) ? ""
                            : DateTimeKit.format(
                            DateTimeKit.parseDate(map.get("cashTime").toString(), "yyyy/MM/dd HH:mm:ss"),
                            "yyyy-MM-dd HH:mm")),
                    font1);
            createCell(wb, rowData, 5, delWithColumn(map.get("apply_phone")), font1);
            createCell(wb, rowData, 6, CommonUtil.isEmpty(map.get("nickname"))?"未知用户":map.get("nickname").toString(), font1);
            if ("1".equals(delWithColumn(map.get("apply_status")).toString())) {
                createCell(wb, rowData, 7, "未兑奖", font1);
            } else if ("2".equals(delWithColumn(map.get("apply_status")).toString())) {
                createCell(wb, rowData, 7, "已兑奖", font1);
            } else {
                createCell(wb, rowData, 7, "已提交", font1);
            }
            createCell(wb, rowData, 8, delWithColumn(map.get("sn_code")), font1);
            createCell(wb, rowData, 9, delWithColumn(map.get("apply_name")), font1);

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
