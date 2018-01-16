package com.gt.game.core.bean.tree.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("编辑圣诞大礼包活动规则设置请求类")
public class TreeModfiyRuleReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("抽奖总数")
	private Integer treeCountOfAll;

	@ApiModelProperty("抽奖次数")
	private Integer treeCountOfDay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTreeCountOfAll() {
		return treeCountOfAll;
	}

	public void setTreeCountOfAll(Integer treeCountOfAll) {
		this.treeCountOfAll = treeCountOfAll;
	}

	public Integer getTreeCountOfDay() {
		return treeCountOfDay;
	}

	public void setTreeCountOfDay(Integer treeCountOfDay) {
		this.treeCountOfDay = treeCountOfDay;
	}
}
