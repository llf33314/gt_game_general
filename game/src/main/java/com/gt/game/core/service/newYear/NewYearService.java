package com.gt.game.core.service.newYear;

import com.baomidou.mybatisplus.service.IService;
import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.newYear.req.*;
import com.gt.game.core.bean.newYear.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.newYear.NewYearGameMain;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface NewYearService{

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<NewYearCountRes> getNewYearCount(BusUser busUser);

    ResponseDTO<List<NewYearListRes>> getNewYearList(BusUser busUser, NewYearListPageReq newYearListPageReq);

    ResponseDTO<List<NewYearApplyListRes>> getNewYearApplyList(BusUser busUser, NewYearApplyListPageReq newYearApplyListPageReq);

    ResponseDTO editNewYearApply(BusUser busUser, NewYearApplyIdReq newYearApplyIdReq);

    ResponseDTO<NewYearRes> getNewYear(BusUser busUser, Integer id);

    ResponseDTO removeNewYear(BusUser busUser, NewYearIdReq newYearIdReq);

    ResponseDTO saveNewYear(BusUser busUser, NewYearSaveReq newYearSaveReq);

    ResponseDTO<List<NewYearAuthorityListRes>> getNewYearAuthorityList(BusUser busUser, NewYearAuthorityListPageReq newYearAuthorityListPageReq);

    ResponseDTO<List<NewYearPrizeTypeListRes>> getNewYearPrizeType(BusUser busUser);

    ResponseDTO removeNewYearAuthority(BusUser busUser, NewYearAuthorityIdsReq newYearAuthorityIdsReq);

    Map<String,Object> exports(Map<String, Object> params);
}
