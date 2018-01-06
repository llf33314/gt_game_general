package com.gt.game.core.service.seagold.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.api.bean.session.BusUser;
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
import com.gt.game.core.bean.seagold.req.*;
import com.gt.game.core.bean.seagold.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.seagold.SeagoldCashPrizeApplyDAO;
import com.gt.game.core.dao.seagold.SeagoldMainDAO;
import com.gt.game.core.entity.seagold.*;
import com.gt.game.core.exception.seagold.SeagoldException;
import com.gt.game.core.service.seagold.*;
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
 * 大海捞金_主表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class SeagoldServiceImpl implements SeagoldService {

    @Autowired
    SeagoldMainService seagoldMainService;

    @Autowired
    SeagoldCashPrizeApplyService seagoldCashPrizeApplyService;

    @Autowired
    SeagoldPrizeService seagoldPrizeService;

    @Autowired
    SeagoldAuthorityService seagoldAuthorityService;

    @Autowired
    SeagoldAddressService seagoldAddressService;

    @Autowired
    SeagoldPrizeImgService seagoldPrizeImgService;

    @Autowired
    SeagoldReportService seagoldReportService;

    @Autowired
    SeagoldPlayRecordService seagoldPlayRecordService;

    @Autowired
    SeagoldCashPrizeApplyDAO seagoldCashPrizeApplyDAO;

    @Autowired
    SeagoldMainDAO seagoldMainDAO;

    @Autowired
    ApplyProperties applyProperties;

    /**
     * 获取移动端访问链接
     *
     * @param mobileUrlReq
     * @return
     */
    @Override
    public MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {
        String url = applyProperties.getMobileBaseUrl() + "seagoldMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
        return new MobileUrlRes(url);
    }
    /**
     * 获取新增授权链接
     *
     * @param mobileUrlReq
     * @return
     */
    @Override
    public ResponseDTO<MobileUrlRes> getAuthorityUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {
        String url = applyProperties.getMobileBaseUrl() + "seagoldMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/saveAuthorizer.do";
        return ResponseDTO.createBySuccess("获取新增授权链接成功",new MobileUrlRes(url));
    }
    /**
     * 分页获取活动
     *
     * @param seagoldListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<SeagoldListRes>> getSeagoldList(BusUser busUser, SeagoldListPageReq seagoldListPageReq) {
        Page<SeagoldListRes> page = new Page<>(seagoldListPageReq.getCurrent(),seagoldListPageReq.getSize());
        Map<String,Object> map = new HashMap<>();
        Date date = new Date();
        map.put("nowTime",date);
        map.put("status",seagoldListPageReq.getStatus());
        map.put("name",seagoldListPageReq.getName());
        map.put("busId",busUser.getId());
        List<SeagoldListRes> seagoldListResList = seagoldMainDAO.getSeagoldList(page,map);
        for (SeagoldListRes seagoldListRes : seagoldListResList){
            if(seagoldListRes.getActivityBeginTime().getTime() > date.getTime()){
                seagoldListRes.setIsEdit(1);
                seagoldListRes.setStatus(0);
            }else if(seagoldListRes.getActivityEndTime().getTime() < date.getTime()){
                seagoldListRes.setIsEdit(0);
                seagoldListRes.setStatus(2);
            }else {
                seagoldListRes.setIsEdit(0);
                seagoldListRes.setStatus(1);
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",seagoldListResList,pageDTO);
    }
    /**
     * 分页获取中奖记录
     *
     * @param seagoldApplyListPageReq
     * @return
     */
    @Override
    public ResponseDTO<List<SeagoldApplyListRes>> getSeagoldApplyList(BusUser busUser, SeagoldApplyListPageReq seagoldApplyListPageReq) {
        Page<SeagoldApplyListRes> page = new Page<>(seagoldApplyListPageReq.getCurrent(),seagoldApplyListPageReq.getSize());
        List<SeagoldApplyListRes> seagoldApplyListResList = seagoldCashPrizeApplyDAO.queryRecodList(page,seagoldApplyListPageReq);
        if(seagoldApplyListResList.size() > 0) {
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < seagoldApplyListResList.size(); i++) {
                ids.append(seagoldApplyListResList.get(i).getMemberId());
                if (seagoldApplyListResList.size() > 1 && i < seagoldApplyListResList.size() - 1) {
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
            for (int i = 0; i < seagoldApplyListResList.size(); i++) {
                for (int j = 0; j < memberResList.size(); j++) {
                    if (memberResList.get(j).getId().intValue() == seagoldApplyListResList.get(i).getMemberId().intValue()) {
                        seagoldApplyListResList.get(i).setNickname(memberResList.get(j).getNickname());
                    }
                }
                if (CommonUtil.isEmpty(seagoldApplyListResList.get(i).getMemberName())) {
                    seagoldApplyListResList.get(i).setNickname("游客");
                }
            }
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",seagoldApplyListResList,pageDTO);
    }
    /**
     * 获取活动数量
     *
     */
    @Override
    public ResponseDTO<SeagoldCountRes> getSeagoldCount(BusUser busUser) {
        SeagoldCountRes seagoldCountRes = new SeagoldCountRes();
        Date date = new Date();
        Map<String,Object> params = new HashMap<>();
        params.put("nowTime",date);
        params.put("busId",busUser.getId());
        Map<String,Object> countMap = seagoldMainDAO.getCount(params);
        seagoldCountRes.setCount2(CommonUtil.isNotEmpty(countMap.get("count2"))?CommonUtil.toInteger(countMap.get("count2")):0);
        seagoldCountRes.setCount3(CommonUtil.isNotEmpty(countMap.get("count3"))?CommonUtil.toInteger(countMap.get("count3")):0);
        seagoldCountRes.setCount4(CommonUtil.isNotEmpty(countMap.get("count4"))?CommonUtil.toInteger(countMap.get("count4")):0);
        seagoldCountRes.setCount1(seagoldCountRes.getCount2()+seagoldCountRes.getCount3()+seagoldCountRes.getCount4());
        return ResponseDTO.createBySuccess("获取成功",seagoldCountRes);
    }
    /**
     * 发放奖品
     *
     * @return
     */
    @Override
    public ResponseDTO editSeagoldApply(BusUser busUser, SeagoldApplyIdReq seagoldApplyIdReq) {
        SeagoldCashPrizeApply seagoldCashPrizeApply = seagoldCashPrizeApplyService.selectById(seagoldApplyIdReq.getId());
        if(CommonUtil.isNotEmpty(seagoldCashPrizeApply)){
            SeagoldMain seagoldMain = seagoldMainService.selectById(seagoldCashPrizeApply.getActId());
            SeagoldPrize seagoldPrize = seagoldPrizeService.selectById(seagoldCashPrizeApply.getPrizeId());
            if(seagoldPrize.getType() != 4){//非兑奖
                if (DateTimeKit.laterThanNow(seagoldMain.getCashPrizeBeginTime())) {
                    //"未到兑奖时间！"
                    throw  new SeagoldException(ResponseEnums.SEAGOLD_HAS3);
                } else if (!DateTimeKit.laterThanNow(seagoldMain.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new SeagoldException(ResponseEnums.SEAGOLD_HAS4);
                }
            }
            if (seagoldCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                seagoldCashPrizeApply.setStatus(2);
                seagoldCashPrizeApply.setCashTime(new Date());
                seagoldCashPrizeApplyService.updateById(seagoldCashPrizeApply);
            } else if (seagoldCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new SeagoldException(ResponseEnums.SEAGOLD_HAS1);
            }else{//还未提交
                throw  new SeagoldException(ResponseEnums.SEAGOLD_HAS2);
            }
        }
        return ResponseDTO.createBySuccess("发放成功");
    }
    /**
     * 获取奖品类型列表
     *
     * @return
     */
    @Override
    public ResponseDTO<List<SeagoldPrizeTypeListRes>> getDemolitionPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<SeagoldPrizeTypeListRes> seagoldPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                SeagoldPrizeTypeListRes seagoldPrizeTypeListRes = new SeagoldPrizeTypeListRes();
                seagoldPrizeTypeListRes.setName(dictApiRes.getItemValue());
                seagoldPrizeTypeListRes.setValue(dictApiRes.getItemKey());
                seagoldPrizeTypeListResList.add(seagoldPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",seagoldPrizeTypeListResList);
    }
    /**
     * 删除核销授权
     *
     * @return
     */
    @Override
    public ResponseDTO removeSeagoldAuthority(BusUser busUser, SeagoldAuthorityIdsReq seagoldAuthorityIdsReq) {
        SeagoldMain seagoldMain = seagoldMainService.selectById(seagoldAuthorityIdsReq.getActId());
        if(CommonUtil.isNotEmpty(seagoldMain)) {
            if (seagoldMain.getBusId().intValue() != busUser.getId().intValue()) {
                throw new SeagoldException(ResponseEnums.DIFF_USER);
            }
        }else {
            throw new SeagoldException(ResponseEnums.SEAGOLD_HAS5);
        }
        SeagoldAuthority seagoldAuthority = new SeagoldAuthority();
        seagoldAuthority.setDeleteStatus(1);
        seagoldAuthorityService.update(seagoldAuthority,new EntityWrapper<SeagoldAuthority>().in("id",seagoldAuthorityIdsReq.getIds()));
        return ResponseDTO.createBySuccess("删除成功");
    }
    /**
     * 分页获取核销授权列表
     *
     * @return
     */
    @Override
    public ResponseDTO<List<SeagoldAuthorityListRes>> getSeagoldAuthorityList(BusUser busUser, SeagoldAuthorityListPageReq seagoldAuthorityListPageReq) {
        Page<SeagoldAuthority> page = new Page<>(seagoldAuthorityListPageReq.getCurrent(),seagoldAuthorityListPageReq.getSize());
        List<SeagoldAuthority> seagoldAuthorityList = seagoldAuthorityService.selectPage(page,
                new EntityWrapper<SeagoldAuthority>().eq("act_id",seagoldAuthorityListPageReq.getActId())
                        .eq("delete_status",0)).getRecords();
        List<SeagoldAuthorityListRes> seagoldAuthorityListResList = new ArrayList<>();
        for (SeagoldAuthority seagoldAuthority : seagoldAuthorityList){
            SeagoldAuthorityListRes seagoldAuthorityListRes = new SeagoldAuthorityListRes();
            BeanUtils.copyProperties(seagoldAuthority,seagoldAuthorityListRes);
            seagoldAuthorityListResList.add(seagoldAuthorityListRes);
        }
        PageDTO pageDTO = new PageDTO(page.getCurrent(),page.getTotal());
        return ResponseDTO.createBySuccessPage("获取成功",seagoldAuthorityListResList,pageDTO);
    }
    /**
     * 保存活动
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO saveSeagold(BusUser busUser, SeagoldSaveReq seagoldSaveReq) {
        SeagoldMain seagoldMain = null;
        Double num = 0.0;
        int f = 0;
        if(seagoldSaveReq.getId() == 0){//新增
            seagoldMain = new SeagoldMain();
            BeanUtils.copyProperties(seagoldSaveReq,seagoldMain);
            seagoldMain.setBusId(busUser.getId());
            seagoldMain.setCreatetime(new Date());
            seagoldMain.setFollowQrCode(seagoldMain.getFollowQrCode().split("/upload").length > 1
                    ?seagoldMain.getFollowQrCode().split("/upload")[1]:seagoldMain.getFollowQrCode());
            seagoldMainService.insert(seagoldMain);
        }else{//编辑
            seagoldMain = seagoldMainService.selectById(seagoldSaveReq.getId());
            if(CommonUtil.isEmpty(seagoldMain)){
                throw new SeagoldException(ResponseEnums.SEAGOLD_HAS5);
            }
            if(seagoldMain.getActivityBeginTime().getTime() < new Date().getTime()){
                throw new SeagoldException(ResponseEnums.SEAGOLD_HAS6);
            }
            if(seagoldMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new SeagoldException(ResponseEnums.DIFF_USER);
            }
            BeanUtils.copyProperties(seagoldSaveReq,seagoldMain);
            seagoldMain.setFollowQrCode(seagoldMain.getFollowQrCode().split("/upload").length > 1
                    ?seagoldMain.getFollowQrCode().split("/upload")[1]:seagoldMain.getFollowQrCode());
            seagoldMainService.updateById(seagoldMain);
            //删除
            //兑奖地址
            seagoldAddressService.delete(new EntityWrapper<SeagoldAddress>().eq("act_id",seagoldMain.getId()));
            //奖品
            List<SeagoldPrize> seagoldPrizes = seagoldPrizeService.selectList(new EntityWrapper<SeagoldPrize>().eq("act_id",seagoldMain.getId()));
            if(seagoldPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(SeagoldPrize seagoldPrize : seagoldPrizes){
                    if(seagoldPrize.getType() == 1){
                        num += seagoldPrize.getNum();
                        f = 1;
                    }
                    ids.add(seagoldPrize.getId());
                }
                seagoldPrizeImgService.delete(new EntityWrapper<SeagoldPrizeImg>().in("prize_id",ids));
            }
            seagoldPrizeService.delete(new EntityWrapper<SeagoldPrize>().eq("act_id",seagoldMain.getId()));
        }
        Double fenbi = 0.0;
        //奖品
        if(CommonUtil.isNotEmpty(seagoldSaveReq.getSeagoldPrizeReqs())){
            for(SeagoldPrizeReq seagoldPrizeReq :seagoldSaveReq.getSeagoldPrizeReqs()){
                if(seagoldPrizeReq.getType()==1){
                    if(seagoldPrizeReq.getNum() < 0){
                        throw new SeagoldException(ResponseEnums.SEAGOLD_HAS14);
                    }
                    fenbi += seagoldPrizeReq.getNum();
                }
                SeagoldPrize seagoldPrize = new SeagoldPrize();
                BeanUtils.copyProperties(seagoldPrizeReq,seagoldPrize);
                seagoldPrize.setActId(seagoldMain.getId());
                seagoldPrizeService.insert(seagoldPrize);
                if(seagoldPrizeReq.getSeagoldPrizeImgReqs().size() > 0){
                    for(SeagoldPrizeImgReq seagoldPrizeImgReq : seagoldPrizeReq.getSeagoldPrizeImgReqs()){
                        SeagoldPrizeImg seagoldPrizeImg = new SeagoldPrizeImg();
                        BeanUtils.copyProperties(seagoldPrizeImgReq,seagoldPrizeImg);
                        seagoldPrizeImg.setPrizeId(seagoldPrize.getId());
                        seagoldPrizeImg.setImgUrl(seagoldPrizeImg.getImgUrl().split("/upload").length>1?
                                seagoldPrizeImg.getImgUrl().split("/upload")[1]:seagoldPrizeImg.getImgUrl());
                        seagoldPrizeImgService.insert(seagoldPrizeImg);
                    }
                }
            }
        }
        if(fenbi > 0){//冻结粉币
            if( f > 0){
                if((fenbi-num) <= (0-num)){
                    throw new SeagoldException(ResponseEnums.SEAGOLD_HAS9);
                }
                // 判断账户中的粉币是否足够
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
                    throw new SeagoldException(ResponseEnums.SEAGOLD_HAS7);
                }
                UpdateFenbiReduceReq updateFenbiReduceReq = new UpdateFenbiReduceReq();
                updateFenbiReduceReq.setBusId(busUser.getId());
                updateFenbiReduceReq.setFkId(seagoldMain.getId());
                updateFenbiReduceReq.setFreType(41);
                updateFenbiReduceReq.setCount(CommonUtil.toDouble(fenbi-num));
                AxisResult axisResult = FenbiflowServer.updaterecUseCountVer2(updateFenbiReduceReq);
                if(axisResult.getCode() != 0){
                    throw new SeagoldException(ResponseEnums.SEAGOLD_HAS15);
                }
            }else {
                // 判断账户中的粉币是否足够
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
                    throw new SeagoldException(ResponseEnums.SEAGOLD_HAS7);
                }
                //构建冻结信息
                FenbiFlowRecord ffr=bulidFenFlow(busUser.getId(), fenbi, seagoldMain.getId(), 41, 1, "大海捞金活动支出", 0);
                // 保存冻结信息
                if(ffr!=null){
                    FenbiFlowRecordReq fenbiFlowRecordReq = new FenbiFlowRecordReq();
                    BeanUtils.copyProperties(ffr,fenbiFlowRecordReq);
                    AxisResult axisResult = FenbiflowServer.saveFenbiFlowRecord(fenbiFlowRecordReq);
                    if(axisResult.getCode() != 0){
                        throw new SeagoldException(ResponseEnums.SEAGOLD_HAS8);
                    }
                }
            }
        }
        //兑奖地址
        if(CommonUtil.isNotEmpty(seagoldSaveReq.getSeagoldAddressReqs())){
            for(SeagoldAddressReq seagoldAddressReq :seagoldSaveReq.getSeagoldAddressReqs()){
                SeagoldAddress seagoldAddress = new SeagoldAddress();
                BeanUtils.copyProperties(seagoldAddressReq,seagoldAddress);
                seagoldAddress.setActId(seagoldMain.getId());
                seagoldAddressService.insert(seagoldAddress);
            }
        }
        return ResponseDTO.createBySuccess("保存成功");
    }
    /**
     * 删除活动
     *
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO removeSeagold(BusUser busUser, SeagoldIdReq seagoldIdReq) {
        SeagoldMain seagoldMain = seagoldMainService.selectById(seagoldIdReq.getId());
        if(CommonUtil.isNotEmpty(seagoldMain)){
            if(seagoldMain.getActivityBeginTime().getTime() < new Date().getTime() && seagoldMain.getActivityEndTime().getTime() > new Date().getTime()){
                throw new SeagoldException(ResponseEnums.SEAGOLD_HAS10);
            }
            if(seagoldMain.getCashPrizeBeginTime().getTime() < new Date().getTime() && seagoldMain.getCashPrizeEndTime().getTime() > new Date().getTime()){
                throw new SeagoldException(ResponseEnums.SEAGOLD_HAS12);
            }
            List<SeagoldCashPrizeApply> seagoldCashPrizeApplies = seagoldCashPrizeApplyService.selectList(
                    new EntityWrapper<SeagoldCashPrizeApply>().eq("act_id",seagoldIdReq.getId()).eq("status",3));
            if(seagoldCashPrizeApplies.size() > 0 ){
                throw new SeagoldException(ResponseEnums.SEAGOLD_HAS11);

            }
            if(seagoldMain.getBusId().intValue() != busUser.getId().intValue()){
                throw new SeagoldException(ResponseEnums.DIFF_USER);
            }
            seagoldMainService.deleteById(seagoldMain.getId());
            //兑奖地址
            seagoldAddressService.delete(new EntityWrapper<SeagoldAddress>().eq("act_id",seagoldMain.getId()));
            //奖品
            List<SeagoldPrize> seagoldPrizes = seagoldPrizeService.selectList(new EntityWrapper<SeagoldPrize>().eq("act_id",seagoldMain.getId()));
            boolean ff = false;
            if(seagoldPrizes.size() > 0){
                List<Integer> ids = new ArrayList<>();
                for(SeagoldPrize seagoldPrize : seagoldPrizes){
                    ids.add(seagoldPrize.getId());
                    if(seagoldPrize.getType() == 1){
                        ff = true;
                    }
                }
                seagoldPrizeImgService.delete(new EntityWrapper<SeagoldPrizeImg>().in("prize_id",ids));
            }
            seagoldPrizeService.delete(new EntityWrapper<SeagoldPrize>().eq("act_id",seagoldMain.getId()));
            seagoldAuthorityService.delete(new EntityWrapper<SeagoldAuthority>().eq("act_id",seagoldMain.getId()));
            seagoldCashPrizeApplyService.delete(new EntityWrapper<SeagoldCashPrizeApply>().eq("act_id",seagoldMain.getId()));
            seagoldPlayRecordService.delete(new EntityWrapper<SeagoldPlayRecord>().eq("act_id",seagoldMain.getId()));
            seagoldReportService.delete(new EntityWrapper<SeagoldReport>().eq("act_id",seagoldMain.getId()));
            //删除冻结信息
            if(ff){
                FenbiSurplus fenbiSurplus = new FenbiSurplus();
                fenbiSurplus.setBusId(busUser.getId());
                fenbiSurplus.setFkId(seagoldMain.getId());
                fenbiSurplus.setFre_type(41);
                fenbiSurplus.setRec_type(1);
                AxisResult<FenbiFlowRecord> ffr = FenbiflowServer.getFenbiFlowRecord(fenbiSurplus);
                if(ffr!=null && ffr.getData() != null && ffr.getData().getRollStatus() == 1){//未回滚
                    // 获取冻结信息中粉币剩余量
                    FenbiSurplus fenbiSurplus1 = new FenbiSurplus();
                    fenbiSurplus1.setBusId(busUser.getId());
                    fenbiSurplus1.setFkId(seagoldMain.getId());
                    fenbiSurplus1.setRec_type(1);
                    fenbiSurplus1.setFre_type(41);
                    AxisResult axisResult = FenbiflowServer.rollbackFenbiRecord(fenbiSurplus1);
                    if(axisResult.getCode() != 0){
                        throw new SeagoldException(ResponseEnums.SEAGOLD_HAS13);
                    }
                }
            }
        }
        return ResponseDTO.createBySuccess("删除成功");

    }
    /**
     * 获取活动
     *
     * @return
     */
    @Override
    public ResponseDTO<SeagoldRes> getSeagoldRes(BusUser busUser, Integer id) {
        SeagoldMain seagoldMain = seagoldMainService.selectById(id);
        SeagoldRes seagoldRes = new SeagoldRes();
        if (CommonUtil.isNotEmpty(seagoldMain)) {
            BeanUtils.copyProperties(seagoldMain, seagoldRes);
            //奖品
            List<SeagoldPrize> seagoldPrizes = seagoldPrizeService.selectList(new EntityWrapper<SeagoldPrize>().eq("act_id", id));
            List<SeagoldPrizeReq> seagoldPrizeReqs = new ArrayList<>();
            if (seagoldPrizes.size() > 0) {
                for (SeagoldPrize seagoldPrize : seagoldPrizes) {
                    SeagoldPrizeReq seagoldPrizeReq = new SeagoldPrizeReq();
                    BeanUtils.copyProperties(seagoldPrize, seagoldPrizeReq);
                    List<SeagoldPrizeImg> seagoldPrizeImgs = seagoldPrizeImgService.selectList(new EntityWrapper<SeagoldPrizeImg>().eq("prize_id", seagoldPrize.getId()));
                    List<SeagoldPrizeImgReq> seagoldPrizeImgReqs = new ArrayList<>();
                    for (SeagoldPrizeImg seagoldPrizeImg : seagoldPrizeImgs) {
                        SeagoldPrizeImgReq seagoldPrizeImgReq = new SeagoldPrizeImgReq();
                        BeanUtils.copyProperties(seagoldPrizeImg, seagoldPrizeImgReq);
                        seagoldPrizeImgReqs.add(seagoldPrizeImgReq);
                    }
                    seagoldPrizeReq.setSeagoldPrizeImgReqs(seagoldPrizeImgReqs);
                    seagoldPrizeReqs.add(seagoldPrizeReq);
                }
            }
            seagoldRes.setSeagoldPrizeReqs(seagoldPrizeReqs);
            //兑奖地址
            List<SeagoldAddress> seagoldAddressList = seagoldAddressService.selectList(new EntityWrapper<SeagoldAddress>().eq("act_id", id));
            List<SeagoldAddressReq> seagoldAddressReqs = new ArrayList<>();
            if (seagoldAddressList.size() > 0) {
                for (SeagoldAddress seagoldAddress : seagoldAddressList) {
                    SeagoldAddressReq seagoldAddressReq = new SeagoldAddressReq();
                    BeanUtils.copyProperties(seagoldAddress, seagoldAddressReq);
                    seagoldAddressReqs.add(seagoldAddressReq);
                }
            }
            seagoldRes.setSeagoldAddressReqs(seagoldAddressReqs);
        }
        return ResponseDTO.createBySuccess("获取成功",seagoldRes);
    }
    @Override
    public Map<String, Object> exports(Map<String, Object> params) {
        Map<String, Object> msg = new HashMap<>();
        boolean result = true;
        String message = "生成成功！";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            // 获取活动信息
            SeagoldMain seagoldMain = seagoldMainService.selectById(CommonUtil.toInteger(params.get("actId")));
            // 获取中奖记录
            List<Map<String, Object>> list = seagoldCashPrizeApplyDAO.findExports(params);
            String title = "活动名称：" + seagoldMain.getName() + "，开始时间：" + sdf.format(seagoldMain.getActivityBeginTime()) + "结束时间："
                    + sdf.format(seagoldMain.getActivityEndTime());
            if(list.size() > 0){
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    ids.append(list.get(i).get("member_id"));
                    if (list.size() > 1 && i < list.size() - 1) {
                        ids.append(",");
                    }
                }
                MemberReq memberReq = new MemberReq();
                memberReq.setBusId(seagoldMain.getBusId());
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
                        if (memberResList.get(j).getId().intValue() == CommonUtil.toInteger(list.get(i).get("member_id")).intValue()) {
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
            msg.put("fileName", seagoldMain.getName());
        } catch (Exception e) {
            msg.put("result", false);
            msg.put("message", "拆礼盒活动记录导出excel失败！");
            e.printStackTrace();
        } finally {
            msg.put("result", result);
            msg.put("message", message);
        }
        return msg;
    }

    /**
     * 构建冻结信息
     *
     *
     * @param busId
     *            用户ID
     * @param recCount
     *            总数或总数量
     * @param fkId
     *            外键
     * @param freeType
     *            冻结类型
     * @param recType
     *            1-粉币，2-流量
     * @param recDesc
     *            描述
     * @param flowType
     *            如果是流量则传，否则传入0
     * @return
     */
    public FenbiFlowRecord bulidFenFlow(Integer busId, Double recCount, Integer fkId, Integer freeType, Integer recType,
                                        String recDesc, Integer flowType) {
        FenbiFlowRecord fb = new FenbiFlowRecord();
        fb.setBusUserId(busId);
        fb.setRecCount(recCount);
        fb.setRecFkId(fkId);
        fb.setRecFreezeType(freeType);
        fb.setRecType(recType);
        fb.setRecDesc(recDesc);
        fb.setFlowType(1);
        fb.setFlowId(1);
        fb.setRecUseCount(0.0);
        fb.setRollStatus(1);
        return fb;
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
        createCell(wb, row1, 3, "成绩", font1);
        createCell(wb, row1, 4, "兑奖时间", font1);
        createCell(wb, row1, 5, "中奖人联系方式", font1);
        createCell(wb, row1, 6, "中奖人昵称", font1);
        createCell(wb, row1, 7, "状态", font1);
        createCell(wb, row1, 8, "兑奖码", font1);
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
            createCell(wb, rowData, 1, delWithColumn(map.get("prize_name")), font1);
            String turPriType = delWithColumn(map.get("type"));
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
                if ("6".equals(turPriType)) {// 积分
                    priTypeName = "积分";
                    priTypeUnit = "分";
                }
                if ("7".equals(turPriType)) {// 优惠券
                    priTypeName = "优惠券包";
                    priTypeUnit = "个";
                }
            }
            createCell(wb, rowData, 2, priTypeName + "/" + delWithColumn(map.get("prize_unit")) + priTypeUnit, font1);

            createCell(wb, rowData, 3, delWithColumn(map.get("score")), font1);
            createCell(wb, rowData, 4,
                    delWithColumn(CommonUtil.isEmpty(map.get("cashTime")) ? ""
                            : DateTimeKit.format(
                            DateTimeKit.parseDate(map.get("cashTime").toString(), "yyyy/MM/dd HH:mm:ss"),
                            "yyyy-MM-dd HH:mm")),
                    font1);
            createCell(wb, rowData, 5, delWithColumn(map.get("member_phone")), font1);
            createCell(wb, rowData, 6,
                    CommonUtil.isEmpty(map.get("nickname")) ? "游客" : map.get("nickname").toString(),
                    font1);
            if ("1".equals(delWithColumn(map.get("status")).toString())) {
                createCell(wb, rowData, 7, "未兑奖", font1);
            } else if ("2".equals(delWithColumn(map.get("status")).toString())) {
                createCell(wb, rowData, 7, "已兑奖", font1);
            } else {
                createCell(wb, rowData, 7, "已提交", font1);
            }
            createCell(wb, rowData, 8, delWithColumn(map.get("sn_code")), font1);

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
}
