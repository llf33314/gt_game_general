package com.gt.game.core.bean.stand.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 一站到底
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一站到底保存题目对象")
public class StandQuestionSaveReq {

	@ApiModelProperty("id 新增 id=0")
	private Integer id;
	/**
	 * 题目标题
	 */
	@ApiModelProperty("题目标题")
	private String quesTitle;
	/**
	 * 选项a
	 */
	@ApiModelProperty("选项a")
	private String optionA;
	/**
	 * 选项b
	 */
	@ApiModelProperty("选项b")
	private String optionB;
	/**
	 * 选项c
	 */
	@ApiModelProperty("选项c")
	private String optionC;
	/**
	 * 选项d
	 */
	@ApiModelProperty("选项d")
	private String optionD;
	/**
	 * 正确答案
	 */
	@ApiModelProperty("正确答案")
	private String rightAnswer;

	/**
	 * 对应题库id
	 */
	@ApiModelProperty("题库id")
	private Integer bankId;

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuesTitle() {
		return quesTitle;
	}

	public void setQuesTitle(String quesTitle) {
		this.quesTitle = quesTitle;
	}

	public String getOptionA() {
		return optionA;
	}

	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}

	public String getRightAnswer() {
		return rightAnswer;
	}

	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
	}
}

