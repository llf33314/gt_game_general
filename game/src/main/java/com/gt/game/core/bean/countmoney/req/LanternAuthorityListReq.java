package com.gt.game.core.bean.countmoney.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 元宵点灯主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("分页获取核销授权列表请求类")
public class LanternAuthorityListReq  extends PageReq{

	@ApiModelProperty("活动id")
	private Integer actId;

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
}
