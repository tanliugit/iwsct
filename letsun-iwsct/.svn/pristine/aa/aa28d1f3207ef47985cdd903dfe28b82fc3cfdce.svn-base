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
 * @Date <2018年04月25日>
 * @version 1.0
 */
@TableName("t_vote")
public class Tvote extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 选项ID */		
	private Long optid;
	/** 微信号 */		
	private String wxno;
	/**  */		
	private Long corpid;
	
	public void setOptid(Long optid) {
		this.optid = optid;
	}	
	public Long getOptid() {
		return this.optid;
	}
	public void setWxno(String wxno) {
		this.wxno = wxno;
	}	
	public String getWxno() {
		return this.wxno;
	}
	public void setCorpid(Long corpid) {
		this.corpid = corpid;
	}	
	public Long getCorpid() {
		return this.corpid;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("optid",getOptid())
			.append("wxno",getWxno())
			.append("createDate",getCreateDate())
			.append("corpid",getCorpid())
			.append("creator",getCreator())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("deleted",getDeleted())
			.toString();
	}
}
