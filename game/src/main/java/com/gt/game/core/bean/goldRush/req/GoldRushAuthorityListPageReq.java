package com.gt.game.core.bean.goldRush.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 欢乐抢元宝主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("欢乐抢元宝分页查询核销授权请求参数对象")
public class GoldRushAuthorityListPageReq extends PageReq{

	@ApiModelProperty("活动id")
	private Integer actId;

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
}
