package com.gt.game.core.service.goldtree;

import com.baomidou.mybatisplus.service.IService;
import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.goldtree.req.*;
import com.gt.game.core.bean.goldtree.res.*;
import com.gt.game.core.bean.raiseflag.res.RaiseflagTemplateRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.goldtree.GoldtreeMain;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 摇钱树 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
public interface GoldtreeService{

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<GoldtreeCountRes> getGoldtreeCount(BusUser busUser);

    ResponseDTO<List<GoldtreeListRes>> getGoldtreeList(BusUser busUser, GoldtreeListPageReq goldtreeListPageReq);

    ResponseDTO<List<GoldtreeApplyListRes>> getGoldtreeApplyList(BusUser busUser, GoldtreeApplyListPageReq goldtreeApplyListPageReq);

    ResponseDTO editGoldtreeApply(BusUser busUser, GoldtreeApplyIdReq goldtreeApplyIdReq);

    ResponseDTO<GoldtreeRes> getGoldtree(BusUser busUser, Integer id);

    ResponseDTO removeGoldtree(BusUser busUser, GoldtreeIdReq goldtreeIdReq);

    ResponseDTO saveGoldtree(BusUser busUser, GoldtreeSaveReq goldtreeSaveReq);

    ResponseDTO<List<GoldtreeAuthorityListRes>> getGoldtreeAuthorityList(BusUser busUser, GoldtreeAuthorityListPageReq goldtreeAuthorityListPageReq);

    ResponseDTO<List<GoldtreePrizeTypeListRes>> getGoldtreePrizeType(BusUser busUser);

    ResponseDTO removeGoldtreeAuthority(BusUser busUser, GoldtreeAuthorityIdsReq goldtreeAuthorityIdsReq);

    Map<String,Object> exports(Map<String, Object> params);

    ResponseDTO<List<RaiseflagTemplateRes>> getTemplateList(BusUser busUser);
}
