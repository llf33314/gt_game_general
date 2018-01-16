package com.gt.game.core.entity.dragonboat;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 端午赛龙舟_游戏记录
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_dragonboatrace_play_record")
public class DragonboatracePlayRecord extends Model<DragonboatracePlayRecord> {

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
     * 成绩
     */
	private Integer score;
    /**
     * 游戏页的提示(0：提示，1：不提示)
     */
	private Integer prompt;
    /**
     * 用户手机号
     */
	private String memberPhone;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
