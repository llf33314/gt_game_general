package com.gt.game.core.dao.demolition;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.demolition.res.DemolitionListRes;
import com.gt.game.core.entity.demolition.DemolitiongiftboxMain;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 拆礼盒主表 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
public interface DemolitiongiftboxMainDAO extends BaseMapper<DemolitiongiftboxMain> {

    List<DemolitionListRes> getDemolitionList(Page<DemolitionListRes> page, Map<String, Object> map);
}