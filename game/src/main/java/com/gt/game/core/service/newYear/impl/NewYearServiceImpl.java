package com.gt.game.core.service.newYear.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.axis.bean.wxmp.bus.BusUser;
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
import com.gt.game.core.bean.newYear.req.*;
import com.gt.game.core.bean.newYear.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.newYear.NewYearGameCashPrizeApplyDAO;
import com.gt.game.core.dao.newYear.NewYearGameMainDAO;
import com.gt.game.core.dao.newYear.NewYearGamePrizeDAO;
import com.gt.game.core.entity.newYear.*;
import com.gt.game.core.exception.newYear.NewYearException;
import com.gt.game.core.service.newYear.*;
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
 * 元旦游戏主表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Service
public class NewYearServiceImpl implements NewYearService {

    @Autowired
    NewYearGamePrizeImgService newYearGamePrizeImgService;

    @Autowired
    NewYearGamePrizeService newYearGamePrizeService;

    @Autowired
    NewYearGameAddressService newYearGameAddressService;

    @Autowired
    NewYearGameAdService newYearGameAdService;

    @Autowired
    NewYearGameMainService newYearGameMainService;

    @Autowired
    NewYearGameCashPrizeApplyService newYearGameCashPrizeApplyService;

    @Autowired
    NewYearGameReportcardService newYearGameReportcardService;

    @Autowired
    NewYearGameAuthorityService newYearGameAuthorityService;

    @Autowired
    NewYearGamePlayRecordService newYearGamePlayRecordService;

    @Autowired
    NewYearGameCashPrizeApplyDAO newYearGameCashPrizeApplyDAO;

    @Autowired
    NewYearGameMainDAO newYearGameMainDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "newYearGameMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
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
        String url = applyProperties.getMobileBaseUrl() + "newYearGameMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }

    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<NewYearCountRes> getNewYearCount(BusUser busUser) {
        NewYearCountRes newYearCountRes = new NewYearCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = newYearGameMainDAO.getCount(params);
        newYearCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        newYearCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        newYearCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        newYearCountRes.setCount1(newYearCountRes.getCount2()+newYearCountRes.getCount3()+newYearCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",newYearCountRes);
    }
    /**
     * 分页获取活动列表
     * @return
     */
    @Override
    public ResponseDTO<List<NewYearListRes>> getNewYearList(BusUser busUser, NewYearListPageReq newYearListPageReq) {
        Page<NewYearListRes> page = new Page<>(newYearListPageReq.getCurrent(),newYearListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",newYearListPageReq.getStatus());
        map.put("name",newYearListPageReq.getName());
        map.put("busId",busUser.getId());
        List<NewYearListRes> newYearListResList = newYearGameMainDAO.getNewYearList(page,map);
        for (NewYearListRes newYearListRes : newYearListResList){
            if(newYearListRes.getActivityBeginTime().getTime() > date.getTime()){
                newYearListRes.setIsEdit(1);
                newYearListRes.setStatus(0);
            }else if(newYearListRes.getActivityEndTime().getTime() < date.getTime()){
                newYearListRes.setIsEdit(0);
                newYearListRes.setStatus(2);
            }else {
                newYearListRes.setIsEdit(0);
                newYearListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",newYearListResList,pageDTO);
    }
    /**
     * 分页获取中奖记录列表
     * @return
     */
    @Override
    public ResponseDTO<List<NewYearApplyListRes>> getNewYearApplyList(BusUser busUser, NewYearApplyListPageReq newYearApplyListPageReq) {
        Page<NewYearApplyListRes> page = new Page<>(newYearApplyListPageReq.getCurrent(),newYearApplyListPageReq.getSize());
        List<NewYearApplyListRes> newYearApplyListResList = newYearGameCashPrizeApplyDAO.queryRecodList(page,newYearApplyListPageReq);
        if(newYearApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < newYearApplyListResList.size(); i++) {
                ids.append(newYearApplyListResList.get(i).getMemberId());
                if (newYearApplyListResList.size() > 1 && i < newYearApplyListResList.size() - 1) {
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
            for (int i = 0; i < newYearApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == newYearApplyListResList.get(i).getMemberId().intValue()) {
                        newYearApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(newYearApplyListResList.get(i).getMemberName())) {
                    newYearApplyListResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",newYearApplyListResList,pageDTO);
    }
    /**
     * 中奖记录发放奖品
     * @param newYearApplyIdReq
     * @return
     */
    @Override
    public ResponseDTO editNewYearApply(BusUser busUser, NewYearApplyIdReq newYearApplyIdReq) {
        NewYearGameCashPrizeApply newYearGameCashPrizeApply = newYearGameCashPrizeApplyService.selectById(newYearApplyIdReq.getId());
        if(CommonUtil.isNotEmpty(newYearGameCashPrizeApply)){
            NewYearGameMain newYearGameMain = newYearGameMainService.selectById(newYearGameCashPrizeApply.getMainId());
            NewYearGamePrize newYearGamePrize = newYearGamePrizeService.selectById(newYearGameCashPrizeApply.getPrizeId());
            if(newYearGamePrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(newYearGameMain.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new NewYearException(ResponseEnums.NEWYEAR_HAS3);
                } else if (!DateTimeKit.laterThanNow(newYearGameMain.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new NewYearException(ResponseEnums.NEWYEAR_HAS4);
                }
            }
            if (newYearGameCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                newYearGameCashPrizeApply.setStatus(2);
                newYearGameCashPrizeApply.setCashTime(new Date());
                newYearGameCashPrizeApplyService.updateById(newYearGameCashPrizeApply);
            } else if (newYearGameCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new NewYearException(ResponseEnums.NEWYEAR_HAS1);
            }else{//还未提交
                throw  new NewYearException(ResponseEnums.NEWYEAR_HAS2);
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
    public ResponseDTO<NewYearRes> getNewYear(BusUser busUser, Integer id) {
        NewYearGameMain NewYearGameMain = newYearGameMainService.selectById(id);
        NewYearRes newYearRes = new NewYearRes();
        if (CommonUtil.isNotEmpty(NewYearGameMain)) {
            BeanUtils.copyProperties(NewYearGameMain, newYearRes);
            //奖品
            List<NewYearGamePrize> NewYearGamePrizes = newYearGamePrizeService.selectList(new EntityWrapper<NewYearGamePrize>().eq("main_id", id));
            List<NewYearPrizeReq> newYearPrizeReqs = new ArrayList<>();
            if (NewYearGamePrizes.size() > 0) {
                for (NewYearGamePrize NewYearGamePrize : NewYearGamePrizes) {
                    NewYearPrizeReq newYearPrizeReq = new NewYearPrizeReq();
                    BeanUtils.copyProperties(NewYearGamePrize, newYearPrizeReq);
                    List<NewYearGamePrizeImg> newYearGamePrizeImgList = newYearGamePrizeImgService.selectList(new EntityWrapper<NewYearGamePrizeImg>().eq("prize_id", NewYearGamePrize.getId()));
                    List<NewYearPrizeImgReq> newYearPrizeImgReqs = new ArrayList<>();
                    for (NewYearGamePrizeImg newYearGamePrizeImg : newYearGamePrizeImgList) {
                        NewYearPrizeImgReq newYearPrizeImgReq = new NewYearPrizeImgReq();
                        BeanUtils.copyProperties(newYearGamePrizeImg, newYearPrizeImgReq);
                        newYearPrizeImgReqs.add(newYearPrizeImgReq);
                    }
                    newYearPrizeReq.setNewYearPrizeImgReqs(newYearPrizeImgReqs);
                    newYearPrizeReqs.add(newYearPrizeReq);
                }
            }
            newYearRes.setNewYearPrizeReqs(newYearPrizeReqs);
            //兑奖地址
            List<NewYearGameAddress> newYearGameAddressList = newYearGameAddressService.selectList(new EntityWrapper<NewYearGameAddress>().eq("main_id", id));
            List<NewYearAddressReq> newYearAddressReqs = new ArrayList<>();
            if (newYearGameAddressList.size() > 0) {
                for (NewYearGameAddress newYearGameAddress : newYearGameAddressList) {
                    NewYearAddressReq newYearAddressReq = new NewYearAddressReq();
                    BeanUtils.copyProperties(newYearGameAddress, newYearAddressReq);
                    newYearAddressReqs.add(newYearAddressReq);
                }
            }
            newYearRes.setNewYearAddressReqs(newYearAddressReqs);
            //广告轮播图
            List<NewYearGameAd> newYearGameAds = newYearGameAdService.selectList(new EntityWrapper<NewYearGameAd>().eq("act_id", id));
            List<NewYearAdReq> newYearAdReqs = new ArrayList<>();
            if (newYearGameAds.size() > 0) {
                for (NewYearGameAd newYearGameAd : newYearGameAds) {
                    NewYearAdReq newYearAdReq = new NewYearAdReq();
                    BeanUtils.copyProperties(newYearGameAd, newYearAdReq);
                    newYearAdReqs.add(newYearAdReq);
                }
            }
            newYearRes.setNewYearAdReqs(newYearAdReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",newYearRes);
    }
    /**
     * 删除活动
     * @param newYearIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeNewYear(BusUser busUser, NewYearIdReq newYearIdReq) {
        NewYearGameMain NewYearGameMain = newYearGameMainService.selectById(newYearIdReq.getId());
        if(CommonUtil.isNotEmpty(NewYearGameMain)){
            if(NewYearGameMain.getActivityBeginTime().getTime() < new Date().getTime() && NewYearGameMain.getActivityEndTime().getTime() > new Date().getTime()){
                throw new NewYearException(ResponseEnums.NEWYEAR_HAS10);
            }
            if(NewYearGameMain.getCashPrizeBeginTime().getTime() < new Date().getTime() && NewYearGameMain.getCashPrizeEndTime().getTime() > new Date().getTime()){
                throw new NewYearException(ResponseEnums.NEWYEAR_HAS12);
            }
            List<NewYearGameCashPrizeApply> NewYearGameCashPrizeApplies = newYearGameCashPrizeApplyService.selectList(
                    new EntityWrapper<NewYearGameCashPrizeApply>().eq("main_id",newYearIdReq.getId()).eq("status",3));
            if(NewYearGameCashPrizeApplies.size() > 0 ){
                throw new NewYearException(ResponseEnums.NEWYEAR_HAS11);

            }
            if(NewYearGameMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new NewYearException(ResponseEnums.DIFF_USER);
            }
            newYearGameMainService.deleteById(NewYearGameMain.getId());
            //兑奖地址
            newYearGameAddressService.delete(new EntityWrapper<NewYearGameAddress>().eq("main_id",NewYearGameMain.getId()));
            //广告轮播图
            newYearGameAdService.delete(new EntityWrapper<NewYearGameAd>().eq("act_id",NewYearGameMain.getId()));
            //奖品
            List<NewYearGamePrize> NewYearGamePrizes = newYearGamePrizeService.selectList(new EntityWrapper<NewYearGamePrize>().eq("main_id",NewYearGameMain.getId()));
            boolean ff = false;
            if(NewYearGamePrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(NewYearGamePrize NewYearGamePrize : NewYearGamePrizes){
                    ids.add(NewYearGamePrize.getId());
                    if(NewYearGamePrize.getType() == 1){
                        ff = true;
                    }
                }
                newYearGamePrizeImgService.delete(new EntityWrapper<NewYearGamePrizeImg>().in("prize_id",ids));
            }
            newYearGamePrizeService.delete(new EntityWrapper<NewYearGamePrize>().eq("main_id",NewYearGameMain.getId()));
            newYearGameAuthorityService.delete(new EntityWrapper<NewYearGameAuthority>().eq("main_id",NewYearGameMain.getId()));
            newYearGameCashPrizeApplyService.delete(new EntityWrapper<NewYearGameCashPrizeApply>().eq("main_id",NewYearGameMain.getId()));
            newYearGamePlayRecordService.delete(new EntityWrapper<NewYearGamePlayRecord>().eq("main_id",NewYearGameMain.getId()));
            newYearGameReportcardService.delete(new EntityWrapper<NewYearGameReportcard>().eq("act_id",NewYearGameMain.getId()));
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(NewYearGameMain.getId());
                fenbiSurplus.setFre_type(37);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(NewYearGameMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(37);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new NewYearException(ResponseEnums.NEWYEAR_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 保存活动
     * @param newYearSaveReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveNewYear(BusUser busUser, NewYearSaveReq newYearSaveReq) {
        NewYearGameMain newYearGameMain = null;
        Double num = 0.0;
        int f = 0;
        if(newYearSaveReq.getId() == 0){//新增
            newYearGameMain = new NewYearGameMain();
            BeanUtils.copyProperties(newYearSaveReq,newYearGameMain);
            newYearGameMain.setBusId(busUser.getId());
            newYearGameMain.setCreatetime(new Date());
            newYearGameMain.setFollowQrCode(newYearGameMain.getFollowQrCode().split("upload/").length > 1
                    ?newYearGameMain.getFollowQrCode().split("upload/")[1]:newYearGameMain.getFollowQrCode());
            newYearGameMainService.insert(newYearGameMain);
        }else{//编辑
            newYearGameMain = newYearGameMainService.selectById(newYearSaveReq.getId());
            if(CommonUtil.isEmpty(newYearGameMain)){
                throw new NewYearException(ResponseEnums.NEWYEAR_HAS5);
            }
            if(newYearGameMain.getActivityBeginTime().getTime() < new Date().getTime()){
                throw new NewYearException(ResponseEnums.NEWYEAR_HAS6);
            }
            if(newYearGameMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new NewYearException(ResponseEnums.DIFF_USER);
            }
            BeanUtils.copyProperties(newYearSaveReq,newYearGameMain);
            newYearGameMain.setFollowQrCode(newYearGameMain.getFollowQrCode().split("upload/").length > 1
                    ?newYearGameMain.getFollowQrCode().split("upload/")[1]:newYearGameMain.getFollowQrCode());
            newYearGameMainService.updateById(newYearGameMain);
            //删除
            //兑奖地址
            newYearGameAddressService.delete(new EntityWrapper<NewYearGameAddress>().eq("main_id",newYearGameMain.getId()));
            //广告轮播图
            newYearGameAdService.delete(new EntityWrapper<NewYearGameAd>().eq("act_id",newYearGameMain.getId()));
            //奖品
            List<NewYearGamePrize> NewYearGamePrizes = newYearGamePrizeService.selectList(new EntityWrapper<NewYearGamePrize>().eq("main_id",newYearGameMain.getId()));
            if(NewYearGamePrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(NewYearGamePrize NewYearGamePrize : NewYearGamePrizes){
                    if(NewYearGamePrize.getType() == 1){
                        num += NewYearGamePrize.getNum();
                        f = 1;
                    }
                    ids.add(NewYearGamePrize.getId());
                }
                newYearGamePrizeImgService.delete(new EntityWrapper<NewYearGamePrizeImg>().in("prize_id",ids));
            }
            newYearGamePrizeService.delete(new EntityWrapper<NewYearGamePrize>().eq("main_id",newYearGameMain.getId()));
        }
        Double fenbi = 0.0;
        //奖品
        if(CommonUtil.isNotEmpty(newYearSaveReq.getNewYearPrizeReqs())){
            for(NewYearPrizeReq newYearPrizeReq :newYearSaveReq.getNewYearPrizeReqs()){
                if(newYearPrizeReq.getType()==1){
                    fenbi += newYearPrizeReq.getNum();
                }
                NewYearGamePrize newYearGamePrize = new NewYearGamePrize();
                BeanUtils.copyProperties(newYearPrizeReq,newYearGamePrize);
                newYearGamePrize.setMainId(newYearGameMain.getId());
                newYearGamePrizeService.insert(newYearGamePrize);
                if(newYearPrizeReq.getNewYearPrizeImgReqs().size() > 0){
                    for(NewYearPrizeImgReq newYearPrizeImgReq : newYearPrizeReq.getNewYearPrizeImgReqs()){
                        NewYearGamePrizeImg newYearGamePrizeImg = new NewYearGamePrizeImg();
                        BeanUtils.copyProperties(newYearPrizeImgReq,newYearGamePrizeImg);
                        newYearGamePrizeImg.setPrizeId(newYearGamePrize.getId());
                        newYearGamePrizeImg.setImgUrl(newYearGamePrizeImg.getImgUrl().split("upload/").length>1?
                                newYearGamePrizeImg.getImgUrl().split("upload/")[1]:newYearGamePrizeImg.getImgUrl());
                        newYearGamePrizeImgService.insert(newYearGamePrizeImg);
                    }
                }
            }
        }
        //兑奖地址
        if(CommonUtil.isNotEmpty(newYearSaveReq.getNewYearAddressReqs())){
            for(NewYearAddressReq newYearAddressReq :newYearSaveReq.getNewYearAddressReqs()){
                NewYearGameAddress newYearGameAddress = new NewYearGameAddress();
                BeanUtils.copyProperties(newYearAddressReq,newYearGameAddress);
                newYearGameAddress.setMainId(newYearGameMain.getId());
                newYearGameAddressService.insert(newYearGameAddress);
            }
        }
        //广告轮播图
        if(CommonUtil.isNotEmpty(newYearSaveReq.getNewYearAdReqs())){
            for(NewYearAdReq newYearAdReq :newYearSaveReq.getNewYearAdReqs()){
                NewYearGameAd newYearGameAd = new NewYearGameAd();
                BeanUtils.copyProperties(newYearAdReq,newYearGameAd);
                newYearGameAd.setActId(newYearGameMain.getId());
                newYearGameAd.setUrl(newYearGameAd.getUrl().split("upload/").length>1?
                        newYearGameAd.getUrl().split("upload/")[1]:newYearGameAd.getUrl());
                newYearGameAdService.insert(newYearGameAd);
            }
        }
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new NewYearException(ResponseEnums.NEWYEAR_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new NewYearException(ResponseEnums.NEWYEAR_HAS7);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(newYearGameMain.getId());
                updateFenbiReduceReq.setFreType(37);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new NewYearException(ResponseEnums.NEWYEAR_HAS8);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new NewYearException(ResponseEnums.NEWYEAR_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, newYearGameMain.getId(), 37, 1, "元旦跨年跳跃活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new NewYearException(ResponseEnums.NEWYEAR_HAS8);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    /**
     * 分页获取核销授权
     * @param newYearAuthorityListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<NewYearAuthorityListRes>> getNewYearAuthorityList(BusUser busUser, NewYearAuthorityListPageReq newYearAuthorityListPageReq) {
        Page<NewYearGameAuthority> page = new Page<>(newYearAuthorityListPageReq.getCurrent(),newYearAuthorityListPageReq.getSize());
        List<NewYearGameAuthority> NewYearGameAuthorityList = newYearGameAuthorityService.selectPage(page,
                new EntityWrapper<NewYearGameAuthority>().eq("main_id",newYearAuthorityListPageReq.getActId())
                        .eq("state",0)).getRecords();
        List<NewYearAuthorityListRes> NewYearAuthorityListRes = new ArrayList<>();
        for (NewYearGameAuthority NewYearGameAuthority : NewYearGameAuthorityList){
            NewYearAuthorityListRes NewYearAuthorityListRes1 = new NewYearAuthorityListRes();
            BeanUtils.copyProperties(NewYearGameAuthority,NewYearAuthorityListRes1);
            NewYearAuthorityListRes1.setMemberName(NewYearGameAuthority.getName());
            NewYearAuthorityListRes.add(NewYearAuthorityListRes1);
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",NewYearAuthorityListRes,pageDTO);
    }
    /**
     * 获取奖品类型列表
     *
     */
    @Override
    public ResponseDTO<List<NewYearPrizeTypeListRes>> getNewYearPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<NewYearPrizeTypeListRes> NewYearPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                NewYearPrizeTypeListRes NewYearPrizeTypeListRes = new NewYearPrizeTypeListRes();
                NewYearPrizeTypeListRes.setName(dictApiRes.getItemValue());
                NewYearPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                NewYearPrizeTypeListResList.add(NewYearPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",NewYearPrizeTypeListResList);
    }
    /**
     * 删除核销授权
     * @param newYearAuthorityIdsReq
     * @return
     */
    @Override
    public ResponseDTO removeNewYearAuthority(BusUser busUser, NewYearAuthorityIdsReq newYearAuthorityIdsReq) {
        NewYearGameMain NewYearGameMain = newYearGameMainService.selectById(newYearAuthorityIdsReq.getActId());
        if(CommonUtil.isNotEmpty(NewYearGameMain)) {
            if (NewYearGameMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new NewYearException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new NewYearException(ResponseEnums.NEWYEAR_HAS5);
        }
        NewYearGameAuthority newYearGameAuthority = new NewYearGameAuthority();
        newYearGameAuthority.setState(1);
        newYearGameAuthorityService.update(newYearGameAuthority,new EntityWrapper<NewYearGameAuthority>().in("id",newYearAuthorityIdsReq.getIds()));
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
            NewYearGameMain NewYearGameMain = newYearGameMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = newYearGameCashPrizeApplyDAO.findExports(params);
            String title = "活动名称：" + NewYearGameMain.getName() + "，开始时间：" + sdf.format(NewYearGameMain.getActivityBeginTime()) + "结束时间："
                    + sdf.format(NewYearGameMain.getActivityEndTime());
            HSSFWorkbook book = exportExcelForRecoding(list, title);
            msg.put("book", book);
            msg.put("fileName", NewYearGameMain.getName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "元旦跨年跳跃活动记录导出excel失败！");
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
                if ("6".equals(turPriType)) {// 积分
                    priTypeName = "积分";
                    priTypeUnit = "分";
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
                    CommonUtil.isEmpty(map.get("nickname")) ? "游客" : CommonUtil.Blob2String(map.get("nickname")),
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
