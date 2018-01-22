package com.gt.game.core.bean.dragonboat.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * <p>
 * 端午赛龙舟主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("奖品设置请求类")
public class DragonboatPrizeSetReq {

	@ApiModelProperty("奖品类型")
	private Integer type;

	@ApiModelProperty("奖品单位")
	private  Integer prizeUnit;

	@ApiModelProperty("奖品名称")
	private  String prizeName;

	@ApiModelProperty("奖品数量")
	private  Integer num;

	@ApiModelProperty("分数")
	private  Integer score;


	@ApiModelProperty("选择图片")
	private List<String> imgUrl;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getPrizeUnit() {
		return prizeUnit;
	}

	public void setPrizeUnit(Integer prizeUnit) {
		this.prizeUnit = prizeUnit;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public List<String> getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(List<String> imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
