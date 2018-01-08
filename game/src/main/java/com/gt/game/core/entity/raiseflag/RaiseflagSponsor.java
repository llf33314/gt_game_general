package com.gt.game.core.entity.raiseflag;

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
 * 升国旗游戏赞助商
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_raiseflag_sponsor")
public class RaiseflagSponsor extends Model<RaiseflagSponsor> {

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
     * 创建时间
     */
	private Date createtime;
    /**
     * 赞助商链接
     */
	private String sponsorUrl;
    /**
     * 赞助商图片
     */
	private String sponsorImgUrl;
    /**
     * 赞助商名称
     */
	private String sponsorName;
    /**
     * 赞助商说明
     */
	private String sponsorInstruction;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
