package com.gt.game.core.bean.scratch.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("批量删除刮刮乐活动请求类")
public class ScratchDelWinningReq {

	@ApiModelProperty("主键id")
	private List<Integer> id;

	public List<Integer> getId() {
		return id;
	}

	public void setId(List<Integer> id) {
		this.id = id;
	}
}
