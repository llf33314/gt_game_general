package com.gt.game.core.service.lantern;


import com.baomidou.mybatisplus.service.IService;
import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.lantern.req.*;
import com.gt.game.core.bean.lantern.res.LanternCountActivityRes;
import com.gt.game.core.bean.lantern.res.LanternListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.entity.lantern.LanternAd;

import java.util.List;

/**
 * <p>
 * 元宵点灯 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface LanternService {

    /**
     * 获取手机端链接
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    /**
     * 分页获取元宵点灯活动列表
     * @param busUser
     * @param lanternListReq
     * @return
     */
    ResponseDTO<List<LanternListRes>> getLanternList(BusUser busUser, LanternListReq lanternListReq);

    /**
     * 统计元宵点灯活动总数
     * @param busUser
     * @param lanternCountActivityReq
     * @return
     */
    LanternCountActivityRes countActivity(BusUser busUser, LanternCountActivityReq lanternCountActivityReq);

    /**
     * 新增元宵点灯活动
     * @param busUser
     * @param lanternAddReq
     */
    void addLantern(BusUser busUser, LanternAddReq lanternAddReq);

    /**
     * 编辑元宵点灯活动基础设置
     * @param busUser
     * @param lanternModfiyBasicsReq
     */
    void modfiyBasicsLantern(BusUser busUser, LanternModfiyBasicsReq lanternModfiyBasicsReq);

    /**
     * 编辑元宵点灯活动规则设置
     * @param busUser
     * @param lanternModfiyRuleReq
     */
    void modfiyRuleLantern(BusUser busUser, LanternModfiyRuleReq lanternModfiyRuleReq);

    /**
     * 编辑元宵点灯活动兑奖设置
     * @param busUser
     * @param lanternModfiyExpiryReq
     */
    void modfiyExpiryLantern(BusUser busUser, LanternModfiyExpiryReq lanternModfiyExpiryReq);

    /**
     * 编辑元宵点灯活动奖项设置
     * @param busUser
     * @param lanternModfiyAwardsReq
     */
    void modfiyAwardsLantern(BusUser busUser, LanternModfiyAwardsReq lanternModfiyAwardsReq);

    ResponseDTO<List<LanternListRes>> getWinningList(BusUser busUser, LanternGetWinningReq lanternGetWinningReq);
}
