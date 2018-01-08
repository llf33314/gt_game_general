package com.gt.game.core.service.raiseflag;

import com.baomidou.mybatisplus.service.IService;
import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.raiseflag.req.*;
import com.gt.game.core.bean.raiseflag.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.raiseflag.RaiseflagMain;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 升国旗 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
public interface RaiseflagService{

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<RaiseflagCountRes> getRaiseflagCount(BusUser busUser);

    ResponseDTO<List<RaiseflagListRes>> getRaiseflagList(BusUser busUser, RaiseflagListPageReq raiseflagListPageReq);

    ResponseDTO<List<RaiseflagApplyListRes>> getRaiseflagApplyList(BusUser busUser, RaiseflagApplyListPageReq raiseflagApplyListPageReq);

    ResponseDTO editRaiseflagApply(BusUser busUser, RaiseflagApplyIdReq raiseflagApplyIdReq);

    ResponseDTO<RaiseflagRes> getRaiseflag(BusUser busUser, Integer id);

    ResponseDTO removeRaiseflag(BusUser busUser, RaiseflagIdReq raiseflagIdReq);

    ResponseDTO saveRaiseflag(BusUser busUser, RaiseflagSaveReq raiseflagSaveReq);

    ResponseDTO<List<RaiseflagAuthorityListRes>> getRaiseflagAuthorityList(BusUser busUser, RaiseflagAuthorityListPageReq raiseflagAuthorityListPageReq);

    ResponseDTO<List<RaiseflagPrizeTypeListRes>> getRaiseflagPrizeType(BusUser busUser);

    ResponseDTO removeRaiseflagAuthority(BusUser busUser, RaiseflagAuthorityIdsReq raiseflagAuthorityIdsReq);

    Map<String,Object> exports(Map<String, Object> params);
}
