/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service.impl;

import org.springframework.stereotype.Service;

import com.letsun.frame.core.service.impl.BaseServiceImpl;
import com.letsun.frame.security.system.domain.Function;
import com.letsun.frame.security.system.mapper.FunctionMapper;
import com.letsun.frame.security.system.service.FunctionService;

/**
 *  
 * @Author YY 
 * @Date <2018年03月17日>
 * @version 1.0
 */
@Service
public class FunctionServiceImpl extends BaseServiceImpl<FunctionMapper,Function> implements FunctionService{
	
	@Override
	public boolean updateStatus(Long id, Integer status) {
		return baseMapper.updateStatus(id,status) > 0;
	}
}
