package com.gt.game.core.entity.eggs;

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
 * @since 2018-01-17
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_eggs_winning")
public class EggsWinning extends Model<EggsWinning> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动ID
     */
	private Integer winActId;
    /**
     * 奖品ID
     */
	private Integer winPrizeId;
    /**
     * 发放状态1-未领取,2-已领取
     */
	private Integer winStatus;
    /**
     * 会员ID
     */
	@TableField("win_memberId")
	private Integer winMemberId;
    /**
     * 获奖时间
     */
	private Date winCreateTime;
    /**
     * 所属用户
     */
	private Integer winUserId;
    /**
     * 兑换码
     */
	@TableField("win_exchangeCode")
	private String winExchangeCode;
    /**
     * 兑奖人
     */
	@TableField("win_cash_memberId")
	private Integer winCashMemberId;
    /**
     * 实体物品领取方式1-到店，2-速递
     */
	private Integer winWay;
    /**
     * 兑奖人手机号码
     */
	private String winPhone;
    /**
     * 兑奖人地址
     */
	private String winAddress;
    /**
     * 兑奖人姓名
     */
	private String winName;
    /**
     * 兑奖时间
     */
	private Date winCashtime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
