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
@TableName("t_resume")
public class Tresume extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** RESUMEID */		
	private Long resumeid;
	/** CORPID */		
	private Long corpid;
	/** 职位id */		
	private Long positionid;
	/** 手机号 */		
	private String mobile;
	/** 应聘人 */		
	private String candidate;
	/** 籍贯 */		
	private String hail;
	/** 身份证号 */		
	private String idnumber;
	/** 性别 */		
	private String sex;
	/** 年龄 */		
	private Long age;
	/** 身高 */		
	private Long height;
	/** 最高学历 */		
	private String education;
	/** 学校 */		
	private String school;
	/** 专业 */		
	private String specialty;
	/** 个人说明 */		
	private String content;
	/** 备注 */		
	private String remarks;
	/** 提交进度：1:已提交，2：审核中，3:（其他） */		
	private Integer schedule;
	/** 1:正常，2：禁用，0:删除（其他） */		
	private Integer status;
	/** CREATECORP */		
	private Long createcorp;
	/** AREAID */		
	private Long areaid;
	/** ENGLEVEL */		
	private String englevel;
	/** 毕业时间 */		
	private String graduation;
	/** 目前工作公司 */		
	private String currentcompany;
	/** 目前工作岗位 */		
	private String currentjob;
	/** 工作年限 */		
	private String worklonger;
	/** 期望薪资 */		
	private String expectations;
	/** 年级 */		
	private String grade;
	/** 邮箱 */		
	private String email;
	/** 活动ID */		
	private Long hractivityid;
	/** 关联的粉丝ID */		
	private Long fansid;
	
	public void setResumeid(Long resumeid) {
		this.resumeid = resumeid;
	}	
	public Long getResumeid() {
		return this.resumeid;
	}
	public void setCorpid(Long corpid) {
		this.corpid = corpid;
	}	
	public Long getCorpid() {
		return this.corpid;
	}
	public void setPositionid(Long positionid) {
		this.positionid = positionid;
	}	
	public Long getPositionid() {
		return this.positionid;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}	
	public String getMobile() {
		return this.mobile;
	}
	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}	
	public String getCandidate() {
		return this.candidate;
	}
	public void setHail(String hail) {
		this.hail = hail;
	}	
	public String getHail() {
		return this.hail;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}	
	public String getIdnumber() {
		return this.idnumber;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}	
	public String getSex() {
		return this.sex;
	}
	public void setAge(Long age) {
		this.age = age;
	}	
	public Long getAge() {
		return this.age;
	}
	public void setHeight(Long height) {
		this.height = height;
	}	
	public Long getHeight() {
		return this.height;
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
	public void setSchedule(Integer schedule) {
		this.schedule = schedule;
	}	
	public Integer getSchedule() {
		return this.schedule;
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
	public void setEnglevel(String englevel) {
		this.englevel = englevel;
	}	
	public String getEnglevel() {
		return this.englevel;
	}
	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}	
	public String getGraduation() {
		return this.graduation;
	}
	public void setCurrentcompany(String currentcompany) {
		this.currentcompany = currentcompany;
	}	
	public String getCurrentcompany() {
		return this.currentcompany;
	}
	public void setCurrentjob(String currentjob) {
		this.currentjob = currentjob;
	}	
	public String getCurrentjob() {
		return this.currentjob;
	}
	public void setWorklonger(String worklonger) {
		this.worklonger = worklonger;
	}	
	public String getWorklonger() {
		return this.worklonger;
	}
	public void setExpectations(String expectations) {
		this.expectations = expectations;
	}	
	public String getExpectations() {
		return this.expectations;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}	
	public String getGrade() {
		return this.grade;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getEmail() {
		return this.email;
	}
	public void setHractivityid(Long hractivityid) {
		this.hractivityid = hractivityid;
	}	
	public Long getHractivityid() {
		return this.hractivityid;
	}
	public void setFansid(Long fansid) {
		this.fansid = fansid;
	}	
	public Long getFansid() {
		return this.fansid;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("resumeid",getResumeid())
			.append("corpid",getCorpid())
			.append("positionid",getPositionid())
			.append("mobile",getMobile())
			.append("candidate",getCandidate())
			.append("hail",getHail())
			.append("idnumber",getIdnumber())
			.append("sex",getSex())
			.append("age",getAge())
			.append("height",getHeight())
			.append("education",getEducation())
			.append("school",getSchool())
			.append("specialty",getSpecialty())
			.append("content",getContent())
			.append("remarks",getRemarks())
			.append("schedule",getSchedule())
			.append("status",getStatus())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("createcorp",getCreatecorp())
			.append("areaid",getAreaid())
			.append("englevel",getEnglevel())
			.append("graduation",getGraduation())
			.append("currentcompany",getCurrentcompany())
			.append("currentjob",getCurrentjob())
			.append("worklonger",getWorklonger())
			.append("expectations",getExpectations())
			.append("grade",getGrade())
			.append("email",getEmail())
			.append("hractivityid",getHractivityid())
			.append("fansid",getFansid())
			.append("deleted",getDeleted())
			.toString();
	}
}
