package com.gt.game.core.bean.lantern.res;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by psr on 2017/9/20 0020.
 */
@ApiModel(value = "元宵点灯活动总数返回类")
public class LanternCountActivityRes {

    @ApiModelProperty(value = "活动总数")
    private Integer count;

    @ApiModelProperty(value = "未开始活动总数")
    private Integer count2;

    @ApiModelProperty(value = "进行中活动总数")
    private Integer count3;

    @ApiModelProperty(value = "已结束活动总数")
    private Integer count4;


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount2() {
        return count2;
    }

    public void setCount2(Integer count2) {
        this.count2 = count2;
    }

    public Integer getCount3() {
        return count3;
    }

    public void setCount3(Integer count3) {
        this.count3 = count3;
    }

    public Integer getCount4() {
        return count4;
    }

    public void setCount4(Integer count4) {
        this.count4 = count4;
    }
}
