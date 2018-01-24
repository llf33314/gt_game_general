package com.gt.game.core.bean.tree.res;


import com.gt.game.core.bean.tree.req.TreePrizeSetReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by psr on 2017/9/20 0020.
 */
@ApiModel(value = "通过活动id查询圣诞大礼包活动返回类")
public class TreeGetActivityRes {

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

	@ApiModelProperty("音乐名称")
	private String treeBgmName;

	@ApiModelProperty("音乐路径")
	private String treeBgm;

    @ApiModelProperty("抽奖总数")
    private Integer treeCountOfAll;

    @ApiModelProperty("抽奖次数")
    private Integer treeCountOfDay;

    @ApiModelProperty("兑奖期限")
    private Integer treeCashDay;

    @ApiModelProperty("兑奖地址")
    private String treeAddress;

    @ApiModelProperty("是否允许转赠送 0.不允许 1.允许")
    private Integer treeIsGive;

    @ApiModelProperty("兑奖提示")
    private String treeWinningTxt;

    @ApiModelProperty("奖品设置")
    private List<TreePrizeSetReq> prizeSetList;

    public String getTreeBgmName() {
        return treeBgmName;
    }

    public void setTreeBgmName(String treeBgmName) {
        this.treeBgmName = treeBgmName;
    }

    public String getTreeBgm() {
        return treeBgm;
    }

    public void setTreeBgm(String treeBgm) {
        this.treeBgm = treeBgm;
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

    public Integer getTreeCountOfAll() {
        return treeCountOfAll;
    }

    public void setTreeCountOfAll(Integer treeCountOfAll) {
        this.treeCountOfAll = treeCountOfAll;
    }

    public Integer getTreeCountOfDay() {
        return treeCountOfDay;
    }

    public void setTreeCountOfDay(Integer treeCountOfDay) {
        this.treeCountOfDay = treeCountOfDay;
    }

    public Integer getTreeCashDay() {
        return treeCashDay;
    }

    public void setTreeCashDay(Integer treeCashDay) {
        this.treeCashDay = treeCashDay;
    }

    public String getTreeAddress() {
        return treeAddress;
    }

    public void setTreeAddress(String treeAddress) {
        this.treeAddress = treeAddress;
    }

    public Integer getTreeIsGive() {
        return treeIsGive;
    }

    public void setTreeIsGive(Integer treeIsGive) {
        this.treeIsGive = treeIsGive;
    }

    public String getTreeWinningTxt() {
        return treeWinningTxt;
    }

    public void setTreeWinningTxt(String treeWinningTxt) {
        this.treeWinningTxt = treeWinningTxt;
    }

    public List<TreePrizeSetReq> getPrizeSetList() {
        return prizeSetList;
    }

    public void setPrizeSetList(List<TreePrizeSetReq> prizeSetList) {
        this.prizeSetList = prizeSetList;
    }
}
