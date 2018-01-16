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
 * 摇钱树游戏记录
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_goldtree_play_record")
public class GoldtreePlayRecord extends Model<GoldtreePlayRecord> {

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
     * 金币
     */
	private Integer coin;
    /**
     * 元宝
     */
	private Integer ingot;
    /**
     * 用户id
     */
	private Integer memberId;
    /**
     * 兑奖码
     */
	private String snCode;
    /**
     * 1收费游戏，0免费
     */
	private Integer pay;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
