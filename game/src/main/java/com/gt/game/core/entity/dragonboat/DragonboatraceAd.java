package com.gt.game.core.entity.dragonboat;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 广告轮播图
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_dragonboatrace_ad")
public class DragonboatraceAd extends Model<DragonboatraceAd> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动ID
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
