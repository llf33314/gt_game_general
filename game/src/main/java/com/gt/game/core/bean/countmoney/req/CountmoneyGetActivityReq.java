package com.gt.game.core.bean.countmoney.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("通过活动id查询疯狂数钱活动请求类")
public class CountmoneyGetActivityReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
