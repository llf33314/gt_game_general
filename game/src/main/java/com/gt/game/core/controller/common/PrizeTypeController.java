package com.gt.game.core.controller.common;

import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.common.req.MemberListPageReq;
import com.gt.game.core.bean.common.res.MemberListPageRes;
import com.gt.game.core.bean.common.res.PrizeTypeListRes;
import com.gt.game.core.service.common.MemberService;
import com.gt.game.core.service.common.PrizeTypeService;
import com.gt.game.core.util.CommonUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 链接controller
 * Created by zwq on 2018/1/18 0021.
 */
@Api(value = "/app/prize", description = "奖品控制器")
@RestController
@RequestMapping(value = "/app/prize")
public class PrizeTypeController {

    @Autowired
    PrizeTypeService prizeTypeService;

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = PrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表(去掉谢谢参与和手机话费)", notes = "获取奖品类型列表(去掉谢谢参与和手机话费)")
    @RequestMapping(value = "/getPrizeTypeOne", method = RequestMethod.POST)
    protected ResponseDTO getPrizeTypeOne(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<PrizeTypeListRes>> responseDTO = prizeTypeService.getPrizeTypeOne(busUser);
            return responseDTO;
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = PrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表", notes = "获取奖品类型列表")
    @RequestMapping(value = "/getPrizeType", method = RequestMethod.POST)
    protected ResponseDTO getPrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<PrizeTypeListRes>> responseDTO = prizeTypeService.getPrizeType(busUser);
            return responseDTO;
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

}
