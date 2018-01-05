package com.gt.game.core.bean.stand.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 一站到底
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一站到底分页查询活动返回对象")
public class StandListRes {


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

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
}
