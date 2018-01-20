package com.gt.game.core.bean.common.res;

import com.gt.game.common.base.PageReq;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * <p>
 * 粉丝
 * </p>
 *
 * @author zwq
 * @since 2017-12-25
 */
@ApiModel("粉丝分页查询返回对象")
public class MemberListPageRes{

	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("openid")
	private String openid;

	@ApiModelProperty("昵称")
	private String nickname;

	@ApiModelProperty("性别 （0未知 1男 2女）")
	private Integer sex;

	@ApiModelProperty("所在城市")
	private String city;

	@ApiModelProperty("头像")
	private String headimgurl;

	@ApiModelProperty("关注时间")
	private Date jointime;

	@ApiModelProperty("组别")
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public Date getJointime() {
		return jointime;
	}

	public void setJointime(Date jointime) {
		this.jointime = jointime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
