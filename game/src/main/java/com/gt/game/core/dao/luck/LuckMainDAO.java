package com.gt.game.core.dao.luck;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.luck.res.LuckListRes;
import com.gt.game.core.entity.luck.LuckMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
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

    List<LuckListRes> getLuckList(Page<LuckListRes> page, Map<String, Object> map);
}