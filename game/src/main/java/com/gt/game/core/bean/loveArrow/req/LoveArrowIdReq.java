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
@ApiModel("一箭穿心分页删除活动请求参数对象")
public class LoveArrowIdReq {

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
