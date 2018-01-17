package com.gt.game.core.dao.tree;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.tree.req.TreeGetWinningReq;
import com.gt.game.core.bean.tree.res.TreeGetWinningRes;
import com.gt.game.core.entity.tree.TreeWinning;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 圣诞树大礼包中奖信息 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-15
 */
public interface TreeWinningDAO extends BaseMapper<TreeWinning> {

    List<TreeGetWinningRes> queryRecodList(Page<TreeGetWinningRes> page, TreeGetWinningReq treeGetWinningReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}