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
@ApiModel("编辑元宵点灯活动基础设置请求类")
public class LanternModfiyReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String name;

	@ApiModelProperty("活动开始时间")
	private Date activityBeginTime;

	@ApiModelProperty("活动结束时间")
	private Date activityEndTime;

	@ApiModelProperty("关注二维码")
	private String followQrCode;

	@ApiModelProperty("背景音乐URL")
	private String musicUrl;

	@ApiModelProperty("背景音乐名")
	private String bgmSp;

	@ApiModelProperty("游戏总数")
	private Integer manTotalChance;

	@ApiModelProperty("每天次数")
	private Integer manDayChance;

	@ApiModelProperty("游戏时长")
	private Integer gameTime;

	@ApiModelProperty("活动规则")
	private String actRule;

	@ApiModelProperty("兑奖开始时间")
	private Date cashPrizeBeginTime;

	@ApiModelProperty("兑奖结束时间")
	private Date cashPrizeEndTime;

	@ApiModelProperty("兑奖方式 （1，到店领取 2，邮寄）")
	private List<String> receiveTypeList;

	@ApiModelProperty("兑奖地址")
	private List<String> addressList;

	@ApiModelProperty("联系电话")
	private String phone;

	@ApiModelProperty("兑奖说明")
	private String cashPrizeInstruction;

	@ApiModelProperty("奖品说明")
	private String prizeDescription;

	@ApiModelProperty("广告轮播图")
	private List<LanternAdvertisingPictureReq> advertisingPictureList;

	@ApiModelProperty("奖品设置")
	private List<LanternPrizeSetReq> prizeSetList;

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

	public String getFollowQrCode() {
		return followQrCode;
	}

	public void setFollowQrCode(String followQrCode) {
		this.followQrCode = followQrCode;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getBgmSp() {
		return bgmSp;
	}

	public void setBgmSp(String bgmSp) {
		this.bgmSp = bgmSp;
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

	public Integer getGameTime() {
		return gameTime;
	}

	public void setGameTime(Integer gameTime) {
		this.gameTime = gameTime;
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

	public List<String> getReceiveTypeList() {
		return receiveTypeList;
	}

	public void setReceiveTypeList(List<String> receiveTypeList) {
		this.receiveTypeList = receiveTypeList;
	}

	public List<String> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
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
