/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsun.frame.security.system.domain.Role;
import com.letsun.frame.security.system.mapper.RoleMapper;
import com.letsun.frame.security.system.mapper.RoleMenuMapper;
import com.letsun.frame.security.system.mapper.UserRoleMapper;
import com.letsun.frame.security.system.service.RoleService;
import com.letsun.frame.core.service.impl.BaseServiceImpl;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper,Role> implements RoleService{
	
	@Autowired private UserRoleMapper userRoleMapper;
	
	@Autowired private RoleMenuMapper roleMenuMapper;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean updateStatus(Long id, Integer status) {
		return baseMapper.updateStatus(id,status) > 0;
	}

	@Override
	public Role findRoleById(Long id) {
		return baseMapper.findRoleById(id);
	}

	@Override
	public Role findRoleMenusById(Long id) {
		return baseMapper.findRoleMenusById(id);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean deleteRoleById(Long id) {
		if(deleteById(id)){
			//删除用户被分配的角色
			userRoleMapper.deleteByRoleId(id);
			//删除角色被分配的菜单
			roleMenuMapper.deleteByRoleId(id);
			return true;
		}
		return false;
	}
	
}
