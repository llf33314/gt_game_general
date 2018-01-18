package com.gt.game.core.bean.luck.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 好运翻翻看
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("好运翻翻看保存活动请求对象")
public class LuckReq {


    /**
     * 主键
     */
	@ApiModelProperty("id 新增id = 0 ")
	private Integer id;

	@ApiModelProperty("活动名称")
	@NotEmpty(message = "请输入活动名称")
	private String luckName;
    /**
     * 活动开始时间
     */
	@ApiModelProperty("活动开始时间")
	private Date luckBeginTime;
    /**
     * 活动结束时间
     */
	@ApiModelProperty("活动结束时间")
	private Date luckEndTime;

	/**
	 * 活动描述
	 */
	@ApiModelProperty("活动说明")
	@NotEmpty(message = "请输入活动说明")
	private String luckDescribe;
	/**
	 * 活动结束说明语
	 */
	@ApiModelProperty("活动结束说明语")
	@NotEmpty(message = "请输入活动结束说明语")
	private String luckOverdescribe;
	/**
	 * 总次数
	 */
	@ApiModelProperty("总次数")
	@NotNull(message = "请输入总次数")
	private Integer luckCountOfAll;
	/**
	 * 每天抽奖次数
	 */
	@ApiModelProperty("每天抽奖次数")
	@NotNull(message = "请输入每天抽奖次数")
	private Integer luckCountOfDay;
	/**
	 * 活动未开始的提示
	 */
	@ApiModelProperty("活动未开始提示")
	@NotEmpty(message = "请输入活动未开始提示")
	private String luckBeforeTxt;
	/**
	 * 兑奖期限
	 */
	@ApiModelProperty("兑奖期限")
	private Integer luckCashDay;
	/**
	 * 兑奖地址
	 */
	@ApiModelProperty("兑奖地址")
	@NotEmpty(message = "请输入兑奖地址")
	private String luckAddress;
	/**
	 * 音乐路径
	 */
	@ApiModelProperty("音乐路径")
	@NotEmpty(message = "请选择背景音乐")
	private String luckBgm;
	/**
	 * 音乐名称
	 */
	@ApiModelProperty("音乐名称")
	@NotEmpty(message = "请选择背景音乐")
	private String luckBgmName;
	/**
	 * 参与人员
	 */
	@ApiModelProperty("参与人员 （所有粉丝 1 仅会员 2）")
	private Integer luckLuckPartaker;
	/**
	 * 参与方式
	 */
	@ApiModelProperty("参与方式 （所有会员，不需要积分 1 会员卡积分满 2 会员卡积分满 3 会员卡积分满 4）")
	private String luckPway;
	/**
	 * 满多少
	 */
	@ApiModelProperty("满多少")
	private String luckMan;
	/**
	 * 扣多少
	 */
	@ApiModelProperty("扣多少")
	private String luckKou;

	@ApiModelProperty("奖品")
	private List<LuckDetailReq> luckDetailReqs;

	public List<LuckDetailReq> getLuckDetailReqs() {
		return luckDetailReqs;
	}

	public void setLuckDetailReqs(List<LuckDetailReq> luckDetailReqs) {
		this.luckDetailReqs = luckDetailReqs;
	}

	public String getLuckName() {
		return luckName;
	}

	public void setLuckName(String luckName) {
		this.luckName = luckName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getLuckBeginTime() {
		return luckBeginTime;
	}

	public void setLuckBeginTime(Date luckBeginTime) {
		this.luckBeginTime = luckBeginTime;
	}

	public Date getLuckEndTime() {
		return luckEndTime;
	}

	public void setLuckEndTime(Date luckEndTime) {
		this.luckEndTime = luckEndTime;
	}

	public String getLuckDescribe() {
		return luckDescribe;
	}

	public void setLuckDescribe(String luckDescribe) {
		this.luckDescribe = luckDescribe;
	}

	public String getLuckOverdescribe() {
		return luckOverdescribe;
	}

	public void setLuckOverdescribe(String luckOverdescribe) {
		this.luckOverdescribe = luckOverdescribe;
	}

	public Integer getLuckCountOfAll() {
		return luckCountOfAll;
	}

	public void setLuckCountOfAll(Integer luckCountOfAll) {
		this.luckCountOfAll = luckCountOfAll;
	}

	public Integer getLuckCountOfDay() {
		return luckCountOfDay;
	}

	public void setLuckCountOfDay(Integer luckCountOfDay) {
		this.luckCountOfDay = luckCountOfDay;
	}

	public String getLuckBeforeTxt() {
		return luckBeforeTxt;
	}

	public void setLuckBeforeTxt(String luckBeforeTxt) {
		this.luckBeforeTxt = luckBeforeTxt;
	}

	public Integer getLuckCashDay() {
		return luckCashDay;
	}

	public void setLuckCashDay(Integer luckCashDay) {
		this.luckCashDay = luckCashDay;
	}

	public String getLuckAddress() {
		return luckAddress;
	}

	public void setLuckAddress(String luckAddress) {
		this.luckAddress = luckAddress;
	}

	public String getLuckBgm() {
		return luckBgm;
	}

	public void setLuckBgm(String luckBgm) {
		this.luckBgm = luckBgm;
	}

	public String getLuckBgmName() {
		return luckBgmName;
	}

	public void setLuckBgmName(String luckBgmName) {
		this.luckBgmName = luckBgmName;
	}

	public Integer getLuckLuckPartaker() {
		return luckLuckPartaker;
	}

	public void setLuckLuckPartaker(Integer luckLuckPartaker) {
		this.luckLuckPartaker = luckLuckPartaker;
	}

	public String getLuckPway() {
		return luckPway;
	}

	public void setLuckPway(String luckPway) {
		this.luckPway = luckPway;
	}

	public String getLuckMan() {
		return luckMan;
	}

	public void setLuckMan(String luckMan) {
		this.luckMan = luckMan;
	}

	public String getLuckKou() {
		return luckKou;
	}

	public void setLuckKou(String luckKou) {
		this.luckKou = luckKou;
	}
}
