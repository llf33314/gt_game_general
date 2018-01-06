package com.gt.game.core.bean.lantern.req;

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
@ApiModel("编辑元宵点灯活动奖项设置请求类")
public class LanternModfiyAwardsReq {

	@ApiModelProperty("奖品说明")
	private String prizeDescription;

	@ApiModelProperty("广告轮播图")
	private List<LanternAdvertisingPictureReq> advertisingPictureList;

	@ApiModelProperty("奖品设置")
	private List<LanternPrizeSetReq> prizeSetList;

	public String getPrizeDescription() {
		return prizeDescription;
	}

	public void setPrizeDescription(String prizeDescription) {
		this.prizeDescription = prizeDescription;
	}

	public List<LanternAdvertisingPictureReq> getAdvertisingPictureList() {
		return advertisingPictureList;
	}

	public void setAdvertisingPictureList(List<LanternAdvertisingPictureReq> advertisingPictureList) {
		this.advertisingPictureList = advertisingPictureList;
	}

	public List<LanternPrizeSetReq> getPrizeSetList() {
		return prizeSetList;
	}

	public void setPrizeSetList(List<LanternPrizeSetReq> prizeSetList) {
		this.prizeSetList = prizeSetList;
	}
}
