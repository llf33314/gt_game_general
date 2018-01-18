package com.gt.game.core.entity.countmoney;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotations.TableId;
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
 * @since 2018-01-18
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_countmoney_probabilityset")
public class CountmoneyProbabilityset extends Model<CountmoneyProbabilityset> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 主表id
     */
	private Integer actId;
    /**
     * 粉币类型
     */
	private Integer fenbiType;
    /**
     * 粉币概率
     */
	private BigDecimal fenbiChance;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
