package com.gt.game.core.service.common.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.common.req.MemberListPageReq;
import com.gt.game.core.bean.common.res.MemberListPageRes;
import com.gt.game.core.dao.common.FansInfoMapper;
import com.gt.game.core.service.common.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 粉丝 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    FansInfoMapper fansInfoMapper;

    @Override
    public ResponseDTO<List<MemberListPageRes>> getMemberList(WxPublicUsers wxPublicUsers, MemberListPageReq memberListPageReq) {
        Page<MemberListPageRes> page = new Page<>(memberListPageReq.getCurrent(),memberListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        map.put("wxUserId",wxPublicUsers.getUserName());
        map.put("search",memberListPageReq.getMemberName());
        List<MemberListPageRes> listPageResList = fansInfoMapper.getMemberList(page,map);
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",listPageResList,pageDTO);
    }
}
