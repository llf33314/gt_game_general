package com.gt.game.core.bean.luck.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 好运翻翻看
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("好运翻翻看分页查询中奖记录返回对象")
public class LuckWinningListRes {


    /**
     * 主键
     */
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("发放状态 1 未兑奖 2 已兑奖 3 已提交")
	private Integer luckStatus;

	@ApiModelProperty("会员Id")
	private Integer luckMemberId;

	@ApiModelProperty("获奖时间")
	private Date luckCreateTime;

	@ApiModelProperty("兑奖码")
	private String luckExchangeCode;

	@ApiModelProperty("兑奖人")
	private Integer luckCashMemberId;

	@ApiModelProperty("实体物品领取方式1-到店，2-速递")
	private Integer luckWay;

	@ApiModelProperty("兑奖人手机号码")
	private String luckPhone;

	@ApiModelProperty("兑奖人地址")
	private String luckAddress;

	@ApiModelProperty("兑奖人姓名")
	private String luckName;

	@ApiModelProperty("兑奖时间")
	private Date luckCashtime;
}
