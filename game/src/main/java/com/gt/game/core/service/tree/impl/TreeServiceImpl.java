package com.gt.game.core.service.tree.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.axis.bean.wxmp.dict.DictApiReq;
import com.gt.axis.bean.wxmp.dict.DictApiRes;
import com.gt.axis.bean.wxmp.wxpublic.WxPublicUsers;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.member.MemberServer;
import com.gt.axis.server.wxmp.DictServer;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.ninelattice.req.NinelatticePrizeSetReq;
import com.gt.game.core.bean.ninelattice.res.NinelatticeAuthorityListRes;
import com.gt.game.core.bean.ninelattice.res.NinelatticeGetWinningRes;
import com.gt.game.core.bean.ninelattice.res.NinelatticePrizeTypeListRes;
import com.gt.game.core.bean.tree.req.*;
import com.gt.game.core.bean.tree.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.tree.TreeWinningDAO;
import com.gt.game.core.entity.ninelattice.*;
import com.gt.game.core.entity.tree.TreeDetail;
import com.gt.game.core.entity.tree.TreeMain;
import com.gt.game.core.entity.tree.TreeWinning;
import com.gt.game.core.exception.ninelattice.NinelatticeException;
import com.gt.game.core.exception.tree.TreeException;
import com.gt.game.core.service.tree.TreeDetailService;
import com.gt.game.core.service.tree.TreeMainService;
import com.gt.game.core.service.tree.TreeService;
import com.gt.game.core.service.tree.TreeWinningService;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 圣诞大礼包  服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class TreeServiceImpl implements TreeService {

    @Autowired
    TreeMainService treeMainService;

    @Autowired
    TreeDetailService treeDetailService;

    @Autowired
    TreeWinningService treeWinningService;

    @Autowired
    TreeWinningDAO treeWinningDAO;

    /**
     * 获取手机端链接
     *
     * @param busUser
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {
        return null;
    }

    /**
     * 分页获取圣诞大礼包活动列表
     * @param busUser
     * @param treeListReq
     * @return
     */
    @Override
    public ResponseDTO<List<TreeListRes>> getTreeList(BusUser busUser, TreeListReq treeListReq, HttpServletRequest request) {

        EntityWrapper<TreeMain> entityWrapper = new EntityWrapper();
        WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
        entityWrapper.eq("tree_wx_userid",loginPbUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(treeListReq.getName()), "tree_name", treeListReq.getName());
        if (treeListReq.getStatus() != -1) {   // TODO -1 全部 0 未开始 1 进行中 2 已结束
            if (treeListReq.getStatus() == 0) {
                entityWrapper.where("activity_begin_time > {0}", new Date());
            }
            if (treeListReq.getStatus() == 1) {
                entityWrapper.where("activity_begin_time <= {0}", new Date());
                entityWrapper.and();
                entityWrapper.where("activity_end_time > {0}", new Date());
            }
            if (treeListReq.getStatus() == 2) {
                entityWrapper.where("activity_end_time <= {0}", new Date());
            }
        }
        Page<TreeMain> page = new Page<>(treeListReq.getCurrent(), treeListReq.getSize());
        List<TreeMain> treeMainList = treeMainService.selectPage(page, entityWrapper).getRecords();

        List<TreeListRes> treeListResList = new ArrayList<>();
        for (TreeMain TreeMain : treeMainList) {
            TreeListRes treeListResRes = new TreeListRes();
            treeListResRes.setId(TreeMain.getId());
            treeListResRes.setName(TreeMain.getTreeName());
            treeListResRes.setActivityBeginTime(TreeMain.getTreeBeginTime());
            treeListResRes.setActivityEndTime(TreeMain.getTreeEndTime());

            Date date = new Date();
            if (TreeMain.getTreeBeginTime().getTime() > date.getTime()) {
                treeListResRes.setStatus(0);
            } else if (TreeMain.getTreeBeginTime().getTime() <= date.getTime() && TreeMain.getTreeEndTime().getTime() >= date.getTime()) {
                treeListResRes.setStatus(1);
            } else if (TreeMain.getTreeEndTime().getTime() < date.getTime()) {
                treeListResRes.setStatus(2);
            }

            treeListResList.add(treeListResRes);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取圣诞大礼包活动列表成功", treeListResList, pageDTO);
    }

    /**
     * 统计元圣诞大礼包活动总数
     * @param busUser
     * @param treeCountActivityReq
     * @param request
     * @return
     */
    @Override
    public TreeCountActivityRes countTree(BusUser busUser, TreeCountActivityReq treeCountActivityReq, HttpServletRequest request) {

        EntityWrapper<TreeMain> entityWrapper = new EntityWrapper();
        WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
        entityWrapper.eq("tree_wx_userid",loginPbUser.getId());
        entityWrapper.like(CommonUtil.isNotEmpty(treeCountActivityReq.getName()), "tree_name", treeCountActivityReq.getName());

        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        List<TreeMain> treeMainList = treeMainService.selectList(entityWrapper);
        Date date = new Date();
        for (TreeMain treeMain : treeMainList) {
            if (treeMain.getTreeBeginTime().getTime() > date.getTime()) {
                count2++; //  TODO    未开始
            } else if (treeMain.getTreeBeginTime().getTime() <= date.getTime() && treeMain.getTreeEndTime().getTime() >= date.getTime()) {
                count3++;  // TODO    进行中
            } else if (treeMain.getTreeEndTime().getTime() < date.getTime()) {
                count4++;  // TODO    已结束
            }
        }
        TreeCountActivityRes treeCountActivityRes = new TreeCountActivityRes();
        treeCountActivityRes.setCount2(count2);
        treeCountActivityRes.setCount3(count3);
        treeCountActivityRes.setCount4(count4);
        treeCountActivityRes.setCount1(count2+count3+count4);  //  TODO 全部
        return  treeCountActivityRes;
    }

    /**
     * 新增圣诞大礼包活动
     * @param busUser
     * @param treeAddReq
     * @param request
     */
    @Override
    public void addTree(BusUser busUser, TreeAddReq treeAddReq, HttpServletRequest request) {

        TreeMain treeMain = new TreeMain();
        WxPublicUsers loginPbUser = CommonUtil.getLoginPbUser(request);
        treeMain.setTreeWxUserid(loginPbUser.getId());

        //TODO  基础设置
        treeMain.setTreeName(treeAddReq.getTreeName());
        treeMain.setTreeBeginTime(treeAddReq.getActivityBeginTime());
        treeMain.setTreeEndTime(treeAddReq.getActivityEndTime());
        treeMain.setTreeEggPartaker(treeAddReq.getTreeEggPartaker());
        if(treeAddReq.getTreeEggPartaker()==2){
            treeMain.setTreePway(treeAddReq.getTreePway());
            if(treeAddReq.getTreePway()==2){
                treeMain.setTreeMan(treeAddReq.getTreeMan());
            }else if(treeAddReq.getTreePway()==3){
                treeMain.setTreeKou(treeAddReq.getTreeKou());
            }else if(treeAddReq.getTreePway()==4){
                treeMain.setTreeMan(treeAddReq.getTreeMan());
                treeMain.setTreeKou(treeAddReq.getTreeKou());
            }
        }
        treeMain.setTreeDescribe(treeAddReq.getTreeDescribe());
        treeMain.setTreeBeforeTxt(treeAddReq.getTreeBeforeTxt());
        //TODO  规则设置
        treeMain.setTreeCountOfAll(treeAddReq.getTreeCountOfAll());
        treeMain.setTreeCountOfDay(treeAddReq.getTreeCountOfDay());
        //TODO  兑奖设置
        treeMain.setTreeCashDay(treeAddReq.getTreeCashDay());
        treeMain.setTreeAddress(treeAddReq.getTreeAddress());
        treeMain.setTreeIsGive(treeAddReq.getTreeIsGive());
        treeMain.setTreeWinningTxt(treeAddReq.getTreeWinningTxt());

        treeMainService.insert(treeMain);

        //TODO   奖项设置
        for(TreePrizeSetReq treePrizeSetReq:treeAddReq.getPrizeSetList()){

            TreeDetail treeDetail = new TreeDetail();
            treeDetail.setTreeId(treeMain.getId());
            treeDetail.setTreePrizeType(treePrizeSetReq.getType());
            treeDetail.setTreePrizeLimit(treePrizeSetReq.getPrizeUnit());
            treeDetail.setTreePrizeName(treePrizeSetReq.getPrizeName());
            treeDetail.setTreePrizeNums(treePrizeSetReq.getNum());
            treeDetail.setTreePrizeChance(treePrizeSetReq.getProbabiliy());
            treeDetail.setNickname(treePrizeSetReq.getNickName());

            treeDetailService.insert(treeDetail);
        }
    }

    /**
     * 通过活动id查询圣诞大礼包活动成功
     * @param busUser
     * @param treeGetActivityReq
     * @return
     */
    @Override
    public TreeGetActivityRes getActivityById(BusUser busUser, TreeGetActivityReq treeGetActivityReq) {

        TreeMain treeMain = treeMainService.selectById(treeGetActivityReq.getId());

        TreeGetActivityRes treeGetActivityRes = new TreeGetActivityRes();
        //TODO  基础设置
        treeGetActivityRes.setTreeName(treeMain.getTreeName());
        treeGetActivityRes.setActivityBeginTime(treeMain.getTreeBeginTime());
        treeGetActivityRes.setActivityEndTime(treeMain.getTreeEndTime());
        treeGetActivityRes.setTreeEggPartaker(treeMain.getTreeEggPartaker());
        if(treeMain.getTreeEggPartaker()==2){
            treeGetActivityRes.setTreePway(treeMain.getTreePway());
            if(treeMain.getTreePway()==2){
                treeGetActivityRes.setTreeMan(treeMain.getTreeMan());
            }else if(treeMain.getTreePway()==3){
                treeGetActivityRes.setTreeKou(treeMain.getTreeKou());
            }else if(treeMain.getTreePway()==4){
                treeGetActivityRes.setTreeMan(treeMain.getTreeMan());
                treeGetActivityRes.setTreeKou(treeMain.getTreeKou());
            }
        }
        treeGetActivityRes.setTreeDescribe(treeMain.getTreeDescribe());
        treeGetActivityRes.setTreeBeforeTxt(treeMain.getTreeBeforeTxt());
        //TODO  规则设置
        treeGetActivityRes.setTreeCountOfAll(treeMain.getTreeCountOfAll());
        treeGetActivityRes.setTreeCountOfDay(treeMain.getTreeCountOfDay());
        //TODO  兑奖设置
        treeGetActivityRes.setTreeCashDay(treeMain.getTreeCashDay());
        treeGetActivityRes.setTreeAddress(treeMain.getTreeAddress());
        treeGetActivityRes.setTreeIsGive(treeMain.getTreeIsGive());
        treeGetActivityRes.setTreeWinningTxt(treeMain.getTreeWinningTxt());

        //TODO   奖项设置
        EntityWrapper<TreeDetail> entityWrapper = new EntityWrapper();
        entityWrapper.eq("tree_id",treeGetActivityReq.getId());
        List<TreeDetail> treeDetailList = treeDetailService.selectList(entityWrapper);
        List<TreePrizeSetReq> list = new ArrayList<>();
        for(TreeDetail treeDetail:treeDetailList){

            TreePrizeSetReq treePrizeSetReq = new TreePrizeSetReq();
            treePrizeSetReq.setType(treeDetail.getTreePrizeType());
            treePrizeSetReq.setPrizeUnit(treeDetail.getTreePrizeLimit());
            treePrizeSetReq.setPrizeName(treeDetail.getTreePrizeName());
            treePrizeSetReq.setNum(treeDetail.getTreePrizeNums());
            treePrizeSetReq.setProbabiliy(treeDetail.getTreePrizeChance());
            treePrizeSetReq.setNickName(treeDetail.getNickname());
            list.add(treePrizeSetReq);
        }
        treeGetActivityRes.setPrizeSetList(list);

        return treeGetActivityRes;
    }

    /**
     * 编辑圣诞大礼包活动基础设置
     * @param busUser
     * @param treeModfiyBasicsReq
     */
    @Override
    public void modfiyBasicsTree(BusUser busUser, TreeModfiyBasicsReq treeModfiyBasicsReq) {

        TreeMain treeMain = new TreeMain();
        treeMain.setId(treeModfiyBasicsReq.getId());
        treeMain.setTreeName(treeModfiyBasicsReq.getTreeName());
        treeMain.setTreeBeginTime(treeModfiyBasicsReq.getActivityBeginTime());
        treeMain.setTreeEndTime(treeModfiyBasicsReq.getActivityEndTime());
        treeMain.setTreeEggPartaker(treeModfiyBasicsReq.getTreeEggPartaker());

        if(treeModfiyBasicsReq.getTreeEggPartaker()==2){
            treeMain.setTreePway(treeModfiyBasicsReq.getTreePway());
            if(treeModfiyBasicsReq.getTreePway()==2){
                treeMain.setTreeMan(treeModfiyBasicsReq.getTreeMan());
            }else if(treeModfiyBasicsReq.getTreePway()==3){
                treeMain.setTreeKou(treeModfiyBasicsReq.getTreeKou());
            }else if(treeModfiyBasicsReq.getTreePway()==4){
                treeMain.setTreeMan(treeModfiyBasicsReq.getTreeMan());
                treeMain.setTreeKou(treeModfiyBasicsReq.getTreeKou());
            }
        }
        treeMain.setTreeDescribe(treeModfiyBasicsReq.getTreeDescribe());
        treeMain.setTreeBeforeTxt(treeModfiyBasicsReq.getTreeBeforeTxt());

        treeMainService.updateById(treeMain);
    }

    /**
     * 编辑圣诞大礼包活动规则设置
     * @param busUser
     * @param treeModfiyRuleReq
     */
    @Override
    public void modfiyRuleTree(BusUser busUser, TreeModfiyRuleReq treeModfiyRuleReq) {

        TreeMain treeMain = new TreeMain();
        treeMain.setId(treeModfiyRuleReq.getId());
        treeMain.setTreeCountOfAll(treeModfiyRuleReq.getTreeCountOfAll());
        treeMain.setTreeCountOfDay(treeModfiyRuleReq.getTreeCountOfDay());

        treeMainService.updateById(treeMain);
    }

    /**
     * 编辑圣诞大礼包活动兑奖设置
     * @param busUser
     * @param treeModfiyExpiryReq
     */
    @Override
    public void modfiyExpiryTree(BusUser busUser, TreeModfiyExpiryReq treeModfiyExpiryReq) {

        TreeMain treeMain = new TreeMain();
        treeMain.setId(treeModfiyExpiryReq.getId());
        treeMain.setTreeCashDay(treeModfiyExpiryReq.getTreeCashDay());
        treeMain.setTreeAddress(treeModfiyExpiryReq.getTreeAddress());
        treeMain.setTreeIsGive(treeModfiyExpiryReq.getTreeIsGive());
        treeMain.setTreeWinningTxt(treeModfiyExpiryReq.getTreeWinningTxt());

        treeMainService.updateById(treeMain);
    }

    /**
     * 编辑圣诞大礼包奖项设置
     * @param busUser
     * @param treeModfiyAwardsReq
     */
    @Override
    public void modfiyAwardsTree(BusUser busUser, TreeModfiyAwardsReq treeModfiyAwardsReq) {

        if(treeModfiyAwardsReq.getPrizeSetList().size()>0){        //TODO  奖品设置
            // TODO  清空奖品设置
            EntityWrapper<TreeDetail> entityWrapper = new EntityWrapper();
            entityWrapper.eq("tree_id",treeModfiyAwardsReq.getId());
            treeDetailService.delete(entityWrapper);

            //TODO   奖项设置
            for(TreePrizeSetReq treePrizeSetReq:treeModfiyAwardsReq.getPrizeSetList()){

                TreeDetail treeDetail = new TreeDetail();
                treeDetail.setTreeId(treeModfiyAwardsReq.getId());
                treeDetail.setTreePrizeType(treePrizeSetReq.getType());
                treeDetail.setTreePrizeLimit(treePrizeSetReq.getPrizeUnit());
                treeDetail.setTreePrizeName(treePrizeSetReq.getPrizeName());
                treeDetail.setTreePrizeNums(treePrizeSetReq.getNum());
                treeDetail.setTreePrizeChance(treePrizeSetReq.getProbabiliy());
                treeDetail.setNickname(treePrizeSetReq.getNickName());

                treeDetailService.insert(treeDetail);
            }
        }
    }


    /**
     * 批量删除圣诞大礼包活动
     * @param busUser
     * @param treeDelReq
     */
    @Override
    public void delTree(BusUser busUser, TreeDelReq treeDelReq) {

        //TODO  批量删除活动
        treeMainService.deleteBatchIds(treeDelReq.getId());


        for(Integer treeId:treeDelReq.getId()){
            //TODO  批量删除奖品设置
            EntityWrapper<TreeDetail> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("tree_id",treeId);
            treeDetailService.delete(entityWrapper);

            //TODO  批量删除中奖记录
            EntityWrapper<TreeWinning> entityWrapper2 = new EntityWrapper<>();
            entityWrapper2.eq("tree_act_id",treeId);
            treeWinningService.delete(entityWrapper2);
        }
     }

    /**
     * 分页获取圣诞大礼包中奖记录列表
     * @param busUser
     * @param treeGetWinningReq
     * @return
     */
    @Override
    public ResponseDTO<List<TreeGetWinningRes>> getWinningList(BusUser busUser, TreeGetWinningReq treeGetWinningReq) {

        Page<TreeGetWinningRes> page = new Page<>(treeGetWinningReq.getCurrent(),treeGetWinningReq.getSize());
        List<TreeGetWinningRes> treeGetWinningResList = treeWinningDAO.queryRecodList(page,treeGetWinningReq);

        //TODO  获取用户名
        if(treeGetWinningResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < treeGetWinningResList.size(); i++) {
                ids.append(treeGetWinningResList.get(i).getMemberId());
                if (treeGetWinningResList.size() > 1 && i < treeGetWinningResList.size() - 1) {
                    ids.append(",");
                }
            }
            MemberReq memberReq = new MemberReq();
            memberReq.setBusId(busUser.getId());
            memberReq.setIds(ids.toString());
            AxisResult<List<MemberRes>> axisResult = null;
            try {
                axisResult = MemberServer.findMemberByIds(memberReq);
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<MemberRes> memberResList = null;
            if (CommonUtil.isNotEmpty(axisResult)) {
                memberResList = axisResult.getData();
            } else {
                memberResList = new ArrayList<>();
            }
            for (int i = 0; i < treeGetWinningResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == treeGetWinningResList.get(i).getMemberId().intValue()) {
                        treeGetWinningResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(treeGetWinningResList.get(i).getMemberName())) {
                    treeGetWinningResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取中奖记录列表成功",treeGetWinningResList,pageDTO);
    }

    /**
     * 中奖记录发放奖品
     * @param busUser
     * @param treeEditApplyReq
     * @return
     */
    @Override
    public ResponseDTO editTreeApply(BusUser busUser, TreeEditApplyReq treeEditApplyReq) {

        TreeWinning treeWinning = treeWinningService.selectById(treeEditApplyReq.getId());
        if(CommonUtil.isNotEmpty(treeWinning)){
            TreeMain treeMain = treeMainService.selectById(treeWinning.getTreeActId());
            TreeDetail treeDetail = treeDetailService.selectById(treeWinning.getTreePrizeId());
            if(treeDetail.getTreePrizeType() != 4){    //非兑奖
                if (DateTimeKit.laterThanNow(treeMain.getTreeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new TreeException(ResponseEnums.TREE_HAS1);
                } else if (!DateTimeKit.laterThanNow(treeMain.getTreeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new TreeException(ResponseEnums.TREE_HAS2);
                }
            }
            if (treeWinning.getTreeStatus() == 3) {
                // 更改记录状态
                treeWinning.setTreeStatus(2);
                treeWinning.setTreeCashtime(new Date());
                treeWinningService.updateById(treeWinning);
            } else if (treeWinning.getTreeStatus() == 2){//已兑奖
                throw  new TreeException(ResponseEnums.TREE_HAS3);
            }else{//还未提交
                throw  new TreeException(ResponseEnums.TREE_HAS4);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }

    /**
     * 导出中奖记录
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> exportTree(Map<String, Object> params,BusUser busUser) {

        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            TreeMain treeMain = treeMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = treeWinningDAO.findExports(params);
            String title = "活动名称：" + treeMain.getTreeName() + "，开始时间：" + sdf.format(treeMain.getTreeBeginTime()) + "结束时间："
                    + sdf.format(treeMain.getTreeEndTime());
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("tree_memberId"));
                    if (list.size() > 1 && i < list.size() - 1) {
                        ids.append(",");
                    }
                }
                MemberReq memberReq = new MemberReq();
                memberReq.setBusId(busUser.getId());
                memberReq.setIds(ids.toString());
                AxisResult<List<MemberRes>> axisResult = null;
                try {
                    axisResult = MemberServer.findMemberByIds(memberReq);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                List<MemberRes> memberResList = null;
                if (CommonUtil.isNotEmpty(axisResult)) {
                    memberResList = axisResult.getData();
                } else {
                    memberResList = new ArrayList<>();
                }
                for (int i = 0; i < list.size(); i++) {
                    for (int j = 0; j < memberResList.size(); j++) {
                        if (memberResList.get(j).getId().intValue() == CommonUtil.toInteger(list.get(i).get("tree_memberId")).intValue()) {
                            list.get(i).put("nickname",memberResList.get(j).getNickname());
                        }
                    }
                    if (CommonUtil.isEmpty(list.get(i).get("nickname"))) {
                        list.get(i).put("nickname","游客");
                    }
                }
            }
            HSSFWorkbook book = exportExcelForRecoding(list, title);
            msg.put("book", book);
            msg.put("fileName", treeMain.getTreeName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "圣诞大礼包活动中奖记录导出excel失败！");
            e.printStackTrace();
        } finally {
            msg.put("result", result);
            msg.put("message", message);
        }
        return msg;
    }

    private HSSFWorkbook exportExcelForRecoding(List<Map<String, Object>> list, String title) {
        // 创建excel文件对象
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个张表
        Sheet sheet = wb.createSheet();
        // 创建第一行
        Row row = sheet.createRow(0);
        // 设置行高
        row.setHeight((short) 500);
        // 创建第二行
        Row row1 = sheet.createRow(1);
        // 处理时间
        // 设置列的宽度
        sheet.setColumnWidth((short) (2), (short) 4000);
        sheet.setColumnWidth((short) (3), (short) 4000);
        sheet.setColumnWidth((short) (4), (short) 8000);
        sheet.setColumnWidth((short) (5), (short) 8000);
        sheet.setColumnWidth((short) (6), (short) 6000);
        sheet.setColumnWidth((short) (7), (short) 6000);
        sheet.setColumnWidth((short) (8), (short) 6000);
        // 文件头字体
        Font font0 = createFonts(wb, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
        Font font1 = createFonts(wb, Font.BOLDWEIGHT_NORMAL, "宋体", false, (short) 200);
        // 合并第一行的单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 6));
        // 设置第一列的文字
        createCell(wb, row, 1, title, font0);
        // 给第二行添加文本
        createCell(wb, row1, 0, "序号", font1);
        createCell(wb, row1, 1, "奖品名称", font1);
        createCell(wb, row1, 2, "奖品", font1);
        createCell(wb, row1, 3, "中奖时间", font1);
        createCell(wb, row1, 4, "中奖人联系方式", font1);
        createCell(wb, row1, 5, "中奖人昵称", font1);
        createCell(wb, row1, 6, "状态", font1);
        createCell(wb, row1, 7, "兑奖码", font1);
        // 第三行表示
        int l = 2;
        String priTypeName = "";
        String priTypeUnit = "";
        // 这里将的信息存入到表格中
        for (int i = 0; i < list.size(); i++) {
            // 创建一行
            Row rowData = sheet.createRow(l++);
            Map<String, Object> map = list.get(i);
            createCell(wb, rowData, 0, String.valueOf(i + 1), font1);
            createCell(wb, rowData, 1, delWithColumn(map.get("tree_prize_name")), font1);
            String turPriType = delWithColumn(map.get("tree_prize_type"));
            if ("4".equals(turPriType)) {// 实物
                priTypeName = "实体物品";
                priTypeUnit = "份";
            } else {
                if ("1".equals(turPriType)) {// 粉币
                    priTypeName = "粉币";
                    priTypeUnit = "个";
                }
                if ("2".equals(turPriType)) {// 流量
                    priTypeName = "流量";
                    priTypeUnit = "M";
                }
                if ("3".equals(turPriType)) {// 话费
                    priTypeName = "话费";
                    priTypeUnit = "元";
                }
            }
            createCell(wb, rowData, 2, priTypeName + "/" + delWithColumn(map.get("tree_prize_limit")) + priTypeUnit, font1);

            createCell(wb, rowData, 3,
                    delWithColumn(CommonUtil.isEmpty(map.get("cashTime")) ? ""
                            : DateTimeKit.format(
                            DateTimeKit.parseDate(map.get("cashTime").toString(), "yyyy/MM/dd HH:mm:ss"),
                            "yyyy-MM-dd HH:mm")),
                    font1);
            createCell(wb, rowData, 4, delWithColumn(map.get("tree_phone")), font1);
            createCell(wb, rowData, 5,
                    CommonUtil.isEmpty(map.get("nickname")) ? "游客" : map.get("nickname").toString(),
                    font1);
            if ("1".equals(delWithColumn(map.get("tree_status")).toString())) {
                createCell(wb, rowData, 6, "已兑奖", font1);
            } else if ("2".equals(delWithColumn(map.get("status")).toString())) {
                createCell(wb, rowData, 6, "未兑奖", font1);
            } else {
                createCell(wb, rowData, 6, "已提交", font1);
            }
            createCell(wb, rowData, 7, delWithColumn(map.get("tree_exchangeCode")), font1);

        }
        return wb;
    }

    /**
     * 设置字体
     *
     * @param wb
     * @return
     */
    public static Font createFonts(Workbook wb, short bold, String fontName, boolean isItalic, short hight) {
        Font font = wb.createFont();
        font.setFontName(fontName);
        font.setBoldweight(bold);
        font.setItalic(isItalic);
        font.setFontHeight(hight);
        return font;
    }

    /**
     * 创建单元格并设置样式,值
     *
     * @param wb
     * @param row
     * @param column
     * @param
     * @param
     * @param value
     */
    public static void createCell(Workbook wb, Row row, int column, String value, Font font) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    private static String delWithColumn(Object obj) {
        if (CommonUtil.isEmpty(obj)) {
            return "";
        } else {
            return obj.toString();
        }
    }

    /**
     * 批量删除圣诞大礼包中奖记录
     * @param busUser
     * @param treeDelWinningReq
     */
    @Override
    public void delTreeWinning(BusUser busUser, TreeDelWinningReq treeDelWinningReq) {

        treeWinningService.deleteBatchIds(treeDelWinningReq.getId());
    }


    /**
     * 获取奖品类型列表
     * @param busUser
     * @return
     */
    @Override
    public ResponseDTO<List<TreePrizeTypeListRes>> getTreePrizeType(BusUser busUser) {

        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<TreePrizeTypeListRes> treePrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                TreePrizeTypeListRes treePrizeTypeListRes = new TreePrizeTypeListRes();
                treePrizeTypeListRes.setName(dictApiRes.getItemValue());
                treePrizeTypeListRes.setValue(dictApiRes.getItemKey());
                treePrizeTypeListResList.add(treePrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",treePrizeTypeListResList);
    }
}