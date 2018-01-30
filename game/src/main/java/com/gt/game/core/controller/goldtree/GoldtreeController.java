package com.gt.game.core.controller.goldtree;


import com.gt.api.bean.session.BusUser;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.goldtree.req.*;
import com.gt.game.core.bean.goldtree.res.*;
import com.gt.game.core.bean.raiseflag.res.RaiseflagTemplateRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.goldtree.GoldtreeException;
import com.gt.game.core.service.goldtree.GoldtreeService;
import com.gt.game.core.util.CommonUtil;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 摇钱树 前端控制器
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Api(value = "/app/goldtree", description = "摇钱树商家后台")
@RestController
@RequestMapping("/app/goldtree")
public class GoldtreeController extends BaseController {

    @Autowired
    GoldtreeService goldtreeService;

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "获取手机端链接", notes = "获取手机端链接")
    @RequestMapping(value = "/getMobileUrl", method = RequestMethod.POST)
    @Override
    protected ResponseDTO getMobileUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            MobileUrlRes mobileUrlRes = goldtreeService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (GoldtreeException e){
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
            ResponseDTO<MobileUrlRes> mobileUrlRes = goldtreeService.getAuthorityUrl(busUser, mobileUrlReq);
            return mobileUrlRes;
        } catch (GoldtreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldtreeCountRes.class),
    })
    @ApiOperation(value = "获取活动数量", notes = "获取活动数量")
    @RequestMapping(value = "/getGoldtreeCount", method = RequestMethod.POST)
    protected ResponseDTO getGoldtreeCount(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<GoldtreeCountRes> responseDTO = goldtreeService.getGoldtreeCount(busUser);
            return responseDTO;
        } catch (GoldtreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldtreeListRes.class),
    })
    @ApiOperation(value = "分页获取活动列表", notes = "分页获取活动列表")
    @RequestMapping(value = "/getGoldtreeList", method = RequestMethod.POST)
    protected ResponseDTO getGoldtreeList(
            @RequestBody @ApiParam("请求参数") GoldtreeListPageReq GoldtreeListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<GoldtreeListRes>> responseDTO = goldtreeService.getGoldtreeList(busUser, GoldtreeListPageReq);
            return responseDTO;
        } catch (GoldtreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldtreeApplyListRes.class),
    })
    @ApiOperation(value = "分页获取中奖记录列表", notes = "分页获取中奖记录列表")
    @RequestMapping(value = "/getGoldtreeApplyList", method = RequestMethod.POST)
    protected ResponseDTO getGoldtreeApplyList(
            @RequestBody @ApiParam("请求参数") GoldtreeApplyListPageReq GoldtreeApplyListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<GoldtreeApplyListRes>> responseDTO = goldtreeService.getGoldtreeApplyList(busUser, GoldtreeApplyListPageReq);
            return responseDTO;
        } catch (GoldtreeException e){
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
    @RequestMapping(value = "/editGoldtreeApply", method = RequestMethod.POST)
    protected ResponseDTO editGoldtreeApply(
            @RequestBody @ApiParam("请求参数") GoldtreeApplyIdReq GoldtreeApplyIdReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = goldtreeService.editGoldtreeApply(busUser, GoldtreeApplyIdReq);
            return responseDTO;
        } catch (GoldtreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldtreeRes.class),
    })
    @ApiOperation(value = "获取活动", notes = "获取活动")
    @RequestMapping(value = "/getGoldtree", method = RequestMethod.GET)
    protected ResponseDTO getGoldtree(
            @RequestParam @ApiParam("id") Integer id,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<GoldtreeRes> responseDTO = goldtreeService.getGoldtree(busUser, id);
            return responseDTO;
        } catch (GoldtreeException e){
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
    @RequestMapping(value = "/removeGoldtree", method = RequestMethod.POST)
    protected ResponseDTO removeGoldtree( @RequestBody @ApiParam("请求参数") GoldtreeIdReq GoldtreeIdReq,
                                           HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = goldtreeService.removeGoldtree(busUser, GoldtreeIdReq);
            return responseDTO;
        } catch (GoldtreeException e){
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
    @RequestMapping(value = "/saveGoldtree", method = RequestMethod.POST)
    protected ResponseDTO getGoldtree(
            @RequestBody @ApiParam("请求参数") @Valid GoldtreeSaveReq GoldtreeSaveReq, BindingResult result ,
            HttpServletRequest request) {
        InvalidParameter(result);
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = goldtreeService.saveGoldtree(busUser, GoldtreeSaveReq);
            return responseDTO;
        } catch (GoldtreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldtreeAuthorityListRes.class),
    })
    @ApiOperation(value = "分页获取核销授权列表", notes = "分页获取核销授权列表")
    @RequestMapping(value = "/getGoldtreeAuthorityList", method = RequestMethod.POST)
    protected ResponseDTO getGoldtreeAuthorityList(
            @RequestBody @ApiParam("请求参数")  GoldtreeAuthorityListPageReq GoldtreeAuthorityListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<GoldtreeAuthorityListRes>> responseDTO = goldtreeService.getGoldtreeAuthorityList(busUser, GoldtreeAuthorityListPageReq);
            return responseDTO;
        } catch (GoldtreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = GoldtreePrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表", notes = "获取奖品类型列表")
    @RequestMapping(value = "/getGoldtreePrizeType", method = RequestMethod.POST)
    protected ResponseDTO getGoldtreePrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<GoldtreePrizeTypeListRes>> responseDTO = goldtreeService.getGoldtreePrizeType(busUser);
            return responseDTO;
        } catch (GoldtreeException e){
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
    @RequestMapping(value = "/removeGoldtreeAuthority", method = RequestMethod.POST)
    protected ResponseDTO removeGoldtreeAuthority(
            @RequestBody @ApiParam("请求参数") GoldtreeAuthorityIdsReq GoldtreeAuthorityIdsReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = goldtreeService.removeGoldtreeAuthority(busUser, GoldtreeAuthorityIdsReq);
            return responseDTO;
        } catch (GoldtreeException e){
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
            Map<String, Object> msg = goldtreeService.exports(params);
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
        } catch (GoldtreeException e){
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
            ResponseDTO<List<RaiseflagTemplateRes>> responseDTO = goldtreeService.getTemplateList(busUser);
            return responseDTO;
        } catch (GoldtreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
}
