package com.gt.game.core.bean.goldtree.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 摇钱树主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("摇钱树查询活动保存对象")
public class GoldtreeSaveReq {


    /**
     * 主键
     */
	@ApiModelProperty("id 新增 id=0")
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

	@ApiModelProperty("关注二维码")
	private String followQrCode;

	@ApiModelProperty("活动规则")
	private String actRule;

	@ApiModelProperty("兑奖开始时间")
	private Date cashPrizeBeginTime;

	@ApiModelProperty("兑奖结束时间")
	private Date cashPrizeEndTime;

	@ApiModelProperty("兑奖方式（1，到店领取 2，邮寄）两个都有用 , 号隔开")
	private String receiveType;

	@ApiModelProperty("兑奖地址")
	private List<GoldtreeAddressReq> goldtreeAddressReqs;

	@ApiModelProperty("联系电话")
	private String phone;

	@ApiModelProperty("兑奖说明")
	private String cashPrizeInstruction;

	@ApiModelProperty("奖品说明")
	private String prizeSetInstruction;

	@ApiModelProperty("广告轮播图")
	private List<GoldtreeAdReq> goldtreeAdReqs;

	@ApiModelProperty("奖品")
	private List<GoldtreePrizeReq> goldtreePrizeReqs;

	@ApiModelProperty("免费次数")
	private Integer freeTimes;

	@ApiModelProperty("每天免费次数")
	private Integer dayFreeTimes;

	@ApiModelProperty("每次游戏粉币数")
	private Integer gameFb;

	@ApiModelProperty("每次游戏积分数")
	private Integer gameJf;

	@ApiModelProperty(" 1元宝=多少金钱")
	private Integer ingotMoney;

	@ApiModelProperty("1金钱=多少金币")
	private Integer moneyCoin;

	@ApiModelProperty("1开启模板，0关闭模板")
	private Integer isMsgTemplate;

	@ApiModelProperty("模板id")
	private String msgTemplateId;

	@ApiModelProperty("游戏时间")
	private Integer gameTime;

	public Integer getFreeTimes() {
		return freeTimes;
	}

	public void setFreeTimes(Integer freeTimes) {
		this.freeTimes = freeTimes;
	}

	public Integer getDayFreeTimes() {
		return dayFreeTimes;
	}

	public void setDayFreeTimes(Integer dayFreeTimes) {
		this.dayFreeTimes = dayFreeTimes;
	}

	public Integer getGameFb() {
		return gameFb;
	}

	public void setGameFb(Integer gameFb) {
		this.gameFb = gameFb;
	}

	public Integer getGameJf() {
		return gameJf;
	}

	public void setGameJf(Integer gameJf) {
		this.gameJf = gameJf;
	}

	public Integer getIngotMoney() {
		return ingotMoney;
	}

	public void setIngotMoney(Integer ingotMoney) {
		this.ingotMoney = ingotMoney;
	}

	public Integer getMoneyCoin() {
		return moneyCoin;
	}

	public void setMoneyCoin(Integer moneyCoin) {
		this.moneyCoin = moneyCoin;
	}

	public Integer getIsMsgTemplate() {
		return isMsgTemplate;
	}

	public void setIsMsgTemplate(Integer isMsgTemplate) {
		this.isMsgTemplate = isMsgTemplate;
	}

	public String getMsgTemplateId() {
		return msgTemplateId;
	}

	public void setMsgTemplateId(String msgTemplateId) {
		this.msgTemplateId = msgTemplateId;
	}

	public Integer getGameTime() {
		return gameTime;
	}

	public void setGameTime(Integer gameTime) {
		this.gameTime = gameTime;
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

	public String getFollowQrCode() {
		return followQrCode;
	}

	public void setFollowQrCode(String followQrCode) {
		this.followQrCode = followQrCode;
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

	public List<GoldtreeAddressReq> getGoldtreeAddressReqs() {
		return goldtreeAddressReqs;
	}

	public void setGoldtreeAddressReqs(List<GoldtreeAddressReq> goldtreeAddressReqs) {
		this.goldtreeAddressReqs = goldtreeAddressReqs;
	}

	public List<GoldtreeAdReq> getGoldtreeAdReqs() {
		return goldtreeAdReqs;
	}

	public void setGoldtreeAdReqs(List<GoldtreeAdReq> goldtreeAdReqs) {
		this.goldtreeAdReqs = goldtreeAdReqs;
	}

	public List<GoldtreePrizeReq> getGoldtreePrizeReqs() {
		return goldtreePrizeReqs;
	}

	public void setGoldtreePrizeReqs(List<GoldtreePrizeReq> goldtreePrizeReqs) {
		this.goldtreePrizeReqs = goldtreePrizeReqs;
	}
}
