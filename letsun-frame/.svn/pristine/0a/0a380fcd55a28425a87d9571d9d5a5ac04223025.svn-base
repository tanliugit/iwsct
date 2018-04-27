/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.mapper;

import com.letsun.frame.security.system.domain.UserLogin;

import org.apache.ibatis.annotations.Param;

import com.letsun.frame.core.mapper.BaseMapper;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface UserLoginMapper extends BaseMapper<UserLogin> {
	
	/**
	 * 通过账号查询用户信息  
	 * @param account
	 * @return
	 * @author YY<2018年4月12日>
	 */
	UserLogin findUserByAccount(String account);
	
	/**
	 * 更新用户密码  
	 * @param id
	 * @param password
	 * @return
	 * @author YY<2018年4月12日>
	 */
	int updatePassword(@Param("id") Long id, @Param("password") String password);
}
