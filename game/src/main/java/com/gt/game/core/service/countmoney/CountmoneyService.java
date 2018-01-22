package com.gt.game.core.service.countmoney;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.countmoney.req.*;
import com.gt.game.core.bean.countmoney.res.*;
import com.gt.game.core.bean.lantern.req.LanternDelReq;
import com.gt.game.core.bean.lantern.req.LanternDelWinningReq;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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

    /**
     * 疯狂数钱活动中奖记录发放奖品
     * @param busUser
     * @param countmoneyEditApplyReq
     * @return
     */
    ResponseDTO editCountmoneyApply(BusUser busUser, CountmoneyEditApplyReq countmoneyEditApplyReq);

    /**
     * 导出疯狂数钱活动中奖记录
     * @param params
     * @return
     */
    Map<String,Object> exportCountmoney(Map<String, Object> params, BusUser busUser);

    /**
     * 批量删除疯狂数钱活动中奖记录
     * @param busUser
     * @param countmoneyDelWinningReq
     */
    void delCountmoneyWinning(BusUser busUser, CountmoneyDelWinningReq countmoneyDelWinningReq);

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    ResponseDTO<List<CountmoneyPrizeTypeListRes>> getCountmoneyPrizeType(BusUser busUser);
}
