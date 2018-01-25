package com.gt.game.core.service.turntable;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.scratch.res.ScratchPrizeTypeListRes;
import com.gt.game.core.bean.tree.req.*;
import com.gt.game.core.bean.tree.res.*;
import com.gt.game.core.bean.turntable.req.*;
import com.gt.game.core.bean.turntable.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 大转盘 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface TurntableService {

    /**
     * 获取手机端链接
     * @param loginPbUser
     * @param mobileUrlReq
     * @return
     */
    MobileUrlRes getMobileUrl( WxPublicUsers loginPbUser, MobileUrlReq mobileUrlReq);

    /**
     * 分页获取大转盘活动列表
     * @param loginPbUser
     * @param turntableListReq
     * @return
     */
    ResponseDTO<List<TurntableListRes>> getTurntableList(WxPublicUsers loginPbUser, TurntableListReq turntableListReq);

    /**
     * 统计大转盘活动总数
     * @param loginPbUser
     * @param turntableCountActivityReq
     * @return
     */
    TurntableCountActivityRes countTurntable(WxPublicUsers loginPbUser, TurntableCountActivityReq turntableCountActivityReq);

    /**
     * 新增大转盘活动
     * @param loginPbUser
     * @param turntableAddReq
     */
    void addScratch( BusUser busUser,WxPublicUsers loginPbUser, TurntableAddReq turntableAddReq);

    /**
     * 通过活动id查询大转盘活动
     * @param busUser
     * @param turntableGetActivityReq
     * @return
     */
    TurntableGetActivityRes getActivityById(BusUser busUser, TurntableGetActivityReq turntableGetActivityReq);

    /**
     * 编辑大转盘活动设置
     * @param busUser
     * @param turntableModfiyReq
     */
    void modfiyTurntable(BusUser busUser, TurntableModfiyReq turntableModfiyReq);

    /**
     * 大转盘活动暂停/开始活动
     * @param busUser
     * @param turntableStopIdReq
     * @return
     */
    ResponseDTO stopTurntable(WxPublicUsers busUser, TurntableStopIdReq turntableStopIdReq);

    /**
     * 删除大转盘活动
     * @param busUser
     * @param turntableDelReq
     */
    void delTurntable(BusUser busUser, TurntableDelReq turntableDelReq);

    /**
     * 分页获取大转盘中奖记录列表
     * @param busUser
     * @param turntableGetWinningReq
     * @return
     */
    ResponseDTO<List<TurntableGetWinningRes>> getWinningList(BusUser busUser, TurntableGetWinningReq turntableGetWinningReq);

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param turntableEditApplyReq
     * @return
     */
    ResponseDTO editTurntableApply(BusUser busUser, TurntableEditApplyReq turntableEditApplyReq);

    /**
     * 导出中奖记录
     * @param params
     * @param busUser
     * @return
     */
    Map<String,Object> exportTurntable(Map<String, Object> params, BusUser busUser);

    /**
     * 批量删除大转盘活动中奖记录
     * @param busUser
     * @param turntableDelWinningReq
     */
    void delTurntableWinning(BusUser busUser, TurntableDelWinningReq turntableDelWinningReq);

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    ResponseDTO<List<TurntablePrizeTypeListRes>> getTurntablePrizeType(BusUser busUser);
}
