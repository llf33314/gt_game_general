package com.gt.game.core.entity.newYear;

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
 * 广告轮播表
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_new_year_game_ad")
public class NewYearGameAd extends Model<NewYearGameAd> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动id
     */
	private Integer actId;
    /**
     * 广告标题
     */
	private String title;
    /**
     * 广告图片地址
     */
	private String url;
    /**
     * 广告链接地址
     */
	private String hrefUrl;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
