package com.gt.game.core.dao.goldtree;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.goldtree.res.GoldtreeListRes;
import com.gt.game.core.entity.goldtree.GoldtreeMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 摇钱树设置表 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
public interface GoldtreeMainDAO extends BaseMapper<GoldtreeMain> {

    Map<String,Object> getCount(Map<String, Object> params);

    List<GoldtreeListRes> getGoldtreeList(Page<GoldtreeListRes> page, Map<String, Object> map);
}