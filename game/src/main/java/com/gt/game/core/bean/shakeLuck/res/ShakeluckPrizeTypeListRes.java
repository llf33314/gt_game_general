package com.gt.game.core.bean.shakeLuck.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 摇手气主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("摇手气奖品类型返回对象")
public class ShakeluckPrizeTypeListRes {

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
