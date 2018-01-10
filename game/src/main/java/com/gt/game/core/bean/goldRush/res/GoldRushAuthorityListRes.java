package com.gt.game.core.bean.goldRush.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 欢乐抢元宝主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("欢乐抢元宝分页查询核销授权返回对象")
public class GoldRushAuthorityListRes {


    /**
     * 主键
     */
	@ApiModelProperty("id")
	private Integer id;

	/**
	 * 授权时间
	 */
	@ApiModelProperty("创建时间")
	private Date createTime;
	/**
	 * 权限人员姓名
	 */
	@ApiModelProperty("核销员")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
