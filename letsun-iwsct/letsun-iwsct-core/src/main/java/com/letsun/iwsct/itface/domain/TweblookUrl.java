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
@TableName("t_weblook_url")
public class TweblookUrl extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** NAME */		
	private String name;
	/** CORPID */		
	private Long corpid;
	/** ORGID */		
	private Long orgid;
	/** 微刊链接 */		
	private String url;
	/** 访问总人数 */		
	private Long visitpsum;
	/** 访问总次数 */		
	private Long visitsum;
	/** 1:正常，2：禁用，0:删除（其他） */		
	private Integer status;
	/** REMARK */		
	private String remark;
	/** 分享次数统计 */		
	private Long sharesum;
	
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
	public void setOrgid(Long orgid) {
		this.orgid = orgid;
	}	
	public Long getOrgid() {
		return this.orgid;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
	public String getUrl() {
		return this.url;
	}
	public void setVisitpsum(Long visitpsum) {
		this.visitpsum = visitpsum;
	}	
	public Long getVisitpsum() {
		return this.visitpsum;
	}
	public void setVisitsum(Long visitsum) {
		this.visitsum = visitsum;
	}	
	public Long getVisitsum() {
		return this.visitsum;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}	
	public Integer getStatus() {
		return this.status;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	public String getRemark() {
		return this.remark;
	}
	public void setSharesum(Long sharesum) {
		this.sharesum = sharesum;
	}	
	public Long getSharesum() {
		return this.sharesum;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("corpid",getCorpid())
			.append("orgid",getOrgid())
			.append("url",getUrl())
			.append("visitpsum",getVisitpsum())
			.append("visitsum",getVisitsum())
			.append("status",getStatus())
			.append("remark",getRemark())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("sharesum",getSharesum())
			.append("deleted",getDeleted())
			.toString();
	}
}
