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
@ApiModel("好运翻翻看分页查询活动返回对象")
public class LuckListRes {


    /**
     * 主键
     */
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String luckName;
    /**
     * 活动开始时间
     */
	@ApiModelProperty("活动开始时间")
	private Date luckBeginTime;
    /**
     * 活动结束时间
     */
	@ApiModelProperty("活动结束时间")
	private Date luckEndTime;

	@ApiModelProperty("活动状态  0 未开始 1 进行中 2 已结束 3 已暂停")
	private Integer status;

	@ApiModelProperty("是否可以编辑  0 不可以 1 可以")
	private Integer isEdit;

	private Integer luckStatus;

	public Integer getLuckStatus() {
		return luckStatus;
	}

	public void setLuckStatus(Integer luckStatus) {
		this.luckStatus = luckStatus;
	}

	public Integer getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(Integer isEdit) {
		this.isEdit = isEdit;
	}

	public String getLuckName() {
		return luckName;
	}

	public void setLuckName(String luckName) {
		this.luckName = luckName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLuckBeginTime() {
		return luckBeginTime;
	}

	public void setLuckBeginTime(Date luckBeginTime) {
		this.luckBeginTime = luckBeginTime;
	}

	public Date getLuckEndTime() {
		return luckEndTime;
	}

	public void setLuckEndTime(Date luckEndTime) {
		this.luckEndTime = luckEndTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
