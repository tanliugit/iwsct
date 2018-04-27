/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service;

import com.letsun.frame.core.service.BaseService;
import com.letsun.frame.security.system.domain.Role;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface RoleService extends BaseService<Role>{
	
	/**
	 *  更新角色状态  
	 * @param id
	 * @param status
	 * @return boolean:成功true,失败false
	 * @author YY<2018年4月12日>
	 */
	boolean updateStatus(Long id, Integer status);
	
	/**
	 * 根据ID查询用户角色及父节点名称  
	 * @param id
	 * @return Role
	 * @author YY<2018年4月12日>
	 */
	Role findRoleById(Long id);
	
	/**
	 * 通过ID查询角色拥有的菜单和功能  
	 * @param roleId
	 * @return Role
	 * @author YY<2018年4月12日>
	 */
	Role findRoleMenusById(Long roleId);
	
	/**
	 * 根据角色ID删除角色，用户被分配的角色，角色被分配的菜单  
	 * @param id
	 * @return
	 * @author YY<2018年4月19日>
	 */
	boolean deleteRoleById(Long id);
	
}
