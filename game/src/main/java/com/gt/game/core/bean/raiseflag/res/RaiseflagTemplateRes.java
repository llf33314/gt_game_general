package com.gt.game.core.bean.raiseflag.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * </p>
 *
 * @author zwq
 * @since 2017-12-14
 */
@ApiModel(" 消息模板对象")
public class RaiseflagTemplateRes {

    /**
     * 主键
     */
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("名称")
	private String title;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
