package com.gt.game.core.service.shakeLuck.impl;

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
import com.gt.game.core.bean.shakeLuck.req.*;
import com.gt.game.core.bean.shakeLuck.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.shakeLuck.ShakeluckCashPrizeApplyDAO;
import com.gt.game.core.dao.shakeLuck.ShakeluckMainDAO;
import com.gt.game.core.entity.shakeLuck.*;
import com.gt.game.core.exception.shakeLuck.ShakeluckException;
import com.gt.game.core.service.shakeLuck.*;
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
 * 摇手气主表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Service
public class ShakeluckServiceImpl  implements ShakeluckService {

    @Autowired
    ShakeluckAddressService shakeluckAddressService;

    @Autowired
    ShakeluckAdService shakeluckAdService;

    @Autowired
    ShakeluckAuthorityService shakeluckAuthorityService;

    @Autowired
    ShakeluckCashPrizeApplyService shakeluckCashPrizeApplyService;

    @Autowired
    ShakeluckMainService shakeluckMainService;

    @Autowired
    ShakeluckPlayRecordService shakeluckPlayRecordService;

    @Autowired
    ShakeluckPrizeService shakeluckPrizeService;

    @Autowired
    ShakeluckPrizeImgService shakeluckPrizeImgService;

    @Autowired
    ShakeluckMainDAO shakeluckMainDAO;

    @Autowired
    ShakeluckCashPrizeApplyDAO shakeluckCashPrizeApplyDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "shakeLuckMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
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
        String url = applyProperties.getMobileBaseUrl() + "shakeLuckMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }
    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<ShakeluckCountRes> getShakeluckCount(BusUser busUser) {
        ShakeluckCountRes ShakeluckCountRes = new ShakeluckCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = shakeluckMainDAO.getCount(params);
        ShakeluckCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        ShakeluckCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        ShakeluckCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        ShakeluckCountRes.setCount1(ShakeluckCountRes.getCount2()+ShakeluckCountRes.getCount3()+ShakeluckCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",ShakeluckCountRes);
    }
    /**
     * 分页获取活动列表
     *
     * @param ShakeluckListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<ShakeluckListRes>> getShakeluckList(BusUser busUser, ShakeluckListPageReq ShakeluckListPageReq) {
        Page<ShakeluckListRes> page = new Page<>(ShakeluckListPageReq.getCurrent(),ShakeluckListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",ShakeluckListPageReq.getStatus());
        map.put("name",ShakeluckListPageReq.getName());
        map.put("busId",busUser.getId());
        List<ShakeluckListRes> ShakeluckListResList = shakeluckMainDAO.getShakeluckList(page,map);
        for (ShakeluckListRes ShakeluckListRes : ShakeluckListResList){
            if(ShakeluckListRes.getActivityBeginTime().getTime() > date.getTime()){
                ShakeluckListRes.setIsEdit(1);
                ShakeluckListRes.setStatus(0);
            }else if(ShakeluckListRes.getActivityEndTime().getTime() < date.getTime()){
                ShakeluckListRes.setIsEdit(0);
                ShakeluckListRes.setStatus(2);
            }else {
                ShakeluckListRes.setIsEdit(0);
                ShakeluckListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",ShakeluckListResList,pageDTO);
    }
    /**
     * 分页获取中奖记录列表
     * @param ShakeluckApplyListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<ShakeluckApplyListRes>> getShakeluckApplyList(BusUser busUser, ShakeluckApplyListPageReq ShakeluckApplyListPageReq) {
        Page<ShakeluckApplyListRes> page = new Page<>(ShakeluckApplyListPageReq.getCurrent(),ShakeluckApplyListPageReq.getSize());
        List<ShakeluckApplyListRes> ShakeluckApplyListResList = shakeluckCashPrizeApplyDAO.queryRecodList(page,ShakeluckApplyListPageReq);
        if(ShakeluckApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < ShakeluckApplyListResList.size(); i++) {
                ids.append(ShakeluckApplyListResList.get(i).getMemberId());
                if (ShakeluckApplyListResList.size() > 1 && i < ShakeluckApplyListResList.size() - 1) {
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
            for (int i = 0; i < ShakeluckApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == ShakeluckApplyListResList.get(i).getMemberId().intValue()) {
                        ShakeluckApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(ShakeluckApplyListResList.get(i).getNickname())) {
                    ShakeluckApplyListResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",ShakeluckApplyListResList,pageDTO);
    }
    /**
     * 中奖记录发放奖品
     * @param ShakeluckApplyIdReq
     * @return
     */
    @Override
    public ResponseDTO editShakeluckApply(BusUser busUser, ShakeluckApplyIdReq ShakeluckApplyIdReq) {
        ShakeluckCashPrizeApply ShakeluckCashPrizeApply = shakeluckCashPrizeApplyService.selectById(ShakeluckApplyIdReq.getId());
        if(CommonUtil.isNotEmpty(ShakeluckCashPrizeApply)){
            ShakeluckMain main = shakeluckMainService.selectById(ShakeluckCashPrizeApply.getActId());
            ShakeluckPrize ShakeluckPrize = shakeluckPrizeService.selectById(ShakeluckCashPrizeApply.getPrizeId());
            if(ShakeluckPrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(main.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new ShakeluckException(ResponseEnums.SHAKELUCK_HAS3);
                } else if (!DateTimeKit.laterThanNow(main.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new ShakeluckException(ResponseEnums.SHAKELUCK_HAS4);
                }
            }
            if (ShakeluckCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                ShakeluckCashPrizeApply.setStatus(2);
                ShakeluckCashPrizeApply.setCashTime(new Date());
                shakeluckCashPrizeApplyService.updateById(ShakeluckCashPrizeApply);
            } else if (ShakeluckCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new ShakeluckException(ResponseEnums.SHAKELUCK_HAS1);
            }else{//还未提交
                throw  new ShakeluckException(ResponseEnums.SHAKELUCK_HAS2);
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
    public ResponseDTO<ShakeluckRes> getShakeluck(BusUser busUser, Integer id) {
        ShakeluckMain ShakeluckMain = shakeluckMainService.selectById(id);
        ShakeluckRes ShakeluckRes = new ShakeluckRes();
        if (CommonUtil.isNotEmpty(ShakeluckMain)) {
            BeanUtils.copyProperties(ShakeluckMain, ShakeluckRes);
            //奖品
            List<ShakeluckPrize> ShakeluckPrizes = shakeluckPrizeService.selectList(new EntityWrapper<ShakeluckPrize>().eq("act_id", id));
            List<ShakeluckPrizeReq> ShakeluckPrizeReqs = new ArrayList<>();
            if (ShakeluckPrizes.size() > 0) {
                for (ShakeluckPrize ShakeluckPrize : ShakeluckPrizes) {
                    ShakeluckPrizeReq ShakeluckPrizeReq = new ShakeluckPrizeReq();
                    BeanUtils.copyProperties(ShakeluckPrize, ShakeluckPrizeReq);
                    List<ShakeluckPrizeImg> ShakeluckPrizeImgs = shakeluckPrizeImgService.selectList(new EntityWrapper<ShakeluckPrizeImg>().eq("prize_id", ShakeluckPrize.getId()));
                    List<ShakeluckPrizeImgReq> ShakeluckPrizeImgReqs = new ArrayList<>();
                    for (ShakeluckPrizeImg ShakeluckPrizeImg : ShakeluckPrizeImgs) {
                        ShakeluckPrizeImgReq ShakeluckPrizeImgReq = new ShakeluckPrizeImgReq();
                        BeanUtils.copyProperties(ShakeluckPrizeImg, ShakeluckPrizeImgReq);
                        ShakeluckPrizeImgReqs.add(ShakeluckPrizeImgReq);
                    }
                    ShakeluckPrizeReq.setShakeluckPrizeImgReqs(ShakeluckPrizeImgReqs);
                    ShakeluckPrizeReqs.add(ShakeluckPrizeReq);
                }
            }
            ShakeluckRes.setShakeluckPrizeReqs(ShakeluckPrizeReqs);
            //兑奖地址
            List<ShakeluckAddress> ShakeluckAddresses = shakeluckAddressService.selectList(new EntityWrapper<ShakeluckAddress>().eq("act_id", id));
            List<ShakeluckAddressReq> ShakeluckAddressReqs = new ArrayList<>();
            if (ShakeluckAddresses.size() > 0) {
                for (ShakeluckAddress ShakeluckAddress : ShakeluckAddresses) {
                    ShakeluckAddressReq ShakeluckAddressReq = new ShakeluckAddressReq();
                    BeanUtils.copyProperties(ShakeluckAddress, ShakeluckAddressReq);
                    ShakeluckAddressReqs.add(ShakeluckAddressReq);
                }
            }
            ShakeluckRes.setShakeluckAddressReqs(ShakeluckAddressReqs);
            //广告轮播图
            List<ShakeluckAd> ShakeluckAds = shakeluckAdService.selectList(new EntityWrapper<ShakeluckAd>().eq("act_id", id));
            List<ShakeluckAdReq> ShakeluckAdReqs = new ArrayList<>();
            if (ShakeluckAds.size() > 0) {
                for (ShakeluckAd ShakeluckAd : ShakeluckAds) {
                    ShakeluckAdReq ShakeluckAdReq = new ShakeluckAdReq();
                    BeanUtils.copyProperties(ShakeluckAd, ShakeluckAdReq);
                    ShakeluckAdReqs.add(ShakeluckAdReq);
                }
            }
            ShakeluckRes.setShakeluckAdReqs(ShakeluckAdReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",ShakeluckRes);
    }
    /**
     * 删除活动
     * @param ShakeluckIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeShakeluck(BusUser busUser, ShakeluckIdReq ShakeluckIdReq) {
        ShakeluckMain ShakeluckMain = shakeluckMainService.selectById(ShakeluckIdReq.getId());
        if(CommonUtil.isNotEmpty(ShakeluckMain)){
            if(ShakeluckMain.getActivityBeginTime().getTime() < new Date().getTime() && ShakeluckMain.getActivityEndTime().getTime() > new Date().getTime()){
                throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS10);
            }
            if(ShakeluckMain.getCashPrizeBeginTime().getTime() < new Date().getTime() && ShakeluckMain.getCashPrizeEndTime().getTime() > new Date().getTime()){
                throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS12);
            }
            List<ShakeluckCashPrizeApply> ShakeluckCashPrizeApplies = shakeluckCashPrizeApplyService.selectList(
                    new EntityWrapper<ShakeluckCashPrizeApply>().eq("act_id",ShakeluckIdReq.getId()).eq("status",3));
            if(ShakeluckCashPrizeApplies.size() > 0 ){
                throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS11);

            }
            if(ShakeluckMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new ShakeluckException(ResponseEnums.DIFF_USER);
            }
            shakeluckMainService.deleteById(ShakeluckMain.getId());
            //兑奖地址
            shakeluckAddressService.delete(new EntityWrapper<ShakeluckAddress>().eq("act_id",ShakeluckMain.getId()));
            //广告轮播图
            shakeluckAdService.delete(new EntityWrapper<ShakeluckAd>().eq("act_id",ShakeluckMain.getId()));
            //奖品
            List<ShakeluckPrize> ShakeluckPrizes = shakeluckPrizeService.selectList(new EntityWrapper<ShakeluckPrize>().eq("act_id",ShakeluckMain.getId()));
            boolean ff = false;
            if(ShakeluckPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(ShakeluckPrize ShakeluckPrize : ShakeluckPrizes){
                    ids.add(ShakeluckPrize.getId());
                    if(ShakeluckPrize.getType() == 1){
                        ff = true;
                    }
                }
                shakeluckPrizeImgService.delete(new EntityWrapper<ShakeluckPrizeImg>().in("prize_id",ids));
            }
            shakeluckPrizeService.delete(new EntityWrapper<ShakeluckPrize>().eq("act_id",ShakeluckMain.getId()));
            shakeluckAuthorityService.delete(new EntityWrapper<ShakeluckAuthority>().eq("act_id",ShakeluckMain.getId()));
            shakeluckCashPrizeApplyService.delete(new EntityWrapper<ShakeluckCashPrizeApply>().eq("act_id",ShakeluckMain.getId()));
            shakeluckPlayRecordService.delete(new EntityWrapper<ShakeluckPlayRecord>().eq("act_id",ShakeluckMain.getId()));
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(ShakeluckMain.getId());
                fenbiSurplus.setFre_type(40);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(ShakeluckMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(40);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 保存活动
     * @param ShakeluckSaveReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveShakeluck(BusUser busUser, ShakeluckSaveReq ShakeluckSaveReq) {
        ShakeluckMain ShakeluckMain = null;
        Double num = 0.0;
        int f = 0;
        if(ShakeluckSaveReq.getId() == 0){//新增
            ShakeluckMain = new ShakeluckMain();
            BeanUtils.copyProperties(ShakeluckSaveReq,ShakeluckMain);
            ShakeluckMain.setBusId(busUser.getId());
            ShakeluckMain.setCreatetime(new Date());
            ShakeluckMain.setFollowQrCode(ShakeluckMain.getFollowQrCode().split("/upload").length > 1
                    ?ShakeluckMain.getFollowQrCode().split("/upload")[1]:ShakeluckMain.getFollowQrCode());
            shakeluckMainService.insert(ShakeluckMain);
        }else{//编辑
            ShakeluckMain = shakeluckMainService.selectById(ShakeluckSaveReq.getId());
            if(CommonUtil.isEmpty(ShakeluckMain)){
                throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS5);
            }
            if(ShakeluckMain.getActivityBeginTime().getTime() < new Date().getTime()){
                throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS6);
            }
            if(ShakeluckMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new ShakeluckException(ResponseEnums.DIFF_USER);
            }
            BeanUtils.copyProperties(ShakeluckSaveReq,ShakeluckMain);
            ShakeluckMain.setFollowQrCode(ShakeluckMain.getFollowQrCode().split("/upload").length > 1
                    ?ShakeluckMain.getFollowQrCode().split("/upload")[1]:ShakeluckMain.getFollowQrCode());
            shakeluckMainService.updateById(ShakeluckMain);
            //删除
            //兑奖地址
            shakeluckAddressService.delete(new EntityWrapper<ShakeluckAddress>().eq("act_id",ShakeluckMain.getId()));
            //广告轮播图
            shakeluckAdService.delete(new EntityWrapper<ShakeluckAd>().eq("act_id",ShakeluckMain.getId()));
            //奖品
            List<ShakeluckPrize> ShakeluckPrizes = shakeluckPrizeService.selectList(new EntityWrapper<ShakeluckPrize>().eq("act_id",ShakeluckMain.getId()));
            if(ShakeluckPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(ShakeluckPrize ShakeluckPrize : ShakeluckPrizes){
                    if(ShakeluckPrize.getType() == 1){
                        num += ShakeluckPrize.getNum();
                        f = 1;
                    }
                    ids.add(ShakeluckPrize.getId());
                }
                shakeluckPrizeImgService.delete(new EntityWrapper<ShakeluckPrizeImg>().in("prize_id",ids));
            }
            shakeluckPrizeService.delete(new EntityWrapper<ShakeluckPrize>().eq("act_id",ShakeluckMain.getId()));
        }
        Double fenbi = 0.0;
        //奖品
        if(CommonUtil.isNotEmpty(ShakeluckSaveReq.getShakeluckPrizeReqs())){
            for(ShakeluckPrizeReq ShakeluckPrizeReq :ShakeluckSaveReq.getShakeluckPrizeReqs()){
                if(ShakeluckPrizeReq.getType()==1){
                    fenbi += ShakeluckPrizeReq.getNum();
                }
                ShakeluckPrize ShakeluckPrize = new ShakeluckPrize();
                BeanUtils.copyProperties(ShakeluckPrizeReq,ShakeluckPrize);
                ShakeluckPrize.setActId(ShakeluckMain.getId());
                shakeluckPrizeService.insert(ShakeluckPrize);
                if(ShakeluckPrizeReq.getShakeluckPrizeImgReqs().size() > 0){
                    for(ShakeluckPrizeImgReq ShakeluckPrizeImgReq : ShakeluckPrizeReq.getShakeluckPrizeImgReqs()){
                        ShakeluckPrizeImg ShakeluckPrizeImg = new ShakeluckPrizeImg();
                        BeanUtils.copyProperties(ShakeluckPrizeImgReq,ShakeluckPrizeImg);
                        ShakeluckPrizeImg.setPrizeId(ShakeluckPrize.getId());
                        ShakeluckPrizeImg.setImgUrl(ShakeluckPrizeImg.getImgUrl().split("/upload").length>1?
                                ShakeluckPrizeImg.getImgUrl().split("/upload")[1]:ShakeluckPrizeImg.getImgUrl());
                        shakeluckPrizeImgService.insert(ShakeluckPrizeImg);
                    }
                }
            }
        }
        //兑奖地址
        if(CommonUtil.isNotEmpty(ShakeluckSaveReq.getShakeluckAddressReqs())){
            for(ShakeluckAddressReq ShakeluckAddressReq :ShakeluckSaveReq.getShakeluckAddressReqs()){
                ShakeluckAddress ShakeluckAddress = new ShakeluckAddress();
                BeanUtils.copyProperties(ShakeluckAddressReq,ShakeluckAddress);
                ShakeluckAddress.setActId(ShakeluckMain.getId());
                shakeluckAddressService.insert(ShakeluckAddress);
            }
        }
        //广告轮播图
        if(CommonUtil.isNotEmpty(ShakeluckSaveReq.getShakeluckAdReqs())){
            for(ShakeluckAdReq ShakeluckAdReq :ShakeluckSaveReq.getShakeluckAdReqs()){
                ShakeluckAd ShakeluckAd = new ShakeluckAd();
                BeanUtils.copyProperties(ShakeluckAdReq,ShakeluckAd);
                ShakeluckAd.setActId(ShakeluckMain.getId());
                ShakeluckAd.setUrl(ShakeluckAd.getUrl().split("/upload").length>1?
                        ShakeluckAd.getUrl().split("/upload")[1]:ShakeluckAd.getUrl());
                shakeluckAdService.insert(ShakeluckAd);
            }
        }
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS14);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(ShakeluckMain.getId());
                updateFenbiReduceReq.setFreType(40);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS15);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, ShakeluckMain.getId(), 40, 1, "摇手气活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS8);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    /**
     * 分页获取核销授权
     * @param ShakeluckAuthorityListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<ShakeluckAuthorityListRes>> getShakeluckAuthorityList(BusUser busUser, ShakeluckAuthorityListPageReq ShakeluckAuthorityListPageReq) {
        Page<ShakeluckAuthority> page = new Page<>(ShakeluckAuthorityListPageReq.getCurrent(),ShakeluckAuthorityListPageReq.getSize());
        List<ShakeluckAuthority> ShakeluckAuthorityList = shakeluckAuthorityService.selectPage(page,
                new EntityWrapper<ShakeluckAuthority>().eq("act_id",ShakeluckAuthorityListPageReq.getActId())
                        .eq("delete_status",0)).getRecords();
        List<ShakeluckAuthorityListRes> ShakeluckAuthorityListRes = new ArrayList<>();
        for (ShakeluckAuthority ShakeluckAuthority : ShakeluckAuthorityList){
            ShakeluckAuthorityListRes ShakeluckAuthorityListRes1 = new ShakeluckAuthorityListRes();
            BeanUtils.copyProperties(ShakeluckAuthority,ShakeluckAuthorityListRes1);
            ShakeluckAuthorityListRes.add(ShakeluckAuthorityListRes1);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",ShakeluckAuthorityListRes,pageDTO);
    }
    /**
     * 获取奖品类型列表
     *
     */
    @Override
    public ResponseDTO<List<ShakeluckPrizeTypeListRes>> getShakeluckPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<ShakeluckPrizeTypeListRes> ShakeluckPrizeTypeListRes = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                ShakeluckPrizeTypeListRes shakeluckPrizeTypeListRes = new ShakeluckPrizeTypeListRes();
                shakeluckPrizeTypeListRes.setName(dictApiRes.getItemValue());
                shakeluckPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                ShakeluckPrizeTypeListRes.add(shakeluckPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",ShakeluckPrizeTypeListRes);
    }
    /**
     * 删除核销授权
     * @param ShakeluckAuthorityIdsReq
     * @return
     */
    @Override
    public ResponseDTO removeShakeluckAuthority(BusUser busUser, ShakeluckAuthorityIdsReq ShakeluckAuthorityIdsReq) {
        ShakeluckMain ShakeluckMain = shakeluckMainService.selectById(ShakeluckAuthorityIdsReq.getActId());
        if(CommonUtil.isNotEmpty(ShakeluckMain)) {
            if (ShakeluckMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new ShakeluckException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new ShakeluckException(ResponseEnums.SHAKELUCK_HAS5);
        }
        ShakeluckAuthority ShakeluckAuthority = new ShakeluckAuthority();
        ShakeluckAuthority.setDeleteStatus(1);
        shakeluckAuthorityService.update(ShakeluckAuthority,new EntityWrapper<ShakeluckAuthority>().in("id",ShakeluckAuthorityIdsReq.getIds()));
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
            ShakeluckMain main = shakeluckMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = shakeluckCashPrizeApplyDAO.findExports(params);
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
            msg.put("message", "摇手气活动记录导出excel失败！");
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
                if ("6".equals(turPriType)) {
                    priTypeName = "积分";
                    priTypeUnit = "分";
                }
                if ("7".equals(turPriType)) {
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
