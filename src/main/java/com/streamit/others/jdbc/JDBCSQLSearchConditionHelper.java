package com.streamit.others.jdbc;

import com.streamit.others.constant.SQLConstantOperType;
import com.streamit.others.constant.SQLConstantWhereType;


public class JDBCSQLSearchConditionHelper {
	
	public static String getSQLSearchCondition(SearchConditionValues[]conditionValues){
		StringBuilder result = new StringBuilder();
		if (conditionValues != null) {
			for(int i=0; i<conditionValues.length && conditionValues[i]!=null; i++){ 
				
				if(conditionValues[i].getValues()!=null && conditionValues[i].getValues().length>0){

					if(conditionValues[i].getValues()[0] instanceof Boolean){
						if(!(Boolean)conditionValues[i].getValues()[0]){
							continue;
						}
					}
					
					//**** check between or ****//
					if(conditionValues[i].getWhereType().equals(SQLConstantWhereType.AND_BETWEEN_BEGIN))
						result.append("AND ( ");	
					
					if(conditionValues[i].getValues()[0]!=null && !conditionValues[i].getValues()[0].equals("") && !conditionValues[i].getValues()[0].equals("NULL") && !conditionValues[i].getValues()[0].equals("null")){						
						
						//**** check between or ****//
						if(!conditionValues[i].getWhereType().equals(SQLConstantWhereType.AND_BETWEEN_BEGIN) && !conditionValues[i].getWhereType().equals(SQLConstantWhereType.AND_BETWEEN) && !conditionValues[i].getWhereType().equals(SQLConstantWhereType.AND_BETWEEN_END)){
							result.append(conditionValues[i].getWhereType());
						}else if(conditionValues[i].getWhereType().equals(SQLConstantWhereType.AND_BETWEEN) || conditionValues[i].getWhereType().equals(SQLConstantWhereType.AND_BETWEEN_END)){
							result.append(" OR ");	
						}
					
						result.append(" (");	
						result.append(" ");			
						result.append(conditionValues[i].getFiledName());
						result.append(" ");					
						result.append(conditionValues[i].getOperatorType());			
						
						if(conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.IS_NULL) || conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.IS_NOT_NULL)){
							
						}else if(conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.BETWEEN) || conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.NOT_BETWEEN)){
							result.append(" (?) AND (?) ");	
						
						}else if(conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.NOT_EQUALS) || conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.NOT_LIKE)){
							result.append(" (?) ");	
							for(int j=1; j<conditionValues[i].getValues().length;j++){
								result.append(" AND ");	
								result.append(conditionValues[i].getFiledName());
								result.append(" ");					
								result.append(conditionValues[i].getOperatorType());
								result.append(" (?) ");	
							}
							
						}else if(conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.EQUALS) 			||
								conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.MORE_DAN) 		|| 
								conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.MORE_DAN_EQUALS) 	|| 
								conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.LESS_DAN) 		|| 
								conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.LESS_DAN_EQUALS) 	|| 

								conditionValues[i].getOperatorType().equalsIgnoreCase(SQLConstantOperType.LIKE)){					
							result.append(" (?) ");	
							for(int j=1; j<conditionValues[i].getValues().length;j++){
								result.append(" OR ");	
								result.append(conditionValues[i].getFiledName());
								result.append(" ");					
								result.append(conditionValues[i].getOperatorType());
								result.append(" (?) ");	
							}
						}		
						
						result.append(")\r\n");
						
						//**** check between or ****//
						if(conditionValues[i].getWhereType().equals(SQLConstantWhereType.AND_BETWEEN_END)){
							result.append(" )\r\n");	
						}						
					}
				}
			}				
		}
				
		return result.toString();
	}	
	
    public static String getSQLOrderBy(SearchCriteria searchCriteria)throws Exception{ 
        StringBuilder result = new StringBuilder();
        if (searchCriteria.getOrderBy()!=null && !searchCriteria.getOrderBy().equals("")){   
            result.append("ORDER BY ");
            result.append(searchCriteria.getOrderBy());
            result.append(" ");
            result.append(searchCriteria.getOrderType());
            result.append(" ");
            result.append("\r\n");
        }
        
        return result.toString();
    }
    
    public static String getSQLGroupBy(SearchCriteria searchCriteria)throws Exception{ 
        StringBuilder result = new StringBuilder();
        if (searchCriteria.getGroupBy()!=null && !searchCriteria.getGroupBy().equals("")){   
            result.append("GROUP BY ");
            result.append(searchCriteria.getGroupBy());
            result.append(" ");
            result.append("\r\n");
        }
        
        return result.toString();
    }
    
    
    
}
