package com.gt.game.core.bean.goldtree.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 摇钱树主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("摇钱树分页查询核销授权返回对象")
public class GoldtreeAuthorityListRes {


    /**
     * 主键
     */
	@ApiModelProperty("id")
	private Integer id;

	/**
	 * 授权时间
	 */
	@ApiModelProperty("创建时间")
	private Date createtime;
	/**
	 * 权限人员姓名
	 */
	@ApiModelProperty("核销员")
	private String memberName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
}
