package com.gt.game.core.entity.tree;

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
 * 圣诞树大礼包
 * </p>
 *
 * @author zwq
 * @since 2018-01-15
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_tree_main")
public class TreeMain extends Model<TreeMain> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动名称
     */
	private String treeName;
    /**
     * 每天抽奖次数
     */
	@TableField("tree_countOfDay")
	private Integer treeCountOfDay;
    /**
     * 中奖后的提示
     */
	private String treeWinningTxt;
    /**
     * 开始时间
     */
	@TableField("tree_beginTime")
	private Date treeBeginTime;
    /**
     * 结束时间
     */
	@TableField("tree_endTime")
	private Date treeEndTime;
    /**
     * 所属用户
     */
	private Integer treeWxUserid;
    /**
     * 活动描述
     */
	private String treeDescribe;
    /**
     * 活动结束说明语
     */
	private String treeOverdescribe;
    /**
     * 活动规则
     */
	private String treeRule;
    /**
     * 概率计算方式
     */
	@TableField("tree_calculateWay")
	private Integer treeCalculateWay;
    /**
     * 总次数
     */
	@TableField("tree_countOfAll")
	private Integer treeCountOfAll;
    /**
     * 活动未开始的提示
     */
	private String treeBeforeTxt;
    /**
     * 是否允许转赠送
     */
	private Integer treeIsGive;
    /**
     * 兑奖期限
     */
	private Integer treeCashDay;
    /**
     * 图片地址
     */
	private String treePicture;
    /**
     * 兑奖地址
     */
	private String treeAddress;
    /**
     * 音乐路径
     */
	private String treeBgm;
    /**
     * 音乐名称
     */
	private String treeBgmName;
    /**
     * 参与人员
     */
	@TableField("tree_eggPartaker")
	private Integer treeEggPartaker;
    /**
     * 参与方式
     */
	private Integer treePway;
    /**
     * 满多少
     */
	private Integer treeMan;
    /**
     * 扣多少
     */
	private Integer treeKou;
    /**
     * 活动状态
     */
	private Integer treeStatus;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
