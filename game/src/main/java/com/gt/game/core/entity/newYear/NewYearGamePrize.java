package com.gt.game.core.entity.newYear;

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
 * 元旦游戏奖品
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_new_year_game_prize")
public class NewYearGamePrize extends Model<NewYearGamePrize> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 奖品名称
     */
	private String prizeName;
    /**
     * 类型 (1：粉币 2：积分 3：流量 4：实体)
     */
	private Integer type;
    /**
     * 奖品数量
     */
	private Integer num;
    /**
     * 活动id
     */
	private Integer mainId;
    /**
     * 粉币单位
     */
	private Integer prizeUnit;
    /**
     * 图片奖品说明
     */
	private String imgInstruction;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
