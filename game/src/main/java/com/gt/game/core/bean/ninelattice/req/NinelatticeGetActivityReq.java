package com.gt.game.core.bean.ninelattice.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("通过活动id查询幸运九宫格活动请求类")
public class NinelatticeGetActivityReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
