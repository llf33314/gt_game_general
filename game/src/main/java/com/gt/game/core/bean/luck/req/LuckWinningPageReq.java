package com.gt.game.core.bean.luck.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 好运翻翻看
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("好运翻翻看分页查询中奖记录请求参数对象")
public class LuckWinningPageReq extends PageReq{

	@ApiModelProperty("奖品类型  -1 全部 1 粉币 2 手机流量 4 实体物品 6 积分 7 优惠劵")
	private  Integer luckPrizeType;

	@ApiModelProperty("兑奖状态 -1 全部 1 未兑奖 2 已兑奖 3 已提交")
	private Integer luckStatus;

	@ApiModelProperty("兑奖码")
	private  String snCode;

	@ApiModelProperty("活动id")
	private Integer actId;

	public Integer getLuckPrizeType() {
		return luckPrizeType;
	}

	public void setLuckPrizeType(Integer luckPrizeType) {
		this.luckPrizeType = luckPrizeType;
	}

	public Integer getLuckStatus() {
		return luckStatus;
	}

	public void setLuckStatus(Integer luckStatus) {
		this.luckStatus = luckStatus;
	}

	public String getSnCode() {
		return snCode;
	}

	public void setSnCode(String snCode) {
		this.snCode = snCode;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
}
