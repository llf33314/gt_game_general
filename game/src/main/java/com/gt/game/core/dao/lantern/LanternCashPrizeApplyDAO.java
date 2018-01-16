package com.gt.game.core.dao.lantern;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.demolition.res.DemolitionApplyListRes;
import com.gt.game.core.bean.lantern.req.LanternGetWinningReq;
import com.gt.game.core.bean.lantern.res.LanternGetWinningRes;
import com.gt.game.core.entity.lantern.LanternCashPrizeApply;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 元宵点灯兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface LanternCashPrizeApplyDAO extends BaseMapper<LanternCashPrizeApply> {

    List<LanternGetWinningRes> queryRecodList(Page<LanternGetWinningRes> page, LanternGetWinningReq lanternGetWinningReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}