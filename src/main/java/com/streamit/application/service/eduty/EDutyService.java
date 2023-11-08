package com.streamit.application.service.eduty;

import com.google.gson.Gson;
import com.streamit.application.dto.*;
import com.streamit.application.utils.datasources.DataSourceService;
import com.streamit.others.constant.SQLConstantFildeType;
import com.streamit.others.constant.SQLConstantOperType;
import com.streamit.others.constant.SQLConstantWhereType;
import com.streamit.others.dao.InquiryDAO;
import com.streamit.others.jdbc.Pagging;
import com.streamit.others.jdbc.SearchConditionValues;
import com.streamit.others.jdbc.SearchCriteria;
import com.streamit.others.jdbc.StateValues;
import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

public interface EDutyService {
    Map<String,Object> CheckCorrectData(String type, CorrectReq req)throws Exception;
    Map<String,Object> CheckCorrectDataPut(CorrectDetailForm req)throws Exception;
    Map<String,Object> CheckSendData(String type, CorrectReq req)throws Exception;
    Map<String,Object> CheckInvoiceData(String type, CorrectReq req)throws Exception;
    Map<String,Object> CheckReceiptFormData(String type, CorrectReq req)throws Exception;
    Map<String,Object> InsertDuty(List<CorrectData> req)throws Exception;
    Map<String,Object> InsertDutyDetail(List<CorrectDetailForm> req)throws Exception;
    Map<String,Object> DeleteData(CorrectDetailForm req)throws Exception;
}

@Slf4j
@Service
class EDutyServiceImp implements EDutyService {


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
    @Autowired
    private DataSourceService dataSourceService;

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

    public Map<String,Object> CheckCorrectDataPut(CorrectDetailForm req)throws Exception{
        Map<String,Object> result = new HashMap<>();
        result.put("status",400);
        //log.info("req={}",new Gson().toJson(req));

        if(StringUtils.isEmpty(req.getId()) || StringUtils.isEmpty(req.getNo() == null ? "" : req.getNo().toString())){
            result.put("error","Bad Request : id or no");
        }else {
            List<StateValues> stateList = new ArrayList<>();
            stateList.add(new StateValues(SQLConstantFildeType.STRING, "INST_INFO_ID", SQLConstantOperType.EQUALS, req.getInstInfoId()));
            stateList.add(new StateValues(SQLConstantFildeType.STRING, "TAX_PAYER_ID", SQLConstantOperType.EQUALS, req.getTaxPayerId()));
            String[] arr_name = req.getFullName().split(" ");
            stateList.add(new StateValues(SQLConstantFildeType.STRING, "FIRST_NAME", SQLConstantOperType.EQUALS, arr_name[0]));
            stateList.add(new StateValues(SQLConstantFildeType.STRING, "LAST_NAME", SQLConstantOperType.EQUALS, arr_name[1]));
            stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "TOTAL_DUTY", SQLConstantOperType.EQUALS, req.getTotalDuty()));
            stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "TOTAL_DUB_DUTY_AMOUNT", SQLConstantOperType.EQUALS, req.getTotalDubDutyAmount()));
            stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "TOTAL_PAYMENT", SQLConstantOperType.EQUALS, req.getTotalPayment()));
            stateList.add(new StateValues(SQLConstantFildeType.STRING, "CONTRACT_NO", SQLConstantOperType.EQUALS, req.getContractNo()));
            stateList.add(new StateValues(SQLConstantFildeType.DATE, "CONTRACT_START_DATE", SQLConstantOperType.EQUALS, req.getContractStartDate()));
            stateList.add(new StateValues(SQLConstantFildeType.DATE, "CONTRACT_END_DATE", SQLConstantOperType.EQUALS, req.getContractEndDate()));
            stateList.add(new StateValues(SQLConstantFildeType.STRING, "BRANCH_NO", SQLConstantOperType.EQUALS, req.getBranchNo()));
            stateList.add(new StateValues(SQLConstantFildeType.STRING, "BRANCH_TYPE", SQLConstantOperType.EQUALS, req.getBranchType()));
            stateList.add(new StateValues(SQLConstantFildeType.STRING, "CONTRACT_WITH", SQLConstantOperType.EQUALS, req.getContractWith()));
            stateList.add(new StateValues(SQLConstantFildeType.DATE, "PAYMENT_END_DATE", SQLConstantOperType.EQUALS, req.getPaymentEndDate()));
            stateList.add(new StateValues(SQLConstantFildeType.STRING, "ID", SQLConstantOperType.WHERE, req.getId()));
            stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "SEQ", SQLConstantOperType.WHERE, req.getNo()));

            Map<String, Object> result1 = dataSourceService.update("DUTY_STAMP_DETAIL", stateList);

            CorrectDetailAddress addReq = req.getAddress();

            List<StateValues> addStateList = new ArrayList<>();
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "PREFIX_CODE", SQLConstantOperType.EQUALS, addReq.getPrefixCode()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "PREFIX", SQLConstantOperType.EQUALS, addReq.getPrefix()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "FIRST_NAME", SQLConstantOperType.EQUALS, addReq.getFirstName()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "LAST_NAME", SQLConstantOperType.EQUALS, addReq.getLastName()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "ADDR_NUMBER", SQLConstantOperType.EQUALS, addReq.getAddrNumber()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "MOO", SQLConstantOperType.EQUALS, addReq.getMoo()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "MOOBAN", SQLConstantOperType.EQUALS, addReq.getMooban()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "SOI", SQLConstantOperType.EQUALS, addReq.getSoi()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "ROAD", SQLConstantOperType.EQUALS, addReq.getRoad()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "TAMBOL_CODE", SQLConstantOperType.EQUALS, addReq.getTambolCode()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "TAMBOL", SQLConstantOperType.EQUALS, addReq.getTambol()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "AMPHUR_CODE", SQLConstantOperType.EQUALS, addReq.getAmphurCode()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "AMPHUR", SQLConstantOperType.EQUALS, addReq.getAmphur()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "PROVINCE_CODE", SQLConstantOperType.EQUALS, addReq.getProvinceCode()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "PROVINCE", SQLConstantOperType.EQUALS, addReq.getProvince()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "ZIPCODE", SQLConstantOperType.EQUALS, addReq.getZipcode()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "ID", SQLConstantOperType.WHERE, req.getId()));
            addStateList.add(new StateValues(SQLConstantFildeType.NUMBER, "SEQ", SQLConstantOperType.WHERE, req.getNo()));

            Map<String, Object> result2 = dataSourceService.update("ADDRESS", addStateList);

            log.info("result1: {} result2: {}", result1.get("status"), result2.get("status"));

            Boolean check1 = (Integer)result1.get("status") == 200;
            Boolean check2 = (Integer)result2.get("status") == 200;

            if(check1 && check2){
                result.put("status",200);
            }else {

                if(!check1)
                    result.put("error", result1.get("error"));
                else
                    result.put("error", result2.get("error"));

            }


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

    public Map<String,Object> InsertDuty(List<CorrectData> list)throws Exception{
        Map<String,Object> result = new HashMap<>();
        result.put("status",400);
        List<Map<String,Object>> list_check = new ArrayList<>();
        
        //log.info("list={}",new Gson().toJson(list));

        for(CorrectData req : list) {
        	
        	Map<String,Object> check = new HashMap<>();
        	check.put("status",400);
        	check.put("id",req.getId());
        	
            if(StringUtils.isEmpty(req.getId())){
                result.put("error","Bad Request : id");
            }else {
                List<StateValues> stateList = new ArrayList<>();
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "ID", SQLConstantOperType.EQUALS, req.getId()));
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "LOT_NAME", SQLConstantOperType.EQUALS, req.getLotName()));
                stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "TOTAL_DOC", SQLConstantOperType.EQUALS, req.getTotalDoc()));
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "APPROVE_STATUS", SQLConstantOperType.EQUALS, req.getApproveStatus()));
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "APPROVE_BY", SQLConstantOperType.EQUALS, req.getApproveBy()));
                stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "TOTAL_DUTY", SQLConstantOperType.EQUALS, req.getTotalDuty()));
                stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "TOTAL_DUB_DUTY_AMOUNT", SQLConstantOperType.EQUALS, req.getTotalDubDutyAmount()));
                stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "TOTAL_PAYMENT", SQLConstantOperType.EQUALS, req.getTotalPayment()));
                
                Map<String, Object> result1 = dataSourceService.insert("DUTY_STAMP", stateList);

                Boolean check1 = (Integer)result1.get("status") == 200;
                
                if(check1){
                    result.put("status",200);
                    check.put("status",200);
                }else {
                	result.put("error", result1.get("error"));
                }

            }
            
            list_check.add(check);
        }
        
        result.put("check",list_check);

        return result;
    }
    
    public Map<String,Object> InsertDutyDetail(List<CorrectDetailForm> list)throws Exception{
        Map<String,Object> result = new HashMap<>();
        result.put("status",400);
        //log.info("list={}",new Gson().toJson(list));

        for(CorrectDetailForm req : list) {
            if(StringUtils.isEmpty(req.getId()) || StringUtils.isEmpty(req.getNo() == null ? "" : req.getNo().toString())){
                result.put("error","Bad Request : id or no");
            }else {
                List<StateValues> stateList = new ArrayList<>();
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "ID", SQLConstantOperType.EQUALS, req.getId()));
                stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "SEQ", SQLConstantOperType.EQUALS, req.getNo()));
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "INST_INFO_ID", SQLConstantOperType.EQUALS, req.getInstInfoId()));
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "TAX_PAYER_ID", SQLConstantOperType.EQUALS, req.getTaxPayerId()));
                String[] arr_name = req.getFullName().split(" ");
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "FIRST_NAME", SQLConstantOperType.EQUALS, arr_name[0]));
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "LAST_NAME", SQLConstantOperType.EQUALS, arr_name[1]));
                stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "TOTAL_DUTY", SQLConstantOperType.EQUALS, req.getTotalDuty()));
                stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "TOTAL_DUB_DUTY_AMOUNT", SQLConstantOperType.EQUALS, req.getTotalDubDutyAmount()));
                stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "TOTAL_PAYMENT", SQLConstantOperType.EQUALS, req.getTotalPayment()));
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "CONTRACT_NO", SQLConstantOperType.EQUALS, req.getContractNo()));
                stateList.add(new StateValues(SQLConstantFildeType.DATE, "CONTRACT_START_DATE", SQLConstantOperType.EQUALS, req.getContractStartDate()));
                stateList.add(new StateValues(SQLConstantFildeType.DATE, "CONTRACT_END_DATE", SQLConstantOperType.EQUALS, req.getContractEndDate()));
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "BRANCH_NO", SQLConstantOperType.EQUALS, req.getBranchNo()));
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "BRANCH_TYPE", SQLConstantOperType.EQUALS, req.getBranchType()));
                stateList.add(new StateValues(SQLConstantFildeType.STRING, "CONTRACT_WITH", SQLConstantOperType.EQUALS, req.getContractWith()));
                stateList.add(new StateValues(SQLConstantFildeType.DATE, "PAYMENT_END_DATE", SQLConstantOperType.EQUALS, req.getPaymentEndDate()));

                Map<String, Object> result1 = dataSourceService.insert("DUTY_STAMP_DETAIL", stateList);
                Map<String, Object> result2 = new HashMap<>();

                Boolean check1 = (Integer)result1.get("status") == 200;
                Boolean check2 = false;

                if(check1) {
                	
                	result2 = this.InsertDetailAddress(req.getAddress(), req.getId(), req.getNo());
                	
                    check2 = (Integer)result2.get("status") == 200;
                }

                //log.info("result1: {} result2: {}", result1.get("status"), result2.get("status"));


                if(check1 && check2){
                    result.put("status",200);
                }else {

                    if(!check1)
                        result.put("error", result1.get("error"));
                    else
                        result.put("error", result2.get("error"));

                }

            }
        }
        
        

        return result;
    }
    
    public Map<String,Object> InsertDetailAddress(CorrectDetailAddress addReq, String id, Integer no)throws Exception{
        Map<String,Object> result = new HashMap<>();
        result.put("status",400);
        //log.info("list={}",new Gson().toJson(addReq));

        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(no == null ? "" : no.toString())){
            result.put("error","Bad Request : id or no");
        }else {
            List<StateValues> addStateList = new ArrayList<>();
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "ID", SQLConstantOperType.EQUALS, id));
            addStateList.add(new StateValues(SQLConstantFildeType.NUMBER, "SEQ", SQLConstantOperType.EQUALS, no));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "PREFIX_CODE", SQLConstantOperType.EQUALS, addReq.getPrefixCode()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "PREFIX", SQLConstantOperType.EQUALS, addReq.getPrefix()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "FIRST_NAME", SQLConstantOperType.EQUALS, addReq.getFirstName()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "LAST_NAME", SQLConstantOperType.EQUALS, addReq.getLastName()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "ADDR_NUMBER", SQLConstantOperType.EQUALS, addReq.getAddrNumber()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "MOO", SQLConstantOperType.EQUALS, addReq.getMoo()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "MOOBAN", SQLConstantOperType.EQUALS, addReq.getMooban()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "SOI", SQLConstantOperType.EQUALS, addReq.getSoi()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "ROAD", SQLConstantOperType.EQUALS, addReq.getRoad()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "TAMBOL_CODE", SQLConstantOperType.EQUALS, addReq.getTambolCode()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "TAMBOL", SQLConstantOperType.EQUALS, addReq.getTambol()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "AMPHUR_CODE", SQLConstantOperType.EQUALS, addReq.getAmphurCode()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "AMPHUR", SQLConstantOperType.EQUALS, addReq.getAmphur()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "PROVINCE_CODE", SQLConstantOperType.EQUALS, addReq.getProvinceCode()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "PROVINCE", SQLConstantOperType.EQUALS, addReq.getProvince()));
            addStateList.add(new StateValues(SQLConstantFildeType.STRING, "ZIPCODE", SQLConstantOperType.EQUALS, addReq.getZipcode()));

            Map<String, Object> result1 = dataSourceService.insert("ADDRESS", addStateList);

            Boolean check1 = (Integer)result1.get("status") == 200;
            

            //log.info("result1: {} result2: {}", result1.get("status"), result2.get("status"));


            if(check1){
                result.put("status",200);
            }else {
            	result.put("error", result1.get("error"));
            }

        }
        
        
        

        return result;
    }

    public Map<String,Object> DeleteData(CorrectDetailForm req)throws Exception{
        Map<String,Object> result = new HashMap<>();
        result.put("status",400);
        //log.info("req={}",new Gson().toJson(req));

        if(StringUtils.isEmpty(req.getId()) || StringUtils.isEmpty(req.getNo() == null ? "" : req.getNo().toString())){
            result.put("error","Bad Request : id or no");
        }else {
            List<StateValues> stateList = new ArrayList<>();
            stateList.add(new StateValues(SQLConstantFildeType.STRING, "ID", SQLConstantOperType.WHERE, req.getId()));
            stateList.add(new StateValues(SQLConstantFildeType.NUMBER, "SEQ", SQLConstantOperType.WHERE, req.getNo()));


            Map<String, Object> result1 = dataSourceService.delete("DUTY_STAMP_DETAIL", stateList);
            Map<String, Object> result2 = new HashMap<>();

            Boolean check1 = (Integer)result1.get("status") == 200;
            Boolean check2 = false;

            if(check1) {
                List<StateValues> addStateList = new ArrayList<>();
                addStateList.add(new StateValues(SQLConstantFildeType.STRING, "ID", SQLConstantOperType.WHERE, req.getId()));
                addStateList.add(new StateValues(SQLConstantFildeType.NUMBER, "SEQ", SQLConstantOperType.WHERE, req.getNo()));

                result2 = dataSourceService.delete("ADDRESS", addStateList);

                check2 = (Integer)result2.get("status") == 200;
            }

            //log.info("result1: {} result2: {}", result1.get("status"), result2.get("status"));


            if(check1 && check2){
                result.put("status",200);
            }else {

                if(!check1)
                    result.put("error", result1.get("error"));
                else
                    result.put("error", result2.get("error"));

            }

        }

        return result;
    }
}
