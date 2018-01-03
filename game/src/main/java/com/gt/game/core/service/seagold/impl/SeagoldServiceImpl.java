package com.gt.game.core.service.seagold.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.axis.bean.wxmp.bus.BusUser;
import com.gt.axis.bean.wxmp.dict.DictApiReq;
import com.gt.axis.bean.wxmp.dict.DictApiRes;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.member.MemberServer;
import com.gt.axis.server.wxmp.DictServer;
import com.gt.game.common.config.ApplyProperties;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.demolition.res.DemolitionListRes;
import com.gt.game.core.bean.seagold.req.*;
import com.gt.game.core.bean.seagold.res.*;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.seagold.SeagoldCashPrizeApplyDAO;
import com.gt.game.core.dao.seagold.SeagoldMainDAO;
import com.gt.game.core.entity.seagold.SeagoldAuthority;
import com.gt.game.core.entity.seagold.SeagoldCashPrizeApply;
import com.gt.game.core.entity.seagold.SeagoldMain;
import com.gt.game.core.entity.seagold.SeagoldPrize;
import com.gt.game.core.exception.seagold.SeagoldException;
import com.gt.game.core.service.seagold.*;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                    throw  new SeagoldException(ResponseEnums.DEMOLITION_HAS1);
                } else if (!DateTimeKit.laterThanNow(seagoldMain.getCashPrizeEndTime())) {
                    //""已过兑奖时间！";
                    throw  new SeagoldException(ResponseEnums.DEMOLITION_HAS1);
                }
            }
            if (seagoldCashPrizeApply.getStatus() == 3) {
                // 更改记录状态
                seagoldCashPrizeApply.setStatus(2);
                seagoldCashPrizeApply.setCashTime(new Date());
                seagoldCashPrizeApplyService.updateById(seagoldCashPrizeApply);
            } else if (seagoldCashPrizeApply.getStatus() == 2){//已兑奖
                throw  new SeagoldException(ResponseEnums.DEMOLITION_HAS1);
            }else{//还未提交
                throw  new SeagoldException(ResponseEnums.DEMOLITION_HAS2);
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
            throw new SeagoldException(ResponseEnums.DEMOLITION_HAS5);
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
}
