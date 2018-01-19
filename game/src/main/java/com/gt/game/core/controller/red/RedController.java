package com.gt.game.core.controller.red;


import com.gt.game.common.base.BaseController;
import com.gt.game.common.dto.ResponseDTO;
import com.gt.game.core.bean.url.MobileUrlReq;
import com.gt.game.core.service.red.RedService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 红包表 前端控制器
 * </p>
 *
 * @author zwq
 * @since 2018-01-19
 */
@Api(value = "/app/red", description = "让红包飞商家后台")
@RestController
@RequestMapping("/app/red")
public class RedController extends BaseController {

    @Autowired
    RedService redService;

    @Override
    protected ResponseDTO getMobileUrl(MobileUrlReq mobileUrlReq, HttpServletRequest request) {
        return null;
    }


}
