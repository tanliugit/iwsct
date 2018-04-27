/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.letsun.frame.core.mapper.BaseMapper;
import com.letsun.frame.security.system.domain.UserRole;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
	
	/**
	 * 根据用户ID删除用户角色  
	 * @param userId
	 * @return
	 * @author YY<2018年4月12日>
	 */
	@Delete("delete from sys_user_role where user_id = #{userId}")
	int deleteByUserId(@Param("userId") Long userId);
	
	@Delete("delete from sys_user_role where role_id = #{roleId}")
	int deleteByRoleId(@Param("roleId")Long roleId);
}
