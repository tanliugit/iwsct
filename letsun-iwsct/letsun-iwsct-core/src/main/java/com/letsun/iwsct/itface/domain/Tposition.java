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
@TableName("t_position")
public class Tposition extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** POSITIONID */		
	private Long positionid;
	/** CORPID */		
	private Long corpid;
	/** ORGID */		
	private Long orgid;
	/** 职位名称 */		
	private String positionname;
	/** 描述 */		
	private String content;
	/** 备注 */		
	private String remarks;
	/** 1:正常，2：禁用，0:删除（其他） */		
	private Integer status;
	/** CREATECORP */		
	private Long createcorp;
	/** AREAID */		
	private Long areaid;
	/** 微信图片地址 */		
	private String logourl;
	
	public void setPositionid(Long positionid) {
		this.positionid = positionid;
	}	
	public Long getPositionid() {
		return this.positionid;
	}
	public void setCorpid(Long corpid) {
		this.corpid = corpid;
	}	
	public Long getCorpid() {
		return this.corpid;
	}
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}	
	public Long getOrgid() {
		return this.orgid;
	}
	public void setPositionname(String positionname) {
		this.positionname = positionname;
	}	
	public String getPositionname() {
		return this.positionname;
	}
	public void setContent(String content) {
		this.content = content;
	}	
	public String getContent() {
		return this.content;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}	
	public String getRemarks() {
		return this.remarks;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}	
	public Integer getStatus() {
		return this.status;
	}
	public void setCreatecorp(Long createcorp) {
		this.createcorp = createcorp;
	}	
	public Long getCreatecorp() {
		return this.createcorp;
	}
	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}	
	public Long getAreaid() {
		return this.areaid;
	}
	public void setLogourl(String logourl) {
		this.logourl = logourl;
	}	
	public String getLogourl() {
		return this.logourl;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("positionid",getPositionid())
			.append("corpid",getCorpid())
			.append("orgid",getOrgid())
			.append("positionname",getPositionname())
			.append("content",getContent())
			.append("remarks",getRemarks())
			.append("status",getStatus())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("createcorp",getCreatecorp())
			.append("areaid",getAreaid())
			.append("logourl",getLogourl())
			.append("deleted",getDeleted())
			.toString();
	}
}
