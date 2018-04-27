/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service;

import java.util.List;

import com.letsun.frame.core.service.BaseService;
import com.letsun.frame.security.system.domain.UserRole;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface UserRoleService extends BaseService<UserRole>{
	
	/**
	 * 批量插入用户角色  
	 * @param userRoles
	 * @return boolean:成功true,失败false
	 * @author YY<2018年4月12日>
	 */
	boolean insertUserRoles(List<UserRole> userRoles);
	
}
