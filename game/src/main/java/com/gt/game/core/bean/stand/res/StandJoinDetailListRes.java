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
	 * 选择的答案
	 */
	private String chooseAnswer;
	/**
	 * 得分
	 */
	private Integer score;

	/**
	 * 创建时间
	 */
	private Date createTime;
}
