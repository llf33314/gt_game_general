package com.gt.game.core.bean.raiseflag.res;

import com.gt.game.core.bean.raiseflag.req.RaiseflagAddressReq;
import com.gt.game.core.bean.raiseflag.req.RaiseflagPrizeReq;
import com.gt.game.core.bean.raiseflag.req.RaiseflagSponsorReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 升国旗主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("升国旗查询活动返回对象")
public class RaiseflagRes {


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
	private List<RaiseflagAddressReq> raiseflagAddressReqs;

	@ApiModelProperty("联系电话")
	private String phone;

	@ApiModelProperty("兑奖说明")
	private String cashPrizeInstruction;

	@ApiModelProperty("奖品说明")
	private String prizeSetInstruction;

	@ApiModelProperty("奖品")
	private List<RaiseflagPrizeReq> raiseflagPrizeReqs;

	@ApiModelProperty("游戏结束后提示语")
	private String gameEndTips;
	/**
	 * 是否需要赞助商(1开启0关闭)
	 */
	@ApiModelProperty("赞助商提示(1开启0关闭)")
	private Integer isSponsor;
	/**
	 * 赞助商提示语
	 */
	@ApiModelProperty("赞助商自定义提示")
	private String sponsorTips;

	@ApiModelProperty("赞助商")
	List<RaiseflagSponsorReq> raiseflagSponsorReqs;
	/**
	 * 发送中奖消息(1开启0关闭)
	 */
	@ApiModelProperty("中奖消息(1开启0关闭)")
	private Integer isMsgTemplate;
	/**
	 * 消息模板id
	 */
	@ApiModelProperty("消息模板id")
	private Integer msgTemplateId;
	/**
	 * 设置参与奖(1开启0关闭)
	 */
	@ApiModelProperty("参与奖(1开启0关闭)")
	private Integer isJoinPrize;
	/**
	 * 设置参与奖链接
	 */
	@ApiModelProperty("参与奖链接")
	private String joinPrizeHref;
	/**
	 * 参与奖提示语
	 */
	@ApiModelProperty("参与奖提示语")
	private String joinPrizeTips;
	/**
	 * 参与奖按钮提示语
	 */
	@ApiModelProperty("参与完成提示语")
	private String joinPrizeBtnTips;

	public Integer getIsSponsor() {
		return isSponsor;
	}

	public void setIsSponsor(Integer isSponsor) {
		this.isSponsor = isSponsor;
	}

	public String getSponsorTips() {
		return sponsorTips;
	}

	public void setSponsorTips(String sponsorTips) {
		this.sponsorTips = sponsorTips;
	}

	public List<RaiseflagSponsorReq> getRaiseflagSponsorReqs() {
		return raiseflagSponsorReqs;
	}

	public void setRaiseflagSponsorReqs(List<RaiseflagSponsorReq> raiseflagSponsorReqs) {
		this.raiseflagSponsorReqs = raiseflagSponsorReqs;
	}

	public Integer getIsMsgTemplate() {
		return isMsgTemplate;
	}

	public void setIsMsgTemplate(Integer isMsgTemplate) {
		this.isMsgTemplate = isMsgTemplate;
	}

	public Integer getMsgTemplateId() {
		return msgTemplateId;
	}

	public void setMsgTemplateId(Integer msgTemplateId) {
		this.msgTemplateId = msgTemplateId;
	}

	public Integer getIsJoinPrize() {
		return isJoinPrize;
	}

	public void setIsJoinPrize(Integer isJoinPrize) {
		this.isJoinPrize = isJoinPrize;
	}

	public String getJoinPrizeHref() {
		return joinPrizeHref;
	}

	public void setJoinPrizeHref(String joinPrizeHref) {
		this.joinPrizeHref = joinPrizeHref;
	}

	public String getJoinPrizeTips() {
		return joinPrizeTips;
	}

	public void setJoinPrizeTips(String joinPrizeTips) {
		this.joinPrizeTips = joinPrizeTips;
	}

	public String getJoinPrizeBtnTips() {
		return joinPrizeBtnTips;
	}

	public void setJoinPrizeBtnTips(String joinPrizeBtnTips) {
		this.joinPrizeBtnTips = joinPrizeBtnTips;
	}

	public String getGameEndTips() {
		return gameEndTips;
	}

	public void setGameEndTips(String gameEndTips) {
		this.gameEndTips = gameEndTips;
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

	public List<RaiseflagAddressReq> getRaiseflagAddressReqs() {
		return raiseflagAddressReqs;
	}

	public void setRaiseflagAddressReqs(List<RaiseflagAddressReq> raiseflagAddressReqs) {
		this.raiseflagAddressReqs = raiseflagAddressReqs;
	}

	public List<RaiseflagPrizeReq> getRaiseflagPrizeReqs() {
		return raiseflagPrizeReqs;
	}

	public void setRaiseflagPrizeReqs(List<RaiseflagPrizeReq> raiseflagPrizeReqs) {
		this.raiseflagPrizeReqs = raiseflagPrizeReqs;
	}
}
