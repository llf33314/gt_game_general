package com.gt.game.core.bean.lantern.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 元宵点灯主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("编辑元宵点灯活动规则设置请求类")
public class LanternModfiyRuleReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("关注二维码")
	private String followQrCode;

	/*@ApiModelProperty("背景音乐URL")
	private String musicUrl;

	@ApiModelProperty("背景音乐名")
	private String bgmSp;*/

	@ApiModelProperty("游戏总数")
	private Integer manTotalChance;

	@ApiModelProperty("每天次数")
	private Integer manDayChance;

	@ApiModelProperty("游戏时长")
	private Integer gameTime;

	@ApiModelProperty("活动规则")
	private String actRule;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFollowQrCode() {
		return followQrCode;
	}

	public void setFollowQrCode(String followQrCode) {
		this.followQrCode = followQrCode;
	}

	/*public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getBgmSp() {
		return bgmSp;
	}

	public void setBgmSp(String bgmSp) {
		this.bgmSp = bgmSp;
	}*/

	public Integer getManTotalChance() {
		return manTotalChance;
	}

	public void setManTotalChance(Integer manTotalChance) {
		this.manTotalChance = manTotalChance;
	}

	public Integer getManDayChance() {
		return manDayChance;
	}

	public void setManDayChance(Integer manDayChance) {
		this.manDayChance = manDayChance;
	}

	public Integer getGameTime() {
		return gameTime;
	}

	public void setGameTime(Integer gameTime) {
		this.gameTime = gameTime;
	}

	public String getActRule() {
		return actRule;
	}

	public void setActRule(String actRule) {
		this.actRule = actRule;
	}
}
