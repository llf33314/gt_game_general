package com.gt.game.core.entity.shakeLuck;

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
 * 摇手气游戏记录
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_shakeluck_play_record")
public class ShakeluckPlayRecord extends Model<ShakeluckPlayRecord> {

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
     * 用户手机
     */
	private String memberPhone;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
