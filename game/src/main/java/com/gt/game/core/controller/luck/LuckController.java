package com.gt.game.core.controller.luck;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.luck.req.LuckIdReq;
import com.gt.game.core.bean.luck.req.LuckListPageReq;
import com.gt.game.core.bean.luck.req.LuckReq;
import com.gt.game.core.bean.luck.req.LuckWinningPageReq;
import com.gt.game.core.bean.luck.res.LuckCountRes;
import com.gt.game.core.bean.luck.res.LuckListRes;
import com.gt.game.core.bean.luck.res.LuckRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.luck.LuckException;
import com.gt.game.core.service.luck.LuckService;
import com.gt.game.core.util.CommonUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 好运翻翻看 前端控制器
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Api(value = "/app/luck", description = "好运翻翻看商家后台")
@RestController
@RequestMapping("/app/luck")
public class LuckController  extends BaseController {
	
    @Autowired
    LuckService luckService;

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "获取手机端链接", notes = "获取手机端链接")
    @RequestMapping(value = "/getMobileUrl", method = RequestMethod.POST)
    @Override
    protected ResponseDTO getMobileUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            MobileUrlRes mobileUrlRes = luckService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = LuckCountRes.class),
    })
    @ApiOperation(value = "获取活动数量", notes = "获取活动数量")
    @RequestMapping(value = "/getLuckCount", method = RequestMethod.POST)
    protected ResponseDTO getLuckCount(
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO<LuckCountRes> responseDTO = luckService.getLuckCount(busUser);
            return responseDTO;
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = LuckListRes.class),
    })
    @ApiOperation(value = "分页获取活动列表", notes = "分页获取活动列表")
    @RequestMapping(value = "/getLuckList", method = RequestMethod.POST)
    protected ResponseDTO getLuckList(
            @RequestBody @ApiParam("请求参数") LuckListPageReq LuckListPageReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO<List<LuckListRes>> responseDTO = luckService.getLuckList(busUser, LuckListPageReq);
            return responseDTO;
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = LuckRes.class),
    })
    @ApiOperation(value = "获取活动", notes = "获取活动")
    @RequestMapping(value = "/getLuck", method = RequestMethod.POST)
    protected ResponseDTO getLuck(
            @RequestBody @ApiParam("请求参数") LuckIdReq luckIdReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO<LuckRes> responseDTO = luckService.getLuck(busUser, luckIdReq);
            return responseDTO;
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "保存活动", notes = "保存活动")
    @RequestMapping(value = "/saveLuck", method = RequestMethod.POST)
    protected ResponseDTO saveLuck(
            @RequestBody @ApiParam("请求参数") @Valid LuckReq luckReq
            , BindingResult bindingResult, HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            BusUser user = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = luckService.saveLuck(busUser,user,luckReq);
            return responseDTO;
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "删除活动", notes = "删除活动")
    @RequestMapping(value = "/removeLuck", method = RequestMethod.POST)
    protected ResponseDTO removeLuck(
            @RequestBody @ApiParam("请求参数") LuckIdReq luckIdReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO responseDTO = luckService.removeLuck(busUser, luckIdReq);
            return responseDTO;
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
//    @ApiResponses({
//            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
//            @ApiResponse(code = 1, message = "响应对象", response = ResponseDTO.class),
//    })
//    @ApiOperation(value = "分页获取中奖记录", notes = "分页获取中奖记录")
//    @RequestMapping(value = "/getLuckWinningList", method = RequestMethod.POST)
//    protected ResponseDTO getLuckWinningList(
//            @RequestBody @ApiParam("请求参数") LuckWinningPageReq luckWinningPageReq,
//            HttpServletRequest request) {
//        try {
//            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
//            ResponseDTO<List<>> responseDTO = luckService.getLuckWinningList(busUser, luckWinningPageReq);
//            return responseDTO;
//        } catch (LuckException e){
//            logger.error(e.getMessage(), e.fillInStackTrace());
//            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
//        } catch (Exception e){
//            e.printStackTrace();
//            return ResponseDTO.createByError();
//        }
//    }
}
