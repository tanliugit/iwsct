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
import com.letsun.frame.security.system.domain.UserRole;
import com.letsun.frame.security.system.mapper.UserRoleMapper;
import com.letsun.frame.security.system.service.UserRoleService;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRoleMapper,UserRole> implements UserRoleService{

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean insertUserRoles(List<UserRole> userRoles) {
		//删除用户原来的角色
		if(userRoles != null && !userRoles.isEmpty() && baseMapper.deleteByUserId(userRoles.get(0).getUserId()) >= 0){
			//批量插入用户角色关联
			if(userRoles.get(0).getRoleId() != -1L && !insertBatch(userRoles)){
				throw new RuntimeException("保存用户角色信息失败");
			}
			return true;
		}
		return false;
	}
	
}
