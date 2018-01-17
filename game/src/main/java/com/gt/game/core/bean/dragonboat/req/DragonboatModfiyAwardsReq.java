package com.gt.game.core.bean.dragonboat.req;

import com.gt.game.core.bean.lantern.req.LanternAdvertisingPictureReq;
import com.gt.game.core.bean.lantern.req.LanternPrizeSetReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 *  端午赛龙舟主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("编辑端午赛龙舟活动奖项设置请求类")
public class DragonboatModfiyAwardsReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("奖品说明")
	private String prizeDescription;

	@ApiModelProperty("广告轮播图")
	private List<DragonboatAdvertisingPictureReq> advertisingPictureList;

	@ApiModelProperty("奖品设置")
	private List<DragonboatPrizeSetReq> prizeSetList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrizeDescription() {
		return prizeDescription;
	}

	public void setPrizeDescription(String prizeDescription) {
		this.prizeDescription = prizeDescription;
	}

	public List<DragonboatAdvertisingPictureReq> getAdvertisingPictureList() {
		return advertisingPictureList;
	}

	public void setAdvertisingPictureList(List<DragonboatAdvertisingPictureReq> advertisingPictureList) {
		this.advertisingPictureList = advertisingPictureList;
	}

	public List<DragonboatPrizeSetReq> getPrizeSetList() {
		return prizeSetList;
	}

	public void setPrizeSetList(List<DragonboatPrizeSetReq> prizeSetList) {
		this.prizeSetList = prizeSetList;
	}
}
