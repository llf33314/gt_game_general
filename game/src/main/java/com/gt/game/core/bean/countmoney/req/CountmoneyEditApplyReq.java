package com.gt.game.core.bean.countmoney.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("疯狂数钱活动中奖记录发放奖品请求类")
public class CountmoneyEditApplyReq {

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
