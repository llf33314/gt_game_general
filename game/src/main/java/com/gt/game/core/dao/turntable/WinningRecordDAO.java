package com.gt.game.core.dao.turntable;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.turntable.req.TurntableGetWinningReq;
import com.gt.game.core.bean.turntable.res.TurntableGetWinningRes;
import com.gt.game.core.entity.turntable.WinningRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 互动中奖记录 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-22
 */
public interface WinningRecordDAO extends BaseMapper<WinningRecord> {

    List<TurntableGetWinningRes> queryRecodList(Page<TurntableGetWinningRes> page, TurntableGetWinningReq turntableGetWinningReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}