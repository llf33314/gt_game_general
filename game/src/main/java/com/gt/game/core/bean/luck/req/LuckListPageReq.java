package com.gt.game.core.bean.luck.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 好运翻翻看
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("好运翻翻看分页查询活动请求参数对象")
public class LuckListPageReq extends PageReq{

    /**
     * 主键
     */
	@ApiModelProperty("活动名称")
	private  String luckName;

	@ApiModelProperty("活动状态 -1 全部 0 未开始 1 进行中 2 已结束 3 已暂停")
	private Integer status;

	public String getLuckName() {
		return luckName;
	}

	public void setLuckName(String luckName) {
		this.luckName = luckName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
