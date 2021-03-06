/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.mapper;

import com.letsun.frame.security.system.domain.RoleMenu;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import com.letsun.frame.core.mapper.BaseMapper;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
	
	/**
	 * 根据角色ID删除角色菜单  
	 * @param roleId
	 * @return
	 * @author YY<2018年4月12日>
	 */
	@Delete("delete from sys_role_menu where role_id = #{roleId}")
	int deleteByRoleId(@Param("roleId") Long roleId);
	
	/**
	 * 根据菜单ID删除角色菜单  
	 * @param roleId
	 * @return
	 * @author YY<2018年4月12日>
	 */
	@Delete("delete from sys_role_menu where menu_id = #{menuId}")
	int deleteByMenuId(@Param("menuId") Long menuId);

}
