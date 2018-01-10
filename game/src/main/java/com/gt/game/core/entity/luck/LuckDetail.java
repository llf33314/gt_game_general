package com.gt.game.core.entity.luck;

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
 * 好运翻翻看奖品
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_luck_detail")
public class LuckDetail extends Model<LuckDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动主表id(t_wx_activity_luck_main)
     */
	private Integer luckId;
    /**
     * 奖项名称
     */
	private String luckPrizeName;
    /**
     * 奖品数量
     */
	private Integer luckPrizeNums;
    /**
     * 中奖概率
     */
	private Double luckPrizeChance;
    /**
     * 奖品图片
     */
	private String luckPrizeImage;
    /**
     * 奖品类型
     */
	private Integer luckPrizeType;
    /**
     * 奖品额度
     */
	private Integer luckPrizeLimit;
    /**
     * 冻结Id
     */
	private Integer freezeId;
    /**
     * openid
     */
	private String openid;
    /**
     * nickname
     */
	private String nickname;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
