package com.gt.game.core.bean.loveArrow.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 一箭穿心主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一箭穿心查询活动数量返回对象")
public class LoveArrowCountRes {

	@ApiModelProperty("全部")
	private Integer count1;

	@ApiModelProperty("未开始")
	private Integer count2;

	@ApiModelProperty("进行中")
	private Integer count3;

	@ApiModelProperty("已结束")
	private Integer count4;

	public Integer getCount1() {
		return count1;
	}

	public void setCount1(Integer count1) {
		this.count1 = count1;
	}

	public Integer getCount2() {
		return count2;
	}

	public void setCount2(Integer count2) {
		this.count2 = count2;
	}

	public Integer getCount3() {
		return count3;
	}

	public void setCount3(Integer count3) {
		this.count3 = count3;
	}

	public Integer getCount4() {
		return count4;
	}

	public void setCount4(Integer count4) {
		this.count4 = count4;
	}
}
