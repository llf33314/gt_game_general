package com.gt.game.core.dao.raiseflag;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.raiseflag.req.RaiseflagApplyListPageReq;
import com.gt.game.core.bean.raiseflag.res.RaiseflagApplyListRes;
import com.gt.game.core.entity.raiseflag.RaiseflagCashPrizeApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 升国旗兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
public interface RaiseflagCashPrizeApplyDAO extends BaseMapper<RaiseflagCashPrizeApply> {

    List<RaiseflagApplyListRes> queryRecodList(Page<RaiseflagApplyListRes> page, RaiseflagApplyListPageReq raiseflagApplyListPageReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}