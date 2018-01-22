package com.gt.game.core.service.countmoney.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.countmoney.req.*;
import com.gt.game.core.bean.countmoney.res.CountmoneyGetWinningRes;
import com.gt.game.core.bean.countmoney.res.CountmoneyListRes;
import com.gt.game.core.bean.countmoney.res.CountmoneyCountActivityRes;
import com.gt.game.core.bean.countmoney.res.CountmoneyGetActivityRes;
import com.gt.game.core.bean.lantern.req.LanternDelReq;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.countmoney.CountmoneyDetail;
import com.gt.game.core.entity.countmoney.CountmoneyMain;
import com.gt.game.core.entity.countmoney.CountmoneyProbabilityset;
import com.gt.game.core.service.countmoney.CountmoneyDetailService;
import com.gt.game.core.service.countmoney.CountmoneyMainService;
import com.gt.game.core.service.countmoney.CountmoneyProbabilitysetService;
import com.gt.game.core.service.countmoney.CountmoneyService;
import com.gt.game.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /**
     * 获取手机端链接
     *
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {
        return null;
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
                count2++; //  TODO    未开始
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
    public void addCountmoney(WxPublicUsers loginPbUser, CountmoneyAddReq countmoneyAddReq) {

        CountmoneyMain countmoneyMain = new CountmoneyMain();
        countmoneyMain.setActWxUserid(loginPbUser.getId());
        //TODO 基础设置
        countmoneyMain.setActType(countmoneyAddReq.getActType());
        countmoneyMain.setActName(countmoneyAddReq.getActName());
        countmoneyMain.setActBeginTime(countmoneyAddReq.getActBeginTime());
        countmoneyMain.setActEndTime(countmoneyAddReq.getActEndTime());
        countmoneyMain.setActDescribe(countmoneyAddReq.getActDescribe());
        countmoneyMain.setActNotStartedTips(countmoneyAddReq.getActNotStartedTips());

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

        if(countmoneyAddReq.getActType()==1){  //TODO  排名中奖模式
            for(CountmoneyPrizeSetReq countmoneyPrizeSetReq:countmoneyAddReq.getPrizeSetList()){

                CountmoneyDetail countmoneyDetail = new CountmoneyDetail();
                countmoneyDetail.setActId(countmoneyMain.getId());
                countmoneyDetail.setTurPrizeName(countmoneyPrizeSetReq.getTurPrizeName());
                countmoneyDetail.setTurPrizeType(countmoneyPrizeSetReq.getTurPrizeType());
                countmoneyDetail.setTurPrizeUnit(countmoneyPrizeSetReq.getTurPrizeUnit());
                countmoneyDetail.setTurPrizeNums(countmoneyPrizeSetReq.getTurPrizeNums());

                countmoneyDetailService.insert(countmoneyDetail);

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

        CountmoneyMain countmoneyMain = new CountmoneyMain();
        //TODO 基础设置
        countmoneyMain.setId(countmoneyModfiyReq.getId());
        countmoneyMain.setActType(countmoneyModfiyReq.getActType());
        countmoneyMain.setActName(countmoneyModfiyReq.getActName());
        countmoneyMain.setActBeginTime(countmoneyModfiyReq.getActBeginTime());
        countmoneyMain.setActEndTime(countmoneyModfiyReq.getActEndTime());
        countmoneyMain.setActDescribe(countmoneyModfiyReq.getActDescribe());
        countmoneyMain.setActNotStartedTips(countmoneyModfiyReq.getActNotStartedTips());


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

        //TODO 清空奖品设置
        EntityWrapper<CountmoneyDetail> entityWrapper2 = new EntityWrapper();
        entityWrapper2.eq("act_id", countmoneyMain.getId());
        countmoneyDetailService.delete(entityWrapper2);

        if(countmoneyMain.getActType()==1){  //TODO  排名中奖模式
            for(CountmoneyPrizeSetReq countmoneyPrizeSetReq:countmoneyModfiyReq.getPrizeSetList()){

                CountmoneyDetail countmoneyDetail = new CountmoneyDetail();
                countmoneyDetail.setActId(countmoneyMain.getId());
                countmoneyDetail.setTurPrizeName(countmoneyPrizeSetReq.getTurPrizeName());
                countmoneyDetail.setTurPrizeType(countmoneyPrizeSetReq.getTurPrizeType());
                countmoneyDetail.setTurPrizeUnit(countmoneyPrizeSetReq.getTurPrizeUnit());
                countmoneyDetail.setTurPrizeNums(countmoneyPrizeSetReq.getTurPrizeNums());

                countmoneyDetailService.insert(countmoneyDetail);
            }
        }
    }

    /**
     * 删除疯狂数钱活动
     * @param busUser
     * @param lanternDelReq
     */
    @Override
    public void delCountmoney(BusUser busUser, LanternDelReq lanternDelReq) {

    }

    /**
     * 分页获取分页获取中奖记录列表
     * @param busUser
     * @param countmoneyGetWinningReq
     * @return
     */
    @Override
    public ResponseDTO<List<CountmoneyGetWinningRes>> getWinningList(BusUser busUser, CountmoneyGetWinningReq countmoneyGetWinningReq) {

        return null;
    }
}