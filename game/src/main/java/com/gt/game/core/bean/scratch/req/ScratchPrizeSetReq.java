package com.gt.game.core.bean.scratch.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("刮刮乐活动奖品设置请求类")
public class ScratchPrizeSetReq {

	@ApiModelProperty("奖品类型")
	private Integer scrPrizeType;

	@ApiModelProperty("奖品数额")
	private  Integer scrPrizeLimit;

	@ApiModelProperty("奖品名称")
	private  String scrPrizeName;

	@ApiModelProperty("奖品数量")
	private  Integer scrPrizeNums;

	@ApiModelProperty("中奖概率")
	private float scrPrizeChance;

	@ApiModelProperty("中奖人")
	private String nickname;

	public Integer getScrPrizeType() {
		return scrPrizeType;
	}

	public void setScrPrizeType(Integer scrPrizeType) {
		this.scrPrizeType = scrPrizeType;
	}

	public Integer getScrPrizeLimit() {
		return scrPrizeLimit;
	}

	public void setScrPrizeLimit(Integer scrPrizeLimit) {
		this.scrPrizeLimit = scrPrizeLimit;
	}

	public String getScrPrizeName() {
		return scrPrizeName;
	}

	public void setScrPrizeName(String scrPrizeName) {
		this.scrPrizeName = scrPrizeName;
	}

	public Integer getScrPrizeNums() {
		return scrPrizeNums;
	}

	public void setScrPrizeNums(Integer scrPrizeNums) {
		this.scrPrizeNums = scrPrizeNums;
	}

	public float getScrPrizeChance() {
		return scrPrizeChance;
	}

	public void setScrPrizeChance(float scrPrizeChance) {
		this.scrPrizeChance = scrPrizeChance;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
