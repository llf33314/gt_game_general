package com.gt.game.core.bean.lantern.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 元宵点灯 主表
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("分页获取中奖记录列表返回类")
public class LanternGetWinningRes {

	@ApiModelProperty("主键id")
	private Integer id;

	@ApiModelProperty("奖品名称")
	private  String prizeName;

	@ApiModelProperty("奖品类型 -1 全部 1 粉币 2 手机流量 4 实体物品 6 积分 7 优惠劵")
	private  Integer type;

	@ApiModelProperty("兑奖时间")
	private Date cashTime;

	@ApiModelProperty("兑奖状态 -1 全部 1 未兑奖 2 已兑奖 3 已提交")
	private Integer status;

	@ApiModelProperty("兑奖码")
	private String snCode;

	@ApiModelProperty("奖品数量")
	private  Integer prizeUnit;

	@ApiModelProperty("兑奖人联系方式")
	private String memberPhone;

	@ApiModelProperty("兑奖人")
	private String memberName;

	@ApiModelProperty("memberId")
	private Integer memberId;

	@ApiModelProperty("成绩")
	private Double score;

	@ApiModelProperty("中奖人")
	private String nickname;

	@ApiModelProperty("领奖方式（1.到店领取 2.邮寄 3 直接兑奖")
	private Integer receiveType;
	/**
	 * 领奖地址ID
	 */
	@ApiModelProperty("到店领取地址")
	private String addressName;
	/**
	 * 邮寄地址
	 */
	@ApiModelProperty("邮寄地址")
	private String address;

}
