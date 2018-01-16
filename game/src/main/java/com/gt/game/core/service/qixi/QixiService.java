package com.gt.game.core.service.qixi;

import com.baomidou.mybatisplus.service.IService;
import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.qixi.req.*;
import com.gt.game.core.bean.qixi.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.qixi.QixiMain;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 浪漫七夕 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
public interface QixiService{

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<QixiCountRes> getQixiCount(BusUser busUser);

    ResponseDTO<List<QixiListRes>> getQixiList(BusUser busUser, QixiListPageReq qixiListPageReq);

    ResponseDTO<List<QixiApplyListRes>> getQixiApplyList(BusUser busUser, QixiApplyListPageReq qixiApplyListPageReq);

    ResponseDTO editQixiApply(BusUser busUser, QixiApplyIdReq qixiApplyIdReq);

    ResponseDTO<QixiRes> getQixi(BusUser busUser, Integer id);

    ResponseDTO removeQixi(BusUser busUser, QixiIdReq qixiIdReq);

    ResponseDTO saveQixi(BusUser busUser, QixiSaveReq qixiSaveReq);

    ResponseDTO<List<QixiAuthorityListRes>> getQixiAuthorityList(BusUser busUser, QixiAuthorityListPageReq qixiAuthorityListPageReq);

    ResponseDTO<List<QixiPrizeTypeListRes>> getQixiPrizeType(BusUser busUser);

    ResponseDTO removeQixiAuthority(BusUser busUser, QixiAuthorityIdsReq qixiAuthorityIdsReq);

    Map<String,Object> exports(Map<String, Object> params);
}
