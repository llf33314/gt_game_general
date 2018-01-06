package com.gt.game.core.bean.lantern.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 元宵点灯主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("分页获取中奖记录列表请求类")
public class LanternGetWinningReq {

	@ApiModelProperty("奖品类型")
	private Integer type;

	@ApiModelProperty("发放状态")
	private Integer status;

	@ApiModelProperty("兑奖码")
	private String snCode;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}
}
