package com.gt.game.core.entity.demolition;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
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
 * 拆礼品盒_礼盒
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_demolitiongiftbox_giftbox")
public class DemolitiongiftboxGiftbox extends Model<DemolitiongiftboxGiftbox> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 礼品盒名称
     */
	private String giftName;
    /**
     * 礼品盒图片
     */
	private String giftImg;
    /**
     * 礼品盒声音
     */
	private String giftSound;
    /**
     * 活动ID
     */
	private Integer actId;
    /**
     * 是否有奖(0,无奖  1,有奖)
     */
	@TableField("Award")
	private Integer Award;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
