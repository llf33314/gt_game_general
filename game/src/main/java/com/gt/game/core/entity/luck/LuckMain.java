package com.gt.game.core.entity.luck;

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
 * 好运翻翻看
 * </p>
 *
 * @author zwq
 * @since 2018-01-10
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_luck_main")
public class LuckMain extends Model<LuckMain> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动名称
     */
	private String luckName;
    /**
     * 每天抽奖次数
     */
	@TableField("luck_countOfDay")
	private Integer luckCountOfDay;
    /**
     * 中奖后的提示
     */
	private String luckWinningTxt;
    /**
     * 开始时间
     */
	@TableField("luck_beginTime")
	private Date luckBeginTime;
    /**
     * 结束时间
     */
	@TableField("luck_endTime")
	private Date luckEndTime;
    /**
     * 所属用户
     */
	private Integer luckWxUserid;
    /**
	 * 活动描述
	 */
	private String luckDescribe;
	/**
	 * 活动结束说明语
	 */
	private String luckOverdescribe;
	/**
	 * 活动规则
	 */
	private String luckRule;
	/**
	 * 概率计算方式
	 */
	@TableField("luck_calculateWay")
	private Integer luckCalculateWay;
	/**
	 * 总次数
	 */
	@TableField("luck_countOfAll")
	private Integer luckCountOfAll;
	/**
	 * 活动未开始的提示
	 */
	private String luckBeforeTxt;
	/**
	 * 是否允许转赠送
	 */
	private Integer luckIsGive;
	/**
	 * 兑奖期限
	 */
	private Integer luckCashDay;
	/**
	 * 图片地址
	 */
	private String luckPicture;
	/**
	 * 兑奖地址
	 */
	private String luckAddress;
	/**
	 * 音乐路径
	 */
	private String luckBgm;
	/**
	 * 音乐名称
	 */
	private String luckBgmName;
	/**
	 * 参与人员
	 */
	@TableField("luck_luckPartaker")
	private Integer luckLuckPartaker;
	/**
	 * 参与方式
	 */
	private Integer luckPway;
	/**
	 * 满多少
	 */
	private Integer luckMan;
	/**
	 * 扣多少
	 */
	private Integer luckKou;
    /**
     * 活动状态
     */
	private Integer luckStatus;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
