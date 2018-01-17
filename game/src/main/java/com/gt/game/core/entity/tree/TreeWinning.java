package com.gt.game.core.entity.tree;

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
 * 圣诞树大礼包中奖信息
 * </p>
 *
 * @author zwq
 * @since 2018-01-15
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_tree_winning")
public class TreeWinning extends Model<TreeWinning> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动Id(t_wx_activity_tree_main)
     */
	private Integer treeActId;
    /**
     * 奖品Id
     */
	private Integer treePrizeId;
    /**
     * 发放状态     1.以领取   2.未领取
     */
	private Integer treeStatus;
    /**
     * 会员Id
     */
	@TableField("tree_memberId")
	private Integer treeMemberId;
    /**
     * 获奖时间
     */
	private Date treeCreateTime;
    /**
     * 所属用户
     */
	private Integer treeUserId;
    /**
     * 兑奖码
     */
	@TableField("tree_exchangeCode")
	private String treeExchangeCode;
    /**
     * 兑奖人
     */
	@TableField("tree_cash_memberId")
	private Integer treeCashMemberId;
    /**
     * 实体物品领取方式1-到店，2-速递
     */
	private Integer treeWay;
    /**
     * 兑奖人手机号码
     */
	private String treePhone;
    /**
     * 兑奖人地址
     */
	private String treeAddress;
    /**
     * 兑奖人姓名
     */
	private String treeName;
    /**
     * 兑奖时间
     */
	private Date treeCashtime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
