package com.gt.game.core.entity.turntable;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 互动中奖记录
 * </p>
 *
 * @author zwq
 * @since 2018-01-22
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_winning_record")
public class WinningRecord extends Model<WinningRecord> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动类型1-大转盘
     */
	private Integer winActType;
    /**
     * 奖品类型1粉币，2-流量
     */
	private Integer winPrizeType;
    /**
     * 具体的奖品（如100粉币）
     */
	private Integer winPrizeLimit;
    /**
     * 具体奖项的ID
     */
	private Integer winPrizeId;
    /**
     * 具体奖项名称
     */
	private String winPrizeName;
    /**
     * 活动表ID
     */
	private Integer winActId;
    /**
     * 活动名称
     */
	private String winActName;
    /**
     * 活动会员ID
     */
	private Integer winWxMemberId;
    /**
     * 微信oppenid
     */
	private String winWxOppenId;
    /**
     * 记录状态1未发放，2-已发放，3-已过期
     */
	private Integer winStatus;
    /**
     * 中奖时间
     */
	private Date winCreatetime;
    /**
     * 过期时间
     */
	private Date winOvertime;
    /**
     * 奖品兑换码
     */
	@TableField("win_exchangeCode")
	private String winExchangeCode;
    /**
     * 商家ID
     */
	private Integer winUserId;
    /**
     * 兑奖人
     */
	@TableField("win_cash_memberId")
	private Integer winCashMemberId;
    /**
     * 兑奖方式
     */
	private Integer winWay;
    /**
     * 兑奖人手机号
     */
	private String winPhone;
    /**
     * 兑奖地址
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
