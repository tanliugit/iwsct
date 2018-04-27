/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.mapper;

import com.letsun.frame.security.system.domain.User;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.letsun.frame.core.mapper.BaseMapper;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface UserMapper extends BaseMapper<User> {
	
	/**
	 * 更新用户状态(状态:0启用1锁定/冻结)  
	 * @param id
	 * @param status
	 * @return
	 * @author YY<2018年4月12日>
	 */
	@Update("update sys_user set status =#{status} where id=#{id}")
	int updateStatus(@Param("id") Long id, @Param("status") Integer status);
	
	/**
	 * 通过ID获取用户信息会关联查询用户拥有的角色  
	 * @param id
	 * @return
	 * @author YY<2018年4月12日>
	 */
	User findUserRoleById(Long id);
	
	/**
	 * 通过ID获取用户信息会关联查询用户账号  
	 * @param id
	 * @return
	 * @author YY<2018年4月12日>
	 */
	User findUserById(Long id);

}
