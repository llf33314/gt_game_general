package com.gt.game.core.service.luck.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.axis.bean.wxmp.dict.DictApiReq;
import com.gt.axis.bean.wxmp.dict.DictApiRes;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiFlowRecord;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiFlowRecordReq;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiSurplus;
import com.gt.axis.bean.wxmp.fenbiflow.UpdateFenbiReduceReq;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.member.MemberServer;
import com.gt.axis.server.wxmp.DictServer;
import com.gt.axis.server.wxmp.FenbiflowServer;
import com.gt.game.common.config.ApplyProperties;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.luck.req.*;
import com.gt.game.core.bean.luck.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.luck.LuckMainDAO;
import com.gt.game.core.dao.luck.LuckWinningDAO;
import com.gt.game.core.entity.luck.LuckDetail;
import com.gt.game.core.entity.luck.LuckMain;
import com.gt.game.core.entity.luck.LuckWinning;
import com.gt.game.core.exception.luck.LuckException;
import com.gt.game.core.service.luck.LuckDetailService;
import com.gt.game.core.service.luck.LuckMainService;
import com.gt.game.core.service.luck.LuckService;
import com.gt.game.core.service.luck.LuckWinningService;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 好运翻翻看 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Service
public class LuckServiceImpl implements LuckService {

    @Autowired
    LuckDetailService luckDetailService;

    @Autowired
    LuckMainService luckMainService;

    @Autowired
    LuckWinningService luckWinningService;

    @Autowired
    LuckMainDAO luckMainDAO;

    @Autowired
    LuckWinningDAO luckWinningDAO;

    @Autowired
    ApplyProperties applyProperties;

    /**
     * 获取移动端访问链接
     *
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(WxPublicUsers busUser, MobileUrlReq mobileUrlReq) {
        String url = applyProperties.getMobileBaseUrl() + "LuckMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
        return new MobileUrlRes(url);
    }
    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<LuckCountRes> getLuckCount(WxPublicUsers busUser) {
        LuckCountRes LuckCountRes = new LuckCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = luckMainDAO.getCount(params);
        LuckCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        LuckCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        LuckCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        LuckCountRes.setCount5(CommonUtil.isNotEmpty(countMap.get("count5"))?CommonUtil.toInteger(countMap.get("count5")):0);
        LuckCountRes.setCount1(LuckCountRes.getCount2()+LuckCountRes.getCount3()+LuckCountRes.getCount4()+LuckCountRes.getCount5());
        return ResponseDTO.createBySuccess("获取成功",LuckCountRes);
    }
    /**
     * 分页获取活动列表
     *
     * @param LuckListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<LuckListRes>> getLuckList(WxPublicUsers busUser, LuckListPageReq LuckListPageReq) {
        Page<LuckListRes> page = new Page<>(LuckListPageReq.getCurrent(),LuckListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",LuckListPageReq.getStatus());
        map.put("luckName",LuckListPageReq.getLuckName());
        map.put("busId",busUser.getId());
        List<LuckListRes> LuckListResList = luckMainDAO.getLuckList(page,map);
        for (LuckListRes LuckListRes : LuckListResList){
            if(LuckListRes.getLuckBeginTime().getTime() > date.getTime()){
                LuckListRes.setIsEdit(1);
                LuckListRes.setStatus(0);
            }else if(LuckListRes.getLuckEndTime().getTime() < date.getTime()){
                LuckListRes.setIsEdit(0);
                LuckListRes.setStatus(2);
            }else if(LuckListRes.getLuckEndTime().getTime() > date.getTime() && LuckListRes.getStatus() == 1){
                LuckListRes.setIsEdit(0);
                LuckListRes.setStatus(1);
            }else {
                LuckListRes.setIsEdit(0);
                LuckListRes.setStatus(3);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",LuckListResList,pageDTO);
    }
    /**
     * 获取活动
     *
     * @param luckIdReq
     * @return
     */
    @Override
    public ResponseDTO<LuckRes> getLuck(WxPublicUsers busUser, LuckIdReq luckIdReq) {
        LuckRes luckRes = new LuckRes();
        LuckMain luckMain = luckMainService.selectById(luckIdReq.getId());
        if(CommonUtil.isNotEmpty(luckMain)){
            BeanUtils.copyProperties(luckMain,luckRes);
            luckRes.setLuckPway(CommonUtil.isNotEmpty(luckMain.getLuckPway())? luckMain.getLuckPway().toString():"");
            luckRes.setLuckMan(CommonUtil.isNotEmpty(luckMain.getLuckMan())? luckMain.getLuckMan().toString():"");
            luckRes.setLuckKou(CommonUtil.isNotEmpty(luckMain.getLuckKou())? luckMain.getLuckKou().toString():"");
            List<LuckDetail> luckDetails = luckDetailService.selectList(new EntityWrapper<LuckDetail>().eq("luck_id",luckIdReq.getId()));
            List<LuckDetailReq> luckDetailReqs = new ArrayList<>();
            for(LuckDetail luckDetail : luckDetails){
                LuckDetailReq luckDetailReq = new LuckDetailReq();
                BeanUtils.copyProperties(luckDetail,luckDetailReq);
                luckDetailReqs.add(luckDetailReq);
            }
            luckRes.setLuckDetailReqs(luckDetailReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",luckRes);
    }
    /**
     * 保存活动
     *
     * @param luckReq
     * @return
     */
    @Override
   @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveLuck(WxPublicUsers busUser, BusUser user, LuckReq luckReq) {
        if(CommonUtil.isNotEmpty(luckReq.getLuckBeginTime())){
            throw new LuckException(ResponseEnums.LUCK_HAS1);
        }
        if(CommonUtil.isEmpty(luckReq.getLuckDetailReqs())){
            throw new LuckException(ResponseEnums.LUCK_HAS4);
        }
        LuckMain luckMain = null;
        int f = 0;
        Integer num = 0;
        if(luckReq.getId() == 0){//新增
            luckMain = new LuckMain();
            BeanUtils.copyProperties(luckReq,luckMain);
            luckMain.setLuckPway(CommonUtil.isNotEmpty(luckReq.getLuckPway())? Integer.parseInt(luckReq.getLuckPway()):null);
            luckMain.setLuckMan(CommonUtil.isNotEmpty(luckReq.getLuckMan())? Integer.parseInt(luckReq.getLuckMan()):null);
            luckMain.setLuckKou(CommonUtil.isNotEmpty(luckReq.getLuckKou())? Integer.parseInt(luckReq.getLuckKou()):null);
            luckMain.setLuckWxUserid(busUser.getId());
            luckMain.setLuckStatus(1);
            luckMainService.insert(luckMain);
        }else {
            luckMain = luckMainService.selectById(luckReq.getId());
            if(CommonUtil.isEmpty(luckMain)) throw new LuckException(ResponseEnums.LUCK_HAS2);
            if(luckMain.getLuckWxUserid().intValue() != busUser.getId().intValue()) throw new LuckException(ResponseEnums.DIFF_USER);
            if(luckMain.getLuckBeginTime().getTime() < new Date().getTime()) throw new LuckException(ResponseEnums.LUCK_HAS3);
            BeanUtils.copyProperties(luckReq,luckMain);
            luckMain.setLuckPway(CommonUtil.isNotEmpty(luckReq.getLuckPway())? Integer.parseInt(luckReq.getLuckPway()):null);
            luckMain.setLuckMan(CommonUtil.isNotEmpty(luckReq.getLuckMan())? Integer.parseInt(luckReq.getLuckMan()):null);
            luckMain.setLuckKou(CommonUtil.isNotEmpty(luckReq.getLuckKou())? Integer.parseInt(luckReq.getLuckKou()):null);
            luckMainService.updateById(luckMain);
            List<LuckDetail> luckDetails = luckDetailService.selectList(new EntityWrapper<LuckDetail>().eq("luck_id",luckMain.getId()));
            if(luckDetails.size() > 0){
                for(LuckDetail luckDetail : luckDetails){
                    if(luckDetail.getLuckPrizeType() == 1){
                        num += luckDetail.getLuckPrizeNums();
                        f = 1;
                    }
                }
                luckDetailService.delete(new EntityWrapper<LuckDetail>().eq("luck_id",luckMain.getId()));
            }
        }
        List<LuckDetail> luckDetails = new ArrayList<>();
        int type = 0 ;
        Integer fenbi = 0 ;
        for (LuckDetailReq luckDetailReq : luckReq.getLuckDetailReqs()){
            LuckDetail luckDetail = new LuckDetail();
            BeanUtils.copyProperties(luckDetailReq,luckDetail);
            luckDetail.setLuckId(luckMain.getId());
            luckDetails.add(luckDetail);
            if(luckDetail.getLuckPrizeType() == 5){
                type = 1;
            }
            if(luckDetail.getLuckPrizeType() == 1){
                fenbi = luckDetailReq.getLuckPrizeNums();
            }
        }
        if(type < 1){
            throw new LuckException(ResponseEnums.LUCK_HAS5);
        }
        luckDetailService.insertOrUpdateAllColumnBatch(luckDetails);
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new LuckException(ResponseEnums.COMMON_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (user.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new LuckException(ResponseEnums.COMMON_HAS14);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(luckMain.getId());
                updateFenbiReduceReq.setFreType(38);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new LuckException(ResponseEnums.COMMON_HAS15);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(user.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new LuckException(ResponseEnums.COMMON_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=CommonUtil.bulidFenFlow(busUser.getId(), fenbi.doubleValue(), luckMain.getId(), 38, 1, "一箭穿心活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new LuckException(ResponseEnums.COMMON_HAS8);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    /**
     * 删除活动
     *
     * @param luckIdReq
     * @return
     */
    @Override
    public ResponseDTO removeLuck(WxPublicUsers busUser, LuckIdReq luckIdReq) {
        LuckMain luckMain = luckMainService.selectById(luckIdReq.getId());
        if(CommonUtil.isNotEmpty(luckMain)){
            Date date = new Date();
            if(luckMain.getLuckBeginTime().getTime() < date.getTime() && luckMain.getLuckEndTime().getTime() > date.getTime()){
                throw new LuckException(ResponseEnums.LOVEARROW_HAS10);
            }
            if(luckMain.getLuckBeginTime().getTime() > date.getTime() && DateTimeKit.addDate(luckMain.getLuckEndTime(),luckMain.getLuckCashDay()).getTime() > date.getTime()){
                throw new LuckException(ResponseEnums.LOVEARROW_HAS12);
            }
            List<LuckWinning> luckWinnings = luckWinningService.selectList(
                    new EntityWrapper<LuckWinning>().eq("luck_act_id",luckMain.getId()).eq("luck_status",3));
            if(luckWinnings.size() > 0 ){
                throw new LuckException(ResponseEnums.LOVEARROW_HAS11);

            }
            if(luckMain.getLuckWxUserid().intValue() != busUser.getId().intValue()){
                throw new LuckException(ResponseEnums.DIFF_USER);
            }
            boolean ff = false;
            List<LuckDetail> luckDetails = luckDetailService.selectList(new EntityWrapper<LuckDetail>().eq("luck_id",luckMain.getId()));
            if(luckDetails.size() > 0){
                for(LuckDetail luckDetail : luckDetails){
                    if(luckDetail.getLuckPrizeType() == 1){
                        ff = true;
                    }
                }
                luckDetailService.delete(new EntityWrapper<LuckDetail>().eq("luck_id",luckMain.getId()));
            }
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(luckMain.getId());
                fenbiSurplus.setFre_type(38);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(luckMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(38);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new LuckException(ResponseEnums.LOVEARROW_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 分页获取中奖记录列表
     * @param luckWinningPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<LuckWinningListRes>> getLuckWinningList(WxPublicUsers busUser, LuckWinningPageReq luckWinningPageReq) {
        Page<LuckWinningListRes> page = new Page<>(luckWinningPageReq.getCurrent(),luckWinningPageReq.getSize());
        List<LuckWinningListRes> luckWinningListResList = luckWinningDAO.queryRecodList(page,luckWinningPageReq);
        if(luckWinningListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < luckWinningListResList.size(); i++) {
                ids.append(luckWinningListResList.get(i).getLuckMemberId());
                if (luckWinningListResList.size() > 1 && i < luckWinningListResList.size() - 1) {
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
            for (int i = 0; i < luckWinningListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == luckWinningListResList.get(i).getLuckMemberId().intValue()) {
                        luckWinningListResList.get(i).setLuckMemberName(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(luckWinningListResList.get(i).getLuckMemberName())) {
                    luckWinningListResList.get(i).setLuckMemberName("未知用户");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",luckWinningListResList,pageDTO);
    }
    /**
     * 中奖记录发放奖品
     * @param luckWinningIdReq
     * @return
     */
    @Override
    public ResponseDTO editLuckWinning(BusUser busUser, LuckWinningIdReq luckWinningIdReq) {
        LuckWinning luckWinning = luckWinningService.selectById(luckWinningIdReq.getId());
        if(CommonUtil.isNotEmpty(luckWinning)){
            LuckMain main = luckMainService.selectById(luckWinning.getLuckActId());
            LuckDetail luckDetail = luckDetailService.selectById(luckWinning.getLuckPrizeId());
            if(luckDetail.getLuckPrizeType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(main.getLuckBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new LuckException(ResponseEnums.LOVEARROW_HAS3);
                } else if (!DateTimeKit.laterThanNow(DateTimeKit.addDate(main.getLuckEndTime(),main.getLuckCashDay()))) {
                    //""已过兑奖时间！";
                    throw  new LuckException(ResponseEnums.LOVEARROW_HAS4);
                }
            }
            if (luckWinning.getLuckStatus() == 3) {
                // 更改记录状态
                luckWinning.setLuckStatus(2);
                luckWinning.setLuckCashtime(new Date());
                luckWinningService.updateById(luckWinning);
            } else if (luckWinning.getLuckStatus() == 2){//已兑奖
                throw  new LuckException(ResponseEnums.LOVEARROW_HAS1);
            }else{//还未提交
                throw  new LuckException(ResponseEnums.LOVEARROW_HAS2);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }
    @Override
    public Map<String, Object> exports(Map<String, Object> params) {
        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            LuckMain main = luckMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = luckWinningDAO.findExports(params);
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("luck_memberId"));
                    if (list.size() > 1 && i < list.size() - 1) {
                        ids.append(",");
                    }
                }
                MemberReq memberReq = new MemberReq();
                memberReq.setBusId(CommonUtil.toInteger(params.get("busId")));
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
                        if (memberResList.get(j).getId().intValue() == CommonUtil.toInteger(list.get(i).get("luck_memberId")).intValue()) {
                            list.get(i).put("nickname",memberResList.get(j).getNickname());
                        }
                    }
                    if (CommonUtil.isEmpty(list.get(i).get("nickname"))) {
                        list.get(i).put("nickname","未知用户");
                    }
                }
            }
            String title = "活动名称：" + main.getLuckName() + "，开始时间：" + sdf.format(main.getLuckBeginTime()) + "结束时间："
                    + sdf.format(main.getLuckEndTime());
            HSSFWorkbook book = exportExcelForWinning(list, title);
            msg.put("book", book);
            msg.put("fileName", main.getLuckName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "好运翻翻看导出excel失败！");
            e.printStackTrace();
        } finally {
            msg.put("result", result);
            msg.put("message", message);
        }
        return msg;
    }
    /*
    *  暂停/开始活动
    */
    @Override
    public ResponseDTO stopLuck(WxPublicUsers busUser, LuckStopIdReq luckStopIdReq) {
        LuckMain luckMain = luckMainService.selectById(luckStopIdReq.getId());
        if(CommonUtil.isNotEmpty(luckMain)){
            if(luckMain.getLuckWxUserid().intValue() != busUser.getId().intValue()){
                throw new LuckException(ResponseEnums.DIFF_USER);
            }
            Date date = new Date();
            if(luckMain.getLuckBeginTime().getTime() > date.getTime()){
                throw new LuckException(ResponseEnums.LUCK_HAS6);
            }
            if(luckMain.getLuckEndTime().getTime() > date.getTime()){
                throw new LuckException(ResponseEnums.LUCK_HAS7);
            }
            luckMain.setLuckStatus(luckStopIdReq.getLuckStatus());
            luckMainService.updateById(luckMain);
        }
        return ResponseDTO.createBySuccess("操作成功");
    }
    /**
     * 获取奖品类型列表
     *
     */
    @Override
    public ResponseDTO<List<LuckPrizeTypeListRes>> getLuckPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<LuckPrizeTypeListRes> luckPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                LuckPrizeTypeListRes luckPrizeTypeListRes = new LuckPrizeTypeListRes();
                luckPrizeTypeListRes.setName(dictApiRes.getItemValue());
                luckPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                luckPrizeTypeListResList.add(luckPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",luckPrizeTypeListResList);
    }
    public static HSSFWorkbook exportExcelForWinning(List<Map<String, Object>> winnLs,String title) {
        // 创建excel文件对象
        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个张表
        Sheet sheet = wb.createSheet();
        // 创建第一行
        Row row = sheet.createRow(0);
        //设置行高
        row.setHeight((short)500);
        // 创建第二行
        Row row1 = sheet.createRow(1);
        //处理时间
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //设置没列的宽度
        sheet.setColumnWidth((short)(2), (short)4000);
        sheet.setColumnWidth((short)(3), (short)8000);
        sheet.setColumnWidth((short)(4), (short)8000);
        sheet.setColumnWidth((short)(5), (short)8000);
        sheet.setColumnWidth((short)(6), (short)8000);
        sheet.setColumnWidth((short)(7), (short)8000);
        // 文件头字体
        Font font0 = createFonts(wb, Font.BOLDWEIGHT_BOLD, "宋体", false,
                (short) 200);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
        cellStyle.setFont(font0);
        Font font1 = createFonts(wb, Font.BOLDWEIGHT_NORMAL, "宋体", false,
                (short) 200);
        // 合并第一行的单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 6));
        // 设置第一列的文字
        createCell(wb, row, 1, title, cellStyle);
        // 给第二行添加文本
        cellStyle.setFont(font1);
        createCell(wb, row1, 0, "序号", cellStyle);
        createCell(wb, row1, 1, "奖项名称", cellStyle);
        createCell(wb, row1, 2, "奖品", cellStyle);
        createCell(wb, row1, 3, "中奖时间", cellStyle);
        createCell(wb, row1, 4, "中奖人联系方式", cellStyle);
        createCell(wb, row1, 5, "中奖人昵称", cellStyle);
        createCell(wb, row1, 6, "状态", cellStyle);
        createCell(wb, row1, 7, "兑奖码", cellStyle);
        // 第三行表示
        int l = 2;
        // 这里将的信息存入到表格中
        for (int i = 0; i < winnLs.size(); i++) {
            // 创建一行
            Row rowData = sheet.createRow(l++);
            Map<String, Object> map = winnLs.get(i);
            createCell(wb, rowData, 0, String.valueOf(i + 1), cellStyle);
            createCell(wb, rowData, 1, delWithColumn(map.get("luck_prize_name")), cellStyle);
            createCell(wb, rowData, 2, delWithColumn(map.get("luck_prize_type")), cellStyle);
            createCell(wb, rowData, 3, delWithColumn(map.get("luck_create_time")), cellStyle);
            createCell(wb, rowData, 4, delWithColumn(map.get("luck_phone")), cellStyle);
            //Blob blob = (Blob) map.get("nickName");
            //Byte[] b = (Byte[]) map.get("nickName");
            //System.out.println(map.get("nickName").getClass());
            createCell(wb, rowData, 5, map.get("nickname").toString(), cellStyle);
            if("1".equals(delWithColumn(map.get("luck_status")).toString())){
                createCell(wb, rowData, 6, "已发放", cellStyle);
            }else if("2".equals(delWithColumn(map.get("luck_status")).toString())){
                createCell(wb, rowData, 6, "未发放", cellStyle);
            }else{
                createCell(wb, rowData, 6, "已提交", cellStyle);
            }

            createCell(wb, rowData, 7, delWithColumn(map.get("luck_exchangeCode")), cellStyle);

        }
        return wb;
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
    public static void createCell(Workbook wb, Row row, int column,
                                  String value, CellStyle cellStyle) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 设置字体
     *
     * @param wb
     * @return
     */
    public static Font createFonts(Workbook wb, short bold, String fontName,
                                   boolean isItalic, short hight) {
        Font font = wb.createFont();
        font.setFontName(fontName);
        font.setBoldweight(bold);
        font.setItalic(isItalic);
        font.setFontHeight(hight);
        return font;
    }

    private static String delWithColumn(Object obj){
        if(CommonUtil.isEmpty(obj)){
            return "";
        }else{
            return obj.toString();
        }
    }

}
