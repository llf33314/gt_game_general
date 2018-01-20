package com.gt.game.core.bean.dragonboat.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("分页获取端午赛龙舟活动中奖记录列表返回类")
public class DragonboatGetWinningRes {

	/**
	 * 主键
	 */
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("奖品名称")
	private String prizeName;

	@ApiModelProperty("奖品类型 -1 全部 1 粉币 2 手机流量 4 实体物品 6 积分 7 优惠劵")
	private  Integer type;

	@ApiModelProperty("奖品数量")
	private  Integer prizeUnit;

	@ApiModelProperty("兑奖时间")
	private Date cashTime;

	@ApiModelProperty("兑奖人联系方式")
	private String memberPhone;

	@ApiModelProperty("兑奖人")
	private String memberName;

	@ApiModelProperty("memberId")
	private Integer memberId;

	@ApiModelProperty("兑奖状态 -1 全部 1 未兑奖 2 已兑奖 3 已提交")
	private Integer status;

	@ApiModelProperty("兑奖码")
	private String snCode;

	@ApiModelProperty("成绩")
	private Double score;

	@ApiModelProperty("中奖人")
	private String nickname;

	@ApiModelProperty("领奖方式（1.到店领取 2.邮寄 3 直接兑奖")
	private Integer receiveType;
	/**
	 * 领奖地址ID
	 */
	@ApiModelProperty("到店领取地址")
	private String addressName;

	@ApiModelProperty("邮寄地址")
	private String address;

	public Integer getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}

	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
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

	public Date getCashTime() {
		return cashTime;
	}

	public void setCashTime(Date cashTime) {
		this.cashTime = cashTime;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}
}
