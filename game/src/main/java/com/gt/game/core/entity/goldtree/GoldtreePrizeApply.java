package com.gt.game.core.entity.goldtree;

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
 * 摇钱树兑奖记录申请
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_goldtree_prize_apply")
public class GoldtreePrizeApply extends Model<GoldtreePrizeApply> {

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
     * 活动id
     */
	private Integer actId;
    /**
     * 奖品id
     */
	private Integer prizeId;
    /**
     * 用户id
     */
	private Integer memberId;
    /**
     * 领奖方式(1：到店领取 2：邮寄 3:直接兑换)
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
     * 领奖地址
     */
	private String receiveAddress;
    /**
     * 邮寄地址
     */
	private String address;
    /**
     * sn码
     */
	private String snCode;
    /**
     * 兑奖状态(0新建，1提交，2已兑奖)
     */
	private Integer status;
    /**
     * 兑奖时间
     */
	private Date cashTime;
    /**
     * 游戏记录id
     */
	private Integer recordId;
    /**
     * 排名
     */
	private Integer ranking;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
