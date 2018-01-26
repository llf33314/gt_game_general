package com.gt.game.core.service.common;

import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.common.req.MemberListPageReq;
import com.gt.game.core.bean.common.res.MemberListPageRes;
import com.gt.game.core.bean.common.res.PrizeTypeListRes;

import java.util.List;

/**
 * <p>
 * 奖品 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
public interface PrizeTypeService {

    ResponseDTO<List<PrizeTypeListRes>> getPrizeTypeOne(BusUser busUser);

    ResponseDTO<List<PrizeTypeListRes>> getPrizeType(BusUser busUser);
}
