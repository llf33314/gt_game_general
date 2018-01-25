package com.gt.game.core.bean.turntable.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("大转盘活动奖品设置请求类")
public class TurntablePrizeSetReq {

	@ApiModelProperty("奖品类型")
	private Integer turPrizeType;

	@ApiModelProperty("奖品数额")
	private  Integer turPrizeLimit;

	@ApiModelProperty("奖品名称")
	private  String turPrizeName;

	@ApiModelProperty("奖品数量")
	private  Integer turPrizeNums;

	@ApiModelProperty("中奖概率")
	private float turPrizeChance;

	@ApiModelProperty("中奖人")
	private String nickname;

	public Integer getTurPrizeType() {
		return turPrizeType;
	}

	public void setTurPrizeType(Integer turPrizeType) {
		this.turPrizeType = turPrizeType;
	}

	public Integer getTurPrizeLimit() {
		return turPrizeLimit;
	}

	public void setTurPrizeLimit(Integer turPrizeLimit) {
		this.turPrizeLimit = turPrizeLimit;
	}

	public String getTurPrizeName() {
		return turPrizeName;
	}

	public void setTurPrizeName(String turPrizeName) {
		this.turPrizeName = turPrizeName;
	}

	public Integer getTurPrizeNums() {
		return turPrizeNums;
	}

	public void setTurPrizeNums(Integer turPrizeNums) {
		this.turPrizeNums = turPrizeNums;
	}

	public float getTurPrizeChance() {
		return turPrizeChance;
	}

	public void setTurPrizeChance(float turPrizeChance) {
		this.turPrizeChance = turPrizeChance;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
