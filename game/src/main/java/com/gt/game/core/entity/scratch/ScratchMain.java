package com.gt.game.core.entity.scratch;

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
 * @since 2018-01-19
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_scratch_main")
public class ScratchMain extends Model<ScratchMain> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动名称
     */
	private String scrName;
    /**
     * 每天抽奖次数
     */
	@TableField("scr_countOfDay")
	private Integer scrCountOfDay;
    /**
     * 中奖后的提示
     */
	private String scrWinningTxt;
    /**
     * 开始时间
     */
	@TableField("scr_beginTime")
	private Date scrBeginTime;
    /**
     * 结束时间
     */
	@TableField("scr_endTime")
	private Date scrEndTime;
    /**
     * 所属用户
     */
	private Integer scrWxUserid;
    /**
     * 活动描述
     */
	private String scrDescribe;
    /**
     * 活动结束说明语
     */
	private String scrOverdescribe;
    /**
     * 活动规则
     */
	private String scrRule;
    /**
     * 概率计算方式
     */
	@TableField("scr_calculateWay")
	private Integer scrCalculateWay;
    /**
     * 总次数
     */
	@TableField("scr_countOfAll")
	private Integer scrCountOfAll;
    /**
     * 活动未开始的提示
     */
	private String scrBeforeTxt;
    /**
     * 是否允许赠送
     */
	private Integer scrIsGive;
    /**
     * 兑奖期限
     */
	private Integer scrCashDay;
    /**
     * 横幅
     */
	private String scrPicture;
    /**
     * 领奖地址
     */
	private String scrAddress;
    /**
     * 背景音乐地址
     */
	private String scrBgm;
    /**
     * 背景音乐名称
     */
	private String scrBgmName;
    /**
     * 参与人员
     */
	private Integer scrPartaker;
    /**
     * 参与方式
     */
	private Integer scrPway;
    /**
     * 满多少
     */
	private Integer scrMan;
    /**
     * 扣多少
     */
	private Integer scrKou;
    /**
     * 是否暂停
     */
	private Integer scrStatus;
    /**
     * 1手动兑奖2自动发放
     */
	private Integer scrCashWay;
    /**
     * 中奖需知
     */
	private String scrWinningNotice;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
