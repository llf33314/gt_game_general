package com.gt.game.core.controller.stand;


import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.stand.req.*;
import com.gt.game.core.bean.stand.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.stand.StandException;
import com.gt.game.core.service.stand.StandService;
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
 * 一站到底 前端控制器
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Api(value = "/app/stand", description = "一站到底商家后台")
@RestController
@RequestMapping(value = "/app/stand")
public class StandController extends BaseController {

    @Autowired
    StandService standService;

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "获取手机端链接", notes = "获取手机端链接")
    @RequestMapping(value = "/getMobileUrl", method = RequestMethod.POST)
    @Override
    protected ResponseDTO getMobileUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            MobileUrlRes mobileUrlRes = standService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (StandException e){
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
            ResponseDTO<MobileUrlRes> mobileUrlRes = standService.getAuthorityUrl(busUser, mobileUrlReq);
            return mobileUrlRes;
        } catch (StandException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = StandCountRes.class),
    })
    @ApiOperation(value = "获取活动数量", notes = "获取活动数量")
    @RequestMapping(value = "/getStandCount", method = RequestMethod.POST)
    protected ResponseDTO getStandCount(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<StandCountRes> responseDTO = standService.getStandCount(busUser);
            return responseDTO;
        } catch (StandException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = StandListRes.class),
    })
    @ApiOperation(value = "分页获取活动列表", notes = "分页获取活动列表")
    @RequestMapping(value = "/getStandList", method = RequestMethod.POST)
    protected ResponseDTO getStandList(
            @RequestBody @ApiParam("请求参数") StandListPageReq StandListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<StandListRes>> responseDTO = standService.getStandList(busUser, StandListPageReq);
            return responseDTO;
        } catch (StandException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = StandApplyListRes.class),
    })
    @ApiOperation(value = "分页获取中奖记录列表", notes = "分页获取中奖记录列表")
    @RequestMapping(value = "/getStandApplyList", method = RequestMethod.POST)
    protected ResponseDTO getStandApplyList(
            @RequestBody @ApiParam("请求参数") StandApplyListPageReq standApplyListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<StandApplyListRes>> responseDTO = standService.getStandApplyList(busUser, standApplyListPageReq);
            return responseDTO;
        } catch (StandException e){
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
    @RequestMapping(value = "/editStandApply", method = RequestMethod.POST)
    protected ResponseDTO editStandApply(
            @RequestBody @ApiParam("请求参数") StandApplyIdReq standApplyIdReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = standService.editStandApply(busUser, standApplyIdReq);
            return responseDTO;
        } catch (StandException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = StandRes.class),
    })
    @ApiOperation(value = "获取活动", notes = "获取活动")
    @RequestMapping(value = "/getStand", method = RequestMethod.GET)
    protected ResponseDTO getStand(
            @RequestParam @ApiParam("id") Integer id,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<StandRes> responseDTO = standService.getStand(busUser, id);
            return responseDTO;
        } catch (StandException e){
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
    @RequestMapping(value = "/removeStand", method = RequestMethod.POST)
    protected ResponseDTO removeStand( @RequestBody @ApiParam("请求参数") StandIdReq StandIdReq,
                                            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = standService.removeStand(busUser, StandIdReq);
            return responseDTO;
        } catch (StandException e){
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
    @RequestMapping(value = "/saveStand", method = RequestMethod.POST)
    protected ResponseDTO getStand(
            @RequestBody @ApiParam("请求参数") StandSaveReq standSaveReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = standService.saveStand(busUser, standSaveReq);
            return responseDTO;
        } catch (StandException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = StandAuthorityListRes.class),
    })
    @ApiOperation(value = "分页获取核销授权列表", notes = "分页获取核销授权列表")
    @RequestMapping(value = "/getStandAuthorityList", method = RequestMethod.POST)
    protected ResponseDTO getStandAuthorityList(
            @RequestBody @ApiParam("请求参数") StandAuthorityListPageReq StandAuthorityListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<StandAuthorityListRes>> responseDTO = standService.getStandAuthorityList(busUser, StandAuthorityListPageReq);
            return responseDTO;
        } catch (StandException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = StandPrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表", notes = "获取奖品类型列表")
    @RequestMapping(value = "/getStandPrizeType", method = RequestMethod.POST)
    protected ResponseDTO getStandPrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<StandPrizeTypeListRes>> responseDTO = standService.getStandPrizeType(busUser);
            return responseDTO;
        } catch (StandException e){
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
    @RequestMapping(value = "/removeStandAuthority", method = RequestMethod.POST)
    protected ResponseDTO removeStandAuthority(
            @RequestBody @ApiParam("请求参数") StandAuthorityIdsReq standAuthorityIdsReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = standService.removeStandAuthority(busUser, standAuthorityIdsReq);
            return responseDTO;
        } catch (StandException e){
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
            Map<String, Object> msg = standService.exports(params);
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
        } catch (StandException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = StandJoinRecordListRes.class),
    })
    @ApiOperation(value = "获取用户信息列表", notes = "获取用户信息列表")
    @RequestMapping(value = "/getStandJoinRecord", method = RequestMethod.POST)
    protected ResponseDTO getStandJoinRecord( @RequestBody @ApiParam("请求参数") StandIdReq StandIdReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<StandJoinRecordListRes>> responseDTO = standService.getStandJoinRecord(busUser,StandIdReq);
            return responseDTO;
        } catch (StandException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = StandJoinDetailListRes.class),
    })
    @ApiOperation(value = "获取答题记录列表", notes = "获取答题记录列表")
    @RequestMapping(value = "/getStandJoinDetail", method = RequestMethod.POST)
    protected ResponseDTO getStandJoinDetail( @RequestBody @ApiParam("请求参数") StandRecordIdReq standRecordIdReq,
                                              HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<StandJoinDetailListRes>> responseDTO = standService.getStandJoinDetail(busUser,standRecordIdReq);
            return responseDTO;
        } catch (StandException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }


}
