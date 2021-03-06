/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotations.TableName;
import com.letsun.frame.core.domain.BaseEntity;

/**
 *  
 * @Author YY 
 * @Date <2018年03月17日>
 * @version 1.0
 */
@TableName("sys_function")
public class Function extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 名称 */		
	private String name;
	/** 功能编码 */		
	private String code;
	/** 类型：0通用功能/1专属功能 */		
	private Integer type;
	/** 状态：0管理员增加/1系统初始化 */		
	private Integer source;
	/** 排序 */		
	private Integer orderby;
	/** 状态：0启用/1关闭 */		
	private Integer status;

	public void setName(String value) {
		this.name = value;
	}	
	public String getName() {
		return this.name;
	}
	public void setCode(String value) {
		this.code = value;
	}	
	public String getCode() {
		return this.code;
	}
	public void setType(Integer value) {
		this.type = value;
	}	
	public Integer getType() {
		return this.type;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public void setOrderby(Integer value) {
		this.orderby = value;
	}	
	public Integer getOrderby() {
		return this.orderby;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}	
	public Integer getStatus() {
		return this.status;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("code",getCode())
			.append("type",getType())
			.append("source",getSource())
			.append("orderby",getOrderby())
			.append("status",getStatus())
			.append("deleted",getDeleted())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.toString();
	}
}
