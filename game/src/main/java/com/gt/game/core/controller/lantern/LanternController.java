package com.gt.game.core.controller.lantern;


import com.gt.api.exception.SignException;
import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.demolition.res.DemolitionListRes;
import com.gt.game.core.bean.lantern.req.*;
import com.gt.game.core.bean.lantern.res.LanternCountActivityRes;
import com.gt.game.core.bean.lantern.res.LanternListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.demolition.lantern.LanternException;
import com.gt.game.core.service.lantern.LanternService;
import com.gt.game.core.util.CommonUtil;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 元宵点灯  前端控制器
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Api(value = "/app/lantern", description = "元宵点灯商家后台")
@RestController
@RequestMapping(value = "/app/lantern")
public class LanternController extends BaseController {

    @Autowired
    LanternService lanternService;


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
            MobileUrlRes mobileUrlRes = lanternService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取元宵点灯活动列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = LanternListRes.class),
    })
    @ApiOperation(value = "分页获取元宵点灯活动列表", notes = "分页获取元宵点灯活动列表")
    @RequestMapping(value = "/getLanternList", method = RequestMethod.POST)
    protected ResponseDTO getLanternList(@RequestBody @ApiParam(value = "分页获取元宵点灯活动列表对象") LanternListReq lanternListReq,BindingResult bindingResult,
            HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternListReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<LanternListRes>> responseDTO = lanternService.getLanternList(busUser, lanternListReq);
            return responseDTO;
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO    统计元宵点灯活动总数
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = LanternCountActivityRes.class),
    })
    @ApiOperation(value = "统计元宵点灯活动总数", notes = "统计元宵点灯活动总数")
    @RequestMapping(value = "/countActivity", method = RequestMethod.POST)
    public ResponseDTO countActivity(@RequestBody @ApiParam(value = "统计元宵点灯活动总数对象") LanternCountActivityReq lanternCountActivityReq, BindingResult bindingResult,
                                     HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternCountActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            LanternCountActivityRes lanternCountActivityRes = lanternService.countActivity(busUser, lanternCountActivityReq);
            return ResponseDTO.createBySuccess("统计元宵点灯活动总数成功", lanternCountActivityRes);
        }catch (LanternException e){
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
    @ApiOperation(value = "新增元宵点灯活动", notes = "新增元宵点灯活动")
    @RequestMapping(value = "/addLantern", method = RequestMethod.POST)
    protected ResponseDTO addLantern(@RequestBody @ApiParam(value = "新增元宵点灯活动列表对象") LanternAddReq lanternAddReq, BindingResult bindingResult,
                                     HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternAddReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            lanternService.addLantern(busUser, lanternAddReq);
            return ResponseDTO.createBySuccessMessage("新增元宵点灯活动成功");
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑元宵点灯活动基础设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑元宵点灯活动基础设置", notes = "编辑元宵点灯活动基础设置")
    @RequestMapping(value = "/addLantern", method = RequestMethod.POST)
    protected ResponseDTO modfiyBasicsLantern(@RequestBody @ApiParam(value = "编辑元宵点灯活动基础设置对象") LanternModfiyBasicsReq lanternModfiyBasicsReq, BindingResult bindingResult,
                                              HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternModfiyBasicsReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            lanternService.modfiyBasicsLantern(busUser, lanternModfiyBasicsReq);
            return ResponseDTO.createBySuccessMessage("编辑元宵点灯活动基础设置成功");
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑元宵点灯活动规则设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑元宵点灯活动规则设置", notes = "编辑元宵点灯活动规则设置")
    @RequestMapping(value = "/modfiyRuleLantern", method = RequestMethod.POST)
    protected ResponseDTO modfiyRuleLantern(@RequestBody @ApiParam(value = "编辑元宵点灯活动基础规则对象") LanternModfiyRuleReq lanternModfiyRuleReq, BindingResult bindingResult,
                                            HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternModfiyRuleReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            lanternService.modfiyRuleLantern(busUser, lanternModfiyRuleReq);
            return ResponseDTO.createBySuccessMessage("编辑元宵点灯活动规则设置成功");
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑元宵点灯活动兑奖设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑元宵点灯活动兑奖设置", notes = "编辑元宵点灯活动规则设置")
    @RequestMapping(value = "/modfiyExpiryLantern", method = RequestMethod.POST)
    protected ResponseDTO modfiyExpiryLantern(@RequestBody @ApiParam(value = "编辑元宵点灯活动基础兑奖对象") LanternModfiyExpiryReq lanternModfiyExpiryReq, BindingResult bindingResult,
                                            HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternModfiyExpiryReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            lanternService.modfiyExpiryLantern(busUser, lanternModfiyExpiryReq);
            return ResponseDTO.createBySuccessMessage("编辑元宵点灯活动兑奖设置成功");
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑元宵点灯活动奖项设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑元宵点灯活动奖项设置", notes = "编辑元宵点灯活动奖项设置")
    @RequestMapping(value = "/modfiyAwardsLantern", method = RequestMethod.POST)
    protected ResponseDTO modfiyAwardsLantern(@RequestBody @ApiParam(value = "编辑元宵点灯活动奖项设置对象") LanternModfiyAwardsReq lanternModfiyAwardsReq, BindingResult bindingResult,
                                              HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternModfiyAwardsReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            lanternService.modfiyAwardsLantern(busUser, lanternModfiyAwardsReq);
            return ResponseDTO.createBySuccessMessage("编辑元宵点灯活动奖项设置成功");
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取元宵点灯中奖记录列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = LanternListRes.class),
    })
    @ApiOperation(value = "分页获取元宵点灯中奖记录列表", notes = "分页获取元宵点灯中奖记录列表")
    @RequestMapping(value = "/getWinningList", method = RequestMethod.POST)
    protected ResponseDTO getWinningList(@RequestBody @ApiParam(value = "分页获取元宵点灯中奖记录列表对象") LanternGetWinningReq lanternGetWinningReq,BindingResult bindingResult,
                                         HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternGetWinningReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<LanternListRes>> responseDTO = lanternService.getWinningList(busUser, lanternGetWinningReq);
            return responseDTO;
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
}