package com.gt.game.core.bean.seagold.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 大海捞金
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("大海捞金分页查询核销授权请求参数对象")
public class SeagoldAuthorityListPageReq extends PageReq{

	@ApiModelProperty("活动id")
	private Integer actId;

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
}
