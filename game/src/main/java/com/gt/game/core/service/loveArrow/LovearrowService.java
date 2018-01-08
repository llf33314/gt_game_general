package com.gt.game.core.service.loveArrow;

import com.baomidou.mybatisplus.service.IService;
import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.loveArrow.req.*;
import com.gt.game.core.bean.loveArrow.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.loveArrow.LovearrowMain;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 一箭穿心 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-06
 */
public interface LovearrowService{

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<LoveArrowCountRes> getLoveArrowCount(BusUser busUser);

    ResponseDTO<List<LoveArrowListRes>> getLoveArrowList(BusUser busUser, LoveArrowListPageReq loveArrowListPageReq);

    ResponseDTO<List<LoveArrowApplyListRes>> getLoveArrowApplyList(BusUser busUser, LoveArrowApplyListPageReq loveArrowApplyListPageReq);

    ResponseDTO editLoveArrowApply(BusUser busUser, LoveArrowApplyIdReq loveArrowApplyIdReq);

    ResponseDTO<LoveArrowRes> getLoveArrow(BusUser busUser, Integer id);

    ResponseDTO removeLoveArrow(BusUser busUser, LoveArrowIdReq loveArrowIdReq);

    ResponseDTO saveLoveArrow(BusUser busUser, LoveArrowSaveReq loveArrowSaveReq);

    ResponseDTO<List<LoveArrowAuthorityListRes>> getLoveArrowAuthorityList(BusUser busUser, LoveArrowAuthorityListPageReq loveArrowAuthorityListPageReq);

    ResponseDTO<List<LoveArrowPrizeTypeListRes>> getLoveArrowPrizeType(BusUser busUser);

    ResponseDTO removeLoveArrowAuthority(BusUser busUser, LoveArrowAuthorityIdsReq loveArrowAuthorityIdsReq);

    Map<String,Object> exports(Map<String, Object> params);
}
