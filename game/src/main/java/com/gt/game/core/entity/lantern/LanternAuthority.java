package com.gt.game.core.entity.lantern;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 核销权限人员
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_lantern_authority")
public class LanternAuthority extends Model<LanternAuthority> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动主键id
     */
	private Integer actId;
    /**
     * 用户id
     */
	private Integer memberId;
    /**
     * 删除状态 (0:未删除 1:删除)
     */
	private Integer deleteStatus;
    /**
     * 授权时间
     */
	private Date createtime;
    /**
     * 权限人员姓名
     */
	private String memberName;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
