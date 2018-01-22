package com.gt.game.core.bean.countmoney.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel("批量删除疯狂数钱请求类")
public class CountmoneyDelWinningReq {

	@ApiModelProperty("主键id")
	private List<Integer> id;

	public List<Integer> getId() {
		return id;
	}

	public void setId(List<Integer> id) {
		this.id = id;
	}
}
