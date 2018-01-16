package com.gt.game.core.bean.shakeLuck.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 摇手气主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("摇手气中奖记录发放奖品请求参数对象")
public class ShakeluckApplyIdReq {


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
