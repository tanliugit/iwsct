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
@TableName("t_lottery_prize")
public class TlotteryPrize extends BaseEntity{
	private static final long serialVersionUID = 1L;
	
	/**  */		
	private String name;
	/**  */		
	private Long lotteryid;
	/** 抽奖概率 */		
	private Double probability;
	/** 奖品等级 */		
	private Integer level;
	/** 已中奖数 */		
	private Integer total;
	/** 奖品数量 */		
	private Integer maxsum;
	/** 每天中奖数设置 */		
	private Integer daysum;
	/** 详细描述 */		
	private String contents;
	/**  */		
	private String remark;
	/** 图片路径 */		
	private String picurl;
	/** 1：实物，2：积分 */		
	private Integer type;
	/** 设置积分 */		
	private Integer score;
	
	public void setName(String name) {
		this.name = name;
	}	
	public String getName() {
		return this.name;
	}
	public void setLotteryid(Long lotteryid) {
		this.lotteryid = lotteryid;
	}	
	public Long getLotteryid() {
		return this.lotteryid;
	}
	public void setProbability(Double probability) {
		this.probability = probability;
	}	
	public Double getProbability() {
		return this.probability;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}	
	public Integer getLevel() {
		return this.level;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}	
	public Integer getTotal() {
		return this.total;
	}
	public void setMaxsum(Integer maxsum) {
		this.maxsum = maxsum;
	}	
	public Integer getMaxsum() {
		return this.maxsum;
	}
	public void setDaysum(Integer daysum) {
		this.daysum = daysum;
	}	
	public Integer getDaysum() {
		return this.daysum;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}	
	public String getContents() {
		return this.contents;
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
	public void setType(Integer type) {
		this.type = type;
	}	
	public Integer getType() {
		return this.type;
	}
	public void setScore(Integer score) {
		this.score = score;
	}	
	public Integer getScore() {
		return this.score;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("id",getId())
			.append("name",getName())
			.append("lotteryid",getLotteryid())
			.append("probability",getProbability())
			.append("level",getLevel())
			.append("total",getTotal())
			.append("maxsum",getMaxsum())
			.append("daysum",getDaysum())
			.append("contents",getContents())
			.append("remark",getRemark())
			.append("picurl",getPicurl())
			.append("type",getType())
			.append("score",getScore())
			.append("creator",getCreator())
			.append("createDate",getCreateDate())
			.append("modifier",getModifier())
			.append("modifyDate",getModifyDate())
			.append("deleted",getDeleted())
			.toString();
	}
}
