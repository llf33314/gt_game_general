package com.gt.game.core.entity.goldtree;

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
 * 摇钱树设置表
 * </p>
 *
 * @author zwq
 * @since 2018-01-16
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_goldtree_main")
public class GoldtreeMain extends Model<GoldtreeMain> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 商家id
     */
	private Integer busId;
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
     * 免费次数
     */
	private Integer freeTimes;
    /**
     * 每天免费次数
     */
	private Integer dayFreeTimes;
    /**
     * 每次游戏粉币数
     */
	private Integer gameFb;
    /**
     * 每次游戏积分数
     */
	private Integer gameJf;
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
     * 暂停状态(0：暂停 1：正常)
     */
	private Integer rfStatus;
    /**
     * 奖项设置说明
     */
	private String prizeSetInstruction;
    /**
     * 1元宝=多少金钱
     */
	private Integer ingotMoney;
    /**
     * 1金钱=多少金币
     */
	private Integer moneyCoin;
    /**
     * 1开启模板，0关闭模板
     */
	private Integer isMsgTemplate;
    /**
     * 模板id
     */
	private Integer msgTemplateId;
    /**
     * 游戏时间
     */
	private Integer gameTime;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
