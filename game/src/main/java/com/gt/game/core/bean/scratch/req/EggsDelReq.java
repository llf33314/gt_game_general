package com.gt.game.core.bean.scratch.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("批量删除砸金蛋请求类")
public class EggsDelReq {

	@ApiModelProperty("主键id")
	private List<Integer> id;

	public List<Integer> getId() {
		return id;
	}

	public void setId(List<Integer> id) {
		this.id = id;
	}
}
