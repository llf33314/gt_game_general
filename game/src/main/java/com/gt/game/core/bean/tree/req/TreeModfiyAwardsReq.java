package com.gt.game.core.bean.tree.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("编辑圣诞大礼包活动奖项设置请求类")
public class TreeModfiyAwardsReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("奖品设置")
	private List<TreePrizeSetReq> prizeSetList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<TreePrizeSetReq> getPrizeSetList() {
		return prizeSetList;
	}

	public void setPrizeSetList(List<TreePrizeSetReq> prizeSetList) {
		this.prizeSetList = prizeSetList;
	}
}
