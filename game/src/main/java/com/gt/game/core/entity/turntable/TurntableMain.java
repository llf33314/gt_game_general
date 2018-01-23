package com.gt.game.core.entity.turntable;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 活动主表
 * </p>
 *
 * @author zwq
 * @since 2018-01-22
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_turntable_main")
public class TurntableMain extends Model<TurntableMain> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动名称
     */
	private String actName;
    /**
     * 是否显示奖品数量
     */
	@TableField("act_isShowNums")
	private Integer actIsShowNums;
    /**
     * 每人每天的抽奖次数
     */
	@TableField("act_countOfDay")
	private Integer actCountOfDay;
    /**
     * 每人抽奖总次数
     */
	@TableField("act_totalOfAct")
	private Integer actTotalOfAct;
    /**
     * 超越抽奖次数后的提示
     */
	@TableField("act_exceedTxt")
	private String actExceedTxt;
    /**
     * 活动开始时间
     */
	@TableField("act_beginTime")
	private Date actBeginTime;
    /**
     * 活动结束时间
     */
	@TableField("act_endTime")
	private Date actEndTime;
    /**
     * 关联公众号用户ID
     */
	private Integer actWxUserid;
    /**
     * 活动描述
     */
	private String actDescribe;
    /**
     * 活动类型(1-大转盘)
     */
	private Integer actType;
    /**
     * 活动结束说明
     */
	private String actOverdescribe;
    /**
     * 概率计算方式
     */
	@TableField("act_calculateWay")
	private Integer actCalculateWay;
    /**
     * 兑奖期限
     */
	private Integer actCashday;
    /**
     * 兑奖地址
     */
	private String actAddress;
    /**
     * 兑奖提示
     */
	private String actCashtext;
    /**
     * 是否赠送
     */
	private Integer actIsgive;
    /**
     * 背景地址
     */
	private String actBgm;
    /**
     * 音乐名称
     */
	private String actBgmName;
    /**
     * 参与人员
     */
	private Integer actPartaker;
    /**
     * 参与方式
     */
	private Integer actPway;
    /**
     * 满
     */
	private Integer actMan;
    /**
     * 扣
     */
	private Integer actKou;
    /**
     * 状态
     */
	private Integer actStatus;
    /**
     * 兑奖方式
     */
	private Integer actCashWay;
    /**
     * 中奖需知
     */
	private String actWinningNotice;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
