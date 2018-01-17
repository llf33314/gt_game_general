package com.gt.game.core.entity.goldtree;

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
 * 摇钱树奖品
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_goldtree_prize")
public class GoldtreePrize extends Model<GoldtreePrize> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 名称
     */
	private String prizeName;
    /**
     * 数量
     */
	private Integer num;
    /**
     * 类型 (1：粉币 2：实体,3流量，4积分)
     */
	private Integer type;
    /**
     * 活动id
     */
	private Integer actId;
    /**
     * 图片奖品说明
     */
	private String imgInstruction;
    /**
     * 总数
     */
	private Integer total;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
