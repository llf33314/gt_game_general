package com.gt.game.core.controller.turntable;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.eggs.res.EggsCountActivityRes;
import com.gt.game.core.bean.eggs.res.EggsListRes;
import com.gt.game.core.bean.ninelattice.res.NinelatticeGetActivityRes;
import com.gt.game.core.bean.scratch.req.*;
import com.gt.game.core.bean.scratch.res.*;
import com.gt.game.core.bean.tree.res.TreeGetWinningRes;
import com.gt.game.core.bean.turntable.req.TurntableListReq;
import com.gt.game.core.bean.turntable.res.TurntableListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.scratch.ScratchException;
import com.gt.game.core.exception.turntable.TurnException;
import com.gt.game.core.service.scratch.ScratchService;
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
            BusUser busUser = CommonUtil.getLoginUser(request);
            MobileUrlRes mobileUrlRes = turntableService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (TurnException e){
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
        } catch (TurnException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

   /*// TODO    统计刮刮乐活动总数
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = EggsCountActivityRes.class),
    })
    @ApiOperation(value = "统计刮刮乐活动总数", notes = "统计刮刮乐活动总数")
    @RequestMapping(value = "/countScratch", method = RequestMethod.POST)
    public ResponseDTO countScratch(@RequestBody @ApiParam(value = "请求对象") ScratchCountActivityReq scratchCountActivityReq, BindingResult bindingResult,
                                    HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(scratchCountActivityReq.toString());
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            ScratchCountActivityRes scratchCountActivityRes = scratchService.countScratch(loginPbUser, scratchCountActivityReq);
            return ResponseDTO.createBySuccess("统计刮刮乐活动总数成功", scratchCountActivityRes);
        }catch (ScratchException e){
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
    @ApiOperation(value = "新增刮刮乐活动", notes = "新增刮刮乐活动")
    @RequestMapping(value = "/addScratch", method = RequestMethod.POST)
    protected ResponseDTO addScratch(@RequestBody @ApiParam(value = "请求对象") ScratchAddReq scratchAddReq, BindingResult bindingResult,
                                     HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(scratchAddReq.toString());
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            scratchService.addScratch(loginPbUser, scratchAddReq);
            return ResponseDTO.createBySuccessMessage("新增刮刮乐活动成功");
        } catch (ScratchException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO    通过活动id查询刮刮乐活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = NinelatticeGetActivityRes.class),
    })
    @ApiOperation(value = "通过活动id查询刮刮乐活动", notes = "通过活动id查询刮刮乐活动")
    @RequestMapping(value = "/getActivityById", method = RequestMethod.POST)
    public ResponseDTO getActivityById(@RequestBody @ApiParam(value = "请求对象") ScratchGetActivityReq scratchGetActivityReq, BindingResult bindingResult,
                                       HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(scratchGetActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ScratchGetActivityRes scratchGetActivityRes = scratchService.getActivityById(busUser, scratchGetActivityReq);
            return ResponseDTO.createBySuccess("通过活动id查询刮刮乐活动成功", scratchGetActivityRes);
        }catch (ScratchException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑刮刮乐活动设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑刮刮乐活动设置", notes = "编辑刮刮乐活动设置")
    @RequestMapping(value = "/modfiyScratch", method = RequestMethod.POST)
    protected ResponseDTO modfiyScratch(@RequestBody @ApiParam(value = "请求对象") ScratchModfiyReq scratchModfiyReq, BindingResult bindingResult,
                                        HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(scratchModfiyReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            scratchService.modfiyScratch(busUser,scratchModfiyReq);
            return ResponseDTO.createBySuccessMessage("编辑刮刮乐活动设置成功");
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
    @ApiOperation(value = "刮刮乐开始活动", notes = "刮刮乐开始活动")
    @RequestMapping(value = "/startScratch", method = RequestMethod.POST)
    protected ResponseDTO startScratch(
            @RequestBody @ApiParam("请求参数") ScratchStartReq scratchStartReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            scratchService.startScratch(busUser, scratchStartReq);
            return ResponseDTO.createBySuccessMessage("刮刮乐开始活动成功");
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
    @ApiOperation(value = "刮刮乐暂停活动", notes = "刮刮乐暂停活动")
    @RequestMapping(value = "/stopScratch", method = RequestMethod.POST)
    protected ResponseDTO stopScratch(
            @RequestBody @ApiParam("请求参数") ScratchStopReq scratchStopReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            scratchService.stopScratch(busUser, scratchStopReq);
            return ResponseDTO.createBySuccessMessage("刮刮乐暂停活动成功");
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
    @ApiOperation(value = "批量删除刮刮乐活动", notes = "批量删除刮刮乐活动")
    @RequestMapping(value = "/delScratch", method = RequestMethod.POST)
    protected ResponseDTO delScratch(
            @RequestBody @ApiParam("请求参数") ScratchDelReq scratchDelReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            scratchService.delScratch(busUser, scratchDelReq);
            return ResponseDTO.createBySuccessMessage("批量删除刮刮乐活动成功");
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
            @ApiResponse(code = 2, message = "任务对象", response = TreeGetWinningRes.class),
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