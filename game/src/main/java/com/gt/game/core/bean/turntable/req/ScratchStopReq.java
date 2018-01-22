package com.gt.game.core.bean.turntable.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("刮刮乐暂停活动请求类")
public class ScratchStopReq {

	@ApiModelProperty("主键id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
