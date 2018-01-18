package com.gt.game.core.entity.countmoney;

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
 * @since 2018-01-18
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_countmoney_main")
public class CountmoneyMain extends Model<CountmoneyMain> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动名称
     */
	private String actName;
    /**
     * 活动尚未开始提示
     */
	@TableField("act_notStartedTips")
	private String actNotStartedTips;
    /**
     * 兑奖提示
     */
	@TableField("act_awardingTips")
	private String actAwardingTips;
    /**
     * 游戏持续时间（秒）
     */
	@TableField("act_gameTime")
	private Integer actGameTime;
    /**
     * 奖品数量是否显示
     */
	@TableField("act_isShowNums")
	private Integer actIsShowNums;
    /**
     * 活动开始时间
     */
	@TableField("act_beginTime")
	private Date actBeginTime;
    /**
     * 活动类型(1-排名中奖；2-数钱折算)
     */
	private Integer actType;
    /**
     * 活动说明
     */
	private String actDescribe;
    /**
     * 兑奖地址
     */
	@TableField("act_awardingAddress")
	private String actAwardingAddress;
    /**
     * 兑奖期限（从活动结束算起：天）
     */
	@TableField("act_awardingTime")
	private Integer actAwardingTime;
    /**
     * 活动结束时间
     */
	@TableField("act_endTime")
	private Date actEndTime;
    /**
     * 每人游戏总次数
     */
	@TableField("act_totalOfAct")
	private Integer actTotalOfAct;
    /**
     * 每人每天的可玩游戏次数
     */
	@TableField("act_countOfDay")
	private Integer actCountOfDay;
    /**
     * 关联公众号用户ID
     */
	private Integer actWxUserid;
    /**
     * 活动状态(-1--删除  0--正常  1：开始  2：停止)
     */
	private Integer actWxStatus;
    /**
     * 活动冻结流量
     */
	private Integer actFrozenFlow;
    /**
     * 活动冻结粉币
     */
	private Integer actFrozenFenbi;
	@TableField("act_scrBgmUrl")
	private String actScrBgmUrl;
	@TableField("act_scrBgmName")
	private String actScrBgmName;
    /**
     * 数钱数：粉币 兑换比例 1:2
     */
	private Integer actRateFenbi;
    /**
     * 数钱数：人民币 兑换比例 1:10
     */
	private Integer actRateRmb;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
