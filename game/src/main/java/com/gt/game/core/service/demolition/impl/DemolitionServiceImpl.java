package com.gt.game.core.service.demolition.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
import com.gt.game.core.bean.demolition.req.*;
import com.gt.game.core.bean.demolition.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.demolition.DemolitiongiftboxCashPrizeApplyDAO;
import com.gt.game.core.dao.demolition.DemolitiongiftboxMainDAO;
import com.gt.game.core.entity.demolition.*;
import com.gt.game.core.exception.demolition.DemolitionException;
import com.gt.game.core.service.demolition.*;
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
 * 拆礼盒主表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class DemolitionServiceImpl implements DemolitionService {

    @Autowired
    DemolitiongiftboxMainService demolitiongiftboxMainService;

    @Autowired
    DemolitiongiftboxCashPrizeApplyService demolitiongiftboxCashPrizeApplyService;

    @Autowired
    DemolitiongiftboxPrizeService demolitiongiftboxPrizeService;

    @Autowired
    DemolitiongiftboxGiftboxService demolitiongiftboxGiftboxService;

    @Autowired
    DemolitionGiftBoxAddressService demolitionGiftBoxAddressService;

    @Autowired
    DemolitionGiftBoxAdService demolitionGiftBoxAdService;

    @Autowired
    DemolitiongiftboxPrizeImgService demolitiongiftboxPrizeImgService;

    @Autowired
    DemolitiongiftboxAuthorityService demolitiongiftboxAuthorityService;

    @Autowired
    DemolitiongiftboxPlayRecordService demolitiongiftboxPlayRecordService;

    @Autowired
    DemolitiongiftboxReportService demolitiongiftboxReportService;

    @Autowired
    DemolitiongiftboxMainDAO demolitiongiftboxMainDAO;

    @Autowired
    DemolitiongiftboxCashPrizeApplyDAO demolitiongiftboxCashPrizeApplyDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "demolitionGiftBoxMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
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
        String url = applyProperties.getMobileBaseUrl() + "demolitionGiftBoxMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }
    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<DemolitionCountRes> getDemolitionCount(BusUser busUser) {
        DemolitionCountRes demolitionCountRes = new DemolitionCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = demolitiongiftboxMainDAO.getCount(params);
        demolitionCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        demolitionCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        demolitionCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        demolitionCountRes.setCount1(demolitionCountRes.getCount2()+demolitionCountRes.getCount3()+demolitionCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",demolitionCountRes);
    }
    /**
     * 获取奖品类型列表
     *
     */
    @Override
    public ResponseDTO<List<DemolitionPrizeTypeListRes>> getDemolitionPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<DemolitionPrizeTypeListRes> demolitionPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                DemolitionPrizeTypeListRes demolitionPrizeTypeListRes = new DemolitionPrizeTypeListRes();
                demolitionPrizeTypeListRes.setName(dictApiRes.getItemValue());
                demolitionPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                demolitionPrizeTypeListResList.add(demolitionPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",demolitionPrizeTypeListResList);
    }

    /**
     * 分页获取活动列表
     *
     * @param demolitionListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<DemolitionListRes>> getDemolitionList(BusUser busUser, DemolitionListPageReq demolitionListPageReq) {
        Page<DemolitionListRes> page = new Page<>(demolitionListPageReq.getCurrent(),demolitionListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",demolitionListPageReq.getStatus());
        map.put("name",demolitionListPageReq.getName());
        map.put("busId",busUser.getId());
        List<DemolitionListRes> demolitionListResList = demolitiongiftboxMainDAO.getDemolitionList(page,map);
        for (DemolitionListRes demolitionListRes : demolitionListResList){
            if(demolitionListRes.getActivityBeginTime().getTime() > date.getTime()){
                demolitionListRes.setIsEdit(1);
                demolitionListRes.setStatus(0);
            }else if(demolitionListRes.getActivityEndTime().getTime() < date.getTime()){
                demolitionListRes.setIsEdit(0);
                demolitionListRes.setStatus(2);
            }else {
                demolitionListRes.setIsEdit(0);
                demolitionListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",demolitionListResList,pageDTO);
    }
    /**
     * 获取活动
     * @param id
     * @return
     */
    @Override
    public ResponseDTO<DemolitionRes> getDemolition(BusUser busUser, Integer id) {
        DemolitiongiftboxMain demolitiongiftboxMain = demolitiongiftboxMainService.selectById(id);
        DemolitionRes demolitionRes = new DemolitionRes();
        if (CommonUtil.isNotEmpty(demolitiongiftboxMain)) {
            BeanUtils.copyProperties(demolitiongiftboxMain, demolitionRes);
            //奖品
            List<DemolitiongiftboxPrize> demolitiongiftboxPrizes = demolitiongiftboxPrizeService.selectList(new EntityWrapper<DemolitiongiftboxPrize>().eq("act_id", id));
            List<DemolitionPrizeReq> demolitionPrizeReqs = new ArrayList<>();
            if (demolitiongiftboxPrizes.size() > 0) {
                for (DemolitiongiftboxPrize demolitiongiftboxPrize : demolitiongiftboxPrizes) {
                    DemolitionPrizeReq demolitionPrizeReq = new DemolitionPrizeReq();
                    BeanUtils.copyProperties(demolitiongiftboxPrize, demolitionPrizeReq);
                    List<DemolitiongiftboxPrizeImg> demolitiongiftboxPrizeImgList = demolitiongiftboxPrizeImgService.selectList(new EntityWrapper<DemolitiongiftboxPrizeImg>().eq("prize_id", demolitiongiftboxPrize.getId()));
                    List<DemolitionPrizeImgReq> demolitionPrizeImgReqs = new ArrayList<>();
                    for (DemolitiongiftboxPrizeImg demolitiongiftboxPrizeImg : demolitiongiftboxPrizeImgList) {
                        DemolitionPrizeImgReq demolitionPrizeImgReq = new DemolitionPrizeImgReq();
                        BeanUtils.copyProperties(demolitiongiftboxPrizeImg, demolitionPrizeImgReq);
                        demolitionPrizeImgReqs.add(demolitionPrizeImgReq);
                    }
                    demolitionPrizeReq.setDemolitionPrizeImgReqs(demolitionPrizeImgReqs);
                    demolitionPrizeReqs.add(demolitionPrizeReq);
                }
            }
            demolitionRes.setDemolitionPrizeReqs(demolitionPrizeReqs);
            //礼盒
            List<DemolitiongiftboxGiftbox> demolitiongiftboxGiftboxes = demolitiongiftboxGiftboxService.selectList(new EntityWrapper<DemolitiongiftboxGiftbox>().eq("act_id", id));
            List<DemolitionGiftBoxReq> demolitionGiftBoxReqs = new ArrayList<>();
            if (demolitiongiftboxGiftboxes.size() > 0) {
                for (DemolitiongiftboxGiftbox demolitiongiftboxGiftbox : demolitiongiftboxGiftboxes) {
                    DemolitionGiftBoxReq demolitionGiftBoxReq = new DemolitionGiftBoxReq();
                    BeanUtils.copyProperties(demolitiongiftboxGiftbox, demolitionGiftBoxReq);
                    demolitionGiftBoxReqs.add(demolitionGiftBoxReq);
                    if (demolitionGiftBoxReq.getGiftSound().equals("/images/activity/demolitionGiftBox/music/1.mp3")) {
                        demolitionGiftBoxReq.setGiftSound("音乐1");
                    }
                    if (demolitionGiftBoxReq.getGiftSound().equals("/images/activity/demolitionGiftBox/music/2.mp3")) {
                        demolitionGiftBoxReq.setGiftSound("音乐2");
                    }
                    if (demolitionGiftBoxReq.getGiftSound().equals("/images/activity/demolitionGiftBox/music/3.mp3")) {
                        demolitionGiftBoxReq.setGiftSound("音乐3");
                    }
                    if (demolitionGiftBoxReq.getGiftSound().equals("/images/activity/demolitionGiftBox/music/4.mp3")) {
                        demolitionGiftBoxReq.setGiftSound("音乐4");
                    }
                    if (demolitionGiftBoxReq.getGiftSound().equals("/images/activity/demolitionGiftBox/music/5.mp3")) {
                        demolitionGiftBoxReq.setGiftSound("音乐5");
                    }
                }
            }
            demolitionRes.setDemolitionGiftBoxReqs(demolitionGiftBoxReqs);
            //兑奖地址
            List<DemolitionGiftBoxAddress> demolitionGiftBoxAddresses = demolitionGiftBoxAddressService.selectList(new EntityWrapper<DemolitionGiftBoxAddress>().eq("act_id", id));
            List<DemolitionAddressReq> demolitionAddressReqs = new ArrayList<>();
            if (demolitionGiftBoxAddresses.size() > 0) {
                for (DemolitionGiftBoxAddress demolitionGiftBoxAddress : demolitionGiftBoxAddresses) {
                    DemolitionAddressReq demolitionAddressReq = new DemolitionAddressReq();
                    BeanUtils.copyProperties(demolitionGiftBoxAddress, demolitionAddressReq);
                    demolitionAddressReqs.add(demolitionAddressReq);
                }
            }
            demolitionRes.setDemolitionAddressReqs(demolitionAddressReqs);
            //广告轮播图
            List<DemolitiongiftboxAd> demolitiongiftboxAds = demolitionGiftBoxAdService.selectList(new EntityWrapper<DemolitiongiftboxAd>().eq("act_id", id));
            List<DemolitionAdReq> demolitionAdReqs = new ArrayList<>();
            if (demolitiongiftboxAds.size() > 0) {
                for (DemolitiongiftboxAd demolitiongiftboxAd : demolitiongiftboxAds) {
                    DemolitionAdReq demolitionAdReq = new DemolitionAdReq();
                    BeanUtils.copyProperties(demolitiongiftboxAd, demolitionAdReq);
                    demolitionAdReqs.add(demolitionAdReq);
                }
            }
            demolitionRes.setDemolitionAdReqs(demolitionAdReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",demolitionRes);
    }
    /**
     * 分页获取中奖记录列表
     * @param demolitionApplyListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<DemolitionApplyListRes>> getDemolitionApplyList(BusUser busUser, DemolitionApplyListPageReq demolitionApplyListPageReq) {
        Page<DemolitionApplyListRes> page = new Page<>(demolitionApplyListPageReq.getCurrent(),demolitionApplyListPageReq.getSize());
        List<DemolitionApplyListRes> demolitionApplyListResList = demolitiongiftboxCashPrizeApplyDAO.queryRecodList(page,demolitionApplyListPageReq);
        if(demolitionApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < demolitionApplyListResList.size(); i++) {
                ids.append(demolitionApplyListResList.get(i).getMemberId());
                if (demolitionApplyListResList.size() > 1 && i < demolitionApplyListResList.size() - 1) {
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
            for (int i = 0; i < demolitionApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == demolitionApplyListResList.get(i).getMemberId().intValue()) {
                        demolitionApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(demolitionApplyListResList.get(i).getNickname())) {
                    demolitionApplyListResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",demolitionApplyListResList,pageDTO);
    }
    /**
     * 中奖记录发放奖品
     * @param demolitionApplyIdReq
     * @return
     */
    @Override
    public ResponseDTO editDemolitionApply(BusUser busUser, DemolitionApplyIdReq demolitionApplyIdReq) {
        DemolitiongiftboxCashPrizeApply demolitiongiftboxCashPrizeApply = demolitiongiftboxCashPrizeApplyService.selectById(demolitionApplyIdReq.getId());
        if(CommonUtil.isNotEmpty(demolitiongiftboxCashPrizeApply)){
            DemolitiongiftboxMain demolitiongiftboxMain = demolitiongiftboxMainService.selectById(demolitiongiftboxCashPrizeApply.getActId());
            DemolitiongiftboxPrize demolitiongiftboxPrize = demolitiongiftboxPrizeService.selectById(demolitiongiftboxCashPrizeApply.getPrizeId());
            if(demolitiongiftboxPrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(demolitiongiftboxMain.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new DemolitionException(ResponseEnums.DEMOLITION_HAS3);
                } else if (!DateTimeKit.laterThanNow(demolitiongiftboxMain.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new DemolitionException(ResponseEnums.DEMOLITION_HAS4);
                }
            }
            if (demolitiongiftboxCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                demolitiongiftboxCashPrizeApply.setStatus(2);
                demolitiongiftboxCashPrizeApply.setCashTime(new Date());
                demolitiongiftboxCashPrizeApplyService.updateById(demolitiongiftboxCashPrizeApply);
            } else if (demolitiongiftboxCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new DemolitionException(ResponseEnums.DEMOLITION_HAS1);
            }else{//还未提交
                throw  new DemolitionException(ResponseEnums.DEMOLITION_HAS2);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }

    @Override
    public Map<String, Object> exports(Map<String, Object> params) {
        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            DemolitiongiftboxMain demolitiongiftboxMain = demolitiongiftboxMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = demolitiongiftboxCashPrizeApplyDAO.findExports(params);
            String title = "活动名称：" + demolitiongiftboxMain.getName() + "，开始时间：" + sdf.format(demolitiongiftboxMain.getActivityBeginTime()) + "结束时间："
                    + sdf.format(demolitiongiftboxMain.getActivityEndTime());
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("member_id"));
                    if (list.size() > 1 && i < list.size() - 1) {
                        ids.append(",");
                    }
                }
                MemberReq memberReq = new MemberReq();
                memberReq.setBusId(demolitiongiftboxMain.getBusId());
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
                        list.get(i).put("nickname","游客");
                    }
                }
            }
            HSSFWorkbook book = exportExcelForRecoding(list, title);
            msg.put("book", book);
            msg.put("fileName", demolitiongiftboxMain.getName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "拆礼盒活动记录导出excel失败！");
            e.printStackTrace();
        } finally {
            msg.put("result", result);
            msg.put("message", message);
        }
        return msg;
    }
    /**
     * 保存活动
     * @param demolitionSaveReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveDemolition(BusUser busUser, DemolitionSaveReq demolitionSaveReq) {
        if(demolitionSaveReq.getDemolitionGiftBoxReqs() == null || demolitionSaveReq.getDemolitionGiftBoxReqs().size() < 3 || demolitionSaveReq.getDemolitionGiftBoxReqs().size() > 5){
            throw new DemolitionException(ResponseEnums.DEMOLITION_HAS14);
        }
        if(demolitionSaveReq.getDemolitionPrizeReqs() == null || demolitionSaveReq.getDemolitionPrizeReqs().size() < 1){
            throw new DemolitionException(ResponseEnums.COMMON_HAS20);
        }
        DemolitiongiftboxMain demolitiongiftboxMain = null;
        Double num = 0.0;
        int f = 0;
        if(demolitionSaveReq.getId() == 0){//新增
            demolitiongiftboxMain = new DemolitiongiftboxMain();
            BeanUtils.copyProperties(demolitionSaveReq,demolitiongiftboxMain);
            demolitiongiftboxMain.setBusId(busUser.getId());
            demolitiongiftboxMain.setCreatetime(new Date());
            demolitiongiftboxMain.setFollowQrCode(demolitiongiftboxMain.getFollowQrCode().split("/upload").length > 1
                                                            ?demolitiongiftboxMain.getFollowQrCode().split("/upload")[1]:demolitiongiftboxMain.getFollowQrCode());
            demolitiongiftboxMainService.insert(demolitiongiftboxMain);
        }else{//编辑
            demolitiongiftboxMain = demolitiongiftboxMainService.selectById(demolitionSaveReq.getId());
            if(CommonUtil.isEmpty(demolitiongiftboxMain)){
                throw new DemolitionException(ResponseEnums.DEMOLITION_HAS5);
            }
            if(demolitiongiftboxMain.getActivityBeginTime().getTime() < new Date().getTime()){
                throw new DemolitionException(ResponseEnums.DEMOLITION_HAS6);
            }
            if(demolitiongiftboxMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new DemolitionException(ResponseEnums.DIFF_USER);
            }
            BeanUtils.copyProperties(demolitionSaveReq,demolitiongiftboxMain);
            demolitiongiftboxMain.setFollowQrCode(demolitiongiftboxMain.getFollowQrCode().split("/upload").length > 1
                    ?demolitiongiftboxMain.getFollowQrCode().split("/upload")[1]:demolitiongiftboxMain.getFollowQrCode());
            demolitiongiftboxMainService.updateById(demolitiongiftboxMain);
            //删除
            //礼盒
            demolitiongiftboxGiftboxService.delete(new EntityWrapper<DemolitiongiftboxGiftbox>().eq("act_id",demolitiongiftboxMain.getId()));
            //兑奖地址
            demolitionGiftBoxAddressService.delete(new EntityWrapper<DemolitionGiftBoxAddress>().eq("act_id",demolitiongiftboxMain.getId()));
            //广告轮播图
            demolitionGiftBoxAdService.delete(new EntityWrapper<DemolitiongiftboxAd>().eq("act_id",demolitiongiftboxMain.getId()));
            //奖品
            List<DemolitiongiftboxPrize> demolitiongiftboxPrizes = demolitiongiftboxPrizeService.selectList(new EntityWrapper<DemolitiongiftboxPrize>().eq("act_id",demolitiongiftboxMain.getId()));
            if(demolitiongiftboxPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(DemolitiongiftboxPrize demolitiongiftboxPrize : demolitiongiftboxPrizes){
                    if(demolitiongiftboxPrize.getType() == 1){
                         num += demolitiongiftboxPrize.getNum();
                         f = 1;
                    }
                    ids.add(demolitiongiftboxPrize.getId());
                }
                demolitiongiftboxPrizeImgService.delete(new EntityWrapper<DemolitiongiftboxPrizeImg>().in("prize_id",ids));
            }
            demolitiongiftboxPrizeService.delete(new EntityWrapper<DemolitiongiftboxPrize>().eq("act_id",demolitiongiftboxMain.getId()));
        }
        Double fenbi = 0.0;
        //奖品
        if(CommonUtil.isNotEmpty(demolitionSaveReq.getDemolitionPrizeReqs())){
            for(DemolitionPrizeReq demolitionPrizeReq :demolitionSaveReq.getDemolitionPrizeReqs()){
                if(demolitionPrizeReq.getType()==1){
                    fenbi += demolitionPrizeReq.getNum();
                }
                DemolitiongiftboxPrize demolitiongiftboxPrize = new DemolitiongiftboxPrize();
                BeanUtils.copyProperties(demolitionPrizeReq,demolitiongiftboxPrize);
                demolitiongiftboxPrize.setActId(demolitiongiftboxMain.getId());
                demolitiongiftboxPrizeService.insert(demolitiongiftboxPrize);
                if(demolitiongiftboxPrize.getType() == 4 && demolitionPrizeReq.getDemolitionPrizeImgReqs().size() == 0){
                    throw new DemolitionException(ResponseEnums.COMMON_HAS21);
                }
                if(demolitiongiftboxPrize.getType() == 4 && demolitionPrizeReq.getDemolitionPrizeImgReqs().size() > 5){
                    throw new DemolitionException(ResponseEnums.COMMON_HAS22);
                }
                if(demolitionPrizeReq.getDemolitionPrizeImgReqs().size() > 0){
                    for(DemolitionPrizeImgReq demolitionPrizeImgReq : demolitionPrizeReq.getDemolitionPrizeImgReqs()){
                        DemolitiongiftboxPrizeImg demolitiongiftboxPrizeImg = new DemolitiongiftboxPrizeImg();
                        BeanUtils.copyProperties(demolitionPrizeImgReq,demolitiongiftboxPrizeImg);
                        demolitiongiftboxPrizeImg.setPrizeId(demolitiongiftboxPrize.getId());
                        demolitiongiftboxPrizeImg.setImgUrl(demolitiongiftboxPrizeImg.getImgUrl().split("/upload").length>1?
                                                        demolitiongiftboxPrizeImg.getImgUrl().split("/upload")[1]:demolitiongiftboxPrizeImg.getImgUrl());
                        demolitiongiftboxPrizeImgService.insert(demolitiongiftboxPrizeImg);
                    }
                }
            }
        }
        //礼盒
        if(CommonUtil.isNotEmpty(demolitionSaveReq.getDemolitionGiftBoxReqs())){
            for(DemolitionGiftBoxReq demolitionGiftBoxReq :demolitionSaveReq.getDemolitionGiftBoxReqs()){
                DemolitiongiftboxGiftbox demolitiongiftboxGiftbox = new DemolitiongiftboxGiftbox();
                BeanUtils.copyProperties(demolitionGiftBoxReq,demolitiongiftboxGiftbox);
                demolitiongiftboxGiftbox.setActId(demolitiongiftboxMain.getId());
                if(demolitiongiftboxGiftbox.getGiftSound().equals("音乐1")){
                    demolitiongiftboxGiftbox.setGiftSound("/images/activity/demolitionGiftBox/music/1.mp3");
                }if(demolitiongiftboxGiftbox.getGiftSound().equals("音乐2")){
                    demolitiongiftboxGiftbox.setGiftSound("/images/activity/demolitionGiftBox/music/2.mp3");
                }if(demolitiongiftboxGiftbox.getGiftSound().equals("音乐3")){
                    demolitiongiftboxGiftbox.setGiftSound("/images/activity/demolitionGiftBox/music/3.mp3");
                }if(demolitiongiftboxGiftbox.getGiftSound().equals("音乐4")){
                    demolitiongiftboxGiftbox.setGiftSound("/images/activity/demolitionGiftBox/music/4.mp3");
                }if(demolitiongiftboxGiftbox.getGiftSound().equals("音乐5")){
                    demolitiongiftboxGiftbox.setGiftSound("/images/activity/demolitionGiftBox/music/5.mp3");
                }
                demolitiongiftboxGiftboxService.insert(demolitiongiftboxGiftbox);
            }
        }
        //兑奖地址
        if(CommonUtil.isNotEmpty(demolitionSaveReq.getDemolitionAddressReqs())){
            for(DemolitionAddressReq demolitionAddressReq :demolitionSaveReq.getDemolitionAddressReqs()){
                DemolitionGiftBoxAddress demolitionGiftBoxAddress = new DemolitionGiftBoxAddress();
                BeanUtils.copyProperties(demolitionAddressReq,demolitionGiftBoxAddress);
                demolitionGiftBoxAddress.setActId(demolitiongiftboxMain.getId());
                demolitionGiftBoxAddressService.insert(demolitionGiftBoxAddress);
            }
        }
        //广告轮播图
        if(CommonUtil.isNotEmpty(demolitionSaveReq.getDemolitionAdReqs())){
            for(DemolitionAdReq demolitionAdReq :demolitionSaveReq.getDemolitionAdReqs()){
                DemolitiongiftboxAd demolitiongiftboxAd = new DemolitiongiftboxAd();
                BeanUtils.copyProperties(demolitionAdReq,demolitiongiftboxAd);
                demolitiongiftboxAd.setActId(demolitiongiftboxMain.getId());
                demolitiongiftboxAd.setUrl(demolitiongiftboxAd.getUrl().split("/upload").length>1?
                        demolitiongiftboxAd.getUrl().split("/upload")[1]:demolitiongiftboxAd.getUrl());
                if(demolitiongiftboxAd.getHrefUrl().indexOf("deeptel.com.cn") == -1 && demolitiongiftboxAd.getHrefUrl().indexOf("duofriend.com") == -1 ){
                    throw new DemolitionException(ResponseEnums.COMMON_HAS19);
                }
                demolitionGiftBoxAdService.insert(demolitiongiftboxAd);
            }
        }
        if(fenbi == 0.0 && num > 0.0){
            throw new DemolitionException(ResponseEnums.COMMON_HAS18);
        }
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new DemolitionException(ResponseEnums.DEMOLITION_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new DemolitionException(ResponseEnums.DEMOLITION_HAS15);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(demolitiongiftboxMain.getId());
                updateFenbiReduceReq.setFreType(99);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new DemolitionException(ResponseEnums.DEMOLITION_HAS16);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new DemolitionException(ResponseEnums.DEMOLITION_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, demolitiongiftboxMain.getId(), 99, 1, "拆礼盒活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new DemolitionException(ResponseEnums.DEMOLITION_HAS8);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }

    /**
     * 删除活动
     * @param demolitionIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeDemolition(BusUser busUser, DemolitionIdReq demolitionIdReq) {
        DemolitiongiftboxMain demolitiongiftboxMain = demolitiongiftboxMainService.selectById(demolitionIdReq.getId());
        if(CommonUtil.isNotEmpty(demolitiongiftboxMain)){
            if(demolitiongiftboxMain.getActivityBeginTime().getTime() < new Date().getTime() && demolitiongiftboxMain.getActivityEndTime().getTime() > new Date().getTime()){
                throw new DemolitionException(ResponseEnums.DEMOLITION_HAS10);
            }
            if(demolitiongiftboxMain.getCashPrizeBeginTime().getTime() < new Date().getTime() && demolitiongiftboxMain.getCashPrizeEndTime().getTime() > new Date().getTime()){
                throw new DemolitionException(ResponseEnums.DEMOLITION_HAS12);
            }
            List<DemolitiongiftboxCashPrizeApply> demolitiongiftboxCashPrizeApplies = demolitiongiftboxCashPrizeApplyService.selectList(
                                new EntityWrapper<DemolitiongiftboxCashPrizeApply>().eq("act_id",demolitionIdReq.getId()).eq("status",3));
            if(demolitiongiftboxCashPrizeApplies.size() > 0 ){
                throw new DemolitionException(ResponseEnums.DEMOLITION_HAS11);

            }
            if(demolitiongiftboxMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new DemolitionException(ResponseEnums.DIFF_USER);
            }
            demolitiongiftboxMainService.deleteById(demolitiongiftboxMain.getId());
            //礼盒
            demolitiongiftboxGiftboxService.delete(new EntityWrapper<DemolitiongiftboxGiftbox>().eq("act_id",demolitiongiftboxMain.getId()));
            //兑奖地址
            demolitionGiftBoxAddressService.delete(new EntityWrapper<DemolitionGiftBoxAddress>().eq("act_id",demolitiongiftboxMain.getId()));
            //广告轮播图
            demolitionGiftBoxAdService.delete(new EntityWrapper<DemolitiongiftboxAd>().eq("act_id",demolitiongiftboxMain.getId()));
            //奖品
            List<DemolitiongiftboxPrize> demolitiongiftboxPrizes = demolitiongiftboxPrizeService.selectList(new EntityWrapper<DemolitiongiftboxPrize>().eq("act_id",demolitiongiftboxMain.getId()));
            boolean ff = false;
            if(demolitiongiftboxPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(DemolitiongiftboxPrize demolitiongiftboxPrize : demolitiongiftboxPrizes){
                    ids.add(demolitiongiftboxPrize.getId());
                    if(demolitiongiftboxPrize.getType() == 1){
                        ff = true;
                    }
                }
                demolitiongiftboxPrizeImgService.delete(new EntityWrapper<DemolitiongiftboxPrizeImg>().in("prize_id",ids));
            }
            demolitiongiftboxPrizeService.delete(new EntityWrapper<DemolitiongiftboxPrize>().eq("act_id",demolitiongiftboxMain.getId()));
            demolitiongiftboxAuthorityService.delete(new EntityWrapper<DemolitiongiftboxAuthority>().eq("act_id",demolitiongiftboxMain.getId()));
            demolitiongiftboxCashPrizeApplyService.delete(new EntityWrapper<DemolitiongiftboxCashPrizeApply>().eq("act_id",demolitiongiftboxMain.getId()));
            demolitiongiftboxPlayRecordService.delete(new EntityWrapper<DemolitiongiftboxPlayRecord>().eq("act_id",demolitiongiftboxMain.getId()));
            demolitiongiftboxReportService.delete(new EntityWrapper<DemolitiongiftboxReport>().eq("act_id",demolitiongiftboxMain.getId()));
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(demolitiongiftboxMain.getId());
                fenbiSurplus.setFre_type(99);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(demolitiongiftboxMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(99);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new DemolitionException(ResponseEnums.DEMOLITION_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 分页获取核销授权
     * @param demolitionAuthorityListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<DemolitionAuthorityListRes>> getDemolitionAuthorityList(BusUser busUser, DemolitionAuthorityListPageReq demolitionAuthorityListPageReq) {
       Page<DemolitiongiftboxAuthority> page = new Page<>(demolitionAuthorityListPageReq.getCurrent(),demolitionAuthorityListPageReq.getSize());
       List<DemolitiongiftboxAuthority> demolitiongiftboxAuthorityList = demolitiongiftboxAuthorityService.selectPage(page,
                                            new EntityWrapper<DemolitiongiftboxAuthority>().eq("act_id",demolitionAuthorityListPageReq.getActId())
                                                                                            .eq("delete_status",0)).getRecords();
        List<DemolitionAuthorityListRes> demolitionAuthorityListRes = new ArrayList<>();
        for (DemolitiongiftboxAuthority demolitiongiftboxAuthority : demolitiongiftboxAuthorityList){
            DemolitionAuthorityListRes demolitionAuthorityListRes1 = new DemolitionAuthorityListRes();
            BeanUtils.copyProperties(demolitiongiftboxAuthority,demolitionAuthorityListRes1);
            demolitionAuthorityListRes.add(demolitionAuthorityListRes1);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",demolitionAuthorityListRes,pageDTO);
    }
    /**
     * 删除核销授权
     * @param demolitionAuthorityIdsReq
     * @return
     */
    @Override
    public ResponseDTO removeDemolitionAuthority(BusUser busUser, DemolitionAuthorityIdsReq demolitionAuthorityIdsReq) {
        DemolitiongiftboxMain demolitiongiftboxMain = demolitiongiftboxMainService.selectById(demolitionAuthorityIdsReq.getActId());
        if(CommonUtil.isNotEmpty(demolitiongiftboxMain)) {
            if (demolitiongiftboxMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new DemolitionException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new DemolitionException(ResponseEnums.DEMOLITION_HAS5);
        }
        DemolitiongiftboxAuthority demolitiongiftboxAuthority = new DemolitiongiftboxAuthority();
        demolitiongiftboxAuthority.setDeleteStatus(1);
        demolitiongiftboxAuthorityService.update(demolitiongiftboxAuthority,new EntityWrapper<DemolitiongiftboxAuthority>().in("id",demolitionAuthorityIdsReq.getIds()));
        return ResponseDTO.createBySuccess("删除成功");
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
                    CommonUtil.isEmpty(map.get("nickname")) ? "游客" : map.get("nickname").toString(),
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
