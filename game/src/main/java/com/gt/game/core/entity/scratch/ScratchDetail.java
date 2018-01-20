package com.gt.game.core.entity.scratch;

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
 * @since 2018-01-19
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_scratch_detail")
public class ScratchDetail extends Model<ScratchDetail> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动主表
     */
	private Integer scrId;
    /**
     * 奖项名称
     */
	private String scrPrizeName;
    /**
     * 奖品数量
     */
	private Integer scrPrizeNums;
    /**
     * 中奖概率
     */
	private Float scrPrizeChance;
    /**
     * 奖品图片
     */
	private String scrPrizeImage;
    /**
     * 奖品类型
     */
	private Integer scrPrizeType;
    /**
     * 奖品额度
     */
	private Integer scrPrizeLimit;
    /**
     * 冻结记录ID
     */
	private Integer freezeId;
    /**
     * openid(查询方便)
     */
	private String openid;
    /**
     * nickname(查询方便)
     */
	private String nickname;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
