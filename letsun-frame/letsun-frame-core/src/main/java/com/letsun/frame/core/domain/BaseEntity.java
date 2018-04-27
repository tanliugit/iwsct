package com.letsun.frame.core.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.letsun.frame.core.common.utils.TimeUtils;

/**
 * Entity基类
 * @author YY
 * @Date 2017年10月10日
 * @version 1.0
 */
public class BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主键 */
	@TableId
	protected Long id;
	/** 是否删除 0否，1是*/
	@TableLogic
	protected Integer deleted;
	/** 创建人 */
	protected String creator;
	/** 创建时间 */
	protected Date createDate;
	/** 修改人 */
	protected String modifier;
	/** 修改时间 */
	protected Date modifyDate;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCreateDateStr() {
		return createDate !=null ? TimeUtils.parseDateToStr(createDate,TimeUtils.YYYY_MM_DD) : "";
	}

	public String getModifyDateStr() {
		return modifyDate !=null ? TimeUtils.parseDateToStr(modifyDate,TimeUtils.YYYY_MM_DD) : "";
	}
	
	public String getCreateDateTimeStr() {
		return createDate !=null ? TimeUtils.parseDateToStr(createDate,TimeUtils.YYYY_MM_DD_HH_MM_SS) : "";
	}

	public String getModifyDateTimeStr() {
		return modifyDate !=null ? TimeUtils.parseDateToStr(modifyDate,TimeUtils.YYYY_MM_DD_HH_MM_SS) : "";
	}

}
