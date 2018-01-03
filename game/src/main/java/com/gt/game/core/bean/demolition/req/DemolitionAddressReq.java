package com.gt.game.core.bean.demolition.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 拆礼盒主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("拆礼盒活动保存地址对象")
public class DemolitionAddressReq {

	@ApiModelProperty("领奖地址")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
