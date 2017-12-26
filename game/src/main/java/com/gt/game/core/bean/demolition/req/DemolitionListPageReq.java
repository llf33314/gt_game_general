package com.gt.game.core.bean.demolition.req;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * <p>
 * 拆礼盒主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("拆礼盒分页查询活动请求参数对象")
public class DemolitionListPageReq extends PageReq{


    /**
     * 主键
     */
	@ApiModelProperty("活动名称")
	private  String name;

	@ApiModelProperty("活动状态 -1 全部 0 未开始 1 进行中 2 已结束")
	private Integer status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
