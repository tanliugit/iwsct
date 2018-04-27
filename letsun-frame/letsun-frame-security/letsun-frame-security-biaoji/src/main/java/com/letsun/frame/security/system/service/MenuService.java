/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.service;

import java.util.List;

import com.letsun.frame.core.service.BaseService;
import com.letsun.frame.security.system.domain.Menu;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
public interface MenuService extends BaseService<Menu>{
	
	/**
	 * 获取当前用户拥有的根菜单  
	 * @param allMenus
	 * @return
	 * @author YY<2018年4月12日>
	 */
	public List<Menu> getRootMenuList(List<Menu> allMenus);
	
	/**
	 * 获取子菜单  
	 * @param menus
	 * @param parentId
	 * @return
	 * @author YY<2018年4月12日>
	 */
	public List<Menu> getchildMenus(List<Menu> menus, Long parentId);
	
	/**
	 * 修改菜单状态  
	 * @param id
	 * @param status
	 * @return
	 * @author YY<2018年4月12日>
	 */
	public boolean updateStatus(Long id, Integer status);
	
	/**
	 * 增加菜单，默认增加【增/删/列表/授权/状态/导出/导入功能】功能  
	 * @param entity
	 * @return
	 * @author YY<2018年4月12日>
	 */
	public boolean insertMenu(Menu entity);
	
	/**
	 * 通过ID查询菜单及父节点名称  
	 * @param id
	 * @return
	 * @author YY<2018年4月12日>
	 */
	public Menu findMenuById(Long id);
	
	/**
	 * 保存菜单功能  
	 * @param menuFunctions
	 * @return
	 * @author YY<2018年4月12日>
	 */
	public boolean insertMenuFunctions(List<Menu> menuFunctions);
	
	/**
	 * 根据ID递归删除菜单及菜单下面的子菜单  
	 * @param id
	 * @return
	 * @author YY<2018年4月12日>
	 */
	public boolean deleteMenusById(Long id);
}
