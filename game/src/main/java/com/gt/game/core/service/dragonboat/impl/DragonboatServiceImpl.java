package com.gt.game.core.service.dragonboat.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.axis.bean.wxmp.dict.DictApiReq;
import com.gt.axis.bean.wxmp.dict.DictApiRes;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.member.MemberServer;
import com.gt.axis.server.wxmp.DictServer;
import com.gt.game.common.config.ApplyProperties;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.dragonboat.req.*;
import com.gt.game.core.bean.dragonboat.res.*;
import com.gt.game.core.bean.lantern.req.LanternAdvertisingPictureReq;
import com.gt.game.core.bean.lantern.req.LanternPrizeSetReq;
import com.gt.game.core.bean.tree.res.TreeGetActivityRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.dragonboat.DragonboatraceCashPrizeApplyDAO;
import com.gt.game.core.entity.dragonboat.*;
import com.gt.game.core.entity.lantern.LanternAd;
import com.gt.game.core.entity.lantern.LanternAddress;
import com.gt.game.core.entity.lantern.LanternPrize;
import com.gt.game.core.entity.lantern.LanternPrizeImg;
import com.gt.game.core.exception.dragonboat.DragonboatException;
import com.gt.game.core.service.dragonboat.*;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 端午赛龙舟   服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class DragonboatServiceImpl implements DragonboatService {

    @Autowired
    DragonboatraceMainService dragonboatraceMainService;

    @Autowired
    DragonboatraceAddressService dragonboatraceAddressService;

    @Autowired
    DragonboatraceAdService dragonboatraceAdService;

    @Autowired
    DragonboatracePrizeService dragonboatracePrizeService;

    @Autowired
    DragonboatracePrizeImgService dragonboatracePrizeImgService;

    @Autowired
    DragonboatraceCashPrizeApplyService dragonboatraceCashPrizeApplyService;

    @Autowired
    DragonboatraceAuthorityService dragonboatraceAuthorityService;

    @Autowired
    DragonboatraceCashPrizeApplyDAO dragonboatraceCashPrizeApplyDAO;

    @Autowired
    ApplyProperties applyProperties;

   /**
     * 获取手机端链接
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {

        String url = applyProperties.getMobileBaseUrl() + "dragonboatraceMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
        return new MobileUrlRes(url);
    }

    /**
     * 获取新增授权链接
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    @Override
    public ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {

        String url = applyProperties.getMobileBaseUrl() + "dragonboatraceMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }

    /**
     * 分页端午赛龙舟获取活动列表
     * @param busUser
     * @param dragonboatListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<DragonboatListRes>> getDragonboatList(BusUser busUser, DragonboatListPageReq dragonboatListPageReq) {

        EntityWrapper<DragonboatraceMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("bus_id", busUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(dragonboatListPageReq.getName()), "name", dragonboatListPageReq.getName());

        if (dragonboatListPageReq.getStatus() != -1) {   // TODO -1 全部 0 未开始 1 进行中 2 已结束
            if (dragonboatListPageReq.getStatus() == 0) {
                entityWrapper.where("activity_begin_time > {0}", new Date());
            }
            if (dragonboatListPageReq.getStatus() == 1) {
                entityWrapper.where("activity_begin_time <= {0}", new Date());
                entityWrapper.and();
                entityWrapper.where("activity_end_time > {0}", new Date());
            }
            if (dragonboatListPageReq.getStatus() == 2) {
                entityWrapper.where("activity_end_time <= {0}", new Date());
            }
        }
        Page<DragonboatraceMain> page = new Page<>(dragonboatListPageReq.getCurrent(), dragonboatListPageReq.getSize());
        List<DragonboatraceMain> DragonboatraceMainList = dragonboatraceMainService.selectPage(page, entityWrapper).getRecords();

        List<DragonboatListRes> dragonboatListResList = new ArrayList<>();
        for (DragonboatraceMain dragonboatraceMain : DragonboatraceMainList) {
            DragonboatListRes dragonboatListRes = new DragonboatListRes();
            dragonboatListRes.setId(dragonboatraceMain.getId());
            dragonboatListRes.setName(dragonboatraceMain.getName());
            dragonboatListRes.setActivityBeginTime(dragonboatraceMain.getActivityBeginTime());
            dragonboatListRes.setActivityEndTime(dragonboatraceMain.getActivityEndTime());
            dragonboatListRes.setCashPrizeBeginTime(dragonboatraceMain.getCashPrizeBeginTime());
            dragonboatListRes.setCashPrizeEndTime(dragonboatraceMain.getCashPrizeEndTime());

            Date date = new Date();
            if (dragonboatraceMain.getActivityBeginTime().getTime() > date.getTime()) {
                dragonboatListRes.setStatus(0);
            } else if (dragonboatraceMain.getActivityBeginTime().getTime() <= date.getTime() && dragonboatraceMain.getActivityEndTime().getTime() >= date.getTime()) {
                dragonboatListRes.setStatus(1);
            } else if (dragonboatraceMain.getActivityEndTime().getTime() < date.getTime()) {
                dragonboatListRes.setStatus(2);
            }

            dragonboatListResList.add(dragonboatListRes);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取端午赛龙舟活动列表成功", dragonboatListResList, pageDTO);
    }

    /**
     * 获取端午赛龙舟活动数量
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<DragonboatCountRes> getDragonboatCount(BusUser busUser,DragonboatCountActivityReq dragonboatCountActivityReq) {

        EntityWrapper<DragonboatraceMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("bus_id", busUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(dragonboatCountActivityReq.getName()), "name", dragonboatCountActivityReq.getName());

        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        List<DragonboatraceMain> dragonboatraceMainList = dragonboatraceMainService.selectList(entityWrapper);
        Date date = new Date();
        for (DragonboatraceMain DragonboatraceMain : dragonboatraceMainList) {
            if (DragonboatraceMain.getActivityBeginTime().getTime() > date.getTime()) {
                count2++; //  TODO    未开始
            } else if (DragonboatraceMain.getActivityBeginTime().getTime() <= date.getTime() && DragonboatraceMain.getActivityEndTime().getTime() >= date.getTime()) {
                count3++;  // TODO    进行中
            } else if (DragonboatraceMain.getActivityEndTime().getTime() < date.getTime()) {
                count4++;  // TODO    已结束
            }
        }
        DragonboatCountRes dragonboatCountRes = new DragonboatCountRes();
        dragonboatCountRes.setCount2(count2);
        dragonboatCountRes.setCount3(count3);
        dragonboatCountRes.setCount4(count4);
        dragonboatCountRes.setCount1(count2+count3+count4);  //  TODO 全部

        return ResponseDTO.createBySuccess("获取成功",dragonboatCountRes);
    }

    /**
     * 新增端午赛龙舟活动
     * @param busUser
     * @param dragonboatAddReq
     */
    @Override
    public void addDragonboat(BusUser busUser, DragonboatAddReq dragonboatAddReq) {

        DragonboatraceMain dragonboatraceMain = new DragonboatraceMain();
        dragonboatraceMain.setBusId(busUser.getId());
        dragonboatraceMain.setCreatetime(new Date());
        dragonboatraceMain.setName(dragonboatAddReq.getName());
        dragonboatraceMain.setActivityBeginTime(dragonboatAddReq.getActivityBeginTime());
        dragonboatraceMain.setActivityEndTime(dragonboatAddReq.getActivityEndTime());
        dragonboatraceMain.setBgmSp(dragonboatAddReq.getBgmSp());
        dragonboatraceMain.setMusicUrl(dragonboatAddReq.getMusicUrl());
        dragonboatraceMain.setFollowQrCode(dragonboatAddReq.getFollowQrCode());
        dragonboatraceMain.setManTotalChance(dragonboatAddReq.getManTotalChance());
        dragonboatraceMain.setManDayChance(dragonboatAddReq.getManDayChance());
        dragonboatraceMain.setActRule(dragonboatAddReq.getActRule());
        dragonboatraceMain.setCashPrizeBeginTime(dragonboatAddReq.getCashPrizeBeginTime());
        dragonboatraceMain.setCashPrizeEndTime(dragonboatAddReq.getCashPrizeEndTime());

        String s1="";
        for(String s:dragonboatAddReq.getReceiveTypeList()){
            s1 = s1+","+s;
        }
        dragonboatraceMain.setReceiveType(s1.substring(1));
        dragonboatraceMain.setPhone(dragonboatAddReq.getPhone());
        dragonboatraceMain.setCashPrizeInstruction(dragonboatAddReq.getCashPrizeInstruction());
        dragonboatraceMain.setPrizeDescription(dragonboatAddReq.getPrizeDescription());

        dragonboatraceMainService.insert(dragonboatraceMain);

        for(String s:dragonboatAddReq.getReceiveTypeList()){
            if("1".equals(s)){
                for(String s2:dragonboatAddReq.getAddressList()){
                    DragonboatraceAddress dragonboatraceAddress = new DragonboatraceAddress();
                    dragonboatraceAddress.setActId(dragonboatraceMain.getId());
                    dragonboatraceAddress.setCreatetime(new Date());
                    dragonboatraceAddress.setAddress(s2);

                    dragonboatraceAddressService.insert(dragonboatraceAddress);
                }
            }
        }

        if(dragonboatAddReq.getAdvertisingPictureList().size()>0){  // TODO  广告轮播图
            for(DragonboatAdvertisingPictureReq dragonboatAdvertisingPictureReq:dragonboatAddReq.getAdvertisingPictureList()){
                DragonboatraceAd dragonboatraceAd = new DragonboatraceAd();
                dragonboatraceAd.setActId(dragonboatraceMain.getId());
                dragonboatraceAd.setUrl(dragonboatAdvertisingPictureReq.getUrl());
                dragonboatraceAd.setHrefUrl(dragonboatAdvertisingPictureReq.getHrefUrl());

                dragonboatraceAdService.insert(dragonboatraceAd);
            }
        }

        if(dragonboatAddReq.getPrizeSetList().size()>0){        //TODO  奖品设置
            for(DragonboatPrizeSetReq dragonboatPrizeSetReq:dragonboatAddReq.getPrizeSetList()){
                DragonboatracePrize dragonboatracePrize = new DragonboatracePrize();
                dragonboatracePrize.setActId(dragonboatraceMain.getId());
                dragonboatracePrize.setType(dragonboatPrizeSetReq.getType());
                dragonboatracePrize.setPrizeUnit(dragonboatPrizeSetReq.getPrizeUnit());
                dragonboatracePrize.setPrizeName(dragonboatPrizeSetReq.getPrizeName());
                dragonboatracePrize.setNum(dragonboatPrizeSetReq.getNum());

                dragonboatracePrizeService.insert(dragonboatracePrize);

                if(dragonboatPrizeSetReq.getImgUrl().size()>0){   ///TODO 添加图片

                    for(String s:dragonboatPrizeSetReq.getImgUrl()){
                        DragonboatracePrizeImg dragonboatracePrizeImg = new DragonboatracePrizeImg();
                        dragonboatracePrizeImg.setPrizeId(dragonboatracePrize.getId());
                        dragonboatracePrizeImg.setImgUrl(s);
                        dragonboatracePrizeImgService.insert(dragonboatracePrizeImg);
                    }
                }
            }
        }
    }

    /**
     * 通过活动id查询端午赛龙舟活动
     * @param busUser
     * @param dragonboatGetActivityReq
     * @return
     */
    @Override
    public  DragonboatGetActivityRes  getActivityById(BusUser busUser, DragonboatGetActivityReq dragonboatGetActivityReq) {

        DragonboatraceMain dragonboatraceMain = dragonboatraceMainService.selectById(dragonboatGetActivityReq.getId());

        DragonboatGetActivityRes dragonboatGetActivityRes = new DragonboatGetActivityRes();
        dragonboatGetActivityRes.setName(dragonboatraceMain.getName());
        dragonboatGetActivityRes.setActivityBeginTime(dragonboatraceMain.getActivityBeginTime());
        dragonboatGetActivityRes.setActivityEndTime(dragonboatraceMain.getActivityEndTime());
        dragonboatGetActivityRes.setBgmSp(dragonboatraceMain.getBgmSp());
        dragonboatGetActivityRes.setMusicUrl(dragonboatraceMain.getMusicUrl());
        dragonboatGetActivityRes.setFollowQrCode(dragonboatraceMain.getFollowQrCode());
        dragonboatGetActivityRes.setManTotalChance(dragonboatraceMain.getManTotalChance());
        dragonboatGetActivityRes.setManDayChance(dragonboatraceMain.getManDayChance());
        dragonboatGetActivityRes.setActRule(dragonboatraceMain.getActRule());
        dragonboatGetActivityRes.setCashPrizeBeginTime(dragonboatraceMain.getCashPrizeBeginTime());
        dragonboatGetActivityRes.setCashPrizeEndTime(dragonboatraceMain.getCashPrizeEndTime());

        ArrayList<String> list = new ArrayList<>();
        for (String s : dragonboatraceMain.getReceiveType().split(",")) {
            list.add(s);
        }
        dragonboatGetActivityRes.setReceiveTypeList(list);   //  TODO  对奖方式

        EntityWrapper<DragonboatraceAddress> entityWrapper2 = new EntityWrapper();
        entityWrapper2.eq("act_id", dragonboatraceMain.getId());
        List<DragonboatraceAddress> dragonboatraceAddressList = dragonboatraceAddressService.selectList(entityWrapper2);

        List<String> list2 = new ArrayList<>();
        for(DragonboatraceAddress dragonboatraceAddress :dragonboatraceAddressList){
            list2.add(dragonboatraceAddress.getAddress());
        }
        dragonboatGetActivityRes.setAddressList(list2);    // TODO  兑奖地址

        dragonboatGetActivityRes.setPhone(dragonboatraceMain.getPhone());
        dragonboatGetActivityRes.setCashPrizeInstruction(dragonboatraceMain.getCashPrizeInstruction());
        dragonboatGetActivityRes.setPrizeDescription(dragonboatraceMain.getPrizeDescription());


        EntityWrapper<DragonboatraceAd> entityWrapper3 = new EntityWrapper();
        entityWrapper3.eq("act_id", dragonboatraceMain.getId());
        List<DragonboatraceAd> dragonboatraceAdList = dragonboatraceAdService.selectList(entityWrapper3);

        List<DragonboatAdvertisingPictureReq> list3 = new ArrayList<>();
        for(DragonboatraceAd dragonboatraceAd :dragonboatraceAdList){
            DragonboatAdvertisingPictureReq dragonboatAdvertisingPictureReq = new DragonboatAdvertisingPictureReq();
            dragonboatAdvertisingPictureReq.setHrefUrl(dragonboatraceAd.getHrefUrl());
            dragonboatAdvertisingPictureReq.setUrl(dragonboatraceAd.getUrl());
            list3.add(dragonboatAdvertisingPictureReq);
        }
        dragonboatGetActivityRes.setAdvertisingPictureList(list3);  //TODO  广告轮播图

        EntityWrapper<DragonboatracePrize> entityWrapper4 = new EntityWrapper();
        entityWrapper4.eq("act_id", dragonboatraceMain.getId());
        List<DragonboatracePrize> dragonboatracePrizeList = dragonboatracePrizeService.selectList(entityWrapper4);
        List<DragonboatPrizeSetReq> list4 = new ArrayList<>();
        for(DragonboatracePrize dragonboatracePrize :dragonboatracePrizeList){
            DragonboatPrizeSetReq dragonboatPrizeSetReq = new DragonboatPrizeSetReq();
            dragonboatPrizeSetReq.setType(dragonboatracePrize.getType());
            dragonboatPrizeSetReq.setPrizeUnit(dragonboatracePrize.getPrizeUnit());
            dragonboatPrizeSetReq.setPrizeName(dragonboatracePrize.getPrizeName());
            dragonboatPrizeSetReq.setNum(dragonboatracePrize.getNum());
            dragonboatPrizeSetReq.setScore(dragonboatracePrize.getScore());

            EntityWrapper<DragonboatracePrizeImg> entityWrapper5 = new EntityWrapper();
            entityWrapper5.eq("prize_id",dragonboatracePrize.getId());
            List<DragonboatracePrizeImg> dragonboatracePrizeImgList = dragonboatracePrizeImgService.selectList(entityWrapper5);
            List<String> list5 = new ArrayList<>();
            for(DragonboatracePrizeImg dragonboatracePrizeImg:dragonboatracePrizeImgList){
                list5.add(dragonboatracePrizeImg.getImgUrl());
            }
            dragonboatPrizeSetReq.setImgUrl(list5);
            list4.add(dragonboatPrizeSetReq);
        }
        dragonboatGetActivityRes.setPrizeSetList(list4); //TODO  奖品设置

        return dragonboatGetActivityRes;
    }

    /**
     *编辑端午赛龙舟活动设置
     * @param busUser
     * @param dragonboatModfiyReq
     */
    @Override
    public void modfiyDragonboat(BusUser busUser, DragonboatModfiyReq dragonboatModfiyReq) {

        DragonboatraceMain dragonboatraceMain = new DragonboatraceMain();
        dragonboatraceMain.setId(dragonboatModfiyReq.getId());
        //TODO 基础设置
        dragonboatraceMain.setName(dragonboatModfiyReq.getName());
        dragonboatraceMain.setActivityBeginTime(dragonboatModfiyReq.getActivityBeginTime());
        dragonboatraceMain.setActivityEndTime(dragonboatModfiyReq.getActivityEndTime());
        dragonboatraceMain.setBgmSp(dragonboatModfiyReq.getBgmSp());
        dragonboatraceMain.setMusicUrl(dragonboatModfiyReq.getMusicUrl());

        //TODO 规则设置
        dragonboatraceMain.setFollowQrCode(dragonboatModfiyReq.getFollowQrCode());
        dragonboatraceMain.setManTotalChance(dragonboatModfiyReq.getManTotalChance());
        dragonboatraceMain.setManDayChance(dragonboatModfiyReq.getManDayChance());
        dragonboatraceMain.setActRule(dragonboatModfiyReq.getActRule());

        //TODO 兑奖设置
        dragonboatraceMain.setCashPrizeBeginTime(dragonboatModfiyReq.getCashPrizeBeginTime());
        dragonboatraceMain.setCashPrizeEndTime(dragonboatModfiyReq.getCashPrizeEndTime());

        String s1="";
        for(String s:dragonboatModfiyReq.getReceiveTypeList()){
            s1 = s1+","+s;
        }
        dragonboatraceMain.setReceiveType(s1.substring(1));
        dragonboatraceMain.setPhone(dragonboatModfiyReq.getPhone());
        dragonboatraceMain.setCashPrizeInstruction(dragonboatModfiyReq.getCashPrizeInstruction());
        dragonboatraceMain.setPrizeDescription(dragonboatModfiyReq.getPrizeDescription());
        dragonboatraceMainService.updateById(dragonboatraceMain);

        //TODO  清空兑奖地址
        EntityWrapper<DragonboatraceAddress> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_id",dragonboatraceMain.getId());
        dragonboatraceAddressService.delete(entityWrapper);

        for(String s:dragonboatModfiyReq.getReceiveTypeList()){
            if("1".equals(s)){
                for(String s2:dragonboatModfiyReq.getAddressList()){
                    DragonboatraceAddress dragonboatraceAddress = new DragonboatraceAddress();
                    dragonboatraceAddress.setActId(dragonboatraceMain.getId());
                    dragonboatraceAddress.setCreatetime(new Date());
                    dragonboatraceAddress.setAddress(s2);

                    dragonboatraceAddressService.insert(dragonboatraceAddress);
                }
            }
        }

        //TODO 奖项设置
        if(dragonboatModfiyReq.getAdvertisingPictureList().size()>0){  // TODO  广告轮播图
            //TODO  清空广告轮播图
            EntityWrapper<DragonboatraceAd> entityWrapper2 = new EntityWrapper();
            entityWrapper2.eq("act_id",dragonboatraceMain.getId());
            dragonboatraceAdService.delete(entityWrapper2);

            for(DragonboatAdvertisingPictureReq DragonboatAdvertisingPictureReq:dragonboatModfiyReq.getAdvertisingPictureList()){
                DragonboatraceAd dragonboatraceAd = new DragonboatraceAd();
                dragonboatraceAd.setActId(dragonboatraceMain.getId());
                dragonboatraceAd.setUrl(DragonboatAdvertisingPictureReq.getUrl());
                dragonboatraceAd.setHrefUrl(DragonboatAdvertisingPictureReq.getHrefUrl());

                dragonboatraceAdService.insert(dragonboatraceAd);
            }
        }

        if(dragonboatModfiyReq.getPrizeSetList().size()>0){        //TODO  奖品设置
            // TODO  清空奖品设置
            EntityWrapper<DragonboatracePrize> entityWrapper3 = new EntityWrapper();
            entityWrapper3.eq("act_id",dragonboatraceMain.getId());
            dragonboatracePrizeService.delete(entityWrapper3);

            for(DragonboatPrizeSetReq dragonboatPrizeSetReq:dragonboatModfiyReq.getPrizeSetList()){
                DragonboatracePrize dragonboatracePrize = new DragonboatracePrize();
                dragonboatracePrize.setActId(dragonboatraceMain.getId());
                dragonboatracePrize.setType(dragonboatPrizeSetReq.getType());
                dragonboatracePrize.setPrizeUnit(dragonboatPrizeSetReq.getPrizeUnit());
                dragonboatracePrize.setPrizeName(dragonboatPrizeSetReq.getPrizeName());
                dragonboatracePrize.setNum(dragonboatPrizeSetReq.getNum());

                dragonboatracePrizeService.insert(dragonboatracePrize);

                //TODO 清空图片
                EntityWrapper<DragonboatracePrizeImg> entityWrapper4 = new EntityWrapper();
                entityWrapper4.eq("prize_id",dragonboatracePrize.getId());
                dragonboatracePrizeImgService.delete(entityWrapper4);

                if(dragonboatPrizeSetReq.getImgUrl().size()>0){   ///TODO 添加图片

                    for(String s:dragonboatPrizeSetReq.getImgUrl()){
                        DragonboatracePrizeImg dragonboatracePrizeImg = new DragonboatracePrizeImg();
                        dragonboatracePrizeImg.setPrizeId(dragonboatracePrize.getId());
                        dragonboatracePrizeImg.setImgUrl(s);
                        dragonboatracePrizeImgService.insert(dragonboatracePrizeImg);
                    }
                }
            }
        }
    }

    /**
     * 删除端午赛龙舟活动
     * @param busUser
     * @param dragonboatDelReq
     */
    @Override
    public void delDragonboat(BusUser busUser, DragonboatDelReq dragonboatDelReq) {

        //TODO  删除活动
        boolean b = dragonboatraceMainService.deleteById(dragonboatDelReq.getId());
        if(b==false){
            throw  new DragonboatException(ResponseEnums.DRAGONBOAT_HAS1);
        }

        //TODO  删除活动地址
        EntityWrapper<DragonboatraceAddress> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("act_id",dragonboatDelReq.getId());
        dragonboatraceAddressService.delete(entityWrapper);

        //TODO  删除广告轮播图
        EntityWrapper<DragonboatraceAd> entityWrapper2 = new EntityWrapper<>();
        entityWrapper2.eq("act_id",dragonboatDelReq.getId());
        dragonboatraceAdService.delete(entityWrapper2);


        EntityWrapper<DragonboatracePrize> entityWrapper3 = new EntityWrapper<>();
        entityWrapper3.eq("act_id",dragonboatDelReq.getId());
        List<DragonboatracePrize> dragonboatracePrizeList = dragonboatracePrizeService.selectList(entityWrapper3);

        if(dragonboatracePrizeList.size()>0){
            for(DragonboatracePrize dragonboatracePrize:dragonboatracePrizeList){

                //TODO  删除奖品图片
                EntityWrapper<DragonboatracePrizeImg> entityWrapper4 = new EntityWrapper<>();
                entityWrapper4.eq("prize_id",dragonboatracePrize.getId());
                dragonboatracePrizeImgService.delete(entityWrapper4);
            }
        }

        //TODO  删除奖品
        EntityWrapper<DragonboatracePrize> entityWrapper5 = new EntityWrapper<>();
        entityWrapper5.eq("act_id",dragonboatDelReq.getId());
        dragonboatracePrizeService.delete(entityWrapper5);
    }

    /**
     * 分页获取端午赛龙舟中奖记录列表
     * @param busUser
     * @param dragonboatGetWinningReq
     * @return
     */
    @Override
    public ResponseDTO<List<DragonboatGetWinningRes>> getWinningList(BusUser busUser, DragonboatGetWinningReq dragonboatGetWinningReq) {

        Page<DragonboatGetWinningRes> page = new Page<>(dragonboatGetWinningReq.getCurrent(),dragonboatGetWinningReq.getSize());
        List<DragonboatGetWinningRes> dragonboatGetWinningResList = dragonboatraceCashPrizeApplyDAO.queryRecodList(page,dragonboatGetWinningReq);

        //TODO  获取用户名
        if(dragonboatGetWinningResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < dragonboatGetWinningResList.size(); i++) {
                ids.append(dragonboatGetWinningResList.get(i).getMemberId());
                if (dragonboatGetWinningResList.size() > 1 && i < dragonboatGetWinningResList.size() - 1) {
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
            for (int i = 0; i < dragonboatGetWinningResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == dragonboatGetWinningResList.get(i).getMemberId().intValue()) {
                        dragonboatGetWinningResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(dragonboatGetWinningResList.get(i).getMemberName())) {
                    dragonboatGetWinningResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取中奖记录列表成功",dragonboatGetWinningResList,pageDTO);
    }

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param dragonboatEditApplyReq
     * @return
     */
    @Override
    public ResponseDTO editDragonboatApply(BusUser busUser, DragonboatEditApplyReq dragonboatEditApplyReq) {

        DragonboatraceCashPrizeApply dragonboatraceCashPrizeApply = dragonboatraceCashPrizeApplyService.selectById(dragonboatEditApplyReq.getId());
        if(CommonUtil.isNotEmpty(dragonboatraceCashPrizeApply)){
            DragonboatraceMain dragonboatraceMain = dragonboatraceMainService.selectById(dragonboatraceCashPrizeApply.getActId());
            DragonboatracePrize dragonboatracePrize = dragonboatracePrizeService.selectById(dragonboatraceCashPrizeApply.getPrizeId());
            if(dragonboatracePrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(dragonboatraceMain.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new DragonboatException(ResponseEnums.DRAGONBOAT_HAS2);
                } else if (!DateTimeKit.laterThanNow(dragonboatraceMain.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new DragonboatException(ResponseEnums.DRAGONBOAT_HAS3);
                }
            }
            if (dragonboatraceCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                dragonboatraceCashPrizeApply.setStatus(2);
                dragonboatraceCashPrizeApply.setCashTime(new Date());
                dragonboatraceCashPrizeApplyService.updateById(dragonboatraceCashPrizeApply);
            } else if (dragonboatraceCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new DragonboatException(ResponseEnums.DRAGONBOAT_HAS4);
            }else{//还未提交
                throw  new DragonboatException(ResponseEnums.DRAGONBOAT_HAS5);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }

    /**
     * 导出中奖记录
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> exportDragonboat(Map<String, Object> params) {

        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            DragonboatraceMain dragonboatraceMain = dragonboatraceMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list =dragonboatraceCashPrizeApplyDAO.findExports(params);
            String title = "活动名称：" + dragonboatraceMain.getName() + "，开始时间：" + sdf.format(dragonboatraceMain.getActivityBeginTime()) + "结束时间："
                    + sdf.format(dragonboatraceMain.getActivityEndTime());
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("member_id"));
                    if (list.size() > 1 && i < list.size() - 1) {
                        ids.append(",");
                    }
                }
                MemberReq memberReq = new MemberReq();
                memberReq.setBusId(dragonboatraceMain.getBusId());
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
            msg.put("fileName", dragonboatraceMain.getName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "端午赛龙舟活动中奖记录导出excel失败！");
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

    /**
     * 批量删除中奖记录
     * @param busUser
     * @param dragonboatDelWinningReq
     */
    @Override
    public void delLanternWinning(BusUser busUser, DragonboatDelWinningReq dragonboatDelWinningReq) {

         dragonboatraceCashPrizeApplyService.deleteBatchIds(dragonboatDelWinningReq.getId());
    }

    /**
     * 分页获取核销授权列表
     * @param busUser
     * @param dragonboatAuthorityListReq
     * @return
     */
    @Override
    public ResponseDTO<List<DragonboatAuthorityListRes>> getDragonboatAuthorityList(BusUser busUser, DragonboatAuthorityListReq dragonboatAuthorityListReq) {

        EntityWrapper<DragonboatraceAuthority> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_id",dragonboatAuthorityListReq.getActId());
        entityWrapper.eq("delete_status",0);

        Page<DragonboatraceAuthority> page = new Page<>(dragonboatAuthorityListReq.getCurrent(),dragonboatAuthorityListReq.getSize());
        List<DragonboatraceAuthority> dragonboatraceAuthorityList = dragonboatraceAuthorityService.selectPage(page,entityWrapper).getRecords();

        List<DragonboatAuthorityListRes> dragonboatAuthorityListResList = new ArrayList<>();
        for(DragonboatraceAuthority dragonboatraceAuthority:dragonboatraceAuthorityList){

            DragonboatAuthorityListRes dragonboatAuthorityListRes = new DragonboatAuthorityListRes();
            dragonboatAuthorityListRes.setId(dragonboatraceAuthority.getId());
            dragonboatAuthorityListRes.setMemberName(dragonboatraceAuthority.getMemberName());
            dragonboatAuthorityListRes.setCreatetime(dragonboatraceAuthority.getCreatetime());

            dragonboatAuthorityListResList.add(dragonboatAuthorityListRes);
        }

        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取核销授权列表成功", dragonboatAuthorityListResList, pageDTO);
    }

    /**
     * 批量删除核销授权
     * @param busUser
     * @param dragonboatDelAuthorityReq
     */
    @Override
    public void delDragonboatAuthority(BusUser busUser, DragonboatDelAuthorityReq dragonboatDelAuthorityReq) {

        DragonboatraceMain dragonboatraceMain = dragonboatraceMainService.selectById(dragonboatDelAuthorityReq.getActId());
        if(CommonUtil.isNotEmpty(dragonboatraceMain)) {
            if (dragonboatraceMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new DragonboatException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new DragonboatException(ResponseEnums.DRAGONBOAT_HAS6);
        }

        for(Integer id:dragonboatDelAuthorityReq.getId()){
            DragonboatraceAuthority dragonboatraceAuthority = new DragonboatraceAuthority();
            dragonboatraceAuthority.setId(id);
            dragonboatraceAuthority.setDeleteStatus(1);
            dragonboatraceAuthorityService.updateById(dragonboatraceAuthority);
        }
    }

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<List<DragonboatPrizeTypeListRes>> getDragonboatPrizeType(BusUser busUser) {

        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult = DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<DragonboatPrizeTypeListRes> dragonboatPrizeTypeListResList = new ArrayList<>();
        if (CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())) {
            for (DictApiRes dictApiRes : axisResult.getData()) {
                DragonboatPrizeTypeListRes dragonboatPrizeTypeListRes = new DragonboatPrizeTypeListRes();
                dragonboatPrizeTypeListRes.setName(dictApiRes.getItemValue());
                dragonboatPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                dragonboatPrizeTypeListResList.add(dragonboatPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功", dragonboatPrizeTypeListResList);
    }


}