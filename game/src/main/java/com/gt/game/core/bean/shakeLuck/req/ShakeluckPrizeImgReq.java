package com.gt.game.core.bean.shakeLuck.req;

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
@ApiModel("摇手气活动保存奖品图片对象")
public class ShakeluckPrizeImgReq {

	@ApiModelProperty("图片路径")
	private String imgUrl;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
