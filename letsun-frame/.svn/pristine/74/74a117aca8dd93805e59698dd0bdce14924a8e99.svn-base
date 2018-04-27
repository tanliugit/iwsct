package com.letsun.frame.core.common.lang.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.letsun.frame.core.common.lang.enums.EnumType.BusinessType;
import com.letsun.frame.core.common.lang.enums.EnumType.OperatorType;



/**
 * @Desc 日志注解
 * @author YY<2017年10月17日>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface OperationLogs {
	
	/** 操作人类别  */		
	public OperatorType operatorType() default OperatorType.MANAGE;
	
	/** 操作人 */		
	public String operator() default "";
	
	/** 操作业务类型 */		
	public BusinessType businessType() default BusinessType.OTHER;
	
	/** 操作业务 */		
	public String businessName() default "";
	
	/** 操作业务备注 */		
	public String businessRemarks() default "";		
	
	/** 描述：可使用动态参数，同Annotation基本用法,即{参数名} */		
	public String description() default "";
}
