package com.gt.game.core.entity.lantern;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.Version;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 举报
 * </p>
 *
 * @author zwq
 * @since 2018-01-04
 */
@Data
@Accessors(chain = true)
@TableName("t_wx_activity_lantern_report")
public class LanternReport extends Model<LanternReport> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 活动主键
     */
	private Integer actId;
    /**
     * 1，诈骗、反社会、谣言，2：色情、赌博、毒品，3：传销、邪教、非法集会，4：侵权、抄袭；5：恶意营销、侵犯隐私、诱导分享，6：虚假广告，7，其他原因',
     */
	private Integer style;
    /**
     * 被举报的次数
     */
	private Integer reportNum;


	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
