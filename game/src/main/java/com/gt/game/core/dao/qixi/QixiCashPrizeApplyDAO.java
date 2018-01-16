package com.gt.game.core.dao.qixi;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.qixi.req.QixiApplyListPageReq;
import com.gt.game.core.bean.qixi.res.QixiApplyListRes;
import com.gt.game.core.entity.qixi.QixiCashPrizeApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 浪漫七夕兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
public interface QixiCashPrizeApplyDAO extends BaseMapper<QixiCashPrizeApply> {

    List<QixiApplyListRes> queryRecodList(Page<QixiApplyListRes> page, QixiApplyListPageReq qixiApplyListPageReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}