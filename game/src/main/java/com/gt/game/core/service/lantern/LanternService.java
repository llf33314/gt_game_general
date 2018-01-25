package com.gt.game.core.service.lantern;



import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.lantern.req.*;
import com.gt.game.core.bean.lantern.res.*;
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
public interface LanternService {

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
     * 通过活动id查询元宵点灯活动
     * @param busUser
     * @param lanternGetActivityReq
     * @return
     */
    LanternGetActivityRes getActivityById(BusUser busUser, LanternGetActivityReq lanternGetActivityReq);

    /**
     * 编辑元宵点灯活动设置
     * @param busUser
     * @param lanternModfiyReq
     */
    void modfiyLantern(BusUser busUser, LanternModfiyReq lanternModfiyReq);

    /**
     * 删除元宵点灯活动
     * @param busUser
     * @param lanternDelReq
     */
    void delLantern(BusUser busUser, LanternDelReq lanternDelReq);

    /**
     * 分页获取元宵点灯中奖记录列表
     * @param busUser
     * @param lanternGetWinningReq
     * @return
     */
    ResponseDTO<List<LanternGetWinningRes>> getWinningList(BusUser busUser, LanternGetWinningReq lanternGetWinningReq);

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param lanternEditApplyReq
     * @return
     */
    ResponseDTO editLanternApply(BusUser busUser, LanternEditApplyReq lanternEditApplyReq);

    /**
     * 导出元宵点灯中奖记录
     * @param params
     * @return
     */
    Map<String,Object> exportLantern(Map<String, Object> params);

    /**
     * 批量删除元宵点灯中奖记录
     * @param busUser
     * @param lanternDelWinningReq
     * @return
     */
    void  delLanternWinning(BusUser busUser, LanternDelWinningReq lanternDelWinningReq);

    /**
     * 分页获取核销授权列表
     * @param busUser
     * @param lanternAuthorityListReq
     * @return
     */
    ResponseDTO<List<LanternAuthorityListRes>> getLanternAuthorityList(BusUser busUser, LanternAuthorityListReq lanternAuthorityListReq);

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    ResponseDTO<List<LanternPrizeTypeListRes>> getLanternPrizeType(BusUser busUser);

    /**
     * 批量删除核销授权
     * @param busUser
     * @param lanternDelAuthorityReq
     */
    void delLanternAuthority(BusUser busUser, LanternDelAuthorityReq lanternDelAuthorityReq);



}
