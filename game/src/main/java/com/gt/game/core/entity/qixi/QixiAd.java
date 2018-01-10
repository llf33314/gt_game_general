package com.gt.game.core.entity.qixi;

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
 * 广告轮播图
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_qixi_ad")
public class QixiAd extends Model<QixiAd> {

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
