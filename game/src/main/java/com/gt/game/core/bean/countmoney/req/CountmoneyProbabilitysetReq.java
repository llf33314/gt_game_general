package com.gt.game.core.bean.countmoney.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import sun.text.normalizer.UCharacter;

import java.math.BigDecimal;

@ApiModel("疯狂数钱活动中奖概率请求类")
public class CountmoneyProbabilitysetReq {

	@ApiModelProperty("面额")
	private  Integer fenbiType;

	@ApiModelProperty("出现概率")
	private BigDecimal fenbiChance;

	public Integer getFenbiType() {
		return fenbiType;
	}

	public void setFenbiType(Integer fenbiType) {
		this.fenbiType = fenbiType;
	}

	public BigDecimal getFenbiChance() {
		return fenbiChance;
	}

	public void setFenbiChance(BigDecimal fenbiChance) {
		this.fenbiChance = fenbiChance;
	}
}
