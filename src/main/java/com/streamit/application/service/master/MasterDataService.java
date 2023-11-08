package com.streamit.application.service.master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.streamit.application.dto.master.MasterData;
import com.streamit.others.constant.SQLConstantOperType;
import com.streamit.others.constant.SQLConstantWhereType;
import com.streamit.others.dao.InquiryDAO;
import com.streamit.others.jdbc.SearchConditionValues;
import com.streamit.others.jdbc.SearchCriteria;

import lombok.extern.slf4j.Slf4j;

public interface MasterDataService {

	Map<String, Object> searchProvince(String locale, Map<String, Object> req);
	Map<String, Object> searchAmphure(String locale, Map<String, Object> req);
	Map<String, Object> searchTambon(String locale, Map<String, Object> req);
	Map<String, Object> searchByZipCode(String locale, Map<String, Object> req);

}

@Slf4j
@Service
class MasterDataServiceImp implements MasterDataService {
	
	@Autowired
	private InquiryDAO masterProvinceInquiryDAO;
	@Autowired
	private InquiryDAO masterAmphureInquiryDAO;
	@Autowired
	private InquiryDAO masterTambonsInquiryDAO;
	@Autowired
	private InquiryDAO masterByZipCodeInquiryDAO;
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchProvince(String locale, Map<String, Object> req) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String,Object>> list_data = new ArrayList<>();
		
		try {
			Integer id = null;
			
			if(req == null || req.get("id") == null) {
				
			}else {
				id = Integer.valueOf(req.get("id").toString());
			}
			
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.setSqlParameter(Arrays.asList(locale));
			
			if(id != null) {
				List<SearchConditionValues> criterialList = new ArrayList<SearchConditionValues>();
				criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "id", SQLConstantOperType.EQUALS, new Object[]{id}));
				searchCriteria.setConditionValues(criterialList.toArray(new SearchConditionValues[]{}));
			}
			
			List<MasterData> datas = masterProvinceInquiryDAO.findAll(searchCriteria);
			
			for (MasterData data : datas) {
				Map<String,Object> map = new HashMap<>();
				map.put("code1", data.getId());
				map.put("name1", data.getLabel());
				list_data.add(map);
			}
			
		}catch (Exception e) {
			log.error("province:{}",e.getMessage());
		}
		
		result.put("data", list_data);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchAmphure(String locale, Map<String, Object> req) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String,Object>> list_data = new ArrayList<>();
		try {
			Integer id = Integer.valueOf(req.get("id").toString());
			
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.setSqlParameter(Arrays.asList(locale,id));
			List<MasterData> datas = masterAmphureInquiryDAO.findAll(searchCriteria);
			
			for (MasterData data : datas) {
				Map<String,Object> map = new HashMap<>();
				map.put("code1", data.getId());
				map.put("name1", data.getLabel());
				list_data.add(map);
			}
			
		}catch (Exception e) {
			log.error("ampher:{}",e.getMessage());
		}
		
		result.put("data", list_data);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchTambon(String locale, Map<String, Object> req) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String,Object>> list_data = new ArrayList<>();
		try {
			Integer id = Integer.valueOf(req.get("id").toString());
			
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.setSqlParameter(Arrays.asList(locale,id));
			List<MasterData> datas = masterTambonsInquiryDAO.findAll(searchCriteria);
			
			for (MasterData data : datas) {
				Map<String,Object> map = new HashMap<>();
				map.put("code1", data.getId());
				String[] arrs = data.getLabel().split("\\|");
				map.put("name1", arrs[0]);
				map.put("code2", Integer.parseInt(arrs[1]));
				list_data.add(map);
				
				//log.info("{}",data.getLabel());
			}
			
		}catch (Exception e) {
			log.error("tambon:{}",e.getMessage());
		}
		
		result.put("data", list_data);
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> searchByZipCode(String locale, Map<String, Object> req) {
		Map<String, Object> result = new HashMap<>();
		List<Map<String,Object>> list_data = new ArrayList<>();
		try {
			Integer id = Integer.valueOf(req.get("id").toString());
			
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.setSqlParameter(Arrays.asList(locale,id));
			List<MasterData> datas = masterByZipCodeInquiryDAO.findAll(searchCriteria);
			
			for (MasterData data : datas) {
				Map<String,Object> map = new HashMap<>();
				map.put("code1", data.getId());
				
				//ทุ่งมหาเมฆ|1028|เขตสาทร|1|กรุงเทพมหานคร|10120
				String[] arrs = data.getLabel().split("\\|");
				map.put("name1", arrs[0]);
				map.put("code2", Integer.parseInt(arrs[1]));
				map.put("name2", arrs[2]);
				map.put("code3", Integer.parseInt(arrs[3]));
				map.put("name3", arrs[4]);
				map.put("code4", Integer.parseInt(arrs[5]));
				list_data.add(map);
				
				//log.info("{}",data.getLabel());
			}
			
		}catch (Exception e) {
			log.error("zipcode:{}",e.getMessage());
		}
		
		result.put("data", list_data);
		
		return result;
	}
}
