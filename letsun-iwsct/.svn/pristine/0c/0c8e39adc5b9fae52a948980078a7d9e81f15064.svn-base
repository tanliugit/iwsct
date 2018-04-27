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
 * @Date <2018年04月24日>
 * @version 1.0
 */
@TableName("t_checkin_comment_record")
public class TcheckinCommentRecord extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 活动ID */		
	private Long activityid;
	/** 签到人ID */		
	private Long fansid;
	/** 论记录信息 */		
	private String remark;
	/** 1:上墙，2：禁用，3:删除（其他） */		
	private Integer status;
	/**  */		
	private Long corpid;
	/**  */		
	private Long orgid;
	/**  */		
	private Date lastedittime;
	
	public void setActivityid(Long activityid) {
		this.activityid = activityid;
	}	
	public Long getActivityid() {
		return this.activityid;
	}
	public void setFansid(Long fansid) {
		this.fansid = fansid;
	}	
	public Long getFansid() {
		return this.fansid;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	public String getRemark() {
		return this.remark;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}	
	public Integer getStatus() {
		return this.status;
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
	public void setLastedittime(Date lastedittime) {
		this.lastedittime = lastedittime;
	}	
	public Date getLastedittime() {
		return this.lastedittime;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("activityid",getActivityid())
			.append("fansid",getFansid())
			.append("remark",getRemark())
			.append("status",getStatus())
			.append("corpid",getCorpid())
			.append("orgid",getOrgid())
			.append("createDate",getCreateDate())
			.append("lastedittime",getLastedittime())
			.append("deleted",getDeleted())
			.append("modifyDate",getModifyDate())
			.append("modifier",getModifier())
			.append("creator",getCreator())
			.toString();
	}
}
