package com.gt.game.core.entity.tree;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;


import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 圣诞树大礼包奖品
 * </p>
 *
 * @author zwq
 * @since 2018-01-15
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_tree_detail")
public class TreeDetail extends Model<TreeDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动主表id(t_wx_activity_tree_main)
     */
	private Integer treeId;
    /**
     * 奖项名称
     */
	private String treePrizeName;
    /**
     * 奖品数量
     */
	private Integer treePrizeNums;
    /**
     * 中奖概率
     */
	private Double treePrizeChance;
    /**
     * 奖品图片
     */
	private String treePrizeImage;
    /**
     * 奖品类型
     */
	private Integer treePrizeType;
    /**
     * 奖品额度
     */
	private Integer treePrizeLimit;
    /**
     * 冻结Id
     */
	private Integer freezeId;
    /**
     * openid
     */
	private String openid;
    /**
     * nickname
     */
	private String nickname;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
