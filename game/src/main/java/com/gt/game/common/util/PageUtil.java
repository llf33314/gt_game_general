package com.gt.game.common.util;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.game.common.dto.PageDTO;
import org.apache.log4j.Logger;

/**
 * 设置分页信息工具类
 * Created by Administrator on 2017/11/7 0007.
 */
public class PageUtil {

	private static final Logger log = Logger.getLogger(PageUtil.class);

	public static PageDTO setPage(Page page){
		PageDTO pageDTO = null;
		try{
			if(page == null){
				log.info("设置分页page参数为空");
				pageDTO = new PageDTO(0, 0);
			}else {
				pageDTO = new PageDTO(page.getPages(), page.getTotal());
			}
		}catch (Exception e){
			log.info("设置分页异常", e);
			pageDTO = new PageDTO(0, 0);
		}
		return pageDTO;
	}
}
