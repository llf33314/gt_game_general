package com.gt.game.core.service.seagold;

import com.baomidou.mybatisplus.service.IService;
import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.seagold.req.SeagoldApplyListPageReq;
import com.gt.game.core.bean.seagold.req.SeagoldListPageReq;
import com.gt.game.core.bean.seagold.res.SeagoldApplyListRes;
import com.gt.game.core.bean.seagold.res.SeagoldListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.seagold.SeagoldMain;

import java.util.List;

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
}
