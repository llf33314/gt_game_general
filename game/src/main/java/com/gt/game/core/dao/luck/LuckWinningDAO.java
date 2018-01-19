package com.gt.game.core.dao.luck;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.luck.req.LuckWinningPageReq;
import com.gt.game.core.bean.luck.res.LuckWinningListRes;
import com.gt.game.core.entity.luck.LuckWinning;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 好运翻翻看中奖信息 Mapper 接口
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
public interface LuckWinningDAO extends BaseMapper<LuckWinning> {

    List<LuckWinningListRes> queryRecodList(Page<LuckWinningListRes> page, LuckWinningPageReq luckWinningPageReq);

    List<Map<String,Object>> findExports(Map<String, Object> params);
}