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
 * 抢元宝奖品图片说明
 * </p>
 *
 * @author zwq
 * @since 2018-01-09
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_gold_rush_prize_img")
public class GoldRushPrizeImg extends Model<GoldRushPrizeImg> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 奖品id
     */
	private Integer prizeId;
    /**
     * 奖品图片路径
     */
	private String imgUrl;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
