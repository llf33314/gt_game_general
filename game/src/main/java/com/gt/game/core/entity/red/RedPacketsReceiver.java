package com.gt.game.core.entity.red;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.enums.IdType;
import java.sql.Blob;
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
@TableName("t_wx_red_packets_receiver")
public class RedPacketsReceiver extends Model<RedPacketsReceiver> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 公众号表ID
     */
	private Integer publicId;
    /**
     * 领取红包的openid
     */
	private String openid;
    /**
     * 领取金额
     */
	private BigDecimal amount;
    /**
     * 领取红包的时间
     */
	private String rcvTime;
    /**
     * 红包表ID
     */
	private Integer redId;
	private String ncikname;
	private String headimgurl;
    /**
     * 是否关注 1：关注  0未关注
     */
	private Integer subscribe;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
