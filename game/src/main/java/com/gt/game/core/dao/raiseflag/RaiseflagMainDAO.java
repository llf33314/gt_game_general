package com.gt.game.core.dao.raiseflag;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.raiseflag.res.RaiseflagListRes;
import com.gt.game.core.entity.raiseflag.RaiseflagMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 升国旗主表 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
public interface RaiseflagMainDAO extends BaseMapper<RaiseflagMain> {

    Map<String,Object> getCount(Map<String, Object> params);

    List<RaiseflagListRes> getRaiseflagList(Page<RaiseflagListRes> page, Map<String, Object> map);
}