package com.gt.game.core.bean.stand.res;

import com.gt.game.core.bean.stand.req.StandAddressReq;
import com.gt.game.core.bean.stand.req.StandPrizeReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 一站到底
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一站到底查询活动返回对象")
public class StandRes {


    /**
     * 主键
     */
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String actName;

    /**
     * 活动开始时间
     */
	@ApiModelProperty("活动开始时间")
	private Date activityBegintime;
    /**
     * 活动结束时间
     */
	@ApiModelProperty("活动结束时间")
	private Date activityEndtime;

	/**
	 * 题库id
	 */
	@ApiModelProperty("题库id")
	private Integer bankId;

	@ApiModelProperty("背景音乐")
	private String musicUrl;

	@ApiModelProperty("每天次数")
	private Integer manDayChance;
	/**
	 * 每日参与答题的总数
	 */
	@ApiModelProperty("游戏总数")
	private Integer manDayTotalQuesChance;

	@ApiModelProperty("答题时间")
	private Integer answerTime;

	@ApiModelProperty("奖励积分")
	private Integer rightCount;

	@ApiModelProperty("活动规则")
	private String actRule;

	@ApiModelProperty("兑奖期限")
	private Integer standCashDay;

	@ApiModelProperty("兑奖方式")
	private String receiveType;

	@ApiModelProperty("兑奖地址")
	List<StandAddressReq> standAddressReqs;

	@ApiModelProperty("联系电话")
	private String phone;

	@ApiModelProperty("兑奖说明")
	private String cashPrizeInstruction;

	@ApiModelProperty("商家名称")
	private String busName;

	@ApiModelProperty("奖品设置")
	List<StandPrizeReq> standPrizeReqs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Date getActivityBegintime() {
		return activityBegintime;
	}

	public void setActivityBegintime(Date activityBegintime) {
		this.activityBegintime = activityBegintime;
	}

	public Date getActivityEndtime() {
		return activityEndtime;
	}

	public void setActivityEndtime(Date activityEndtime) {
		this.activityEndtime = activityEndtime;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public Integer getManDayChance() {
		return manDayChance;
	}

	public void setManDayChance(Integer manDayChance) {
		this.manDayChance = manDayChance;
	}

	public Integer getManDayTotalQuesChance() {
		return manDayTotalQuesChance;
	}

	public void setManDayTotalQuesChance(Integer manDayTotalQuesChance) {
		this.manDayTotalQuesChance = manDayTotalQuesChance;
	}

	public Integer getAnswerTime() {
		return answerTime;
	}

	public void setAnswerTime(Integer answerTime) {
		this.answerTime = answerTime;
	}

	public Integer getRightCount() {
		return rightCount;
	}

	public void setRightCount(Integer rightCount) {
		this.rightCount = rightCount;
	}

	public String getActRule() {
		return actRule;
	}

	public void setActRule(String actRule) {
		this.actRule = actRule;
	}

	public Integer getStandCashDay() {
		return standCashDay;
	}

	public void setStandCashDay(Integer standCashDay) {
		this.standCashDay = standCashDay;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public List<StandAddressReq> getStandAddressReqs() {
		return standAddressReqs;
	}

	public void setStandAddressReqs(List<StandAddressReq> standAddressReqs) {
		this.standAddressReqs = standAddressReqs;
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

	public String getBusName() {
		return busName;
	}

	public void setBusName(String busName) {
		this.busName = busName;
	}

	public List<StandPrizeReq> getStandPrizeReqs() {
		return standPrizeReqs;
	}

	public void setStandPrizeReqs(List<StandPrizeReq> standPrizeReqs) {
		this.standPrizeReqs = standPrizeReqs;
	}
}
