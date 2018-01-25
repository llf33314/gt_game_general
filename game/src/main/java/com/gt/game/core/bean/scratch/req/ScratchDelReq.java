package com.gt.game.core.bean.scratch.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("批量删除刮刮乐请求类")
public class ScratchDelReq {

	@ApiModelProperty("主键id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
