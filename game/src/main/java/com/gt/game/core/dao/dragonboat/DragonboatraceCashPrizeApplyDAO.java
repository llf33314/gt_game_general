package com.gt.game.core.dao.dragonboat;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.dragonboat.req.DragonboatGetWinningReq;
import com.gt.game.core.bean.dragonboat.res.DragonboatGetWinningRes;
import com.gt.game.core.entity.dragonboat.DragonboatraceCashPrizeApply;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 端午赛龙舟_兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
public interface DragonboatraceCashPrizeApplyDAO extends BaseMapper<DragonboatraceCashPrizeApply> {

    List<DragonboatGetWinningRes> queryRecodList(Page<DragonboatGetWinningRes> page, DragonboatGetWinningReq dragonboatGetWinningReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}