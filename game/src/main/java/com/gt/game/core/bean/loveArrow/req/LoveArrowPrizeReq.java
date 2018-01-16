package com.gt.game.core.bean.loveArrow.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * 一箭穿心主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一箭穿心活动保存奖品对象")
public class LoveArrowPrizeReq {
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
	/**
	 */
	@ApiModelProperty("中奖概率")
	private Double probabiliy;

	@ApiModelProperty("奖品图片")
	private List<LoveArrowPrizeImgReq> loveArrowPrizeImgReqs;

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

	public Double getProbabiliy() {
		return probabiliy;
	}

	public void setProbabiliy(Double probabiliy) {
		this.probabiliy = probabiliy;
	}

	public List<LoveArrowPrizeImgReq> getLoveArrowPrizeImgReqs() {
		return loveArrowPrizeImgReqs;
	}

	public void setLoveArrowPrizeImgReqs(List<LoveArrowPrizeImgReq> loveArrowPrizeImgReqs) {
		this.loveArrowPrizeImgReqs = loveArrowPrizeImgReqs;
	}
}
