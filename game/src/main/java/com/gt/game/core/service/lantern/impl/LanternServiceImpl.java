package com.gt.game.core.service.lantern.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
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
import com.gt.game.core.bean.lantern.req.*;
import com.gt.game.core.bean.lantern.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.lantern.LanternCashPrizeApplyDAO;
import com.gt.game.core.entity.dragonboat.*;
import com.gt.game.core.entity.lantern.*;
import com.gt.game.core.exception.dragonboat.DragonboatException;
import com.gt.game.core.exception.lantern.LanternException;
import com.gt.game.core.service.lantern.*;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
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

    @Autowired
    LanternCashPrizeApplyService lanternCashPrizeApplyService;

    @Autowired
    LanternAuthorityService lanternAuthorityService;

    @Autowired
    LanternCashPrizeApplyDAO lanternCashPrizeApplyDAO;

    @Autowired
    LanternPlayRecordService lanternPlayRecordService;

    @Autowired
    LanternReportService lanternReportService;

    @Autowired
    ApplyProperties applyProperties;



    /**
     * 获取手机端链接
     *
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {

        String url = applyProperties.getMobileBaseUrl() + "lanternMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
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

        String url = applyProperties.getMobileBaseUrl() + "lanternMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
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
        entityWrapper.orderBy("createtime", false);
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
        for (LanternMain lanternMain : lanternMainList) {
            if (lanternMain.getActivityBeginTime().getTime() > date.getTime()) {
                count2++; //  TODO    未开始
            } else if (lanternMain.getActivityBeginTime().getTime() <= date.getTime() && lanternMain.getActivityEndTime().getTime() >= date.getTime()) {
                count3++;  // TODO    进行中
            } else if (lanternMain.getActivityEndTime().getTime() < date.getTime()) {
                count4++;  // TODO    已结束
            }
        }
        LanternCountActivityRes lanternCountActivityRes = new LanternCountActivityRes();
        lanternCountActivityRes.setCount2(count2);
        lanternCountActivityRes.setCount3(count3);
        lanternCountActivityRes.setCount4(count4);
        lanternCountActivityRes.setCount1(count2+count3+count4);  //  TODO 全部
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
        lanternMain.setBusId(busUser.getId());
        lanternMain.setCreatetime(new Date());
        lanternMain.setName(lanternAddReq.getName());
        lanternMain.setActivityBeginTime(lanternAddReq.getActivityBeginTime());
        lanternMain.setActivityEndTime(lanternAddReq.getActivityEndTime());
        lanternMain.setFollowQrCode(lanternAddReq.getFollowQrCode());
        lanternMain.setMusicUrl(lanternAddReq.getMusicUrl());
        lanternMain.setBgmSp(lanternAddReq.getBgmSp());
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

        Double fenbi = 0.0;
        if(lanternAddReq.getPrizeSetList().size()>0){        //TODO  奖品设置
            for(LanternPrizeSetReq lanternPrizeSetReq:lanternAddReq.getPrizeSetList()){
                if (lanternPrizeSetReq.getType() == 1) {
                    fenbi += lanternPrizeSetReq.getNum();
                }
                LanternPrize lanternPrize = new LanternPrize();
                lanternPrize.setActId(lanternMain.getId());
                lanternPrize.setType(lanternPrizeSetReq.getType());
                lanternPrize.setPrizeUnit(lanternPrizeSetReq.getPrizeUnit());
                lanternPrize.setPrizeName(lanternPrizeSetReq.getPrizeName());
                lanternPrize.setNum(lanternPrizeSetReq.getNum());

                lanternPrizeService.insert(lanternPrize);

                if(lanternPrizeSetReq.getImgUrl().size()>0){   ///TODO 添加图片

                    for(String s:lanternPrizeSetReq.getImgUrl()){
                        LanternPrizeImg lanternPrizeImg = new LanternPrizeImg();
                        lanternPrizeImg.setPrizeId(lanternPrize.getId());
                        lanternPrizeImg.setImgUrl(s);
                        lanternPrizeImgService.insert(lanternPrizeImg);
                    }
                }
            }
        }

        if(fenbi > 0){//冻结粉币
            // 判断账户中的粉币是否足够
            if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                throw new LanternException(ResponseEnums.LANTERN_HAS7);
            }
            //构建冻结信息
            FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, lanternMain.getId(), 97, 1, "元宵点灯活动支出", 0);
            // 保存冻结信息
            if(ffr!=null){
                FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                if(axisResult.getCode() != 0){
                    throw new LanternException(ResponseEnums.LANTERN_HAS8);
                }
            }
        }
    }

    /**
     * 通过活动id查询元宵点灯活动
     * @param busUser
     * @param lanternGetActivityReq
     * @return
     */
    @Override
    public LanternGetActivityRes getActivityById(BusUser busUser, LanternGetActivityReq lanternGetActivityReq) {

        LanternMain lanternMain = lanternMainService.selectById(lanternGetActivityReq.getId());
        LanternGetActivityRes lanternGetActivityRes = new LanternGetActivityRes();
        lanternGetActivityRes.setName(lanternMain.getName());
        lanternGetActivityRes.setActivityBeginTime(lanternMain.getActivityBeginTime());
        lanternGetActivityRes.setActivityEndTime(lanternMain.getActivityEndTime());
        lanternGetActivityRes.setCashPrizeBeginTime(lanternMain.getCashPrizeBeginTime());
        lanternGetActivityRes.setCashPrizeEndTime(lanternMain.getCashPrizeEndTime());
        lanternGetActivityRes.setFollowQrCode(lanternMain.getFollowQrCode());
        lanternGetActivityRes.setMusicUrl(lanternMain.getMusicUrl());
        lanternGetActivityRes.setBgmSp(lanternMain.getBgmSp());
        lanternGetActivityRes.setManTotalChance(lanternMain.getManTotalChance());
        lanternGetActivityRes.setManDayChance(lanternMain.getManDayChance());
        lanternGetActivityRes.setGameTime(lanternMain.getGameTime());
        lanternGetActivityRes.setActRule(lanternMain.getActRule());

        ArrayList<String> list = new ArrayList<>();
        for (String s : lanternMain.getReceiveType().split(",")) {
            list.add(s);
        }
        lanternGetActivityRes.setReceiveTypeList(list);   //  TODO  对奖方式

        EntityWrapper<LanternAddress> entityWrapper2 = new EntityWrapper();
        entityWrapper2.eq("act_id", lanternMain.getId());
        List<LanternAddress> lanternAddressesList = lanternAddressService.selectList(entityWrapper2);

        List<String> list2 = new ArrayList<>();
        for(LanternAddress lanternAddress :lanternAddressesList){
            list2.add(lanternAddress.getAddress());
        }
        lanternGetActivityRes.setAddressList(list2);    // TODO  兑奖地址

        lanternGetActivityRes.setPhone(lanternMain.getPhone());
        lanternGetActivityRes.setCashPrizeInstruction(lanternMain.getCashPrizeInstruction());
        lanternGetActivityRes.setPrizeDescription(lanternMain.getPrizeDescription());

        EntityWrapper<LanternAd> entityWrapper3 = new EntityWrapper();
        entityWrapper3.eq("act_id", lanternMain.getId());
        List<LanternAd> lanternAdList = lanternAdService.selectList(entityWrapper3);

        List<LanternAdvertisingPictureReq> list3 = new ArrayList<>();
        for(LanternAd lanternAd :lanternAdList){
            LanternAdvertisingPictureReq lanternAdvertisingPictureReq = new LanternAdvertisingPictureReq();
            lanternAdvertisingPictureReq.setHrefUrl(lanternAd.getHrefUrl());
            lanternAdvertisingPictureReq.setUrl(lanternAd.getUrl());
            list3.add(lanternAdvertisingPictureReq);
        }
        lanternGetActivityRes.setAdvertisingPictureList(list3);  //TODO  广告轮播图

        EntityWrapper<LanternPrize> entityWrapper4 = new EntityWrapper();
        entityWrapper4.eq("act_id", lanternMain.getId());
        List<LanternPrize> lanternPrizeList = lanternPrizeService.selectList(entityWrapper4);
        List<LanternPrizeSetReq> list4 = new ArrayList<>();
        for(LanternPrize lanternPrize :lanternPrizeList){
            LanternPrizeSetReq lanternPrizeSetReq = new LanternPrizeSetReq();
            lanternPrizeSetReq.setType(lanternPrize.getType());
            lanternPrizeSetReq.setPrizeUnit(lanternPrize.getPrizeUnit());
            lanternPrizeSetReq.setPrizeName(lanternPrize.getPrizeName());
            lanternPrizeSetReq.setNum(lanternPrize.getNum());

            EntityWrapper<LanternPrizeImg> entityWrapper5 = new EntityWrapper();
            entityWrapper5.eq("prize_id",lanternPrize.getId());
            List<LanternPrizeImg> lanternPrizeImgList = lanternPrizeImgService.selectList(entityWrapper5);
            List<String> list5 = new ArrayList<>();
            for(LanternPrizeImg lanternPrizeImg:lanternPrizeImgList){
                list5.add(lanternPrizeImg.getImgUrl());
            }
            lanternPrizeSetReq.setImgUrl(list5);
            list4.add(lanternPrizeSetReq);
        }
        lanternGetActivityRes.setPrizeSetList(list4); //TODO  奖品设置

        return lanternGetActivityRes;
    }

    /**
     * 编辑元宵点灯活动设置
     * @param busUser
     * @param lanternModfiyReq
     */
    @Override
    public void modfiyLantern(BusUser busUser, LanternModfiyReq lanternModfiyReq) {

        LanternMain lanternMain = lanternMainService.selectById(lanternModfiyReq.getId());
        if(CommonUtil.isEmpty(lanternMain)){
            throw new LanternException(ResponseEnums.LANTERN_HAS5);
        }
        if(lanternMain.getActivityBeginTime().getTime() < new Date().getTime()){
            throw new LanternException(ResponseEnums.LANTERN_HAS10);
        }
        if(lanternMain.getBusId().intValue() != busUser.getId().intValue()){
            throw new LanternException(ResponseEnums.DIFF_USER);
        }
        //TODO 基础设置
        lanternMain.setName(lanternModfiyReq.getName());
        lanternMain.setActivityBeginTime(lanternModfiyReq.getActivityBeginTime());
        lanternMain.setActivityEndTime(lanternModfiyReq.getActivityEndTime());
        lanternMain.setBgmSp(lanternModfiyReq.getBgmSp());
        lanternMain.setMusicUrl(lanternModfiyReq.getMusicUrl());

        //TODO 规则设置
        lanternMain.setFollowQrCode(lanternModfiyReq.getFollowQrCode());
        lanternMain.setManTotalChance(lanternModfiyReq.getManTotalChance());
        lanternMain.setManDayChance(lanternModfiyReq.getManDayChance());
        lanternMain.setGameTime(lanternModfiyReq.getGameTime());
        lanternMain.setActRule(lanternModfiyReq.getActRule());

        //TODO 兑奖设置
        lanternMain.setCashPrizeBeginTime(lanternModfiyReq.getCashPrizeBeginTime());
        lanternMain.setCashPrizeEndTime(lanternModfiyReq.getCashPrizeEndTime());

        String s1="";
        for(String s:lanternModfiyReq.getReceiveTypeList()){
            s1 = s1+","+s;
        }
        lanternMain.setReceiveType(s1.substring(1));
        lanternMain.setPhone(lanternModfiyReq.getPhone());
        lanternMain.setCashPrizeInstruction(lanternModfiyReq.getCashPrizeInstruction());
        lanternMain.setPrizeDescription(lanternModfiyReq.getPrizeDescription());
        lanternMainService.updateById(lanternMain);

        //TODO  清空兑奖地址
        EntityWrapper<LanternAddress> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_id",lanternMain.getId());
        lanternAddressService.delete(entityWrapper);

        for(String s:lanternModfiyReq.getReceiveTypeList()){
            if("1".equals(s)){
                for(String s2:lanternModfiyReq.getAddressList()){
                    LanternAddress lanternAddress = new LanternAddress();
                    lanternAddress.setActId(lanternMain.getId());
                    lanternAddress.setCreatetime(new Date());
                    lanternAddress.setAddress(s2);

                    lanternAddressService.insert(lanternAddress);
                }
            }
        }

        //TODO 奖项设置
        if(lanternModfiyReq.getAdvertisingPictureList().size()>0){  // TODO  广告轮播图
            //TODO  清空广告轮播图
            EntityWrapper<LanternAd> entityWrapper2 = new EntityWrapper();
            entityWrapper2.eq("act_id",lanternModfiyReq.getId());
            lanternAdService.delete(entityWrapper2);

            for(LanternAdvertisingPictureReq lanternAdvertisingPictureReq:lanternModfiyReq.getAdvertisingPictureList()){
                LanternAd lanternAd = new LanternAd();
                lanternAd.setActId(lanternModfiyReq.getId());
                lanternAd.setUrl(lanternAdvertisingPictureReq.getUrl());
                lanternAd.setHrefUrl(lanternAdvertisingPictureReq.getHrefUrl());

                lanternAdService.insert(lanternAd);
            }
        }

        Double fenbi = 0.0;
        Double num   = 0.0;
        int f = 0;
        if(lanternModfiyReq.getPrizeSetList().size()>0){        //TODO  奖品设置

            //TODO 统计粉币
            EntityWrapper<LanternPrize> entityWrapper5 = new EntityWrapper();
            entityWrapper5.eq("act_id",lanternModfiyReq.getId());
            List<LanternPrize> lanternPrizeList = lanternPrizeService.selectList(entityWrapper5);
            if(lanternPrizeList.size() > 0) {
                for (LanternPrize lanternPrize : lanternPrizeList) {
                    if (lanternPrize.getType() == 1) {
                        num += lanternPrize.getNum();
                        f = 1;
                    }
                }
            }

            // TODO  清空奖品设置
            EntityWrapper<LanternPrize> entityWrapper3 = new EntityWrapper();
            entityWrapper3.eq("act_id",lanternModfiyReq.getId());
            lanternPrizeService.delete(entityWrapper3);

            for(LanternPrizeSetReq lanternPrizeSetReq:lanternModfiyReq.getPrizeSetList()){
                if (lanternPrizeSetReq.getType() == 1) {
                    fenbi += lanternPrizeSetReq.getNum();
                }
                LanternPrize lanternPrize = new LanternPrize();
                lanternPrize.setActId(lanternModfiyReq.getId());
                lanternPrize.setType(lanternPrizeSetReq.getType());
                lanternPrize.setPrizeUnit(lanternPrizeSetReq.getPrizeUnit());
                lanternPrize.setPrizeName(lanternPrizeSetReq.getPrizeName());
                lanternPrize.setNum(lanternPrizeSetReq.getNum());

                lanternPrizeService.insert(lanternPrize);

                //TODO 清空图片
                EntityWrapper<LanternPrizeImg> entityWrapper4 = new EntityWrapper();
                entityWrapper4.eq("prize_id",lanternPrize.getId());
                lanternPrizeImgService.delete(entityWrapper4);

                if(lanternPrizeSetReq.getImgUrl().size()>0){   ///TODO 添加图片

                    for(String s:lanternPrizeSetReq.getImgUrl()){
                        LanternPrizeImg lanternPrizeImg = new LanternPrizeImg();
                        lanternPrizeImg.setPrizeId(lanternPrize.getId());
                        lanternPrizeImg.setImgUrl(s);
                        lanternPrizeImgService.insert(lanternPrizeImg);
                    }
                }
            }
        }

        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if ((fenbi - num) <= (0 - num)) {
                    throw new LanternException(ResponseEnums.LANTERN_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi - num)) {
                    throw new LanternException(ResponseEnums.LANTERN_HAS7);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(lanternMain.getId());
                updateFenbiReduceReq.setFreType(97);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi - num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if (axisResult.getCode() != 0) {
                    throw new LanternException(ResponseEnums.LANTERN_HAS8);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new LanternException(ResponseEnums.LANTERN_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi, lanternMain.getId(), 97, 1, "元宵点灯活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new LanternException(ResponseEnums.LANTERN_HAS8);
                    }
                }
            }
        }
    }

    /**
     * 删除元宵点灯活动
     * @param busUser
     * @param lanternDelReq
     */
    @Override
    public void delLantern(BusUser busUser, LanternDelReq lanternDelReq) {

        LanternMain lanternMain = lanternMainService.selectById(lanternDelReq.getId());
        if(CommonUtil.isNotEmpty(lanternMain)) {
            if (lanternMain.getActivityBeginTime().getTime() < new Date().getTime() && lanternMain.getActivityEndTime().getTime() > new Date().getTime()) {
                throw new LanternException(ResponseEnums.LANTERN_HAS11);
            }
            if (lanternMain.getCashPrizeBeginTime().getTime() < new Date().getTime() && lanternMain.getCashPrizeEndTime().getTime() > new Date().getTime()) {
                throw new LanternException(ResponseEnums.LANTERN_HAS12);
            }

            List<LanternCashPrizeApply> lanternCashPrizeApplyList = lanternCashPrizeApplyService.selectList(
                    new EntityWrapper<LanternCashPrizeApply>().eq("act_id", lanternDelReq.getId()).eq("status", 3));
            if (lanternCashPrizeApplyList.size() > 0) {
                throw new LanternException(ResponseEnums.LANTERN_HAS13);

            }
            if(lanternMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new LanternException(ResponseEnums.DIFF_USER);
            }
        }

        //TODO  删除活动
        boolean b = lanternMainService.deleteById(lanternDelReq.getId());
        if(b==false){
            throw  new LanternException(ResponseEnums.LANTERN_HAS6);
        }

        //TODO  删除活动地址
        EntityWrapper< LanternAddress> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("act_id",lanternDelReq.getId());
        lanternAddressService.delete(entityWrapper);

        //TODO  删除广告轮播图
        EntityWrapper<LanternAd> entityWrapper2 = new EntityWrapper<>();
        entityWrapper2.eq("act_id",lanternDelReq.getId());
        lanternAdService.delete(entityWrapper2);


        EntityWrapper<LanternPrize> entityWrapper3 = new EntityWrapper<>();
        entityWrapper3.eq("act_id",lanternDelReq.getId());
        List<LanternPrize> lanternPrizeList = lanternPrizeService.selectList(entityWrapper3);

        boolean ff = false;
        if(lanternPrizeList.size() > 0){
            for(LanternPrize lanternPrize : lanternPrizeList){
                if(lanternPrize.getType() == 1){
                    ff = true;
                }
            }
        }

        if(lanternPrizeList.size()>0){
            for(LanternPrize lanternPrize:lanternPrizeList){

                //TODO  删除奖品图片
                EntityWrapper<LanternPrizeImg> entityWrapper4 = new EntityWrapper<>();
                entityWrapper4.eq("prize_id",lanternPrize.getId());
                lanternPrizeImgService.delete(entityWrapper4);
            }
        }

        //TODO  删除奖品
        EntityWrapper<LanternPrize> entityWrapper5 = new EntityWrapper<>();
        entityWrapper5.eq("act_id",lanternDelReq.getId());
        lanternPrizeService.delete(entityWrapper5);

        //TODO  删除活动用户授权信息
        EntityWrapper<LanternAuthority> entityWrapper6 = new EntityWrapper<>();
        entityWrapper6.eq("act_id",lanternDelReq.getId());
        lanternAuthorityService.delete(entityWrapper6);

        EntityWrapper<LanternReport> entityWrapper7 = new EntityWrapper<>();
        entityWrapper7.eq("act_id",lanternDelReq.getId());
        lanternReportService.delete(entityWrapper7);

        EntityWrapper<LanternPlayRecord> entityWrapper8 = new EntityWrapper<>();
        entityWrapper8.eq("act_id",lanternDelReq.getId());
        lanternPlayRecordService.delete(entityWrapper8);

        //删除冻结信息
        if(ff){
            FenbiSurplus fenbiSurplus = new FenbiSurplus();
            fenbiSurplus.setBusId(busUser.getId());
            fenbiSurplus.setFkId(lanternMain.getId());
            fenbiSurplus.setFre_type(97);
            fenbiSurplus.setRec_type(1);
            AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
            if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                // 获取冻结信息中粉币剩余量
                FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                fenbiSurplus1.setBusId(busUser.getId());
                fenbiSurplus1.setFkId(lanternMain.getId());
                fenbiSurplus1.setRec_type(1);
                fenbiSurplus1.setFre_type(97);
                AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                if(axisResult.getCode() != 0){
                    throw new LanternException(ResponseEnums.LANTERN_HAS14);
                }
            }
        }
    }

    /**
     * 分页获取元宵点灯中奖记录列表
     * @param busUser
     * @param lanternGetWinningReq
     * @return
     */
    @Override
    public ResponseDTO<List<LanternGetWinningRes>> getWinningList(BusUser busUser, LanternGetWinningReq lanternGetWinningReq) {

        Page<LanternGetWinningRes> page = new Page<>(lanternGetWinningReq.getCurrent(),lanternGetWinningReq.getSize());
        List<LanternGetWinningRes> lanternGetWinningResList = lanternCashPrizeApplyDAO.queryRecodList(page,lanternGetWinningReq);

        //TODO  获取用户名
        if(lanternGetWinningResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < lanternGetWinningResList.size(); i++) {
                ids.append(lanternGetWinningResList.get(i).getMemberId());
                if (lanternGetWinningResList.size() > 1 && i < lanternGetWinningResList.size() - 1) {
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
            for (int i = 0; i < lanternGetWinningResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == lanternGetWinningResList.get(i).getMemberId().intValue()) {
                        lanternGetWinningResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(lanternGetWinningResList.get(i).getMemberName())) {
                    lanternGetWinningResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取元宵点灯中奖记录列表成功",lanternGetWinningResList,pageDTO);

    }

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param lanternEditApplyReq
     * @return
     */
    @Override
    public ResponseDTO editLanternApply(BusUser busUser, LanternEditApplyReq lanternEditApplyReq) {

        LanternCashPrizeApply lanternCashPrizeApply = lanternCashPrizeApplyService.selectById(lanternEditApplyReq.getId());
        if(CommonUtil.isNotEmpty(lanternCashPrizeApply)){
            LanternMain lanternMain = lanternMainService.selectById(lanternCashPrizeApply.getActId());
            LanternPrize lanternPrize = lanternPrizeService.selectById(lanternCashPrizeApply.getPrizeId());
            if(lanternPrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(lanternMain.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new LanternException(ResponseEnums.LANTERN_HAS1);
                } else if (!DateTimeKit.laterThanNow(lanternMain.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new LanternException(ResponseEnums.LANTERN_HAS2);
                }
            }
            if (lanternCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                lanternCashPrizeApply.setStatus(2);
                lanternCashPrizeApply.setCashTime(new Date());
                lanternCashPrizeApplyService.updateById(lanternCashPrizeApply);
            } else if (lanternCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new LanternException(ResponseEnums.LANTERN_HAS3);
            }else{//还未提交
                throw  new LanternException(ResponseEnums.LANTERN_HAS4);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }

    /**
     * 导出元宵点灯中奖记录
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> exportLantern(Map<String, Object> params) {

        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            LanternMain lanternMain = lanternMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = lanternCashPrizeApplyDAO.findExports(params);
            String title = "活动名称：" + lanternMain.getName() + "，开始时间：" + sdf.format(lanternMain.getActivityBeginTime()) + "结束时间："
                    + sdf.format(lanternMain.getActivityEndTime());
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("member_id"));
                    if (list.size() > 1 && i < list.size() - 1) {
                        ids.append(",");
                    }
                }
                MemberReq memberReq = new MemberReq();
                memberReq.setBusId(lanternMain.getBusId());
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
            msg.put("fileName", lanternMain.getName());
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
     * 批量删除元宵点灯中奖记录
     * @param busUser
     * @param lanternDelWinningReq
     * @return
     */
    @Override
    public void delLanternWinning(BusUser busUser, LanternDelWinningReq lanternDelWinningReq) {

        lanternCashPrizeApplyService.deleteBatchIds(lanternDelWinningReq.getId());
    }

    /**
     * 分页获取核销授权列表
     * @param busUser
     * @param lanternAuthorityListReq
     * @return
     */
    @Override
    public ResponseDTO<List<LanternAuthorityListRes>> getLanternAuthorityList(BusUser busUser, LanternAuthorityListReq lanternAuthorityListReq) {

        EntityWrapper<LanternAuthority> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_id",lanternAuthorityListReq.getActId());
        entityWrapper.eq("delete_status",0);

        Page<LanternAuthority> page = new Page<>(lanternAuthorityListReq.getCurrent(),lanternAuthorityListReq.getSize());
        List<LanternAuthority> lanternAuthoritieList = lanternAuthorityService.selectPage(page,entityWrapper).getRecords();

        List<LanternAuthorityListRes> lanternAuthorityListResList = new ArrayList<>();
        for(LanternAuthority lanternAuthority:lanternAuthoritieList){
            LanternAuthorityListRes lanternAuthorityListRes = new LanternAuthorityListRes();
            lanternAuthorityListRes.setId(lanternAuthority.getId());
            lanternAuthorityListRes.setMemberName(lanternAuthority.getMemberName());
            lanternAuthorityListRes.setCreatetime(lanternAuthority.getCreatetime());

            lanternAuthorityListResList.add(lanternAuthorityListRes);
        }

        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取核销授权列表成功", lanternAuthorityListResList, pageDTO);
    }

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<List<LanternPrizeTypeListRes>> getLanternPrizeType(BusUser busUser) {

        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<LanternPrizeTypeListRes> lanternPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                LanternPrizeTypeListRes lanternPrizeTypeListRes = new LanternPrizeTypeListRes();
                lanternPrizeTypeListRes.setName(dictApiRes.getItemValue());
                lanternPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                lanternPrizeTypeListResList.add(lanternPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",lanternPrizeTypeListResList);
    }

    /**
     * 批量删除核销授权
     * @param busUser
     * @param lanternDelAuthorityReq
     */
    @Override
    public void delLanternAuthority(BusUser busUser, LanternDelAuthorityReq lanternDelAuthorityReq) {

        LanternMain lanternMain = lanternMainService.selectById(lanternDelAuthorityReq.getActId());
        if(CommonUtil.isNotEmpty(lanternMain)) {
            if (lanternMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new LanternException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new LanternException(ResponseEnums.LANTERN_HAS5);
        }

        for(Integer id:lanternDelAuthorityReq.getId()){
            LanternAuthority lanternAuthority = new LanternAuthority();
            lanternAuthority.setId(id);
            lanternAuthority.setDeleteStatus(1);
            lanternAuthorityService.updateById(lanternAuthority);
        }
    }
}