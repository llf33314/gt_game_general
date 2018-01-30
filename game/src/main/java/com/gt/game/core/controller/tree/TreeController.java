package com.gt.game.core.controller.tree;



import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.eggs.req.EggsStopIdReq;
import com.gt.game.core.bean.lantern.res.LanternPrizeTypeListRes;
import com.gt.game.core.bean.ninelattice.res.*;
import com.gt.game.core.bean.tree.req.*;
import com.gt.game.core.bean.tree.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.exception.eggs.EggsException;
import com.gt.game.core.exception.ninelattice.NinelatticeException;
import com.gt.game.core.exception.tree.TreeException;
import com.gt.game.core.service.tree.TreeService;
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
 * 圣诞大礼包  前端控制器
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Api(value = "/app/tree", description = "圣诞大礼包商家后台")
@RestController
@RequestMapping(value = "/app/tree")
public class TreeController extends BaseController {

    @Autowired
    TreeService treeService;


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
            MobileUrlRes mobileUrlRes = treeService.getMobileUrl(loginPbUser, mobileUrlReq);
            return ResponseDTO.createBySuccess("获取手机端链接成功", mobileUrlRes);
        } catch (TreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }


    // TODO  分页获取圣诞大礼包活动列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = TreeListRes.class),
    })
    @ApiOperation(value = "分页获取圣诞大礼包活动列表", notes = "分页获取圣诞大礼包活动列表")
    @RequestMapping(value = "/getTreeList", method = RequestMethod.POST)
    protected ResponseDTO getTreeList(@RequestBody @ApiParam(value = "请求对象") TreeListReq treeListReq, BindingResult bindingResult,
                                      HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(treeListReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<TreeListRes>> responseDTO = treeService.getTreeList(busUser, treeListReq,request);
            return responseDTO;
        } catch (TreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (NinelatticeException e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO    统计元圣诞大礼包活动总数
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = TreeCountActivityRes.class),
    })
    @ApiOperation(value = "统计元圣诞大礼包活动总数", notes = "统计元圣诞大礼包活动总数")
    @RequestMapping(value = "/countTree", method = RequestMethod.POST)
    public ResponseDTO countTree(@RequestBody @ApiParam(value = "请求对象") TreeCountActivityReq treeCountActivityReq, BindingResult bindingResult,
                                 HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(treeCountActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            TreeCountActivityRes treeCountActivityRes = treeService.countTree(busUser, treeCountActivityReq,request);
            return ResponseDTO.createBySuccess("统计元圣诞大礼包活动总数成功", treeCountActivityRes);
        }catch (TreeException e){
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
    @ApiOperation(value = "新增圣诞大礼包活动", notes = "新增圣诞大礼包活动")
    @RequestMapping(value = "/addTree", method = RequestMethod.POST)
    protected ResponseDTO addTree(@RequestBody @ApiParam(value = "请求对象") TreeAddReq treeAddReq, BindingResult bindingResult,
                                  HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(treeAddReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            treeService.addTree(busUser, treeAddReq,request);
            return ResponseDTO.createBySuccessMessage("新增圣诞大礼包活动成功");
        } catch (TreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO    通过活动id查询圣诞大礼包活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = NinelatticeGetActivityRes.class),
    })
    @ApiOperation(value = "通过活动id查询圣诞大礼包活动", notes = "通过活动id查询圣诞大礼包活动")
    @RequestMapping(value = "/getActivityById", method = RequestMethod.POST)
    public ResponseDTO getActivityById(@RequestBody @ApiParam(value = "请求对象") TreeGetActivityReq treeGetActivityReq, BindingResult bindingResult,
                                       HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(treeGetActivityReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            TreeGetActivityRes treeGetActivityRes = treeService.getActivityById(busUser, treeGetActivityReq);
            return ResponseDTO.createBySuccess("通过活动id查询圣诞大礼包活动成功", treeGetActivityRes);
        }catch (TreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  编辑圣诞大礼包活动设置
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "编辑圣诞大礼包活动设置", notes = "编辑圣诞大礼包活动设置")
    @RequestMapping(value = "/modfiyTree", method = RequestMethod.POST)
    protected ResponseDTO modfiyTree(@RequestBody @ApiParam(value = "请求对象") TreeModfiyReq treeModfiyReq, BindingResult bindingResult,
                                           HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(treeModfiyReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            treeService.modfiyTree(busUser,treeModfiyReq);
            return ResponseDTO.createBySuccessMessage("编辑圣诞大礼包活动设置成功");
        } catch (TreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  开始/暂停圣诞大礼包活动
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
    })
    @ApiOperation(value = "开始/暂停圣诞大礼包活动", notes = "开始/暂停圣诞大礼包活动")
    @RequestMapping(value = "/stopTree", method = RequestMethod.POST)
    protected ResponseDTO stopTree(
            @RequestBody @ApiParam("请求参数") TreeStopIdReq treeStopIdReq,
            HttpServletRequest request) {
        try {
            WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
            ResponseDTO responseDTO = treeService.stopTree(loginPbUser, treeStopIdReq);
            return responseDTO;
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
    @ApiOperation(value = "删除圣诞大礼包活动", notes = "删除圣诞大礼包活动")
    @RequestMapping(value = "/delTree", method = RequestMethod.POST)
    protected ResponseDTO delTree(
            @RequestBody @ApiParam("请求参数") TreeDelReq treeDelReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            treeService.delTree(busUser, treeDelReq);
            return ResponseDTO.createBySuccessMessage("删除圣诞大礼包活动成功");
        } catch (TreeException e){
            logger.error(e.getMessage(), e.fillInStackTrace());
            return ResponseDTO.createByErrorCodeMessage(e.getCode(), e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseDTO.createByError();
        }
    }

    // TODO  分页获取圣诞大礼包中奖记录列表
    @ApiResponses({
            @ApiResponse(code = 0, message = "统一响应对象", response = ResponseDTO.class),
            @ApiResponse(code = 1, message = "data对象（数组对象）", response = List.class),
            @ApiResponse(code = 2, message = "任务对象", response = TreeGetWinningRes.class),
    })
    @ApiOperation(value = "分页获取圣诞大礼包中奖记录列表", notes = "分页获取圣诞大礼包中奖记录列表")
    @RequestMapping(value = "/getWinningList", method = RequestMethod.POST)
    protected ResponseDTO getWinningList(@RequestBody @ApiParam(value = "请求对象") TreeGetWinningReq treeGetWinningReq, BindingResult bindingResult,
                                         HttpServletRequest request) {
        InvalidParameter(bindingResult);
        try {
            logger.debug(treeGetWinningReq.toString());
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<TreeGetWinningRes>> responseDTO = treeService.getWinningList(busUser, treeGetWinningReq);
            return responseDTO;
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
    @ApiOperation(value = "中奖记录发放奖品", notes = "中奖记录发放奖品")
    @RequestMapping(value = "/editTreeApply", method = RequestMethod.POST)
    protected ResponseDTO editTreeApply(
            @RequestBody @ApiParam("请求参数") TreeEditApplyReq treeEditApplyReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO responseDTO = treeService.editTreeApply(busUser, treeEditApplyReq);
            return responseDTO;
        } catch (TreeException e){
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
            Map<String, Object> msg = treeService.exportTree(params,busUser);
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
    @ApiOperation(value = "批量删除圣诞大礼包中奖记录", notes = "批量删除圣诞大礼包中奖记录")
    @RequestMapping(value = "/delTreeWinning", method = RequestMethod.POST)
    protected ResponseDTO delTreeWinning(
            @RequestBody @ApiParam("请求参数") TreeDelWinningReq treeDelWinningReq,
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            treeService.delTreeWinning(busUser, treeDelWinningReq);
            return ResponseDTO.createBySuccessMessage("批量删除圣诞大礼包中奖记录成功");
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
            @ApiResponse(code = 1, message = "响应对象", response = LanternPrizeTypeListRes.class),
    })
    @ApiOperation(value = "获取奖品类型列表", notes = "获取奖品类型列表")
    @RequestMapping(value = "/getTreePrizeType", method = RequestMethod.POST)
    protected ResponseDTO getTreePrizeType(
            HttpServletRequest request) {
        try {
            BusUser busUser = CommonUtil.getLoginUser(request);
            ResponseDTO<List<TreePrizeTypeListRes>> responseDTO = treeService.getTreePrizeType(busUser);
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