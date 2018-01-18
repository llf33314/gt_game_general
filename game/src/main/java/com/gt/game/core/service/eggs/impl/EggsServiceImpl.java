package com.gt.game.core.service.eggs.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.axis.bean.wxmp.dict.DictApiReq;
import com.gt.axis.bean.wxmp.dict.DictApiRes;
import com.gt.axis.bean.wxmp.wxpublic.WxPublicUsers;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.member.MemberServer;
import com.gt.axis.server.wxmp.DictServer;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.eggs.req.*;
import com.gt.game.core.bean.eggs.res.*;
import com.gt.game.core.bean.tree.req.*;
import com.gt.game.core.bean.tree.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.eggs.EggsWinningDAO;
import com.gt.game.core.dao.tree.TreeWinningDAO;
import com.gt.game.core.entity.eggs.EggsDetail;
import com.gt.game.core.entity.eggs.EggsMain;
import com.gt.game.core.entity.eggs.EggsWinning;
import com.gt.game.core.entity.tree.TreeDetail;
import com.gt.game.core.entity.tree.TreeMain;
import com.gt.game.core.entity.tree.TreeWinning;
import com.gt.game.core.exception.eggs.EggsException;
import com.gt.game.core.exception.tree.TreeException;
import com.gt.game.core.service.eggs.EggsDetailService;
import com.gt.game.core.service.eggs.EggsMainService;
import com.gt.game.core.service.eggs.EggsService;
import com.gt.game.core.service.eggs.EggsWinningService;
import com.gt.game.core.service.tree.TreeDetailService;
import com.gt.game.core.service.tree.TreeMainService;
import com.gt.game.core.service.tree.TreeService;
import com.gt.game.core.service.tree.TreeWinningService;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 砸金蛋 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class EggsServiceImpl implements EggsService {

    @Autowired
    EggsMainService eggsMainService;

    @Autowired
    EggsDetailService eggsDetailService;

    @Autowired
    EggsWinningService eggsWinningService;

    @Autowired
    EggsWinningDAO eggsWinningDAO;
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
     * 分页获取砸金蛋活动列表
     * @param busUser
     * @param eggsListReq
     * @param request
     * @return
     */
    @Override
    public ResponseDTO<List<EggsListRes>> getEggsList(BusUser busUser, EggsListReq eggsListReq, HttpServletRequest request) {

        EntityWrapper<EggsMain> entityWrapper = new EntityWrapper();
        WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
        entityWrapper.eq("egg_wx_userid",loginPbUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(eggsListReq.getName()), "egg_name", eggsListReq.getName());
        if (eggsListReq.getStatus() != -1) {   // TODO -1 全部 0 未开始 1 进行中 2 已结束
            if (eggsListReq.getStatus() == 0) {
                entityWrapper.where("egg_beginTime > {0}", new Date());
            }
            if (eggsListReq.getStatus() == 1) {
                entityWrapper.where("egg_beginTime <= {0}", new Date());
                entityWrapper.and();
                entityWrapper.where("egg_endTime > {0}", new Date());
            }
            if (eggsListReq.getStatus() == 2) {
                entityWrapper.where("egg_endTime <= {0}", new Date());
            }
        }
        Page<EggsMain> page = new Page<>(eggsListReq.getCurrent(), eggsListReq.getSize());
        List<EggsMain> eggsMainList = eggsMainService.selectPage(page, entityWrapper).getRecords();

        List<EggsListRes> eggsListResList = new ArrayList<>();
        for (EggsMain eggsMain : eggsMainList) {
            EggsListRes eggsListRes = new EggsListRes();
            eggsListRes.setId(eggsMain.getId());
            eggsListRes.setName(eggsMain.getEggName());
            eggsListRes.setActivityBeginTime(eggsMain.getEggBeginTime());
            eggsListRes.setActivityEndTime(eggsMain.getEggEndTime());

            Date date = new Date();
            if (eggsMain.getEggBeginTime().getTime() > date.getTime()) {
                eggsListRes.setStatus(0);
            } else if (eggsMain.getEggBeginTime().getTime() <= date.getTime() && eggsMain.getEggEndTime().getTime() >= date.getTime()) {
                eggsListRes.setStatus(1);
            } else if (eggsMain.getEggEndTime().getTime() < date.getTime()) {
                eggsListRes.setStatus(2);
            }

            eggsListResList.add(eggsListRes);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取砸金蛋活动列表成功", eggsListResList, pageDTO);
    }

    /**
     * 统计砸金蛋活动总数
     * @param busUser
     * @param eggsCountActivityReq
     * @param request
     * @return
     */
    @Override
    public EggsCountActivityRes countEggs(BusUser busUser, EggsCountActivityReq eggsCountActivityReq, HttpServletRequest request) {

        EntityWrapper<EggsMain> entityWrapper = new EntityWrapper();
        WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
        entityWrapper.eq("egg_wx_userid",loginPbUser.getId());
        entityWrapper.like(CommonUtil.isNotEmpty(eggsCountActivityReq.getName()), "egg_name", eggsCountActivityReq.getName());

        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        List<EggsMain> eggsMainList = eggsMainService.selectList(entityWrapper);
        Date date = new Date();
        for (EggsMain eggsMain : eggsMainList) {
            if (eggsMain.getEggBeginTime().getTime() > date.getTime()) {
                count2++; //  TODO    未开始
            } else if (eggsMain.getEggBeginTime().getTime() <= date.getTime() && eggsMain.getEggEndTime().getTime() >= date.getTime()) {
                count3++;  // TODO    进行中
            } else if (eggsMain.getEggEndTime().getTime() < date.getTime()) {
                count4++;  // TODO    已结束
            }
        }
        EggsCountActivityRes eggsCountActivityRes = new EggsCountActivityRes();
        eggsCountActivityRes.setCount2(count2);
        eggsCountActivityRes.setCount3(count3);
        eggsCountActivityRes.setCount4(count4);
        eggsCountActivityRes.setCount1(count2+count3+count4);  //  TODO 全部
        return  eggsCountActivityRes;
    }

    /**
     * 新增砸金蛋活动成功
     * @param busUser
     * @param eggsAddReq
     * @param request
     */
    @Override
    public void addEggs(BusUser busUser, EggsAddReq eggsAddReq, HttpServletRequest request) {

        EggsMain eggsMain = new EggsMain();
        WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
        eggsMain.setEggWxUserid(loginPbUser.getId());

        //TODO  基础设置
        eggsMain.setEggName(eggsAddReq.getEggName());
        eggsMain.setEggBeginTime(eggsAddReq.getEggBeginTime());
        eggsMain.setEggEndTime(eggsAddReq.getEggEndTime());
        eggsMain.setEggEggPartaker(eggsAddReq.getEggEggPartaker());
        if(eggsAddReq.getEggEggPartaker()==2){
            eggsMain.setEggPway(eggsAddReq.getEggPway());
            if(eggsAddReq.getEggPway()==2){
                eggsMain.setEggMan(eggsAddReq.getEggMan());
            }else if(eggsAddReq.getEggPway()==3){
                eggsMain.setEggKou(eggsAddReq.getEggKou());
            }else if(eggsAddReq.getEggPway()==4){
                eggsMain.setEggMan(eggsAddReq.getEggMan());
                eggsMain.setEggKou(eggsAddReq.getEggKou());
            }
        }
        eggsMain.setEggDescribe(eggsAddReq.getEggDescribe());
        eggsMain.setEggBeforeTxt(eggsAddReq.getEggBeforeTxt());

        //TODO  规则设置
        eggsMain.setEggCountOfAll(eggsAddReq.getEggCountOfAll());
        eggsMain.setEggCountOfDay(eggsAddReq.getEggCountOfDay());

        //TODO  兑奖设置
        eggsMain.setEggCashDay(eggsAddReq.getEggCashDay());
        eggsMain.setEggAddress(eggsAddReq.getEggAddress());
        eggsMain.setEggCashWay(eggsAddReq.getEggCashWay());
        eggsMain.setEggWinningTxt(eggsAddReq.getEggWinningTxt());
        eggsMain.setEggWinningNotice(eggsAddReq.getEggWinningNotice());

        eggsMainService.insert(eggsMain);

        //TODO   奖项设置
        for(EggsPrizeSetReq eggsPrizeSetReq:eggsAddReq.getPrizeSetList()){

            EggsDetail eggsDetail = new EggsDetail();
            eggsDetail.setEggId(eggsMain.getId());
            eggsDetail.setEggPrizeType(eggsPrizeSetReq.getEggPrizeType());
            eggsDetail.setEggPrizeLimit(eggsPrizeSetReq.getEggPrizeLimit());
            eggsDetail.setEggPrizeName(eggsPrizeSetReq.getEggPrizeName());
            eggsDetail.setEggPrizeNums(eggsPrizeSetReq.getEggPrizeNums());
            eggsDetail.setEggPrizeChance(eggsPrizeSetReq.getEggPrizeChance());
            eggsDetail.setNickname(eggsPrizeSetReq.getNickname());

            eggsDetailService.insert(eggsDetail);
        }
    }

    /**
     * 通过活动id查询砸金蛋活动
     * @param busUser
     * @param eggsGetActivityReq
     * @return
     */
    @Override
    public EggsGetActivityRes getActivityById(BusUser busUser, EggsGetActivityReq eggsGetActivityReq) {

        EggsMain eggsMain = eggsMainService.selectById(eggsGetActivityReq.getId());

        EggsGetActivityRes eggsGetActivityRes = new EggsGetActivityRes();
        //TODO  基础设置
        eggsGetActivityRes.setEggName(eggsMain.getEggName());
        eggsGetActivityRes.setEggBeginTime(eggsMain.getEggBeginTime());
        eggsGetActivityRes.setEggEndTime(eggsMain.getEggEndTime());
        eggsGetActivityRes.setEggEggPartaker(eggsMain.getEggEggPartaker());
        if(eggsMain.getEggEggPartaker()==2){
            eggsGetActivityRes.setEggPway(eggsMain.getEggPway());
            if(eggsMain.getEggPway()==2){
                eggsGetActivityRes.setEggMan(eggsMain.getEggMan());
            }else if(eggsMain.getEggPway()==3){
                eggsGetActivityRes.setEggKou(eggsMain.getEggKou());
            }else if(eggsMain.getEggPway()==4){
                eggsGetActivityRes.setEggMan(eggsMain.getEggMan());
                eggsGetActivityRes.setEggKou(eggsMain.getEggKou());
            }
        }
        eggsGetActivityRes.setEggDescribe(eggsMain.getEggDescribe());
        eggsGetActivityRes.setEggBeforeTxt(eggsMain.getEggBeforeTxt());

        //TODO  规则设置
        eggsGetActivityRes.setEggCountOfAll(eggsMain.getEggCountOfAll());
        eggsGetActivityRes.setEggCountOfDay(eggsMain.getEggCountOfDay());

        //TODO  兑奖设置
        eggsGetActivityRes.setEggCashDay(eggsMain.getEggCashDay());
        eggsGetActivityRes.setEggAddress(eggsMain.getEggAddress());
        eggsGetActivityRes.setEggWinningTxt(eggsMain.getEggWinningTxt());
        eggsGetActivityRes.setEggCashWay(eggsMain.getEggCashWay());
        eggsGetActivityRes.setEggWinningNotice(eggsMain.getEggWinningNotice());

        //TODO   奖项设置
        EntityWrapper<EggsDetail> entityWrapper = new EntityWrapper();
        entityWrapper.eq("egg_id",eggsGetActivityReq.getId());
        List<EggsDetail> eggsDetailList = eggsDetailService.selectList(entityWrapper);
        List<EggsPrizeSetReq> list = new ArrayList<>();
        for(EggsDetail EggsDetail:eggsDetailList){

            EggsPrizeSetReq eggsPrizeSetReq = new EggsPrizeSetReq();
            eggsPrizeSetReq.setEggPrizeType(EggsDetail.getEggPrizeType());
            eggsPrizeSetReq.setEggPrizeLimit(EggsDetail.getEggPrizeLimit());
            eggsPrizeSetReq.setEggPrizeName(EggsDetail.getEggPrizeName());
            eggsPrizeSetReq.setEggPrizeNums(EggsDetail.getEggPrizeNums());
            eggsPrizeSetReq.setEggPrizeChance(EggsDetail.getEggPrizeChance());
            eggsPrizeSetReq.setNickname(EggsDetail.getNickname());
            list.add(eggsPrizeSetReq);
        }
        eggsGetActivityRes.setPrizeSetList(list);

        return eggsGetActivityRes;
    }

    /**
     * 编辑砸金蛋活动基础设置
     * @param busUser
     * @param eggsModfiyBasicsReq
     */
    @Override
    public void modfiyBasicsEggs(BusUser busUser, EggsModfiyBasicsReq eggsModfiyBasicsReq) {

        EggsMain eggsMain = new EggsMain();
        eggsMain.setId(eggsModfiyBasicsReq.getId());
        eggsMain.setEggName(eggsModfiyBasicsReq.getEggName());
        eggsMain.setEggBeginTime(eggsModfiyBasicsReq.getEggBeginTime());
        eggsMain.setEggEndTime(eggsModfiyBasicsReq.getEggEndTime());
        eggsMain.setEggEggPartaker(eggsModfiyBasicsReq.getEggEggPartaker());

        if(eggsModfiyBasicsReq.getEggEggPartaker()==2){
            eggsMain.setEggPway(eggsModfiyBasicsReq.getEggPway());
            if(eggsModfiyBasicsReq.getEggPway()==2){
                eggsMain.setEggMan(eggsModfiyBasicsReq.getEggMan());
            }else if(eggsModfiyBasicsReq.getEggPway()==3){
                eggsMain.setEggKou(eggsModfiyBasicsReq.getEggKou());
            }else if(eggsModfiyBasicsReq.getEggPway()==4){
                eggsMain.setEggMan(eggsModfiyBasicsReq.getEggMan());
                eggsMain.setEggKou(eggsModfiyBasicsReq.getEggKou());
            }
        }
        eggsMain.setEggDescribe(eggsModfiyBasicsReq.getEggDescribe());
        eggsMain.setEggBeforeTxt(eggsModfiyBasicsReq.getEggBeforeTxt());

        eggsMainService.updateById(eggsMain);
    }

    /**
     * 编辑砸金蛋活动规则设置
     * @param busUser
     * @param eggsModfiyRuleReq
     */
    @Override
    public void modfiyRuleEggs(BusUser busUser, EggsModfiyRuleReq eggsModfiyRuleReq) {

        EggsMain eggsMain = new EggsMain();
        eggsMain.setId(eggsModfiyRuleReq.getId());
        eggsMain.setEggCountOfAll(eggsModfiyRuleReq.getEggsCountOfAll());
        eggsMain.setEggCountOfDay(eggsModfiyRuleReq.getEggsCountOfDay());

        eggsMainService.updateById(eggsMain);
    }

    /**
     * 编辑砸金蛋活动兑奖设置
     * @param busUser
     * @param eggsModfiyExpiryReq
     */
    @Override
    public void modfiyExpiryEggs(BusUser busUser, EggsModfiyExpiryReq eggsModfiyExpiryReq) {

        EggsMain eggsMain = new EggsMain();
        eggsMain.setId(eggsModfiyExpiryReq.getId());
        eggsMain.setEggCashDay(eggsModfiyExpiryReq.getEggCashDay());
        eggsMain.setEggAddress(eggsModfiyExpiryReq.getEggAddress());
        eggsMain.setEggCashWay(eggsModfiyExpiryReq.getEggCashWay());
        eggsMain.setEggWinningTxt(eggsModfiyExpiryReq.getEggWinningTxt());
        eggsMain.setEggWinningNotice(eggsModfiyExpiryReq.getEggWinningNotice());

        eggsMainService.updateById(eggsMain);
    }

    /**
     * 编辑砸金蛋奖项设置
     * @param busUser
     * @param eggsModfiyAwardsReq
     */
    @Override
    public void modfiyAwardsEggs(BusUser busUser, EggsModfiyAwardsReq eggsModfiyAwardsReq) {

        if(eggsModfiyAwardsReq.getPrizeSetList().size()>0){        //TODO  奖品设置
            // TODO  清空奖品设置
            EntityWrapper<EggsDetail> entityWrapper = new EntityWrapper();
            entityWrapper.eq("egg_id",eggsModfiyAwardsReq.getId());
            eggsDetailService.delete(entityWrapper);

            //TODO   奖项设置
            for(EggsPrizeSetReq eggsPrizeSetReq:eggsModfiyAwardsReq.getPrizeSetList()){

                EggsDetail eggsDetail = new EggsDetail();
                eggsDetail.setEggId(eggsModfiyAwardsReq.getId());
                eggsDetail.setEggPrizeType(eggsPrizeSetReq.getEggPrizeType());
                eggsDetail.setEggPrizeLimit(eggsPrizeSetReq.getEggPrizeLimit());
                eggsDetail.setEggPrizeName(eggsPrizeSetReq.getEggPrizeName());
                eggsDetail.setEggPrizeNums(eggsPrizeSetReq.getEggPrizeNums());
                eggsDetail.setEggPrizeChance(eggsPrizeSetReq.getEggPrizeChance());
                eggsDetail.setNickname(eggsPrizeSetReq.getNickname());

                eggsDetailService.insert(eggsDetail);
            }
        }
    }

    /**
     * 批量删除砸金蛋活动
     * @param busUser
     * @param eggsDelReq
     */
    @Override
    public void delEggs(BusUser busUser, EggsDelReq eggsDelReq) {

        //TODO  批量删除活动
        eggsMainService.deleteBatchIds(eggsDelReq.getId());


        for(Integer eggsId:eggsDelReq.getId()){
            //TODO  批量删除奖品设置
            EntityWrapper<EggsDetail> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("egg_id",eggsId);
            eggsDetailService.delete(entityWrapper);

            //TODO  批量删除中奖记录
            EntityWrapper<EggsWinning> entityWrapper2 = new EntityWrapper<>();
            entityWrapper2.eq("win_act_id",eggsId);
            eggsWinningService.delete(entityWrapper2);
        }
    }

    /**
     * 分页获取圣诞大礼包中奖记录列表
     * @param busUser
     * @param eggsGetWinningReq
     * @return
     */
    @Override
    public ResponseDTO<List<EggsGetWinningRes>> getWinningList(BusUser busUser, EggsGetWinningReq eggsGetWinningReq) {

        Page<EggsGetWinningRes> page = new Page<>(eggsGetWinningReq.getCurrent(),eggsGetWinningReq.getSize());
        List<EggsGetWinningRes> eggsGetWinningResList = eggsWinningDAO.queryRecodList(page,eggsGetWinningReq);

        //TODO  获取用户名
        if(eggsGetWinningResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < eggsGetWinningResList.size(); i++) {
                ids.append(eggsGetWinningResList.get(i).getMemberId());
                if (eggsGetWinningResList.size() > 1 && i < eggsGetWinningResList.size() - 1) {
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
            for (int i = 0; i < eggsGetWinningResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == eggsGetWinningResList.get(i).getMemberId().intValue()) {
                        eggsGetWinningResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(eggsGetWinningResList.get(i).getMemberName())) {
                    eggsGetWinningResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取中奖记录列表成功",eggsGetWinningResList,pageDTO);
    }

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param eggsEditApplyReq
     * @return
     */
    @Override
    public ResponseDTO editEggsApply(BusUser busUser, EggsEditApplyReq eggsEditApplyReq) {

        EggsWinning eggsWinning = eggsWinningService.selectById(eggsEditApplyReq.getId());
        if(CommonUtil.isNotEmpty(eggsWinning)){
            EggsMain eggsMain = eggsMainService.selectById(eggsWinning.getWinActId());
            EggsDetail eggsDetail = eggsDetailService.selectById(eggsWinning.getWinPrizeId());
            if(eggsDetail.getEggPrizeType() != 4){    //非兑奖
                if (DateTimeKit.laterThanNow(eggsMain.getEggBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new EggsException(ResponseEnums.EGGS_HAS1);
                } else if (!DateTimeKit.laterThanNow(eggsMain.getEggEndTime())) {
                    //""已过兑奖时间！";
                    throw  new EggsException(ResponseEnums.EGGS_HAS2);
                }
            }
            if (eggsWinning.getWinStatus() == 3) {
                // 更改记录状态
                eggsWinning.setWinStatus(2);
                eggsWinning.setWinCreateTime(new Date());
                eggsWinningService.updateById(eggsWinning);
            } else if (eggsWinning.getWinStatus() == 2){//已兑奖
                throw  new EggsException(ResponseEnums.EGGS_HAS3);
            }else{//还未提交
                throw  new EggsException(ResponseEnums.EGGS_HAS4);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }

    /**
     * 导出中奖记录
     * @param params
     * @param busUser
     * @return
     */
    @Override
    public Map<String, Object> exportTree(Map<String, Object> params, BusUser busUser) {

        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            EggsMain eggsMain = eggsMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = eggsWinningDAO.findExports(params);
            String title = "活动名称：" + eggsMain.getEggName() + "，开始时间：" + sdf.format(eggsMain.getEggBeginTime()) + "结束时间："
                    + sdf.format(eggsMain.getEggEndTime());
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("win_memberId"));
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
                        if (memberResList.get(j).getId().intValue() == CommonUtil.toInteger(list.get(i).get("win_memberId")).intValue()) {
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
            msg.put("fileName", eggsMain.getEggName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "砸金蛋活动中奖记录导出excel失败！");
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
            createCell(wb, rowData, 1, delWithColumn(map.get("egg_prize_name")), font1);
            String turPriType = delWithColumn(map.get("egg_prize_type"));
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
            createCell(wb, rowData, 2, priTypeName + "/" + delWithColumn(map.get("egg_prize_limit")) + priTypeUnit, font1);

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
            } else if ("2".equals(delWithColumn(map.get("status")).toString())) {
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
     * 批量删除砸金蛋中奖记录
     * @param busUser
     * @param eggsDelWinningReq
     */
    @Override
    public void delEggsWinning(BusUser busUser, EggsDelWinningReq eggsDelWinningReq) {

        eggsWinningService.deleteBatchIds(eggsDelWinningReq.getId());
    }

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<List<EggsPrizeTypeListRes>> getEggsPrizeType(BusUser busUser) {

        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EggsPrizeTypeListRes> eggsPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                EggsPrizeTypeListRes eggsPrizeTypeListRes = new EggsPrizeTypeListRes();
                eggsPrizeTypeListRes.setName(dictApiRes.getItemValue());
                eggsPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                eggsPrizeTypeListResList.add(eggsPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",eggsPrizeTypeListResList);
    }
}