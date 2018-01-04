package com.gt.game.core.dao.newYear;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.newYear.req.NewYearApplyListPageReq;
import com.gt.game.core.bean.newYear.res.NewYearApplyListRes;
import com.gt.game.core.entity.newYear.NewYearGameCashPrizeApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 元旦游戏兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface NewYearGameCashPrizeApplyDAO extends BaseMapper<NewYearGameCashPrizeApply> {

    List<NewYearApplyListRes> queryRecodList(Page<NewYearApplyListRes> page, NewYearApplyListPageReq newYearApplyListPageReq);
}