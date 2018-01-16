package com.gt.game.core.entity.qixi;

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
 * 浪漫七夕游戏记录
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_qixi_play_record")
public class QixiPlayRecord extends Model<QixiPlayRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 创建时间
     */
	private Date createtime;
    /**
     * 活动ID
     */
	private Integer actId;
    /**
     * 用户ID
     */
	private Integer memberId;
    /**
     * 兑奖码
     */
	private String snCode;
    /**
     * 0:游戏完成 1:游戏未完成
     */
	private Integer isGameDone;
    /**
     * 成绩(毫秒值，越短成绩越高)
     */
	private Integer score;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
