package com.gt.game.core.dao.seagold;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.seagold.req.SeagoldApplyListPageReq;
import com.gt.game.core.bean.seagold.res.SeagoldApplyListRes;
import com.gt.game.core.entity.seagold.SeagoldCashPrizeApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 大海捞金_兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
public interface SeagoldCashPrizeApplyDAO extends BaseMapper<SeagoldCashPrizeApply> {

    List<SeagoldApplyListRes> queryRecodList(Page<SeagoldApplyListRes> page, SeagoldApplyListPageReq seagoldApplyListPageReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}