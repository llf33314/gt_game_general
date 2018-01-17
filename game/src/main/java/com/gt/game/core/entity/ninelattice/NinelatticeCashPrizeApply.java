package com.gt.game.core.entity.ninelattice;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 幸运九宫格兑奖记录申请
 * </p>
 *
 * @author zwq
 * @since 2018-01-11
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_ninelattice_cash_prize_apply")
public class NinelatticeCashPrizeApply extends Model<NinelatticeCashPrizeApply> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 创建时间
     */
	private Date createtime;
    /**
     * 活动ID
     */
	private Integer actId;
    /**
     * 奖品ID
     */
	private Integer prizeId;
    /**
     * 用户ID
     */
	private Integer memberId;
    /**
     * 领奖方式（1.到店领取 2.邮寄
     */
	private Integer receiveType;
    /**
     * 领奖人名称
     */
	private String memberName;
    /**
     * 领奖人电话
     */
	private String memberPhone;
    /**
     * 领奖地址ID
     */
	private Integer addressId;
    /**
     * 邮寄地址
     */
	private String address;
    /**
     * sn码
     */
	private String snCode;
    /**
     * 兑奖状态
     */
	private Integer status;
    /**
     * 兑奖时间
     */
	private Date cashTime;
    /**
     * 游戏记录ID
     */
	private Integer recordId;
	private Integer auId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
