package com.gt.game.core.bean.dragonboat.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 端午赛龙舟主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("编辑端午赛龙舟活动基础设置请求类")
public class DragonboatModfiyBasicsReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String name;

	@ApiModelProperty("活动开始时间")
	private Date activityBeginTime;

	@ApiModelProperty("活动结束时间")
	private Date activityEndTime;

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
}
