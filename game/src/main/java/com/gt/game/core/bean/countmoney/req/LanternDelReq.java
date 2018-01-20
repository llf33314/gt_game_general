package com.gt.game.core.bean.countmoney.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("删除元宵点灯活动请求类")
public class LanternDelReq {

	@ApiModelProperty("主键id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
