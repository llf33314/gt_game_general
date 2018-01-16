package com.gt.game.core.service.luck.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gt.api.bean.session.BusUser;
import com.gt.game.common.config.ApplyProperties;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.luck.res.LuckCountRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.luck.LuckMainDAO;
import com.gt.game.core.dao.luck.LuckWinningDAO;
import com.gt.game.core.entity.luck.LuckMain;
import com.gt.game.core.entity.luck.LuckWinning;
import com.gt.game.core.service.luck.LuckDetailService;
import com.gt.game.core.service.luck.LuckMainService;
import com.gt.game.core.service.luck.LuckService;
import com.gt.game.core.service.luck.LuckWinningService;
import com.gt.game.core.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 好运翻翻看 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Service
public class LuckServiceImpl implements LuckService {

    @Autowired
    LuckDetailService luckDetailService;

    @Autowired
    LuckMainService luckMainService;

    @Autowired
    LuckWinningService luckWinningService;

    @Autowired
    LuckMainDAO luckMainDAO;

    @Autowired
    LuckWinningDAO luckWinningDAO;

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
        String url = applyProperties.getMobileBaseUrl() + "LuckMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
        return new MobileUrlRes(url);
    }
    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<LuckCountRes> getLuckCount(BusUser busUser) {
        LuckCountRes LuckCountRes = new LuckCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = luckMainDAO.getCount(params);
        LuckCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        LuckCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        LuckCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        LuckCountRes.setCount1(LuckCountRes.getCount2()+LuckCountRes.getCount3()+LuckCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",LuckCountRes);
    }
}
