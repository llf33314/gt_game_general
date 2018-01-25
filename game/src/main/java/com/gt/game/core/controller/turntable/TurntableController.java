package com.gt.game.core.controller.turntable;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.eggs.res.EggsCountActivityRes;
import com.gt.game.core.bean.eggs.res.EggsListRes;
import com.gt.game.core.bean.ninelattice.res.NinelatticeGetActivityRes;
import com.gt.game.core.bean.scratch.req.*;
import com.gt.game.core.bean.scratch.req.ScratchDelWinningReq;
import com.gt.game.core.bean.scratch.res.*;
import com.gt.game.core.bean.tree.res.TreeGetWinningRes;
import com.gt.game.core.bean.turntable.req.*;
import com.gt.game.core.bean.turntable.res.TurntableCountActivityRes;
import com.gt.game.core.bean.turntable.res.TurntableGetActivityRes;
import com.gt.game.core.bean.turntable.res.TurntableListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.scratch.ScratchException;
import com.gt.game.core.exception.turntable.TurntableException;
import com.gt.game.core.service.turntable.TurntableService;
import com.gt.game.core.util.CommonUtil;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 大转盘  前端控制器
 * </p>
 *
 * @author cf
 * @since 2017-12-25
 */
@Api(value = "/app/turntable", description = "大转盘商家后台")
@RestController
@RequestMapping(value = "/app/turntable")
public class TurntableController extends BaseController {

    @Autowired
    TurntableService turntableService;


    //TODO  获取手机端链接
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "获取手机端链接", notes = "获取手机端链接")
    @RequestMapping(value = "/getMobileUrl", method = RequestMethod.POST)
    @Override
    protected ResponseDTO getMobileUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            MobileUrlRes mobileUrlRes = turntableService.getMobileUrl(loginPbUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (TurntableException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取大转盘活动列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = EggsListRes.class),
    })
    @ApiOperation(value = "分页获取大转盘活动列表", notes = "分页获取大转盘活动列表")
    @RequestMapping(value = "/getTurntableList", method = RequestMethod.POST)
    protected ResponseDTO getTurntableList(@RequestBody @ApiParam(value = "请求对象") TurntableListReq turntableListReq, BindingResult bindingResult,
                                           HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(turntableListReq.toString());
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO<List<TurntableListRes>> responseDTO = turntableService.getTurntableList(loginPbUser, turntableListReq);
            return responseDTO;
        } catch (TurntableException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

   // TODO    统计大转盘活动总数
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = TurntableCountActivityRes.class),
    })
    @ApiOperation(value = "统计大转盘活动总数", notes = "统计大转盘活动总数")
    @RequestMapping(value = "/countTurntable", method = RequestMethod.POST)
    public ResponseDTO countTurntable(@RequestBody @ApiParam(value = "请求对象") TurntableCountActivityReq turntableCountActivityReq, BindingResult bindingResult,
                                      HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(turntableCountActivityReq.toString());
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            TurntableCountActivityRes turntableCountActivityRes = turntableService.countTurntable(loginPbUser, turntableCountActivityReq);
            return ResponseDTO.createBySuccess("统计大转盘活动总数成功", turntableCountActivityRes);
        }catch (TurntableException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  新增刮刮乐活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "新增大转盘活动", notes = "新增大转盘活动")
    @RequestMapping(value = "/addScratch", method = RequestMethod.POST)
    protected ResponseDTO addScratch(@RequestBody @ApiParam(value = "请求对象") TurntableAddReq turntableAddReq, BindingResult bindingResult,
                                     HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(turntableAddReq.toString());
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            BusUser busUser = CommonUtil.getLoginUser(request);
            turntableService.addScratch(busUser,loginPbUser, turntableAddReq);
            return ResponseDTO.createBySuccessMessage("新增大转盘活动成功");
        } catch (TurntableException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO    通过活动id查询大转盘活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = TurntableGetActivityRes.class),
    })
    @ApiOperation(value = "通过活动id查询大转盘活动", notes = "通过活动id查询大转盘活动")
    @RequestMapping(value = "/getActivityById", method = RequestMethod.POST)
    public ResponseDTO getActivityById(@RequestBody @ApiParam(value = "请求对象") TurntableGetActivityReq turntableGetActivityReq, BindingResult bindingResult,
                                       HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(turntableGetActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            TurntableGetActivityRes turntableGetActivityRes = turntableService.getActivityById(busUser, turntableGetActivityReq);
            return ResponseDTO.createBySuccess("通过活动id查询大转盘活动成功", turntableGetActivityRes);
        }catch (TurntableException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑大转盘活动设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑大转盘活动设置", notes = "编辑大转盘活动设置")
    @RequestMapping(value = "/modfiyTurntable", method = RequestMethod.POST)
    protected ResponseDTO modfiyTurntable(@RequestBody @ApiParam(value = "请求对象") TurntableModfiyReq turntableModfiyReq, BindingResult bindingResult,
                                        HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(turntableModfiyReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            turntableService.modfiyTurntable(busUser,turntableModfiyReq);
            return ResponseDTO.createBySuccessMessage("编辑大转盘活动设置成功");
        } catch (TurntableException e){
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
    @ApiOperation(value = "大转盘活动暂停/开始活动", notes = "大转盘活动暂停/开始活动")
    @RequestMapping(value = "/stopTurntable", method = RequestMethod.POST)
    protected ResponseDTO stopTurntable(
            @RequestBody @ApiParam("请求参数") TurntableStopIdReq turntableStopIdReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO responseDTO = turntableService.stopTurntable(busUser, turntableStopIdReq);
            return responseDTO;
        } catch (TurntableException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    /*@ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "删除刮刮乐活动", notes = "删除刮刮乐活动")
    @RequestMapping(value = "/delScratch", method = RequestMethod.POST)
    protected ResponseDTO delScratch(
            @RequestBody @ApiParam("请求参数") ScratchDelReq scratchDelReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            scratchService.delScratch(busUser, scratchDelReq);
            return ResponseDTO.createBySuccessMessage("删除刮刮乐活动成功");
        } catch (ScratchException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取刮刮乐中奖记录列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = ScratchGetWinningRes.class),
    })
    @ApiOperation(value = "分页获取刮刮乐中奖记录列表", notes = "分页获取刮刮乐中奖记录列表")
    @RequestMapping(value = "/getWinningList", method = RequestMethod.POST)
    protected ResponseDTO getWinningList(@RequestBody @ApiParam(value = "请求对象") ScratchGetWinningReq scratchGetWinningReq, BindingResult bindingResult,
                                         HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(scratchGetWinningReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<ScratchGetWinningRes>> responseDTO = scratchService.getWinningList(busUser, scratchGetWinningReq);
            return responseDTO;
        } catch (ScratchException e){
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
    @ApiOperation(value = "中奖记录发放奖品", notes = "中奖记录发放奖品")
    @RequestMapping(value = "/editScratchApply", method = RequestMethod.POST)
    protected ResponseDTO editScratchApply(
            @RequestBody @ApiParam("请求参数") ScratchEditApplyReq scratchEditApplyReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = scratchService.editScratchApply(busUser, scratchEditApplyReq);
            return responseDTO;
        } catch (ScratchException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

   *//**
     * 导出刮刮乐活动中奖记录
     * @param request
     * @param response
     *//*
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "导出中奖记录", notes = "导出中奖记录")
    @RequestMapping(value = "/exportScratch", method = RequestMethod.GET)
    protected ResponseDTO exportTree(
            @RequestParam @ApiParam("活动id") Integer actId,
            @RequestParam @ApiParam("兑奖状态 -1 全部 1 未兑奖 2 已兑奖 3 已提交") Integer status,
            @RequestParam @ApiParam("奖品类型 -1 全部 1 粉币 2 手机流量 4 实体物品 6 积分 7 优惠劵") Integer type,
            @RequestParam @ApiParam("兑奖码") String snCode,
            HttpServletResponse response, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            Map<String,Object> params = new HashMap<>();
            params.put("actId",actId);
            params.put("status",status);
            params.put("type",type);
            params.put("snCode",snCode);
            Map<String, Object> msg = scratchService.exportScratch(params,busUser);
            if ((boolean) msg.get("result")) {
                HSSFWorkbook wb = (HSSFWorkbook) msg.get("book");
                String filename = msg.get("fileName").toString() + ".xls";
                response.reset();
                // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
                response.addHeader("Content-Disposition",
                        "attachment;filename="
                                + new String(filename.replaceAll(" ", "")
                                .getBytes("utf-8"), "iso8859-1"));
                OutputStream os = new BufferedOutputStream(
                        response.getOutputStream());
                response.setContentType("application/octet-stream");
                wb.write(os);// 输出文件
                os.flush();
                os.close();
            }
            return ResponseDTO.createBySuccess("导出成功");
        } catch (ScratchException e){
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
    @ApiOperation(value = "批量删除刮刮乐活动中奖记录", notes = "批量删除刮刮乐活动中奖记录")
    @RequestMapping(value = "/delScratchWinning", method = RequestMethod.POST)
    protected ResponseDTO delScratchWinning(
            @RequestBody @ApiParam("请求参数") ScratchDelWinningReq scratchDelWinningReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            scratchService.delScratchWinning(busUser, scratchDelWinningReq);
            return ResponseDTO.createBySuccessMessage("批量删除刮刮乐活动中奖记录成功");
        } catch (ScratchException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = ScratchPrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表", notes = "获取奖品类型列表")
    @RequestMapping(value = "/getScratchPrizeType", method = RequestMethod.POST)
    protected ResponseDTO getScratchPrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<ScratchPrizeTypeListRes>> responseDTO = scratchService.getScratchPrizeType(busUser);
            return responseDTO;
        } catch (ScratchException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }*/
}