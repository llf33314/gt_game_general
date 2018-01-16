package com.gt.game.core.bean.dragonboat.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("分页获取核销授权列表请求类")
public class DragonboatAuthorityListReq extends PageReq{

	@ApiModelProperty("活动id")
	private Integer actId;

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
}
