package com.gt.game.core.bean.countmoney.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("分页查询疯狂数钱活动返回类")
public class CountmoneyListRes {

	@ApiModelProperty("主键id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String actName;

	@ApiModelProperty("活动类型(1-排名中奖；2-数钱折算)")
	private Integer actType;

	@ApiModelProperty("活动开始时间")
	private Date actBeginTime;

	@ApiModelProperty("活动结束时间")
	private Date actEndTime;

	@ApiModelProperty("活动状态  0 未开始 1 进行中 2 已结束")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}

	public Date getActBeginTime() {
		return actBeginTime;
	}

	public void setActBeginTime(Date actBeginTime) {
		this.actBeginTime = actBeginTime;
	}

	public Date getActEndTime() {
		return actEndTime;
	}

	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
