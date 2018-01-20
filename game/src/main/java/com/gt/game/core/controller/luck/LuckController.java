package com.gt.game.core.controller.luck;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.luck.req.*;
import com.gt.game.core.bean.luck.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.luck.LuckException;
import com.gt.game.core.service.luck.LuckService;
import com.gt.game.core.util.CommonUtil;
import io.swagger.annotations.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
 * 好运翻翻看 前端控制器
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Api(value = "/app/luck", description = "好运翻翻看商家后台")
@RestController
@RequestMapping("/app/luck")
public class LuckController  extends BaseController {
	
    @Autowired
    LuckService luckService;

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "获取手机端链接", notes = "获取手机端链接")
    @RequestMapping(value = "/getMobileUrl", method = RequestMethod.POST)
    @Override
    protected ResponseDTO getMobileUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            MobileUrlRes mobileUrlRes = luckService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = LuckCountRes.class),
    })
    @ApiOperation(value = "获取活动数量", notes = "获取活动数量")
    @RequestMapping(value = "/getLuckCount", method = RequestMethod.POST)
    protected ResponseDTO getLuckCount(
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO<LuckCountRes> responseDTO = luckService.getLuckCount(busUser);
            return responseDTO;
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = LuckListRes.class),
    })
    @ApiOperation(value = "分页获取活动列表", notes = "分页获取活动列表")
    @RequestMapping(value = "/getLuckList", method = RequestMethod.POST)
    protected ResponseDTO getLuckList(
            @RequestBody @ApiParam("请求参数") LuckListPageReq LuckListPageReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO<List<LuckListRes>> responseDTO = luckService.getLuckList(busUser, LuckListPageReq);
            return responseDTO;
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = LuckRes.class),
    })
    @ApiOperation(value = "获取活动", notes = "获取活动")
    @RequestMapping(value = "/getLuck", method = RequestMethod.POST)
    protected ResponseDTO getLuck(
            @RequestBody @ApiParam("请求参数") LuckIdReq luckIdReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO<LuckRes> responseDTO = luckService.getLuck(busUser, luckIdReq);
            return responseDTO;
        } catch (LuckException e){
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
    @RequestMapping(value = "/saveLuck", method = RequestMethod.POST)
    protected ResponseDTO saveLuck(
            @RequestBody @ApiParam("请求参数") @Valid LuckReq luckReq
            , BindingResult bindingResult, HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            BusUser user = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = luckService.saveLuck(busUser,user,luckReq);
            return responseDTO;
        } catch (LuckException e){
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
    @RequestMapping(value = "/removeLuck", method = RequestMethod.POST)
    protected ResponseDTO removeLuck(
            @RequestBody @ApiParam("请求参数") LuckIdReq luckIdReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO responseDTO = luckService.removeLuck(busUser, luckIdReq);
            return responseDTO;
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = LuckWinningListRes.class),
    })
    @ApiOperation(value = "分页获取中奖记录", notes = "分页获取中奖记录")
    @RequestMapping(value = "/getLuckWinningList", method = RequestMethod.POST)
    protected ResponseDTO getLuckWinningList(
            @RequestBody @ApiParam("请求参数") LuckWinningPageReq luckWinningPageReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO<List<LuckWinningListRes>> responseDTO = luckService.getLuckWinningList(busUser, luckWinningPageReq);
            return responseDTO;
        } catch (LuckException e){
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
    @RequestMapping(value = "/editLuckWinning", method = RequestMethod.POST)
    protected ResponseDTO editLuckWinning(
            @RequestBody @ApiParam("请求参数") LuckWinningIdReq luckWinningIdReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = luckService.editLuckWinning(busUser, luckWinningIdReq);
            return responseDTO;
        } catch (LuckException e){
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
            @RequestParam @ApiParam("兑奖状态 -1 全部 1 未兑奖 2 已兑奖 3 已提交") Integer luckStatus,
            @RequestParam @ApiParam("奖品类型 -1 全部 1 粉币 2 手机流量 4 实体物品 6 积分 7 优惠劵") Integer luckPrizeType,
            @RequestParam @ApiParam("兑奖码") String snCode,
            HttpServletResponse response, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            Map<String,Object> params = new HashMap<>();
            params.put("actId",actId);
            params.put("luckStatus",luckStatus);
            params.put("luckPrizeType",luckPrizeType);
            params.put("snCode",snCode);
            params.put("busId",busUser.getId());
            Map<String, Object> msg = luckService.exports(params);
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
        } catch (LuckException e){
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
    @ApiOperation(value = "暂停/开始活动", notes = "暂停/开始活动")
    @RequestMapping(value = "/stopLuck", method = RequestMethod.POST)
    protected ResponseDTO stopLuck(
            @RequestBody @ApiParam("请求参数") LuckStopIdReq luckStopIdReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers busUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO responseDTO = luckService.stopLuck(busUser, luckStopIdReq);
            return responseDTO;
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = LuckPrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表", notes = "获取奖品类型列表")
    @RequestMapping(value = "/getLuckPrizeType", method = RequestMethod.POST)
    protected ResponseDTO getLuckPrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<LuckPrizeTypeListRes>> responseDTO = luckService.getLuckPrizeType(busUser);
            return responseDTO;
        } catch (LuckException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

}
