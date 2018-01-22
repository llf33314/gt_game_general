package com.gt.game.core.service.turntable;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.tree.req.*;
import com.gt.game.core.bean.tree.res.*;
import com.gt.game.core.bean.turntable.req.TurntableListReq;
import com.gt.game.core.bean.turntable.res.TurntableListRes;
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
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq);

    /**
     * 分页获取大转盘活动列表
     * @param loginPbUser
     * @param turntableListReq
     * @return
     */
    ResponseDTO<List<TurntableListRes>> getTurntableList(WxPublicUsers loginPbUser, TurntableListReq turntableListReq);
}
