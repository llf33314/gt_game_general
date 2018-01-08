package com.gt.game.core.dao.loveArrow;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.loveArrow.res.LoveArrowListRes;
import com.gt.game.core.entity.loveArrow.LovearrowMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 一箭穿心主表 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-06
 */
public interface LovearrowMainDAO extends BaseMapper<LovearrowMain> {

    Map<String,Object> getCount(Map<String, Object> params);

    List<LoveArrowListRes> getLoveArrowList(Page<LoveArrowListRes> page, Map<String, Object> map);
}