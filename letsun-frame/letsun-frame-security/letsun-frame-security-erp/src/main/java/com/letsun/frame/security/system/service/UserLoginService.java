/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service;

import com.letsun.frame.core.service.BaseService;
import com.letsun.frame.security.system.domain.UserLogin;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface UserLoginService extends BaseService<UserLogin>{
	
	/**
	 * 通过账号查询用户登录信息  
	 * @param account
	 * @return
	 * @author YY<2018年4月12日>
	 */
	UserLogin findUserByAccount(String account);
	
	/**
	 * 修改密码
	 * @param id
	 * @param md5Hex
	 * @return
	 * @author YY<2018年4月12日>
	 */
	boolean updatePassword(Long id, String md5Hex);
}
