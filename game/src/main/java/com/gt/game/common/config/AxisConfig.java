package com.gt.game.common.config;

import com.gt.axis.content.AxisContent;
import com.gt.axis.content.AxisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by psr on 2017/9/8 0008.
 */
@Configuration
@EnableConfigurationProperties(AxisProperties.class)
public class AxisConfig {

    @Autowired
    private AxisProperties axisProperties;

    @Bean
    public AxisContent axisContentBean(){
        AxisContent axisContent = AxisContent.getInstance();
        axisContent.setWxmpSignKey(axisProperties.getWxmpSignKey());
        axisContent.setWxmpUrl(axisProperties.getWxmpUrl());
        axisContent.setMemberSignKey(axisProperties.getMemberSignKey());
        axisContent.setMemberUrl(axisProperties.getMemberUrl());
        axisContent.setServiceUrl(axisProperties.getServiceUrl());
        axisContent.setSignKey(axisProperties.getSignKey());
        return axisContent;
    }

}
