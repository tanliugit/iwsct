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
 * @Date <2018年03月13日>
 * @version 1.0
 */
@TableName("sys_user_role")
public class UserRole extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 用户ID */		
	private Long userId;
	/** 角色ID */		
	private Long roleId;
	
	public void setUserId(Long value) {
		this.userId = value;
	}	
	public Long getUserId() {
		return this.userId;
	}
	public void setRoleId(Long value) {
		this.roleId = value;
	}	
	public Long getRoleId() {
		return this.roleId;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("userId",getUserId())
			.append("roleId",getRoleId())
			.append("deleted",getDeleted())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.toString();
	}
}
