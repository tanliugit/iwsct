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
 * @Date <2018年03月16日>
 * @version 1.0
 */
@TableName("sys_menu")
public class Menu extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 父节点ID */		
	private Long parentId;
	/** 功能ID */		
	private Long functionId;
	/** 名称 */		
	private String name;
	/** URL */		
	private String url;
	/** icon图片 */		
	private String icon;
	/** 是否展开:0:是 ,1:否 */		
	private Integer isopen;
	/** 类型：0菜单/1目录/2功能 */		
	private Integer type;
	/** 菜单层级 */		
	private Integer level;
	/** 状态：0管理员增加/1系统初始化 */		
	private Integer source;
	/** 状态：0启用/1禁用 */		
	private Integer status;
	/** 排序 */		
	private Integer orderby;
	/** 备注 */		
	private String remark;
	/** 子菜单*/	
	@TableField(exist=false)
	private List<Menu> children;
	/** 父节点名称 */	
	@TableField(exist=false)
	private String parentName;
	/** 初始化功能：0是/1否 */
	@TableField(exist=false)
	private Integer initFunction;

	public void setParentId(Long value) {
		this.parentId = value;
	}	
	public Integer getInitFunction() {
		return initFunction;
	}
	public void setInitFunction(Integer initFunction) {
		this.initFunction = initFunction;
	}
	public List<Menu> getChildren() {
		return children;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public void setChildren(List<Menu> children) {
		this.children = children;
	}
	public Long getParentId() {
		return this.parentId;
	}
	public Long getFunctionId() {
		return functionId;
	}
	public void setFunctionId(Long functionId) {
		this.functionId = functionId;
	}
	public void setName(String value) {
		this.name = value;
	}	
	public String getName() {
		return this.name;
	}
	public void setUrl(String value) {
		this.url = value;
	}	
	public String getUrl() {
		return this.url;
	}
	public void setIcon(String value) {
		this.icon = value;
	}	
	public String getIcon() {
		return this.icon;
	}
	public void setIsopen(Integer value) {
		this.isopen = value;
	}	
	public Integer getIsopen() {
		return this.isopen;
	}
	public void setType(Integer value) {
		this.type = value;
	}	
	public Integer getType() {
		return this.type;
	}
	public void setLevel(Integer value) {
		this.level = value;
	}	
	public Integer getLevel() {
		return this.level;
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
	public void setRemark(String value) {
		this.remark = value;
	}	
	public String getRemark() {
		return this.remark;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (id == null && other.id != null) {
			return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("parentId",getParentId())
			.append("functionId",getFunctionId())
			.append("name",getName())
			.append("url",getUrl())
			.append("icon",getIcon())
			.append("isopen",getIsopen())
			.append("type",getType())
			.append("level",getLevel())
			.append("source",getSource())
			.append("status",getStatus())
			.append("orderby",getOrderby())
			.append("remark",getRemark())
			.append("deleted",getDeleted())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.toString();
	}
}
