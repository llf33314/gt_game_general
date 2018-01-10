package com.gt.game.core.entity.goldRush;

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
 * 抢元宝游戏领奖地址
 * </p>
 *
 * @author zwq
 * @since 2018-01-09
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_gold_rush_address")
public class GoldRushAddress extends Model<GoldRushAddress> {

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
     * 领奖地址
     */
	private String address;
    /**
     * 活动主键
     */
	private Integer actId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
