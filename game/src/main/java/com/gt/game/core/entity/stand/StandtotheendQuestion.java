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
 * 题目表
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_standtotheend_question")
public class StandtotheendQuestion extends Model<StandtotheendQuestion> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 排序
     */
	private Integer orderId;
    /**
     * 题目标题
     */
	private String quesTitle;
    /**
     * 选项a
     */
	private String optionA;
    /**
     * 选项b
     */
	private String optionB;
    /**
     * 选项c
     */
	private String optionC;
    /**
     * 选项d
     */
	private String optionD;
    /**
     * 创建时间
     */
	private Date createtime;
    /**
     * 正确答案
     */
	private String rightAnswer;
    /**
     * 对应题库id
     */
	private Integer bankId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
