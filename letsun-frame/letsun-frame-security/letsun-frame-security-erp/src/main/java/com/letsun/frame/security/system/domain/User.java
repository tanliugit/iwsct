/*
 *Project: security
 ****************************************************************
 * 版权所有@2018 立信创源  保留所有权利.
 ***************************************************************/
package com.letsun.frame.security.system.domain;


import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.letsun.frame.core.domain.BaseEntity;

/**
 *  
 * @Author YY 
 * @Date <2018年03月13日>
 * @version 1.0
 */
@TableName("sys_user")
public class User extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/** 头像 */		
	private String imageFace;
	/** 昵称 */		
	private String nickname;
	/** 姓名 */		
	private String name;
	/** 邮箱 */		
	private String email;
	/** 性别:0男，1女 */		
	private Integer sex;
	/** 手机 */		
	private String mobile;
	/** 固话 */		
	private String phone;
	/** QQ号 */		
	private String qq;
	/** 微信 */		
	private String weixin;
	/** 支付宝 */		
	private String alipay;
	/** 地址 */		
	private String address;
	/** 类型:0平台会员1后台管理员 */		
	private Integer type;
	/** 状态:0待审核1已审核2锁定/冻结 */		
	private Integer status;
	/** 备注 */		
	private String remark;
	/** 角色列表 */
	@TableField(exist=false)
	private List<Role> roles;
	/** 用户账号 */
	@TableField(exist=false)
	private String account;
	/** 用户密码*/
	@TableField(exist=false)
	private String password;
	
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setImageFace(String value) {
		this.imageFace = value;
	}	
	public String getImageFace() {
		return this.imageFace;
	}
	public void setNickname(String value) {
		this.nickname = value;
	}	
	public String getNickname() {
		return this.nickname;
	}
	public void setName(String value) {
		this.name = value;
	}	
	public String getName() {
		return this.name;
	}
	public void setEmail(String value) {
		this.email = value;
	}	
	public String getEmail() {
		return this.email;
	}
	public void setSex(Integer value) {
		this.sex = value;
	}	
	public Integer getSex() {
		return this.sex;
	}
	public void setMobile(String value) {
		this.mobile = value;
	}	
	public String getMobile() {
		return this.mobile;
	}
	public void setPhone(String value) {
		this.phone = value;
	}	
	public String getPhone() {
		return this.phone;
	}
	public void setQq(String value) {
		this.qq = value;
	}	
	public String getQq() {
		return this.qq;
	}
	public void setWeixin(String value) {
		this.weixin = value;
	}	
	public String getWeixin() {
		return this.weixin;
	}
	public void setAlipay(String value) {
		this.alipay = value;
	}	
	public String getAlipay() {
		return this.alipay;
	}
	public void setAddress(String value) {
		this.address = value;
	}	
	public String getAddress() {
		return this.address;
	}
	public void setType(Integer value) {
		this.type = value;
	}	
	public Integer getType() {
		return this.type;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}	
	public Integer getStatus() {
		return this.status;
	}
	public void setRemark(String value) {
		this.remark = value;
	}	
	public String getRemark() {
		return this.remark;
	}
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("imageFace",getImageFace())
			.append("nickname",getNickname())
			.append("name",getName())
			.append("email",getEmail())
			.append("sex",getSex())
			.append("mobile",getMobile())
			.append("phone",getPhone())
			.append("qq",getQq())
			.append("weixin",getWeixin())
			.append("alipay",getAlipay())
			.append("address",getAddress())
			.append("type",getType())
			.append("status",getStatus())
			.append("remark",getRemark())
			.append("deleted",getDeleted())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.toString();
	}
}
