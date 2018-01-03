package com.gt.game.core.service.seagold.impl;

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
import com.gt.game.core.bean.demolition.res.DemolitionListRes;
import com.gt.game.core.bean.seagold.req.SeagoldApplyListPageReq;
import com.gt.game.core.bean.seagold.req.SeagoldListPageReq;
import com.gt.game.core.bean.seagold.res.SeagoldApplyListRes;
import com.gt.game.core.bean.seagold.res.SeagoldListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.seagold.SeagoldCashPrizeApplyDAO;
import com.gt.game.core.dao.seagold.SeagoldMainDAO;
import com.gt.game.core.entity.seagold.SeagoldCashPrizeApply;
import com.gt.game.core.entity.seagold.SeagoldMain;
import com.gt.game.core.service.seagold.SeagoldCashPrizeApplyService;
import com.gt.game.core.service.seagold.SeagoldMainService;
import com.gt.game.core.service.seagold.SeagoldService;
import com.gt.game.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 大海捞金_主表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class SeagoldServiceImpl implements SeagoldService {

    @Autowired
    SeagoldMainService seagoldMainService;

    @Autowired
    SeagoldCashPrizeApplyService seagoldCashPrizeApplyService;

    @Autowired
    SeagoldCashPrizeApplyDAO seagoldCashPrizeApplyDAO;

    @Autowired
    SeagoldMainDAO seagoldMainDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "seagoldMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
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
        String url = applyProperties.getMobileBaseUrl() + "seagoldMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }
    /**
     * 分页获取活动
     *
     * @param seagoldListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<SeagoldListRes>> getSeagoldList(BusUser busUser, SeagoldListPageReq seagoldListPageReq) {
        Page<SeagoldListRes> page = new Page<>(seagoldListPageReq.getCurrent(),seagoldListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",seagoldListPageReq.getStatus());
        map.put("name",seagoldListPageReq.getName());
        map.put("busId",busUser.getId());
        List<SeagoldListRes> seagoldListResList = seagoldMainDAO.getSeagoldList(page,map);
        for (SeagoldListRes seagoldListRes : seagoldListResList){
            if(seagoldListRes.getActivityBeginTime().getTime() > date.getTime()){
                seagoldListRes.setIsEdit(1);
                seagoldListRes.setStatus(0);
            }else if(seagoldListRes.getActivityEndTime().getTime() < date.getTime()){
                seagoldListRes.setIsEdit(0);
                seagoldListRes.setStatus(2);
            }else {
                seagoldListRes.setIsEdit(0);
                seagoldListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",seagoldListResList,pageDTO);
    }
    /**
     * 分页获取中奖记录
     *
     * @param seagoldApplyListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<SeagoldApplyListRes>> getSeagoldApplyList(BusUser busUser, SeagoldApplyListPageReq seagoldApplyListPageReq) {
        Page<SeagoldApplyListRes> page = new Page<>(seagoldApplyListPageReq.getCurrent(),seagoldApplyListPageReq.getSize());
        List<SeagoldApplyListRes> seagoldApplyListResList = seagoldCashPrizeApplyDAO.queryRecodList(page,seagoldApplyListPageReq);
        if(seagoldApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < seagoldApplyListResList.size(); i++) {
                ids.append(seagoldApplyListResList.get(i).getMemberId());
                if (seagoldApplyListResList.size() > 1 && i < seagoldApplyListResList.size() - 1) {
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
            for (int i = 0; i < seagoldApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == seagoldApplyListResList.get(i).getMemberId().intValue()) {
                        seagoldApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(seagoldApplyListResList.get(i).getMemberName())) {
                    seagoldApplyListResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",seagoldApplyListResList,pageDTO);
    }
}
