package com.gt.game.core.bean.stand.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 一站到底
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一站到底获取答题记录列表返回对象")
public class StandJoinDetailListRes {

	@ApiModelProperty("id")
	private Integer id;

	/**
	 * 题目标题
	 */
	@ApiModelProperty("题号")
	private String quesTitle;
	/**
	 * 选择的答案
	 */
	@ApiModelProperty("选择答案")
	private String chooseAnswer;
	/**
	 * 得分
	 */
	@ApiModelProperty("获得积分")
	private Integer score;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("答题时间")
	private Date createTime;

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

	public String getChooseAnswer() {
		return chooseAnswer;
	}

	public void setChooseAnswer(String chooseAnswer) {
		this.chooseAnswer = chooseAnswer;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
