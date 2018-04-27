/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service;

import com.letsun.frame.security.system.domain.Function;
import com.letsun.frame.core.service.BaseService;

/**
 *  
 * @Author YY 
 * @Date <2018年03月17日>
 * @version 1.0
 */
public interface FunctionService extends BaseService<Function>{
	
	/**
	 * 修改菜单状态  
	 * @param id
	 * @param status
	 * @return boolean:成功true,失败false
	 * @author YY<2018年4月12日>
	 */
	boolean updateStatus(Long id, Integer status);
	
}
