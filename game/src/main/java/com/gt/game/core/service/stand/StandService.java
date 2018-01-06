package com.gt.game.core.service.stand;


import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.stand.req.*;
import com.gt.game.core.bean.stand.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 一站到底 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface StandService{

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<StandCountRes> getStandCount(BusUser busUser);

    ResponseDTO<List<StandListRes>> getStandList(BusUser busUser, StandListPageReq standListPageReq);

    ResponseDTO<List<StandApplyListRes>> getStandApplyList(BusUser busUser, StandApplyListPageReq standApplyListPageReq);

    ResponseDTO editStandApply(BusUser busUser, StandApplyIdReq standApplyIdReq);

    ResponseDTO<StandRes> getStand(BusUser busUser, Integer id);

    ResponseDTO removeStand(BusUser busUser, StandIdReq standIdReq);

    ResponseDTO saveStand(BusUser busUser, StandSaveReq standSaveReq);

    ResponseDTO<List<StandAuthorityListRes>> getStandAuthorityList(BusUser busUser, StandAuthorityListPageReq standAuthorityListPageReq);

    ResponseDTO<List<StandPrizeTypeListRes>> getStandPrizeType(BusUser busUser);

    ResponseDTO removeStandAuthority(BusUser busUser, StandAuthorityIdsReq standAuthorityIdsReq);

    Map<String,Object> exports(Map<String, Object> params);

    ResponseDTO<List<StandJoinRecordListRes>> getStandJoinRecord(BusUser busUser, StandIdReq standIdReq);

    ResponseDTO<List<StandJoinDetailListRes>> getStandJoinDetail(BusUser busUser, StandRecordIdReq standRecordIdReq);

    ResponseDTO<List<StandQuesbankListRes>> getStandQuesbankList(BusUser busUser);

    ResponseDTO<StandQuesbankRes> getStandQuesbank(BusUser busUser, StandQuesbankIdReq standQuesbankIdReq);

    ResponseDTO<StandQuesbankSaveReq> saveStandQuesbank(BusUser busUser, StandQuesbankSaveReq standQuesbankSaveReq);

    ResponseDTO saveStandQuestion(BusUser busUser, StandQuestionSaveReq standQuestionSaveReq);

    ResponseDTO removeStandQuesbank(BusUser busUser, StandQuesbankIdReq standQuesbankIdReq);

    ResponseDTO removeStandQuestion(BusUser busUser, StandQuesbankIdReq standQuesbankIdReq);

    ResponseDTO uploadStandQuestion(BusUser busUser, MultipartFile file, Integer bankId);
}
