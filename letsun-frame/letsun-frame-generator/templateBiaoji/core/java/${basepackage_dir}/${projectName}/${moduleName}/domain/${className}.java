<#include "/javadoc_source.include">
package ${basepackage}.${projectName}.${moduleName}.domain;

<#list table.columns as column>
<#if !ignoreAttributes?seq_contains(column.columnNameLower) && column.javaType == 'Date'>
import java.util.Date;<#break>
</#if>	
</#list>

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotations.TableName;
import com.letsun.frame.core.domain.BaseEntity;

/**
 * 实体类
 * @Author generator
 * @Date <<#if now??>${now?string('yyyy年MM月dd日')}</#if>>
 * @version 1.0
 */
@TableName("${table.sqlName}")
public class ${className} extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	<#list table.columns as column>
	<#if !ignoreAttributes?seq_contains(column.columnNameLower)>
	/** ${column.remarks!''} */		
	private ${column.javaType} ${column.columnNameLower};
	</#if>	
	</#list>
	
	<#list table.columns as column>	
	<#if !ignoreAttributes?seq_contains(column.columnNameLower)>
	public void set${column.columnName}(${column.javaType} ${column.columnNameLower}) {
		this.${column.columnNameLower} = ${column.columnNameLower};
	}	
	public ${column.javaType} get${column.columnName}() {
		return this.${column.columnNameLower};
	}
	</#if>
	</#list>
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
		<#list table.columns as column>
			.append("${column.columnNameLower}",get${column.columnName}())
		</#list>
			.toString();
	}
}
