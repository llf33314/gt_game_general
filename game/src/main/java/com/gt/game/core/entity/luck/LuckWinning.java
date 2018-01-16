package com.gt.game.core.entity.luck;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import java.util.Date;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 好运翻翻看中奖信息
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_luck_winning")
public class LuckWinning extends Model<LuckWinning> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动Id(t_wx_activity_luck_main)
     */
	private Integer luckActId;
    /**
     * 奖品Id
     */
	private Integer luckPrizeId;
    /**
     * 发放状态     1.以领取   2.未领取
     */
	private Integer luckStatus;
    /**
     * 会员Id
     */
	@TableField("luck_memberId")
	private Integer luckMemberId;
    /**
     * 获奖时间
     */
	private Date luckCreateTime;
    /**
     * 所属用户
     */
	private Integer luckUserId;
    /**
     * 兑奖码
     */
	@TableField("luck_exchangeCode")
	private String luckExchangeCode;
    /**
     * 兑奖人
     */
	@TableField("luck_cash_memberId")
	private Integer luckCashMemberId;
    /**
     * 实体物品领取方式1-到店，2-速递
     */
	private Integer luckWay;
    /**
     * 兑奖人手机号码
     */
	private String luckPhone;
    /**
     * 兑奖人地址
     */
	private String luckAddress;
    /**
     * 兑奖人姓名
     */
	private String luckName;
    /**
     * 兑奖时间
     */
	private Date luckCashtime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
