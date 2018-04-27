/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.mapper;

import com.letsun.frame.security.system.domain.Role;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.letsun.frame.core.mapper.BaseMapper;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 更新用户状态(状态：0启用/1禁用)  
	 * @param id
	 * @param status
	 * @return
	 * @author YY<2018年4月12日>
	 */
	@Update("update sys_role set status =#{status} where id=#{id}")
	int updateStatus(@Param("id") Long id, @Param("status") Integer status);
	
	/**
	 * 根据ID查询用户角色及父节点名称  
	 * @param id
	 * @return
	 * @author YY<2018年4月12日>
	 */
	Role findRoleById(Long id);
	
	/**
	 * 通过ID查询角色拥有的菜单和功能  
	 * @param id
	 * @return
	 * @author YY<2018年4月12日>
	 */
	Role findRoleMenusById(Long id);

}
