package com.gt.game.core.dao.stand;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.stand.req.StandApplyListPageReq;
import com.gt.game.core.bean.stand.res.StandApplyListRes;
import com.gt.game.core.entity.stand.StandtotheendCashPrizeApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 兑奖申请表
 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface StandtotheendCashPrizeApplyDAO extends BaseMapper<StandtotheendCashPrizeApply> {

    List<StandApplyListRes> getStandApplyList(Page<StandApplyListRes> page, StandApplyListPageReq standApplyListPageReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}