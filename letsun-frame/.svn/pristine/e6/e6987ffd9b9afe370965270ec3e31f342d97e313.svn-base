package com.letsun.frame.core.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Desc 错误页面定义  
 * @author YY  
 * @date 2018年4月12日
 */
@Controller
public class BaseErrorController implements ErrorController {
	
	@Override
    public String getErrorPath() {
        return "/common/error/404";
    }

	@RequestMapping("/error")
    public String error() {
		 return "/common/error/404";
    }
}
