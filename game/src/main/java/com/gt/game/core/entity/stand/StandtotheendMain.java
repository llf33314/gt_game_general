package com.gt.game.core.entity.stand;

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
 * 商家活动表
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_standtotheend_main")
public class StandtotheendMain extends Model<StandtotheendMain> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商家id
     */
	@TableField("bus_Id")
	private Integer busId;
    /**
     * 商家logo
     */
	private String busLogo;
    /**
     * 商家名称
     */
	private String busName;
    /**
     * 创建时间
     */
	private Date createtime;
    /**
     * 活动开始时间
     */
	private Date activityBegintime;
    /**
     * 活动结束时间
     */
	private Date activityEndtime;
    /**
     * 活动说明
     */
	private String actRule;
    /**
     * 活动名称
     */
	private String actName;
    /**
     * 关注的二维码地址
     */
	private String followQrCode;
    /**
     * 每日参与次数
     */
	private Integer manDayChance;
    /**
     * 每日参与答题的总数
     */
	@TableField("man_day_total_Ques_Chance")
	private Integer manDayTotalQuesChance;
    /**
     * 正确答题的奖励分数
     */
	private Integer rightCount;
    /**
     * 用于回答的时间
     */
	private Integer answerTime;
    /**
     * 结束提示语
     */
	private String endTips;
    /**
     * 题库id
     */
	private Integer bankId;
    /**
     * 活动banner
     */
	private String banner;
    /**
     * 兑奖说明
     */
	private String cashPrizeInstruction;
    /**
     * 奖品收取方式
     */
	private String receiveType;
    /**
     * 获奖人手机号
     */
	private String phone;
    /**
     * 活动开始提示语
     */
	private String standBeforeText;
    /**
     * 活动状态
     */
	private Integer rfStatue;
    /**
     * 活动结束后用于兑奖的天数
     */
	private Integer standCashDay;
    /**
     * 昵称
     */
	private String nickName;
	private String musicUrl;
	private String bgmSp;
	private String authorityCode;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
