/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.domain;


import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.letsun.frame.core.domain.BaseEntity;


/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
@TableName("sys_role")
public class Role extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 名称 */		
	private String name;
	/** 父节点ID */		
	private Long parentId;
	/** 类型：0新增普通角色/1初始化超级角色 */		
	private Integer type;
	/** 状态：0管理员增加/1系统初始化 */		
	private Integer source;
	/** 状态：0启用/1禁用 */		
	private Integer status;
	/** 排序 */		
	private Integer orderby;
	/** 角色拥有的菜单/功能列表*/	
	@TableField(exist=false)
	private List<Menu> menus;
	/** 父节点名称 */	
	@TableField(exist=false)
	private String parentName;

	public void setName(String value) {
		this.name = value;
	}	
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getName() {
		return this.name;
	}
	public void setParentId(Long value) {
		this.parentId = value;
	}	
	public Long getParentId() {
		return this.parentId;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}	
	public Integer getStatus() {
		return this.status;
	}
	public void setOrderby(Integer value) {
		this.orderby = value;
	}	
	public Integer getOrderby() {
		return this.orderby;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("parentId",getParentId())
			.append("type",getType())
			.append("source",getSource())
			.append("status",getStatus())
			.append("orderby",getOrderby())
			.append("deleted",getDeleted())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.toString();
	}
}
