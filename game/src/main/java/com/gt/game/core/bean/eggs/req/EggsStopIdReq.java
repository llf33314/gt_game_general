package com.gt.game.core.bean.eggs.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("砸金蛋活动开始/暂停请求类")
public class EggsStopIdReq {

	@ApiModelProperty("活动id")
	private Integer id;

	@ApiModelProperty("活动状态 1.开始 2.暂停")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
