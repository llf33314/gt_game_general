package com.gt.game.core.bean.url;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * 移动链接请求类
 * Created by psr on 2017/9/18 0018.
 */
@ApiModel(value = "移动链接请求类")
public class MobileUrlReq {

    @ApiModelProperty(value = "活动id")
    @NotNull(message = "活动id为空")
    private Integer mainId; // 活动id

    public Integer getMainId() {
        return mainId;
    }

    public void setMainId(Integer mainId) {
        this.mainId = mainId;
    }
}
