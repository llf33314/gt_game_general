package com.gt.game.core.bean.common.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("优惠劵查询返回对象")
public class CardReceiveListRes {

	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("卡包名称")
	private String cardsName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardsName() {
		return cardsName;
	}

	public void setCardsName(String cardsName) {
		this.cardsName = cardsName;
	}
}
