package com.gt.game.core.bean.scratch.res;


import com.gt.game.core.bean.scratch.req.ScratchPrizeSetReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by psr on 2017/9/20 0020.
 */
@ApiModel(value = "通过活动id查询砸金蛋活动返回类")
public class ScratchGetActivityRes {

    @ApiModelProperty("活动名称")
    private String scrName;

    @ApiModelProperty("活动开始时间")
    private Date scrBeginTime;

    @ApiModelProperty("活动结束时间")
    private Date scrEndTime;

    @ApiModelProperty("参与人员 1.所有粉丝  2.仅会员(持有会员卡的粉丝) ")
    private Integer scrPartaker;

    @ApiModelProperty("参与方式 1.所有会员不需要积分  2.会员积分满  3.每次抽奖扣除  4.会员积分满&每次抽奖扣除")
    private Integer scrPway;

    @ApiModelProperty("会员积分满")
    private Integer scrMan;

    @ApiModelProperty("每次抽奖扣除")
    private Integer scrKou;

    @ApiModelProperty("上传横幅")
    private String scrPicture;

    @ApiModelProperty("活动描述")
    private String scrDescribe;

    @ApiModelProperty("活动未开始提示")
    private String scrBeforeTxt;

/*	@ApiModelProperty("音乐名称")
	private String scrBgmName;

	@ApiModelProperty("音乐路径")
	private String scrBgm;*/

    @ApiModelProperty("抽奖总数")
    private Integer scrCountOfAll;

    @ApiModelProperty("抽奖次数")
    private Integer scrCountOfDay;

    @ApiModelProperty("兑奖期限")
    private Integer scrCashDay;

    @ApiModelProperty("兑奖地址")
    private String scrAddress;

    @ApiModelProperty("兑奖方式(1手动 2自动)")
    private Integer scrCashWay;

    @ApiModelProperty("兑奖提示")
    private String scrWinningTxt;

    @ApiModelProperty("中奖须知")
    private String scrWinningNotice;

    @ApiModelProperty("奖品设置")
    private List<ScratchPrizeSetReq> prizeSetList;

    public String getScrName() {
        return scrName;
    }

    public void setScrName(String scrName) {
        this.scrName = scrName;
    }

    public Date getScrBeginTime() {
        return scrBeginTime;
    }

    public void setScrBeginTime(Date scrBeginTime) {
        this.scrBeginTime = scrBeginTime;
    }

    public Date getScrEndTime() {
        return scrEndTime;
    }

    public void setScrEndTime(Date scrEndTime) {
        this.scrEndTime = scrEndTime;
    }

    public Integer getScrPartaker() {
        return scrPartaker;
    }

    public void setScrPartaker(Integer scrPartaker) {
        this.scrPartaker = scrPartaker;
    }

    public Integer getScrPway() {
        return scrPway;
    }

    public void setScrPway(Integer scrPway) {
        this.scrPway = scrPway;
    }

    public Integer getScrMan() {
        return scrMan;
    }

    public void setScrMan(Integer scrMan) {
        this.scrMan = scrMan;
    }

    public Integer getScrKou() {
        return scrKou;
    }

    public void setScrKou(Integer scrKou) {
        this.scrKou = scrKou;
    }

    public String getScrDescribe() {
        return scrDescribe;
    }

    public void setScrDescribe(String scrDescribe) {
        this.scrDescribe = scrDescribe;
    }

    public String getScrBeforeTxt() {
        return scrBeforeTxt;
    }

    public void setScrBeforeTxt(String scrBeforeTxt) {
        this.scrBeforeTxt = scrBeforeTxt;
    }

    public Integer getScrCountOfAll() {
        return scrCountOfAll;
    }

    public void setScrCountOfAll(Integer scrCountOfAll) {
        this.scrCountOfAll = scrCountOfAll;
    }

    public Integer getScrCountOfDay() {
        return scrCountOfDay;
    }

    public void setScrCountOfDay(Integer scrCountOfDay) {
        this.scrCountOfDay = scrCountOfDay;
    }

    public Integer getScrCashDay() {
        return scrCashDay;
    }

    public void setScrCashDay(Integer scrCashDay) {
        this.scrCashDay = scrCashDay;
    }

    public String getScrAddress() {
        return scrAddress;
    }

    public void setScrAddress(String scrAddress) {
        this.scrAddress = scrAddress;
    }

    public Integer getScrCashWay() {
        return scrCashWay;
    }

    public void setScrCashWay(Integer scrCashWay) {
        this.scrCashWay = scrCashWay;
    }

    public String getScrWinningTxt() {
        return scrWinningTxt;
    }

    public void setScrWinningTxt(String scrWinningTxt) {
        this.scrWinningTxt = scrWinningTxt;
    }

    public String getScrWinningNotice() {
        return scrWinningNotice;
    }

    public void setScrWinningNotice(String scrWinningNotice) {
        this.scrWinningNotice = scrWinningNotice;
    }

    public List<ScratchPrizeSetReq> getPrizeSetList() {
        return prizeSetList;
    }

    public void setPrizeSetList(List<ScratchPrizeSetReq> prizeSetList) {
        this.prizeSetList = prizeSetList;
    }

    public String getScrPicture() {
        return scrPicture;
    }

    public void setScrPicture(String scrPicture) {
        this.scrPicture = scrPicture;
    }
}
