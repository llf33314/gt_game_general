package com.gt.game.core.bean.goldtree.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * 摇钱树主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("摇钱树删除核销授权请求参数对象")
public class GoldtreeAuthorityIdsReq {

	@ApiModelProperty("ids")
	private List<Integer> ids;

	@ApiModelProperty("活动id")
	private Integer actId;

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
}
