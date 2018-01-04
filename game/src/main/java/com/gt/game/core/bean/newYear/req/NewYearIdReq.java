package com.gt.game.core.bean.newYear.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 元旦跨年跳跃主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("元旦跨年跳跃分页删除活动请求参数对象")
public class NewYearIdReq {

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
