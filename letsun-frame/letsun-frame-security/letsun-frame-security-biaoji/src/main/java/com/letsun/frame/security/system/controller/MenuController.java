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
import com.letsun.frame.core.common.lang.annotation.OperationLogs;
import com.letsun.frame.core.common.lang.enums.EnumType.BusinessType;
import com.letsun.frame.core.domain.Message;
import com.letsun.frame.core.domain.Ztree;
import com.letsun.frame.security.common.controller.BaseAdminController;
import com.letsun.frame.security.system.domain.Function;
import com.letsun.frame.security.system.domain.Menu;
import com.letsun.frame.security.system.service.FunctionService;
import com.letsun.frame.security.system.service.MenuService;

/**
 *  
 * @Author YY 
 * @Date <2018年03月16日>
 * @version 1.0
 */
@Controller
@RequestMapping("/system/menu/")
public class MenuController extends BaseAdminController {
	
	@Autowired private MenuService menuService;
	@Autowired private FunctionService functionService;
	
	
	/**
	 * @Desc 首页
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping("index")
	public String index() {
		return "/system/menu/index";
	}	
	
	/**
	 * @Desc 列表
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping("list")
	public  Map<String, Object> search(Menu entity, Integer page,Integer limit) {
		Map<String, Object> map = new HashMap<String, Object>(8);
		map.put("name", entity.getName());
		map.put("status", entity.getStatus());
		map.put("source", entity.getSource());
		return toLayuiList(menuService.selectComplex(map, page, limit));
	}
	
	/**
	 * @Desc 新增 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping(value="add",method = RequestMethod.GET)
	public String add(ModelMap mmap) {
		EntityWrapper<Menu> wrapper = new EntityWrapper<Menu>();
		//不查询功能
		wrapper.ne("type",2);
		wrapper.eq("deleted",0);
		List<Menu> menus = menuService.selectList(wrapper);
		List<Ztree> ztrees = new ArrayList<Ztree>();
		for (Menu menu : menus) {
			ztrees.add(initZtree(menu));
		}
		mmap.addAttribute("ztrees", ztrees);
		return "/system/menu/add";
	}	
	

	/**
	 * 将菜单转Ztree需要的结构
	 * @param userId
	 * @param role
	 * @return
	 */
	private Ztree initZtree(Menu menu) {
		Ztree ztree = new Ztree();
		ztree.setId(menu.getId());
		ztree.setpId(menu.getParentId());
		ztree.setName(menu.getName());
		ztree.setOpen(true);
		return ztree;
	}
	
	/**
	 * @Desc 新增保存 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping(value="add",method = RequestMethod.POST)
	@OperationLogs(businessType=BusinessType.INSERT,businessName="新增")
	public Message addSave(Menu entity){
		Menu parentMenu = menuService.selectById(entity.getParentId());
		if(parentMenu.getType() == 1){
			 Message.error("功能不能增加子节点");
		}
		entity.setLevel(parentMenu.getLevel()+1);
		entity.setCreator(getLoginUser().getAccount());
		entity.setCreateDate(new Date());
		return menuService.insertMenu(entity) ? Message.success("操作成功") : Message.error("操作失败");		
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
		mmap.put("obj", menuService.findMenuById(id));
		EntityWrapper<Menu> wrapper = new EntityWrapper<Menu>();
		//不查询功能
		wrapper.ne("type",2);
		wrapper.eq("deleted",0);
		List<Menu> menus = menuService.selectList(wrapper);
		List<Ztree> ztrees = new ArrayList<Ztree>();
		for (Menu menu : menus) {
			ztrees.add(initZtree(menu));
		}
		mmap.addAttribute("ztrees", ztrees);
		return "/system/menu/edit";
	}
	
	/**
	 * @Desc 编辑保存 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping(value="edit",method = RequestMethod.POST)
	@OperationLogs(businessType=BusinessType.UPDATE,businessName="更新")
	public Message editSave(Menu entity, ModelMap mmap){
		return menuService.updateById(entity) ? Message.success("操作成功") : Message.error("操作失败");	
	}	
	
	/**
	 * @Desc 查看
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping("detail")
	public String detail(Long id, ModelMap mmap){
		mmap.put("obj", menuService.findMenuById(id));
		return "/system/menu/detail";
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
		return menuService.deleteMenusById(id) ? Message.success("操作成功") : Message.error("操作失败");		
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
		return menuService.updateStatus(id,status) ? Message.success("操作成功") : Message.error("操作失败");		
	}
	
	/**
	 * @Desc 菜单授权功能
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping(value="grant",method = RequestMethod.GET)
	public String grant(Long id,ModelMap mmap) {
		//获取所有正常状态的功能
		EntityWrapper<Function> wrapper = new EntityWrapper<Function>();
		wrapper.eq("status",0);
		List<Function> functions = functionService.selectList(wrapper);
		
		//获取当前菜单拥有的功能
		EntityWrapper<Menu> menuWrapper = new EntityWrapper<Menu>();
		menuWrapper.eq("parent_id",id);
		menuWrapper.eq("type",2);
		menuWrapper.eq("status",0);
		List<Menu> menuFunctions = menuService.selectList(menuWrapper);
		mmap.addAttribute("menuId", id);
		mmap.addAttribute("ztrees", initZtree(functions, menuFunctions));
		return "/system/menu/grant";
	}
	
	/**
	 * 初始化功能树
	 * @param function
	 * @param menuFunctions
	 * @return
	 */
	private List<Ztree> initZtree(List<Function> functions, List<Menu> menuFunctions) {
		List<Ztree> ztrees = new ArrayList<Ztree>();
		Ztree ztree0 = new Ztree();
		ztree0.setId(0L);
		ztree0.setpId(-1L);
		ztree0.setName("功能列表");
		ztree0.setOpen(true);
		ztrees.add(ztree0);
		
		for (Function function : functions) {
			Ztree ztree = new Ztree();
			ztree.setId(function.getId());
			ztree.setpId(0L);
			ztree.setName(function.getName());
			ztree.setOpen(true);
			for (Menu menuFunction : menuFunctions) {
				if(function.getId().equals(menuFunction.getFunctionId())){
					ztree.setChecked(true);
				}
			}
			ztrees.add(ztree);
		}
		return ztrees;
	}
	
	/**
	 * @Desc 授权保存 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping(value="grant",method = RequestMethod.POST)
	@OperationLogs(businessType=BusinessType.INSERT,businessName="授权")
	public Message grantSave(@RequestBody List<Menu> menuFunctions){
		for (Menu menu : menuFunctions) {
			menu.setCreator(getLoginUser().getAccount());
			menu.setCreateDate(new Date());
		}
		return menuService.insertMenuFunctions(menuFunctions) ? Message.success("操作成功") : Message.error("操作失败");
	}
}
