<#include "/javadoc_source.include">
package ${basepackage}.${projectName}.${moduleName}.service.impl;

import org.springframework.stereotype.Service;

import ${basepackage}.${projectName}.${moduleName}.domain.${className};
import ${basepackage}.${projectName}.${moduleName}.mapper.${className}Mapper;
import ${basepackage}.${projectName}.${moduleName}.service.${className}Service;
import com.letsun.frame.core.service.impl.BaseServiceImpl;

/**
 * Service实现类
 * @Author generator
 * @Date <<#if now??>${now?string('yyyy年MM月dd日')}</#if>>
 * @version 1.0
 */
@Service
public class ${className}ServiceImpl extends BaseServiceImpl<${className}Mapper,${className}> implements ${className}Service{
	
}
