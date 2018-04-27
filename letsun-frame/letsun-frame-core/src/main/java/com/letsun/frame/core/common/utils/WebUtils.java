package com.letsun.frame.core.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc WEB工具类  
 * @author YY  
 * @date 2018年4月12日
 */
public class WebUtils {
	
	/**
	 * @Desc 是否ajax请求 
	 * @param request
	 * @return
	 * @author YY<2017年10月17日>
	 */
	public static boolean isAjax(HttpServletRequest request){
		return request.getHeader("accept").indexOf("application/json") > -1 || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1);
	}
	
	public static String getHostUrl(HttpServletRequest request){
		StringBuffer buffer = new StringBuffer(request.getScheme()+"://"+ request.getServerName());
		if(request.getServerPort() != 80){
			buffer.append(":"+request.getServerPort());
		}
		return buffer.toString();
	}
}
