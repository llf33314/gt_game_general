package com.gt.game.core.entity.eggs;

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
 * 
 * </p>
 *
 * @author zwq
 * @since 2018-01-17
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_eggs_detail")
public class EggsDetail extends Model<EggsDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动主表
     */
	private Integer eggId;
    /**
     * 奖项名称
     */
	private String eggPrizeName;
    /**
     * 奖品数量
     */
	private Integer eggPrizeNums;
    /**
     * 中奖概率
     */
	private Double eggPrizeChance;
    /**
     * 奖品图片
     */
	private String eggPrizeImage;
    /**
     * 奖品类型
     */
	private Integer eggPrizeType;
    /**
     * 奖品额度
     */
	private Integer eggPrizeLimit;
    /**
     * 冻结记录ID
     */
	private Integer freezeId;
	private String openid;
	private String nickname;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
