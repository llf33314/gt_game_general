package com.gt.game.core.service.common;

import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.common.req.MemberListPageReq;
import com.gt.game.core.bean.common.res.MemberListPageRes;

import java.util.List;

/**
 * <p>
 * 粉丝 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
public interface MemberService {

    ResponseDTO<List<MemberListPageRes>> getMemberList(BusUser busUser, MemberListPageReq loveArrowListPageReq);
}
