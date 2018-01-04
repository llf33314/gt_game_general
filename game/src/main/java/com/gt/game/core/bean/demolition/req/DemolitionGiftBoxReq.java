package com.gt.game.core.bean.demolition.req;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 拆礼盒主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("拆礼盒活动保存礼盒对象")
public class DemolitionGiftBoxReq {
	/**
	 * 礼品盒名称
	 */
	@ApiModelProperty("礼品盒名称")
	private String giftName;
	/**
	 * 礼品盒图片
	 */
	@ApiModelProperty("礼品盒图片")
	private String giftImg;
	/**
	 * 礼品盒声音
	 */
	@ApiModelProperty("礼品盒声音")
	private String giftSound;
	/**
	 * 是否有奖(0,无奖  1,有奖)
	 */
	@ApiModelProperty(" 是否放置礼品(0,否  1,是)")
	private Integer Award;

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getGiftImg() {
		return giftImg;
	}

	public void setGiftImg(String giftImg) {
		this.giftImg = giftImg;
	}

	public String getGiftSound() {
		return giftSound;
	}

	public void setGiftSound(String giftSound) {
		this.giftSound = giftSound;
	}

	public Integer getAward() {
		return Award;
	}

	public void setAward(Integer award) {
		Award = award;
	}
}
