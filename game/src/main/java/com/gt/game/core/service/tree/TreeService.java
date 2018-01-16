package com.gt.game.core.service.tree;


import com.gt.api.bean.session.BusUser;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.lantern.req.*;
import com.gt.game.core.bean.lantern.res.*;
import com.gt.game.core.bean.ninelattice.res.NinelatticeAuthorityListRes;
import com.gt.game.core.bean.ninelattice.res.NinelatticeListRes;
import com.gt.game.core.bean.tree.req.*;
import com.gt.game.core.bean.tree.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 圣诞大礼包 服务类
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
public interface TreeService {

    /**
     * 获取手机端链接
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    /**
     * 分页获取圣诞大礼包活动列表
     * @param busUser
     * @param treeListReq
     * @return
     */
    ResponseDTO<List<TreeListRes>> getTreeList(BusUser busUser, TreeListReq treeListReq, HttpServletRequest request);

    /**
     * 统计元圣诞大礼包活动总数
     * @param busUser
     * @param treeCountActivityReq
     * @param request
     * @return
     */
    TreeCountActivityRes countTree(BusUser busUser, TreeCountActivityReq treeCountActivityReq, HttpServletRequest request);

    /**
     * 新增圣诞大礼包活动
     * @param busUser
     * @param treeAddReq
     * @param request
     */
    void addTree(BusUser busUser, TreeAddReq treeAddReq, HttpServletRequest request);

    /**
     * 通过活动id查询圣诞大礼包活动成功
     * @param busUser
     * @param treeGetActivityReq
     * @return
     */
    TreeGetActivityRes getActivityById(BusUser busUser, TreeGetActivityReq treeGetActivityReq);

    /**
     * 编辑圣诞大礼包活动基础设置
     * @param busUser
     * @param treeModfiyBasicsReq
     */
    void modfiyBasicsTree(BusUser busUser, TreeModfiyBasicsReq treeModfiyBasicsReq);

    /**
     * 编辑圣诞大礼包活动规则设置
     * @param busUser
     * @param treeModfiyRuleReq
     */
    void modfiyRuleTree(BusUser busUser, TreeModfiyRuleReq treeModfiyRuleReq);

    /**
     * 编辑圣诞大礼包活动兑奖设置
     * @param busUser
     * @param treeModfiyExpiryReq
     */
    void modfiyExpiryTree(BusUser busUser, TreeModfiyExpiryReq treeModfiyExpiryReq);

    /**
     * 编辑圣诞大礼包奖项设置
     * @param busUser
     * @param treeModfiyAwardsReq
     */
    void modfiyAwardsTree(BusUser busUser, TreeModfiyAwardsReq treeModfiyAwardsReq);

    /**
     * 批量删除圣诞大礼包活动
     * @param busUser
     * @param treeDelReq
     */
    void delTree(BusUser busUser, TreeDelReq treeDelReq);

    /**
     * 分页获取圣诞大礼包中奖记录列表
     * @param busUser
     * @param treeGetWinningReq
     * @return
     */
    ResponseDTO<List<TreeGetWinningRes>> getWinningList(BusUser busUser, TreeGetWinningReq treeGetWinningReq);

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param treeEditApplyReq
     * @return
     */
    ResponseDTO editTreeApply(BusUser busUser, TreeEditApplyReq treeEditApplyReq);

    /**
     * 导出中奖记录
     * @param params
     * @return
     */
    Map<String,Object> exportTree(Map<String, Object> params,BusUser busUser);

    /**
     * 批量删除圣诞大礼包中奖记录
     * @param busUser
     * @param treeDelWinningReq
     */
    void delTreeWinning(BusUser busUser, TreeDelWinningReq treeDelWinningReq);

    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    ResponseDTO<List<TreePrizeTypeListRes>> getTreePrizeType(BusUser busUser);
}
