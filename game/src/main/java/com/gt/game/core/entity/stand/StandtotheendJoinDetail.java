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
 * 一站到底答题详情
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_standtotheend_join_detail")
public class StandtotheendJoinDetail extends Model<StandtotheendJoinDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 答题详情记录表主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 创建时间
     */
	private Date createTime;
    /**
     * 对应的题目
     */
	private Integer quesId;
    /**
     * 选择的答案
     */
	private String chooseAnswer;
    /**
     * 得分
     */
	private Integer score;
    /**
     * 对应记录表id
     */
	private Integer recordId;
    /**
     * 题目标题
     */
	private String quesTitle;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
