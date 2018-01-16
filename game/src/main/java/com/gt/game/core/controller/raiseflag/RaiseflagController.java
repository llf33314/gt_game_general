package com.gt.game.core.controller.raiseflag;


import com.gt.api.bean.session.BusUser;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.raiseflag.req.*;
import com.gt.game.core.bean.raiseflag.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.raiseflag.RaiseflagException;
import com.gt.game.core.service.raiseflag.RaiseflagService;
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
 * 升国旗 前端控制器
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
@Api(value = "/app/raiseflag", description = "升国旗商家后台")
@RestController
@RequestMapping(value = "/app/raiseflag")
public class RaiseflagController  extends BaseController {

    @Autowired
    RaiseflagService raiseflagService;

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "获取手机端链接", notes = "获取手机端链接")
    @RequestMapping(value = "/getMobileUrl", method = RequestMethod.POST)
    @Override
    protected ResponseDTO getMobileUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            MobileUrlRes mobileUrlRes = raiseflagService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (RaiseflagException e){
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
            ResponseDTO<MobileUrlRes> mobileUrlRes = raiseflagService.getAuthorityUrl(busUser, mobileUrlReq);
            return mobileUrlRes;
        } catch (RaiseflagException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = RaiseflagTemplateRes.class),
    })
    @ApiOperation(value = "获取公众号消息模板列表", notes = "获取公众号消息模板列表")
    @RequestMapping(value = "/getTemplateList", method = RequestMethod.POST)
    public ResponseDTO getTemplateList( HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<RaiseflagTemplateRes>> responseDTO = raiseflagService.getTemplateList(busUser);
            return responseDTO;
        } catch (RaiseflagException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = RaiseflagCountRes.class),
    })
    @ApiOperation(value = "获取活动数量", notes = "获取活动数量")
    @RequestMapping(value = "/getRaiseflagCount", method = RequestMethod.POST)
    protected ResponseDTO getRaiseflagCount(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<RaiseflagCountRes> responseDTO = raiseflagService.getRaiseflagCount(busUser);
            return responseDTO;
        } catch (RaiseflagException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = RaiseflagListRes.class),
    })
    @ApiOperation(value = "分页获取活动列表", notes = "分页获取活动列表")
    @RequestMapping(value = "/getRaiseflagList", method = RequestMethod.POST)
    protected ResponseDTO getRaiseflagList(
            @RequestBody @ApiParam("请求参数") RaiseflagListPageReq RaiseflagListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<RaiseflagListRes>> responseDTO = raiseflagService.getRaiseflagList(busUser, RaiseflagListPageReq);
            return responseDTO;
        } catch (RaiseflagException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = RaiseflagApplyListRes.class),
    })
    @ApiOperation(value = "分页获取中奖记录列表", notes = "分页获取中奖记录列表")
    @RequestMapping(value = "/getRaiseflagApplyList", method = RequestMethod.POST)
    protected ResponseDTO getRaiseflagApplyList(
            @RequestBody @ApiParam("请求参数") RaiseflagApplyListPageReq RaiseflagApplyListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<RaiseflagApplyListRes>> responseDTO = raiseflagService.getRaiseflagApplyList(busUser, RaiseflagApplyListPageReq);
            return responseDTO;
        } catch (RaiseflagException e){
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
    @RequestMapping(value = "/editRaiseflagApply", method = RequestMethod.POST)
    protected ResponseDTO editRaiseflagApply(
            @RequestBody @ApiParam("请求参数") RaiseflagApplyIdReq RaiseflagApplyIdReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = raiseflagService.editRaiseflagApply(busUser, RaiseflagApplyIdReq);
            return responseDTO;
        } catch (RaiseflagException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = RaiseflagRes.class),
    })
    @ApiOperation(value = "获取活动", notes = "获取活动")
    @RequestMapping(value = "/getRaiseflag", method = RequestMethod.GET)
    protected ResponseDTO getRaiseflag(
            @RequestParam @ApiParam("id") Integer id,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<RaiseflagRes> responseDTO = raiseflagService.getRaiseflag(busUser, id);
            return responseDTO;
        } catch (RaiseflagException e){
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
    @RequestMapping(value = "/removeRaiseflag", method = RequestMethod.POST)
    protected ResponseDTO removeRaiseflag( @RequestBody @ApiParam("请求参数") RaiseflagIdReq RaiseflagIdReq,
                                           HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = raiseflagService.removeRaiseflag(busUser, RaiseflagIdReq);
            return responseDTO;
        } catch (RaiseflagException e){
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
    @RequestMapping(value = "/saveRaiseflag", method = RequestMethod.POST)
    protected ResponseDTO getRaiseflag(
            @RequestBody @ApiParam("请求参数") RaiseflagSaveReq RaiseflagSaveReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = raiseflagService.saveRaiseflag(busUser, RaiseflagSaveReq);
            return responseDTO;
        } catch (RaiseflagException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = RaiseflagAuthorityListRes.class),
    })
    @ApiOperation(value = "分页获取核销授权列表", notes = "分页获取核销授权列表")
    @RequestMapping(value = "/getRaiseflagAuthorityList", method = RequestMethod.POST)
    protected ResponseDTO getRaiseflagAuthorityList(
            @RequestBody @ApiParam("请求参数") RaiseflagAuthorityListPageReq RaiseflagAuthorityListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<RaiseflagAuthorityListRes>> responseDTO = raiseflagService.getRaiseflagAuthorityList(busUser, RaiseflagAuthorityListPageReq);
            return responseDTO;
        } catch (RaiseflagException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = RaiseflagPrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表", notes = "获取奖品类型列表")
    @RequestMapping(value = "/getRaiseflagPrizeType", method = RequestMethod.POST)
    protected ResponseDTO getRaiseflagPrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<RaiseflagPrizeTypeListRes>> responseDTO = raiseflagService.getRaiseflagPrizeType(busUser);
            return responseDTO;
        } catch (RaiseflagException e){
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
    @RequestMapping(value = "/removeRaiseflagAuthority", method = RequestMethod.POST)
    protected ResponseDTO removeRaiseflagAuthority(
            @RequestBody @ApiParam("请求参数") RaiseflagAuthorityIdsReq RaiseflagAuthorityIdsReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = raiseflagService.removeRaiseflagAuthority(busUser, RaiseflagAuthorityIdsReq);
            return responseDTO;
        } catch (RaiseflagException e){
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
            Map<String, Object> msg = raiseflagService.exports(params);
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
        } catch (RaiseflagException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
}
