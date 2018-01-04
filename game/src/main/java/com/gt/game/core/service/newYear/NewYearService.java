package com.gt.game.core.service.newYear;

import com.baomidou.mybatisplus.service.IService;
import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.newYear.req.NewYearApplyIdReq;
import com.gt.game.core.bean.newYear.req.NewYearApplyListPageReq;
import com.gt.game.core.bean.newYear.req.NewYearListPageReq;
import com.gt.game.core.bean.newYear.res.NewYearApplyListRes;
import com.gt.game.core.bean.newYear.res.NewYearCountRes;
import com.gt.game.core.bean.newYear.res.NewYearListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.newYear.NewYearGameMain;

import java.util.List;

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
}
