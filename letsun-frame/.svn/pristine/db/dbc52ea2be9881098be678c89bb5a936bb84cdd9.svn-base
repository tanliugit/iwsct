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
@TableName("sys_error_log")
public class ErrorLog extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	// columns
	/** 错误代码 */		
	private String errorCode;
	/** 请求URL */		
	private String requestUrl;
	/** 请求参数 */		
	private String params;
	/** 错误信息 */		
	private String message;
	/** 异常信息 */		
	private String exception;
	
	public void setErrorCode(String value) {
		this.errorCode = value;
	}	
	public String getErrorCode() {
		return this.errorCode;
	}
	public void setRequestUrl(String value) {
		this.requestUrl = value;
	}	
	public String getRequestUrl() {
		return this.requestUrl;
	}
	public void setParams(String value) {
		this.params = value;
	}	
	public String getParams() {
		return this.params;
	}
	public void setMessage(String value) {
		this.message = value;
	}	
	public String getMessage() {
		return this.message;
	}
	public void setException(String value) {
		this.exception = value;
	}	
	public String getException() {
		return this.exception;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("errorCode",getErrorCode())
			.append("requestUrl",getRequestUrl())
			.append("params",getParams())
			.append("message",getMessage())
			.append("exception",getException())
			.append("deleted",getDeleted())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.toString();
	}
}
