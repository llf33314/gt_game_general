package com.gt.game.core.service.luck.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gt.api.bean.session.BusUser;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiFlowRecord;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiFlowRecordReq;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiSurplus;
import com.gt.axis.bean.wxmp.fenbiflow.UpdateFenbiReduceReq;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.wxmp.FenbiflowServer;
import com.gt.game.common.config.ApplyProperties;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.luck.req.LuckDetailReq;
import com.gt.game.core.bean.luck.req.LuckIdReq;
import com.gt.game.core.bean.luck.req.LuckListPageReq;
import com.gt.game.core.bean.luck.req.LuckReq;
import com.gt.game.core.bean.luck.res.LuckCountRes;
import com.gt.game.core.bean.luck.res.LuckListRes;
import com.gt.game.core.bean.luck.res.LuckRes;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public MobileUrlRes getMobileUrl(BusUser busUser, MobileUrlReq mobileUrlReq) {
        String url = applyProperties.getMobileBaseUrl() + "LuckMobile/"+ mobileUrlReq.getMainId() + "/79B4DE7C/toPhoneIndex.do";
        return new MobileUrlRes(url);
    }
    /**
     * 获取活动数量
     * @return
     */
    @Override
    public ResponseDTO<LuckCountRes> getLuckCount(BusUser busUser) {
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
    public ResponseDTO<List<LuckListRes>> getLuckList(BusUser busUser, LuckListPageReq LuckListPageReq) {
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
    public ResponseDTO<LuckRes> getLuck(BusUser busUser, LuckIdReq luckIdReq) {
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
    public ResponseDTO saveLuck(BusUser busUser, LuckReq luckReq) {
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
                if (busUser.getFansCurrency().doubleValue() < (fenbi-num)) {
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
                if(busUser.getFansCurrency().doubleValue() < fenbi.doubleValue()){
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
    public ResponseDTO removeLuck(BusUser busUser, LuckIdReq luckIdReq) {
        LuckMain luckMain = luckMainService.selectById(luckIdReq.getId());
        if(CommonUtil.isNotEmpty(luckMain)){
            if(luckMain.getLuckBeginTime().getTime() < new Date().getTime() && luckMain.getLuckEndTime().getTime() > new Date().getTime()){
                throw new LuckException(ResponseEnums.LOVEARROW_HAS10);
            }
            if(luckMain.getLuckBeginTime().getTime() < new Date().getTime() && luckMain.getLuckEndTime().getTime() > new Date().getTime()){
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
}
