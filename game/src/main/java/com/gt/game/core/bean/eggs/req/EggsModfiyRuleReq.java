package com.gt.game.core.bean.eggs.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("编辑砸金蛋活动规则设置请求类")
public class EggsModfiyRuleReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("抽奖总数")
	private Integer eggsCountOfAll;

	@ApiModelProperty("抽奖次数")
	private Integer eggsCountOfDay;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEggsCountOfAll() {
		return eggsCountOfAll;
	}

	public void setEggsCountOfAll(Integer eggsCountOfAll) {
		this.eggsCountOfAll = eggsCountOfAll;
	}

	public Integer getEggsCountOfDay() {
		return eggsCountOfDay;
	}

	public void setEggsCountOfDay(Integer eggsCountOfDay) {
		this.eggsCountOfDay = eggsCountOfDay;
	}
}
