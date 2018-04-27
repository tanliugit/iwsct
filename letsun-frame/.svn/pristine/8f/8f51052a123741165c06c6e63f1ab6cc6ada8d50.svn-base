/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsun.frame.core.service.impl.BaseServiceImpl;
import com.letsun.frame.security.system.domain.RoleMenu;
import com.letsun.frame.security.system.mapper.RoleMenuMapper;
import com.letsun.frame.security.system.service.RoleMenuService;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper,RoleMenu> implements RoleMenuService{
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean insertRoleMenus(List<RoleMenu> roleMenus) {
		//删除用户原来角色的菜单，做物理删除
		if(roleMenus != null && !roleMenus.isEmpty() && baseMapper.deleteByRoleId(roleMenus.get(0).getRoleId()) >= 0){
			//批量插入用户角色关联
			if(roleMenus.get(0).getMenuId() != -1L && !insertBatch(roleMenus)){
				throw new RuntimeException("保存用户角色信息失败");
			}
			return true;
		}
		return false;
	}
}
