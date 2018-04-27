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
@TableName("t_activity_checkin")
public class TactivityCheckin extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**  */		
	private String name;
	/** 起始日期 */		
	private String starttime;
	/** 终止日期 */		
	private String endtime;
	/** 是否开启抽奖功能1：是；0：否 */		
	private Integer isflag;
	/** 抽奖启动密码 */		
	private String lotterypassword;
	/**  */		
	private Integer status;
	/** 描述 */		
	private String remark;
	/**  */		
	private Long corpid;
	/**  */		
	private Long orgid;
	
	public void setName(String name) {
		this.name = name;
	}	
	public String getName() {
		return this.name;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}	
	public String getStarttime() {
		return this.starttime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}	
	public String getEndtime() {
		return this.endtime;
	}
	public void setIsflag(Integer isflag) {
		this.isflag = isflag;
	}	
	public Integer getIsflag() {
		return this.isflag;
	}
	public void setLotterypassword(String lotterypassword) {
		this.lotterypassword = lotterypassword;
	}	
	public String getLotterypassword() {
		return this.lotterypassword;
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
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("starttime",getStarttime())
			.append("endtime",getEndtime())
			.append("isflag",getIsflag())
			.append("lotterypassword",getLotterypassword())
			.append("status",getStatus())
			.append("remark",getRemark())
			.append("corpid",getCorpid())
			.append("orgid",getOrgid())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("deleted",getDeleted())
			.toString();
	}
}
