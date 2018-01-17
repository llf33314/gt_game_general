package com.gt.game.core.bean.tree.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("编辑幸运圣诞大礼包兑奖设置请求类")
public class TreeModfiyExpiryReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("兑奖期限")
	private Integer treeCashDay;

	@ApiModelProperty("兑奖地址")
	private String treeAddress;

	@ApiModelProperty("是否允许转赠送 0.不允许 1.允许")
	private Integer treeIsGive;

	@ApiModelProperty("兑奖提示")
	private String treeWinningTxt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTreeCashDay() {
		return treeCashDay;
	}

	public void setTreeCashDay(Integer treeCashDay) {
		this.treeCashDay = treeCashDay;
	}

	public String getTreeAddress() {
		return treeAddress;
	}

	public void setTreeAddress(String treeAddress) {
		this.treeAddress = treeAddress;
	}

	public Integer getTreeIsGive() {
		return treeIsGive;
	}

	public void setTreeIsGive(Integer treeIsGive) {
		this.treeIsGive = treeIsGive;
	}

	public String getTreeWinningTxt() {
		return treeWinningTxt;
	}

	public void setTreeWinningTxt(String treeWinningTxt) {
		this.treeWinningTxt = treeWinningTxt;
	}
}
