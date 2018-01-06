package com.gt.game.core.bean.loveArrow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 一箭穿心
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一箭穿心活动保存地址对象")
public class LoveArrowAddressReq {

	@ApiModelProperty("领奖地址")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
