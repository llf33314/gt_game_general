package com.gt.game.core.dao.goldRush;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.goldRush.req.GoldRushApplyListPageReq;
import com.gt.game.core.bean.goldRush.res.GoldRushApplyListRes;
import com.gt.game.core.entity.goldRush.GoldRushCashPrizeApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 抢元宝兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-09
 */
public interface GoldRushCashPrizeApplyDAO extends BaseMapper<GoldRushCashPrizeApply> {

    List<GoldRushApplyListRes> queryRecodList(Page<GoldRushApplyListRes> page, GoldRushApplyListPageReq goldRushApplyListPageReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}