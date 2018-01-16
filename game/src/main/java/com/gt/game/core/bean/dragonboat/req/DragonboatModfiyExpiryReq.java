package com.gt.game.core.bean.dragonboat.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 端午赛龙舟主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("编辑端午赛龙舟活动兑奖设置请求类")
public class DragonboatModfiyExpiryReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("兑奖开始时间")
	private Date cashPrizeBeginTime;

	@ApiModelProperty("兑奖结束时间")
	private Date cashPrizeEndTime;

	@ApiModelProperty("兑奖方式 （1，到店领取 2，邮寄）")
	private List<String> receiveTypeList;

	@ApiModelProperty("兑奖地址")
	private List<String> addressList;

	@ApiModelProperty("联系电话")
	private String phone;

	@ApiModelProperty("兑奖说明")
	private String cashPrizeInstruction;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCashPrizeBeginTime() {
		return cashPrizeBeginTime;
	}

	public void setCashPrizeBeginTime(Date cashPrizeBeginTime) {
		this.cashPrizeBeginTime = cashPrizeBeginTime;
	}

	public Date getCashPrizeEndTime() {
		return cashPrizeEndTime;
	}

	public void setCashPrizeEndTime(Date cashPrizeEndTime) {
		this.cashPrizeEndTime = cashPrizeEndTime;
	}

	public List<String> getReceiveTypeList() {
		return receiveTypeList;
	}

	public void setReceiveTypeList(List<String> receiveTypeList) {
		this.receiveTypeList = receiveTypeList;
	}

	public List<String> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCashPrizeInstruction() {
		return cashPrizeInstruction;
	}

	public void setCashPrizeInstruction(String cashPrizeInstruction) {
		this.cashPrizeInstruction = cashPrizeInstruction;
	}
}
