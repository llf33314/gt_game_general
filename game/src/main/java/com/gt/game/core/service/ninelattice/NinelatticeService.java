package com.gt.game.core.service.ninelattice;


import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.lantern.req.*;
import com.gt.game.core.bean.lantern.req.LanternDelWinningReq;
import com.gt.game.core.bean.lantern.res.*;
import com.gt.game.core.bean.ninelattice.req.*;
import com.gt.game.core.bean.ninelattice.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 幸运九宫格 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface NinelatticeService {

    /**
     * 获取手机端链接
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);


    /**
     *分页获取幸运九宫格活动列表
     * @param busUser
     * @param ninelatticeListReq
     * @return
     */
    ResponseDTO<List<NinelatticeListRes>> getNinelatticeList(BusUser busUser, NinelatticeListReq ninelatticeListReq);

    /**
     * 统计元幸运九宫格活动总数
     * @param busUser
     * @param ninelatticeCountActivityReq
     * @return
     */
    NinelatticeCountActivityRes countNinelattice(BusUser busUser, NinelatticeCountActivityReq ninelatticeCountActivityReq);

    /**
     * 新增幸运九宫格活动
     * @param busUser
     * @param ninelatticeAddReq
     */
    void addNinelattice(BusUser busUser, NinelatticeAddReq ninelatticeAddReq);

    /**
     * 通过活动id查询幸运九宫格活动
     * @param busUser
     * @param ninelatticeGetActivityReq
     * @return
     */
    NinelatticeGetActivityRes getActivityById(BusUser busUser, NinelatticeGetActivityReq ninelatticeGetActivityReq);

    /**
     * 编辑幸运九宫格活动基础设置
     * @param busUser
     * @param ninelatticeModfiyBasicsReq
     */
    void modfiyBasicsNinelattice(BusUser busUser, NinelatticeModfiyBasicsReq ninelatticeModfiyBasicsReq);

    /**
     * 编辑幸运九宫格活动规则设置
     * @param busUser
     * @param ninelatticeModfiyRuleReq
     */
    void modfiyRuleNinelattice(BusUser busUser, NinelatticeModfiyRuleReq ninelatticeModfiyRuleReq);

    /**
     * 编辑幸运九宫格活动兑奖设置
     * @param busUser
     * @param ninelatticeModfiyExpiryReq
     */
    void modfiyExpiryNinelattice(BusUser busUser, NinelatticeModfiyExpiryReq ninelatticeModfiyExpiryReq);

    /**
     * 编辑幸运九宫格奖项设置
     * @param busUser
     * @param ninelatticeModfiyAwardsReq
     */
    void modfiyAwardsNinelattice(BusUser busUser, NinelatticeModfiyAwardsReq ninelatticeModfiyAwardsReq);

    /**
     * 删除幸运九宫格活动
     * @param busUser
     * @param ninelatticeDelReq
     */
    void delNinelattice(BusUser busUser, NinelatticeDelReq ninelatticeDelReq);

    /**
     * 分页获取幸运九宫格中奖记录列表
     * @param busUser
     * @param ninelatticeGetWinningReq
     * @return
     */
    ResponseDTO<List<NinelatticeGetWinningRes>> getWinningList(BusUser busUser, NinelatticeGetWinningReq ninelatticeGetWinningReq);

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param ninelatticeEditApplyReq
     * @return
     */
    ResponseDTO editNinelatticeApply(BusUser busUser, NinelatticeEditApplyReq ninelatticeEditApplyReq);

    /**
     * 导出中奖记录
     * @param params
     * @return
     */
    Map<String,Object> exportNinelattice(Map<String, Object> params);

    /**
     * 批量删除幸运九宫格中奖记录
     * @param busUser
     * @param ninelatticeDelWinningReq
     */
    void delNinelatticeWinning(BusUser busUser, NinelatticeDelWinningReq ninelatticeDelWinningReq);

    /**
     * 分页获取核销授权列表
     * @param busUser
     * @param ninelatticeAuthorityListReq
     * @return
     */
    ResponseDTO<List<NinelatticeAuthorityListRes>> getNinelatticeAuthorityList(BusUser busUser, NinelatticeAuthorityListReq ninelatticeAuthorityListReq);

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    ResponseDTO<List<NinelatticePrizeTypeListRes>> getNinelatticePrizeType(BusUser busUser);

    /**
     * 批量删除核销授权
     * @param busUser
     * @param ninelatticeDelAuthorityReq
     */
    void delNinelatticeAuthority(BusUser busUser, NinelatticeDelAuthorityReq ninelatticeDelAuthorityReq);


}
