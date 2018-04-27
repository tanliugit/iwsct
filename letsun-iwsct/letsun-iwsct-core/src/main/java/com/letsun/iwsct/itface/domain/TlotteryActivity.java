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
@TableName("t_lottery_activity")
public class TlotteryActivity extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 活动名称 */		
	private String name;
	/**  */		
	private Long corpid;
	/**  */		
	private Long orgid;
	/** 1:启动，2：停用，3:删除（其他） */		
	private Integer status;
	/**  */		
	private Date starttime;
	/**  */		
	private Date endtime;
	/**  */		
	private String remark;
	/** 图片路径 */		
	private String picurl;
	/**  */		
	private Long createorg;
	/**  */		
	private Long createcorp;
	/** 1：转盘，2：摇一摇 */		
	private Integer type;
	
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
	public void setStatus(Integer status) {
		this.status = status;
	}	
	public Integer getStatus() {
		return this.status;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}	
	public Date getStarttime() {
		return this.starttime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}	
	public Date getEndtime() {
		return this.endtime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	public String getRemark() {
		return this.remark;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}	
	public String getPicurl() {
		return this.picurl;
	}
	public void setCreateorg(Long createorg) {
		this.createorg = createorg;
	}	
	public Long getCreateorg() {
		return this.createorg;
	}
	public void setCreatecorp(Long createcorp) {
		this.createcorp = createcorp;
	}	
	public Long getCreatecorp() {
		return this.createcorp;
	}
	public void setType(Integer type) {
		this.type = type;
	}	
	public Integer getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("corpid",getCorpid())
			.append("orgid",getOrgid())
			.append("status",getStatus())
			.append("starttime",getStarttime())
			.append("endtime",getEndtime())
			.append("remark",getRemark())
			.append("picurl",getPicurl())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("createorg",getCreateorg())
			.append("createcorp",getCreatecorp())
			.append("type",getType())
			.append("deleted",getDeleted())
			.toString();
	}
}
