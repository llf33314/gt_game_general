package com.gt.game.core.controller.eggs;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.eggs.req.*;
import com.gt.game.core.bean.eggs.res.*;
import com.gt.game.core.bean.lantern.res.LanternPrizeTypeListRes;
import com.gt.game.core.bean.ninelattice.res.NinelatticeGetActivityRes;
import com.gt.game.core.bean.scratch.req.ScratchStopIdReq;
import com.gt.game.core.bean.tree.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.eggs.EggsException;
import com.gt.game.core.exception.scratch.ScratchException;
import com.gt.game.core.exception.tree.TreeException;
import com.gt.game.core.service.eggs.EggsService;
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
     * 砸金蛋 前端控制器
     * </p>
     *
     * @author zwq
     * @since 2017-12-25
     */
    @Api(value = "/app/eggs", description = "砸金蛋商家后台")
    @RestController
    @RequestMapping(value = "/app/eggs")
    public class EggsController extends BaseController {

        @Autowired
        EggsService eggsService;


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
                MobileUrlRes mobileUrlRes = eggsService.getMobileUrl(loginPbUser, mobileUrlReq);
                return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
            } catch (EggsException e){
                logger.error(e.getMessage(), e.fillInStackTrace());
                return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
            } catch (Exception e){
                e.printStackTrace();
                return ResponseDTO.createByError();
            }
        }


    // TODO  分页获取砸金蛋活动列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = EggsListRes.class),
    })
    @ApiOperation(value = "分页获取砸金蛋活动列表", notes = "分页获取砸金蛋活动列表")
    @RequestMapping(value = "/getEggsList", method = RequestMethod.POST)
    protected ResponseDTO getEggsList(@RequestBody @ApiParam(value = "请求对象") EggsListReq eggsListReq, BindingResult bindingResult,
                                      HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(eggsListReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<EggsListRes>> responseDTO = eggsService.getEggsList(busUser, eggsListReq,request);
            return responseDTO;
        } catch (EggsException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

   // TODO    统计砸金蛋活动总数
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = EggsCountActivityRes.class),
    })
    @ApiOperation(value = "统计砸金蛋活动总数", notes = "统计砸金蛋活动总数")
    @RequestMapping(value = "/countEggs", method = RequestMethod.POST)
    public ResponseDTO countEggs(@RequestBody @ApiParam(value = "请求对象") EggsCountActivityReq eggsCountActivityReq, BindingResult bindingResult,
                                 HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(eggsCountActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            EggsCountActivityRes eggsCountActivityRes = eggsService.countEggs(busUser, eggsCountActivityReq,request);
            return ResponseDTO.createBySuccess("统计砸金蛋活动总数成功", eggsCountActivityRes);
        }catch (EggsException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  新增圣诞大礼包活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "新增砸金蛋活动", notes = "新增砸金蛋活动")
    @RequestMapping(value = "/addEggs", method = RequestMethod.POST)
    protected ResponseDTO addEggs(@RequestBody @ApiParam(value = "请求对象") EggsAddReq eggsAddReq, BindingResult bindingResult,
                                  HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(eggsAddReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            eggsService.addEggs(busUser, eggsAddReq,request);
            return ResponseDTO.createBySuccessMessage("新增砸金蛋活动成功");
        } catch (EggsException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

     // TODO    通过活动id查询砸金蛋活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = NinelatticeGetActivityRes.class),
    })
    @ApiOperation(value = "通过活动id查询砸金蛋活动", notes = "通过活动id查询砸金蛋活动")
    @RequestMapping(value = "/getActivityById", method = RequestMethod.POST)
    public ResponseDTO getActivityById(@RequestBody @ApiParam(value = "请求对象") EggsGetActivityReq eggsGetActivityReq, BindingResult bindingResult,
                                       HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(eggsGetActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            EggsGetActivityRes eggsGetActivityRes = eggsService.getActivityById(busUser, eggsGetActivityReq);
            return ResponseDTO.createBySuccess("通过活动id查询砸金蛋活动成功", eggsGetActivityRes);
        }catch (EggsException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑砸金蛋活动设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑砸金蛋活动设置", notes = "编辑砸金蛋活动设置")
    @RequestMapping(value = "/modfiyEggs", method = RequestMethod.POST)
    protected ResponseDTO modfiyEggs(@RequestBody @ApiParam(value = "请求对象") EggsModfiyReq eggsModfiyReq, BindingResult bindingResult,
                                     HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(eggsModfiyReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            eggsService.modfiyEggs(busUser,eggsModfiyReq);
            return ResponseDTO.createBySuccessMessage("编辑砸金蛋活动设置成功");
        } catch (EggsException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  开始/暂停砸金蛋活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "开始/暂停砸金蛋活动", notes = "开始/暂停砸金蛋活动")
    @RequestMapping(value = "/stopEggs", method = RequestMethod.POST)
    protected ResponseDTO stopEggs(
            @RequestBody @ApiParam("请求参数") EggsStopIdReq eggsStopIdReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO responseDTO = eggsService.stopEggs(loginPbUser, eggsStopIdReq);
            return responseDTO;
        } catch (EggsException e){
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
    @ApiOperation(value = "删除砸金蛋活动", notes = "删除砸金蛋活动")
    @RequestMapping(value = "/delEggs", method = RequestMethod.POST)
    protected ResponseDTO delEggs(
            @RequestBody @ApiParam("请求参数") EggsDelReq eggsDelReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            eggsService.delEggs(busUser, eggsDelReq);
            return ResponseDTO.createBySuccessMessage("删除砸金蛋活动成功");
        } catch (EggsException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取砸金蛋中奖记录列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = TreeGetWinningRes.class),
    })
    @ApiOperation(value = "分页获取砸金蛋中奖记录列表", notes = "分页获取砸金蛋中奖记录列表")
    @RequestMapping(value = "/getWinningList", method = RequestMethod.POST)
    protected ResponseDTO getWinningList(@RequestBody @ApiParam(value = "请求对象") EggsGetWinningReq eggsGetWinningReq, BindingResult bindingResult,
                                         HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(eggsGetWinningReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<EggsGetWinningRes>> responseDTO = eggsService.getWinningList(busUser, eggsGetWinningReq);
            return responseDTO;
        } catch (EggsException e){
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
   @RequestMapping(value = "/editEggsApply", method = RequestMethod.POST)
   protected ResponseDTO editEggsApply(
            @RequestBody @ApiParam("请求参数") EggsEditApplyReq eggsEditApplyReq,
            HttpServletRequest request) {
       try {
           BusUser busUser = CommonUtil.getLoginUser(request);
           ResponseDTO responseDTO = eggsService.editEggsApply(busUser, eggsEditApplyReq);
           return responseDTO;
       } catch (EggsException e){
           logger.error(e.getMessage(), e.fillInStackTrace());
           return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
       } catch (Exception e){
           e.printStackTrace();
           return ResponseDTO.createByError();
       }
   }

   /**
    * 导出圣诞大礼包中奖记录
    * @param request
    * @param response
    */
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "导出中奖记录", notes = "导出中奖记录")
    @RequestMapping(value = "/exportTree", method = RequestMethod.GET)
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
            Map<String, Object> msg = eggsService.exportTree(params,busUser);
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
        } catch (TreeException e){
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
    @ApiOperation(value = "批量删除砸金蛋中奖记录", notes = "批量删除砸金蛋中奖记录")
    @RequestMapping(value = "/delEggsWinning", method = RequestMethod.POST)
    protected ResponseDTO delEggsWinning(
            @RequestBody @ApiParam("请求参数") EggsDelWinningReq eggsDelWinningReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            eggsService.delEggsWinning(busUser, eggsDelWinningReq);
            return ResponseDTO.createBySuccessMessage("批量删除砸金蛋中奖记录");
        } catch (EggsException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = LanternPrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表", notes = "获取奖品类型列表")
    @RequestMapping(value = "/getEggsPrizeType", method = RequestMethod.POST)
    protected ResponseDTO getEggsPrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<EggsPrizeTypeListRes>> responseDTO = eggsService.getEggsPrizeType(busUser);
            return responseDTO;
        } catch (TreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
}