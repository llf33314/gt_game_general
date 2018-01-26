package com.gt.game.core.service.scratch;

import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.eggs.res.EggsListRes;
import com.gt.game.core.bean.qixi.req.*;
import com.gt.game.core.bean.qixi.res.*;
import com.gt.game.core.bean.scratch.req.*;
import com.gt.game.core.bean.scratch.res.*;

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

    MobileUrlRes getMobileUrl(WxPublicUsers loginPbUser, MobileUrlReq mobileUrlReq);

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
    void addScratch(BusUser busUser ,WxPublicUsers loginPbUser, ScratchAddReq scratchAddReq);

    /**
     * 通过活动id查询刮刮乐活动
     * @param busUser
     * @param scratchGetActivityReq
     * @return
     */
    ScratchGetActivityRes getActivityById(BusUser busUser, ScratchGetActivityReq scratchGetActivityReq);

    /**
     * 编辑刮刮乐活动设置
     * @param busUser
     * @param scratchModfiyReq
     */
    void modfiyScratch(BusUser busUser, ScratchModfiyReq scratchModfiyReq);

    /**
     * 刮刮乐 暂停/开始活动
     * @param busUser
     * @param scratchStopIdReq
     * @return
     */
    ResponseDTO stopScratch(WxPublicUsers busUser, ScratchStopIdReq scratchStopIdReq);

    /**
     * 批量删除刮刮乐活动
     * @param busUser
     * @param scratchDelReq
     */
    void delScratch(BusUser busUser, ScratchDelReq scratchDelReq);


    /**
     * 分页获取砸金蛋中奖记录列表
     * @param busUser
     * @param scratchGetWinningReq
     * @return
     */
    ResponseDTO<List<ScratchGetWinningRes>> getWinningList(BusUser busUser, ScratchGetWinningReq scratchGetWinningReq);

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param scratchEditApplyReq
     * @return
     */
    ResponseDTO editScratchApply(BusUser busUser, ScratchEditApplyReq scratchEditApplyReq);

    /**
     * 导出刮刮乐中奖记录
     * @param params
     * @param busUser
     * @return
     */
    Map<String,Object> exportScratch(Map<String, Object> params, BusUser busUser);

    /**
     * 批量删除刮刮乐活动中奖记录
     * @param busUser
     * @param scratchDelWinningReq
     */
    void delScratchWinning(BusUser busUser, ScratchDelWinningReq scratchDelWinningReq);

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    ResponseDTO<List<ScratchPrizeTypeListRes>> getScratchPrizeType(BusUser busUser);


}
