package com.gt.game.core.controller.demolition;


import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.demolition.req.DemolitionListPageReq;
import com.gt.game.core.bean.demolition.res.DemolitionListRes;
import com.gt.game.core.bean.demolition.res.DemolitionRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.demolition.DemolitionException;
import com.gt.game.core.service.demolition.DemolitionService;
import com.gt.game.core.util.CommonUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 拆礼盒 前端控制器
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Api(value = "/app/demolition", description = "拆礼盒商家后台")
@RestController
@RequestMapping(value = "/app/demolition")
public class DemolitiongiftboxController  extends BaseController {


    @Autowired
    DemolitionService demolitionService;


    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "获取手机端链接", notes = "获取手机端链接")
    @RequestMapping(value = "/getMobileUrl", method = RequestMethod.POST)
    @Override
    protected ResponseDTO getMobileUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            MobileUrlRes mobileUrlRes = demolitionService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (DemolitionException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = DemolitionListRes.class),
    })
    @ApiOperation(value = "分页获取活动列表", notes = "分页获取活动列表")
    @RequestMapping(value = "/getDemolitionList", method = RequestMethod.POST)
    protected ResponseDTO getDemolitionList(
            @RequestBody @ApiParam("请求参数") DemolitionListPageReq demolitionListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<DemolitionListRes>> responseDTO = demolitionService.getDemolitionList(busUser, demolitionListPageReq);
            return responseDTO;
        } catch (DemolitionException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = DemolitionListRes.class),
    })
    @ApiOperation(value = "获取活动", notes = "获取活动")
    @RequestMapping(value = "/getDemolition", method = RequestMethod.GET)
    protected ResponseDTO getDemolition(
            @RequestBody @ApiParam("id") Integer id,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<DemolitionRes> responseDTO = demolitionService.getDemolition(busUser, id);
            return responseDTO;
        } catch (DemolitionException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
}
