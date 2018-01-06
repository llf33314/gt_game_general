package com.gt.game.core.service.seagold;

import com.baomidou.mybatisplus.service.IService;
import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.seagold.req.*;
import com.gt.game.core.bean.seagold.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.seagold.SeagoldMain;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 大海捞金 服务类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
public interface SeagoldService{

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<List<SeagoldListRes>> getSeagoldList(BusUser busUser, SeagoldListPageReq seagoldListPageReq);

    ResponseDTO<List<SeagoldApplyListRes>> getSeagoldApplyList(BusUser busUser, SeagoldApplyListPageReq seagoldApplyListPageReq);

    ResponseDTO<SeagoldCountRes> getSeagoldCount(BusUser busUser);

    ResponseDTO editSeagoldApply(BusUser busUser, SeagoldApplyIdReq seagoldApplyIdReq);

    ResponseDTO<List<SeagoldPrizeTypeListRes>> getDemolitionPrizeType(BusUser busUser);

    ResponseDTO removeSeagoldAuthority(BusUser busUser, SeagoldAuthorityIdsReq seagoldAuthorityIdsReq);

    ResponseDTO<List<SeagoldAuthorityListRes>> getSeagoldAuthorityList(BusUser busUser, SeagoldAuthorityListPageReq seagoldAuthorityListPageReq);

    ResponseDTO saveSeagold(BusUser busUser, SeagoldSaveReq seagoldSaveReq);

    ResponseDTO removeSeagold(BusUser busUser, SeagoldIdReq seagoldIdReq);

    ResponseDTO<SeagoldRes> getSeagoldRes(BusUser busUser, Integer id);

    Map<String,Object> exports(Map<String, Object> params);
}
