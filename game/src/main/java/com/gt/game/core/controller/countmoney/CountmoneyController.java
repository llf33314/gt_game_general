package com.gt.game.core.controller.countmoney;



import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.countmoney.req.*;
import com.gt.game.core.bean.countmoney.res.CountmoneyListRes;
import com.gt.game.core.bean.countmoney.res.CountmoneyCountActivityRes;
import com.gt.game.core.bean.countmoney.res.CountmoneyGetActivityRes;
import com.gt.game.core.bean.countmoney.res.CountmoneyGetWinningRes;
import com.gt.game.core.bean.lantern.req.LanternDelReq;
import com.gt.game.core.bean.lantern.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.countmoney.CountmoneyException;
import com.gt.game.core.service.countmoney.CountmoneyService;
import com.gt.game.core.util.CommonUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 疯狂数钱  前端控制器
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Api(value = "/app/countmoney", description = "疯狂数钱商家后台")
@RestController
@RequestMapping(value = "/app/countmoney")
public class CountmoneyController extends BaseController {

    @Autowired
    CountmoneyService countmoneyService;


    //TODO  获取手机端链接
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "获取手机端链接", notes = "获取手机端链接")
    @RequestMapping(value = "/getMobileUrl", method = RequestMethod.POST)
    @Override
    protected ResponseDTO getMobileUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            MobileUrlRes mobileUrlRes = countmoneyService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (CountmoneyException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取疯狂数钱活动列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = LanternListRes.class),
    })
    @ApiOperation(value = "分页获取疯狂数钱活动列表", notes = "分页获取疯狂数钱活动列表")
    @RequestMapping(value = "/getCountMoneyList", method = RequestMethod.POST)
    protected ResponseDTO getCountMoneyList(@RequestBody @ApiParam(value = "分页获取疯狂数钱活动列表对象") CountmoneyListReq countmoneyListReq, BindingResult bindingResult,
                                            HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(countmoneyListReq.toString());
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO<List<CountmoneyListRes>> responseDTO = countmoneyService.getCountMoneyList(loginPbUser, countmoneyListReq);
            return responseDTO;
        } catch (CountmoneyException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO    统计疯狂数钱活动总数
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = LanternCountActivityRes.class),
    })
    @ApiOperation(value = "统计疯狂数钱活动总数", notes = "统计疯狂数钱活动总数")
    @RequestMapping(value = "/countActivity", method = RequestMethod.POST)
    public ResponseDTO countActivity(@RequestBody @ApiParam(value = "统计疯狂数钱活动总数对象") CountmoneyCountActivityReq countmoneyCountActivityReq, BindingResult bindingResult,
                                     HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(countmoneyCountActivityReq.toString());
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            CountmoneyCountActivityRes countmoneyCountActivityRes = countmoneyService.countActivity(loginPbUser, countmoneyCountActivityReq);
            return ResponseDTO.createBySuccess("统计疯狂数钱活动总数成功", countmoneyCountActivityRes);
        }catch (CountmoneyException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  新增元宵点灯活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "新增疯狂数钱活动", notes = "新增疯狂数钱活动")
    @RequestMapping(value = "/addCountmoney", method = RequestMethod.POST)
    protected ResponseDTO addCountmoney(@RequestBody @ApiParam(value = "新增疯狂数钱活动列表对象") CountmoneyAddReq countmoneyAddReq, BindingResult bindingResult,
                                        HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(countmoneyAddReq.toString());
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            countmoneyService.addCountmoney(loginPbUser, countmoneyAddReq);
            return ResponseDTO.createBySuccessMessage("新增疯狂数钱活动成功");
        } catch (CountmoneyException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO    通过活动id查询疯狂数钱活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = LanternCountActivityRes.class),
    })
    @ApiOperation(value = "通过活动id查询疯狂数钱活动", notes = "通过活动id查询疯狂数钱活动")
    @RequestMapping(value = "/getActivityById", method = RequestMethod.POST)
    public ResponseDTO getActivityById(@RequestBody @ApiParam(value = "通过活动id查询疯狂数钱活动对象") CountmoneyGetActivityReq countmoneyGetActivityReq, BindingResult bindingResult,
                                       HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(countmoneyGetActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            CountmoneyGetActivityRes countmoneyGetActivityRes = countmoneyService.getActivityById(busUser, countmoneyGetActivityReq);
            return ResponseDTO.createBySuccess("通过活动id查询疯狂数钱活动成功", countmoneyGetActivityRes);
        }catch (CountmoneyException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑疯狂数钱活动基础设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑疯狂数钱活动设置", notes = "编辑疯狂数钱活动设置")
    @RequestMapping(value = "/modfiyCountmoney", method = RequestMethod.POST)
    protected ResponseDTO modfiyCountmoney(@RequestBody @ApiParam(value = "编辑疯狂数钱活动设置对象") CountmoneyModfiyReq countmoneyModfiyBasicsReq, BindingResult bindingResult,
                                           HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(countmoneyModfiyBasicsReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            countmoneyService.modfiyBasicsCountmoney(busUser,countmoneyModfiyBasicsReq);
            return ResponseDTO.createBySuccessMessage("编辑疯狂数钱活动设置成功");
        } catch (CountmoneyException e){
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
    @ApiOperation(value = "删除疯狂数钱活动", notes = "删除疯狂数钱活动")
    @RequestMapping(value = "/delCountmoney", method = RequestMethod.POST)
    protected ResponseDTO delCountmoney(
            @RequestBody @ApiParam("请求参数") LanternDelReq lanternDelReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            countmoneyService.delCountmoney(busUser, lanternDelReq);
            return ResponseDTO.createBySuccessMessage("删除疯狂数钱活动成功");
        } catch (CountmoneyException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取分页获取中奖记录列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = LanternGetWinningRes.class),
    })
    @ApiOperation(value = "分页获取分页获取中奖记录列表", notes = "分页获取分页获取中奖记录列表")
    @RequestMapping(value = "/getWinningList", method = RequestMethod.POST)
    protected ResponseDTO getWinningList(@RequestBody @ApiParam(value = "分页获取分页获取中奖记录列表对象") CountmoneyGetWinningReq countmoneyGetWinningReq, BindingResult bindingResult,
                                         HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(countmoneyGetWinningReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<CountmoneyGetWinningRes>> responseDTO = countmoneyService.getWinningList(busUser, countmoneyGetWinningReq);
            return responseDTO;
        } catch (CountmoneyException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
}