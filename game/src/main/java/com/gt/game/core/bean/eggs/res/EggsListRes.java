package com.gt.game.core.bean.eggs.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;


@ApiModel("分页查询砸金蛋活动返回类")
public class EggsListRes {

	@ApiModelProperty("主键id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String name;

	@ApiModelProperty("活动开始时间")
	private Date activityBeginTime;

	@ApiModelProperty("活动结束时间")
	private Date activityEndTime;


	@ApiModelProperty("活动状态  0 未开始 1 进行中 2 已结束 3.已暂停")
	private Integer status;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
