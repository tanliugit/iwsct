package com.letsun.frame.security.common.controller;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.letsun.frame.core.common.utils.WebUtils;
import com.letsun.frame.core.controller.BaseController;
import com.letsun.frame.core.domain.Message;

/**
 * @Desc 公共controller  
 * @author YY  
 * @date 2018年4月12日
 */
@Controller
public class CommonController extends BaseController{
	
    @RequestMapping(value="/forbid",method=RequestMethod.GET)
    public String forbid(){ 
    	if (WebUtils.isAjax(getRequest())) {
    		// 如果是ajax请求，JSON格式返回
			getResponse().setContentType("application/json;charset=UTF-8");
			PrintWriter writer = null;
			try {
				writer = getResponse().getWriter();
				writer.write(JSON.toJSONString(Message.error("你没有访问权限")));
				writer.flush();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
        return "/common/error/403";
    } 
}
