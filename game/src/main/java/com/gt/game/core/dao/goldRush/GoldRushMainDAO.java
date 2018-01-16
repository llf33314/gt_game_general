package com.gt.game.core.dao.goldRush;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.goldRush.res.GoldRushListRes;
import com.gt.game.core.entity.goldRush.GoldRushMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 抢元宝主表 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-09
 */
public interface GoldRushMainDAO extends BaseMapper<GoldRushMain> {

    Map<String,Object> getCount(Map<String, Object> params);

    List<GoldRushListRes> getGoldRushList(Page<GoldRushListRes> page, Map<String, Object> map);
}