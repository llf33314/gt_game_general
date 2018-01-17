package com.gt.game.core.controller.dragonboat;



import com.gt.api.bean.session.BusUser;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.dragonboat.req.*;
import com.gt.game.core.bean.dragonboat.res.*;
import com.gt.game.core.bean.lantern.req.LanternAuthorityListReq;
import com.gt.game.core.bean.lantern.req.LanternDelAuthorityReq;
import com.gt.game.core.bean.lantern.req.LanternDelWinningReq;
import com.gt.game.core.bean.lantern.res.LanternAuthorityListRes;
import com.gt.game.core.bean.lantern.res.LanternPrizeTypeListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.dragonboat.DragonboatException;
import com.gt.game.core.exception.lantern.LanternException;
import com.gt.game.core.service.dragonboat.DragonboatService;
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
 * 端午赛龙舟  前端控制器
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Api(value = "/app/Dragonboat", description = "端午赛龙舟商家后台")
@RestController
@RequestMapping(value = "/app/Dragonboat")
public class DragonboatController extends BaseController {

    @Autowired
    DragonboatService  dragonboatService;

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
            MobileUrlRes mobileUrlRes = dragonboatService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (DragonboatException e){
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
            ResponseDTO<MobileUrlRes> mobileUrlRes = dragonboatService.getAuthorityUrl(busUser, mobileUrlReq);
            return mobileUrlRes;
        } catch (DragonboatException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = DragonboatCountRes.class),
    })
    @ApiOperation(value = "获取端午赛龙舟活动数量", notes = "获取端午赛龙舟活动数量")
    @RequestMapping(value = "/getDragonboatCount", method = RequestMethod.POST)
    protected ResponseDTO getDragonboatCount(@RequestBody @ApiParam(value = "请求参数") DragonboatCountActivityReq dragonboatCountActivityReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<DragonboatCountRes> responseDTO = dragonboatService.getDragonboatCount(busUser,dragonboatCountActivityReq);
            return responseDTO;
        } catch (DragonboatException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = DragonboatListRes.class),
    })
    @ApiOperation(value = "分页端午赛龙舟获取活动列表", notes = "分页端午赛龙舟获取活动列表")
    @RequestMapping(value = "/getDragonboatList", method = RequestMethod.POST)
    protected ResponseDTO getDragonboatList(
            @RequestBody @ApiParam("请求参数") DragonboatListPageReq dragonboatListPageReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<DragonboatListRes>> responseDTO = dragonboatService.getDragonboatList(busUser, dragonboatListPageReq);
            return responseDTO;
        } catch (DragonboatException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  新增端午赛龙舟活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "新增端午赛龙舟活动", notes = "新增端午赛龙舟活动")
    @RequestMapping(value = "/addDragonboat", method = RequestMethod.POST)
    protected ResponseDTO addDragonboat(@RequestBody @ApiParam(value = "新增端午赛龙舟活动列表对象") DragonboatAddReq dragonboatAddReq, BindingResult bindingResult,
                                        HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(dragonboatAddReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            dragonboatService.addDragonboat(busUser, dragonboatAddReq);
            return ResponseDTO.createBySuccessMessage("新增端午赛龙舟活动成功");
        } catch (DragonboatException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑端午赛龙舟活动基础设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑端午赛龙舟活动基础设置", notes = "编辑端午赛龙舟活动基础设置")
    @RequestMapping(value = "/modfiyBasicsDragonboat", method = RequestMethod.POST)
    protected ResponseDTO modfiyBasicsDragonboat(@RequestBody @ApiParam(value = "编辑端午赛龙舟活动基础设置对象") DragonboatModfiyBasicsReq dragonboatModfiyBasicsReq, BindingResult bindingResult,
                                                 HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(dragonboatModfiyBasicsReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            dragonboatService.modfiyBasicsDragonboat(busUser,dragonboatModfiyBasicsReq);
            return ResponseDTO.createBySuccessMessage("编辑端午赛龙舟活动基础设置成功");
        } catch (DragonboatException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑端午赛龙舟活动规则设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑端午赛龙舟活动规则设置", notes = "编辑端午赛龙舟活动规则设置")
    @RequestMapping(value = "/modfiyRuleDragonboat", method = RequestMethod.POST)
    protected ResponseDTO modfiyRuleDragonboat(@RequestBody @ApiParam(value = "编辑元宵点灯活动基础规则对象") DragonboatModfiyRuleReq dragonboatModfiyRuleReq, BindingResult bindingResult,
                                               HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(dragonboatModfiyRuleReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            dragonboatService.modfiyRuleDragonboat(busUser, dragonboatModfiyRuleReq);
            return ResponseDTO.createBySuccessMessage("编辑端午赛龙舟活动规则设置成功");
        } catch (DragonboatException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }


    // TODO  编辑端午赛龙舟活动兑奖设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑端午赛龙舟活动兑奖设置", notes = "编辑端午赛龙舟活动兑奖设置")
    @RequestMapping(value = "/modfiyExpiryDragonboat", method = RequestMethod.POST)
    protected ResponseDTO modfiyExpiryDragonboat(@RequestBody @ApiParam(value = "编辑端午赛龙舟活动兑奖设置对象") DragonboatModfiyExpiryReq dragonboatModfiyExpiryReq, BindingResult bindingResult,
                                              HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(dragonboatModfiyExpiryReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            dragonboatService.modfiyExpiryDragonboat(busUser, dragonboatModfiyExpiryReq);
            return ResponseDTO.createBySuccessMessage("编辑端午赛龙舟活动兑奖设置成功");
        } catch (DragonboatException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑端午赛龙舟活动奖项设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑端午赛龙舟活动奖项设置", notes = "编辑端午赛龙舟活动奖项设置")
    @RequestMapping(value = "/modfiyAwardsDragonboat", method = RequestMethod.POST)
    protected ResponseDTO modfiyAwardsDragonboat(@RequestBody @ApiParam(value = "编辑端午赛龙舟活动奖项设置对象") DragonboatModfiyAwardsReq dragonboatModfiyAwardsReq, BindingResult bindingResult,
                                              HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(dragonboatModfiyAwardsReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            dragonboatService.modfiyAwardsDragonboat(busUser, dragonboatModfiyAwardsReq);
            return ResponseDTO.createBySuccessMessage("编辑端午赛龙舟活动奖项设置成功");
        } catch (DragonboatException e){
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
    @ApiOperation(value = "删除端午赛龙舟活动", notes = "删除端午赛龙舟活动")
    @RequestMapping(value = "/delDragonboat", method = RequestMethod.POST)
    protected ResponseDTO delDragonboat(
            @RequestBody @ApiParam("请求参数") DragonboatDelReq dragonboatDelReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            dragonboatService.delDragonboat(busUser, dragonboatDelReq);
            return ResponseDTO.createBySuccessMessage("删除端午赛龙舟活动成功");
        } catch (DragonboatException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取端午赛龙舟中奖记录列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = DragonboatGetWinningRes.class),
    })
    @ApiOperation(value = "分页获取端午赛龙舟中奖记录列表", notes = "分页获取端午赛龙舟中奖记录列表")
    @RequestMapping(value = "/getWinningList", method = RequestMethod.POST)
    protected ResponseDTO getWinningList(@RequestBody @ApiParam(value = "分页获取端午赛龙舟中奖记录列表对象") DragonboatGetWinningReq dragonboatGetWinningReq, BindingResult bindingResult,
                                         HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(dragonboatGetWinningReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<DragonboatGetWinningRes>> responseDTO = dragonboatService.getWinningList(busUser, dragonboatGetWinningReq);
            return responseDTO;
        } catch (DragonboatException e){
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
    @RequestMapping(value = "/editDragonboatApply", method = RequestMethod.POST)
    protected ResponseDTO editDragonboatApply(
            @RequestBody @ApiParam("请求参数") DragonboatEditApplyReq dragonboatEditApplyReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = dragonboatService.editDragonboatApply(busUser, dragonboatEditApplyReq);
            return responseDTO;
        } catch (DragonboatException e){
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
    @RequestMapping(value = "/exportDragonboat", method = RequestMethod.GET)
    protected ResponseDTO exportDragonboat(
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
            Map<String, Object> msg = dragonboatService.exportDragonboat(params);
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
        } catch (DragonboatException e){
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
    @ApiOperation(value = "批量删除中奖记录", notes = "批量删除中奖记录")
    @RequestMapping(value = "/delDragonboatWinning", method = RequestMethod.POST)
    protected ResponseDTO delDragonboatWinning(
            @RequestBody @ApiParam("请求参数") DragonboatDelWinningReq dragonboatDelWinningReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            dragonboatService.delLanternWinning(busUser, dragonboatDelWinningReq);
            return ResponseDTO.createBySuccessMessage("批量删除中奖记录成功");
        } catch (DragonboatException e){
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
    @RequestMapping(value = "/getDragonboatAuthorityList", method = RequestMethod.POST)
    protected ResponseDTO getDragonboatAuthorityList(@RequestBody @ApiParam("分页获取核销授权列表对象") DragonboatAuthorityListReq dragonboatAuthorityListReq, BindingResult bindingResult,
                                                  HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(dragonboatAuthorityListReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<DragonboatAuthorityListRes>> responseDTO = dragonboatService.getDragonboatAuthorityList(busUser, dragonboatAuthorityListReq);
            return responseDTO;
        } catch (DragonboatException e){
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
    @RequestMapping(value = "/delDragonboatAuthority", method = RequestMethod.POST)
    protected ResponseDTO delDragonboatAuthority(
            @RequestBody @ApiParam("批量删除核销授权对象") DragonboatDelAuthorityReq dragonboatDelAuthorityReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            dragonboatService.delDragonboatAuthority(busUser, dragonboatDelAuthorityReq);
            return ResponseDTO.createBySuccessMessage("批量删除核销授权成功");
        } catch (DragonboatException e){
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
    @RequestMapping(value = "/getDragonboatPrizeType", method = RequestMethod.POST)
    protected ResponseDTO getDragonboatPrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<DragonboatPrizeTypeListRes>> responseDTO = dragonboatService.getDragonboatPrizeType(busUser);
            return responseDTO;
        } catch (DragonboatException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }



}