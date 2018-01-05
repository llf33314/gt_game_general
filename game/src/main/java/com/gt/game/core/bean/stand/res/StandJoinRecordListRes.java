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
@ApiModel("一站到底获取用户列表返回对象")
public class StandJoinRecordListRes {

	@ApiModelProperty("id")
	private Integer id;
	/**
	 * 参与人姓名
	 */
	@ApiModelProperty("微信昵称")
	private String memberName;

	/**
	 * 总得分
	 */
	@ApiModelProperty("答题总积分")
	private Integer totalScore;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("完成时间")
	private Date createtime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
