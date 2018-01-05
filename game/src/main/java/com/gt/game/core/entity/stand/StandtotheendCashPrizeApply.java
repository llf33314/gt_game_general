package com.gt.game.core.entity.stand;

import java.io.Serializable;

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
 * 兑奖申请表

 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_standtotheend_cash_prize_apply")
public class StandtotheendCashPrizeApply extends Model<StandtotheendCashPrizeApply> {

    private static final long serialVersionUID = 1L;

    /**
     * 申请领奖表id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 申请人姓名
     */
	private String applyName;
    /**
     * 申请人id
     */
	private Integer memberId;
    /**
     * 申请时间
     */
	private Date createTime;
    /**
     * 收取奖品方式
     */
	private Integer receiveType;
    /**
     * 邮寄地址
     */
	private String receiveAddress;
    /**
     * 申请记录状态
     */
	private Integer applyStatus;
    /**
     * sn码
     */
	private String snCode;
    /**
     * 申请人手机号
     */
	private String applyPhone;
    /**
     * 奖品id
     */
	private Integer prizeId;
    /**
     * 活动id
     */
	private Integer actId;
    /**
     * 游戏记录id
     */
	private Integer recordId;
    /**
     * 兑现时间
     */
	private Date cashTime;
	private Integer addressId;
	private Integer auId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
