package com.streamit.others.jdbc;

public class SearchConditionValues {
	public String whereType = "";
	public String filedName = "";
	public String operatorType = "";
	public Object values[];

	public SearchConditionValues(){}
	
	public SearchConditionValues(String whereType, String filedName, String operatorType, Object[] values) {
		super();
		this.whereType = whereType;
		this.filedName = filedName;
		this.operatorType = operatorType;
		this.values = values;
	}
	
	public String getWhereType() {
		return whereType;
	}
	public void setWhereType(String whereType) {
		this.whereType = whereType;
	}
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}
	public String getOperatorType() {
		return operatorType;
	}
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	public Object[] getValues() {
		return values;
	}
	public void setValues(Object[] values) {
		this.values = values;
	}	
}
