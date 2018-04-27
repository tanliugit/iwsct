/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.letsun.frame.core.common.lang.annotation.OperationLogs;
import com.letsun.frame.core.common.lang.enums.EnumType.BusinessType;
import com.letsun.frame.core.domain.Message;
import com.letsun.frame.core.domain.Ztree;
import com.letsun.frame.security.common.controller.BaseAdminController;
import com.letsun.frame.security.system.domain.Menu;
import com.letsun.frame.security.system.domain.Role;
import com.letsun.frame.security.system.domain.RoleMenu;
import com.letsun.frame.security.system.service.MenuService;
import com.letsun.frame.security.system.service.RoleService;
import com.letsun.frame.security.system.service.RoleMenuService;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
@Controller
@RequestMapping("/system/role/")
public class RoleController extends BaseAdminController {
	
	@Autowired private RoleService roleService;
	
	@Autowired private RoleMenuService roleMenuService;
	
	@Autowired private MenuService menuService;
	
	
	/**
	 * @Desc 首页
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping("index")
	public String index() {
		return "/system/role/index";
	}	
	
	/**
	 * @Desc 列表
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping("list")
	public  Map<String, Object> search(Role entity, Integer page,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>(8);
		map.put("name", entity.getName());
		map.put("status", entity.getStatus());
		return toLayuiList(roleService.selectComplex(map, page, limit));
	}
	
	/**
	 * @Desc 新增 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping(value="add",method = RequestMethod.GET)
	public String add( ModelMap mmap) {
		Wrapper<Role> wrapper = new EntityWrapper<Role>();
		wrapper.eq("deleted",0);
		mmap.put("roleList",roleService.selectList(wrapper));
		return "/system/role/add";
	}	
	
	/**
	 * @Desc 新增保存 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping(value="add",method = RequestMethod.POST)
	@OperationLogs(businessType=BusinessType.INSERT,businessName="新增")
	public Message addSave(Role entity){
		entity.setCreator(getLoginUser().getAccount());
		entity.setCreateDate(new Date());
		return roleService.insert(entity) ? Message.success("操作成功") : Message.error("操作失败");		
	}
	
	/**
	 * @Desc 编辑 
	 * @param id
	 * @param mmap
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping(value="edit",method = RequestMethod.GET)
	public String edit(Long id, ModelMap mmap){
		mmap.put("obj", roleService.selectById(id));
		Wrapper<Role> wrapper = new EntityWrapper<Role>();
		//编辑不能选择自己为父级
		wrapper.ne("id",id);
		wrapper.eq("deleted",0);
		mmap.put("roleList",roleService.selectList(wrapper));
		return "/system/role/edit";
	}
	
	/**
	 * @Desc 编辑保存 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping(value="edit",method = RequestMethod.POST)
	@OperationLogs(businessType=BusinessType.UPDATE,businessName="更新")
	public Message editSave(Role entity, ModelMap mmap){
		entity.setModifier(getLoginUser().getAccount());
		entity.setModifyDate(new Date());
		return roleService.updateById(entity) ? Message.success("操作成功") : Message.error("操作失败");	
	}	
	
	/**
	 * @Desc 查看
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping("detail")
	public String detail(Long id, ModelMap mmap){
		mmap.put("obj", roleService.findRoleById(id));
		return "/system/role/detail";
	}
	 
	/**
	 * @Desc 删除 
	 * @param id
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping("delete")
	@OperationLogs(businessType=BusinessType.DELETE,businessName="删除")
	public Message delete(Long id){
		return roleService.deleteRoleById(id) ? Message.success("操作成功") : Message.error("操作失败");		
	}
	
	/**
	 * @Desc 修改状态
	 * @param id
	 * @return
	 * @author YY<2017年10月17日>
	 */
	@ResponseBody
	@RequestMapping("status")
	@OperationLogs(businessType=BusinessType.UPDATE,businessName="修改状态")
	public Message status(Long id,Integer status){
		return roleService.updateStatus(id,status) ? Message.success("操作成功") : Message.error("操作失败");		
	}
	/**
	 * @Desc 角色授权菜单
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping(value="grant",method = RequestMethod.GET)
	public String grant(Long id,ModelMap mmap) {
		EntityWrapper<Menu> wrapper = new EntityWrapper<Menu>();
		wrapper.eq("status",0);
		wrapper.eq("deleted",0);
		List<Menu> menus = menuService.selectList(wrapper);
		//获取角色拥有的菜单，回显
		Role role = roleService.findRoleMenusById(id);
		List<Ztree> ztrees = new ArrayList<Ztree>();
		for (Menu menu : menus) {
			ztrees.add(initZtree(menu,role));
		}
		mmap.addAttribute("roleId", id);
		mmap.addAttribute("ztrees", ztrees);
		return "/system/role/grant";
	}
	

	/**
	 * 将菜单转Ztree需要的结构
	 * @param userId
	 * @param role
	 * @return
	 */
	private Ztree initZtree(Menu menu,Role role) {
		Ztree ztree = new Ztree();
		ztree.setId(menu.getId());
		ztree.setpId(menu.getParentId());
		ztree.setName(menu.getName());
		if(menu.getLevel() <=1 ){
			ztree.setOpen(true);
		}else{
			ztree.setOpen(false);
		}
		if (role != null && role.getMenus() != null) {
			for (Menu myMenu : role.getMenus()) {
				if (menu.getId().equals(myMenu.getId())) {
					ztree.setChecked(true);
					break;
				}
			}
		}
		return ztree;
	}
	
	/**
	 * @Desc 角色授权菜单保存 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping(value="grant",method = RequestMethod.POST)
	@OperationLogs(businessType=BusinessType.INSERT,businessName="授权")
	public Message grantSave(@RequestBody List<RoleMenu> roleMenus){
		for (RoleMenu roleMenu : roleMenus) {
			roleMenu.setCreator(getLoginUser().getAccount());
			roleMenu.setCreateDate(new Date());
		}
		return roleMenuService.insertRoleMenus(roleMenus) ? Message.success("操作成功") : Message.error("操作失败");
	}

}
