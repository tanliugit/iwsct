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
@TableName("t_lottery_record")
public class TlotteryRecord extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**  */		
	private Long buyerid;
	/**  */		
	private Long corpid;
	/**  */		
	private Long lotteryid;
	/**  */		
	private Long prizeid;
	/** 1:已中奖，2：已发奖，3：未发奖，0:删除（其他） */		
	private Integer status;
	/**  */		
	private String remark;
	
	public void setBuyerid(Long buyerid) {
		this.buyerid = buyerid;
	}	
	public Long getBuyerid() {
		return this.buyerid;
	}
	public void setCorpid(Long corpid) {
		this.corpid = corpid;
	}	
	public Long getCorpid() {
		return this.corpid;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}	
	public Long getLotteryid() {
		return this.lotteryid;
	}
	public void setPrizeid(Long prizeid) {
		this.prizeid = prizeid;
	}	
	public Long getPrizeid() {
		return this.prizeid;
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
			.append("buyerid",getBuyerid())
			.append("corpid",getCorpid())
			.append("lotteryid",getLotteryid())
			.append("prizeid",getPrizeid())
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
