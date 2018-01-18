package com.gt.game.core.entity.countmoney;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author zwq
 * @since 2018-01-18
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_countmoney_courecord")
public class CountmoneyCourecord extends Model<CountmoneyCourecord> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动表ID
     */
	private Integer couActId;
    /**
     * 活动名称
     */
	private String couActName;
    /**
     * 数钱数量
     */
	private Integer couMoneyNum;
    /**
     * 可兑换粉币数
     */
	private Integer couConvertibleFenbi;
    /**
     * 可兑换人民币
     */
	private Integer couConvertibleRmb;
    /**
     * 微信oppenid
     */
	private String couWxOppenId;
    /**
     * 数钱用户ID
     */
	private Integer couMemberId;
    /**
     * 数钱时间
     */
	private Date couCreatetime;
    /**
     * 过期时间
     */
	private Date couOvertime;
    /**
     * 兑换码
     */
	@TableField("cou_exchangeCode")
	private String couExchangeCode;
    /**
     * 兑换用户ID
     */
	@TableField("cou_cash_memberId")
	private Integer couCashMemberId;
    /**
     * 兑换时间
     */
	private Date couCashtime;
    /**
     * 兑换方式1-兑换粉币，2-兑换现金
     */
	private Integer couCashway;
    /**
     * 兑换状态 1-未兑换 2-已兑换
     */
	private Integer couStatus;
    /**
     * 备注
     */
	private String couMemo;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
