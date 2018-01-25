package com.gt.game.core.service.goldRush.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
import com.gt.game.core.bean.goldRush.req.*;
import com.gt.game.core.bean.goldRush.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.goldRush.GoldRushCashPrizeApplyDAO;
import com.gt.game.core.dao.goldRush.GoldRushMainDAO;
import com.gt.game.core.entity.goldRush.*;
import com.gt.game.core.exception.goldRush.GoldRushException;
import com.gt.game.core.service.goldRush.*;
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
 * 欢乐抢元宝 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-09
 */
@Service
public class GoldRushServiceImpl  implements GoldRushService {

    @Autowired
    GoldRushAddressService goldRushAddressService;

    @Autowired
    GoldRushAuthorityService goldRushAuthorityService;

    @Autowired
    GoldRushCashPrizeApplyService goldRushCashPrizeApplyService;

    @Autowired
    GoldRushMainService goldRushMainService;

    @Autowired
    GoldRushPlayRecordService goldRushPlayRecordService;

    @Autowired
    GoldRushPrizeImgService goldRushPrizeImgService;

    @Autowired
    GoldRushPrizeService goldRushPrizeService;

    @Autowired
    GoldRushMainDAO goldRushMainDAO;

    @Autowired
    GoldRushCashPrizeApplyDAO goldRushCashPrizeApplyDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "goldrushMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
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
        String url = applyProperties.getMobileBaseUrl() + "goldrushMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }
    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<GoldRushCountRes> getGoldRushCount(BusUser busUser) {
        GoldRushCountRes GoldRushCountRes = new GoldRushCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = goldRushMainDAO.getCount(params);
        GoldRushCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        GoldRushCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        GoldRushCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        GoldRushCountRes.setCount1(GoldRushCountRes.getCount2()+GoldRushCountRes.getCount3()+GoldRushCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",GoldRushCountRes);
    }
    /**
     * 分页获取活动列表
     *
     * @param GoldRushListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<GoldRushListRes>> getGoldRushList(BusUser busUser, GoldRushListPageReq GoldRushListPageReq) {
        Page<GoldRushListRes> page = new Page<>(GoldRushListPageReq.getCurrent(),GoldRushListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",GoldRushListPageReq.getStatus());
        map.put("name",GoldRushListPageReq.getName());
        map.put("busId",busUser.getId());
        List<GoldRushListRes> GoldRushListResList = goldRushMainDAO.getGoldRushList(page,map);
        for (GoldRushListRes GoldRushListRes : GoldRushListResList){
            if(GoldRushListRes.getActivityBeginTime().getTime() > date.getTime()){
                GoldRushListRes.setIsEdit(1);
                GoldRushListRes.setStatus(0);
            }else if(GoldRushListRes.getActivityEndTime().getTime() < date.getTime()){
                GoldRushListRes.setIsEdit(0);
                GoldRushListRes.setStatus(2);
            }else {
                GoldRushListRes.setIsEdit(0);
                GoldRushListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",GoldRushListResList,pageDTO);
    }
    /**
     * 分页获取中奖记录列表
     * @param GoldRushApplyListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<GoldRushApplyListRes>> getGoldRushApplyList(BusUser busUser, GoldRushApplyListPageReq GoldRushApplyListPageReq) {
        Page<GoldRushApplyListRes> page = new Page<>(GoldRushApplyListPageReq.getCurrent(),GoldRushApplyListPageReq.getSize());
        List<GoldRushApplyListRes> GoldRushApplyListResList = goldRushCashPrizeApplyDAO.queryRecodList(page,GoldRushApplyListPageReq);
        if(GoldRushApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < GoldRushApplyListResList.size(); i++) {
                ids.append(GoldRushApplyListResList.get(i).getMemberId());
                if (GoldRushApplyListResList.size() > 1 && i < GoldRushApplyListResList.size() - 1) {
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
            for (int i = 0; i < GoldRushApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == GoldRushApplyListResList.get(i).getMemberId().intValue()) {
                        GoldRushApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(GoldRushApplyListResList.get(i).getNickname())) {
                    GoldRushApplyListResList.get(i).setNickname("未知用户");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",GoldRushApplyListResList,pageDTO);

    }
    /**
     * 中奖记录发放奖品
     * @param GoldRushApplyIdReq
     * @return
     */
    @Override
    public ResponseDTO editGoldRushApply(BusUser busUser, GoldRushApplyIdReq GoldRushApplyIdReq) {
        GoldRushCashPrizeApply GoldRushCashPrizeApply = goldRushCashPrizeApplyService.selectById(GoldRushApplyIdReq.getId());
        if(CommonUtil.isNotEmpty(GoldRushCashPrizeApply)){
            GoldRushMain main = goldRushMainService.selectById(GoldRushCashPrizeApply.getActId());
            GoldRushPrize GoldRushPrize = goldRushPrizeService.selectById(GoldRushCashPrizeApply.getPrizeId());
            if(GoldRushPrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(main.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new GoldRushException(ResponseEnums.GOLDRUSH_HAS3);
                } else if (!DateTimeKit.laterThanNow(main.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new GoldRushException(ResponseEnums.GOLDRUSH_HAS4);
                }
            }
            if (GoldRushCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                GoldRushCashPrizeApply.setStatus(2);
                GoldRushCashPrizeApply.setCashTime(new Date());
                goldRushCashPrizeApplyService.updateById(GoldRushCashPrizeApply);
            } else if (GoldRushCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new GoldRushException(ResponseEnums.GOLDRUSH_HAS1);
            }else{//还未提交
                throw  new GoldRushException(ResponseEnums.GOLDRUSH_HAS2);
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
    public ResponseDTO<GoldRushRes> getGoldRush(BusUser busUser, Integer id) {
        GoldRushMain GoldRushMain = goldRushMainService.selectById(id);
        GoldRushRes GoldRushRes = new GoldRushRes();
        if (CommonUtil.isNotEmpty(GoldRushMain)) {
            BeanUtils.copyProperties(GoldRushMain, GoldRushRes);
            //奖品
            List<GoldRushPrize> GoldRushPrizes = goldRushPrizeService.selectList(new EntityWrapper<GoldRushPrize>().eq("act_id", id));
            List<GoldRushPrizeReq> GoldRushPrizeReqs = new ArrayList<>();
            if (GoldRushPrizes.size() > 0) {
                for (GoldRushPrize GoldRushPrize : GoldRushPrizes) {
                    GoldRushPrizeReq GoldRushPrizeReq = new GoldRushPrizeReq();
                    BeanUtils.copyProperties(GoldRushPrize, GoldRushPrizeReq);
                    List<GoldRushPrizeImg> GoldRushPrizeImgs = goldRushPrizeImgService.selectList(new EntityWrapper<GoldRushPrizeImg>().eq("prize_id", GoldRushPrize.getId()));
                    List<GoldRushPrizeImgReq> GoldRushPrizeImgReqs = new ArrayList<>();
                    for (GoldRushPrizeImg GoldRushPrizeImg : GoldRushPrizeImgs) {
                        GoldRushPrizeImgReq GoldRushPrizeImgReq = new GoldRushPrizeImgReq();
                        BeanUtils.copyProperties(GoldRushPrizeImg, GoldRushPrizeImgReq);
                        GoldRushPrizeImgReqs.add(GoldRushPrizeImgReq);
                    }
                    GoldRushPrizeReq.setGoldRushPrizeImgReqs(GoldRushPrizeImgReqs);
                    GoldRushPrizeReqs.add(GoldRushPrizeReq);
                }
            }
            GoldRushRes.setGoldRushPrizeReqs(GoldRushPrizeReqs);
            //兑奖地址
            List<GoldRushAddress> GoldRushAddresses = goldRushAddressService.selectList(new EntityWrapper<GoldRushAddress>().eq("act_id", id));
            List<GoldRushAddressReq> GoldRushAddressReqs = new ArrayList<>();
            if (GoldRushAddresses.size() > 0) {
                for (GoldRushAddress GoldRushAddress : GoldRushAddresses) {
                    GoldRushAddressReq GoldRushAddressReq = new GoldRushAddressReq();
                    BeanUtils.copyProperties(GoldRushAddress, GoldRushAddressReq);
                    GoldRushAddressReqs.add(GoldRushAddressReq);
                }
            }
            GoldRushRes.setGoldRushAddressReqs(GoldRushAddressReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",GoldRushRes);
    }
    /**
     * 删除活动
     * @param GoldRushIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeGoldRush(BusUser busUser, GoldRushIdReq GoldRushIdReq) {
        GoldRushMain GoldRushMain = goldRushMainService.selectById(GoldRushIdReq.getId());
        if(CommonUtil.isNotEmpty(GoldRushMain)){
            if(GoldRushMain.getActivityBeginTime().getTime() < new Date().getTime() && GoldRushMain.getActivityEndTime().getTime() > new Date().getTime()){
                throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS10);
            }
            if(GoldRushMain.getCashPrizeBeginTime().getTime() < new Date().getTime() && GoldRushMain.getCashPrizeEndTime().getTime() > new Date().getTime()){
                throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS12);
            }
            List<GoldRushCashPrizeApply> GoldRushCashPrizeApplies = goldRushCashPrizeApplyService.selectList(
                    new EntityWrapper<GoldRushCashPrizeApply>().eq("act_id",GoldRushIdReq.getId()).eq("status",3));
            if(GoldRushCashPrizeApplies.size() > 0 ){
                throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS11);

            }
            if(GoldRushMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new GoldRushException(ResponseEnums.DIFF_USER);
            }
            goldRushMainService.deleteById(GoldRushMain.getId());
            //兑奖地址
            goldRushAddressService.delete(new EntityWrapper<GoldRushAddress>().eq("act_id",GoldRushMain.getId()));
            //奖品
            List<GoldRushPrize> GoldRushPrizes = goldRushPrizeService.selectList(new EntityWrapper<GoldRushPrize>().eq("act_id",GoldRushMain.getId()));
            boolean ff = false;
            if(GoldRushPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(GoldRushPrize GoldRushPrize : GoldRushPrizes){
                    ids.add(GoldRushPrize.getId());
                    if(GoldRushPrize.getType() == 1){
                        ff = true;
                    }
                }
                goldRushPrizeImgService.delete(new EntityWrapper<GoldRushPrizeImg>().in("prize_id",ids));
            }
             goldRushPrizeService.delete(new EntityWrapper<GoldRushPrize>().eq("act_id",GoldRushMain.getId()));
            goldRushAuthorityService.delete(new EntityWrapper<GoldRushAuthority>().eq("main_id",GoldRushMain.getId()));
            goldRushCashPrizeApplyService.delete(new EntityWrapper<GoldRushCashPrizeApply>().eq("act_id",GoldRushMain.getId()));
            goldRushPlayRecordService.delete(new EntityWrapper<GoldRushPlayRecord>().eq("act_id",GoldRushMain.getId()));
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(GoldRushMain.getId());
                fenbiSurplus.setFre_type(38);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(GoldRushMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(38);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 保存活动
     * @param GoldRushSaveReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveGoldRush(BusUser busUser, GoldRushSaveReq GoldRushSaveReq) {
        if(GoldRushSaveReq.getGoldRushPrizeReqs() == null || GoldRushSaveReq.getGoldRushPrizeReqs().size() < 1){
            throw new GoldRushException(ResponseEnums.COMMON_HAS20);
        }
        GoldRushMain GoldRushMain = null;
        Double num = 0.0;
        int f = 0;
        if(GoldRushSaveReq.getActivityBeginTime() == null){
            throw new GoldRushException(ResponseEnums.COMMON_HAS17);
        }
        if(GoldRushSaveReq.getCashPrizeBeginTime().getTime() < GoldRushSaveReq.getActivityBeginTime().getTime()){
            throw new GoldRushException(ResponseEnums.COMMON_HAS16);
        }
        if(GoldRushSaveReq.getId() == 0){//新增
            GoldRushMain = new GoldRushMain();
            BeanUtils.copyProperties(GoldRushSaveReq,GoldRushMain);
            GoldRushMain.setBusId(busUser.getId());
            GoldRushMain.setCreatetime(new Date());
            GoldRushMain.setFollowQrCode(GoldRushMain.getFollowQrCode().split("/upload").length > 1
                    ?GoldRushMain.getFollowQrCode().split("/upload")[1]:GoldRushMain.getFollowQrCode());
            goldRushMainService.insert(GoldRushMain);
        }else{//编辑
            GoldRushMain = goldRushMainService.selectById(GoldRushSaveReq.getId());
            if(CommonUtil.isEmpty(GoldRushMain)){
                throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS5);
            }
            if(GoldRushMain.getActivityBeginTime().getTime() < new Date().getTime()){
                throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS6);
            }
            if(GoldRushMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new GoldRushException(ResponseEnums.DIFF_USER);
            }
            BeanUtils.copyProperties(GoldRushSaveReq,GoldRushMain);
            GoldRushMain.setFollowQrCode(GoldRushMain.getFollowQrCode().split("/upload").length > 1
                    ?GoldRushMain.getFollowQrCode().split("/upload")[1]:GoldRushMain.getFollowQrCode());
            goldRushMainService.updateById(GoldRushMain);
            //删除
            //兑奖地址
            goldRushAddressService.delete(new EntityWrapper<GoldRushAddress>().eq("act_id",GoldRushMain.getId()));
            //奖品
            List<GoldRushPrize> GoldRushPrizes = goldRushPrizeService.selectList(new EntityWrapper<GoldRushPrize>().eq("act_id",GoldRushMain.getId()));
            if(GoldRushPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(GoldRushPrize GoldRushPrize : GoldRushPrizes){
                    if(GoldRushPrize.getType() == 1){
                        num += GoldRushPrize.getNum();
                        f = 1;
                    }
                    ids.add(GoldRushPrize.getId());
                }
                goldRushPrizeImgService.delete(new EntityWrapper<GoldRushPrizeImg>().in("prize_id",ids));
            }
            goldRushPrizeService.delete(new EntityWrapper<GoldRushPrize>().eq("act_id",GoldRushMain.getId()));
        }
        Double fenbi = 0.0;
        //奖品
        if(CommonUtil.isNotEmpty(GoldRushSaveReq.getGoldRushPrizeReqs())){
            for(GoldRushPrizeReq GoldRushPrizeReq :GoldRushSaveReq.getGoldRushPrizeReqs()){
                if(GoldRushPrizeReq.getType()==1){
                    fenbi += GoldRushPrizeReq.getNum();
                }
                GoldRushPrize GoldRushPrize = new GoldRushPrize();
                BeanUtils.copyProperties(GoldRushPrizeReq,GoldRushPrize);
                GoldRushPrize.setActId(GoldRushMain.getId());
                goldRushPrizeService.insert(GoldRushPrize);
                if(GoldRushPrizeReq.getGoldRushPrizeImgReqs().size() > 0){
                    for(GoldRushPrizeImgReq GoldRushPrizeImgReq : GoldRushPrizeReq.getGoldRushPrizeImgReqs()){
                        GoldRushPrizeImg GoldRushPrizeImg = new GoldRushPrizeImg();
                        BeanUtils.copyProperties(GoldRushPrizeImgReq,GoldRushPrizeImg);
                        GoldRushPrizeImg.setPrizeId(GoldRushPrize.getId());
                        GoldRushPrizeImg.setImgUrl(GoldRushPrizeImg.getImgUrl().split("/upload").length>1?
                                GoldRushPrizeImg.getImgUrl().split("/upload")[1]:GoldRushPrizeImg.getImgUrl());
                        goldRushPrizeImgService.insert(GoldRushPrizeImg);
                    }
                }
            }
        }
        //兑奖地址
        if(CommonUtil.isNotEmpty(GoldRushSaveReq.getGoldRushAddressReqs())){
            for(GoldRushAddressReq GoldRushAddressReq :GoldRushSaveReq.getGoldRushAddressReqs()){
                GoldRushAddress GoldRushAddress = new GoldRushAddress();
                BeanUtils.copyProperties(GoldRushAddressReq,GoldRushAddress);
                GoldRushAddress.setActId(GoldRushMain.getId());
                goldRushAddressService.insert(GoldRushAddress);
            }
        }
        if(fenbi == 0.0 && num > 0.0){
            throw new GoldRushException(ResponseEnums.COMMON_HAS18);
        }
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS14);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(GoldRushMain.getId());
                updateFenbiReduceReq.setFreType(38);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS15);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, GoldRushMain.getId(), 38, 1, "一箭穿心活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS8);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    /**
     * 分页获取核销授权
     * @param GoldRushAuthorityListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<GoldRushAuthorityListRes>> getGoldRushAuthorityList(BusUser busUser, GoldRushAuthorityListPageReq GoldRushAuthorityListPageReq) {
        Page<GoldRushAuthority> page = new Page<>(GoldRushAuthorityListPageReq.getCurrent(),GoldRushAuthorityListPageReq.getSize());
        List<GoldRushAuthority> GoldRushAuthorityList = goldRushAuthorityService.selectPage(page,
                new EntityWrapper<GoldRushAuthority>().eq("main_id",GoldRushAuthorityListPageReq.getActId())
                        .eq("state",0)).getRecords();
        List<GoldRushAuthorityListRes> GoldRushAuthorityListRes = new ArrayList<>();
        for (GoldRushAuthority GoldRushAuthority : GoldRushAuthorityList){
            GoldRushAuthorityListRes GoldRushAuthorityListRes1 = new GoldRushAuthorityListRes();
            BeanUtils.copyProperties(GoldRushAuthority,GoldRushAuthorityListRes1);
            GoldRushAuthorityListRes.add(GoldRushAuthorityListRes1);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",GoldRushAuthorityListRes,pageDTO);
    }
    /**
     * 获取奖品类型列表
     *
     */
    @Override
    public ResponseDTO<List<GoldRushPrizeTypeListRes>> getGoldRushPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<GoldRushPrizeTypeListRes> GoldRushPrizeTypeListRes = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                GoldRushPrizeTypeListRes goldRushPrizeTypeListRes = new GoldRushPrizeTypeListRes();
                goldRushPrizeTypeListRes.setName(dictApiRes.getItemValue());
                goldRushPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                GoldRushPrizeTypeListRes.add(goldRushPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",GoldRushPrizeTypeListRes);
    }
    /**
     * 删除核销授权
     * @param GoldRushAuthorityIdsReq
     * @return
     */
    @Override
    public ResponseDTO removeGoldRushAuthority(BusUser busUser, GoldRushAuthorityIdsReq GoldRushAuthorityIdsReq) {
        GoldRushMain GoldRushMain = goldRushMainService.selectById(GoldRushAuthorityIdsReq.getActId());
        if(CommonUtil.isNotEmpty(GoldRushMain)) {
            if (GoldRushMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new GoldRushException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new GoldRushException(ResponseEnums.GOLDRUSH_HAS5);
        }
        GoldRushAuthority GoldRushAuthority = new GoldRushAuthority();
        GoldRushAuthority.setState(1);
        goldRushAuthorityService.update(GoldRushAuthority,new EntityWrapper<GoldRushAuthority>().in("id",GoldRushAuthorityIdsReq.getIds()));
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
            GoldRushMain main = goldRushMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = goldRushCashPrizeApplyDAO.findExports(params);
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
            msg.put("message", "欢乐抢元宝活动记录导出excel失败！");
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
                    CommonUtil.isEmpty(map.get("nickname")) ? "未知用户" : map.get("nickname").toString(),
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
    private Font createFonts(Workbook wb, short bold, String fontName, boolean isItalic, short hight) {
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
     * @param value
     */
    private void createCell(Workbook wb, Row row, int column, String value, Font font) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    private String delWithColumn(Object obj) {
        if (CommonUtil.isEmpty(obj)) {
            return "";
        } else {
            return obj.toString();
        }
    }


}
