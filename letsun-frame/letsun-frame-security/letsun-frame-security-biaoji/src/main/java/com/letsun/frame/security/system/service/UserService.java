/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service;

import com.letsun.frame.core.service.BaseService;
import com.letsun.frame.security.system.domain.User;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface UserService extends BaseService<User>{
	
	/**
	 * 更新用户状态  
	 * @param id
	 * @param status
	 * @return 成功true,失败false
	 * @author YY<2018年4月12日>
	 */
	boolean updateStatus(Long id, Integer status);
	
	/**
	 * 插入用户信息和用户登录信息  
	 * @param entity
	 * @return 成功true,失败false
	 * @author YY<2018年4月12日>
	 */
	boolean insertUser(User entity);
	
	/**
	 * 根据用户ID查询用户，会关联查询出角色信息  
	 * @param id
	 * @return User
	 * @author YY<2018年4月12日>
	 */
	User findUserRoleById(Long id);
	
	/**
	 * 通过ID获取用户信息会关联查询用户账号  
	 * @param id
	 * @return 成功true,失败false
	 * @author YY<2018年4月12日>
	 */
	User findUserById(Long id);
	
	/**
	 * 更新用户信息和用户登录信息  
	 * @param entity
	 * @return
	 * @author YY<2018年4月12日>
	 */
	boolean updateUser(User entity);
	
	/**
	 * 根据用户ID删除用户和用户角色  
	 * @param id
	 * @return
	 * @author YY<2018年4月19日>
	 */
	boolean deleteByUserId(Long id);
	
}
