package com.gt.game.core.bean.stand.req;

import com.gt.game.core.bean.stand.res.StandQuestionRes;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 一站到底
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("一站到底保存题库请求对象")
public class StandQuesbankSaveReq {

	@ApiModelProperty("id 新增 id=0")
	private Integer id;
	/**
	 * 题库名称
	 */
	@ApiModelProperty("题库名称")
	private String bankName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
}

