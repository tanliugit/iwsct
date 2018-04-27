package com.letsun.frame.security.common.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.letsun.frame.core.controller.BaseController;
import com.letsun.frame.security.system.domain.UserLogin;


/**
 * @Desc Controller基类
 * @author YY<2017年10月11日>
 */
@Controller
public class BaseAdminController extends BaseController{
	
	@ModelAttribute
	public void init(ModelMap mmp) {
		if(getLoginUser() != null && getLoginUser().getUser() != null){
			String parentId = getRequest().getParameter("parentId");
			if(StringUtils.isNotEmpty(parentId)){
				mmp.put("parentId", Long.parseLong(parentId));
			}
			String childId = getRequest().getParameter("childId");
			if(StringUtils.isNotEmpty(childId)){
				mmp.put("childId", Long.parseLong(childId));
			}
		}
	}
	/**
	 * @Desc 获取后台用户登录信息
	 * @return
	 * @author YY<2017年10月23日>
	 */
	public UserLogin getLoginUser() {
		return (UserLogin) SecurityUtils.getSubject().getSession().getAttribute("currentUserLogin");
	}
	
}
