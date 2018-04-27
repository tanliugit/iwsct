/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotations.TableName;
import com.letsun.frame.core.domain.BaseEntity;

/**
 * 实体类
 * @Author generator
 * @Date <2018年04月19日>
 * @version 1.0
 */
@TableName("t_corp_area")
public class TcorpArea extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 地区名称 */		
	private String name;
	/** 企业ID */		
	private Long corpid;
	/** 状态 */		
	private Integer status;
	/** 地区别名 */		
	private String nickname;
	/** 详细地址 */		
	private String address;
	/** 备注 */		
	private String remarks;
	/** 描述 */		
	private String content;
	/** 默认回复 */		
	private String recovery;
	
	public void setName(String name) {
		this.name = name;
	}	
	public String getName() {
		return this.name;
	}
	public void setCorpid(Long corpid) {
		this.corpid = corpid;
	}	
	public Long getCorpid() {
		return this.corpid;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}	
	public Integer getStatus() {
		return this.status;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}	
	public String getNickname() {
		return this.nickname;
	}
	public void setAddress(String address) {
		this.address = address;
	}	
	public String getAddress() {
		return this.address;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}	
	public String getRemarks() {
		return this.remarks;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public String getContent() {
		return this.content;
	}
	public void setRecovery(String recovery) {
		this.recovery = recovery;
	}	
	public String getRecovery() {
		return this.recovery;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("corpid",getCorpid())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("status",getStatus())
			.append("nickname",getNickname())
			.append("address",getAddress())
			.append("remarks",getRemarks())
			.append("content",getContent())
			.append("recovery",getRecovery())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("deleted",getDeleted())
			.toString();
	}
}
