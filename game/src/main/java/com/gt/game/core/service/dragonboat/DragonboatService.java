package com.gt.game.core.service.dragonboat;


import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.dragonboat.req.*;
import com.gt.game.core.bean.dragonboat.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 元宵点灯 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface DragonboatService {

   /**
     * 获取手机端链接
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    /**
     * 获取新增授权链接
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    /**
     * 获取端午赛龙舟活动数量
     * @param busUser
     * @return
     */
    ResponseDTO<DragonboatCountRes> getDragonboatCount(BusUser busUser,DragonboatCountActivityReq dragonboatCountActivityReq);

    /**
     * 分页端午赛龙舟获取活动列表
     * @param busUser
     * @param dragonboatListPageReq
     * @return
     */
    ResponseDTO<List<DragonboatListRes>> getDragonboatList(BusUser busUser, DragonboatListPageReq dragonboatListPageReq);

    /**
     * 新增端午赛龙舟活动
     * @param busUser
     * @param dragonboatAddReq
     */
    void addDragonboat(BusUser busUser, DragonboatAddReq dragonboatAddReq);

    /**
     * 编辑端午赛龙舟活动基础设置
     * @param busUser
     * @param dragonboatModfiyBasicsReq
     */
    void modfiyBasicsDragonboat(BusUser busUser, DragonboatModfiyBasicsReq dragonboatModfiyBasicsReq);

    /**
     * 编辑端午赛龙舟活动规则设置
     * @param busUser
     * @param dragonboatModfiyRuleReq
     */
    void modfiyRuleDragonboat(BusUser busUser, DragonboatModfiyRuleReq dragonboatModfiyRuleReq);

    /**
     * 编辑端午赛龙舟活动兑奖设置
     * @param busUser
     * @param dragonboatModfiyExpiryReq
     */
    void modfiyExpiryDragonboat(BusUser busUser, DragonboatModfiyExpiryReq dragonboatModfiyExpiryReq);

    /**
     * 编辑端午赛龙舟活动奖项设置
     * @param busUser
     * @param dragonboatModfiyAwardsReq
     */
    void modfiyAwardsDragonboat(BusUser busUser, DragonboatModfiyAwardsReq dragonboatModfiyAwardsReq);

    /**
     * 批量删除端午赛龙舟活动
     * @param busUser
     * @param dragonboatDelReq
     */
    void delDragonboat(BusUser busUser, DragonboatDelReq dragonboatDelReq);

    /**
     * 分页获取端午赛龙舟中奖记录列表
     * @param busUser
     * @param dragonboatGetWinningReq
     * @return
     */
    ResponseDTO<List<DragonboatGetWinningRes>> getWinningList(BusUser busUser, DragonboatGetWinningReq dragonboatGetWinningReq);

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param dragonboatEditApplyReq
     * @return
     */
    ResponseDTO editDragonboatApply(BusUser busUser, DragonboatEditApplyReq dragonboatEditApplyReq);

    /**
     * 导出中奖记录
     * @param params
     * @return
     */
    Map<String,Object> exportDragonboat(Map<String, Object> params);

    /**
     * 批量删除中奖记录
     * @param busUser
     * @param dragonboatDelWinningReq
     */
    void delLanternWinning(BusUser busUser, DragonboatDelWinningReq dragonboatDelWinningReq);

    /**
     * 分页获取核销授权列表
     * @param busUser
     * @param dragonboatAuthorityListReq
     * @return
     */
    ResponseDTO<List<DragonboatAuthorityListRes>> getDragonboatAuthorityList(BusUser busUser, DragonboatAuthorityListReq dragonboatAuthorityListReq);

    /**
     * 批量删除核销授权
     * @param busUser
     * @param dragonboatDelAuthorityReq
     */
    void delDragonboatAuthority(BusUser busUser, DragonboatDelAuthorityReq dragonboatDelAuthorityReq);

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    ResponseDTO<List<DragonboatPrizeTypeListRes>> getDragonboatPrizeType(BusUser busUser);
}
