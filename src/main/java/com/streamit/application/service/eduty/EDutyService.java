package com.streamit.application.service.eduty;

import com.google.gson.Gson;
import com.streamit.application.dto.CorrectData;
import com.streamit.application.dto.CorrectDetail;
import com.streamit.application.dto.CorrectReq;
import com.streamit.others.constant.SQLConstantOperType;
import com.streamit.others.constant.SQLConstantWhereType;
import com.streamit.others.dao.InquiryDAO;
import com.streamit.others.jdbc.Pagging;
import com.streamit.others.jdbc.SearchConditionValues;
import com.streamit.others.jdbc.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface EDutyService {
    Map<String,Object> GetCorrectData(String type, CorrectReq req)throws Exception;
}

@Service
class EDutyServiceImp implements EDutyService {
    private Logger logger = LoggerFactory.getLogger(EDutyService.class);

    @Autowired
    private InquiryDAO edutyCorrectDataInquiryDAO;
    @Autowired
    private InquiryDAO edutyCorrectDetailInquiryDAO;

    public Map<String,Object> GetCorrectData(String type, CorrectReq req)throws Exception{
        Map<String,Object> result = new HashMap<>();
        logger.info("req={}",new Gson().toJson(req));

        if("data".equals(type.toLowerCase().trim())) {
            SearchCriteria searchCriteria = new SearchCriteria(new Pagging(req.getPaging().getPageNo(), req.getPaging().getPageSize()));
            List<SearchConditionValues> criterialList = new ArrayList<SearchConditionValues>();
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "BATCH_DATE", SQLConstantOperType.EQUALS, new Object[]{req.getBatchDate()}));
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "LOT_NAME", SQLConstantOperType.EQUALS, new Object[]{req.getLotName()}));
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "APPROVE_STATUS", SQLConstantOperType.EQUALS, new Object[]{req.getApproveStatus()}));

            //if(req.getCaseId() != null && !"".equals(req.getCaseId().trim())) {
            //    criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "CLAIM_ID", SQLConstantOperType.LIKE, new Object[]{"%" + req.getCaseId() + "%"}));
            //}

            searchCriteria.setConditionValues(criterialList.toArray(new SearchConditionValues[]{}));

            List<CorrectData> data = edutyCorrectDataInquiryDAO.findByPage(searchCriteria);
            //logger.info("data={}",new Gson().toJson(data));

            result.put("data", data);
            result.put("pagging", searchCriteria.getPagging());

        }else if("detail".equals(type.toLowerCase().trim())){
            SearchCriteria searchCriteria = new SearchCriteria();
            List<SearchConditionValues> criterialList = new ArrayList<SearchConditionValues>();
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "ID", SQLConstantOperType.EQUALS, new Object[]{req.getId()}));

            searchCriteria.setConditionValues(criterialList.toArray(new SearchConditionValues[]{}));

            List<CorrectDetail> data = edutyCorrectDetailInquiryDAO.findAll(searchCriteria);
            //logger.info("data={}",new Gson().toJson(data));

            result.put("data", data);
        }else if("detail-form".equals(type.toLowerCase().trim())){
            SearchCriteria searchCriteria = new SearchCriteria();
            List<SearchConditionValues> criterialList = new ArrayList<SearchConditionValues>();
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "ID", SQLConstantOperType.EQUALS, new Object[]{req.getId()}));

            searchCriteria.setConditionValues(criterialList.toArray(new SearchConditionValues[]{}));

            List<CorrectDetail> data = edutyCorrectDetailInquiryDAO.findAll(searchCriteria);
            //logger.info("data={}",new Gson().toJson(data));

            result.put("data", data);
        }



        return result;
    }


}
