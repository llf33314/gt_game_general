package com.gt.game.core.service.countmoney.impl;

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
import com.gt.game.core.bean.countmoney.req.*;
import com.gt.game.core.bean.countmoney.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.countmoney.CountmoneyRecordDAO;
import com.gt.game.core.entity.countmoney.CountmoneyDetail;
import com.gt.game.core.entity.countmoney.CountmoneyMain;
import com.gt.game.core.entity.countmoney.CountmoneyProbabilityset;
import com.gt.game.core.entity.countmoney.CountmoneyRecord;
import com.gt.game.core.exception.countmoney.CountmoneyException;
import com.gt.game.core.service.countmoney.*;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import com.sun.xml.internal.bind.v2.TODO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 疯狂数钱  服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class CountmoneyServiceImpl implements CountmoneyService {

    @Autowired
    CountmoneyMainService countmoneyMainService;

    @Autowired
    CountmoneyProbabilitysetService countmoneyProbabilitysetService;

    @Autowired
    CountmoneyDetailService countmoneyDetailService;

    @Autowired
    CountmoneyRecordService countmoneyRecordService;

    @Autowired
    CountmoneyRecordDAO countmoneyRecordDAO;

    @Autowired
    ApplyProperties applyProperties;

    /**
     * 获取手机端链接
     *
     * @param loginPbUser
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(WxPublicUsers loginPbUser, MobileUrlReq mobileUrlReq) {

        String url = applyProperties.getMobileBaseUrl() + "countMoney/"+loginPbUser.getId()+"/"+ mobileUrlReq.getMainId()+"/79B4DE7C/userGrant.do";
        return new MobileUrlRes(url);
    }

    /**
     * 分页获取疯狂数钱活动列表
     * @param loginPbUser
     * @param countmoneyListReq
     * @return
     */
    @Override
    public ResponseDTO<List<CountmoneyListRes>> getCountMoneyList(WxPublicUsers loginPbUser, CountmoneyListReq countmoneyListReq) {

        EntityWrapper<CountmoneyMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_wx_userid", loginPbUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(countmoneyListReq.getActName()), "act_name", countmoneyListReq.getActName());
        if (countmoneyListReq.getStatus() != -1) {   // TODO -1 全部 0 未开始 1 进行中 2 已结束
            if (countmoneyListReq.getStatus() == 0) {
                entityWrapper.where("act_beginTime > {0}", new Date());
            }
            if (countmoneyListReq.getStatus() == 1) {
                entityWrapper.where("act_beginTime <= {0}", new Date());
                entityWrapper.and();
                entityWrapper.where("act_endTime > {0}", new Date());
            }
            if (countmoneyListReq.getStatus() == 2) {
                entityWrapper.where("act_endTime <= {0}", new Date());
            }
        }
        Page<CountmoneyMain> page = new Page<>(countmoneyListReq.getCurrent(), countmoneyListReq.getSize());
        List<CountmoneyMain> countmoneyMainList = countmoneyMainService.selectPage(page, entityWrapper).getRecords();

        List<CountmoneyListRes> countMoneyListResList = new ArrayList<>();
        for (CountmoneyMain countmoneyMain : countmoneyMainList) {
            CountmoneyListRes countMoneyListRes = new CountmoneyListRes();
            countMoneyListRes.setId(countmoneyMain.getId());
            countMoneyListRes.setActName(countmoneyMain.getActName());
            countMoneyListRes.setActType(countmoneyMain.getActType());
            countMoneyListRes.setActBeginTime(countmoneyMain.getActBeginTime());
            countMoneyListRes.setActEndTime(countmoneyMain.getActEndTime());

            Date date = new Date();
            if (countmoneyMain.getActBeginTime().getTime() > date.getTime()) {
                countMoneyListRes.setStatus(0);
            } else if (countmoneyMain.getActBeginTime().getTime() <= date.getTime() && countmoneyMain.getActEndTime().getTime() >= date.getTime()) {
                countMoneyListRes.setStatus(1);
            } else if (countmoneyMain.getActEndTime().getTime() < date.getTime()) {
                countMoneyListRes.setStatus(2);
            }

            countMoneyListResList.add(countMoneyListRes);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取疯狂数钱活动列表成功", countMoneyListResList, pageDTO);
    }

    /**
     * 统计疯狂数钱活动总数
     * @param loginPbUser
     * @param countmoneyCountActivityReq
     * @return
     */
    @Override
    public CountmoneyCountActivityRes countActivity(WxPublicUsers loginPbUser, CountmoneyCountActivityReq countmoneyCountActivityReq) {

        EntityWrapper<CountmoneyMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_wx_userid", loginPbUser.getId());
        entityWrapper.like(CommonUtil.isNotEmpty(countmoneyCountActivityReq.getName()), "act_name", countmoneyCountActivityReq.getName());

        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        List<CountmoneyMain> countmoneyMainList = countmoneyMainService.selectList(entityWrapper);
        Date date = new Date();
        for (CountmoneyMain countmoneyMain : countmoneyMainList) {
            if (countmoneyMain.getActBeginTime().getTime() > date.getTime()) {
                count2++; //   TODO    未开始
            } else if (countmoneyMain.getActBeginTime().getTime() <= date.getTime() && countmoneyMain.getActEndTime().getTime() >= date.getTime()) {
                count3++;  // TODO    进行中
            } else if (countmoneyMain.getActEndTime().getTime() < date.getTime()) {
                count4++;  // TODO    已结束
            }
        }
        CountmoneyCountActivityRes countmoneyCountActivityRes = new CountmoneyCountActivityRes();
        countmoneyCountActivityRes.setCount2(count2);
        countmoneyCountActivityRes.setCount3(count3);
        countmoneyCountActivityRes.setCount4(count4);
        countmoneyCountActivityRes.setCount1(count2+count3+count4);  //  TODO 全部
        return  countmoneyCountActivityRes;
    }

    /**
     * 新增疯狂数钱活动
     * @param loginPbUser
     * @param countmoneyAddReq
     */
    @Override
    public void addCountmoney(BusUser busUser ,WxPublicUsers loginPbUser, CountmoneyAddReq countmoneyAddReq) {

        CountmoneyMain countmoneyMain = new CountmoneyMain();
        countmoneyMain.setActWxUserid(loginPbUser.getId());

        //TODO 基础设置
        countmoneyMain.setActType(countmoneyAddReq.getActType());
        countmoneyMain.setActName(countmoneyAddReq.getActName());
        countmoneyMain.setActBeginTime(countmoneyAddReq.getActBeginTime());
        countmoneyMain.setActEndTime(countmoneyAddReq.getActEndTime());
        countmoneyMain.setActDescribe(countmoneyAddReq.getActDescribe());
        countmoneyMain.setActNotStartedTips(countmoneyAddReq.getActNotStartedTips());
        countmoneyMain.setActScrBgmName(countmoneyAddReq.getActScrBgmName());
        countmoneyMain.setActScrBgmUrl(countmoneyAddReq.getActScrBgmUrl());

        //TODO  规则设置
        countmoneyMain.setActGameTime(countmoneyAddReq.getActGameTime());
        countmoneyMain.setActTotalOfAct(countmoneyAddReq.getActTotalOfAct());
        countmoneyMain.setActCountOfDay(countmoneyAddReq.getActCountOfDay());

       //TODO 兑奖设置

      countmoneyMain.setActAwardingTime(countmoneyAddReq.getActAwardingTime());
      countmoneyMain.setActAwardingAddress(countmoneyAddReq.getActAwardingAddress());
      countmoneyMain.setActAwardingTips(countmoneyAddReq.getActAwardingTips());

      if(countmoneyAddReq.getActType()==2){  //TODO  数钱折算模式
          countmoneyMain.setActRateFenbi(countmoneyAddReq.getActRateFenbi());
          countmoneyMain.setActRateRmb(countmoneyAddReq.getActRateRmb());
          countmoneyMain.setActFrozenFenbi(countmoneyAddReq.getActFrozenFenbi());
      }

       //TODO 奖品设置
       countmoneyMain.setActIsShowNums(countmoneyAddReq.getActIsShowNums());

       countmoneyMainService.insert(countmoneyMain);

       //TODO  概率设置
       for(CountmoneyProbabilitysetReq countmoneyProbabilitysetReq:countmoneyAddReq.getCountmoneyProbabilitysetList()){

           CountmoneyProbabilityset countmoneyProbabilityset = new CountmoneyProbabilityset();
           countmoneyProbabilityset.setActId(countmoneyMain.getId());
           countmoneyProbabilityset.setFenbiType(countmoneyProbabilitysetReq.getFenbiType());
           countmoneyProbabilityset.setFenbiChance(countmoneyProbabilitysetReq.getFenbiChance());

           countmoneyProbabilitysetService.insert(countmoneyProbabilityset);
       }
        Double fenbi = 0.0;
        if(countmoneyAddReq.getActType()==1){  //TODO  排名中奖模式  奖项设置
            for(CountmoneyPrizeSetReq countmoneyPrizeSetReq:countmoneyAddReq.getPrizeSetList()){
                if (countmoneyPrizeSetReq.getTurPrizeType() == 1) {
                    fenbi += countmoneyPrizeSetReq.getTurPrizeNums();
                }
                CountmoneyDetail countmoneyDetail = new CountmoneyDetail();
                countmoneyDetail.setActId(countmoneyMain.getId());
                countmoneyDetail.setTurPrizeName(countmoneyPrizeSetReq.getTurPrizeName());
                countmoneyDetail.setTurPrizeType(countmoneyPrizeSetReq.getTurPrizeType());
                countmoneyDetail.setTurPrizeUnit(countmoneyPrizeSetReq.getTurPrizeUnit());
                countmoneyDetail.setTurPrizeNums(countmoneyPrizeSetReq.getTurPrizeNums());

                countmoneyDetailService.insert(countmoneyDetail);
            }
        }

        if(fenbi > 0){//冻结粉币
            // 判断账户中的粉币是否足够
            if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS7);
            }
            //构建冻结信息
            FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, countmoneyMain.getId(), 17, 1, "疯狂数钱活动支出", 0);
            // 保存冻结信息
            if(ffr!=null){
                FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                if(axisResult.getCode() != 0){
                    throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS8);
                }
            }
        }
    }

    /**
     * 通过活动id查询疯狂数钱活动
     * @param busUser
     * @param countmoneyGetActivityReq
     * @return
     */
    @Override
    public CountmoneyGetActivityRes getActivityById(BusUser busUser, CountmoneyGetActivityReq countmoneyGetActivityReq) {

        CountmoneyMain countmoneyMain = countmoneyMainService.selectById(countmoneyGetActivityReq.getId());
        CountmoneyGetActivityRes countmoneyGetActivityRes = new CountmoneyGetActivityRes();
        //TODO 基础设置
        countmoneyGetActivityRes.setActType(countmoneyMain.getActType());
        countmoneyGetActivityRes.setActName(countmoneyMain.getActName());
        countmoneyGetActivityRes.setActBeginTime(countmoneyMain.getActBeginTime());
        countmoneyGetActivityRes.setActEndTime(countmoneyMain.getActEndTime());
        countmoneyGetActivityRes.setActDescribe(countmoneyMain.getActDescribe());
        countmoneyGetActivityRes.setActNotStartedTips(countmoneyMain.getActNotStartedTips());
        countmoneyGetActivityRes.setActScrBgmName(countmoneyMain.getActScrBgmName());
        countmoneyGetActivityRes.setActScrBgmUrl(countmoneyMain.getActScrBgmUrl());

        //TODO  规则设置
        countmoneyGetActivityRes.setActGameTime(countmoneyMain.getActGameTime());
        countmoneyGetActivityRes.setActTotalOfAct(countmoneyMain.getActTotalOfAct());
        countmoneyGetActivityRes.setActCountOfDay(countmoneyMain.getActCountOfDay());

        //TODO 兑奖设置
        countmoneyGetActivityRes.setActAwardingTime(countmoneyMain.getActAwardingTime());
        countmoneyGetActivityRes.setActAwardingAddress(countmoneyMain.getActAwardingAddress());
        countmoneyGetActivityRes.setActAwardingTips(countmoneyMain.getActAwardingTips());

        if (countmoneyMain.getActType() == 2) {  //TODO  数钱折算模式
            countmoneyGetActivityRes.setActRateFenbi(countmoneyMain.getActRateFenbi());
            countmoneyGetActivityRes.setActRateRmb(countmoneyMain.getActRateRmb());
            countmoneyGetActivityRes.setActFrozenFenbi(countmoneyMain.getActFrozenFenbi());
        }

        //TODO 奖品设置
        countmoneyGetActivityRes.setActIsShowNums(countmoneyMain.getActIsShowNums());


        //TODO  概率设置
        EntityWrapper<CountmoneyProbabilityset> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_id", countmoneyMain.getId());
        List<CountmoneyProbabilityset> countmoneyProbabilitysetList = countmoneyProbabilitysetService.selectList(entityWrapper);

        List<CountmoneyProbabilitysetReq> list1 = new ArrayList<>();
        for (CountmoneyProbabilityset countmoneyProbabilityset : countmoneyProbabilitysetList) {

            CountmoneyProbabilitysetReq countmoneyProbabilitysetReq = new CountmoneyProbabilitysetReq();
            countmoneyProbabilitysetReq.setFenbiType(countmoneyProbabilityset.getFenbiType());
            countmoneyProbabilitysetReq.setFenbiChance(countmoneyProbabilityset.getFenbiChance());
            list1.add(countmoneyProbabilitysetReq);
        }

        countmoneyGetActivityRes.setCountmoneyProbabilitysetList(list1);  //TODO  概率设置

        List<CountmoneyPrizeSetReq> list2 = new ArrayList<>();
        if (countmoneyMain.getActType() == 1) {  //TODO  排名中奖模式   奖品设置

            EntityWrapper<CountmoneyDetail> entityWrapper2 = new EntityWrapper();
            entityWrapper2.eq("act_id", countmoneyMain.getId());
            List<CountmoneyDetail> countmoneyDetailList = countmoneyDetailService.selectList(entityWrapper2);

            for (CountmoneyDetail countmoneyDetail : countmoneyDetailList) {

                CountmoneyPrizeSetReq countmoneyPrizeSetReq = new CountmoneyPrizeSetReq();
                countmoneyPrizeSetReq.setTurPrizeName(countmoneyDetail.getTurPrizeName());
                countmoneyPrizeSetReq.setTurPrizeType(countmoneyDetail.getTurPrizeType());
                countmoneyPrizeSetReq.setTurPrizeUnit(countmoneyDetail.getTurPrizeUnit());
                countmoneyPrizeSetReq.setTurPrizeNums(countmoneyDetail.getTurPrizeNums());
                list2.add(countmoneyPrizeSetReq);
            }
            countmoneyGetActivityRes.setPrizeSetList(list2);
        }
        return countmoneyGetActivityRes;
    }

    /**
     * 编辑疯狂数钱活动设置
     * @param busUser
     * @param countmoneyModfiyReq
     */
    @Override
    public void modfiyBasicsCountmoney(BusUser busUser, CountmoneyModfiyReq countmoneyModfiyReq) {

        CountmoneyMain countmoneyMain = countmoneyMainService.selectById(countmoneyModfiyReq.getId());
        if(CommonUtil.isEmpty(countmoneyMain)){
            throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS5);
        }
        if(countmoneyMain.getActBeginTime().getTime() < new Date().getTime()){
            throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS10);
        }

        //TODO 基础设置
        countmoneyMain.setActType(countmoneyModfiyReq.getActType());
        countmoneyMain.setActName(countmoneyModfiyReq.getActName());
        countmoneyMain.setActBeginTime(countmoneyModfiyReq.getActBeginTime());
        countmoneyMain.setActEndTime(countmoneyModfiyReq.getActEndTime());
        countmoneyMain.setActDescribe(countmoneyModfiyReq.getActDescribe());
        countmoneyMain.setActNotStartedTips(countmoneyModfiyReq.getActNotStartedTips());
        countmoneyMain.setActScrBgmName(countmoneyModfiyReq.getActScrBgmName());
        countmoneyMain.setActScrBgmUrl(countmoneyModfiyReq.getActScrBgmUrl());

        //TODO  规则设置
        countmoneyMain.setActGameTime(countmoneyModfiyReq.getActGameTime());
        countmoneyMain.setActTotalOfAct(countmoneyModfiyReq.getActTotalOfAct());
        countmoneyMain.setActCountOfDay(countmoneyModfiyReq.getActCountOfDay());

        //TODO 兑奖设置
        countmoneyMain.setActAwardingTime(countmoneyModfiyReq.getActAwardingTime());
        countmoneyMain.setActAwardingAddress(countmoneyModfiyReq.getActAwardingAddress());
        countmoneyMain.setActAwardingTips(countmoneyModfiyReq.getActAwardingTips());

        if (countmoneyMain.getActType() == 2) {  //TODO  数钱折算模式
            countmoneyMain.setActRateFenbi(countmoneyModfiyReq.getActRateFenbi());
            countmoneyMain.setActRateRmb(countmoneyModfiyReq.getActRateRmb());
            countmoneyMain.setActFrozenFenbi(countmoneyModfiyReq.getActFrozenFenbi());
        }

        //TODO 奖品设置
        countmoneyMain.setActIsShowNums(countmoneyModfiyReq.getActIsShowNums());

        countmoneyMainService.updateById(countmoneyMain);

        //TODO 清空活动概率设置
        EntityWrapper<CountmoneyProbabilityset> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_id", countmoneyMain.getId());
        countmoneyProbabilitysetService.delete(entityWrapper);

        //TODO  添加概率设置
        for(CountmoneyProbabilitysetReq countmoneyProbabilitysetReq:countmoneyModfiyReq.getCountmoneyProbabilitysetList()){

            CountmoneyProbabilityset countmoneyProbabilityset = new CountmoneyProbabilityset();
            countmoneyProbabilityset.setActId(countmoneyMain.getId());
            countmoneyProbabilityset.setFenbiType(countmoneyProbabilitysetReq.getFenbiType());
            countmoneyProbabilityset.setFenbiChance(countmoneyProbabilitysetReq.getFenbiChance());

            countmoneyProbabilitysetService.insert(countmoneyProbabilityset);
        }

        Double fenbi = 0.0;
        Double num   = 0.0;
        int f = 0;

        EntityWrapper<CountmoneyDetail> entityWrapper5 = new EntityWrapper();
        entityWrapper5.eq("act_id",countmoneyModfiyReq.getId());
        List<CountmoneyDetail> countmoneyDetailList = countmoneyDetailService.selectList(entityWrapper5);
        if(countmoneyDetailList.size() > 0) {
            for (CountmoneyDetail countmoneyDetail : countmoneyDetailList) {
                if (countmoneyDetail.getTurPrizeType() == 1) {
                    num += countmoneyDetail.getTurPrizeNums();
                    f = 1;
                }
            }
        }

        //TODO 清空奖品设置
        EntityWrapper<CountmoneyDetail> entityWrapper2 = new EntityWrapper();
        entityWrapper2.eq("act_id", countmoneyMain.getId());
        countmoneyDetailService.delete(entityWrapper2);


        if(countmoneyMain.getActType()==1){  //TODO  排名中奖模式  奖品设置
            for(CountmoneyPrizeSetReq countmoneyPrizeSetReq:countmoneyModfiyReq.getPrizeSetList()){
                if (countmoneyPrizeSetReq.getTurPrizeType() == 1) {
                    fenbi += countmoneyPrizeSetReq.getTurPrizeNums();
                }
                CountmoneyDetail countmoneyDetail = new CountmoneyDetail();
                countmoneyDetail.setActId(countmoneyMain.getId());
                countmoneyDetail.setTurPrizeName(countmoneyPrizeSetReq.getTurPrizeName());
                countmoneyDetail.setTurPrizeType(countmoneyPrizeSetReq.getTurPrizeType());
                countmoneyDetail.setTurPrizeUnit(countmoneyPrizeSetReq.getTurPrizeUnit());
                countmoneyDetail.setTurPrizeNums(countmoneyPrizeSetReq.getTurPrizeNums());

                countmoneyDetailService.insert(countmoneyDetail);
            }
        }

        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if ((fenbi - num) <= (0 - num)) {
                    throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi - num)) {
                    throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS7);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(countmoneyMain.getId());
                updateFenbiReduceReq.setFreType(17);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi - num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if (axisResult.getCode() != 0) {
                    throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS8);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, countmoneyMain.getId(), 17, 1, "疯狂数钱活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS8);
                    }
                }
            }
        }
    }

    /**
     * 删除疯狂数钱活动
     * @param busUser
     */
    @Override
    public void delCountmoney(BusUser busUser, CountmoneyDelReq countmoneyDelReq) {

        CountmoneyMain countmoneyMain = countmoneyMainService.selectById(countmoneyDelReq.getId());
        if(CommonUtil.isNotEmpty(countmoneyMain)) {
            if (countmoneyMain.getActBeginTime().getTime() < new Date().getTime() && countmoneyMain.getActEndTime().getTime() > new Date().getTime()) {
                throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS11);
            }

            List<CountmoneyRecord> countmoneyRecordList = countmoneyRecordService.selectList(
                    new EntityWrapper<CountmoneyRecord>().eq("win_act_id", countmoneyDelReq.getId()).eq("win_status", 3));
            if (countmoneyRecordList.size() > 0) {
                throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS13);

            }
        }
        //TODO  删除活动
        boolean b = countmoneyMainService.deleteById(countmoneyDelReq.getId());
        if(b==false){
            throw  new CountmoneyException(ResponseEnums.COUNTMONEY_HAS6);
        }

        EntityWrapper<CountmoneyDetail> entityWrapper3 = new EntityWrapper<>();
        entityWrapper3.eq("act_id",countmoneyDelReq.getId());
        List<CountmoneyDetail> countmoneyDetailList = countmoneyDetailService.selectList(entityWrapper3);

        boolean ff = false;
        if(countmoneyDetailList.size() > 0){
            for(CountmoneyDetail countmoneyDetail : countmoneyDetailList){
                if(countmoneyDetail.getTurPrizeType() == 1){
                    ff = true;
                }
            }
        }

        //TODO  删除奖品设置
        EntityWrapper<CountmoneyDetail> entityWrapper5 = new EntityWrapper<>();
        entityWrapper3.eq("act_id",countmoneyDelReq.getId());
        countmoneyDetailService.delete(entityWrapper5);

        //删除冻结信息
        if(ff){
            FenbiSurplus fenbiSurplus = new FenbiSurplus();
            fenbiSurplus.setBusId(busUser.getId());
            fenbiSurplus.setFkId(countmoneyMain.getId());
            fenbiSurplus.setFre_type(17);
            fenbiSurplus.setRec_type(1);
            AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
            if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                // 获取冻结信息中粉币剩余量
                FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                fenbiSurplus1.setBusId(busUser.getId());
                fenbiSurplus1.setFkId(countmoneyMain.getId());
                fenbiSurplus1.setRec_type(1);
                fenbiSurplus1.setFre_type(17);
                AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                if(axisResult.getCode() != 0){
                    throw new CountmoneyException(ResponseEnums.COUNTMONEY_HAS14);
                }
            }
        }
    }

    /**
     * 分页获取分页获取中奖记录列表
     * @param busUser
     * @param countmoneyGetWinningReq
     * @return
     */
    @Override
    public ResponseDTO<List<CountmoneyGetWinningRes>> getWinningList(BusUser busUser, CountmoneyGetWinningReq countmoneyGetWinningReq) {

        Page<CountmoneyGetWinningRes> page = new Page<>(countmoneyGetWinningReq.getCurrent(),countmoneyGetWinningReq.getSize());
        List<CountmoneyGetWinningRes> countmoneyGetWinningResList = countmoneyRecordDAO.queryRecodList(page,countmoneyGetWinningReq);

        //TODO  获取用户名
        if(countmoneyGetWinningResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < countmoneyGetWinningResList.size(); i++) {
                ids.append(countmoneyGetWinningResList.get(i).getMemberId());
                if (countmoneyGetWinningResList.size() > 1 && i < countmoneyGetWinningResList.size() - 1) {
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
            for (int i = 0; i < countmoneyGetWinningResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == countmoneyGetWinningResList.get(i).getMemberId().intValue()) {
                        countmoneyGetWinningResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(countmoneyGetWinningResList.get(i).getMemberName())) {
                    countmoneyGetWinningResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取疯狂数钱中奖记录列表成功",countmoneyGetWinningResList,pageDTO);
    }

    /**
     * 疯狂数钱活动中奖记录发放奖品
     * @param busUser
     * @param countmoneyEditApplyReq
     * @return
     */
    @Override
    public ResponseDTO editCountmoneyApply(BusUser busUser, CountmoneyEditApplyReq countmoneyEditApplyReq) {

        CountmoneyRecord countmoneyRecord = countmoneyRecordService.selectById(countmoneyEditApplyReq.getId());
        if(CommonUtil.isNotEmpty(countmoneyRecord)){
            CountmoneyMain countmoneyMain = countmoneyMainService.selectById(countmoneyEditApplyReq.getId());
            CountmoneyDetail countmoneyDetail = countmoneyDetailService.selectById(countmoneyRecord.getWinPrizeId());
            if(countmoneyDetail.getTurPrizeType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(countmoneyMain.getActBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new CountmoneyException(ResponseEnums.COUNTMONEY_HAS1);
                } else if (!DateTimeKit.laterThanNow(countmoneyMain.getActEndTime())) {
                    //""已过兑奖时间！";
                    throw  new CountmoneyException(ResponseEnums.COUNTMONEY_HAS2);
                }
            }
            if (countmoneyRecord.getWinStatus() == 3) {
                // 更改记录状态
                countmoneyRecord.setWinStatus(2);
                countmoneyRecord.setWinCreatetime(new Date());
                countmoneyRecordService.updateById(countmoneyRecord);
            } else if (countmoneyRecord.getWinStatus()== 2){//已兑奖
                throw  new CountmoneyException(ResponseEnums.COUNTMONEY_HAS3);
            }else{//还未提交
                throw  new CountmoneyException(ResponseEnums.COUNTMONEY_HAS4);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }

    /**
     * 导出疯狂数钱活动中奖记录
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> exportCountmoney(Map<String, Object> params, BusUser busUser) {

        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            CountmoneyMain countmoneyMain = countmoneyMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = countmoneyRecordDAO.findExports(params);
            String title = "活动名称：" + countmoneyMain.getActName() + "，开始时间：" + sdf.format(countmoneyMain.getActBeginTime()) + "结束时间："
                    + sdf.format(countmoneyMain.getActEndTime());
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("win_wx_member_id"));
                    if (list.size() > 1 && i < list.size() - 1) {
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
            msg.put("fileName", countmoneyMain.getActName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "元宵点灯活动中奖记录导出excel失败！");
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
        createCell(wb, row1, 3, "中奖时间", font1);
        createCell(wb, row1, 4, "中奖人联系方式", font1);
        createCell(wb, row1, 5, "中奖人昵称", font1);
        createCell(wb, row1, 6, "状态", font1);
        createCell(wb, row1, 7, "兑奖码", font1);
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
            createCell(wb, rowData, 1, delWithColumn(map.get("tur_prize_name")), font1);
            String turPriType = delWithColumn(map.get("tur_prize_type"));
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
            createCell(wb, rowData, 2, priTypeName + "/" + delWithColumn(map.get("tur_prize_unit")) + priTypeUnit, font1);


            createCell(wb, rowData, 3,
                    delWithColumn(CommonUtil.isEmpty(map.get("cashTime")) ? ""
                            : DateTimeKit.format(
                            DateTimeKit.parseDate(map.get("cashTime").toString(), "yyyy/MM/dd HH:mm:ss"),
                            "yyyy-MM-dd HH:mm")),
                    font1);
            createCell(wb, rowData, 4, delWithColumn(map.get("win_phone")), font1);
            createCell(wb, rowData, 5,
                    CommonUtil.isEmpty(map.get("nickname")) ? "游客" : map.get("nickname").toString(),
                    font1);
            if ("1".equals(delWithColumn(map.get("win_status")).toString())) {
                createCell(wb, rowData, 6, "未兑奖", font1);
            } else if ("2".equals(delWithColumn(map.get("win_status")).toString())) {
                createCell(wb, rowData, 6, "已兑奖", font1);
            } else {
                createCell(wb, rowData, 6, "已提交", font1);
            }
            createCell(wb, rowData, 7, delWithColumn(map.get("win_exchangeCode")), font1);

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

    /**
     * 批量删除疯狂数钱活动中奖记录
     * @param busUser
     * @param countmoneyDelWinningReq
     */
    @Override
    public void delCountmoneyWinning(BusUser busUser, CountmoneyDelWinningReq countmoneyDelWinningReq) {

        countmoneyRecordService.deleteBatchIds(countmoneyDelWinningReq.getId());
    }

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<List<CountmoneyPrizeTypeListRes>> getCountmoneyPrizeType(BusUser busUser) {

        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<CountmoneyPrizeTypeListRes> countmoneyPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                CountmoneyPrizeTypeListRes countmoneyPrizeTypeListRes = new CountmoneyPrizeTypeListRes();
                countmoneyPrizeTypeListRes.setName(dictApiRes.getItemValue());
                countmoneyPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                countmoneyPrizeTypeListResList.add(countmoneyPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",countmoneyPrizeTypeListResList);
    }

}