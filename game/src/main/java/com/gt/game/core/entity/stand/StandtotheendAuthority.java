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
 * 
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_standtotheend_authority")
public class StandtotheendAuthority extends Model<StandtotheendAuthority> {

    private static final long serialVersionUID = 1L;

    /**
     * 一站到底授权人员表主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 授权人员对应活动id
     */
	private Integer mainId;
	private String authorityCode;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 对应memberid
     */
	private Integer memberId;
	private Integer state;
	private String name;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
