package com.gt.game.core.dao.luck;

import com.gt.game.core.entity.luck.LuckMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
  * 好运翻翻看 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
public interface LuckMainDAO extends BaseMapper<LuckMain> {

    Map<String,Object> getCount(Map<String, Object> params);
}