package com.gt.game.core.bean.demolition.res;

import com.gt.game.core.bean.demolition.req.DemolitionAdReq;
import com.gt.game.core.bean.demolition.req.DemolitionAddressReq;
import com.gt.game.core.bean.demolition.req.DemolitionGiftBoxReq;
import com.gt.game.core.bean.demolition.req.DemolitionPrizeReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 拆礼盒主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("拆礼盒查询活动返回对象")
public class DemolitionRes {


    /**
     * 主键
     */
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String name;
	/**
	 * 活动开始时间
	 */
	@ApiModelProperty("活动开始时间")
	private Date activityBeginTime;
	/**
	 * 活动结束时间
	 */
	@ApiModelProperty("活动结束时间")
	private Date activityEndTime;

	@ApiModelProperty("背景音乐")
	private String musicUrl;

	@ApiModelProperty("礼盒")
	private List<DemolitionGiftBoxReq> demolitionGiftBoxReqs;

	@ApiModelProperty("关注二维码")
	private String followQrCode;

	@ApiModelProperty("游戏总数")
	private Integer manTotalChance;

	@ApiModelProperty("每天次数")
	private Integer manDayChance;

	@ApiModelProperty("活动规则")
	private String actRule;

	@ApiModelProperty("兑奖开始时间")
	private Date cashPrizeBeginTime;

	@ApiModelProperty("兑奖结束时间")
	private Date cashPrizeEndTime;

	@ApiModelProperty("兑奖方式（1，到店领取 2，邮寄）两个都有用 , 号隔开")
	private String receiveType;

	@ApiModelProperty("兑奖地址")
	private List<DemolitionAddressReq> demolitionAddressReqs;

	@ApiModelProperty("联系电话")
	private String phone;

	@ApiModelProperty("兑奖说明")
	private String cashPrizeInstruction;

	@ApiModelProperty("奖品说明")
	private String prizeSetInstruction;

	@ApiModelProperty("广告轮播图")
	private List<DemolitionAdReq> demolitionAdReqs;

	@ApiModelProperty("奖品")
	private List<DemolitionPrizeReq> demolitionPrizeReqs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getActivityBeginTime() {
		return activityBeginTime;
	}

	public void setActivityBeginTime(Date activityBeginTime) {
		this.activityBeginTime = activityBeginTime;
	}

	public Date getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public List<DemolitionGiftBoxReq> getDemolitionGiftBoxReqs() {
		return demolitionGiftBoxReqs;
	}

	public void setDemolitionGiftBoxReqs(List<DemolitionGiftBoxReq> demolitionGiftBoxReqs) {
		this.demolitionGiftBoxReqs = demolitionGiftBoxReqs;
	}

	public String getFollowQrCode() {
		return followQrCode;
	}

	public void setFollowQrCode(String followQrCode) {
		this.followQrCode = followQrCode;
	}

	public Integer getManTotalChance() {
		return manTotalChance;
	}

	public void setManTotalChance(Integer manTotalChance) {
		this.manTotalChance = manTotalChance;
	}

	public Integer getManDayChance() {
		return manDayChance;
	}

	public void setManDayChance(Integer manDayChance) {
		this.manDayChance = manDayChance;
	}

	public String getActRule() {
		return actRule;
	}

	public void setActRule(String actRule) {
		this.actRule = actRule;
	}

	public Date getCashPrizeBeginTime() {
		return cashPrizeBeginTime;
	}

	public void setCashPrizeBeginTime(Date cashPrizeBeginTime) {
		this.cashPrizeBeginTime = cashPrizeBeginTime;
	}

	public Date getCashPrizeEndTime() {
		return cashPrizeEndTime;
	}

	public void setCashPrizeEndTime(Date cashPrizeEndTime) {
		this.cashPrizeEndTime = cashPrizeEndTime;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public List<DemolitionAddressReq> getDemolitionAddressReqs() {
		return demolitionAddressReqs;
	}

	public void setDemolitionAddressReqs(List<DemolitionAddressReq> demolitionAddressReqs) {
		this.demolitionAddressReqs = demolitionAddressReqs;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCashPrizeInstruction() {
		return cashPrizeInstruction;
	}

	public void setCashPrizeInstruction(String cashPrizeInstruction) {
		this.cashPrizeInstruction = cashPrizeInstruction;
	}

	public String getPrizeSetInstruction() {
		return prizeSetInstruction;
	}

	public void setPrizeSetInstruction(String prizeSetInstruction) {
		this.prizeSetInstruction = prizeSetInstruction;
	}

	public List<DemolitionAdReq> getDemolitionAdReqs() {
		return demolitionAdReqs;
	}

	public void setDemolitionAdReqs(List<DemolitionAdReq> demolitionAdReqs) {
		this.demolitionAdReqs = demolitionAdReqs;
	}

	public List<DemolitionPrizeReq> getDemolitionPrizeReqs() {
		return demolitionPrizeReqs;
	}

	public void setDemolitionPrizeReqs(List<DemolitionPrizeReq> demolitionPrizeReqs) {
		this.demolitionPrizeReqs = demolitionPrizeReqs;
	}
}
