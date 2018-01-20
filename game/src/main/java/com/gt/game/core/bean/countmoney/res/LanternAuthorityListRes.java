package com.gt.game.core.bean.countmoney.res;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by psr on 2017/9/20 0020.
 */
@ApiModel(value = "分页获取核销授权列表返回类")
public class LanternAuthorityListRes {

    /**
     * 主键
     */
    @ApiModelProperty("id")
    private Integer id;

    /**
     * 授权时间
     */
    @ApiModelProperty("创建时间")
    private Date createtime;
    /**
     * 权限人员姓名
     */
    @ApiModelProperty("核销员")
    private String memberName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }



}
