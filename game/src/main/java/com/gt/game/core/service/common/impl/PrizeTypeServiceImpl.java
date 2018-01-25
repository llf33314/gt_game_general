package com.gt.game.core.service.common.impl;

import com.gt.api.bean.session.BusUser;
import com.gt.axis.bean.wxmp.dict.DictApiReq;
import com.gt.axis.bean.wxmp.dict.DictApiRes;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.wxmp.DictServer;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.common.res.PrizeTypeListRes;
import com.gt.game.core.service.common.PrizeTypeService;
import com.gt.game.core.util.CommonUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 * 粉丝 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Service
public class PrizeTypeServiceImpl implements PrizeTypeService {

    @Override
    public ResponseDTO<List<PrizeTypeListRes>> getPrizeTypeOne(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<PrizeTypeListRes> luckPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                if(!dictApiRes.getItemKey().equals("3") && !dictApiRes.getItemKey().equals("5")){
                    PrizeTypeListRes luckPrizeTypeListRes = new PrizeTypeListRes();
                    luckPrizeTypeListRes.setName(dictApiRes.getItemValue());
                    luckPrizeTypeListRes.setValue(CommonUtil.toInteger(dictApiRes.getItemKey()));
                    luckPrizeTypeListResList.add(luckPrizeTypeListRes);
                }
            }
        }
        return ResponseDTO.createBySuccess("获取成功",luckPrizeTypeListResList);
    }
    @Override
    public ResponseDTO<List<PrizeTypeListRes>> getPrizeType(BusUser busUser) {
        DictApiReq dictApiReq = new DictApiReq();
        dictApiReq.setStyle("1062");
        AxisResult<List<DictApiRes>> axisResult = null;
        try {
            axisResult =  DictServer.getDictApi(dictApiReq);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<PrizeTypeListRes> luckPrizeTypeListResList = new ArrayList<>();
        if(CommonUtil.isNotEmpty(axisResult) && CommonUtil.isNotEmpty(axisResult.getData())){
            for(DictApiRes dictApiRes : axisResult.getData()){
                    PrizeTypeListRes luckPrizeTypeListRes = new PrizeTypeListRes();
                    luckPrizeTypeListRes.setName(dictApiRes.getItemValue());
                    luckPrizeTypeListRes.setValue(CommonUtil.toInteger(dictApiRes.getItemKey()));
                    luckPrizeTypeListResList.add(luckPrizeTypeListRes);
            }
        }
        return ResponseDTO.createBySuccess("获取成功",luckPrizeTypeListResList);
    }
}
