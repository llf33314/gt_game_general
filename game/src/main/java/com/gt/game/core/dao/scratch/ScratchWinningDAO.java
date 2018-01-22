package com.gt.game.core.dao.scratch;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.scratch.req.ScratchGetWinningReq;
import com.gt.game.core.bean.scratch.res.ScratchGetWinningRes;
import com.gt.game.core.entity.scratch.ScratchWinning;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-19
 */
public interface ScratchWinningDAO extends BaseMapper<ScratchWinning> {

    List<ScratchGetWinningRes> queryRecodList(Page<ScratchGetWinningRes> page, ScratchGetWinningReq scratchGetWinningReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}