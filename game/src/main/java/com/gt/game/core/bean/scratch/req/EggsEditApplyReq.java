package com.gt.game.core.bean.scratch.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("中奖记录发放奖品请求类")
public class EggsEditApplyReq {

	/**
	 * 主键
	 */
	@ApiModelProperty("id")
	private  Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
