package com.gt.game.core.entity.stand;

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
 * 奖品图片地址
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_standtotheend_prize_img_url")
public class StandtotheendPrizeImgUrl extends Model<StandtotheendPrizeImgUrl> {

    private static final long serialVersionUID = 1L;

    /**
     * 图片表id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 图片地址
     */
	private String picUrl;
    /**
     * 奖品id
     */
	private Integer prizeId;
    /**
     * 创建时间
     */
	private Date createTime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
