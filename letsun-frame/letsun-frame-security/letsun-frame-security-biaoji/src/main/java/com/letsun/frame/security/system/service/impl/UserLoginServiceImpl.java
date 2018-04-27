/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service.impl;

import org.springframework.stereotype.Service;

import com.letsun.frame.security.system.domain.UserLogin;
import com.letsun.frame.security.system.mapper.UserLoginMapper;
import com.letsun.frame.security.system.service.UserLoginService;
import com.letsun.frame.core.service.impl.BaseServiceImpl;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
@Service
public class UserLoginServiceImpl extends BaseServiceImpl<UserLoginMapper,UserLogin> implements UserLoginService{

	@Override
	public UserLogin findUserByAccount(String account) {
		return baseMapper.findUserByAccount(account);
	}
	
	@Override
	public boolean updatePassword(Long id, String password) {
		return baseMapper.updatePassword(id,password) > 0;
	}
	
}
