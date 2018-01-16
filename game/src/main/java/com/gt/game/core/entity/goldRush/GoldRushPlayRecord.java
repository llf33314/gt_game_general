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
 * 抢元宝游戏记录
 * </p>
 *
 * @author zwq
 * @since 2018-01-09
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_gold_rush_play_record")
public class GoldRushPlayRecord extends Model<GoldRushPlayRecord> {

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
     * 成绩
     */
	private Double score;
    /**
     * 用户id
     */
	private Integer memberId;
    /**
     * 0:游戏完成 1:游戏未完成
     */
	private Integer isGameDone;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
