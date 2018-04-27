/*
 *Project: frame
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.letsun.frame.core.domain.OperationLog;
import com.letsun.frame.core.service.OperationLogService;
import com.letsun.frame.security.common.controller.BaseAdminController;

/**
 *  
 * @Author YY 
 * @Date <2018年03月19日>
 * @version 1.0
 */
@Controller
@RequestMapping("/system/operationLog/")
public class OperationLogController extends BaseAdminController {
	
	@Autowired private OperationLogService operationLogService;
	
	
	/**
	 * @Desc 首页
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping("index")
	public String index() {
		return "/system/operationLog/index";
	}	
	
	/**
	 * @Desc 列表
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping("list")
	public  Map<String, Object> search(OperationLog entity, Integer page,Integer limit) {
		Wrapper<OperationLog> wapper = new EntityWrapper<OperationLog>();
		if(entity.getBusinessType() !=null){
			wapper.eq("business_type", entity.getBusinessType());
		}
		if(entity.getBusinessStatus() !=null){
			wapper.eq("business_status", entity.getBusinessStatus());
		}
		wapper.like("request_ip", entity.getRequestIp());
		wapper.like("creator", entity.getCreator());
		return toLayuiList(operationLogService.selectPage(new Page<OperationLog>(page, limit),wapper));
	}
	
	/**
	 * @Desc 查看
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping("detail")
	public String detail(Long id, ModelMap mmap){
		mmap.put("obj", operationLogService.selectById(id));
		return "/system/operationLog/detail";
	}
}
