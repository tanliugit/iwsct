/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service.impl;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.letsun.frame.security.system.domain.User;
import com.letsun.frame.security.system.domain.UserLogin;
import com.letsun.frame.security.system.mapper.UserLoginMapper;
import com.letsun.frame.security.system.mapper.UserMapper;
import com.letsun.frame.security.system.mapper.UserRoleMapper;
import com.letsun.frame.security.system.service.UserService;
import com.letsun.frame.core.service.impl.BaseServiceImpl;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper,User> implements UserService{
	
	@Autowired private UserLoginMapper userLoginMapper;
	
	@Autowired private UserRoleMapper userRoleMapper;
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean updateStatus(Long id, Integer status) {
		return baseMapper.updateStatus(id,status) > 0;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean insertUser(User user) {
		if(baseMapper.insert(user) > 0){
			UserLogin userLogin = new UserLogin();
			userLogin.setUserId(user.getId());
			userLogin.setOriginCode("PLATFORM");
			userLogin.setAccount(user.getAccount());
			userLogin.setPassword(DigestUtils.md5Hex(user.getPassword()));
			userLogin.setCreator(user.getCreator());
			userLogin.setCreateDate(new Date());
			if(userLoginMapper.insert(userLogin) <= 0){
				throw new RuntimeException("保存用户失败");
			}
			return true;
		}
		return false;
	}

	@Override
	public User findUserRoleById(Long id) {
		return baseMapper.findUserRoleById(id);
	}
	
	@Override
	public User findUserById(Long id) {
		return baseMapper.findUserById(id);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean updateUser(User user) {
		if(baseMapper.updateById(user) > 0 ){
			UserLogin userLogin = userLoginMapper.findUserByAccount(user.getAccount());
			if(!userLogin.getPassword().equals(user.getPassword())){
				userLogin.setPassword(DigestUtils.md5Hex(user.getPassword()));
			}
			userLogin.setModifier(user.getCreator());
			userLogin.setModifyDate(new Date());
			if(userLoginMapper.updateById(userLogin) <= 0){
				throw new RuntimeException("保存用户失败");
			}
			return true;
		}
		return false;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean deleteByUserId(Long id) {
		if(deleteById(id)){
			//删除用户被分配的角色
			userRoleMapper.deleteByUserId(id);
			return true;
		}
		return false;
	}
}
