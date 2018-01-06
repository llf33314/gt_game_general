package com.gt.game.core.dao.demolition;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.demolition.req.DemolitionApplyListPageReq;
import com.gt.game.core.bean.demolition.res.DemolitionApplyListRes;
import com.gt.game.core.entity.demolition.DemolitiongiftboxCashPrizeApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 拆礼盒兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
public interface DemolitiongiftboxCashPrizeApplyDAO extends BaseMapper<DemolitiongiftboxCashPrizeApply> {

    List<DemolitionApplyListRes> queryRecodList(Page<DemolitionApplyListRes> page, DemolitionApplyListPageReq demolitionApplyListPageReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}