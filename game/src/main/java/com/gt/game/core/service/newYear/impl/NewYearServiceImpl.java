package com.gt.game.core.service.newYear.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.member.MemberServer;
import com.gt.game.common.config.ApplyProperties;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.newYear.req.NewYearApplyIdReq;
import com.gt.game.core.bean.newYear.req.NewYearApplyListPageReq;
import com.gt.game.core.bean.newYear.req.NewYearListPageReq;
import com.gt.game.core.bean.newYear.res.NewYearApplyListRes;
import com.gt.game.core.bean.newYear.res.NewYearCountRes;
import com.gt.game.core.bean.newYear.res.NewYearListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.newYear.NewYearGameCashPrizeApplyDAO;
import com.gt.game.core.dao.newYear.NewYearGameMainDAO;
import com.gt.game.core.dao.newYear.NewYearGamePrizeDAO;
import com.gt.game.core.entity.newYear.NewYearGameCashPrizeApply;
import com.gt.game.core.entity.newYear.NewYearGameMain;
import com.gt.game.core.entity.newYear.NewYearGamePrize;
import com.gt.game.core.exception.newYear.NewYearException;
import com.gt.game.core.service.newYear.NewYearGameCashPrizeApplyService;
import com.gt.game.core.service.newYear.NewYearGameMainService;
import com.gt.game.core.service.newYear.NewYearGamePrizeService;
import com.gt.game.core.service.newYear.NewYearService;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    NewYearGamePrizeService newYearGamePrizeService;

    @Autowired
    NewYearGameMainService newYearGameMainService;

    @Autowired
    NewYearGameCashPrizeApplyService newYearGameCashPrizeApplyService;

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
        String url = applyProperties.getMobileBaseUrl() + "NewYearGiftBoxMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
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
}
