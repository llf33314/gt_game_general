package com.gt.game.core.entity.shakeLuck;

import java.io.Serializable;

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
 * 摇手气主表
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_shakeluck_main")
public class ShakeluckMain extends Model<ShakeluckMain> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 创建时间
     */
	private Date createtime;
    /**
     * 活动开始时间
     */
	private Date activityBeginTime;
    /**
     * 活动结束时间
     */
	private Date activityEndTime;
    /**
     * 兑奖开始时间
     */
	private Date cashPrizeBeginTime;
    /**
     * 兑奖结束时间
     */
	private Date cashPrizeEndTime;
    /**
     * 游戏规则
     */
	private String actRule;
    /**
     * 游戏名称
     */
	private String name;
    /**
     * 关注二维码
     */
	private String followQrCode;
    /**
     * 每人每天机会
     */
	private Integer manDayChance;
    /**
     * 每人总机会
     */
	private Integer manTotalChance;
    /**
     * 兑奖说明
     */
	private String cashPrizeInstruction;
    /**
     * 领奖方式（1，到店领取 2，邮寄）
     */
	private String receiveType;
    /**
     * 联系电话
     */
	private String phone;
    /**
     * 兑奖地址
     */
	private String cashAddress;
    /**
     * 商家ID
     */
	private Integer busId;
    /**
     * 背景音乐
     */
	private String musicUrl;
    /**
     * 背景音乐名
     */
	private String bgmSp;
    /**
     * 商家标志
     */
	private String busLogo;
    /**
     * 商家名称
     */
	private String busName;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
