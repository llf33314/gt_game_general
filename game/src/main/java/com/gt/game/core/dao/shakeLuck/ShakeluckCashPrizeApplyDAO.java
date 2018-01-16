package com.gt.game.core.dao.shakeLuck;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.shakeLuck.req.ShakeluckApplyListPageReq;
import com.gt.game.core.bean.shakeLuck.res.ShakeluckApplyListRes;
import com.gt.game.core.entity.shakeLuck.ShakeluckCashPrizeApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 摇手气兑奖记录申请 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
public interface ShakeluckCashPrizeApplyDAO extends BaseMapper<ShakeluckCashPrizeApply> {

    List<ShakeluckApplyListRes> queryRecodList(Page<ShakeluckApplyListRes> page, ShakeluckApplyListPageReq shakeluckApplyListPageReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}