package com.gt.game.core.controller.lantern;



import com.gt.api.bean.session.BusUser;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.lantern.req.*;
import com.gt.game.core.bean.lantern.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.demolition.DemolitionException;
import com.gt.game.core.exception.dragonboat.DragonboatException;
import com.gt.game.core.exception.lantern.LanternException;
import com.gt.game.core.service.lantern.LanternService;
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

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = MobileUrlRes.class),
    })
    @ApiOperation(value = "获取新增授权链接", notes = "获取新增授权链接")
    @RequestMapping(value = "/getAuthorityUrl", method = RequestMethod.POST)
    protected ResponseDTO getAuthorityUrl(@RequestBody @ApiParam(value = "请求参数") MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<MobileUrlRes> mobileUrlRes = lanternService.getAuthorityUrl(busUser, mobileUrlReq);
            return mobileUrlRes;
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

    // TODO    通过活动id查询元宵点灯活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = LanternCountActivityRes.class),
    })
    @ApiOperation(value = "通过活动id查询元宵点灯活动", notes = "通过活动id查询元宵点灯活动")
    @RequestMapping(value = "/getActivityById", method = RequestMethod.POST)
    public ResponseDTO getActivityById(@RequestBody @ApiParam(value = "通过活动id查询元宵点灯活动对象") LanternGetActivityReq lanternGetActivityReq, BindingResult bindingResult,
                                     HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternGetActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            LanternGetActivityRes lanternGetActivityRes = lanternService.getActivityById(busUser, lanternGetActivityReq);
            return ResponseDTO.createBySuccess("通过活动id查询元宵点灯活动成功", lanternGetActivityRes);
        }catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑元宵点灯活动设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑元宵点灯活动设置", notes = "编辑元宵点灯活动设置")
    @RequestMapping(value = "/modfiyLantern", method = RequestMethod.POST)
    protected ResponseDTO modfiyLantern(@RequestBody @ApiParam(value = "编辑元宵点灯活动对象") LanternModfiyReq lanternModfiyReq, BindingResult bindingResult,
                                              HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternModfiyReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            lanternService.modfiyLantern(busUser,lanternModfiyReq);
            return ResponseDTO.createBySuccessMessage("编辑元宵点灯活动设置成功");
        } catch (LanternException e){
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
    @ApiOperation(value = "删除元宵点灯活动", notes = "删除元宵点灯活动")
    @RequestMapping(value = "/delLantern", method = RequestMethod.POST)
    protected ResponseDTO delLantern(
            @RequestBody @ApiParam("请求参数") LanternDelReq lanternDelReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            lanternService.delLantern(busUser, lanternDelReq);
            return ResponseDTO.createBySuccessMessage("删除元宵点灯活动成功");
        } catch (DragonboatException e){
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
            @ApiResponse(code = 2, message = "任务对象", response = LanternGetWinningRes.class),
    })
    @ApiOperation(value = "分页获取元宵点灯中奖记录列表", notes = "分页获取元宵点灯中奖记录列表")
    @RequestMapping(value = "/getWinningList", method = RequestMethod.POST)
    protected ResponseDTO getWinningList(@RequestBody @ApiParam(value = "分页获取元宵点灯中奖记录列表对象") LanternGetWinningReq lanternGetWinningReq,BindingResult bindingResult,
                                         HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternGetWinningReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<LanternGetWinningRes>> responseDTO = lanternService.getWinningList(busUser, lanternGetWinningReq);
            return responseDTO;
        } catch (LanternException e){
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
    @RequestMapping(value = "/editLanternApply", method = RequestMethod.POST)
    protected ResponseDTO editLanternApply(
            @RequestBody @ApiParam("请求参数") LanternEditApplyReq lanternEditApplyReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = lanternService.editLanternApply(busUser, lanternEditApplyReq);
            return responseDTO;
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    /**
     * 导出元宵点灯中奖记录
     * @param request
     * @param response
     */
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "导出中奖记录", notes = "导出中奖记录")
    @RequestMapping(value = "/exportLantern", method = RequestMethod.GET)
    protected ResponseDTO exportLantern(
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
            Map<String, Object> msg = lanternService.exportLantern(params);
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
        } catch (DemolitionException e){
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
    @ApiOperation(value = "批量删除元宵点灯中奖记录", notes = "批量删除元宵点灯中奖记录")
    @RequestMapping(value = "/delLanternWinning", method = RequestMethod.POST)
    protected ResponseDTO delLanternWinning(
            @RequestBody @ApiParam("请求参数") LanternDelWinningReq lanternDelWinningReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            lanternService.delLanternWinning(busUser, lanternDelWinningReq);
            return ResponseDTO.createBySuccessMessage("批量删除元宵点灯中奖记录成功");
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = LanternAuthorityListRes.class),
    })
    @ApiOperation(value = "分页获取核销授权列表", notes = "分页获取核销授权列表")
    @RequestMapping(value = "/getLanternAuthorityList", method = RequestMethod.POST)
    protected ResponseDTO getLanternAuthorityList(@RequestBody @ApiParam("分页获取核销授权列表对象") LanternAuthorityListReq lanternAuthorityListReq,BindingResult bindingResult,
            HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(lanternAuthorityListReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<LanternAuthorityListRes>> responseDTO = lanternService.getLanternAuthorityList(busUser, lanternAuthorityListReq);
            return responseDTO;
        } catch (LanternException e){
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
    @RequestMapping(value = "/getLanternPrizeType", method = RequestMethod.POST)
    protected ResponseDTO getLanternPrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<LanternPrizeTypeListRes>> responseDTO = lanternService.getLanternPrizeType(busUser);
            return responseDTO;
        } catch (LanternException e){
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
    @ApiOperation(value = "批量删除核销授权", notes = "批量删除核销授权")
    @RequestMapping(value = "/delLanternAuthority", method = RequestMethod.POST)
    protected ResponseDTO delLanternAuthority(
            @RequestBody @ApiParam("批量删除核销授权对象") LanternDelAuthorityReq lanternDelAuthorityReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            lanternService.delLanternAuthority(busUser, lanternDelAuthorityReq);
            return ResponseDTO.createBySuccessMessage("批量删除核销授权成功");
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
}