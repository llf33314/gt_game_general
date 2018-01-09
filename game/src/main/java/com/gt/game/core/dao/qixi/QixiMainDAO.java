package com.gt.game.core.dao.qixi;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.qixi.res.QixiListRes;
import com.gt.game.core.entity.qixi.QixiMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 浪漫七夕主表 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
public interface QixiMainDAO extends BaseMapper<QixiMain> {

    Map<String,Object> getCount(Map<String, Object> params);

    List<QixiListRes> getQixiList(Page<QixiListRes> page, Map<String, Object> map);
}