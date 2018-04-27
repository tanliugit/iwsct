/*
 *Project: iwsct
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.iwsct.itface.domain;

import java.util.Date;
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
@TableName("t_weblook_pvuv")
public class TweblookPvuv extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 微刊URL */		
	private String url;
	/** 粉丝ID */		
	private Long fansid;
	/** CORPID */		
	private Long corpid;
	/** ORGID */		
	private Long orgid;
	/** 统计类型：1:URL，2：点击，3:（其他） */		
	private Integer type;
	/** 访问IP */		
	private String visitip;
	/** 访问次数 */		
	private Long visitsum;
	/** 访问时间 */		
	private Date visittime;
	
	public void setUrl(String url) {
		this.url = url;
	}	
	public String getUrl() {
		return this.url;
	}
	public void setFansid(Long fansid) {
		this.fansid = fansid;
	}	
	public Long getFansid() {
		return this.fansid;
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
	public void setType(Integer type) {
		this.type = type;
	}	
	public Integer getType() {
		return this.type;
	}
	public void setVisitip(String visitip) {
		this.visitip = visitip;
	}	
	public String getVisitip() {
		return this.visitip;
	}
	public void setVisitsum(Long visitsum) {
		this.visitsum = visitsum;
	}	
	public Long getVisitsum() {
		return this.visitsum;
	}
	public void setVisittime(Date visittime) {
		this.visittime = visittime;
	}	
	public Date getVisittime() {
		return this.visittime;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("url",getUrl())
			.append("fansid",getFansid())
			.append("corpid",getCorpid())
			.append("orgid",getOrgid())
			.append("type",getType())
			.append("visitip",getVisitip())
			.append("visitsum",getVisitsum())
			.append("visittime",getVisittime())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("deleted",getDeleted())
			.toString();
	}
}
