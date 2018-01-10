package com.gt.game.core.entity.goldRush;

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
 * @since 2018-01-09
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_gold_rush_authority")
public class GoldRushAuthority extends Model<GoldRushAuthority> {

    private static final long serialVersionUID = 1L;

    /**
     * 授权人员表主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 授权人员对应活动id
     */
	private Integer mainId;
    /**
     * 授权code
     */
	private String authorityCode;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 对应memberid
     */
	private Integer memberId;
    /**
     * 核销人员核销是否有权核销  0:有权   1:无权
     */
	private Integer state;
    /**
     * 昵名
     */
	private String name;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
