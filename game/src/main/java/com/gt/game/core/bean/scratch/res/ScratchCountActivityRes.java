package com.gt.game.core.bean.scratch.res;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by psr on 2017/9/20 0020.
 */
@ApiModel(value = "砸金蛋活动总数返回类")
public class ScratchCountActivityRes {

    @ApiModelProperty(value = "活动总数")
    private Integer count1;

    @ApiModelProperty(value = "未开始活动总数")
    private Integer count2;

    @ApiModelProperty(value = "进行中活动总数")
    private Integer count3;

    @ApiModelProperty(value = "已结束活动总数")
    private Integer count4;

    @ApiModelProperty(value = "已暂停活动总数")
    private Integer count5;


    public Integer getCount1() {
        return count1;
    }

    public void setCount1(Integer count1) {
        this.count1 = count1;
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

    public Integer getCount5() {
        return count5;
    }

    public void setCount5(Integer count5) {
        this.count5 = count5;
    }
}
