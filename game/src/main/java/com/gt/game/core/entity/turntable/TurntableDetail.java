package com.gt.game.core.entity.turntable;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 大转盘
 * </p>
 *
 * @author zwq
 * @since 2018-01-22
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_turntable_detail")
public class TurntableDetail extends Model<TurntableDetail> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 奖项名称
     */
	private String turPrizeName;
    /**
     * 奖品数量
     */
	private Integer turPrizeNums;
    /**
     * 中奖概率
     */
	private Float turPrizeChance;
    /**
     * 奖品图片
     */
	private Integer turPrizeImage;
    /**
     * 主表id
     */
	private Integer actId;
    /**
     * 奖品类型
     */
	private String turPrizeType;
    /**
     * 单个的限额
     */
	private Integer turPrizeLimit;
    /**
     * 冻结记录ID
     */
	private Integer freezeId;
    /**
     * 图片地址
     */
	private String turImageUrl;
	private String openid;
	private String nickname;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
