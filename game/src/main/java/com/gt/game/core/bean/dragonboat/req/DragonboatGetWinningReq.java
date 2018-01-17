package com.gt.game.core.bean.dragonboat.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel("分页获取中奖记录列表请求类")
public class DragonboatGetWinningReq extends PageReq {

	@ApiModelProperty("活动id")
	private Integer actId;

	@ApiModelProperty("奖品类型  -1 全部 1 粉币 2 手机流量 4 实体物品 6 积分 7 优惠劵")
	private Integer type;

	@ApiModelProperty("发放状态  -1 全部 1 未兑奖 2 已兑奖 3 已提交")
	private Integer status;

	@ApiModelProperty("兑奖码")
	private String snCode;

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

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
