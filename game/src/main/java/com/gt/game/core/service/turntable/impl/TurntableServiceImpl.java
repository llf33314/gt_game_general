package com.gt.game.core.service.turntable.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.axis.bean.member.member.MemberReq;
import com.gt.axis.bean.member.member.MemberRes;
import com.gt.axis.bean.wxmp.dict.DictApiReq;
import com.gt.axis.bean.wxmp.dict.DictApiRes;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.member.MemberServer;
import com.gt.axis.server.wxmp.DictServer;
import com.gt.game.common.dto.PageDTO;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.common.enums.ResponseEnums;
import com.gt.game.core.bean.scratch.res.ScratchListRes;
import com.gt.game.core.bean.tree.req.*;
import com.gt.game.core.bean.tree.res.*;
import com.gt.game.core.bean.turntable.req.TurntableListReq;
import com.gt.game.core.bean.turntable.res.TurntableListRes;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.bean.url.MobileUrlRes;
import com.gt.game.core.dao.tree.TreeWinningDAO;
import com.gt.game.core.entity.scratch.ScratchMain;
import com.gt.game.core.entity.tree.TreeDetail;
import com.gt.game.core.entity.tree.TreeMain;
import com.gt.game.core.entity.tree.TreeWinning;
import com.gt.game.core.entity.turntable.TurntableMain;
import com.gt.game.core.exception.tree.TreeException;
import com.gt.game.core.service.tree.TreeDetailService;
import com.gt.game.core.service.tree.TreeMainService;
import com.gt.game.core.service.tree.TreeService;
import com.gt.game.core.service.tree.TreeWinningService;
import com.gt.game.core.service.turntable.TurntableMainService;
import com.gt.game.core.service.turntable.TurntableService;
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
 * 大转盘 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Service
public class TurntableServiceImpl implements TurntableService {

    @Autowired
    TurntableMainService turntableMainService;

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
     * 分页获取大转盘活动列表
     * @param loginPbUser
     * @param turntableListReq
     * @return
     */
    @Override
    public ResponseDTO<List<TurntableListRes>> getTurntableList(WxPublicUsers loginPbUser, TurntableListReq turntableListReq) {

        EntityWrapper<TurntableMain> entityWrapper = new EntityWrapper();
        entityWrapper.eq("act_wx_userid", loginPbUser.getId());
        entityWrapper.orderBy("id", false);
        entityWrapper.like(CommonUtil.isNotEmpty(turntableListReq.getName()), "act_name", turntableListReq.getName());
        if (turntableListReq.getStatus() != -1) {   // TODO -1 全部 0 未开始 1 进行中 2 已结束 3.已暂停

            if (turntableListReq.getStatus() == 0) {
                entityWrapper.where("act_beginTime > {0}", new Date());
            }
            if (turntableListReq.getStatus() == 1) {
                entityWrapper.where("act_beginTime <= {0}", new Date());
                entityWrapper.and();
                entityWrapper.where("act_endTime > {0}", new Date());
            }
            if (turntableListReq.getStatus() == 2) {
                entityWrapper.where("act_endTime <= {0}", new Date());
            }
            if (turntableListReq.getStatus() == 3) {    //TODO   已暂停
                entityWrapper.eq("scr_status", 2);
            }
        }
        Page<TurntableMain> page = new Page<>(turntableListReq.getCurrent(), turntableListReq.getSize());
        List<TurntableMain> turntableMainList = turntableMainService.selectPage(page, entityWrapper).getRecords();

        List<TurntableListRes> turntableListResList = new ArrayList<>();
        for (TurntableMain turntableMain : turntableMainList) {
            TurntableListRes turntableListRes = new TurntableListRes();
            turntableListRes.setId(turntableMain.getId());
            turntableListRes.setName(turntableMain.getActName());
            turntableListRes.setActivityBeginTime(turntableMain.getActBeginTime());
            turntableListRes.setActivityEndTime(turntableMain.getActEndTime());

            if (turntableMain.getActStatus() == 2) {    //TODO   已暂停
                turntableListRes.setStatus(3);
            } else {
                Date date = new Date();
                if (turntableMain.getActBeginTime().getTime() > date.getTime()) {
                    turntableListRes.setStatus(0);
                } else if (turntableMain.getActBeginTime().getTime() <= date.getTime() && turntableMain.getActEndTime().getTime() >= date.getTime()) {
                    turntableListRes.setStatus(1);
                } else if (turntableMain.getActEndTime().getTime() < date.getTime()) {
                    turntableListRes.setStatus(2);
                }
            }
            turntableListResList.add(turntableListRes);
        }
        PageDTO pageDTO = new PageDTO(page.getPages(), page.getTotal());
        return ResponseDTO.createBySuccessPage("分页获取大转盘活动列表成功", turntableListResList, pageDTO);
    }
}