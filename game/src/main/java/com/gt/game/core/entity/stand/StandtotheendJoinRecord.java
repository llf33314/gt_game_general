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
 * 一站到底活动记录表
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_standtotheend_join_record")
public class StandtotheendJoinRecord extends Model<StandtotheendJoinRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 记录表id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动id
     */
	private Integer actId;
    /**
     * 参与人id
     */
	private Integer memberId;
    /**
     * 创建时间
     */
	private Date createtime;
    /**
     * 参与人姓名
     */
	private String memberName;
    /**
     * 奖品获取方式
     */
	private String receiveType;
    /**
     * 参与人手机号
     */
	private String memberPhone;
    /**
     * 总得分
     */
	private Integer totalScore;
    /**
     * 是否完成（0完成，1未完成）
     */
	private Integer isComplete;
    /**
     * 答对次数
     */
	private Integer rightCount;
    /**
     * 答错次数
     */
	private Integer falseCount;
    /**
     * 总用时
     */
	private Integer totalTime;
    /**
     * 微信昵称
     */
	private String nickName;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
