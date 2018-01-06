package com.gt.game.core.entity.lantern;

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
 * 元宵点灯奖品
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_lantern_prize")
public class LanternPrize extends Model<LanternPrize> {

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
     * 卡券包ID
     */
	private Integer cardReceiveId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrizeName() {
		return prizeName;
	}

	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}

	public Integer getPrizeUnit() {
		return prizeUnit;
	}

	public void setPrizeUnit(Integer prizeUnit) {
		this.prizeUnit = prizeUnit;
	}

	public String getImgInstruction() {
		return imgInstruction;
	}

	public void setImgInstruction(String imgInstruction) {
		this.imgInstruction = imgInstruction;
	}

	public Integer getCardReceiveId() {
		return cardReceiveId;
	}

	public void setCardReceiveId(Integer cardReceiveId) {
		this.cardReceiveId = cardReceiveId;
	}
}
