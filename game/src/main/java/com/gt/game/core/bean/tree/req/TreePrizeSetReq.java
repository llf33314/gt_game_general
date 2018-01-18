package com.gt.game.core.bean.tree.req;

import com.gt.game.core.bean.ninelattice.req.NinelatticePrizeSetReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("圣诞大礼包奖品设置请求类")
public class TreePrizeSetReq {

	@ApiModelProperty("奖品类型")
	private Integer type;

	@ApiModelProperty("奖品单位")
	private  Integer prizeUnit;

	@ApiModelProperty("奖品名称")
	private  String prizeName;

	@ApiModelProperty("奖品数量")
	private  Integer num;

	@ApiModelProperty("中奖概率")
	private double probabiliy;

	@ApiModelProperty("中奖人")
	private String nickName;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPrizeUnit() {
		return prizeUnit;
	}

	public void setPrizeUnit(Integer prizeUnit) {
		this.prizeUnit = prizeUnit;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public double getProbabiliy() {
		return probabiliy;
	}

	public void setProbabiliy(double probabiliy) {
		this.probabiliy = probabiliy;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}
