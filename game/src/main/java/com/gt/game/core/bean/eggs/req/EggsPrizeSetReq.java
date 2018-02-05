package com.gt.game.core.bean.eggs.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("砸金蛋奖品设置请求类")
public class EggsPrizeSetReq {

	@ApiModelProperty("奖品类型")
	private Integer eggPrizeType;

	@ApiModelProperty("奖品数额")
	private  Integer eggPrizeLimit;

	@ApiModelProperty("奖品名称")
	private  String eggPrizeName;

	@ApiModelProperty("奖品数量")
	private  Integer eggPrizeNums;

	@ApiModelProperty("中奖概率")
	private double eggPrizeChance;

	@ApiModelProperty("中奖人Id")
	private String openId;

	@ApiModelProperty("中奖人昵称")
	private String nickname;

	public Integer getEggPrizeType() {
		return eggPrizeType;
	}

	public void setEggPrizeType(Integer eggPrizeType) {
		this.eggPrizeType = eggPrizeType;
	}

	public Integer getEggPrizeLimit() {
		return eggPrizeLimit;
	}

	public void setEggPrizeLimit(Integer eggPrizeLimit) {
		this.eggPrizeLimit = eggPrizeLimit;
	}

	public String getEggPrizeName() {
		return eggPrizeName;
	}

	public void setEggPrizeName(String eggPrizeName) {
		this.eggPrizeName = eggPrizeName;
	}

	public Integer getEggPrizeNums() {
		return eggPrizeNums;
	}

	public void setEggPrizeNums(Integer eggPrizeNums) {
		this.eggPrizeNums = eggPrizeNums;
	}

	public double getEggPrizeChance() {
		return eggPrizeChance;
	}

	public void setEggPrizeChance(double eggPrizeChance) {
		this.eggPrizeChance = eggPrizeChance;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
