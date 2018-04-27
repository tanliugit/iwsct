package com.letsun.generator;

import org.apache.commons.lang3.StringUtils;

import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorProperties;

/**
 * @Desc 代码生成器入口函数
 * @author YY
 * @date 2018年4月12日
 */
public class GeneratorMain {

	public static void main(String[] args) throws Exception {
		GeneratorFacade g = new GeneratorFacade();
		g.getGenerator().addTemplateRootDir(GeneratorProperties.getProperty("templateRootDir"));
		g.deleteOutRootDir();

		String tables = GeneratorProperties.getProperty("generatorTables");
		if (StringUtils.isNotEmpty(tables)) {
			g.generateByTable(tables.split(","));
		} else {
			g.generateByAllTable();
		}
		Runtime.getRuntime().exec("cmd.exe /c start " + GeneratorProperties.getRequiredProperty("outRoot"));
	}

}