package com.gt.game.core.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.api.util.HttpClienUtils;
import com.gt.api.util.RequestUtils;
import com.gt.axis.content.AxisContent;
import com.gt.axis.content.AxisResult;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.common.req.FansInfoReq;
import com.gt.game.core.bean.common.req.MemberListPageReq;
import com.gt.game.core.bean.common.res.MemberListPageRes;
import com.gt.game.core.dao.common.FansInfoMapper;
import com.gt.game.core.exception.common.MemberException;
import com.gt.game.core.service.common.MemberService;
import com.gt.game.core.util.CommonUtil;
import com.gt.game.core.util.DateTimeKit;
import com.gt.game.core.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 粉丝 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    FansInfoMapper fansInfoMapper;

//    @Override
//    public ResponseDTO<List<MemberListPageRes>> getMemberList(WxPublicUsers wxPublicUsers, MemberListPageReq memberListPageReq) {
//        Page<MemberListPageRes> page = new Page<>(memberListPageReq.getCurrent(),memberListPageReq.getSize());
//        Map<String,Object> map = new HashMap<>();
//        map.put("wxUserId",wxPublicUsers.getUserName());
//        map.put("search",memberListPageReq.getMemberName());
//        List<MemberListPageRes> listPageResList = fansInfoMapper.getMemberList(page,map);
//        PageDTO pageDTO = new PageDTO(page.getPages(),page.getTotal());
//        return ResponseDTO.createBySuccessPage("获取成功",listPageResList,pageDTO);
//    }
    @Override
    public ResponseDTO<List<MemberListPageRes>> getMemberList(BusUser busUser, MemberListPageReq memberListPageReq) {
        PageDTO pageDTO = null;
        List<MemberListPageRes> listPageResList = new ArrayList<>();
        FansInfoReq fansInfoReq = new FansInfoReq();
        fansInfoReq.setBusId(36);
        fansInfoReq.setCurPage(memberListPageReq.getCurrent());
        fansInfoReq.setSearch(memberListPageReq.getMemberName());
        fansInfoReq.setPageSize(memberListPageReq.getSize());
        RequestUtils<FansInfoReq> reqRequestUtils = new RequestUtils<>();
        reqRequestUtils.setReqdata(fansInfoReq);
        String messsageJson = JSONObject.toJSONString(reqRequestUtils);
        String url = AxisContent.getInstance().getWxmpUrl() + "8A5DA52E/fanInfo/6F6D9AD2/79B4DE7C/6F6D9AD2/79B4DE7C/getPage.do";
        Map<String, Object> resMap = HttpClienUtils.reqPost(messsageJson, url, Map.class, AxisContent.getInstance().getWxmpSignKey());
        int code = (int) resMap.get("code");
        if(code != 0){
            throw new MemberException(ResponseEnums.COMMON_HAS23);
        }
        if(CommonUtil.isNotEmpty(resMap.get("data"))){
            String json = resMap.get("data").toString();
            Map<String,Object> map = JsonUtil.json2Map(json);
            pageDTO = new PageDTO(CommonUtil.isNotEmpty(map.get("pageCount"))?CommonUtil.toInteger(map.get("pageCount")):0,CommonUtil.isNotEmpty(map.get("rowCount"))?CommonUtil.toInteger(map.get("rowCount")):0);
           if(CommonUtil.isNotEmpty(map.get("subList"))){
               List<Map<String,Object>> list = JsonUtil.json2List(map.get("subList").toString());
               for(int i = 0 ; i < list.size() ; i++){
                   MemberListPageRes memberListPageRes = new MemberListPageRes();
                   memberListPageRes.setHeadimgurl(CommonUtil.isNotEmpty(list.get(i).get("headimgurl"))?list.get(i).get("headimgurl").toString():"");
                   memberListPageRes.setCity(CommonUtil.isNotEmpty(list.get(i).get("city"))?list.get(i).get("city").toString():"");
                   memberListPageRes.setId(CommonUtil.isNotEmpty(list.get(i).get("id"))?CommonUtil.toInteger(list.get(i).get("id")):0);
                   memberListPageRes.setName(CommonUtil.isNotEmpty(list.get(i).get("name"))?list.get(i).get("name").toString():"未分组");
                   memberListPageRes.setOpenid(CommonUtil.isNotEmpty(list.get(i).get("openid"))?list.get(i).get("openid").toString():"");
                   memberListPageRes.setSex(CommonUtil.isNotEmpty(list.get(i).get("sex"))?CommonUtil.toInteger(list.get(i).get("sex")):3);
                   memberListPageRes.setNickname(CommonUtil.isNotEmpty(list.get(i).get("nickname"))?list.get(i).get("nickname").toString():"未知用户");
                   memberListPageRes.setJointime(CommonUtil.isNotEmpty(list.get(i).get("jointime"))? new Date(Long.parseLong(list.get(i).get("jointime").toString())):null);
                   listPageResList.add(memberListPageRes);
               }
           }
        }else {
            pageDTO = new PageDTO(0,0);
        }

//        String msg = resMap.get("msg").toString();
//        List<CityRes> listProvinceRes = null;
//        if (resMap.get("data") != null){
//            String json = resMap.get("data").toString();
//            listProvinceRes = JSON.parseArray(json, CityRes.class);
//        }
//        AxisResult<List<CityRes>> axisResult = AxisResult.create(code, msg, listProvinceRes);
        return ResponseDTO.createBySuccessPage("获取成功",listPageResList,pageDTO);
    }
}
