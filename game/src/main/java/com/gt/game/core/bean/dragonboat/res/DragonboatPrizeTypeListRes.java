package com.gt.game.core.bean.dragonboat.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("端午赛龙舟奖品类型返回类")
public class DragonboatPrizeTypeListRes {

	@ApiModelProperty("value")
	private String value;

	@ApiModelProperty("name")
	private String name;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
