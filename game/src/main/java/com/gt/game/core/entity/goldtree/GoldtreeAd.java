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
 * 摇钱树广告
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_goldtree_ad")
public class GoldtreeAd extends Model<GoldtreeAd> {

    private static final long serialVersionUID = 1L;

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
     * 广告图片路径
     */
	private String url;
    /**
     * 广告链接
     */
	private String hrefUrl;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
