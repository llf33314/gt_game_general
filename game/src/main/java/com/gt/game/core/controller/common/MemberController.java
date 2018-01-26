package com.gt.game.core.controller.common;

import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.axis.server.wxmp.ShortUrlServer;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.common.req.MemberListPageReq;
import com.gt.game.core.bean.common.res.CardReceiveListRes;
import com.gt.game.core.bean.common.res.MemberListPageRes;
import com.gt.game.core.service.common.MemberService;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.QRcodeKit;
import io.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 链接controller
 * Created by zwq on 2018/1/18 0021.
 */
@Api(value = "/app/member", description = "粉丝控制器")
@RestController
@RequestMapping(value = "/app/member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = MemberListPageRes.class),
    })
    @ApiOperation(value = "分页获取粉丝列表", notes = "分页获取粉丝列表")
    @RequestMapping(value = "/getMemberList", method = RequestMethod.POST)
    protected ResponseDTO getMemberList(
            @RequestBody @ApiParam("请求参数") MemberListPageReq LoveArrowListPageReq,
            HttpServletRequest request) {
        try {

            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<MemberListPageRes>> responseDTO = memberService.getMemberList(busUser, LoveArrowListPageReq);
            return responseDTO;
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = CardReceiveListRes.class),
    })
    @ApiOperation(value = "获取优惠劵列表", notes = "获取优惠劵列表")
    @RequestMapping(value = "/getCardReceviceList", method = RequestMethod.POST)
    protected ResponseDTO getCardReceviceList( HttpServletRequest request) {
        try {

            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<CardReceiveListRes>> responseDTO = memberService.getCardReceviceList(busUser);
            return responseDTO;
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

}
