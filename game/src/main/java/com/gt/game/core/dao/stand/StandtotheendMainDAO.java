package com.gt.game.core.dao.stand;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.stand.res.StandListRes;
import com.gt.game.core.entity.stand.StandtotheendMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 商家活动表 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface StandtotheendMainDAO extends BaseMapper<StandtotheendMain> {

    Map<String,Object> getCount(Map<String, Object> params);

    List<StandListRes> getStandList(Page<StandListRes> page, Map<String, Object> map);
}