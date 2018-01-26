package com.gt.game.core.bean.tree.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("分页查询圣诞大礼包活动请求类")
public class TreeListReq extends PageReq{

	@ApiModelProperty("活动名称")
	private  String name;

	@ApiModelProperty("活动状态 -1 全部 0 未开始 1 进行中 2 已结束 3.已暂停")
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
