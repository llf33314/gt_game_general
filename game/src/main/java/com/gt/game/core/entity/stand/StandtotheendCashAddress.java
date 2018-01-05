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
 * 商家兑奖地址表
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_standtotheend_cash_address")
public class StandtotheendCashAddress extends Model<StandtotheendCashAddress> {

    private static final long serialVersionUID = 1L;

    /**
     * 兑奖地址表主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 兑奖地址
     */
	private String address;
    /**
     * 活动id
     */
	private Integer actId;
    /**
     * 创建时间
     */
	private Date createTime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
