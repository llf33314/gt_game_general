package com.gt.game.core.bean.goldRush.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 欢乐抢元宝
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("欢乐抢元宝活动保存地址对象")
public class GoldRushAddressReq {

	@ApiModelProperty("领奖地址")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
