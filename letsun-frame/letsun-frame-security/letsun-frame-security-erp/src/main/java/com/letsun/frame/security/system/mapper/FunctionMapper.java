/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.mapper;

import com.letsun.frame.security.system.domain.Function;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.letsun.frame.core.mapper.BaseMapper;

/**
 * 
 * @Author YY
 * @Date <2018年03月17日>
 * @version 1.0
 */
public interface FunctionMapper extends BaseMapper<Function> {

	/**
	 * 更新用户状态(状态：0启用/1禁用)  
	 * @param id
	 * @param status
	 * @return
	 * @author YY<2018年4月12日>
	 */
	@Update("update sys_function set status =#{status} where id=#{id}")
	int updateStatus(@Param("id") Long id, @Param("status") Integer status);
}
