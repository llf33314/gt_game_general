package com.gt.game.core.entity.lantern;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 元宵点灯领奖地址
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_lantern_address")
public class LanternAddress extends Model<LanternAddress> {

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
     * 领奖地址
     */
	private String address;
    /**
     * 活动主键
     */
	private Integer actId;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getActId() {
		return actId;
	}

	public void setActId(Integer actId) {
		this.actId = actId;
	}
}
