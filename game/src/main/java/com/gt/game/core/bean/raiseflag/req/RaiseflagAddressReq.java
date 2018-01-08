package com.gt.game.core.bean.raiseflag.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 升国旗
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("升国旗活动保存地址对象")
public class RaiseflagAddressReq {

	@ApiModelProperty("领奖地址")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
