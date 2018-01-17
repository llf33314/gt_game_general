package com.gt.game.core.entity.shakeLuck;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 摇手气奖品
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_shakeluck_prize")
public class ShakeluckPrize extends Model<ShakeluckPrize> {

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
     * 类型（1：粉币 2：实物）
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
     * 中奖概率（%）
     */
	private Double probabiliy;
    /**
     * 卡券包ID
     */
	private Integer cardReceiveId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
