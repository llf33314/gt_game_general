package com.gt.game.core.bean.raiseflag.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 升国旗主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("升国旗活动保存奖品图片对象")
public class RaiseflagPrizeImgReq {

	@ApiModelProperty("图片路径")
	private String imgUrl;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
