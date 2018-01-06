package com.gt.game.core.service.lantern.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.lantern.req.*;
import com.gt.game.core.bean.lantern.res.LanternCountActivityRes;
import com.gt.game.core.bean.lantern.res.LanternListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.lantern.*;
import com.gt.game.core.service.lantern.*;
import com.gt.game.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * <p>
 * 元宵点灯   服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class LanternServiceImpl implements LanternService {

    @Autowired
    LanternMainService lanternMainService;

    @Autowired
    LanternAddressService lanternAddressService;

    @Autowired
    LanternAdService  lanternAdService;

    @Autowired
    LanternPrizeService lanternPrizeService;

    @Autowired
    LanternPrizeImgService lanternPrizeImgService;

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
     * 分页获取元宵点灯活动列表
     *
     * @param busUser
     * @param lanternListReq
     * @return
     */
    @Override
    public ResponseDTO<List<LanternListRes>> getLanternList(BusUser busUser, LanternListReq lanternListReq) {

        EntityWrapper<LanternMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("bus_id", busUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(lanternListReq.getName()), "name", lanternListReq.getName());
        if (lanternListReq.getStatus() != -1) {   // TODO -1 全部 0 未开始 1 进行中 2 已结束
            if (lanternListReq.getStatus() == 0) {
                entityWrapper.where("activity_begin_time > {0}", new Date());
            }
            if (lanternListReq.getStatus() == 1) {
                entityWrapper.where("activity_begin_time <= {0}", new Date());
                entityWrapper.and();
                entityWrapper.where("activity_end_time > {0}", new Date());
            }
            if (lanternListReq.getStatus() == 2) {
                entityWrapper.where("activity_end_time <= {0}", new Date());
            }
        }
        Page<LanternMain> page = new Page<>(lanternListReq.getCurrent(), lanternListReq.getSize());
        List<LanternMain> LanternMainList = lanternMainService.selectPage(page, entityWrapper).getRecords();

        List<LanternListRes> LanternListResList = new ArrayList<>();
        for (LanternMain lanternMain : LanternMainList) {
            LanternListRes lanternListRes = new LanternListRes();
            lanternListRes.setId(lanternMain.getId());
            lanternListRes.setName(lanternMain.getName());
            lanternListRes.setActivityBeginTime(lanternMain.getActivityBeginTime());
            lanternListRes.setActivityEndTime(lanternMain.getActivityEndTime());
            lanternListRes.setCashPrizeBeginTime(lanternMain.getCashPrizeBeginTime());
            lanternListRes.setCashPrizeEndTime(lanternMain.getCashPrizeEndTime());

            Date date = new Date();
            if (lanternMain.getActivityBeginTime().getTime() > date.getTime()) {
                lanternListRes.setStatus(0);
            } else if (lanternMain.getActivityBeginTime().getTime() <= date.getTime() && lanternMain.getActivityEndTime().getTime() >= date.getTime()) {
                lanternListRes.setStatus(1);
            } else if (lanternMain.getActivityEndTime().getTime() < date.getTime()) {
                lanternListRes.setStatus(2);
            }
            LanternListResList.add(lanternListRes);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取元宵点灯活动列表成功", LanternListResList, pageDTO);
    }

    /**
     * 统计元宵点灯活动总数
     *
     * @param busUser
     * @param lanternCountActivityReq
     * @return
     */
    @Override
    public LanternCountActivityRes countActivity(BusUser busUser, LanternCountActivityReq lanternCountActivityReq) {

        EntityWrapper<LanternMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("bus_id", busUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(lanternCountActivityReq.getName()), "name", lanternCountActivityReq.getName());

        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        List<LanternMain> lanternMainList = lanternMainService.selectList(entityWrapper);
        Date date = new Date();
        for (LanternMain LanternMain : lanternMainList) {
            if (LanternMain.getActivityBeginTime().getTime() > date.getTime()) {
                count2++; //  TODO    未开始
            } else if (LanternMain.getActivityBeginTime().getTime() <= date.getTime() && LanternMain.getActivityEndTime().getTime() >= date.getTime()) {
                count3++;  // TODO    进行中
            } else if (LanternMain.getActivityEndTime().getTime() < date.getTime()) {
                count4++;  // TODO    已结束
            }
        }
        LanternCountActivityRes lanternCountActivityRes = new LanternCountActivityRes();
        lanternCountActivityRes.setCount2(count2);
        lanternCountActivityRes.setCount3(count3);
        lanternCountActivityRes.setCount4(count4);
        lanternCountActivityRes.setCount(count2+count3+count4);  //  TODO    全部
        return  lanternCountActivityRes;
    }

    /**
     * 新增元宵点灯活动
     * @param busUser
     * @param lanternAddReq
     */
    @Override
    public void addLantern(BusUser busUser, LanternAddReq lanternAddReq) {

        LanternMain lanternMain = new LanternMain();
        lanternMain.setCreatetime(new Date());
        lanternMain.setName(lanternAddReq.getName());
        lanternMain.setActivityBeginTime(lanternAddReq.getActivityBeginTime());
        lanternMain.setActivityEndTime(lanternAddReq.getActivityEndTime());
        lanternMain.setFollowQrCode(lanternAddReq.getFollowQrCode());
        lanternMain.setManTotalChance(lanternAddReq.getManTotalChance());
        lanternMain.setManDayChance(lanternAddReq.getManDayChance());
        lanternMain.setGameTime(lanternAddReq.getGameTime());
        lanternMain.setActRule(lanternAddReq.getActRule());
        lanternMain.setCashPrizeBeginTime(lanternAddReq.getCashPrizeBeginTime());
        lanternMain.setCashPrizeEndTime(lanternAddReq.getCashPrizeEndTime());

        String s1="";
        for(String s:lanternAddReq.getReceiveTypeList()){
               s1 = s1+","+s;
        }
        lanternMain.setReceiveType(s1.substring(1));
        lanternMain.setPhone(lanternAddReq.getPhone());
        lanternMain.setCashPrizeInstruction(lanternAddReq.getCashPrizeInstruction());
        lanternMain.setPrizeDescription(lanternAddReq.getPrizeDescription());

        lanternMainService.insert(lanternMain);

        for(String s:lanternAddReq.getReceiveTypeList()){
            if("1".equals(s)){
                for(String s2:lanternAddReq.getAddressList()){
                    LanternAddress lanternAddress = new LanternAddress();
                    lanternAddress.setActId(lanternMain.getId());
                    lanternAddress.setCreatetime(new Date());
                    lanternAddress.setAddress(s2);

                    lanternAddressService.insert(lanternAddress);
                }
            }
        }

        if(lanternAddReq.getAdvertisingPictureList().size()>0){  // TODO  广告轮播图
            for(LanternAdvertisingPictureReq lanternAdvertisingPictureReq:lanternAddReq.getAdvertisingPictureList()){
                LanternAd lanternAd = new LanternAd();
                lanternAd.setActId(lanternMain.getId());
                lanternAd.setUrl(lanternAdvertisingPictureReq.getUrl());
                lanternAd.setHrefUrl(lanternAdvertisingPictureReq.getHrefUrl());

                lanternAdService.insert(lanternAd);
            }
        }

        if(lanternAddReq.getPrizeSetList().size()>0){        //TODO  奖品设置
            for(LanternPrizeSetReq lanternPrizeSetReq:lanternAddReq.getPrizeSetList()){
                LanternPrize lanternPrize = new LanternPrize();
                lanternPrize.setActId(lanternMain.getId());
                lanternPrize.setType(lanternPrizeSetReq.getType());
                lanternPrize.setPrizeUnit(lanternPrizeSetReq.getPrizeUnit());
                lanternPrize.setPrizeName(lanternPrizeSetReq.getPrizeName());
                lanternPrize.setNum(lanternPrizeSetReq.getNum());

                lanternPrizeService.insert(lanternPrize);

                if(CommonUtil.isNotEmpty(lanternPrizeSetReq.getImgUrl())){   ///TODO 添加图片
                    LanternPrizeImg lanternPrizeImg = new LanternPrizeImg();
                    lanternPrizeImg.setPrizeId(lanternPrize.getId());
                    lanternPrizeImg.setImgUrl(lanternPrizeSetReq.getImgUrl());

                    lanternPrizeImgService.insert(lanternPrizeImg);
                }
            }
        }
    }

    /**
     * 编辑元宵点灯活动基础设置
     * @param busUser
     * @param lanternModfiyBasicsReq
     */
    @Override
    public void modfiyBasicsLantern(BusUser busUser, LanternModfiyBasicsReq lanternModfiyBasicsReq) {

        LanternMain lanternMain = new LanternMain();
        lanternMain.setName(lanternModfiyBasicsReq.getName());
        lanternMain.setActivityBeginTime(lanternModfiyBasicsReq.getActivityBeginTime());
        lanternMain.setActivityEndTime(lanternModfiyBasicsReq.getActivityEndTime());

        lanternMainService.updateById(lanternMain);
    }

    /**
     * 编辑元宵点灯活动规则设置
     * @param busUser
     * @param lanternModfiyRuleReq
     */
    @Override
    public void modfiyRuleLantern(BusUser busUser, LanternModfiyRuleReq lanternModfiyRuleReq) {

        LanternMain lanternMain = new LanternMain();
        lanternMain.setFollowQrCode(lanternModfiyRuleReq.getFollowQrCode());
        lanternMain.setManTotalChance(lanternModfiyRuleReq.getManTotalChance());
        lanternMain.setManDayChance(lanternModfiyRuleReq.getManDayChance());
        lanternMain.setGameTime(lanternModfiyRuleReq.getGameTime());
        lanternMain.setActRule(lanternModfiyRuleReq.getActRule());

        lanternMainService.updateById(lanternMain);
    }

    /**
     * 编辑元宵点灯活动兑奖设置
     * @param busUser
     * @param lanternModfiyExpiryReq
     */
    @Override
    public void modfiyExpiryLantern(BusUser busUser, LanternModfiyExpiryReq lanternModfiyExpiryReq) {

        LanternMain lanternMain = new LanternMain();
        lanternMain.setCashPrizeBeginTime(lanternModfiyExpiryReq.getCashPrizeBeginTime());
        lanternMain.setCashPrizeEndTime(lanternModfiyExpiryReq.getCashPrizeEndTime());

        String s1="";
        for(String s:lanternModfiyExpiryReq.getReceiveTypeList()){
            s1 = s1+","+s;
        }
        lanternMain.setReceiveType(s1.substring(1));
        lanternMain.setPhone(lanternModfiyExpiryReq.getPhone());
        lanternMain.setCashPrizeInstruction(lanternModfiyExpiryReq.getCashPrizeInstruction());

        lanternMainService.updateById(lanternMain);

        //TODO  清空兑奖地址
        EntityWrapper<LanternAddress> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_id",lanternMain.getId());
        lanternAddressService.delete(entityWrapper);

        for(String s:lanternModfiyExpiryReq.getReceiveTypeList()){
            if("1".equals(s)){
                for(String s2:lanternModfiyExpiryReq.getAddressList()){
                    LanternAddress lanternAddress = new LanternAddress();
                    lanternAddress.setActId(lanternMain.getId());
                    lanternAddress.setCreatetime(new Date());
                    lanternAddress.setAddress(s2);

                    lanternAddressService.insert(lanternAddress);
                }
            }
        }
    }

    /**
     * 编辑元宵点灯活动奖项设置
     * @param busUser
     * @param lanternModfiyAwardsReq
     */
    @Override
    public void modfiyAwardsLantern(BusUser busUser, LanternModfiyAwardsReq lanternModfiyAwardsReq) {

        LanternMain lanternMain = new LanternMain();
        lanternMain.setPrizeDescription(lanternModfiyAwardsReq.getPrizeDescription());
        lanternMainService.updateById(lanternMain);

        if(lanternModfiyAwardsReq.getAdvertisingPictureList().size()>0){  // TODO  广告轮播图
            //TODO  清空广告轮播图
            EntityWrapper<LanternAd> entityWrapper = new EntityWrapper();
            entityWrapper.eq("act_id",lanternMain.getId());
            lanternAdService.delete(entityWrapper);

            for(LanternAdvertisingPictureReq lanternAdvertisingPictureReq:lanternModfiyAwardsReq.getAdvertisingPictureList()){
                LanternAd lanternAd = new LanternAd();
                lanternAd.setActId(lanternMain.getId());
                lanternAd.setUrl(lanternAdvertisingPictureReq.getUrl());
                lanternAd.setHrefUrl(lanternAdvertisingPictureReq.getHrefUrl());

                lanternAdService.insert(lanternAd);
            }
        }

        if(lanternModfiyAwardsReq.getPrizeSetList().size()>0){        //TODO  奖品设置
            // TODO  清空奖品设置
            EntityWrapper<LanternPrize> entityWrapper = new EntityWrapper();
            entityWrapper.eq("act_id",lanternMain.getId());
            lanternPrizeService.delete(entityWrapper);

            for(LanternPrizeSetReq lanternPrizeSetReq:lanternModfiyAwardsReq.getPrizeSetList()){
                LanternPrize lanternPrize = new LanternPrize();
                lanternPrize.setActId(lanternMain.getId());
                lanternPrize.setType(lanternPrizeSetReq.getType());
                lanternPrize.setPrizeUnit(lanternPrizeSetReq.getPrizeUnit());
                lanternPrize.setPrizeName(lanternPrizeSetReq.getPrizeName());
                lanternPrize.setNum(lanternPrizeSetReq.getNum());

                lanternPrizeService.insert(lanternPrize);

                //TODO 清空图片
                EntityWrapper<LanternPrizeImg> entityWrapper2 = new EntityWrapper();
                entityWrapper2.eq("prize_id",lanternPrize.getId());
                lanternPrizeImgService.delete(entityWrapper2);

                if(CommonUtil.isNotEmpty(lanternPrizeSetReq.getImgUrl())){   ///TODO 添加图片
                    LanternPrizeImg lanternPrizeImg = new LanternPrizeImg();
                    lanternPrizeImg.setPrizeId(lanternPrize.getId());
                    lanternPrizeImg.setImgUrl(lanternPrizeSetReq.getImgUrl());

                    lanternPrizeImgService.insert(lanternPrizeImg);
                }
            }
        }
    }

    @Override
    public ResponseDTO<List<LanternListRes>> getWinningList(BusUser busUser, LanternGetWinningReq lanternGetWinningReq) {
        return null;
    }
}