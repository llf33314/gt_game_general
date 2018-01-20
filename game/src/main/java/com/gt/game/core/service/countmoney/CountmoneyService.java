package com.gt.game.core.service.countmoney;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.countmoney.req.*;
import com.gt.game.core.bean.countmoney.res.CountmoneyGetWinningRes;
import com.gt.game.core.bean.countmoney.res.CountmoneyListRes;
import com.gt.game.core.bean.countmoney.res.CountmoneyCountActivityRes;
import com.gt.game.core.bean.countmoney.res.CountmoneyGetActivityRes;
import com.gt.game.core.bean.lantern.req.LanternDelReq;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;

import java.util.List;

/**
 * <p>
 * 疯狂数钱 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface CountmoneyService {

    /**
     * 获取手机端链接
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    /**
     * 分页获取疯狂数钱活动列表
     * @param loginPbUser
     * @param countmoneyListReq
     * @return
     */
    ResponseDTO<List<CountmoneyListRes>> getCountMoneyList(WxPublicUsers loginPbUser, CountmoneyListReq countmoneyListReq);

    /**
     * 统计疯狂数钱活动总数
     * @param loginPbUser
     * @param countmoneyCountActivityReq
     * @return
     */
    CountmoneyCountActivityRes countActivity(WxPublicUsers loginPbUser, CountmoneyCountActivityReq countmoneyCountActivityReq);

    /**
     * 新增疯狂数钱活动
     * @param loginPbUser
     * @param countmoneyAddReq
     */
    void addCountmoney(WxPublicUsers loginPbUser, CountmoneyAddReq countmoneyAddReq);

    /**
     * 通过活动id查询疯狂数钱活动
     * @param busUser
     * @param countmoneyGetActivityReq
     * @return
     */
    CountmoneyGetActivityRes getActivityById(BusUser busUser, CountmoneyGetActivityReq countmoneyGetActivityReq);

    /**
     * 编辑疯狂数钱活动设置
     * @param busUser
     * @param countmoneyModfiyBasicsReq
     */
    void modfiyBasicsCountmoney(BusUser busUser, CountmoneyModfiyReq countmoneyModfiyBasicsReq);

    /**
     * 删除疯狂数钱活动
     * @param busUser
     * @param lanternDelReq
     */
    void delCountmoney(BusUser busUser, LanternDelReq lanternDelReq);

    /**
     * 分页获取分页获取中奖记录列表
     * @param busUser
     * @param countmoneyGetWinningReq
     * @return
     */
    ResponseDTO<List<CountmoneyGetWinningRes>> getWinningList(BusUser busUser, CountmoneyGetWinningReq countmoneyGetWinningReq);
}
