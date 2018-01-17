package com.gt.game.core.service.shakeLuck;

import com.baomidou.mybatisplus.service.IService;
import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.shakeLuck.req.*;
import com.gt.game.core.bean.shakeLuck.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.shakeLuck.ShakeluckMain;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 摇手气 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
public interface ShakeluckService {

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<ShakeluckCountRes> getShakeluckCount(BusUser busUser);

    ResponseDTO<List<ShakeluckListRes>> getShakeluckList(BusUser busUser, ShakeluckListPageReq shakeluckListPageReq);

    ResponseDTO<List<ShakeluckApplyListRes>> getShakeluckApplyList(BusUser busUser, ShakeluckApplyListPageReq shakeluckApplyListPageReq);

    ResponseDTO editShakeluckApply(BusUser busUser, ShakeluckApplyIdReq shakeluckApplyIdReq);

    ResponseDTO<ShakeluckRes> getShakeluck(BusUser busUser, Integer id);

    ResponseDTO removeShakeluck(BusUser busUser, ShakeluckIdReq shakeluckIdReq);

    ResponseDTO saveShakeluck(BusUser busUser, ShakeluckSaveReq shakeluckSaveReq);

    ResponseDTO<List<ShakeluckAuthorityListRes>> getShakeluckAuthorityList(BusUser busUser, ShakeluckAuthorityListPageReq shakeluckAuthorityListPageReq);

    ResponseDTO<List<ShakeluckPrizeTypeListRes>> getShakeluckPrizeType(BusUser busUser);

    ResponseDTO removeShakeluckAuthority(BusUser busUser, ShakeluckAuthorityIdsReq shakeluckAuthorityIdsReq);

    Map<String,Object> exports(Map<String, Object> params);
}
