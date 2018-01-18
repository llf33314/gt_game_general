package com.gt.game.core.entity.countmoney;

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
 * @since 2018-01-18
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_countmoney_detail")
public class CountmoneyDetail extends Model<CountmoneyDetail> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 奖项名称
     */
	private String turPrizeName;
    /**
     * 奖品类型
     */
	private Integer turPrizeType;
    /**
     * 奖品单位
     */
	private Integer turPrizeUnit;
    /**
     * 奖品数量
     */
	private Integer turPrizeNums;
    /**
     * 奖品图片地址
     */
	private String turImageUrl;
    /**
     * 主表id
     */
	private Integer actId;
    /**
     * 冻结记录id
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
