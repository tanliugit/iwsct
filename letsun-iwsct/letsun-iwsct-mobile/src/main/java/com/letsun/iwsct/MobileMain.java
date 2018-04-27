package com.letsun.iwsct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * 入口主函数  
 * @author generator  
 * @date 2018年4月16日
 */
@SpringBootApplication(scanBasePackages="com.letsun")
public class MobileMain extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(MobileMain.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类
		return builder.sources(MobileMain.class);
	}
}
