package com.gt.game.core.service.goldRush;

import com.baomidou.mybatisplus.service.IService;
import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.goldRush.req.*;
import com.gt.game.core.bean.goldRush.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.goldRush.GoldRushMain;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 欢乐抢元宝 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-09
 */
public interface GoldRushService {

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<GoldRushCountRes> getGoldRushCount(BusUser busUser);

    ResponseDTO<List<GoldRushListRes>> getGoldRushList(BusUser busUser, GoldRushListPageReq goldRushListPageReq);

    ResponseDTO<List<GoldRushApplyListRes>> getGoldRushApplyList(BusUser busUser, GoldRushApplyListPageReq goldRushApplyListPageReq);

    ResponseDTO editGoldRushApply(BusUser busUser, GoldRushApplyIdReq goldRushApplyIdReq);

    ResponseDTO<GoldRushRes> getGoldRush(BusUser busUser, Integer id);

    ResponseDTO removeGoldRush(BusUser busUser, GoldRushIdReq goldRushIdReq);

    ResponseDTO saveGoldRush(BusUser busUser, GoldRushSaveReq goldRushSaveReq);

    ResponseDTO removeGoldRushAuthority(BusUser busUser, GoldRushAuthorityIdsReq goldRushAuthorityIdsReq);

    ResponseDTO<List<GoldRushPrizeTypeListRes>> getGoldRushPrizeType(BusUser busUser);

    ResponseDTO<List<GoldRushAuthorityListRes>> getGoldRushAuthorityList(BusUser busUser, GoldRushAuthorityListPageReq goldRushAuthorityListPageReq);

    Map<String,Object> exports(Map<String, Object> params);
}
