package com.gt.game.core.bean.seagold.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 大海捞金
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("大海捞金奖品类型返回对象")
public class SeagoldPrizeTypeListRes {

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
