package com.gt.game.core.controller.ninelattice;



import com.gt.api.bean.session.BusUser;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.lantern.res.LanternPrizeTypeListRes;
import com.gt.game.core.bean.ninelattice.req.*;
import com.gt.game.core.bean.ninelattice.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.demolition.DemolitionException;
import com.gt.game.core.exception.dragonboat.DragonboatException;
import com.gt.game.core.exception.lantern.LanternException;
import com.gt.game.core.exception.ninelattice.NinelatticeException;
import com.gt.game.core.service.lantern.LanternService;
import com.gt.game.core.service.ninelattice.NinelatticeService;
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
 * 幸运九宫格  前端控制器
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Api(value = "/app/ninelattice", description = "幸运九宫格商家后台")
@RestController
@RequestMapping(value = "/app/ninelattice")
public class NinelatticeController extends BaseController {

    @Autowired
    NinelatticeService ninelatticeService;


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
            MobileUrlRes mobileUrlRes = ninelatticeService.getMobileUrl(busUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取幸运九宫格活动列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = NinelatticeListRes.class),
    })
    @ApiOperation(value = "分页获取幸运九宫格活动列表", notes = "分页获取幸运九宫格活动列表")
    @RequestMapping(value = "/getNinelatticeList", method = RequestMethod.POST)
    protected ResponseDTO getNinelatticeList(@RequestBody @ApiParam(value = "请求对象") NinelatticeListReq ninelatticeListReq, BindingResult bindingResult,
                                             HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(ninelatticeListReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<NinelatticeListRes>> responseDTO = ninelatticeService.getNinelatticeList(busUser, ninelatticeListReq);
            return responseDTO;
        } catch (LanternException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (NinelatticeException e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO    统计元幸运九宫格活动总数
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = NinelatticeCountActivityRes.class),
    })
    @ApiOperation(value = "统计元幸运九宫格活动总数", notes = "统计元幸运九宫格活动总数")
    @RequestMapping(value = "/countNinelattice", method = RequestMethod.POST)
    public ResponseDTO countNinelattice(@RequestBody @ApiParam(value = "请求对象") NinelatticeCountActivityReq ninelatticeCountActivityReq, BindingResult bindingResult,
                                        HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(ninelatticeCountActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            NinelatticeCountActivityRes ninelatticeCountActivityRes = ninelatticeService.countNinelattice(busUser, ninelatticeCountActivityReq);
            return ResponseDTO.createBySuccess("统计幸运九宫格活动总数成功", ninelatticeCountActivityRes);
        }catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  新增幸运九宫格活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "新增幸运九宫格活动", notes = "新增幸运九宫格活动")
    @RequestMapping(value = "/addNinelattice", method = RequestMethod.POST)
    protected ResponseDTO addNinelattice(@RequestBody @ApiParam(value = "请求对象") NinelatticeAddReq ninelatticeAddReq, BindingResult bindingResult,
                                         HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(ninelatticeAddReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ninelatticeService.addNinelattice(busUser, ninelatticeAddReq);
            return ResponseDTO.createBySuccessMessage("新增幸运九宫格活动成功");
        } catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO    通过活动id查询幸运九宫格活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = NinelatticeGetActivityRes.class),
    })
    @ApiOperation(value = "通过活动id查询幸运九宫格活动", notes = "通过活动id查询幸运九宫格活动")
    @RequestMapping(value = "/getActivityById", method = RequestMethod.POST)
    public ResponseDTO getActivityById(@RequestBody @ApiParam(value = "请求对象") NinelatticeGetActivityReq ninelatticeGetActivityReq, BindingResult bindingResult,
                                       HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(ninelatticeGetActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            NinelatticeGetActivityRes ninelatticeGetActivityRes = ninelatticeService.getActivityById(busUser, ninelatticeGetActivityReq);
            return ResponseDTO.createBySuccess("通过活动id查询幸运九宫格活动成功", ninelatticeGetActivityRes);
        }catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑幸运九宫格活动基础设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑幸运九宫格活动基础设置", notes = "编辑幸运九宫格活动基础设置")
    @RequestMapping(value = "/modfiyBasicsNinelattice", method = RequestMethod.POST)
    protected ResponseDTO modfiyBasicsNinelattice(@RequestBody @ApiParam(value = "请求对象") NinelatticeModfiyBasicsReq ninelatticeModfiyBasicsReq, BindingResult bindingResult,
                                                  HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(ninelatticeModfiyBasicsReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ninelatticeService.modfiyBasicsNinelattice(busUser,ninelatticeModfiyBasicsReq);
            return ResponseDTO.createBySuccessMessage("编辑幸运九宫格活动基础设置成功");
        } catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑幸运九宫格活动规则设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑幸运九宫格活动规则设置", notes = "编辑幸运九宫格活动规则设置")
    @RequestMapping(value = "/modfiyRuleNinelattice", method = RequestMethod.POST)
    protected ResponseDTO modfiyRuleNinelattice(@RequestBody @ApiParam(value = "请求对象") NinelatticeModfiyRuleReq ninelatticeModfiyRuleReq, BindingResult bindingResult,
                                            HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(ninelatticeModfiyRuleReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ninelatticeService.modfiyRuleNinelattice(busUser, ninelatticeModfiyRuleReq);
            return ResponseDTO.createBySuccessMessage("编辑幸运九宫格活动规则设置成功");
        } catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑幸运九宫格活动兑奖设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑幸运九宫格活动兑奖设置", notes = "编辑幸运九宫格活动兑奖设置")
    @RequestMapping(value = "/modfiyExpiryNinelattice", method = RequestMethod.POST)
    protected ResponseDTO modfiyExpiryNinelattice(@RequestBody @ApiParam(value = "请求对象") NinelatticeModfiyExpiryReq ninelatticeModfiyExpiryReq, BindingResult bindingResult,
                                              HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(ninelatticeModfiyExpiryReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ninelatticeService.modfiyExpiryNinelattice(busUser, ninelatticeModfiyExpiryReq);
            return ResponseDTO.createBySuccessMessage("编辑幸运九宫格活动兑奖设置成功");
        } catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑幸运九宫格奖项设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑幸运九宫格活动奖项设置", notes = "编辑幸运九宫格奖项设置")
    @RequestMapping(value = "/modfiyAwardsNinelattice", method = RequestMethod.POST)
    protected ResponseDTO modfiyAwardsNinelattice(@RequestBody @ApiParam(value = "请求对象") NinelatticeModfiyAwardsReq ninelatticeModfiyAwardsReq, BindingResult bindingResult,
                                              HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(ninelatticeModfiyAwardsReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ninelatticeService.modfiyAwardsNinelattice(busUser, ninelatticeModfiyAwardsReq);
            return ResponseDTO.createBySuccessMessage("编辑幸运九宫格活动奖项设置成功");
        } catch (NinelatticeException e){
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
    @ApiOperation(value = "删除幸运九宫格活动", notes = "删除幸运九宫格活动")
    @RequestMapping(value = "/delNinelattice", method = RequestMethod.POST)
    protected ResponseDTO delNinelattice(
            @RequestBody @ApiParam("请求参数") NinelatticeDelReq ninelatticeDelReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ninelatticeService.delNinelattice(busUser, ninelatticeDelReq);
            return ResponseDTO.createBySuccessMessage("删除幸运九宫格活动成功");
        } catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取幸运九宫格中奖记录列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = NinelatticeGetWinningRes.class),
    })
    @ApiOperation(value = "分页获取幸运九宫格中奖记录列表", notes = "分页获取幸运九宫格中奖记录列表")
    @RequestMapping(value = "/getWinningList", method = RequestMethod.POST)
    protected ResponseDTO getWinningList(@RequestBody @ApiParam(value = "请求对象") NinelatticeGetWinningReq ninelatticeGetWinningReq,BindingResult bindingResult,
                                         HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(ninelatticeGetWinningReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<NinelatticeGetWinningRes>> responseDTO = ninelatticeService.getWinningList(busUser, ninelatticeGetWinningReq);
            return responseDTO;
        } catch (NinelatticeException e){
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
    @RequestMapping(value = "/editNinelatticeApply", method = RequestMethod.POST)
    protected ResponseDTO editNinelatticeApply(
            @RequestBody @ApiParam("请求参数") NinelatticeEditApplyReq ninelatticeEditApplyReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = ninelatticeService.editNinelatticeApply(busUser, ninelatticeEditApplyReq);
            return responseDTO;
        } catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    /**
     * 导出幸运九宫格中奖记录
     * @param request
     * @param response
     */
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "导出中奖记录", notes = "导出中奖记录")
    @RequestMapping(value = "/exportNinelattice", method = RequestMethod.GET)
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
            Map<String, Object> msg = ninelatticeService.exportNinelattice(params);
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
    @ApiOperation(value = "批量删除幸运九宫格中奖记录", notes = "批量删除幸运九宫格中奖记录")
    @RequestMapping(value = "/delNinelatticeWinning", method = RequestMethod.POST)
    protected ResponseDTO delNinelatticeWinning(
            @RequestBody @ApiParam("请求参数") NinelatticeDelWinningReq ninelatticeDelWinningReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ninelatticeService.delNinelatticeWinning(busUser, ninelatticeDelWinningReq);
            return ResponseDTO.createBySuccessMessage("批量删除幸运九宫格中奖记录成功");
        } catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "响应对象", response = NinelatticeAuthorityListRes.class),
    })
    @ApiOperation(value = "分页获取核销授权列表", notes = "分页获取核销授权列表")
    @RequestMapping(value = "/getNinelatticeAuthorityList", method = RequestMethod.POST)
    protected ResponseDTO getNinelatticeAuthorityList(@RequestBody @ApiParam("分页获取核销授权列表对象") NinelatticeAuthorityListReq ninelatticeAuthorityListReq, BindingResult bindingResult,
                                                  HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(ninelatticeAuthorityListReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<NinelatticeAuthorityListRes>> responseDTO = ninelatticeService.getNinelatticeAuthorityList(busUser, ninelatticeAuthorityListReq);
            return responseDTO;
        } catch (NinelatticeException e){
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
    @RequestMapping(value = "/getNinelatticePrizeType", method = RequestMethod.POST)
    protected ResponseDTO getNinelatticePrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<NinelatticePrizeTypeListRes>> responseDTO = ninelatticeService.getNinelatticePrizeType(busUser);
            return responseDTO;
        } catch (NinelatticeException e){
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
    @RequestMapping(value = "/delNinelatticeAuthority", method = RequestMethod.POST)
    protected ResponseDTO delNinelatticeAuthority(
            @RequestBody @ApiParam("请求对象") NinelatticeDelAuthorityReq ninelatticeDelAuthorityReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ninelatticeService.delNinelatticeAuthority(busUser, ninelatticeDelAuthorityReq);
            return ResponseDTO.createBySuccessMessage("批量删除核销授权成功");
        } catch (NinelatticeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }
}