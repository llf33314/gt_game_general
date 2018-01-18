package com.gt.game.core.dao.eggs;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.eggs.req.EggsGetWinningReq;
import com.gt.game.core.bean.eggs.res.EggsGetWinningRes;
import com.gt.game.core.entity.eggs.EggsWinning;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-17
 */
public interface EggsWinningDAO extends BaseMapper<EggsWinning> {

    List<EggsGetWinningRes> queryRecodList(Page<EggsGetWinningRes> page, EggsGetWinningReq eggsGetWinningReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}