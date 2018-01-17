package com.gt.game.core.bean.shakeLuck.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 摇手气
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("摇手气活动保存地址对象")
public class ShakeluckAddressReq {

	@ApiModelProperty("领奖地址")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
