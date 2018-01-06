package com.gt.game.core.bean.lantern.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 元宵点灯 主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("分页查询元宵点灯活动返回类")
public class LanternListRes {

	@ApiModelProperty("主键id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String name;

	@ApiModelProperty("活动开始时间")
	private Date activityBeginTime;

	@ApiModelProperty("活动结束时间")
	private Date activityEndTime;

	@ApiModelProperty("兑奖开始时间")
	private Date cashPrizeBeginTime;

	@ApiModelProperty("兑奖结束时间")
	private Date cashPrizeEndTime;

	@ApiModelProperty("活动状态  0 未开始 1 进行中 2 已结束")
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
