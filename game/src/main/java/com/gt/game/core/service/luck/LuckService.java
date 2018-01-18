package com.gt.game.core.service.luck;

import com.baomidou.mybatisplus.service.IService;
import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.luck.req.LuckIdReq;
import com.gt.game.core.bean.luck.req.LuckListPageReq;
import com.gt.game.core.bean.luck.req.LuckReq;
import com.gt.game.core.bean.luck.res.LuckCountRes;
import com.gt.game.core.bean.luck.res.LuckListRes;
import com.gt.game.core.bean.luck.res.LuckRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.luck.LuckMain;

import java.util.List;

/**
 * <p>
 * 好运翻翻看 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
public interface LuckService{

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    ResponseDTO<LuckCountRes> getLuckCount(BusUser busUser);

    ResponseDTO<List<LuckListRes>> getLuckList(BusUser busUser, LuckListPageReq luckListPageReq);

    ResponseDTO<LuckRes> getLuck(BusUser busUser, LuckIdReq luckIdReq);

    ResponseDTO saveLuck(BusUser busUser, LuckReq luckReq);

    ResponseDTO removeLuck(BusUser busUser, LuckIdReq luckIdReq);
}
