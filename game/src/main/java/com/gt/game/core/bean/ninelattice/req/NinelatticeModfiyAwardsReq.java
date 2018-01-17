package com.gt.game.core.bean.ninelattice.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * 元宵点灯主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("编辑幸运九宫格活动奖项设置请求类")
public class NinelatticeModfiyAwardsReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("奖品设置")
	private List<NinelatticePrizeSetReq> prizeSetList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<NinelatticePrizeSetReq> getPrizeSetList() {
		return prizeSetList;
	}

	public void setPrizeSetList(List<NinelatticePrizeSetReq> prizeSetList) {
		this.prizeSetList = prizeSetList;
	}
}
