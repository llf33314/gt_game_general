package com.gt.game.core.entity.eggs;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
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
 * @since 2018-01-17
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_eggs_main")
public class EggsMain extends Model<EggsMain> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动名称
     */
	private String eggName;
    /**
     * 每天抽奖次数
     */
	@TableField("egg_countOfDay")
	private Integer eggCountOfDay;
    /**
     * 中奖后的提示
     */
	private String eggWinningTxt;
    /**
     * 开始时间
     */
	@TableField("egg_beginTime")
	private Date eggBeginTime;
    /**
     * 结束时间
     */
	@TableField("egg_endTime")
	private Date eggEndTime;
    /**
     * 所属用户
     */
	private Integer eggWxUserid;
    /**
     * 活动描述
     */
	private String eggDescribe;
    /**
     * 活动结束说明语
     */
	private String eggOverdescribe;
    /**
     * 活动规则
     */
	private String eggRule;
    /**
     * 概率计算方式
     */
	@TableField("egg_calculateWay")
	private Integer eggCalculateWay;
    /**
     * 总次数
     */
	@TableField("egg_countOfAll")
	private Integer eggCountOfAll;
    /**
     * 活动未开始的提示
     */
	private String eggBeforeTxt;
    /**
     * 是否允许赠送
     */
	private Integer eggIsGive;
    /**
     * 兑奖期限
     */
	private Integer eggCashDay;
    /**
     * 图片
     */
	private String eggPicture;
    /**
     * 兑奖地址
     */
	private String eggAddress;
    /**
     * 背景音乐
     */
	private String eggBgm;
    /**
     * 背景音乐
     */
	private String eggBgmName;
    /**
     * 参与方式(1-所有会员2-积分满即可3-每次扣除4-满并扣除)
     */
	private Integer eggPway;
    /**
     * 满
     */
	private Integer eggMan;
    /**
     * 减
     */
	private Integer eggKou;
    /**
     * 状态
     */
	private Integer eggStatus;
    /**
     * 参与人员
     */
	@TableField("egg_eggPartaker")
	private Integer eggEggPartaker;
    /**
     * 兑奖方式(1手动2自动)
     */
	private Integer eggCashWay;
    /**
     * 中奖需知
     */
	private String eggWinningNotice;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
