package com.gt.game.core.service.demolition;


import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.demolition.req.*;
import com.gt.game.core.bean.demolition.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 拆礼盒主表 服务类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
public interface DemolitionService {

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<List<DemolitionListRes>> getDemolitionList(BusUser busUser, DemolitionListPageReq demolitionListPageReq);

    ResponseDTO<DemolitionRes> getDemolition(BusUser busUser, Integer id);

    ResponseDTO<List<DemolitionApplyListRes>> getDemolitionApplyList(BusUser busUser, DemolitionApplyListPageReq demolitionApplyListPageReq);

    ResponseDTO editDemolitionApply(BusUser busUser, DemolitionApplyIdReq demolitionApplyIdReq);

    Map<String,Object> exports(Map<String, Object> params);

    ResponseDTO saveDemolition(BusUser busUser, DemolitionSaveReq demolitionSaveReq);

    ResponseDTO removeDemolition(BusUser busUser, DemolitionIdReq demolitionIdReq);

    ResponseDTO<List<DemolitionAuthorityListRes>> getDemolitionAuthorityList(BusUser busUser, DemolitionAuthorityListPageReq demolitionAuthorityListPageReq);

    ResponseDTO removeDemolitionAuthority(BusUser busUser, DemolitionAuthorityIdsReq demolitionAuthorityIdsReq);

    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<DemolitionCountRes> getDemolitionCount(BusUser busUser);

    ResponseDTO<List<DemolitionPrizeTypeListRes>> getDemolitionPrizeType(BusUser busUser);
}
