package com.gt.game.core.dao.shakeLuck;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.shakeLuck.res.ShakeluckListRes;
import com.gt.game.core.entity.shakeLuck.ShakeluckMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 摇手气主表 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
public interface ShakeluckMainDAO extends BaseMapper<ShakeluckMain> {

    Map<String,Object> getCount(Map<String, Object> params);

    List<ShakeluckListRes> getShakeluckList(Page<ShakeluckListRes> page, Map<String, Object> map);
}