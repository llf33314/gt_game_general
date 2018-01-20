package com.gt.game.core.bean.countmoney.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分页查询疯狂数钱活动请求类")
public class CountmoneyListReq extends PageReq{

	@ApiModelProperty("活动名称")
	private  String actName;

	@ApiModelProperty("活动状态 -1 全部 0 未开始 1 进行中 2 已结束")
	private Integer status;

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
