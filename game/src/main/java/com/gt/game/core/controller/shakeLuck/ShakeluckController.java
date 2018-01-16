package com.gt.game.core.controller.shakeLuck;


import com.gt.api.bean.session.BusUser;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.shakeLuck.req.*;
import com.gt.game.core.bean.shakeLuck.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.shakeLuck.ShakeluckException;
import com.gt.game.core.service.shakeLuck.ShakeluckService;
import com.gt.game.core.util.CommonUtil;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 摇手气 前端控制器
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Api(value = "/app/shakeluck", description = "摇手气商家后台")
@RestController
@RequestMapping("/app/shakeluck")
public class ShakeluckController  extends BaseController {

    @Autowired
    ShakeluckService shakeluckService;

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "获取手机端链接", notes = "获取手机端链接")
    @RequestMapping(value = "/getMobileUrl", method = RequestMethod.POST)
    @Override
    protected ResponseDTO getMobileUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            MobileUrlRes mobileUrlRes = shakeluckService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (ShakeluckException e){
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
            ResponseDTO<MobileUrlRes> mobileUrlRes = shakeluckService.getAuthorityUrl(busUser, mobileUrlReq);
            return mobileUrlRes;
        } catch (ShakeluckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = ShakeluckCountRes.class),
    })
    @ApiOperation(value = "获取活动数量", notes = "获取活动数量")
    @RequestMapping(value = "/getShakeluckCount", method = RequestMethod.POST)
    protected ResponseDTO getShakeluckCount(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<ShakeluckCountRes> responseDTO = shakeluckService.getShakeluckCount(busUser);
            return responseDTO;
        } catch (ShakeluckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = ShakeluckListRes.class),
    })
    @ApiOperation(value = "分页获取活动列表", notes = "分页获取活动列表")
    @RequestMapping(value = "/getShakeluckList", method = RequestMethod.POST)
    protected ResponseDTO getShakeluckList(
            @RequestBody @ApiParam("请求参数") ShakeluckListPageReq ShakeluckListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<ShakeluckListRes>> responseDTO = shakeluckService.getShakeluckList(busUser, ShakeluckListPageReq);
            return responseDTO;
        } catch (ShakeluckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = ShakeluckApplyListRes.class),
    })
    @ApiOperation(value = "分页获取中奖记录列表", notes = "分页获取中奖记录列表")
    @RequestMapping(value = "/getShakeluckApplyList", method = RequestMethod.POST)
    protected ResponseDTO getShakeluckApplyList(
            @RequestBody @ApiParam("请求参数") ShakeluckApplyListPageReq ShakeluckApplyListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<ShakeluckApplyListRes>> responseDTO = shakeluckService.getShakeluckApplyList(busUser, ShakeluckApplyListPageReq);
            return responseDTO;
        } catch (ShakeluckException e){
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
    @RequestMapping(value = "/editShakeluckApply", method = RequestMethod.POST)
    protected ResponseDTO editShakeluckApply(
            @RequestBody @ApiParam("请求参数") ShakeluckApplyIdReq ShakeluckApplyIdReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = shakeluckService.editShakeluckApply(busUser, ShakeluckApplyIdReq);
            return responseDTO;
        } catch (ShakeluckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = ShakeluckRes.class),
    })
    @ApiOperation(value = "获取活动", notes = "获取活动")
    @RequestMapping(value = "/getShakeluck", method = RequestMethod.GET)
    protected ResponseDTO getShakeluck(
            @RequestParam @ApiParam("id") Integer id,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<ShakeluckRes> responseDTO = shakeluckService.getShakeluck(busUser, id);
            return responseDTO;
        } catch (ShakeluckException e){
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
    @RequestMapping(value = "/removeShakeluck", method = RequestMethod.POST)
    protected ResponseDTO removeShakeluck( @RequestBody @ApiParam("请求参数") ShakeluckIdReq ShakeluckIdReq,
                                           HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = shakeluckService.removeShakeluck(busUser, ShakeluckIdReq);
            return responseDTO;
        } catch (ShakeluckException e){
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
    @RequestMapping(value = "/saveShakeluck", method = RequestMethod.POST)
    protected ResponseDTO getShakeluck(
            @RequestBody @ApiParam("请求参数") ShakeluckSaveReq ShakeluckSaveReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = shakeluckService.saveShakeluck(busUser, ShakeluckSaveReq);
            return responseDTO;
        } catch (ShakeluckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = ShakeluckAuthorityListRes.class),
    })
    @ApiOperation(value = "分页获取核销授权列表", notes = "分页获取核销授权列表")
    @RequestMapping(value = "/getShakeluckAuthorityList", method = RequestMethod.POST)
    protected ResponseDTO getShakeluckAuthorityList(
            @RequestBody @ApiParam("请求参数") ShakeluckAuthorityListPageReq ShakeluckAuthorityListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<ShakeluckAuthorityListRes>> responseDTO = shakeluckService.getShakeluckAuthorityList(busUser, ShakeluckAuthorityListPageReq);
            return responseDTO;
        } catch (ShakeluckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = ShakeluckPrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表", notes = "获取奖品类型列表")
    @RequestMapping(value = "/getShakeluckPrizeType", method = RequestMethod.POST)
    protected ResponseDTO getShakeluckPrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<ShakeluckPrizeTypeListRes>> responseDTO = shakeluckService.getShakeluckPrizeType(busUser);
            return responseDTO;
        } catch (ShakeluckException e){
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
    @RequestMapping(value = "/removeShakeluckAuthority", method = RequestMethod.POST)
    protected ResponseDTO removeShakeluckAuthority(
            @RequestBody @ApiParam("请求参数") ShakeluckAuthorityIdsReq ShakeluckAuthorityIdsReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = shakeluckService.removeShakeluckAuthority(busUser, ShakeluckAuthorityIdsReq);
            return responseDTO;
        } catch (ShakeluckException e){
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
            Map<String, Object> msg = shakeluckService.exports(params);
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
        } catch (ShakeluckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

}
