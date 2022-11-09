package com.streamit.application.service.eduty;

import com.streamit.application.dto.*;
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

import java.util.*;

public interface EDutyService {
    Map<String,Object> CheckCorrectData(String type, CorrectReq req)throws Exception;
    Map<String,Object> CheckSendData(String type, CorrectReq req)throws Exception;
    Map<String,Object> CheckInvoiceData(String type, CorrectReq req)throws Exception;
    Map<String,Object> CheckReceiptFormData(String type, CorrectReq req)throws Exception;
}

@Service
class EDutyServiceImp implements EDutyService {
    private Logger logger = LoggerFactory.getLogger(EDutyService.class);

    @Autowired
    private InquiryDAO edutyCorrectDataInquiryDAO;
    @Autowired
    private InquiryDAO edutyCorrectDetailInquiryDAO;
    @Autowired
    private InquiryDAO edutyCorrectDetailFormInquiryDAO;
    @Autowired
    private InquiryDAO edutyCorrectDetailAddressInquiryDAO;
    @Autowired
    private InquiryDAO edutySendDataInquiryDAO;
    @Autowired
    private InquiryDAO edutySendDetailInquiryDAO;
    @Autowired
    private InquiryDAO edutyInvDataInquiryDAO;
    @Autowired
    private InquiryDAO edutyReceiptFormDataInquiryDAO;

    public Map<String,Object> CheckCorrectData(String type, CorrectReq req)throws Exception{
        Map<String,Object> result = new HashMap<>();
        //logger.info("req={}",new Gson().toJson(req));

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
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "SEQ", SQLConstantOperType.EQUALS, new Object[]{req.getNo()}));

            searchCriteria.setConditionValues(criterialList.toArray(new SearchConditionValues[]{}));

            List<CorrectDetailForm> details = edutyCorrectDetailFormInquiryDAO.findAll(searchCriteria);
            //logger.info("data={}",new Gson().toJson(data));
            CorrectDetailForm detail = new CorrectDetailForm();
            if(details.size() > 0){
                detail = details.get(0);
            }

            List<CorrectDetailAddress> detailAddresses = edutyCorrectDetailAddressInquiryDAO.findAll(searchCriteria);
            if(detailAddresses.size() > 0){
                detail.setAddress(detailAddresses.get(0));
            }



            result.put("data", new ArrayList<>(Arrays.asList(detail)));
        }



        return result;
    }


    public Map<String,Object> CheckSendData(String type, CorrectReq req)throws Exception{
        Map<String,Object> result = new HashMap<>();

        if("data".equals(type.toLowerCase().trim())) {
            SearchCriteria searchCriteria = new SearchCriteria(new Pagging(req.getPaging().getPageNo(), req.getPaging().getPageSize()));
            List<SearchConditionValues> criterialList = new ArrayList<SearchConditionValues>();
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "D1.BATCH_DATE", SQLConstantOperType.EQUALS, new Object[]{req.getBatchDate()}));
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "D1.LOT_NAME", SQLConstantOperType.EQUALS, new Object[]{req.getLotName()}));
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "D1.APPROVE_STATUS", SQLConstantOperType.EQUALS, new Object[]{req.getApproveStatus()}));
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "D1.PAYMENT_STATUS", SQLConstantOperType.EQUALS, new Object[]{req.getPaymentStatus()}));

            //if(req.getCaseId() != null && !"".equals(req.getCaseId().trim())) {
            //    criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "CLAIM_ID", SQLConstantOperType.LIKE, new Object[]{"%" + req.getCaseId() + "%"}));
            //}

            searchCriteria.setConditionValues(criterialList.toArray(new SearchConditionValues[]{}));

            List<SendRdData> data = edutySendDataInquiryDAO.findByPage(searchCriteria);
            //logger.info("data={}",new Gson().toJson(data));

            result.put("data", data);
            result.put("pagging", searchCriteria.getPagging());

        }else if("detail".equals(type.toLowerCase().trim())){
            SearchCriteria searchCriteria = new SearchCriteria();
            List<SearchConditionValues> criterialList = new ArrayList<SearchConditionValues>();
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "ID", SQLConstantOperType.EQUALS, new Object[]{req.getId()}));

            searchCriteria.setConditionValues(criterialList.toArray(new SearchConditionValues[]{}));

            List<SendRdDetail> data = edutySendDetailInquiryDAO.findAll(searchCriteria);
            //logger.info("data={}",new Gson().toJson(data));

            result.put("data", data);

        }

        return result;
    }

    public Map<String,Object> CheckInvoiceData(String type, CorrectReq req)throws Exception{
        Map<String,Object> result = new HashMap<>();

        if("data".equals(type.toLowerCase().trim())) {
            SearchCriteria searchCriteria = new SearchCriteria(new Pagging(req.getPaging().getPageNo(), req.getPaging().getPageSize()));
            List<SearchConditionValues> criterialList = new ArrayList<SearchConditionValues>();
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "BATCH_DATE", SQLConstantOperType.EQUALS, new Object[]{req.getBatchDate()}));
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "LOT_NAME", SQLConstantOperType.EQUALS, new Object[]{req.getLotName()}));
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "SEND_RD_STATUS", SQLConstantOperType.EQUALS, new Object[]{req.getSendRdStatus()}));
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "PAYMENT_STATUS", SQLConstantOperType.EQUALS, new Object[]{req.getPaymentStatus()}));

            //if(req.getCaseId() != null && !"".equals(req.getCaseId().trim())) {
            //    criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "CLAIM_ID", SQLConstantOperType.LIKE, new Object[]{"%" + req.getCaseId() + "%"}));
            //}

            searchCriteria.setConditionValues(criterialList.toArray(new SearchConditionValues[]{}));

            List<InvoiceData> data = edutyInvDataInquiryDAO.findByPage(searchCriteria);
            //logger.info("data={}",new Gson().toJson(data));

            result.put("data", data);
            result.put("pagging", searchCriteria.getPagging());

        }

        return result;
    }

    public Map<String,Object> CheckReceiptFormData(String type, CorrectReq req)throws Exception{
        Map<String,Object> result = new HashMap<>();

        if("data".equals(type.toLowerCase().trim())) {
            SearchCriteria searchCriteria = new SearchCriteria(new Pagging(req.getPaging().getPageNo(), req.getPaging().getPageSize()));
            List<SearchConditionValues> criterialList = new ArrayList<SearchConditionValues>();
            criterialList.add(new SearchConditionValues(SQLConstantWhereType.AND, "REF1", SQLConstantOperType.EQUALS, new Object[]{req.getRefNo()}));

            searchCriteria.setConditionValues(criterialList.toArray(new SearchConditionValues[]{}));

            List<ReceiptFormData> data = edutyReceiptFormDataInquiryDAO.findByPage(searchCriteria);
            //logger.info("data={}",new Gson().toJson(data));

            result.put("data", data);
            result.put("pagging", searchCriteria.getPagging());

        }

        return result;
    }
}
