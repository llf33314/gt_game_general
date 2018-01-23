package com.gt.game.core.dao.countmoney;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.countmoney.req.CountmoneyGetWinningReq;
import com.gt.game.core.bean.countmoney.res.CountmoneyGetWinningRes;
import com.gt.game.core.entity.countmoney.CountmoneyRecord;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-18
 */
public interface CountmoneyRecordDAO extends BaseMapper<CountmoneyRecord> {

    List<CountmoneyGetWinningRes> queryRecodList(Page<CountmoneyGetWinningRes> page, CountmoneyGetWinningReq countmoneyGetWinningReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}