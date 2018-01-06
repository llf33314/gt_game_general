package com.gt.game.core.bean.loveArrow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 一箭穿心主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一箭穿心活动保存奖品图片对象")
public class LoveArrowPrizeImgReq {

	@ApiModelProperty("图片路径")
	private String imgUrl;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
