package com.gt.game.core.entity.dragonboat;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 端午赛龙舟_奖品
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_dragonboatrace_prize")
public class DragonboatracePrize extends Model<DragonboatracePrize> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 名称
     */
	private String prizeName;
    /**
     * 数量
     */
	private Integer num;
    /**
     * 类型（1：粉币 2：流量 4：实物 6：积分）
     */
	private Integer type;
    /**
     * 活动ID
     */
	private Integer actId;
    /**
     * 单位
     */
	private Integer prizeUnit;
    /**
     * 图片奖励说明
     */
	private String imgInstruction;
    /**
     * 分数
     */
	private Integer score;
    /**
     * 卡券包ID
     */
	private Integer cardReceiveId;
    /**
     * 备注信息
     */
	private String remarks;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
