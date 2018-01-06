package com.gt.game.core.bean.loveArrow.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 一箭穿心主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一箭穿心分页查询核销授权请求参数对象")
public class LoveArrowAuthorityListPageReq extends PageReq{

	@ApiModelProperty("活动id")
	private Integer actId;

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
}
