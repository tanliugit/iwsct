/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.mapper;

import com.letsun.frame.security.system.domain.Menu;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.letsun.frame.core.mapper.BaseMapper;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface MenuMapper extends BaseMapper<Menu> {

	 /**
	  * 更新用户状态(状态：0启用/1禁用)  
	  * @param id
	  * @param status
	  * @return
	  * @author YY<2018年4月12日>
	  */
	@Update("update sys_menu set status =#{status} where id=#{id}")
	int updateStatus(@Param("id") Long id, @Param("status") Integer status);
	
	/**
	 * 通过ID查询菜单及父节点名称  
	 * @param id
	 * @return
	 * @author YY<2018年4月12日>
	 */
	Menu findMenuById(Long id);
	
	/**
	 * 根据父ID和功能类型删除所有子节点  
	 * @param parentId
	 * @param type
	 * @author YY<2018年4月12日>
	 */
	@Delete("delete from sys_menu where parent_id =#{parentId} and  type =#{type}")
	void deleteByParentId(@Param("parentId")Long parentId,@Param("type")Integer type);

}
