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

import com.letsun.frame.core.domain.ErrorLog;
import com.letsun.frame.core.service.ErrorLogService;
import com.letsun.frame.security.common.controller.BaseAdminController;

/**
 *  
 * @Author YY 
 * @Date <2018年03月19日>
 * @version 1.0
 */
@Controller
@RequestMapping("/system/errorLog/")
public class ErrorLogController extends BaseAdminController {
	
	@Autowired private ErrorLogService errorLogService;
	
	
	/**
	 * @Desc 首页
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping("index")
	public String index() {
		return "/system/errorLog/index";
	}	
	
	/**
	 * @Desc 列表
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@ResponseBody
	@RequestMapping("list")
	public  Map<String, Object> search(ErrorLog entity, Integer page,Integer limit) {
		return toLayuiList(errorLogService.selectPage(entity, page, limit));
	}
	
	/**
	 * @Desc 查看
	 * @return
	 * @author YY<2018年01月01日>
	 */
	@RequestMapping("detail")
	public String detail(Long id, ModelMap mmap){
		mmap.put("obj", errorLogService.selectById(id));
		return "/system/errorLog/detail";
	}
	
}
