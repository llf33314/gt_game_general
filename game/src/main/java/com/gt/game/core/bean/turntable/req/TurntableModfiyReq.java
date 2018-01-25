package com.gt.game.core.bean.turntable.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("编辑大转盘活动设置请求类")
public class TurntableModfiyReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String actName;

	@ApiModelProperty("活动开始时间")
	private Date actBeginTime;

	@ApiModelProperty("活动结束时间")
	private Date actEndTime;

	@ApiModelProperty("结束说明")
	private String actOverdescribe;

	@ApiModelProperty("参与人员 1.所有粉丝  2.仅会员(持有会员卡的粉丝) ")
	private Integer actPartaker;

	@ApiModelProperty("参与方式 1.所有会员不需要积分  2.会员积分满  3.每次抽奖扣除  4.会员积分满&每次抽奖扣除")
	private Integer actPway;

	@ApiModelProperty("会员积分满")
	private Integer actMan;

	@ApiModelProperty("每次抽奖扣除")
	private Integer actKou;

	@ApiModelProperty("音乐名称")
	private String actBgmName;

	@ApiModelProperty("音乐路径")
	private String actBgm;

	@ApiModelProperty("抽奖次数")
	private Integer actCountOfDay;

	@ApiModelProperty("抽奖总数")
	private Integer actTotalOfAct;

	@ApiModelProperty("活动规则")
	private String actDescribe;

	@ApiModelProperty("兑奖期限")
	private Integer actCashday;

	@ApiModelProperty("兑奖方式(1手动 2自动)")
	private Integer actCashWay;

	@ApiModelProperty("兑奖地址")
	private String actAddress;

	@ApiModelProperty("兑奖提示")
	private String actCashtext;

	@ApiModelProperty("中奖须知")
	private String actWinningNotice;

	@ApiModelProperty("奖品设置")
	private List<TurntablePrizeSetReq> prizeSetList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getActOverdescribe() {
		return actOverdescribe;
	}

	public void setActOverdescribe(String actOverdescribe) {
		this.actOverdescribe = actOverdescribe;
	}

	public Integer getActPartaker() {
		return actPartaker;
	}

	public void setActPartaker(Integer actPartaker) {
		this.actPartaker = actPartaker;
	}

	public Integer getActPway() {
		return actPway;
	}

	public void setActPway(Integer actPway) {
		this.actPway = actPway;
	}

	public Integer getActMan() {
		return actMan;
	}

	public void setActMan(Integer actMan) {
		this.actMan = actMan;
	}

	public Integer getActKou() {
		return actKou;
	}

	public void setActKou(Integer actKou) {
		this.actKou = actKou;
	}

	public String getActBgmName() {
		return actBgmName;
	}

	public void setActBgmName(String actBgmName) {
		this.actBgmName = actBgmName;
	}

	public String getActBgm() {
		return actBgm;
	}

	public void setActBgm(String actBgm) {
		this.actBgm = actBgm;
	}

	public Integer getActCountOfDay() {
		return actCountOfDay;
	}

	public void setActCountOfDay(Integer actCountOfDay) {
		this.actCountOfDay = actCountOfDay;
	}

	public Integer getActTotalOfAct() {
		return actTotalOfAct;
	}

	public void setActTotalOfAct(Integer actTotalOfAct) {
		this.actTotalOfAct = actTotalOfAct;
	}

	public String getActDescribe() {
		return actDescribe;
	}

	public void setActDescribe(String actDescribe) {
		this.actDescribe = actDescribe;
	}

	public Integer getActCashday() {
		return actCashday;
	}

	public void setActCashday(Integer actCashday) {
		this.actCashday = actCashday;
	}

	public Integer getActCashWay() {
		return actCashWay;
	}

	public void setActCashWay(Integer actCashWay) {
		this.actCashWay = actCashWay;
	}

	public String getActAddress() {
		return actAddress;
	}

	public void setActAddress(String actAddress) {
		this.actAddress = actAddress;
	}

	public String getActCashtext() {
		return actCashtext;
	}

	public void setActCashtext(String actCashtext) {
		this.actCashtext = actCashtext;
	}

	public String getActWinningNotice() {
		return actWinningNotice;
	}

	public void setActWinningNotice(String actWinningNotice) {
		this.actWinningNotice = actWinningNotice;
	}

	public List<TurntablePrizeSetReq> getPrizeSetList() {
		return prizeSetList;
	}

	public void setPrizeSetList(List<TurntablePrizeSetReq> prizeSetList) {
		this.prizeSetList = prizeSetList;
	}
}
