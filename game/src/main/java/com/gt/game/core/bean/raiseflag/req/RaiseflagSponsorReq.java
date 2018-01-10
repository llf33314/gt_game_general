package com.gt.game.core.bean.raiseflag.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * 升国旗主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("升国旗活动保存赞助商对象")
public class RaiseflagSponsorReq {
	/**
	 * 赞助商链接
	 */
	@ApiModelProperty("赞助商链接")
	private String sponsorUrl;
	/**
	 * 赞助商图片
	 */
	@ApiModelProperty("赞助商图片")
	private String sponsorImgUrl;

	public String getSponsorUrl() {
		return sponsorUrl;
	}

	public void setSponsorUrl(String sponsorUrl) {
		this.sponsorUrl = sponsorUrl;
	}

	public String getSponsorImgUrl() {
		return sponsorImgUrl;
	}

	public void setSponsorImgUrl(String sponsorImgUrl) {
		this.sponsorImgUrl = sponsorImgUrl;
	}
}
