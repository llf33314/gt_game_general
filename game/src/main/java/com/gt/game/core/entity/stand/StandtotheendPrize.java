package com.gt.game.core.entity.stand;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
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
 * 一站到底奖品表
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_standtotheend_prize")
public class StandtotheendPrize extends Model<StandtotheendPrize> {

    private static final long serialVersionUID = 1L;

    /**
     * 奖品id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 奖品名称
     */
	private String prizeName;
    /**
     * 活动id
     */
	private Integer actId;
    /**
     * 奖品创建时间
     */
	private Date createtime;
    /**
     * 奖品数量
     */
	private Integer prizeCount;
    /**
     * 中奖后分发数量
     */
	@TableField("prize_Per")
	private Integer prizePer;
    /**
     * 中奖概率
     */
	private Double prizeChance;
    /**
     * 奖品图片地址
     */
	private String prizeImg;
    /**
     * 奖品类型
     */
	private Integer prizeType;
    /**
     * 奖品图片说明
     */
	@TableField("img_Instruction")
	private String imgInstruction;
    /**
     * 奖品链接
     */
	private String cashUrl;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
