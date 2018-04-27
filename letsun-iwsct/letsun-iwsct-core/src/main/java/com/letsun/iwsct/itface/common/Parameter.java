package com.letsun.iwsct.itface.common;

public class Parameter {
	public Object name = null;
	public String tabname = null;
	public String keyName = null;
	public String type = null;
	public Object value = null;
	public String displayValue = null;
	public String condition = null;
	public String linkType = null;
	public String dataFrom = null;

	public Parameter() {
		// TODO Auto-generated constructor stub
	}
	
	public Parameter(String tabname, String value) {
		// TODO Auto-generated constructor stub
		this.tabname = tabname;
		this.value = value;
	}
	
	public Parameter(String tabname,String keyName,Object value, String condition) {
		// TODO Auto-generated constructor stub
		this.tabname = tabname;
		this.keyName = keyName;
		this.value = value;
		this.condition = condition;
	}

	public String getTabname() {
		return tabname;
	}

	public void setTabname(String tabname) {
		this.tabname = tabname;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(String dataFrom) {
		this.dataFrom = dataFrom;
	}

	public Object getName() {
		return name;
	}

	public void setName(Object name) {
		this.name = name;
	}

}
