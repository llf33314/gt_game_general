package com.gt.game.core.util;


import com.gt.api.bean.session.BusUser;
import com.gt.api.bean.session.WxPublicUsers;
import com.gt.api.util.SessionUtils;
import com.gt.axis.bean.wxmp.bus.BusUserApiReq;
import com.gt.axis.bean.wxmp.fenbiflow.FenbiFlowRecord;
import com.gt.axis.content.AxisResult;
import com.gt.axis.server.wxmp.BusServer;
import com.gt.axis.server.wxmp.WxPublicServer;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 获取登录用户信息
 * 
 * @author Administrator
 * 
 */
public class CommonUtil {
	private static final Logger log = Logger.getLogger(CommonUtil.class);

	/**
	 * 获取session中的登录用户
	 *
	 * @param request
	 * @return
	 */
	public static BusUser getLoginUser(HttpServletRequest request) {
		try {
			// 从共享的session中获取，部署
//			return SessionUtils.getLoginUser(request);
			// 用于测试
			BusUserApiReq busUserApiReq = new BusUserApiReq();
			busUserApiReq.setUserId(33);
			return BusServer.getBusUserApi(busUserApiReq).getData();
//			BusUser busUser = new BusUser();
//			busUser.setId(33);
//			busUser.setLevel(4);
//			return busUser;
		} catch (Exception e) {
			log.info(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	};

	/**
	 * 获取session中的登录用户
	 *
	 * @param request
	 * @return
	 */
	public static WxPublicUsers getLoginPbUser(HttpServletRequest request) {
		try {
			// 部署打开注释
//			return SessionUtils.getLoginPbUser(request);
//			AxisResult<WxPublicUsers> wxPublicUsersAxisResult = WxPublicServer.selectByBusId(apiWxPublicUsers.getBusUserId());
			AxisResult<WxPublicUsers> wxPublicUsersAxisResult = WxPublicServer.selectByBusId(33);
			return wxPublicUsersAxisResult.getData();
		} catch (Exception e) {
			log.info(e.getLocalizedMessage());
			e.printStackTrace();
		}
		return null;
	};

	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		boolean b = false;
		try {
			if (obj == null || "".equals(obj)) {
				b = true;
			} else {
				b = false;
			}
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 判断对象是否不为空
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		boolean b = false;
		try {
			if (obj == null || "".equals(obj)) {
				b = false;
			} else {
				b = true;
			}
		} catch (Exception e) {
			b = false;
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * 转Integer
	 *
	 * @param obj
	 */
	public static Integer toInteger(Object obj) {
		try {
			if (!isEmpty(obj)) {
				return Integer.parseInt(obj.toString());
			} else {
				throw new Exception("对象为空，转换失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 转String
	 *
	 * @param obj
	 */
	public static String toString(Object obj) {
		try {
			if (!isEmpty(obj)) {
				return obj.toString();
			} else {
				throw new Exception("对象为空，转换失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 转string
	 * @param obj
	 * @return
	 */
	public static String Blob2String(Object obj) {
		String string = null;
		try {
			if (obj == null || obj.equals("")) {
				return "";
			}
			byte[] bytes = (byte[]) obj;
			string = new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return string;
	}

	/**
	 * 转Double
	 *
	 * @param obj
	 */
	public static Double toDouble(Object obj) {
		try {
			if (!isEmpty(obj)) {
				return Double.parseDouble(obj.toString());
			} else {
				throw new Exception("对象为空，转换失败！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断列表是否为空
	 *
	 * @param list
	 * @return
	 */
	public static final boolean isEmpty(List<?> list) {
		return list == null || list.size() == 0;
	}

	/**
	 * 判断列表是否不为空
	 *
	 * @param list
	 * @return
	 */
	public static final boolean isNotEmpty(List<?> list) {
		return list != null && list.size() > 0;
	}

	/**
	 * 获取字符串长度  中文算2个字符
	 * @param str
	 * @return
	 */
	public static double getStringLength(String str) {
		if(CommonUtil.isEmpty(str)){
			return 0;
		}
		double valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";       // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
		for (int i = 0; i < str.length(); i++) {         // 获取一个字符
			String temp = str.substring(i, i + 1);         // 判断是否为中文字符
			if (temp.matches(chinese)) {           // 中文字符长度为1
				valueLength += 1;
			} else {           // 其他字符长度为0.5
				valueLength += 0.5;
			}
		}       //进位取整
		return Math.ceil(valueLength);
	}

	/**
	 * 判断浏览器
	 * @return 1:微信浏览器,99:其他浏览器
	 */
	public static Integer judgeBrowser(HttpServletRequest request){
		String ua = request.getHeader("user-agent")
				.toLowerCase();
		if (ua.indexOf("micromessenger") > 0) {// 微信-1
			return 1;
		}else{//其他 -99
			return 99;
		}
	}


	/**
	 * 构建冻结信息
	 *
	 *
	 * @param busId
	 *            用户ID
	 * @param recCount
	 *            总数或总数量
	 * @param fkId
	 *            外键
	 * @param freeType
	 *            冻结类型
	 * @param recType
	 *            1-粉币，2-流量
	 * @param recDesc
	 *            描述
	 * @param flowType
	 *            如果是流量则传，否则传入0
	 * @return
	 */
	public static FenbiFlowRecord bulidFenFlow(Integer busId, Double recCount, Integer fkId, Integer freeType, Integer recType,
										String recDesc, Integer flowType) {
		FenbiFlowRecord fb = new FenbiFlowRecord();
		fb.setBusUserId(busId);
		fb.setRecCount(recCount);
		fb.setRecFkId(fkId);
		fb.setRecFreezeType(freeType);
		fb.setRecType(recType);
		fb.setRecDesc(recDesc);
		fb.setFlowType(1);
		fb.setFlowId(1);
		fb.setRecUseCount(0.0);
		fb.setRollStatus(1);
		return fb;
	}


}
