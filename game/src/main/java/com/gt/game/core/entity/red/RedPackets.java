package com.gt.game.core.entity.red;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 红包表
 * </p>
 *
 * @author zwq
 * @since 2018-01-19
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_red_packets")
public class RedPackets extends Model<RedPackets> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 公众号表ID
     */
	private Integer publicId;
    /**
     * 多粉系统内部订单号
     */
	private String sysOrderNo;
    /**
     * openid
     */
	private String openid;
    /**
     * 红包总金额
     */
	private Integer totalAmount;
    /**
     * 红包发放总人数
     */
	private Integer totalNum;
    /**
     * 红包金额设置方式(普通红包特有属性)
     */
	private String amtType;
    /**
     * 红包祝福语
     */
	private String wishing;
    /**
     * 活动名称
     */
	private String actName;
    /**
     * 活动描述
     */
	private String remark;
    /**
     * 红包单号
     */
	private String detailId;
    /**
     * 对应字典表里面的 1037 红包状态:SENDING:发放中 SENT:已发放待领取 FAILED：发放失败 RECEIVED:已领取 REFUND:已退款
     */
	private String status;
    /**
     * 对应字典表里面的 1038 发放类型:API:通过API接口发放  UPLOAD:通过上传文件方式发放  ACTIVITY:通过活动方式发放
     */
	private String sendType;
    /**
     * 对应字典表里面的 1039 红包类型: GROUP:裂变红包 ;NORMAL:普通红包
     */
	private String hbType;
    /**
     * 失败原因:发送失败原因
     */
	private String reason;
    /**
     * 红包发送时间
     */
	private String sendTime;
    /**
     * 红包退款金额
     */
	private BigDecimal refundAmount;
    /**
     * 发布状态，默认是0，未发布，1是发布
     */
	private Integer start;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
