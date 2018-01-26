package com.gt.game.core.service.eggs;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.eggs.req.*;
import com.gt.game.core.bean.eggs.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 砸金蛋 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface EggsService {

    /**
     * 获取手机端链接
     * @param loginPbUser
     * @param mobileUrlReq
     * @return
     */
    MobileUrlRes getMobileUrl(WxPublicUsers loginPbUser, MobileUrlReq mobileUrlReq);

    /**
     * 分页获取砸金蛋活动列表
     * @param busUser
     * @param eggsListReq
     * @param request
     * @return
     */
    ResponseDTO<List<EggsListRes>> getEggsList(BusUser busUser, EggsListReq eggsListReq, HttpServletRequest request);

    /**
     * 统计砸金蛋活动总数
     * @param busUser
     * @param eggsCountActivityReq
     * @param request
     * @return
     */
    EggsCountActivityRes countEggs(BusUser busUser, EggsCountActivityReq eggsCountActivityReq, HttpServletRequest request);

    /**
     * 新增砸金蛋活动成功
     * @param busUser
     * @param eggsAddReq
     * @param request
     */
    void addEggs(BusUser busUser, EggsAddReq eggsAddReq, HttpServletRequest request);

    /**
     * 通过活动id查询砸金蛋活动
     * @param busUser
     * @param eggsGetActivityReq
     * @return
     */
    EggsGetActivityRes getActivityById(BusUser busUser, EggsGetActivityReq eggsGetActivityReq);

    /**
     * 编辑砸金蛋活动设置
     * @param busUser
     * @param eggsModfiyReq
     */
    void modfiyEggs(BusUser busUser, EggsModfiyReq eggsModfiyReq);

    /**
     * 开始/暂停砸金蛋活动
     * @param loginPbUser
     * @param eggsStopIdReq
     * @return
     */
    ResponseDTO stopEggs( WxPublicUsers loginPbUser, EggsStopIdReq eggsStopIdReq);

    /**
     * 删除砸金蛋活动
     * @param busUser
     * @param eggsDelReq
     */
    void delEggs(BusUser busUser, EggsDelReq eggsDelReq);

    /**
     * 分页获取圣诞大礼包中奖记录列表
     * @param busUser
     * @param eggsGetWinningReq
     * @return
     */
    ResponseDTO<List<EggsGetWinningRes>> getWinningList(BusUser busUser, EggsGetWinningReq eggsGetWinningReq);

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param eggsEditApplyReq
     * @return
     */
    ResponseDTO editEggsApply(BusUser busUser, EggsEditApplyReq eggsEditApplyReq);

    /**
     * 导出中奖记录
     * @param params
     * @param busUser
     * @return
     */
    Map<String,Object> exportTree(Map<String, Object> params, BusUser busUser);

    /**
     * 批量删除砸金蛋中奖记录
     * @param busUser
     * @param eggsDelWinningReq
     */
    void delEggsWinning(BusUser busUser, EggsDelWinningReq eggsDelWinningReq);

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    ResponseDTO<List<EggsPrizeTypeListRes>> getEggsPrizeType(BusUser busUser);


}
