package com.gt.game.core.entity.goldtree;

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
 * 
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_goldtree_authority")
public class GoldtreeAuthority extends Model<GoldtreeAuthority> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	private Integer memberId;
	private String memberName;
    /**
     * 摇钱树活动id
     */
	private Integer actId;
	private Date createTime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
