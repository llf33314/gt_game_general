package com.gt.game.core.dao.seagold;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.seagold.res.SeagoldListRes;
import com.gt.game.core.entity.seagold.SeagoldMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 大海捞金_主表 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
public interface SeagoldMainDAO extends BaseMapper<SeagoldMain> {

    List<SeagoldListRes> getSeagoldList(Page<SeagoldListRes> page, Map<String, Object> map);

    Map<String,Object> getCount(Map<String, Object> params);
}