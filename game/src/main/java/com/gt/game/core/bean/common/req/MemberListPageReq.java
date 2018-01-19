package com.gt.game.core.bean.common.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 粉丝
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("粉丝分页查询请求参数对象")
public class MemberListPageReq extends PageReq{

	@ApiModelProperty("昵称")
	private  String memberName;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
}
