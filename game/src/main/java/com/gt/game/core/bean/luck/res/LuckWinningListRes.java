package com.gt.game.core.bean.luck.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 好运翻翻看
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("好运翻翻看分页查询中奖记录返回对象")
public class LuckWinningListRes {


    /**
     * 主键
     */
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("发放状态 1 未兑奖 2 已兑奖 3 已提交")
	private Integer luckStatus;

	@ApiModelProperty("中奖人")
	private String luckMemberName;

	@ApiModelProperty("会员Id")
	private Integer luckMemberId;

	@ApiModelProperty("获奖时间")
	private Date luckCreateTime;

	@ApiModelProperty("兑奖码")
	private String luckExchangeCode;

	@ApiModelProperty("luckCashMemberId")
	private Integer luckCashMemberId;

	@ApiModelProperty("实体物品领取方式1到店，2速递 3直接领取")
	private Integer luckWay;

	@ApiModelProperty("兑奖人手机号码")
	private String luckPhone;

	@ApiModelProperty("兑奖人地址")
	private String luckAddress;

	@ApiModelProperty("兑奖人姓名")
	private String luckName;

	@ApiModelProperty("兑奖时间")
	private Date luckCashtime;

	@ApiModelProperty("奖项名称")
	private String luckPrizeName;

	@ApiModelProperty("奖品类型")
	private Integer luckPrizeType;

	@ApiModelProperty("中奖数量")
	private Integer luckPrizeLimit;

	@ApiModelProperty("到店领取地址")
	private String address;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLuckPrizeName() {
		return luckPrizeName;
	}

	public void setLuckPrizeName(String luckPrizeName) {
		this.luckPrizeName = luckPrizeName;
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

	public String getLuckMemberName() {
		return luckMemberName;
	}

	public void setLuckMemberName(String luckMemberName) {
		this.luckMemberName = luckMemberName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLuckStatus() {
		return luckStatus;
	}

	public void setLuckStatus(Integer luckStatus) {
		this.luckStatus = luckStatus;
	}

	public Integer getLuckMemberId() {
		return luckMemberId;
	}

	public void setLuckMemberId(Integer luckMemberId) {
		this.luckMemberId = luckMemberId;
	}

	public Date getLuckCreateTime() {
		return luckCreateTime;
	}

	public void setLuckCreateTime(Date luckCreateTime) {
		this.luckCreateTime = luckCreateTime;
	}

	public String getLuckExchangeCode() {
		return luckExchangeCode;
	}

	public void setLuckExchangeCode(String luckExchangeCode) {
		this.luckExchangeCode = luckExchangeCode;
	}

	public Integer getLuckCashMemberId() {
		return luckCashMemberId;
	}

	public void setLuckCashMemberId(Integer luckCashMemberId) {
		this.luckCashMemberId = luckCashMemberId;
	}

	public Integer getLuckWay() {
		return luckWay;
	}

	public void setLuckWay(Integer luckWay) {
		this.luckWay = luckWay;
	}

	public String getLuckPhone() {
		return luckPhone;
	}

	public void setLuckPhone(String luckPhone) {
		this.luckPhone = luckPhone;
	}

	public String getLuckAddress() {
		return luckAddress;
	}

	public void setLuckAddress(String luckAddress) {
		this.luckAddress = luckAddress;
	}

	public String getLuckName() {
		return luckName;
	}

	public void setLuckName(String luckName) {
		this.luckName = luckName;
	}

	public Date getLuckCashtime() {
		return luckCashtime;
	}

	public void setLuckCashtime(Date luckCashtime) {
		this.luckCashtime = luckCashtime;
	}
}
