package com.gt.game.core.dao.goldtree;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.goldtree.req.GoldtreeApplyListPageReq;
import com.gt.game.core.bean.goldtree.res.GoldtreeApplyListRes;
import com.gt.game.core.entity.goldtree.GoldtreePrizeApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 摇钱树兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
public interface GoldtreePrizeApplyDAO extends BaseMapper<GoldtreePrizeApply> {

    List<GoldtreeApplyListRes> queryRecodList(Page<GoldtreeApplyListRes> page, GoldtreeApplyListPageReq goldtreeApplyListPageReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}