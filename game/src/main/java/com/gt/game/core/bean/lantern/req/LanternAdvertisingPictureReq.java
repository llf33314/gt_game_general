package com.gt.game.core.bean.lantern.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 元宵点灯主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("广告轮播图请求类")
public class LanternAdvertisingPictureReq {

	@ApiModelProperty("广告链接")
	private  String hrefUrl;

	@ApiModelProperty("广告图片路径")
	private  String url;

	public String getHrefUrl() {
		return hrefUrl;
	}

	public void setHrefUrl(String hrefUrl) {
		this.hrefUrl = hrefUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
