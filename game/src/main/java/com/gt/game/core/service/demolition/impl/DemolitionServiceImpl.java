package com.gt.game.core.service.demolition.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.game.common.config.ApplyProperties;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.demolition.req.DemolitionListPageReq;
import com.gt.game.core.bean.demolition.res.DemolitionListRes;
import com.gt.game.core.bean.demolition.res.DemolitionRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.demolition.DemolitiongiftboxMainDAO;
import com.gt.game.core.entity.demolition.DemolitiongiftboxMain;
import com.gt.game.core.service.demolition.DemolitionService;
import com.gt.game.core.service.demolition.DemolitiongiftboxMainService;
import com.gt.game.core.util.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 拆礼盒主表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class DemolitionServiceImpl implements DemolitionService {

    @Autowired
    DemolitiongiftboxMainService demolitiongiftboxMainService;

    @Autowired
    DemolitiongiftboxMainDAO demolitiongiftboxMainDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "demolitionGiftBoxMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
        return new MobileUrlRes(url);
    }
    /**
     * 分页获取活动列表
     *
     * @param demolitionListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<DemolitionListRes>> getDemolitionList(BusUser busUser, DemolitionListPageReq demolitionListPageReq) {
        Page<DemolitionListRes> page = new Page<>(demolitionListPageReq.getCurrent(),demolitionListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",demolitionListPageReq.getStatus());
        map.put("name",demolitionListPageReq.getName());
        map.put("busId",busUser.getId());
        List<DemolitionListRes> demolitionListResList = demolitiongiftboxMainDAO.getDemolitionList(page,map);
        for (DemolitionListRes demolitionListRes : demolitionListResList){
            if(demolitionListRes.getActivityBeginTime().getTime() > date.getTime()){
                demolitionListRes.setStatus(0);
            }else if(demolitionListRes.getActivityEndTime().getTime() < date.getTime()){
                demolitionListRes.setStatus(2);
            }else {
                demolitionListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",demolitionListResList,pageDTO);
    }
    /**
     * 获取活动
     * @param id
     * @return
     */
    @Override
    public ResponseDTO<DemolitionRes> getDemolition(BusUser busUser, Integer id) {
        DemolitiongiftboxMain demolitiongiftboxMain = demolitiongiftboxMainService.selectById(id);
        if(CommonUtil.isNotEmpty(demolitiongiftboxMain)){
            DemolitionRes demolitionRes = new DemolitionRes();
            BeanUtils.copyProperties(demolitiongiftboxMain,demolitionRes);
            return ResponseDTO.createBySuccess("获取成功",demolitionRes);
        }
        return ResponseDTO.createBySuccess("获取成功",null);
    }
}
