package com.gt.game.core.service.demolition;


import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.demolition.req.DemolitionListPageReq;
import com.gt.game.core.bean.demolition.res.DemolitionListRes;
import com.gt.game.core.bean.demolition.res.DemolitionRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;

import java.util.List;

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
}
