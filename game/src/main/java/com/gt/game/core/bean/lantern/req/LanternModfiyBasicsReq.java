package com.gt.game.core.bean.lantern.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 元宵点灯主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("编辑元宵点灯活动基础设置请求类")
public class LanternModfiyBasicsReq {

	@ApiModelProperty("活动名称")
	private String name;

	@ApiModelProperty("活动开始时间")
	private Date activityBeginTime;

	@ApiModelProperty("活动结束时间")
	private Date activityEndTime;

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
}
