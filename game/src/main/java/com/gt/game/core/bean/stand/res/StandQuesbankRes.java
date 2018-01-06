package com.gt.game.core.bean.stand.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 一站到底
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一站到底获取题库返回对象")
public class StandQuesbankRes {

	@ApiModelProperty("id")
	private Integer id;
	/**
	 * 创建时间
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;
	/**
	 * 题库名称
	 */
	@ApiModelProperty("题库名称")
	private String bankName;
	/**
	 * 题库题目数量
	 */
	@ApiModelProperty("题目数量")
	private Integer quesAmount;

	@ApiModelProperty("题目列表")
	List<StandQuestionRes> standQuestionResList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getQuesAmount() {
		return quesAmount;
	}

	public void setQuesAmount(Integer quesAmount) {
		this.quesAmount = quesAmount;
	}

	public List<StandQuestionRes> getStandQuestionResList() {
		return standQuestionResList;
	}

	public void setStandQuestionResList(List<StandQuestionRes> standQuestionResList) {
		this.standQuestionResList = standQuestionResList;
	}
}

