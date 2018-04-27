/*
 *Project: glorypty-framework
 *File: com.glorypty.framework.shiro.AuthorizationJhFilter.java <2015年8月4日>
 ****************************************************************
 * 版权所有@2015 国裕网络科技  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.common.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc 动态URL过滤
 * @author YY<2017年10月18日>
 */
public class ShiroFilter extends AuthorizationFilter {
	
	protected final Logger logger = LoggerFactory.getLogger(ShiroFilter.class);
	/**
	 * @Desc 动态URL过滤 
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @author YY<2017年10月18日>
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request,ServletResponse response, Object handler) throws Exception {
		//请求路径
		String permissionUrl = ((HttpServletRequest)request).getServletPath();
		if(StringUtils.isEmpty(permissionUrl)){
			return false;
		}
		//请求方法
		String permissionMethod = permissionUrl.substring(permissionUrl.lastIndexOf("/")==-1 ? 0 : permissionUrl.lastIndexOf("/")+1).split("\\.")[0];
		boolean isIgn = StringUtils.isNotEmpty(permissionMethod) && (permissionMethod.equalsIgnoreCase("index") || permissionMethod.startsWith("ign"));
		if(isIgn){
			return true;
		}
		// 权限校验
		if (this.getSubject(request, response).isPermitted(permissionUrl)) {
			return true;
		}
		logger.error("你没有访问权限："+permissionUrl);
		return false;
	}

}
