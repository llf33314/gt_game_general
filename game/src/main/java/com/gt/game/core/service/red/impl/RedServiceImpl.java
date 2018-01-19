package com.gt.game.core.service.red.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gt.game.core.dao.red.RedPacketsDAO;
import com.gt.game.core.entity.red.RedPackets;
import com.gt.game.core.entity.red.RedPacketsReceiver;
import com.gt.game.core.service.red.RedPacketsReceiverService;
import com.gt.game.core.service.red.RedPacketsService;
import com.gt.game.core.service.red.RedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 红包表 服务实现类
 * </p>
 *
 * @author zwq
 * @since 2018-01-19
 */
@Service
public class RedServiceImpl implements RedService {

    @Autowired
    RedPacketsService redPacketsService;

    @Autowired
    RedPacketsReceiverService redPacketsReceiverService;


	
}
