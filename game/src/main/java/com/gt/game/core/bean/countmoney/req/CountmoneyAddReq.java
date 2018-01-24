package com.gt.game.core.bean.countmoney.req;

import com.gt.game.core.entity.countmoney.CountmoneyProbabilityset;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("新增疯狂数钱活动请求类")
public class CountmoneyAddReq {

	@ApiModelProperty("游戏模式(1-排名中奖；2-数钱折算)")
	private Integer actType;

	@ApiModelProperty("活动名称")
	private String actName;

	@ApiModelProperty("活动开始时间")
	private Date actBeginTime;

	@ApiModelProperty("活动结束时间")
	private Date actEndTime;

	@ApiModelProperty("活动说明")
	private String actDescribe;

	@ApiModelProperty("活动尚未开始提示")
	private String actNotStartedTips;

	@ApiModelProperty("背景音乐名")
	private String actScrBgmName;

	@ApiModelProperty("背景音乐URL")
	private String actScrBgmUrl;

	@ApiModelProperty("游戏时间")
	private Integer actGameTime;

	@ApiModelProperty("抽奖总数")
	private Integer actTotalOfAct;

	@ApiModelProperty("抽奖次数")
	private Integer actCountOfDay;

	@ApiModelProperty("概率设置")
	private List<CountmoneyProbabilitysetReq> countmoneyProbabilitysetList;

	@ApiModelProperty("兑奖期限")
	private Integer actAwardingTime;

	@ApiModelProperty("兑奖地址")
	private String  actAwardingAddress;

	@ApiModelProperty("兑奖提示")
	private String actAwardingTips;

	@ApiModelProperty("数钱数：粉币")
	private Integer actRateFenbi;

	@ApiModelProperty("数钱数：人民币")
	private Integer actRateRmb;

	@ApiModelProperty("冻结粉币总数")
	private Integer actFrozenFenbi;

	@ApiModelProperty("是否显示奖品数量(0.不显示 1.显示)")
	private Integer actIsShowNums;

	@ApiModelProperty("奖品设置")
	private List<CountmoneyPrizeSetReq> prizeSetList;

	public String getActScrBgmName() {
		return actScrBgmName;
	}

	public void setActScrBgmName(String actScrBgmName) {
		this.actScrBgmName = actScrBgmName;
	}

	public String getActScrBgmUrl() {
		return actScrBgmUrl;
	}

	public void setActScrBgmUrl(String actScrBgmUrl) {
		this.actScrBgmUrl = actScrBgmUrl;
	}

	public Integer getActType() {
		return actType;
	}

	public void setActType(Integer actType) {
		this.actType = actType;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public Date getActBeginTime() {
		return actBeginTime;
	}

	public void setActBeginTime(Date actBeginTime) {
		this.actBeginTime = actBeginTime;
	}

	public Date getActEndTime() {
		return actEndTime;
	}

	public void setActEndTime(Date actEndTime) {
		this.actEndTime = actEndTime;
	}

	public String getActDescribe() {
		return actDescribe;
	}

	public void setActDescribe(String actDescribe) {
		this.actDescribe = actDescribe;
	}

	public String getActNotStartedTips() {
		return actNotStartedTips;
	}

	public void setActNotStartedTips(String actNotStartedTips) {
		this.actNotStartedTips = actNotStartedTips;
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

	public Integer getActGameTime() {
		return actGameTime;
	}

	public void setActGameTime(Integer actGameTime) {
		this.actGameTime = actGameTime;
	}

	public Integer getActTotalOfAct() {
		return actTotalOfAct;
	}

	public void setActTotalOfAct(Integer actTotalOfAct) {
		this.actTotalOfAct = actTotalOfAct;
	}

	public Integer getActCountOfDay() {
		return actCountOfDay;
	}

	public void setActCountOfDay(Integer actCountOfDay) {
		this.actCountOfDay = actCountOfDay;
	}

	public Integer getActRateFenbi() {
		return actRateFenbi;
	}

	public void setActRateFenbi(Integer actRateFenbi) {
		this.actRateFenbi = actRateFenbi;
	}

	public Integer getActRateRmb() {
		return actRateRmb;
	}

	public void setActRateRmb(Integer actRateRmb) {
		this.actRateRmb = actRateRmb;
	}

	public Integer getActFrozenFenbi() {
		return actFrozenFenbi;
	}

	public void setActFrozenFenbi(Integer actFrozenFenbi) {
		this.actFrozenFenbi = actFrozenFenbi;
	}

	public List<CountmoneyProbabilitysetReq> getCountmoneyProbabilitysetList() {
		return countmoneyProbabilitysetList;
	}

	public void setCountmoneyProbabilitysetList(List<CountmoneyProbabilitysetReq> countmoneyProbabilitysetList) {
		this.countmoneyProbabilitysetList = countmoneyProbabilitysetList;
	}

	public Integer getActAwardingTime() {
		return actAwardingTime;
	}

	public void setActAwardingTime(Integer actAwardingTime) {
		this.actAwardingTime = actAwardingTime;
	}

	public String getActAwardingAddress() {
		return actAwardingAddress;
	}

	public void setActAwardingAddress(String actAwardingAddress) {
		this.actAwardingAddress = actAwardingAddress;
	}

	public String getActAwardingTips() {
		return actAwardingTips;
	}

	public void setActAwardingTips(String actAwardingTips) {
		this.actAwardingTips = actAwardingTips;
	}

	public Integer getActIsShowNums() {
		return actIsShowNums;
	}

	public void setActIsShowNums(Integer actIsShowNums) {
		this.actIsShowNums = actIsShowNums;
	}

	public List<CountmoneyPrizeSetReq> getPrizeSetList() {
		return prizeSetList;
	}

	public void setPrizeSetList(List<CountmoneyPrizeSetReq> prizeSetList) {
		this.prizeSetList = prizeSetList;
	}
}
