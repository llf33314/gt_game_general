package com.gt.game.core.dao.common;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.core.bean.common.res.MemberListPageRes;

import java.util.List;
import java.util.Map;

public interface FansInfoMapper {

	/**
	 * 查询所有的粉丝
	 *
	 * @param page
	 * @param map
	 * @return
	 */
	List<MemberListPageRes> getMemberList(Page<MemberListPageRes> page, Map<String, Object> map);
}
