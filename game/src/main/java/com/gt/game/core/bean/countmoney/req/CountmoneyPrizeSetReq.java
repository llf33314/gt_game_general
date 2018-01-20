package com.gt.game.core.bean.countmoney.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("疯狂数钱活动奖品设置请求类")
public class CountmoneyPrizeSetReq {

	@ApiModelProperty("奖品名称")
	private  String turPrizeName;

	@ApiModelProperty("奖品类型")
	private Integer turPrizeType;

	@ApiModelProperty("奖品数额")
	private  Integer turPrizeUnit;

	@ApiModelProperty("奖品数量")
	private  Integer turPrizeNums;

	public String getTurPrizeName() {
		return turPrizeName;
	}

	public void setTurPrizeName(String turPrizeName) {
		this.turPrizeName = turPrizeName;
	}

	public Integer getTurPrizeType() {
		return turPrizeType;
	}

	public void setTurPrizeType(Integer turPrizeType) {
		this.turPrizeType = turPrizeType;
	}

	public Integer getTurPrizeUnit() {
		return turPrizeUnit;
	}

	public void setTurPrizeUnit(Integer turPrizeUnit) {
		this.turPrizeUnit = turPrizeUnit;
	}

	public Integer getTurPrizeNums() {
		return turPrizeNums;
	}

	public void setTurPrizeNums(Integer turPrizeNums) {
		this.turPrizeNums = turPrizeNums;
	}
}
