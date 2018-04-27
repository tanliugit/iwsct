/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letsun.frame.core.domain.Message;
import com.letsun.frame.core.common.lang.annotation.OperationLogs;
import com.letsun.frame.core.common.lang.enums.EnumType.BusinessType;
import com.letsun.frame.security.common.controller.BaseAdminController;

import com.letsun.iwsct.itface.domain.TlotteryActivity;
import com.letsun.iwsct.itface.service.TlotteryActivityService;

/**
 *  
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
@Controller
@RequestMapping("/itface/tlotteryActivity/")
public class TlotteryActivityController extends BaseAdminController {
	
	@Autowired private TlotteryActivityService tlotteryActivityService;
	
	
	/**
	 * @Desc 首页
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping("index")
	public String index() {
		return "/itface/tlotteryActivity/index";
	}	
	
	/**
	 * @Desc 列表
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping("list")
	public  Map<String, Object> search(TlotteryActivity entity, Integer page,Integer limit) {
		return toLayuiList(tlotteryActivityService.selectPage(entity, page, limit));
	}
	
	/**
	 * @Desc 新增 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping(value="add",method = RequestMethod.GET)
	public String add() {
		return "/itface/tlotteryActivity/add";
	}	
	
	/**
	 * @Desc 新增保存 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping(value="add",method = RequestMethod.POST)
	@OperationLogs(businessType=BusinessType.INSERT,businessName="新增")
	public Message addSave(TlotteryActivity entity){
		entity.setCreator(getLoginUser().getAccount());
		entity.setCreateDate(new Date());
		return tlotteryActivityService.insert(entity) ? Message.success("操作成功") : Message.error("操作失败");		
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
		mmap.put("obj", tlotteryActivityService.selectById(id));
		return "/itface/tlotteryActivity/edit";
	}
	
	/**
	 * @Desc 编辑保存 
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping(value="edit",method = RequestMethod.POST)
	@OperationLogs(businessType=BusinessType.UPDATE,businessName="更新")
	public Message editSave(TlotteryActivity entity, ModelMap mmap){
		entity.setModifier(getLoginUser().getAccount());
		entity.setModifyDate(new Date());
		return tlotteryActivityService.updateById(entity) ? Message.success("操作成功") : Message.error("操作失败");	
	}	
	
	/**
	 * @Desc 查看
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping("detail")
	public String detail(Long id, ModelMap mmap){
		mmap.put("obj", tlotteryActivityService.selectById(id));
		return "/itface/tlotteryActivity/detail";
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
		return tlotteryActivityService.deleteById(id) ? Message.success("操作成功") : Message.error("操作失败");		
	}
	
}
