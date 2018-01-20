package com.gt.game.core.service.scratch;

import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.eggs.res.EggsListRes;
import com.gt.game.core.bean.qixi.req.*;
import com.gt.game.core.bean.qixi.res.*;
import com.gt.game.core.bean.scratch.req.ScratchAddReq;
import com.gt.game.core.bean.scratch.req.ScratchCountActivityReq;
import com.gt.game.core.bean.scratch.req.ScratchGetActivityReq;
import com.gt.game.core.bean.scratch.req.ScratchListReq;
import com.gt.game.core.bean.scratch.res.ScratchCountActivityRes;
import com.gt.game.core.bean.scratch.res.ScratchGetActivityRes;
import com.gt.game.core.bean.scratch.res.ScratchListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 刮刮乐 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
public interface ScratchService {

    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    /**
     * 分页获取刮刮乐活动列表
     * @param loginPbUser
     * @param scratchListReq
     * @return
     */
    ResponseDTO<List<ScratchListRes>> getScratchList(WxPublicUsers loginPbUser, ScratchListReq scratchListReq);

    /**
     * 统计刮刮乐活动总数
     * @param loginPbUser
     * @param scratchCountActivityReq
     * @return
     */
    ScratchCountActivityRes countScratch(WxPublicUsers loginPbUser, ScratchCountActivityReq scratchCountActivityReq);

    /**
     * 新增刮刮乐活动
     * @param loginPbUser
     * @param scratchAddReq
     */
    void addScratch(WxPublicUsers loginPbUser, ScratchAddReq scratchAddReq);

    /**
     * 通过活动id查询刮刮乐活动
     * @param busUser
     * @param scratchGetActivityReq
     * @return
     */
    ScratchGetActivityRes getActivityById(BusUser busUser, ScratchGetActivityReq scratchGetActivityReq);
}
