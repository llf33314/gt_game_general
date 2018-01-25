package com.gt.game.core.controller.goldRush;


import com.gt.api.bean.session.BusUser;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.goldRush.req.*;
import com.gt.game.core.bean.goldRush.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.goldRush.GoldRushException;
import com.gt.game.core.service.goldRush.GoldRushService;
import com.gt.game.core.util.CommonUtil;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 欢乐抢元宝 前端控制器
 * </p>
 *
 * @author zwq
 * @since 2018-01-09
 */
@Api(value = "/app/goldRush", description = "欢乐抢元宝商家后台")
@RestController
@RequestMapping(value = "/app/goldRush")
public class GoldRushController  extends BaseController {

    @Autowired
    GoldRushService goldRushService;
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "获取手机端链接", notes = "获取手机端链接")
    @RequestMapping(value = "/getMobileUrl", method = RequestMethod.POST)
    @Override
    protected ResponseDTO getMobileUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            MobileUrlRes mobileUrlRes = goldRushService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (GoldRushException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = MobileUrlRes.class),
    })
    @ApiOperation(value = "获取新增授权链接", notes = "获取新增授权链接")
    @RequestMapping(value = "/getAuthorityUrl", method = RequestMethod.POST)
    protected ResponseDTO getAuthorityUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<MobileUrlRes> mobileUrlRes = goldRushService.getAuthorityUrl(busUser, mobileUrlReq);
            return mobileUrlRes;
        } catch (GoldRushException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldRushCountRes.class),
    })
    @ApiOperation(value = "获取活动数量", notes = "获取活动数量")
    @RequestMapping(value = "/getGoldRushCount", method = RequestMethod.POST)
    protected ResponseDTO getGoldRushCount(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<GoldRushCountRes> responseDTO = goldRushService.getGoldRushCount(busUser);
            return responseDTO;
        } catch (GoldRushException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldRushListRes.class),
    })
    @ApiOperation(value = "分页获取活动列表", notes = "分页获取活动列表")
    @RequestMapping(value = "/getGoldRushList", method = RequestMethod.POST)
    protected ResponseDTO getGoldRushList(
            @RequestBody @ApiParam("请求参数") GoldRushListPageReq GoldRushListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<GoldRushListRes>> responseDTO = goldRushService.getGoldRushList(busUser, GoldRushListPageReq);
            return responseDTO;
        } catch (GoldRushException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldRushApplyListRes.class),
    })
    @ApiOperation(value = "分页获取中奖记录列表", notes = "分页获取中奖记录列表")
    @RequestMapping(value = "/getGoldRushApplyList", method = RequestMethod.POST)
    protected ResponseDTO getGoldRushApplyList(
            @RequestBody @ApiParam("请求参数") GoldRushApplyListPageReq GoldRushApplyListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<GoldRushApplyListRes>> responseDTO = goldRushService.getGoldRushApplyList(busUser, GoldRushApplyListPageReq);
            return responseDTO;
        } catch (GoldRushException e){
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
    @RequestMapping(value = "/editGoldRushApply", method = RequestMethod.POST)
    protected ResponseDTO editGoldRushApply(
            @RequestBody @ApiParam("请求参数") GoldRushApplyIdReq GoldRushApplyIdReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = goldRushService.editGoldRushApply(busUser, GoldRushApplyIdReq);
            return responseDTO;
        } catch (GoldRushException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldRushRes.class),
    })
    @ApiOperation(value = "获取活动", notes = "获取活动")
    @RequestMapping(value = "/getGoldRush", method = RequestMethod.GET)
    protected ResponseDTO getGoldRush(
            @RequestParam @ApiParam("id") Integer id,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<GoldRushRes> responseDTO = goldRushService.getGoldRush(busUser, id);
            return responseDTO;
        } catch (GoldRushException e){
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
    @RequestMapping(value = "/removeGoldRush", method = RequestMethod.POST)
    protected ResponseDTO removeGoldRush( @RequestBody @ApiParam("请求参数") GoldRushIdReq GoldRushIdReq,
                                           HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = goldRushService.removeGoldRush(busUser, GoldRushIdReq);
            return responseDTO;
        } catch (GoldRushException e){
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
    @RequestMapping(value = "/saveGoldRush", method = RequestMethod.POST)
    protected ResponseDTO getGoldRush(
            @RequestBody @ApiParam("请求参数") GoldRushSaveReq GoldRushSaveReq,BindingResult result ,
            HttpServletRequest request) {
        InvalidParameter(result);
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = goldRushService.saveGoldRush(busUser, GoldRushSaveReq);
            return responseDTO;
        } catch (GoldRushException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldRushAuthorityListRes.class),
    })
    @ApiOperation(value = "分页获取核销授权列表", notes = "分页获取核销授权列表")
    @RequestMapping(value = "/getGoldRushAuthorityList", method = RequestMethod.POST)
    protected ResponseDTO getGoldRushAuthorityList(
            @RequestBody @ApiParam("请求参数") GoldRushAuthorityListPageReq GoldRushAuthorityListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<GoldRushAuthorityListRes>> responseDTO = goldRushService.getGoldRushAuthorityList(busUser, GoldRushAuthorityListPageReq);
            return responseDTO;
        } catch (GoldRushException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }


    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldRushPrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表", notes = "获取奖品类型列表")
    @RequestMapping(value = "/getGoldRushPrizeType", method = RequestMethod.POST)
    protected ResponseDTO getGoldRushPrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<GoldRushPrizeTypeListRes>> responseDTO = goldRushService.getGoldRushPrizeType(busUser);
            return responseDTO;
        } catch (GoldRushException e){
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
    @ApiOperation(value = "删除核销授权", notes = "删除核销授权")
    @RequestMapping(value = "/removeGoldRushAuthority", method = RequestMethod.POST)
    protected ResponseDTO removeGoldRushAuthority(
            @RequestBody @ApiParam("请求参数") GoldRushAuthorityIdsReq GoldRushAuthorityIdsReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = goldRushService.removeGoldRushAuthority(busUser, GoldRushAuthorityIdsReq);
            return responseDTO;
        } catch (GoldRushException e){
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
    @ApiOperation(value = "导出中奖记录", notes = "导出中奖记录")
    @RequestMapping(value = "/exports", method = RequestMethod.GET)
    protected ResponseDTO exports(
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
            Map<String, Object> msg = goldRushService.exports(params);
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
        } catch (GoldRushException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
}
