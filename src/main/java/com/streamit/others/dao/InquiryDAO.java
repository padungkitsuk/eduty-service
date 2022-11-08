package com.streamit.others.dao;

import java.util.List;

import com.streamit.others.jdbc.DataBo;
import com.streamit.others.jdbc.SearchCriteria;


public interface InquiryDAO {
	@SuppressWarnings("rawtypes")
	public List listAll()throws Exception;
    @SuppressWarnings("rawtypes")
	public List listAll(SearchCriteria searchCriteria)throws Exception;
    @SuppressWarnings("rawtypes")
    public DataBo find(SearchCriteria searchCriteria) throws Exception;
	public List findAll(SearchCriteria searchCriteria) throws Exception;  
	Integer update(Object[] params) throws Exception;
	public List findByPage(SearchCriteria searchCriteria) throws Exception;
}
