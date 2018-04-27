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
 * @Date <2018年04月23日>
 * @version 1.0
 */
@TableName("t_fans_la")
public class TfansLa extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**  */		
	private Long fansid;
	/**  */		
	private Long lafansid;
	/**  */		
	private Integer shopid;
	/**  */		
	private Long corpid;
	/**  */		
	private Long orgid;
	/**  */		
	private Integer status;
	/**  */		
	private String remark;
	
	public void setFansid(Long fansid) {
		this.fansid = fansid;
	}	
	public Long getFansid() {
		return this.fansid;
	}
	public void setLafansid(Long lafansid) {
		this.lafansid = lafansid;
	}	
	public Long getLafansid() {
		return this.lafansid;
	}
	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}	
	public Integer getShopid() {
		return this.shopid;
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
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	public String getRemark() {
		return this.remark;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("fansid",getFansid())
			.append("lafansid",getLafansid())
			.append("shopid",getShopid())
			.append("corpid",getCorpid())
			.append("orgid",getOrgid())
			.append("status",getStatus())
			.append("remark",getRemark())
			.append("createDate",getCreateDate())
			.append("creator",getCreator())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("deleted",getDeleted())
			.toString();
	}
}
