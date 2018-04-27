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
@TableName("t_lottery_buyer")
public class TlotteryBuyer extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**  */		
	private Long corpid;
	/** 1:正常，2：其他，0:删除 */		
	private Integer status;
	/** 抽奖次数，默认只能抽一次 */		
	private Integer total;
	/** 手机号 */		
	private String mobile;
	/** 查看中奖密码 */		
	private String password;
	/** 提交的收奖地址 */		
	private String address;
	/**  */		
	private String name;
	/**  */		
	private String sex;
	/**  */		
	private Date birthday;
	/**  */		
	private Long memberid;
	/**  */		
	private String wxno;
	/**  */		
	private String nick;
	/**  */		
	private String photo;
	/**  */		
	private String remark;
	/** 邮箱 */		
	private String email;
	
	public void setCorpid(Long corpid) {
		this.corpid = corpid;
	}	
	public Long getCorpid() {
		return this.corpid;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}	
	public Integer getStatus() {
		return this.status;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}	
	public Integer getTotal() {
		return this.total;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}	
	public String getMobile() {
		return this.mobile;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	public String getPassword() {
		return this.password;
	}
	public void setAddress(String address) {
		this.address = address;
	}	
	public String getAddress() {
		return this.address;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public String getName() {
		return this.name;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}	
	public String getSex() {
		return this.sex;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}	
	public Date getBirthday() {
		return this.birthday;
	}
	public void setMemberid(Long memberid) {
		this.memberid = memberid;
	}	
	public Long getMemberid() {
		return this.memberid;
	}
	public void setWxno(String wxno) {
		this.wxno = wxno;
	}	
	public String getWxno() {
		return this.wxno;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}	
	public String getNick() {
		return this.nick;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}	
	public String getPhoto() {
		return this.photo;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}	
	public String getRemark() {
		return this.remark;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
	public String getEmail() {
		return this.email;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("corpid",getCorpid())
			.append("status",getStatus())
			.append("total",getTotal())
			.append("mobile",getMobile())
			.append("password",getPassword())
			.append("address",getAddress())
			.append("name",getName())
			.append("sex",getSex())
			.append("birthday",getBirthday())
			.append("memberid",getMemberid())
			.append("wxno",getWxno())
			.append("nick",getNick())
			.append("photo",getPhoto())
			.append("remark",getRemark())
			.append("createDate",getCreateDate())
			.append("email",getEmail())
			.append("creator",getCreator())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("deleted",getDeleted())
			.toString();
	}
}
