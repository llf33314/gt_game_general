package com.gt.game.core.entity.raiseflag;

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
 * 升国旗主表
 * </p>
 *
 * @author zwq
 * @since 2018-01-08
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_raiseflag_main")
public class RaiseflagMain extends Model<RaiseflagMain> {

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
     * 背景音乐
     */
	private String musicUrl;
    /**
     * 兑奖说明
     */
	private String cashPrizeInstruction;
    /**
     * 领奖方式(1,到店领取 2,邮寄)
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
     * 商家id
     */
	private Integer busId;
    /**
     * 暂停状态(0：暂停 1：正常)
     */
	private Integer rfStatus;
    /**
     * 奖项设置说明
     */
	private String prizeSetInstruction;
    /**
     * 是否需要赞助商
     */
	private Integer isSponsor;
    /**
     * 赞助商提示语
     */
	private String sponsorTips;
    /**
     * 0:否 1:是
     */
	private Integer isJoinPrize;
    /**
     * 设置参与奖链接
     */
	private String joinPrizeHref;
    /**
     * 参与奖提示语
     */
	private String joinPrizeTips;
    /**
     * 游戏结束后提示语
     */
	private String gameEndTips;
    /**
     * 参与奖按钮提示语
     */
	private String joinPrizeBtnTips;
    /**
     * 授权标识
     */
	private String authoritySign;
    /**
     * 0:否 1:是
     */
	private Integer isMsgTemplate;
    /**
     * 消息模板id
     */
	private Integer msgTemplateId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
