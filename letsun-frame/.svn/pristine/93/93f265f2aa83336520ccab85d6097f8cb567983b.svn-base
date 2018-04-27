/*
 *Project: core
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.core.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotations.TableName;
import com.letsun.frame.core.domain.BaseEntity;

/**
 *  
 * @Author YY 
 * @Date <2018年03月19日>
 * @version 1.0
 */
@TableName("sys_operation_log")
public class OperationLog extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	// columns
	/** 操作人IP */		
	private String requestIp;
	/** 操作人请求URL */		
	private String requestUrl;
	/** 操作人请求URL */		
	private String requestParams;
	/** 类路径 */		
	private String classPath;
	/** 请求方法名称 */		
	private String methodName;
	/** 方法参数 */		
	private String methodParams;
	/** 业务类型：0其它1新增2修改3删除4授权5导出6导入7登录8退出登录9禁止访问 */		
	private Integer businessType;
	/** 业务名称：如用户管理 */		
	private String businessName;
	/** 业务状态：0其它1成功2失败 */		
	private Integer businessStatus;
	/** 业务备注 */		
	private String businessRemarks;
	/** 业务类型：0其它1后台用户2手机端用户 */		
	private Integer operatorType;
	/** 更新前JSOn对象 */		
	private String beforeUpdateJson;
	/** 更新后JSOn对象 */		
	private String afterUpdateJson;
	
	public void setRequestIp(String value) {
		this.requestIp = value;
	}	
	public String getRequestIp() {
		return this.requestIp;
	}
	public void setRequestUrl(String value) {
		this.requestUrl = value;
	}	
	public String getRequestUrl() {
		return this.requestUrl;
	}
	public void setRequestParams(String value) {
		this.requestParams = value;
	}	
	public String getRequestParams() {
		return this.requestParams;
	}
	public void setClassPath(String value) {
		this.classPath = value;
	}	
	public String getClassPath() {
		return this.classPath;
	}
	public void setMethodName(String value) {
		this.methodName = value;
	}	
	public String getMethodName() {
		return this.methodName;
	}
	public void setMethodParams(String value) {
		this.methodParams = value;
	}	
	public String getMethodParams() {
		return this.methodParams;
	}
	public void setBusinessType(Integer value) {
		this.businessType = value;
	}	
	public Integer getBusinessType() {
		return this.businessType;
	}
	public void setBusinessName(String value) {
		this.businessName = value;
	}	
	public String getBusinessName() {
		return this.businessName;
	}
	public void setBusinessStatus(Integer value) {
		this.businessStatus = value;
	}	
	public Integer getBusinessStatus() {
		return this.businessStatus;
	}
	public void setBusinessRemarks(String value) {
		this.businessRemarks = value;
	}	
	public String getBusinessRemarks() {
		return this.businessRemarks;
	}
	public void setOperatorType(Integer value) {
		this.operatorType = value;
	}	
	public Integer getOperatorType() {
		return this.operatorType;
	}
	public void setBeforeUpdateJson(String value) {
		this.beforeUpdateJson = value;
	}	
	public String getBeforeUpdateJson() {
		return this.beforeUpdateJson;
	}
	public void setAfterUpdateJson(String value) {
		this.afterUpdateJson = value;
	}	
	public String getAfterUpdateJson() {
		return this.afterUpdateJson;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("requestIp",getRequestIp())
			.append("requestUrl",getRequestUrl())
			.append("requestParams",getRequestParams())
			.append("classPath",getClassPath())
			.append("methodName",getMethodName())
			.append("methodParams",getMethodParams())
			.append("businessType",getBusinessType())
			.append("businessName",getBusinessName())
			.append("businessStatus",getBusinessStatus())
			.append("businessRemarks",getBusinessRemarks())
			.append("operatorType",getOperatorType())
			.append("beforeUpdateJson",getBeforeUpdateJson())
			.append("afterUpdateJson",getAfterUpdateJson())
			.append("deleted",getDeleted())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.toString();
	}
}
