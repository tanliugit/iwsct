package com.letsun.frame.security.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Desc 首页controller  
 * @author YY  
 * @date 2018年4月12日
 */
@Controller
public class IndexAdminController extends BaseAdminController {

	@RequestMapping("/")
	public String index(Model model) {
		return "redirect:/index";
	}

	@RequestMapping("/index")
	public String admnIndex(Model model) {
		return "/index";
	}

}
