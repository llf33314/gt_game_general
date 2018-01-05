package com.gt.game.core.bean.stand.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 一站到底
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一站到底中奖记录发放奖品请求参数对象")
public class StandApplyIdReq {


    /**
     * 主键
     */
	@ApiModelProperty("id")
	private  Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
