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
@TableName("t_checkin_record")
public class TcheckinRecord extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 活动ID */		
	private Long activityid;
	/** 签到人ID */		
	private Long fansid;
	/** 1.已签到，2中奖，3，已提交信息 */		
	private Integer status;
	/** 描述 */		
	private String remark;
	/**  */		
	private Long corpid;
	/**  */		
	private Long orgid;
	/** 微信签到中奖等级 */		
	private Integer level;
	/** 姓名 */		
	private String lotteryname;
	/** 性别 */		
	private String sex;
	/** 最高学历 */		
	private String education;
	/** 学校 */		
	private String school;
	/** 专业 */		
	private String specialty;
	/** 手机号 */		
	private String mobile;
	/** 快递收件地址 */		
	private String address;
	/** 邮编 */		
	private String zipcode;
	
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
	public void setLevel(Integer level) {
		this.level = level;
	}	
	public Integer getLevel() {
		return this.level;
	}
	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}	
	public String getLotteryname() {
		return this.lotteryname;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}	
	public String getSex() {
		return this.sex;
	}
	public void setEducation(String education) {
		this.education = education;
	}	
	public String getEducation() {
		return this.education;
	}
	public void setSchool(String school) {
		this.school = school;
	}	
	public String getSchool() {
		return this.school;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}	
	public String getSpecialty() {
		return this.specialty;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}	
	public String getMobile() {
		return this.mobile;
	}
	public void setAddress(String address) {
		this.address = address;
	}	
	public String getAddress() {
		return this.address;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}	
	public String getZipcode() {
		return this.zipcode;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("activityid",getActivityid())
			.append("fansid",getFansid())
			.append("status",getStatus())
			.append("remark",getRemark())
			.append("corpid",getCorpid())
			.append("orgid",getOrgid())
			.append("createDate",getCreateDate())
			.append("level",getLevel())
			.append("lotteryname",getLotteryname())
			.append("sex",getSex())
			.append("education",getEducation())
			.append("school",getSchool())
			.append("specialty",getSpecialty())
			.append("mobile",getMobile())
			.append("address",getAddress())
			.append("zipcode",getZipcode())
			.append("creator",getCreator())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("deleted",getDeleted())
			.toString();
	}
}
