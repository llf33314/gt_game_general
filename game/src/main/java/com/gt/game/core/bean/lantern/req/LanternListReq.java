package com.gt.game.core.bean.lantern.req;

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
@ApiModel("分页查询元宵点灯活动请求类")
public class LanternListReq extends PageReq{

	@ApiModelProperty("活动名称")
	private  String name;

	@ApiModelProperty("活动状态 -1 全部 0 未开始 1 进行中 2 已结束")
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
