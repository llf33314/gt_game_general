package com.gt.game.core.bean.eggs.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by psr on 2017/9/21 0021.
 */
@ApiModel(value = "获取砸金蛋活动总数请求类")
public class EggsCountActivityReq {

    @ApiModelProperty("活动名称")
    private  String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
