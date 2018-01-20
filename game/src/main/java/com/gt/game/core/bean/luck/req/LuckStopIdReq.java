package com.gt.game.core.bean.luck.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 好运翻翻看
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("好运翻翻看活动id")
public class LuckStopIdReq {

	@ApiModelProperty("活动id")
	private Integer id;

	@ApiModelProperty("活动状态 暂停2 开始1")
	private Integer luckStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLuckStatus() {
		return luckStatus;
	}

	public void setLuckStatus(Integer luckStatus) {
		this.luckStatus = luckStatus;
	}
}
