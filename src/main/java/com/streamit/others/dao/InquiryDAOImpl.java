package com.streamit.others.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.streamit.others.jdbc.DataBo;
import com.streamit.others.jdbc.JDBCSQLSearchConditionHelper;
import com.streamit.others.jdbc.SearchCriteria;

public class InquiryDAOImpl extends JdbcDaoSupport implements InquiryDAO, Serializable {
	private static final long serialVersionUID = 1L;
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private String sql;
	private String classMapping;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List listAll() throws Exception {
		List resultList = getJdbcTemplate().query(sql, new BeanPropertyRowMapper(Class.forName(classMapping)));
		logger.info("result size:" + resultList.size());

		System.out.println(sql);

		return resultList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List listAll(SearchCriteria searchCriteria) throws Exception {
		List param = searchCriteria.getSqlParameter();
		String sqlCondition = JDBCSQLSearchConditionHelper
				.getSQLSearchCondition(searchCriteria.getConditionValuesBean());
		String orderBy = JDBCSQLSearchConditionHelper.getSQLOrderBy(searchCriteria);
		String groupBy = JDBCSQLSearchConditionHelper.getSQLGroupBy(searchCriteria);

		String sqlStr = sql + sqlCondition + groupBy + orderBy;
		logger.info("sql.." + sqlStr);
		logger.info("param.." + param);

		/*** MYSQL EXCUTE SQL PAGGING ****/
		sqlStr = "SELECT @ROW_NUMNER:=@ROW_NUMNER+1 AS NO,QUERY1.* FROM (" + sqlStr
				+ ")QUERY1, (SELECT @ROW_NUMNER:=0)QUERY2";

		List resultList = getJdbcTemplate().query(sqlStr, param.toArray(),
				new BeanPropertyRowMapper(Class.forName(classMapping)));
		searchCriteria.getPagging().setTotalRow(resultList.size());
		logger.info("result size:" + resultList.size());

		return resultList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List findAll(SearchCriteria searchCriteria) throws Exception {
		List param = searchCriteria.getSqlParameter();
		String sqlCondition = JDBCSQLSearchConditionHelper
				.getSQLSearchCondition(searchCriteria.getConditionValuesBean());
		String orderBy = JDBCSQLSearchConditionHelper.getSQLOrderBy(searchCriteria);
		String groupBy = JDBCSQLSearchConditionHelper.getSQLGroupBy(searchCriteria);

		String sqlStr = sql + sqlCondition + groupBy + orderBy;
//		logger.info("sql.." + sqlStr);
//		logger.info("param.." + param);

		/*** MYSQL EXCUTE SQL PAGGING ****/
	
		List resultList = getJdbcTemplate().query(sqlStr, param.toArray(),
				new BeanPropertyRowMapper(Class.forName(classMapping)));
		searchCriteria.getPagging().setTotalRow(resultList.size());
//		logger.info("result size:" + resultList.size());

		return resultList;
	}
	
	
	@Override
	public DataBo find(SearchCriteria searchBean) throws Exception {

		return null;
	}

	// ********** setter,getter **************//
	public void setClassMapping(String classMapping) {
		this.classMapping = classMapping;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
	@Override
	public Integer update(Object[] param) throws Exception {

		logger.info("Update Sql... "+sql);
		Integer result  =  getJdbcTemplate().update(sql,param);
		logger.info("Reset row => {}" + result.toString());
		return result;
	}

	@Override
	public List findByPage(SearchCriteria searchBean) throws Exception {
		Long startTime = System.currentTimeMillis();
//		logger.info("findByPage|START|{}", DateUtil.getCurrentYearTime(Locale.US));
		List resultList = new ArrayList();
		List param = searchBean.getSqlParameter();

		String sqlCondition = JDBCSQLSearchConditionHelper.getSQLSearchCondition(searchBean.getConditionValuesBean());
		String orderBy = JDBCSQLSearchConditionHelper.getSQLOrderBy(searchBean);
		String groupBy = JDBCSQLSearchConditionHelper.getSQLGroupBy(searchBean);

		String sqlStr = sql + sqlCondition + groupBy + orderBy;
		//logger.info("sqlStr.." + sqlStr);
		//logger.info("param.." + param);

		// sqlStr = sqlStr.replaceAll("\n", "").replaceAll("\t", "");
		String sqlCount = "select count(*) from(" + sqlStr + ")AS sub_query";

		int pageNo = searchBean.getPagging().getPageNo();
		final int pageSize = searchBean.getPagging().getPageSize();
		@SuppressWarnings("deprecation") final int totalRow = getJdbcTemplate().queryForObject(sqlCount, param.toArray(), int.class);

		int totalPage = totalRow / pageSize;

		if (totalRow > pageSize * totalPage) {
			totalPage++;
		}

		if (pageNo > totalPage)
			pageNo = 1;

		final int startRow = (pageNo - 1) * pageSize;
		if (totalRow > 0) {
			if (orderBy.equals(""))
				sqlStr = sqlStr + " ORDER BY 1 ";

			sqlStr += "OFFSET " + startRow + " " + " ROW fetch next " + pageSize + "row only";
			resultList = getJdbcTemplate().query(sqlStr, param.toArray(), new BeanPropertyRowMapper(Class.forName(classMapping)));
		}

		// *** set variable to class call ****//
		searchBean.getPagging().setPageNo(pageNo);
		searchBean.getPagging().setPageSize(pageSize);
		searchBean.getPagging().setTotalPage(totalPage);
		searchBean.getPagging().setTotalRow(totalRow);

		//logger.info("findByPage|END|result size: {} resp: {}" ,resultList.size(), System.currentTimeMillis() - startTime);

		return resultList;
	}

}