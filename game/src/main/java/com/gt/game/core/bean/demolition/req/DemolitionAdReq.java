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
@ApiModel("拆礼盒活动保存轮播图对象")
public class DemolitionAdReq {

	/**
	 * 广告图片路径
	 */
	@ApiModelProperty("广告图片路径")
	private String url;
	/**
	 * 广告链接
	 */
	@ApiModelProperty("广告链接")
	private String hrefUrl;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHrefUrl() {
		return hrefUrl;
	}

	public void setHrefUrl(String hrefUrl) {
		this.hrefUrl = hrefUrl;
	}
}
