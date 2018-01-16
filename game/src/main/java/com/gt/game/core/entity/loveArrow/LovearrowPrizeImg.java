package com.gt.game.core.entity.loveArrow;

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
 * 一箭穿心奖品图片说明
 * </p>
 *
 * @author zwq
 * @since 2018-01-06
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_lovearrow_prize_img")
public class LovearrowPrizeImg extends Model<LovearrowPrizeImg> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 奖品ID
     */
	private Integer prizeId;
    /**
     * 图片路径
     */
	private String imgUrl;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
