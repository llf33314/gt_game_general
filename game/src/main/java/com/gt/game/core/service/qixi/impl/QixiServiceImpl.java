package com.gt.game.core.service.qixi.impl;

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
import com.gt.game.core.bean.qixi.req.*;
import com.gt.game.core.bean.qixi.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.qixi.QixiCashPrizeApplyDAO;
import com.gt.game.core.dao.qixi.QixiMainDAO;
import com.gt.game.core.entity.qixi.*;
import com.gt.game.core.exception.qixi.QixiException;
import com.gt.game.core.service.qixi.*;
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
 * 浪漫七夕主表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
@Service
public class QixiServiceImpl implements QixiService {

    @Autowired
    QixiAddressService qixiAddressService;

    @Autowired
    QixiAdService qixiAdService;

    @Autowired
    QixiAuthorityService qixiAuthorityService;

    @Autowired
    QixiCashPrizeApplyService qixiCashPrizeApplyService;

    @Autowired
    QixiMainService qixiMainService;

    @Autowired
    QixiPlayRecordService qixiPlayRecordService;

    @Autowired
    QixiPrizeImgService qixiPrizeImgService;

    @Autowired
    QixiPrizeService qixiPrizeService;

    @Autowired
    QixiReportService qixiReportService;

    @Autowired
    QixiMainDAO qixiMainDAO;

    @Autowired
    QixiCashPrizeApplyDAO qixiCashPrizeApplyDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "qixiMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
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
        String url = applyProperties.getMobileBaseUrl() + "qixiMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }
    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<QixiCountRes> getQixiCount(BusUser busUser) {
        QixiCountRes QixiCountRes = new QixiCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = qixiMainDAO.getCount(params);
        QixiCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        QixiCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        QixiCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        QixiCountRes.setCount1(QixiCountRes.getCount2()+QixiCountRes.getCount3()+QixiCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",QixiCountRes);
    }
    /**
     * 分页获取活动列表
     *
     * @param QixiListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<QixiListRes>> getQixiList(BusUser busUser, QixiListPageReq QixiListPageReq) {
        Page<QixiListRes> page = new Page<>(QixiListPageReq.getCurrent(),QixiListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",QixiListPageReq.getStatus());
        map.put("name",QixiListPageReq.getName());
        map.put("busId",busUser.getId());
        List<QixiListRes> QixiListResList = qixiMainDAO.getQixiList(page,map);
        for (QixiListRes QixiListRes : QixiListResList){
            if(QixiListRes.getActivityBeginTime().getTime() > date.getTime()){
                QixiListRes.setIsEdit(1);
                QixiListRes.setStatus(0);
            }else if(QixiListRes.getActivityEndTime().getTime() < date.getTime()){
                QixiListRes.setIsEdit(0);
                QixiListRes.setStatus(2);
            }else {
                QixiListRes.setIsEdit(0);
                QixiListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",QixiListResList,pageDTO);
    }
    /**
     * 分页获取中奖记录列表
     * @param QixiApplyListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<QixiApplyListRes>> getQixiApplyList(BusUser busUser, QixiApplyListPageReq QixiApplyListPageReq) {
        Page<QixiApplyListRes> page = new Page<>(QixiApplyListPageReq.getCurrent(),QixiApplyListPageReq.getSize());
        List<QixiApplyListRes> QixiApplyListResList = qixiCashPrizeApplyDAO.queryRecodList(page,QixiApplyListPageReq);
        if(QixiApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < QixiApplyListResList.size(); i++) {
                ids.append(QixiApplyListResList.get(i).getMemberId());
                if (QixiApplyListResList.size() > 1 && i < QixiApplyListResList.size() - 1) {
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
            for (int i = 0; i < QixiApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == QixiApplyListResList.get(i).getMemberId().intValue()) {
                        QixiApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(QixiApplyListResList.get(i).getNickname())) {
                    QixiApplyListResList.get(i).setNickname("未知用户");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",QixiApplyListResList,pageDTO);

    }
    /**
     * 中奖记录发放奖品
     * @param QixiApplyIdReq
     * @return
     */
    @Override
    public ResponseDTO editQixiApply(BusUser busUser, QixiApplyIdReq QixiApplyIdReq) {
        QixiCashPrizeApply QixiCashPrizeApply = qixiCashPrizeApplyService.selectById(QixiApplyIdReq.getId());
        if(CommonUtil.isNotEmpty(QixiCashPrizeApply)){
            QixiMain main = qixiMainService.selectById(QixiCashPrizeApply.getActId());
            QixiPrize QixiPrize = qixiPrizeService.selectById(QixiCashPrizeApply.getPrizeId());
            if(QixiPrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(main.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new QixiException(ResponseEnums.QIXI_HAS3);
                } else if (!DateTimeKit.laterThanNow(main.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new QixiException(ResponseEnums.QIXI_HAS4);
                }
            }
            if (QixiCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                QixiCashPrizeApply.setStatus(2);
                QixiCashPrizeApply.setCashTime(new Date());
                qixiCashPrizeApplyService.updateById(QixiCashPrizeApply);
            } else if (QixiCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new QixiException(ResponseEnums.QIXI_HAS1);
            }else{//还未提交
                throw  new QixiException(ResponseEnums.QIXI_HAS2);
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
    public ResponseDTO<QixiRes> getQixi(BusUser busUser, Integer id) {
        QixiMain QixiMain = qixiMainService.selectById(id);
        QixiRes QixiRes = new QixiRes();
        if (CommonUtil.isNotEmpty(QixiMain)) {
            BeanUtils.copyProperties(QixiMain, QixiRes);
            //奖品
            List<QixiPrize> QixiPrizes = qixiPrizeService.selectList(new EntityWrapper<QixiPrize>().eq("act_id", id));
            List<QixiPrizeReq> QixiPrizeReqs = new ArrayList<>();
            if (QixiPrizes.size() > 0) {
                for (QixiPrize QixiPrize : QixiPrizes) {
                    QixiPrizeReq QixiPrizeReq = new QixiPrizeReq();
                    BeanUtils.copyProperties(QixiPrize, QixiPrizeReq);
                    List<QixiPrizeImg> QixiPrizeImgs = qixiPrizeImgService.selectList(new EntityWrapper<QixiPrizeImg>().eq("prize_id", QixiPrize.getId()));
                    List<QixiPrizeImgReq> QixiPrizeImgReqs = new ArrayList<>();
                    for (QixiPrizeImg QixiPrizeImg : QixiPrizeImgs) {
                        QixiPrizeImgReq QixiPrizeImgReq = new QixiPrizeImgReq();
                        BeanUtils.copyProperties(QixiPrizeImg, QixiPrizeImgReq);
                        QixiPrizeImgReqs.add(QixiPrizeImgReq);
                    }
                    QixiPrizeReq.setQixiPrizeImgReqs(QixiPrizeImgReqs);
                    QixiPrizeReqs.add(QixiPrizeReq);
                }
            }
            QixiRes.setQixiPrizeReqs(QixiPrizeReqs);
            //兑奖地址
            List<QixiAddress> QixiAddresses = qixiAddressService.selectList(new EntityWrapper<QixiAddress>().eq("act_id", id));
            List<QixiAddressReq> QixiAddressReqs = new ArrayList<>();
            if (QixiAddresses.size() > 0) {
                for (QixiAddress QixiAddress : QixiAddresses) {
                    QixiAddressReq QixiAddressReq = new QixiAddressReq();
                    BeanUtils.copyProperties(QixiAddress, QixiAddressReq);
                    QixiAddressReqs.add(QixiAddressReq);
                }
            }
            QixiRes.setQixiAddressReqs(QixiAddressReqs);
            //广告轮播图
            List<QixiAd> QixiAds = qixiAdService.selectList(new EntityWrapper<QixiAd>().eq("act_id", id));
            List<QixiAdReq> QixiAdReqs = new ArrayList<>();
            if (QixiAds.size() > 0) {
                for (QixiAd QixiAd : QixiAds) {
                    QixiAdReq QixiAdReq = new QixiAdReq();
                    BeanUtils.copyProperties(QixiAd, QixiAdReq);
                    QixiAdReqs.add(QixiAdReq);
                }
            }
            QixiRes.setQixiAdReqs(QixiAdReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",QixiRes);
    }
    /**
     * 删除活动
     * @param QixiIdReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeQixi(BusUser busUser, QixiIdReq QixiIdReq) {
        QixiMain QixiMain = qixiMainService.selectById(QixiIdReq.getId());
        if(CommonUtil.isNotEmpty(QixiMain)){
            if(QixiMain.getActivityBeginTime().getTime() < new Date().getTime() && QixiMain.getActivityEndTime().getTime() > new Date().getTime()){
                throw new QixiException(ResponseEnums.QIXI_HAS10);
            }
            if(QixiMain.getCashPrizeBeginTime().getTime() < new Date().getTime() && QixiMain.getCashPrizeEndTime().getTime() > new Date().getTime()){
                throw new QixiException(ResponseEnums.QIXI_HAS12);
            }
            List<QixiCashPrizeApply> QixiCashPrizeApplies = qixiCashPrizeApplyService.selectList(
                    new EntityWrapper<QixiCashPrizeApply>().eq("act_id",QixiIdReq.getId()).eq("status",3));
            if(QixiCashPrizeApplies.size() > 0 ){
                throw new QixiException(ResponseEnums.QIXI_HAS11);

            }
            if(QixiMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new QixiException(ResponseEnums.DIFF_USER);
            }
            qixiMainService.deleteById(QixiMain.getId());
            //兑奖地址
            qixiAddressService.delete(new EntityWrapper<QixiAddress>().eq("act_id",QixiMain.getId()));
            //广告轮播图
            qixiAdService.delete(new EntityWrapper<QixiAd>().eq("act_id",QixiMain.getId()));
            //奖品
            List<QixiPrize> QixiPrizes = qixiPrizeService.selectList(new EntityWrapper<QixiPrize>().eq("act_id",QixiMain.getId()));
            boolean ff = false;
            if(QixiPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(QixiPrize QixiPrize : QixiPrizes){
                    ids.add(QixiPrize.getId());
                    if(QixiPrize.getType() == 1){
                        ff = true;
                    }
                }
                qixiPrizeImgService.delete(new EntityWrapper<QixiPrizeImg>().in("prize_id",ids));
            }
            qixiPrizeService.delete(new EntityWrapper<QixiPrize>().eq("act_id",QixiMain.getId()));
            qixiAuthorityService.delete(new EntityWrapper<QixiAuthority>().eq("act_id",QixiMain.getId()));
            qixiCashPrizeApplyService.delete(new EntityWrapper<QixiCashPrizeApply>().eq("act_id",QixiMain.getId()));
            qixiPlayRecordService.delete(new EntityWrapper<QixiPlayRecord>().eq("act_id",QixiMain.getId()));
            qixiReportService.delete(new EntityWrapper<QixiReport>().eq("act_id",QixiMain.getId()));
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(QixiMain.getId());
                fenbiSurplus.setFre_type(39);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(QixiMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(39);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new QixiException(ResponseEnums.QIXI_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 保存活动
     * @param QixiSaveReq
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveQixi(BusUser busUser, QixiSaveReq QixiSaveReq) {
        QixiMain QixiMain = null;
        Double num = 0.0;
        int f = 0;
        if(QixiSaveReq.getId() == 0){//新增
            QixiMain = new QixiMain();
            BeanUtils.copyProperties(QixiSaveReq,QixiMain);
            QixiMain.setBusId(busUser.getId());
            QixiMain.setCreatetime(new Date());
            QixiMain.setFollowQrCode(QixiMain.getFollowQrCode().split("/upload").length > 1
                    ?QixiMain.getFollowQrCode().split("/upload")[1]:QixiMain.getFollowQrCode());
            qixiMainService.insert(QixiMain);
        }else{//编辑
            QixiMain = qixiMainService.selectById(QixiSaveReq.getId());
            if(CommonUtil.isEmpty(QixiMain)){
                throw new QixiException(ResponseEnums.QIXI_HAS5);
            }
            if(QixiMain.getActivityBeginTime().getTime() < new Date().getTime()){
                throw new QixiException(ResponseEnums.QIXI_HAS6);
            }
            if(QixiMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new QixiException(ResponseEnums.DIFF_USER);
            }
            BeanUtils.copyProperties(QixiSaveReq,QixiMain);
            QixiMain.setFollowQrCode(QixiMain.getFollowQrCode().split("/upload").length > 1
                    ?QixiMain.getFollowQrCode().split("/upload")[1]:QixiMain.getFollowQrCode());
            qixiMainService.updateById(QixiMain);
            //删除
            //兑奖地址
            qixiAddressService.delete(new EntityWrapper<QixiAddress>().eq("act_id",QixiMain.getId()));
            //广告轮播图
            qixiAdService.delete(new EntityWrapper<QixiAd>().eq("act_id",QixiMain.getId()));
            //奖品
            List<QixiPrize> QixiPrizes = qixiPrizeService.selectList(new EntityWrapper<QixiPrize>().eq("act_id",QixiMain.getId()));
            if(QixiPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(QixiPrize QixiPrize : QixiPrizes){
                    if(QixiPrize.getType() == 1){
                        num += QixiPrize.getNum();
                        f = 1;
                    }
                    ids.add(QixiPrize.getId());
                }
                qixiPrizeImgService.delete(new EntityWrapper<QixiPrizeImg>().in("prize_id",ids));
            }
            qixiPrizeService.delete(new EntityWrapper<QixiPrize>().eq("act_id",QixiMain.getId()));
        }
        Double fenbi = 0.0;
        //奖品
        if(CommonUtil.isNotEmpty(QixiSaveReq.getQixiPrizeReqs())){
            for(QixiPrizeReq QixiPrizeReq :QixiSaveReq.getQixiPrizeReqs()){
                if(QixiPrizeReq.getType()==1){
                    fenbi += QixiPrizeReq.getNum();
                }
                QixiPrize QixiPrize = new QixiPrize();
                BeanUtils.copyProperties(QixiPrizeReq,QixiPrize);
                QixiPrize.setActId(QixiMain.getId());
                qixiPrizeService.insert(QixiPrize);
                if(QixiPrizeReq.getQixiPrizeImgReqs().size() > 0){
                    for(QixiPrizeImgReq QixiPrizeImgReq : QixiPrizeReq.getQixiPrizeImgReqs()){
                        QixiPrizeImg QixiPrizeImg = new QixiPrizeImg();
                        BeanUtils.copyProperties(QixiPrizeImgReq,QixiPrizeImg);
                        QixiPrizeImg.setPrizeId(QixiPrize.getId());
                        QixiPrizeImg.setImgUrl(QixiPrizeImg.getImgUrl().split("/upload").length>1?
                                QixiPrizeImg.getImgUrl().split("/upload")[1]:QixiPrizeImg.getImgUrl());
                        qixiPrizeImgService.insert(QixiPrizeImg);
                    }
                }
            }
        }
        //兑奖地址
        if(CommonUtil.isNotEmpty(QixiSaveReq.getQixiAddressReqs())){
            for(QixiAddressReq QixiAddressReq :QixiSaveReq.getQixiAddressReqs()){
                QixiAddress QixiAddress = new QixiAddress();
                BeanUtils.copyProperties(QixiAddressReq,QixiAddress);
                QixiAddress.setActId(QixiMain.getId());
                qixiAddressService.insert(QixiAddress);
            }
        }
        //广告轮播图
        if(CommonUtil.isNotEmpty(QixiSaveReq.getQixiAdReqs())){
            for(QixiAdReq QixiAdReq :QixiSaveReq.getQixiAdReqs()){
                QixiAd QixiAd = new QixiAd();
                BeanUtils.copyProperties(QixiAdReq,QixiAd);
                QixiAd.setActId(QixiMain.getId());
                QixiAd.setUrl(QixiAd.getUrl().split("/upload").length>1?
                        QixiAd.getUrl().split("/upload")[1]:QixiAd.getUrl());
                qixiAdService.insert(QixiAd);
            }
        }
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new QixiException(ResponseEnums.QIXI_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new QixiException(ResponseEnums.QIXI_HAS14);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(QixiMain.getId());
                updateFenbiReduceReq.setFreType(39);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new QixiException(ResponseEnums.QIXI_HAS15);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new QixiException(ResponseEnums.QIXI_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, QixiMain.getId(), 39, 1, "浪漫七夕活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new QixiException(ResponseEnums.QIXI_HAS8);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    /**
     * 分页获取核销授权
     * @param QixiAuthorityListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<QixiAuthorityListRes>> getQixiAuthorityList(BusUser busUser, QixiAuthorityListPageReq QixiAuthorityListPageReq) {
        Page<QixiAuthority> page = new Page<>(QixiAuthorityListPageReq.getCurrent(),QixiAuthorityListPageReq.getSize());
        List<QixiAuthority> QixiAuthorityList = qixiAuthorityService.selectPage(page,
                new EntityWrapper<QixiAuthority>().eq("act_id",QixiAuthorityListPageReq.getActId())
                        .eq("delete_status",0)).getRecords();
        List<QixiAuthorityListRes> QixiAuthorityListRes = new ArrayList<>();
        for (QixiAuthority QixiAuthority : QixiAuthorityList){
            QixiAuthorityListRes QixiAuthorityListRes1 = new QixiAuthorityListRes();
            BeanUtils.copyProperties(QixiAuthority,QixiAuthorityListRes1);
            QixiAuthorityListRes.add(QixiAuthorityListRes1);
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",QixiAuthorityListRes,pageDTO);
    }
    /**
     * 获取奖品类型列表
     *
     */
    @Override
    public ResponseDTO<List<QixiPrizeTypeListRes>> getQixiPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<QixiPrizeTypeListRes> QixiPrizeTypeListRes = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                QixiPrizeTypeListRes qixiPrizeTypeListRes = new QixiPrizeTypeListRes();
                qixiPrizeTypeListRes.setName(dictApiRes.getItemValue());
                qixiPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                QixiPrizeTypeListRes.add(qixiPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",QixiPrizeTypeListRes);
    }
    /**
     * 删除核销授权
     * @param QixiAuthorityIdsReq
     * @return
     */
    @Override
    public ResponseDTO removeQixiAuthority(BusUser busUser, QixiAuthorityIdsReq QixiAuthorityIdsReq) {
        QixiMain QixiMain = qixiMainService.selectById(QixiAuthorityIdsReq.getActId());
        if(CommonUtil.isNotEmpty(QixiMain)) {
            if (QixiMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new QixiException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new QixiException(ResponseEnums.QIXI_HAS5);
        }
        QixiAuthority QixiAuthority = new QixiAuthority();
        QixiAuthority.setDeleteStatus(1);
        qixiAuthorityService.update(QixiAuthority,new EntityWrapper<QixiAuthority>().in("id",QixiAuthorityIdsReq.getIds()));
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
            QixiMain main = qixiMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = qixiCashPrizeApplyDAO.findExports(params);
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
            msg.put("message", "浪漫七夕活动记录导出excel失败！");
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
                if ("7".equals(turPriType)) {// 优惠券
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
