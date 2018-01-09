package com.gt.game.core.bean.qixi.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 浪漫七夕主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("浪漫七夕中奖记录发放奖品请求参数对象")
public class QixiApplyIdReq {


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
