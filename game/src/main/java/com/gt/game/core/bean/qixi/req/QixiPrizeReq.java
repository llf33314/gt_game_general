package com.gt.game.core.bean.qixi.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * 浪漫七夕主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("浪漫七夕活动保存奖品对象")
public class QixiPrizeReq {
	/**
	 * 名称
	 */
	@ApiModelProperty("奖品名称")
	private String prizeName;
	/**
	 * 数量
	 */
	@ApiModelProperty("奖品数量")
	private Integer num;
	/**
	 * 类型（1：粉币 2：手机流量 4：实体物品 6：积分 7：优惠券）
	 */
	@ApiModelProperty("奖品类型（1：粉币 2：手机流量 4：实体物品 6：积分 7：优惠券）")
	private Integer type;
	/**
	 * 单位
	 */
	@ApiModelProperty("奖品单位")
	private Integer prizeUnit;
	/**
	 * 图片奖励说明
	 */
	@ApiModelProperty("奖品图片说明")
	private String imgInstruction;

	@ApiModelProperty("奖品图片")
	private List<QixiPrizeImgReq> qixiPrizeImgReqs;

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPrizeUnit() {
		return prizeUnit;
	}

	public void setPrizeUnit(Integer prizeUnit) {
		this.prizeUnit = prizeUnit;
	}

	public String getImgInstruction() {
		return imgInstruction;
	}

	public void setImgInstruction(String imgInstruction) {
		this.imgInstruction = imgInstruction;
	}

	public List<QixiPrizeImgReq> getQixiPrizeImgReqs() {
		return qixiPrizeImgReqs;
	}

	public void setQixiPrizeImgReqs(List<QixiPrizeImgReq> qixiPrizeImgReqs) {
		this.qixiPrizeImgReqs = qixiPrizeImgReqs;
	}
}
