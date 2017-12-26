package com.gt.game.core.bean.url;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 移动端访问链接
 * Created by psr on 2017/9/18 0018.
 */
@ApiModel(value = "移动端访问链接")
public class MobileUrlRes {

    public MobileUrlRes(){}

    public MobileUrlRes(String mobileUrl){
        this.mobileUrl = mobileUrl;
    }

    @ApiModelProperty(value = "移动端访问链接")
    private String mobileUrl;

    public String getMobileUrl() {
        return mobileUrl;
    }

    public void setMobileUrl(String mobileUrl) {
        this.mobileUrl = mobileUrl;
    }
}
