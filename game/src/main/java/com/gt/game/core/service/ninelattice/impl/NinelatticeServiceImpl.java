package com.gt.game.core.service.ninelattice.impl;

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
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.lantern.req.*;
import com.gt.game.core.bean.lantern.req.LanternAdvertisingPictureReq;
import com.gt.game.core.bean.lantern.req.LanternDelWinningReq;
import com.gt.game.core.bean.lantern.res.*;
import com.gt.game.core.bean.ninelattice.req.*;
import com.gt.game.core.bean.ninelattice.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.lantern.LanternCashPrizeApplyDAO;
import com.gt.game.core.dao.ninelattice.NinelatticeCashPrizeApplyDAO;
import com.gt.game.core.entity.dragonboat.DragonboatraceAd;
import com.gt.game.core.entity.dragonboat.DragonboatraceAddress;
import com.gt.game.core.entity.dragonboat.DragonboatracePrize;
import com.gt.game.core.entity.dragonboat.DragonboatracePrizeImg;
import com.gt.game.core.entity.lantern.*;
import com.gt.game.core.entity.ninelattice.*;
import com.gt.game.core.exception.dragonboat.DragonboatException;
import com.gt.game.core.exception.lantern.LanternException;
import com.gt.game.core.exception.ninelattice.NinelatticeException;
import com.gt.game.core.service.lantern.*;
import com.gt.game.core.service.ninelattice.*;
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
 * 幸运九宫格  服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class NinelatticeServiceImpl implements NinelatticeService {

    @Autowired
    NinelatticeMainService ninelatticeMainService;

    @Autowired
    NinelatticeAddressService ninelatticeAddressService;

    @Autowired
    NinelatticePrizeService ninelatticePrizeService;

    @Autowired
    NinelatticePrizeImgService ninelatticePrizeImgService;

    @Autowired
    NinelatticeCashPrizeApplyService ninelatticeCashPrizeApplyService;

    @Autowired
    NinelatticeAuthorityService ninelatticeAuthorityService;

    @Autowired
    NinelatticeCashPrizeApplyDAO ninelatticeCashPrizeApplyDAO;

    /**
     * 获取手机端链接
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {
        return null;
    }

    /**
     *分页获取幸运九宫格活动列表
     * @param busUser
     * @param ninelatticeListReq
     * @return
     */
    @Override
    public ResponseDTO<List<NinelatticeListRes>> getNinelatticeList(BusUser busUser, NinelatticeListReq ninelatticeListReq) {

        EntityWrapper<NinelatticeMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("bus_id", busUser.getId());
        entityWrapper.orderBy("createtime", false);
        entityWrapper.like(CommonUtil.isNotEmpty(ninelatticeListReq.getName()), "name", ninelatticeListReq.getName());
        if (ninelatticeListReq.getStatus() != -1) {   // TODO -1 全部 0 未开始 1 进行中 2 已结束
            if (ninelatticeListReq.getStatus() == 0) {
                entityWrapper.where("activity_begin_time > {0}", new Date());
            }
            if (ninelatticeListReq.getStatus() == 1) {
                entityWrapper.where("activity_begin_time <= {0}", new Date());
                entityWrapper.and();
                entityWrapper.where("activity_end_time > {0}", new Date());
            }
            if (ninelatticeListReq.getStatus() == 2) {
                entityWrapper.where("activity_end_time <= {0}", new Date());
            }
        }
        Page<NinelatticeMain> page = new Page<>(ninelatticeListReq.getCurrent(), ninelatticeListReq.getSize());
        List<NinelatticeMain> ninelatticeMainList = ninelatticeMainService.selectPage(page, entityWrapper).getRecords();

        List<NinelatticeListRes> ninelatticeListResList = new ArrayList<>();
        for (NinelatticeMain ninelatticeMain : ninelatticeMainList) {
            NinelatticeListRes ninelatticeListRes = new NinelatticeListRes();
            ninelatticeListRes.setId(ninelatticeMain.getId());
            ninelatticeListRes.setName(ninelatticeMain.getName());
            ninelatticeListRes.setActivityBeginTime(ninelatticeMain.getActivityBeginTime());
            ninelatticeListRes.setActivityEndTime(ninelatticeMain.getActivityEndTime());
            ninelatticeListRes.setCashPrizeBeginTime(ninelatticeMain.getCashPrizeBeginTime());
            ninelatticeListRes.setCashPrizeEndTime(ninelatticeMain.getCashPrizeEndTime());

            Date date = new Date();
            if (ninelatticeMain.getActivityBeginTime().getTime() > date.getTime()) {
                ninelatticeListRes.setStatus(0);
            } else if (ninelatticeMain.getActivityBeginTime().getTime() <= date.getTime() && ninelatticeMain.getActivityEndTime().getTime() >= date.getTime()) {
                ninelatticeListRes.setStatus(1);
            } else if (ninelatticeMain.getActivityEndTime().getTime() < date.getTime()) {
                ninelatticeListRes.setStatus(2);
            }

            ninelatticeListResList.add(ninelatticeListRes);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取幸运九宫格活动列表成功", ninelatticeListResList, pageDTO);
    }

    /**
     * 统计元幸运九宫格活动总数
     * @param busUser
     * @param ninelatticeCountActivityReq
     * @return
     */
    @Override
    public NinelatticeCountActivityRes countNinelattice(BusUser busUser, NinelatticeCountActivityReq ninelatticeCountActivityReq) {

        EntityWrapper<NinelatticeMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("bus_id", busUser.getId());
        entityWrapper.like(CommonUtil.isNotEmpty(ninelatticeCountActivityReq.getName()), "name", ninelatticeCountActivityReq.getName());

        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        List<NinelatticeMain> ninelatticeMainList = ninelatticeMainService.selectList(entityWrapper);
        Date date = new Date();
        for (NinelatticeMain ninelatticeMain : ninelatticeMainList) {
            if (ninelatticeMain.getActivityBeginTime().getTime() > date.getTime()) {
                count2++; //  TODO    未开始
            } else if (ninelatticeMain.getActivityBeginTime().getTime() <= date.getTime() && ninelatticeMain.getActivityEndTime().getTime() >= date.getTime()) {
                count3++;  // TODO    进行中
            } else if (ninelatticeMain.getActivityEndTime().getTime() < date.getTime()) {
                count4++;  // TODO    已结束
            }
        }
        NinelatticeCountActivityRes ninelatticeCountActivityRes = new NinelatticeCountActivityRes();
        ninelatticeCountActivityRes.setCount2(count2);
        ninelatticeCountActivityRes.setCount3(count3);
        ninelatticeCountActivityRes.setCount4(count4);
        ninelatticeCountActivityRes.setCount1(count2+count3+count4);  //  TODO 全部
        return  ninelatticeCountActivityRes;
    }

    /**
     * 新增幸运九宫格活动
     * @param busUser
     * @param ninelatticeAddReq
     */
    @Override
    public void addNinelattice(BusUser busUser, NinelatticeAddReq ninelatticeAddReq) {

        NinelatticeMain ninelatticeMain = new NinelatticeMain();
        ninelatticeMain.setBusId(busUser.getId());
        ninelatticeMain.setCreatetime(new Date());
        ninelatticeMain.setName(ninelatticeAddReq.getName());
        ninelatticeMain.setActivityBeginTime(ninelatticeAddReq.getActivityBeginTime());
        ninelatticeMain.setActivityEndTime(ninelatticeAddReq.getActivityEndTime());
        ninelatticeMain.setFollowQrCode(ninelatticeAddReq.getFollowQrCode());
        ninelatticeMain.setManTotalChance(ninelatticeAddReq.getManTotalChance());
        ninelatticeMain.setManDayChance(ninelatticeAddReq.getManDayChance());
        ninelatticeMain.setActRule(ninelatticeAddReq.getActRule());
        ninelatticeMain.setCashPrizeBeginTime(ninelatticeAddReq.getCashPrizeBeginTime());
        ninelatticeMain.setCashPrizeEndTime(ninelatticeAddReq.getCashPrizeEndTime());

        String s1="";
        for(String s:ninelatticeAddReq.getReceiveTypeList()){
            s1 = s1+","+s;
        }
        ninelatticeMain.setReceiveType(s1.substring(1));
        ninelatticeMain.setPhone(ninelatticeAddReq.getPhone());
        ninelatticeMain.setCashPrizeInstruction(ninelatticeAddReq.getCashPrizeInstruction());

        ninelatticeMainService.insert(ninelatticeMain);

        for(String s:ninelatticeAddReq.getReceiveTypeList()){
            if("1".equals(s)){
                for(String s2:ninelatticeAddReq.getAddressList()){
                    NinelatticeAddress ninelatticeAddress = new NinelatticeAddress();
                    ninelatticeAddress.setActId(ninelatticeMain.getId());
                    ninelatticeAddress.setCreatetime(new Date());
                    ninelatticeAddress.setAddress(s2);

                    ninelatticeAddressService.insert(ninelatticeAddress);
                }
            }
        }

        if(ninelatticeAddReq.getPrizeSetList().size()>0){        //TODO  奖品设置
            for(NinelatticePrizeSetReq ninelatticePrizeSetReq:ninelatticeAddReq.getPrizeSetList()){
                NinelatticePrize ninelatticePrize = new NinelatticePrize();
                ninelatticePrize.setActId(ninelatticeMain.getId());
                ninelatticePrize.setType(ninelatticePrizeSetReq.getType());
                ninelatticePrize.setPrizeUnit(ninelatticePrizeSetReq.getPrizeUnit());
                ninelatticePrize.setPrizeName(ninelatticePrizeSetReq.getPrizeName());
                ninelatticePrize.setNum(ninelatticePrizeSetReq.getNum());
                ninelatticePrize.setProbabiliy(ninelatticePrizeSetReq.getProbabiliy());

                ninelatticePrizeService.insert(ninelatticePrize);

                if(ninelatticePrizeSetReq.getImgUrl().size()>0){   ///TODO 添加图片

                    for(String s:ninelatticePrizeSetReq.getImgUrl()){
                        NinelatticePrizeImg ninelatticePrizeImg = new NinelatticePrizeImg();
                        ninelatticePrizeImg.setPrizeId(ninelatticePrize.getId());
                        ninelatticePrizeImg.setImgUrl(s);
                        ninelatticePrizeImgService.insert(ninelatticePrizeImg);
                    }
                }
            }
        }
    }

    /**
     * 通过活动id查询幸运九宫格活动
     * @param busUser
     * @param ninelatticeGetActivityReq
     * @return
     */
    @Override
    public NinelatticeGetActivityRes getActivityById(BusUser busUser, NinelatticeGetActivityReq ninelatticeGetActivityReq) {

        NinelatticeMain ninelatticeMain = ninelatticeMainService.selectById(ninelatticeGetActivityReq.getId());
        NinelatticeGetActivityRes ninelatticeGetActivityRes = new NinelatticeGetActivityRes();
        ninelatticeGetActivityRes.setName(ninelatticeMain.getName());
        ninelatticeGetActivityRes.setActivityBeginTime(ninelatticeMain.getActivityBeginTime());
        ninelatticeGetActivityRes.setActivityEndTime(ninelatticeMain.getActivityEndTime());
        ninelatticeGetActivityRes.setCashPrizeBeginTime(ninelatticeMain.getCashPrizeBeginTime());
        ninelatticeGetActivityRes.setCashPrizeEndTime(ninelatticeMain.getCashPrizeEndTime());
        ninelatticeGetActivityRes.setFollowQrCode(ninelatticeMain.getFollowQrCode());
        ninelatticeGetActivityRes.setManTotalChance(ninelatticeMain.getManTotalChance());
        ninelatticeGetActivityRes.setManDayChance(ninelatticeMain.getManDayChance());
        ninelatticeGetActivityRes.setActRule(ninelatticeMain.getActRule());

        ArrayList<String> list = new ArrayList<>();
        for (String s : ninelatticeMain.getReceiveType().split(",")) {
            list.add(s);
        }
        ninelatticeGetActivityRes.setReceiveTypeList(list);   //  TODO  对奖方式

        EntityWrapper<NinelatticeAddress> entityWrapper2 = new EntityWrapper();
        entityWrapper2.eq("act_id", ninelatticeMain.getId());
        List<NinelatticeAddress> ninelatticeAddressList = ninelatticeAddressService.selectList(entityWrapper2);

        List<String> list2 = new ArrayList<>();
        for(NinelatticeAddress ninelatticeAddress :ninelatticeAddressList){
            list2.add(ninelatticeAddress.getAddress());
        }
        ninelatticeGetActivityRes.setAddressList(list2);    // TODO  兑奖地址

        ninelatticeGetActivityRes.setPhone(ninelatticeMain.getPhone());
        ninelatticeGetActivityRes.setCashPrizeInstruction(ninelatticeMain.getCashPrizeInstruction());

        EntityWrapper<NinelatticePrize> entityWrapper4 = new EntityWrapper();
        entityWrapper4.eq("act_id", ninelatticeMain.getId());
        List<NinelatticePrize> ninelatticePrizeList= ninelatticePrizeService.selectList(entityWrapper4);
        List<NinelatticePrizeSetReq> list4 = new ArrayList<>();
        for(NinelatticePrize ninelatticePrize :ninelatticePrizeList){
            NinelatticePrizeSetReq ninelatticePrizeSetReq = new NinelatticePrizeSetReq();
            ninelatticePrizeSetReq.setType(ninelatticePrize.getType());
            ninelatticePrizeSetReq.setPrizeUnit(ninelatticePrize.getPrizeUnit());
            ninelatticePrizeSetReq.setPrizeName(ninelatticePrize.getPrizeName());
            ninelatticePrizeSetReq.setNum(ninelatticePrize.getNum());
            ninelatticePrizeSetReq.setProbabiliy(ninelatticePrize.getProbabiliy());

            EntityWrapper<NinelatticePrizeImg> entityWrapper5 = new EntityWrapper();
            entityWrapper5.eq("prize_id",ninelatticePrize.getId());
            List<NinelatticePrizeImg> ninelatticePrizeImgList = ninelatticePrizeImgService.selectList(entityWrapper5);
            List<String> list5 = new ArrayList<>();
            for(NinelatticePrizeImg ninelatticePrizeImg:ninelatticePrizeImgList){
                list5.add(ninelatticePrizeImg.getImgUrl());
            }
            ninelatticePrizeSetReq.setImgUrl(list5);
            list4.add(ninelatticePrizeSetReq);
        }
        ninelatticeGetActivityRes.setPrizeSetList(list4); //TODO  奖品设置

        return ninelatticeGetActivityRes;
    }

    /**
     * 编辑幸运九宫格活动基础设置
     * @param busUser
     * @param ninelatticeModfiyBasicsReq
     */
    @Override
    public void modfiyBasicsNinelattice(BusUser busUser, NinelatticeModfiyBasicsReq ninelatticeModfiyBasicsReq) {

        NinelatticeMain ninelatticeMain = new NinelatticeMain();
        ninelatticeMain.setId(ninelatticeModfiyBasicsReq.getId());
        ninelatticeMain.setName(ninelatticeModfiyBasicsReq.getName());
        ninelatticeMain.setActivityBeginTime(ninelatticeModfiyBasicsReq.getActivityBeginTime());
        ninelatticeMain.setActivityEndTime(ninelatticeModfiyBasicsReq.getActivityEndTime());

        ninelatticeMainService.updateById(ninelatticeMain);
    }

    /**
     * 编辑幸运九宫格活动规则设置
     * @param busUser
     * @param ninelatticeModfiyRuleReq
     */
    @Override
    public void modfiyRuleNinelattice(BusUser busUser, NinelatticeModfiyRuleReq ninelatticeModfiyRuleReq) {

        NinelatticeMain ninelatticeMain = new NinelatticeMain();
        ninelatticeMain.setId(ninelatticeModfiyRuleReq.getId());
        ninelatticeMain.setFollowQrCode(ninelatticeModfiyRuleReq.getFollowQrCode());
        ninelatticeMain.setManTotalChance(ninelatticeModfiyRuleReq.getManTotalChance());
        ninelatticeMain.setManDayChance(ninelatticeModfiyRuleReq.getManDayChance());
        ninelatticeMain.setActRule(ninelatticeModfiyRuleReq.getActRule());

        ninelatticeMainService.updateById(ninelatticeMain);
    }

    /**
     * 编辑幸运九宫格活动兑奖设置
     * @param busUser
     * @param ninelatticeModfiyExpiryReq
     */
    @Override
    public void modfiyExpiryNinelattice(BusUser busUser, NinelatticeModfiyExpiryReq ninelatticeModfiyExpiryReq) {

        NinelatticeMain ninelatticeMain = new NinelatticeMain();
        ninelatticeMain.setId(ninelatticeModfiyExpiryReq.getId());
        ninelatticeMain.setCashPrizeBeginTime(ninelatticeModfiyExpiryReq.getCashPrizeBeginTime());
        ninelatticeMain.setCashPrizeEndTime(ninelatticeModfiyExpiryReq.getCashPrizeEndTime());

        String s1="";
        for(String s:ninelatticeModfiyExpiryReq.getReceiveTypeList()){
            s1 = s1+","+s;
        }
        ninelatticeMain.setReceiveType(s1.substring(1));
        ninelatticeMain.setPhone(ninelatticeModfiyExpiryReq.getPhone());
        ninelatticeMain.setCashPrizeInstruction(ninelatticeModfiyExpiryReq.getCashPrizeInstruction());

        ninelatticeMainService.updateById(ninelatticeMain);

        //TODO  清空兑奖地址
        EntityWrapper<NinelatticeAddress> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_id",ninelatticeMain.getId());
        ninelatticeAddressService.delete(entityWrapper);

        for(String s:ninelatticeModfiyExpiryReq.getReceiveTypeList()){
            if("1".equals(s)){
                for(String s2:ninelatticeModfiyExpiryReq.getAddressList()){
                    NinelatticeAddress ninelatticeAddress = new NinelatticeAddress();
                    ninelatticeAddress.setActId(ninelatticeMain.getId());
                    ninelatticeAddress.setCreatetime(new Date());
                    ninelatticeAddress.setAddress(s2);

                    ninelatticeAddressService.insert(ninelatticeAddress);
                }
            }
        }
    }

    /**
     * 编辑幸运九宫格奖项设置
     * @param busUser
     * @param ninelatticeModfiyAwardsReq
     */
    @Override
    public void modfiyAwardsNinelattice(BusUser busUser, NinelatticeModfiyAwardsReq ninelatticeModfiyAwardsReq) {

        if(ninelatticeModfiyAwardsReq.getPrizeSetList().size()>0){        //TODO  奖品设置
            // TODO  清空奖品设置
            EntityWrapper<NinelatticePrize> entityWrapper = new EntityWrapper();
            entityWrapper.eq("act_id",ninelatticeModfiyAwardsReq.getId());
            ninelatticePrizeService.delete(entityWrapper);

            for(NinelatticePrizeSetReq ninelatticePrizeSetReq:ninelatticeModfiyAwardsReq.getPrizeSetList()){
                NinelatticePrize ninelatticePrize = new NinelatticePrize();
                ninelatticePrize.setActId(ninelatticeModfiyAwardsReq.getId());
                ninelatticePrize.setType(ninelatticePrizeSetReq.getType());
                ninelatticePrize.setPrizeUnit(ninelatticePrizeSetReq.getPrizeUnit());
                ninelatticePrize.setPrizeName(ninelatticePrizeSetReq.getPrizeName());
                ninelatticePrize.setNum(ninelatticePrizeSetReq.getNum());
                ninelatticePrize.setProbabiliy(ninelatticePrizeSetReq.getProbabiliy());

                ninelatticePrizeService.insert(ninelatticePrize);

                //TODO 清空图片
                EntityWrapper<NinelatticePrizeImg> entityWrapper2 = new EntityWrapper();
                entityWrapper2.eq("prize_id",ninelatticePrize.getId());
                ninelatticePrizeImgService.delete(entityWrapper2);

                if(ninelatticePrizeSetReq.getImgUrl().size()>0){   ///TODO 添加图片

                    for(String s:ninelatticePrizeSetReq.getImgUrl()){
                        NinelatticePrizeImg ninelatticePrizeImg = new NinelatticePrizeImg();
                        ninelatticePrizeImg.setPrizeId(ninelatticePrize.getId());
                        ninelatticePrizeImg.setImgUrl(s);
                        ninelatticePrizeImgService.insert(ninelatticePrizeImg);
                    }
                }
            }
        }
    }

    /**
     * 删除幸运九宫格活动
     * @param busUser
     * @param ninelatticeDelReq
     */
    @Override
    public void delNinelattice(BusUser busUser, NinelatticeDelReq ninelatticeDelReq) {

        //TODO  删除活动
        boolean b = ninelatticeMainService.deleteById(ninelatticeDelReq.getId());
        if(b==false){
            throw  new NinelatticeException(ResponseEnums.NINELATTICE_HAS6);
        }

        //TODO  删除活动地址
        EntityWrapper<NinelatticeAddress> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("act_id",ninelatticeDelReq.getId());
        ninelatticeAddressService.delete(entityWrapper);


        EntityWrapper<NinelatticePrize> entityWrapper2 = new EntityWrapper<>();
        entityWrapper2.eq("act_id",ninelatticeDelReq.getId());
        List<NinelatticePrize> ninelatticePrizeList = ninelatticePrizeService.selectList(entityWrapper2);

        if(ninelatticePrizeList.size()>0){
            for(NinelatticePrize ninelatticePrize:ninelatticePrizeList){

                //TODO  删除奖品图片
                EntityWrapper<NinelatticePrizeImg> entityWrapper3 = new EntityWrapper<>();
                entityWrapper3.eq("prize_id",ninelatticePrize.getId());
                ninelatticePrizeImgService.delete(entityWrapper3);
            }
        }

        //TODO  删除奖品
        EntityWrapper<NinelatticePrize> entityWrapper4 = new EntityWrapper<>();
        entityWrapper4.eq("act_id",ninelatticeDelReq.getId());
        ninelatticePrizeService.delete(entityWrapper4);
    }

    /**
     * 分页获取幸运九宫格中奖记录列表
     * @param busUser
     * @param ninelatticeGetWinningReq
     * @return
     */
    @Override
    public ResponseDTO<List<NinelatticeGetWinningRes>> getWinningList(BusUser busUser, NinelatticeGetWinningReq ninelatticeGetWinningReq) {

        Page<NinelatticeGetWinningRes> page = new Page<>(ninelatticeGetWinningReq.getCurrent(),ninelatticeGetWinningReq.getSize());
        List<NinelatticeGetWinningRes> ninelatticeGetWinningResList = ninelatticeCashPrizeApplyDAO.queryRecodList(page,ninelatticeGetWinningReq);

        //TODO  获取用户名
        if(ninelatticeGetWinningResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < ninelatticeGetWinningResList.size(); i++) {
                ids.append(ninelatticeGetWinningResList.get(i).getMemberId());
                if (ninelatticeGetWinningResList.size() > 1 && i < ninelatticeGetWinningResList.size() - 1) {
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
            for (int i = 0; i < ninelatticeGetWinningResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == ninelatticeGetWinningResList.get(i).getMemberId().intValue()) {
                        ninelatticeGetWinningResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(ninelatticeGetWinningResList.get(i).getMemberName())) {
                    ninelatticeGetWinningResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取中奖记录列表成功",ninelatticeGetWinningResList,pageDTO);
    }

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param ninelatticeEditApplyReq
     * @return
     */
    @Override
    public ResponseDTO editNinelatticeApply(BusUser busUser, NinelatticeEditApplyReq ninelatticeEditApplyReq) {

        NinelatticeCashPrizeApply ninelatticeCashPrizeApply = ninelatticeCashPrizeApplyService.selectById(ninelatticeEditApplyReq.getId());
        if(CommonUtil.isNotEmpty(ninelatticeCashPrizeApply)){
            NinelatticeMain ninelatticeMain = ninelatticeMainService.selectById(ninelatticeCashPrizeApply.getActId());
            NinelatticePrize ninelatticePrize = ninelatticePrizeService.selectById(ninelatticeCashPrizeApply.getPrizeId());
            if(ninelatticePrize.getType() != 4){    //非兑奖
                if (DateTimeKit.laterThanNow(ninelatticeMain.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new NinelatticeException(ResponseEnums.NINELATTICE_HAS1);
                } else if (!DateTimeKit.laterThanNow(ninelatticeMain.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new NinelatticeException(ResponseEnums.NINELATTICE_HAS2);
                }
            }
            if (ninelatticeCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                ninelatticeCashPrizeApply.setStatus(2);
                ninelatticeCashPrizeApply.setCashTime(new Date());
                ninelatticeCashPrizeApplyService.updateById(ninelatticeCashPrizeApply);
            } else if (ninelatticeCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new NinelatticeException(ResponseEnums.LANTERN_HAS3);
            }else{//还未提交
                throw  new NinelatticeException(ResponseEnums.LANTERN_HAS4);
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
    public Map<String, Object> exportNinelattice(Map<String, Object> params) {

        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            NinelatticeMain ninelatticeMain = ninelatticeMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = ninelatticeCashPrizeApplyDAO.findExports(params);
            String title = "活动名称：" + ninelatticeMain.getName() + "，开始时间：" + sdf.format(ninelatticeMain.getActivityBeginTime()) + "结束时间："
                    + sdf.format(ninelatticeMain.getActivityEndTime());
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("member_id"));
                    if (list.size() > 1 && i < list.size() - 1) {
                        ids.append(",");
                    }
                }
                MemberReq memberReq = new MemberReq();
                memberReq.setBusId(ninelatticeMain.getBusId());
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
            msg.put("fileName", ninelatticeMain.getName());
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

            createCell(wb, rowData, 3, delWithColumn(""), font1);
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
     * 批量删除幸运九宫格中奖记录
     * @param busUser
     * @param ninelatticeDelWinningReq
     */
    @Override
    public void delNinelatticeWinning(BusUser busUser, NinelatticeDelWinningReq ninelatticeDelWinningReq) {

        ninelatticeCashPrizeApplyService.deleteBatchIds(ninelatticeDelWinningReq.getId());
    }

    /**
     * 分页获取核销授权列表
     * @param busUser
     * @param ninelatticeAuthorityListReq
     * @return
     */
    @Override
    public ResponseDTO<List<NinelatticeAuthorityListRes>> getNinelatticeAuthorityList(BusUser busUser, NinelatticeAuthorityListReq ninelatticeAuthorityListReq) {

        EntityWrapper<NinelatticeAuthority> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_id",ninelatticeAuthorityListReq.getActId());
        entityWrapper.eq("delete_status",0);

        Page<NinelatticeAuthority> page = new Page<>(ninelatticeAuthorityListReq.getCurrent(),ninelatticeAuthorityListReq.getSize());
        List<NinelatticeAuthority> ninelatticeAuthorityList = ninelatticeAuthorityService.selectPage(page, entityWrapper).getRecords();

        List<NinelatticeAuthorityListRes> ninelatticeAuthorityListResList = new ArrayList<>();
        for(NinelatticeAuthority ninelatticeAuthority:ninelatticeAuthorityList){
            NinelatticeAuthorityListRes ninelatticeAuthorityListRes = new NinelatticeAuthorityListRes();
            ninelatticeAuthorityListRes.setId(ninelatticeAuthority.getId());
            ninelatticeAuthorityListRes.setMemberName(ninelatticeAuthority.getMemberName());
            ninelatticeAuthorityListRes.setCreatetime(ninelatticeAuthority.getCreatetime());

            ninelatticeAuthorityListResList.add(ninelatticeAuthorityListRes);
        }

        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取核销授权列表成功", ninelatticeAuthorityListResList, pageDTO);
    }

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<List<NinelatticePrizeTypeListRes>> getNinelatticePrizeType(BusUser busUser) {

        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<NinelatticePrizeTypeListRes> ninelatticePrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                NinelatticePrizeTypeListRes ninelatticePrizeTypeListRes = new NinelatticePrizeTypeListRes();
                ninelatticePrizeTypeListRes.setName(dictApiRes.getItemValue());
                ninelatticePrizeTypeListRes.setValue(dictApiRes.getItemKey());
                ninelatticePrizeTypeListResList.add(ninelatticePrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",ninelatticePrizeTypeListResList);
    }

    /**
     * 批量删除核销授权
     * @param busUser
     * @param ninelatticeDelAuthorityReq
     */
    @Override
    public void delNinelatticeAuthority(BusUser busUser, NinelatticeDelAuthorityReq ninelatticeDelAuthorityReq) {

        NinelatticeMain ninelatticeMain = ninelatticeMainService.selectById(ninelatticeDelAuthorityReq.getActId());
        if(CommonUtil.isNotEmpty(ninelatticeMain)) {
            if (ninelatticeMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new NinelatticeException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new NinelatticeException(ResponseEnums.NINELATTICE_HAS5);
        }

        for(Integer id:ninelatticeDelAuthorityReq.getId()){
            NinelatticeAuthority ninelatticeAuthority = new NinelatticeAuthority();
            ninelatticeAuthority.setId(id);
            ninelatticeAuthority.setDeleteStatus(1);
            ninelatticeAuthorityService.updateById(ninelatticeAuthority);
        }
    }
}