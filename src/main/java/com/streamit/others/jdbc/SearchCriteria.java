package com.streamit.others.jdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class SearchCriteria implements Serializable{
    private String orderBy = "";
    private String orderType = "";
    private String groupBy = "";
    private SearchConditionValues[] searchConditionValues;
    private Pagging pagging = new Pagging();
    
    public SearchCriteria(){}
    
    public SearchCriteria(Pagging pagging){
    	this.pagging = pagging;
    }
    
    @SuppressWarnings("rawtypes")
	private List sqlParameter = new ArrayList();    
    
    private String sqlCustomCondition;
    
	public String getGroupBy() {
        return groupBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List getSqlParameter() {
    	List sqlParameterTemp = new ArrayList(sqlParameter);
    	
    	sqlParameter = new ArrayList();
    	if(searchConditionValues!=null && searchConditionValues.length>0){
    		for(int i=0; i<searchConditionValues.length; i++){
    			SearchConditionValues valuesBean = searchConditionValues[i];
    			if(valuesBean!=null){
	    			Object[] value = valuesBean.getValues();
	    			if(value!=null){
		    			for(int j=0;j<value.length; j++){		    				
		    				if(value[j]!=null && !value[j].equals("") && !("NULL").equalsIgnoreCase(value[j].toString()) && !(value[j] instanceof Boolean)){
		    					sqlParameter.add(value[j].toString().toUpperCase());
		    				}
		    			}    
	    			}
    			}
    		}
    	}
    	sqlParameterTemp.addAll(sqlParameter);
    	sqlParameter = new ArrayList();
    	
    	return sqlParameterTemp;
    }

    public void setSqlParameter(@SuppressWarnings("rawtypes") List parameterSQL) {
        this.sqlParameter = parameterSQL;
    }

	public Pagging getPagging() {
		return pagging;
	}

	public void setPagging(Pagging pagging) {
		this.pagging = pagging;
	}

	public SearchConditionValues[] getConditionValuesBean() {
		return searchConditionValues;
	}

	public void setConditionValues(SearchConditionValues[] searchConditionValues) {
		this.searchConditionValues = searchConditionValues;
	}

	public String getSqlCustomCondition() {
		return sqlCustomCondition;
	}

	public void setSqlCustomCondition(String sqlCustomCondition) {
		this.sqlCustomCondition = sqlCustomCondition;
	}
}
