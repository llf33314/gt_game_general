package com.gt.game.core.bean.luck.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 好运翻翻看
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("好运翻翻看活动奖品")
public class LuckDetailReq {

	/**
	 * 奖项名称
	 */
	@ApiModelProperty("奖品名称")
	private String luckPrizeName;
	/**
	 * 奖品数量
	 */
	@ApiModelProperty("奖品数量")
	private Integer luckPrizeNums;
	/**
	 * 中奖概率
	 */
	@ApiModelProperty("中奖概率")
	private Double luckPrizeChance;
	/**
	 * 奖品类型
	 */
	@ApiModelProperty("奖品类型  -1 全部 1 粉币 2 手机流量 4 实体物品 6 积分 7 优惠劵")
	private Integer luckPrizeType;
	/**
	 * 奖品额度
	 */
	@ApiModelProperty("奖品额度")
	private Integer luckPrizeLimit;
	/**
	 * openid
	 */
	@ApiModelProperty("会员id")
	private String openid;
	/**
	 * nickname
	 */
	@ApiModelProperty("会员名称")
	private String nickname;

	public String getLuckPrizeName() {
		return luckPrizeName;
	}

	public void setLuckPrizeName(String luckPrizeName) {
		this.luckPrizeName = luckPrizeName;
	}

	public Integer getLuckPrizeNums() {
		return luckPrizeNums;
	}

	public void setLuckPrizeNums(Integer luckPrizeNums) {
		this.luckPrizeNums = luckPrizeNums;
	}

	public Double getLuckPrizeChance() {
		return luckPrizeChance;
	}

	public void setLuckPrizeChance(Double luckPrizeChance) {
		this.luckPrizeChance = luckPrizeChance;
	}

	public Integer getLuckPrizeType() {
		return luckPrizeType;
	}

	public void setLuckPrizeType(Integer luckPrizeType) {
		this.luckPrizeType = luckPrizeType;
	}

	public Integer getLuckPrizeLimit() {
		return luckPrizeLimit;
	}

	public void setLuckPrizeLimit(Integer luckPrizeLimit) {
		this.luckPrizeLimit = luckPrizeLimit;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
