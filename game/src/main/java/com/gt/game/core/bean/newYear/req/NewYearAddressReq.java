package com.gt.game.core.bean.newYear.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 元旦跨年跳跃主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("元旦跨年跳跃活动保存地址对象")
public class NewYearAddressReq {

	@ApiModelProperty("领奖地址")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
