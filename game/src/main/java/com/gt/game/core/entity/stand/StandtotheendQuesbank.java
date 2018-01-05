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
 * 题库表
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_standtotheend_quesbank")
public class StandtotheendQuesbank extends Model<StandtotheendQuesbank> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商家id
     */
	private Integer busId;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 题库名称
     */
	private String bankName;
    /**
     * 题库题目数量 
     */
	private Integer quesAmount;
	private Integer type;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
