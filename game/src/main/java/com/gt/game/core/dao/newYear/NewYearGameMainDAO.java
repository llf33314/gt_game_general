package com.gt.game.core.dao.newYear;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.newYear.res.NewYearListRes;
import com.gt.game.core.entity.newYear.NewYearGameMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 元旦游戏主表 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface NewYearGameMainDAO extends BaseMapper<NewYearGameMain> {

    Map<String,Object> getCount(Map<String, Object> params);

    List<NewYearListRes> getNewYearList(Page<NewYearListRes> page, Map<String, Object> map);
}