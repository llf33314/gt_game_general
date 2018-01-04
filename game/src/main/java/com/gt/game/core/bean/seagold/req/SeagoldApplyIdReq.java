package com.gt.game.core.bean.seagold.req;

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
@ApiModel("大海捞金中奖记录发放奖品请求参数对象")
public class SeagoldApplyIdReq {


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
