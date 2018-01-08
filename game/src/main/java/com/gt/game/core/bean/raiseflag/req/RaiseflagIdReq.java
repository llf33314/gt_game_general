package com.gt.game.core.bean.raiseflag.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 升国旗主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("升国旗分页删除活动请求参数对象")
public class RaiseflagIdReq {

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
