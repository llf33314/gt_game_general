package com.gt.game.core.bean.goldRush.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 欢乐抢元宝主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("欢乐抢元宝活动保存奖品图片对象")
public class GoldRushPrizeImgReq {

	@ApiModelProperty("图片路径")
	private String imgUrl;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
