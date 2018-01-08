package com.gt.game.core.bean.raiseflag.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 升国旗主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("升国旗奖品类型返回对象")
public class RaiseflagPrizeTypeListRes {

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
