package com.gt.game.core.bean.eggs.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("通过活动id查询圣诞大礼包活动请求类")
public class EggsGetActivityReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
