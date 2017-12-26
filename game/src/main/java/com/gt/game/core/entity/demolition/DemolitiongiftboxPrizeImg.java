package com.gt.game.core.entity.demolition;

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
 * 拆礼盒图片说明
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_demolitiongiftbox_prize_img")
public class DemolitiongiftboxPrizeImg extends Model<DemolitiongiftboxPrizeImg> {

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
