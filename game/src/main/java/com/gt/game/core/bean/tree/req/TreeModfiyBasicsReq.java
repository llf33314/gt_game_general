package com.gt.game.core.bean.tree.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel("编辑圣诞大礼包活动基础设置请求类")
public class TreeModfiyBasicsReq {

	@ApiModelProperty("活动主键id")
	private Integer id;

	@ApiModelProperty("活动名称")
	private String treeName;

	@ApiModelProperty("活动开始时间")
	private Date activityBeginTime;

	@ApiModelProperty("活动结束时间")
	private Date activityEndTime;

	@ApiModelProperty("参与人员 1.所有粉丝  2.仅会员(持有会员卡的粉丝) ")
	private Integer treeEggPartaker;

	@ApiModelProperty("参与方式 1.所有会员不需要积分  2.会员积分满  3.每次抽奖扣除  4.会员积分满&每次抽奖扣除")
	private Integer treePway;

	@ApiModelProperty("会员积分满")
	private Integer treeMan;

	@ApiModelProperty("每次抽奖扣除")
	private Integer treeKou;

	@ApiModelProperty("活动说明")
	private String treeDescribe;

	@ApiModelProperty("活动未开始提示")
	private String treeBeforeTxt;

/*	@ApiModelProperty("音乐名称")
	private String treeBgmName;

	@ApiModelProperty("音乐路径")
	private String treeBgm;*/

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTreeName() {
		return treeName;
	}

	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}

	public Date getActivityBeginTime() {
		return activityBeginTime;
	}

	public void setActivityBeginTime(Date activityBeginTime) {
		this.activityBeginTime = activityBeginTime;
	}

	public Date getActivityEndTime() {
		return activityEndTime;
	}

	public void setActivityEndTime(Date activityEndTime) {
		this.activityEndTime = activityEndTime;
	}

	public Integer getTreeEggPartaker() {
		return treeEggPartaker;
	}

	public void setTreeEggPartaker(Integer treeEggPartaker) {
		this.treeEggPartaker = treeEggPartaker;
	}

	public Integer getTreePway() {
		return treePway;
	}

	public void setTreePway(Integer treePway) {
		this.treePway = treePway;
	}

	public Integer getTreeMan() {
		return treeMan;
	}

	public void setTreeMan(Integer treeMan) {
		this.treeMan = treeMan;
	}

	public Integer getTreeKou() {
		return treeKou;
	}

	public void setTreeKou(Integer treeKou) {
		this.treeKou = treeKou;
	}

	public String getTreeDescribe() {
		return treeDescribe;
	}

	public void setTreeDescribe(String treeDescribe) {
		this.treeDescribe = treeDescribe;
	}

	public String getTreeBeforeTxt() {
		return treeBeforeTxt;
	}

	public void setTreeBeforeTxt(String treeBeforeTxt) {
		this.treeBeforeTxt = treeBeforeTxt;
	}
}
