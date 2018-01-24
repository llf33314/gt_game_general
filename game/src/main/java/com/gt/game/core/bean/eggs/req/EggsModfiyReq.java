package com.gt.game.core.bean.eggs.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel("编辑砸金蛋活动设置请求类")
public class EggsModfiyReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String eggName;

	@ApiModelProperty("活动开始时间")
	private Date eggBeginTime;

	@ApiModelProperty("活动结束时间")
	private Date eggEndTime;

	@ApiModelProperty("参与人员 1.所有粉丝  2.仅会员(持有会员卡的粉丝) ")
	private Integer eggEggPartaker;

	@ApiModelProperty("参与方式 1.所有会员不需要积分  2.会员积分满  3.每次抽奖扣除  4.会员积分满&每次抽奖扣除")
	private Integer eggPway;

	@ApiModelProperty("会员积分满")
	private Integer eggMan;

	@ApiModelProperty("每次抽奖扣除")
	private Integer eggKou;

	@ApiModelProperty("活动描述")
	private String eggDescribe;

	@ApiModelProperty("活动未开始提示")
	private String eggBeforeTxt;

	@ApiModelProperty("音乐名称")
	private String eggBgmName;

	@ApiModelProperty("音乐路径")
	private String eggBgm;

	@ApiModelProperty("抽奖总数")
	private Integer eggCountOfAll;

	@ApiModelProperty("抽奖次数")
	private Integer eggCountOfDay;

	@ApiModelProperty("兑奖期限")
	private Integer eggCashDay;

	@ApiModelProperty("兑奖地址")
	private String eggAddress;

	@ApiModelProperty("兑奖方式(1手动 2自动)")
	private Integer eggCashWay;

	@ApiModelProperty("兑奖提示")
	private String eggWinningTxt;

	@ApiModelProperty("中奖须知")
	private String eggWinningNotice;

	@ApiModelProperty("奖品设置")
	private List<EggsPrizeSetReq> prizeSetList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEggName() {
		return eggName;
	}

	public void setEggName(String eggName) {
		this.eggName = eggName;
	}

	public Date getEggBeginTime() {
		return eggBeginTime;
	}

	public void setEggBeginTime(Date eggBeginTime) {
		this.eggBeginTime = eggBeginTime;
	}

	public Date getEggEndTime() {
		return eggEndTime;
	}

	public void setEggEndTime(Date eggEndTime) {
		this.eggEndTime = eggEndTime;
	}

	public Integer getEggEggPartaker() {
		return eggEggPartaker;
	}

	public void setEggEggPartaker(Integer eggEggPartaker) {
		this.eggEggPartaker = eggEggPartaker;
	}

	public Integer getEggPway() {
		return eggPway;
	}

	public void setEggPway(Integer eggPway) {
		this.eggPway = eggPway;
	}

	public Integer getEggMan() {
		return eggMan;
	}

	public void setEggMan(Integer eggMan) {
		this.eggMan = eggMan;
	}

	public Integer getEggKou() {
		return eggKou;
	}

	public void setEggKou(Integer eggKou) {
		this.eggKou = eggKou;
	}

	public String getEggDescribe() {
		return eggDescribe;
	}

	public void setEggDescribe(String eggDescribe) {
		this.eggDescribe = eggDescribe;
	}

	public String getEggBeforeTxt() {
		return eggBeforeTxt;
	}

	public void setEggBeforeTxt(String eggBeforeTxt) {
		this.eggBeforeTxt = eggBeforeTxt;
	}

	public String getEggBgmName() {
		return eggBgmName;
	}

	public void setEggBgmName(String eggBgmName) {
		this.eggBgmName = eggBgmName;
	}

	public String getEggBgm() {
		return eggBgm;
	}

	public void setEggBgm(String eggBgm) {
		this.eggBgm = eggBgm;
	}

	public Integer getEggCountOfAll() {
		return eggCountOfAll;
	}

	public void setEggCountOfAll(Integer eggCountOfAll) {
		this.eggCountOfAll = eggCountOfAll;
	}

	public Integer getEggCountOfDay() {
		return eggCountOfDay;
	}

	public void setEggCountOfDay(Integer eggCountOfDay) {
		this.eggCountOfDay = eggCountOfDay;
	}

	public Integer getEggCashDay() {
		return eggCashDay;
	}

	public void setEggCashDay(Integer eggCashDay) {
		this.eggCashDay = eggCashDay;
	}

	public String getEggAddress() {
		return eggAddress;
	}

	public void setEggAddress(String eggAddress) {
		this.eggAddress = eggAddress;
	}

	public Integer getEggCashWay() {
		return eggCashWay;
	}

	public void setEggCashWay(Integer eggCashWay) {
		this.eggCashWay = eggCashWay;
	}

	public String getEggWinningTxt() {
		return eggWinningTxt;
	}

	public void setEggWinningTxt(String eggWinningTxt) {
		this.eggWinningTxt = eggWinningTxt;
	}

	public String getEggWinningNotice() {
		return eggWinningNotice;
	}

	public void setEggWinningNotice(String eggWinningNotice) {
		this.eggWinningNotice = eggWinningNotice;
	}

	public List<EggsPrizeSetReq> getPrizeSetList() {
		return prizeSetList;
	}

	public void setPrizeSetList(List<EggsPrizeSetReq> prizeSetList) {
		this.prizeSetList = prizeSetList;
	}
}
