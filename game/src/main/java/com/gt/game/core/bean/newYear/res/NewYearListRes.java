package com.gt.game.core.bean.newYear.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 元旦跨年跳跃主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("元旦跨年跳跃分页查询活动返回对象")
public class NewYearListRes {


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

	@ApiModelProperty("活动状态  0 未开始 1 进行中 2 已结束")
	private Integer status;

	@ApiModelProperty("是否可以编辑  0 不可以 1 可以")
	private Integer isEdit;

	public Integer getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Integer isEdit) {
		this.isEdit = isEdit;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
