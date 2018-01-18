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
@TableName("t_wx_activity_countmoney_record")
public class CountmoneyRecord extends Model<CountmoneyRecord> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动类型
     */
	private Integer winActType;
    /**
     * 奖品类型
     */
	private Integer winPrizeType;
    /**
     * 具体的奖品数量（如100）
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
     * 中奖人id
     */
	private Integer winWxMemberId;
    /**
     * 微信oppenid
     */
	private String winWxOppenId;
    /**
     * 记录状态1未兑换 2-已兑换，3-已提交
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
     * 兑奖人姓名
     */
	private String winName;
    /**
     * 中奖人手机号码
     */
	private String winPhone;
    /**
     * 该用户最大数钱粉币数
     */
	private Integer fenbiNumMax;
    /**
     * 兑换码
     */
	@TableField("win_exchangeCode")
	private String winExchangeCode;
    /**
     * 实体物品领取方式1-到店，2-速递
     */
	private Integer winWay;
    /**
     * 兑奖人地址
     */
	private String winAddress;
    /**
     * 兑奖时间
     */
	private Date winCashtime;
    /**
     * 兑奖人id
     */
	@TableField("win_cash_memberId")
	private Integer winCashMemberId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
