package com.gt.game.core.bean.goldRush.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 欢乐抢元宝主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("欢乐抢元宝查询活动保存对象")
public class GoldRushSaveReq {


    /**
     * 主键
     */
	@ApiModelProperty("id 新增 id=0")
	private Integer id;

	@ApiModelProperty("活动名称")
	@NotEmpty(message = "请输入活动名称")
	private String name;
    /**
     * 活动开始时间
     */
	@ApiModelProperty("活动开始时间")
	@NotNull(message = "请选择活动时间")
	private Date activityBeginTime;
    /**
     * 活动结束时间
     */
	@ApiModelProperty("活动结束时间")
	@NotNull(message = "请选择活动时间")
	private Date activityEndTime;

	@ApiModelProperty("背景音乐")
	private String musicUrl;

	@ApiModelProperty("关注二维码")
	private String followQrCode;

	@ApiModelProperty("游戏总数")
	@NotNull(message = "请输入游戏总数")
	private Integer manTotalChance;

	@ApiModelProperty("每天次数")
	@NotNull(message = "请输入每天次数")
	private Integer manDayChance;

	@ApiModelProperty("活动规则")
	@NotEmpty(message = "请输入活动规则")
	private String actRule;

	@ApiModelProperty("兑奖开始时间")
	@NotNull(message = "请选择兑奖时间")
	private Date cashPrizeBeginTime;

	@ApiModelProperty("兑奖结束时间")
	@NotNull(message = "请选择兑奖时间")
	private Date cashPrizeEndTime;

	@ApiModelProperty("兑奖方式（1，到店领取 2，邮寄）两个都有用 , 号隔开")
	@NotEmpty(message = "请输入活动名称")
	private String receiveType;

	@ApiModelProperty("兑奖地址")
	private List<GoldRushAddressReq> goldRushAddressReqs;

	@ApiModelProperty("联系电话")
	@NotEmpty(message = "请输入联系电话")
	private String phone;

	@ApiModelProperty("兑奖说明")
	@NotEmpty(message = "请输入兑奖说明")
	private String cashPrizeInstruction;

	@ApiModelProperty("奖品说明")
	@NotEmpty(message = "请输入奖品说明")
	private String prizeSetInstruction;

	@ApiModelProperty("奖品")
	private List<GoldRushPrizeReq> goldRushPrizeReqs;

	@ApiModelProperty("祝福语")
	private String goldRushTips;

	@ApiModelProperty("游戏时间")
	@NotNull(message = "请输入游戏时间")
	private Integer gametime;

	public String getGoldRushTips() {
		return goldRushTips;
	}

	public void setGoldRushTips(String goldRushTips) {
		this.goldRushTips = goldRushTips;
	}

	public Integer getGametime() {
		return gametime;
	}

	public void setGametime(Integer gametime) {
		this.gametime = gametime;
	}

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

	public List<GoldRushAddressReq> getGoldRushAddressReqs() {
		return goldRushAddressReqs;
	}

	public void setGoldRushAddressReqs(List<GoldRushAddressReq> goldRushAddressReqs) {
		this.goldRushAddressReqs = goldRushAddressReqs;
	}

	public List<GoldRushPrizeReq> getGoldRushPrizeReqs() {
		return goldRushPrizeReqs;
	}

	public void setGoldRushPrizeReqs(List<GoldRushPrizeReq> goldRushPrizeReqs) {
		this.goldRushPrizeReqs = goldRushPrizeReqs;
	}
}
