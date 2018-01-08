package com.gt.game.core.dao.loveArrow;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.loveArrow.req.LoveArrowApplyListPageReq;
import com.gt.game.core.bean.loveArrow.res.LoveArrowApplyListRes;
import com.gt.game.core.entity.loveArrow.LovearrowCashPrizeApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 一箭穿心兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-06
 */
public interface LovearrowCashPrizeApplyDAO extends BaseMapper<LovearrowCashPrizeApply> {

    List<LoveArrowApplyListRes> queryRecodList(Page<LoveArrowApplyListRes> page, LoveArrowApplyListPageReq loveArrowApplyListPageReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}