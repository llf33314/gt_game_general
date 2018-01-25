package com.gt.game.core.bean.turntable.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("大转盘活动暂停/开始活动请求类")
public class TurntableStopIdReq {

	@ApiModelProperty("活动id")
	private Integer id;

	@ApiModelProperty("活动状态 1.开始 2.暂停")
	private Integer Status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}
}
