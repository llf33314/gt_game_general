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
 * 升国旗授权人员
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_raiseflag_authority")
public class RaiseflagAuthority extends Model<RaiseflagAuthority> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 删除状态
     */
	private Integer delStatus;
    /**
     * 创建时间
     */
	private Date createtime;
    /**
     * 用户id
     */
	private Integer memberId;
    /**
     * 活动id
     */
	private Integer mainId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
