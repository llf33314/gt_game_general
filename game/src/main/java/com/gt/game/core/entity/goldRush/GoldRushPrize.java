package com.gt.game.core.entity.goldRush;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 抢元宝奖品
 * </p>
 *
 * @author zwq
 * @since 2018-01-09
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_gold_rush_prize")
public class GoldRushPrize extends Model<GoldRushPrize> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 奖品名称
     */
	private String prizeName;
    /**
     * 类型 (1：粉币 2：实体)
     */
	private Integer type;
    /**
     * 奖品数量
     */
	private Integer num;
    /**
     * 活动id
     */
	private Integer actId;
    /**
     * 粉币单位
     */
	private Integer prizeUnit;
    /**
     * 图片奖品说明
     */
	private String imgInstruction;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
