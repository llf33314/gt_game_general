package com.gt.game.core.bean.eggs.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("编辑砸金蛋活动兑奖设置请求类")
public class EggsModfiyExpiryReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("兑奖期限")
	private Integer eggCashDay;

	@ApiModelProperty("兑奖地址")
	private String eggAddress;

	@ApiModelProperty("兑奖方式(1手动 2自动)")
	private Integer eggCashWay;

	@ApiModelProperty("兑奖提示")
	private String eggWinningTxt;

	@ApiModelProperty("中奖须知")
	private String eggWinningNotice;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEggCashDay() {
		return eggCashDay;
	}

	public void setEggCashDay(Integer eggCashDay) {
		this.eggCashDay = eggCashDay;
	}

	public String getEggAddress() {
		return eggAddress;
	}

	public void setEggAddress(String eggAddress) {
		this.eggAddress = eggAddress;
	}

	public Integer getEggCashWay() {
		return eggCashWay;
	}

	public void setEggCashWay(Integer eggCashWay) {
		this.eggCashWay = eggCashWay;
	}

	public String getEggWinningTxt() {
		return eggWinningTxt;
	}

	public void setEggWinningTxt(String eggWinningTxt) {
		this.eggWinningTxt = eggWinningTxt;
	}

	public String getEggWinningNotice() {
		return eggWinningNotice;
	}

	public void setEggWinningNotice(String eggWinningNotice) {
		this.eggWinningNotice = eggWinningNotice;
	}
}
