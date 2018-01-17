package com.gt.game.core.dao.ninelattice;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.ninelattice.req.NinelatticeGetWinningReq;
import com.gt.game.core.bean.ninelattice.res.NinelatticeGetWinningRes;
import com.gt.game.core.entity.ninelattice.NinelatticeCashPrizeApply;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 幸运九宫格兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-11
 */
public interface NinelatticeCashPrizeApplyDAO extends BaseMapper<NinelatticeCashPrizeApply> {

    List<NinelatticeGetWinningRes> queryRecodList(Page<NinelatticeGetWinningRes> page, NinelatticeGetWinningReq ninelatticeGetWinningReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}