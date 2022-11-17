package com.streamit.application.dto;

import lombok.Data;

import java.util.Date;

@Data
public class InstInfoSubmitReq {

    private String id;
    private String contractNo;
    private Date creationDate;
    private Date effectiveDate;
    private Date expireDate;
    private String expireDateText;
    private Date receiveDate;
    private String sendFormType;
    private Integer filingNo;
    private String overdueFlag;
    private Integer dupNumber;
    private RelateInstInfo relateInstInfo;
    private Double instAmount;
    private TaxPayer taxPayer;
    private Party party;
    private AttachDetail attachDetail;
    private RelateParty relateParty;
    private RelateContract relateContract;
    private Payment payment;



    public InstInfoSubmitReq() {}

    public InstInfoSubmitReq(String id, String contractNo, Date creationDate, Date effectiveDate, Date expireDate, String expireDateText, Date receiveDate, String sendFormType, Integer filingNo, String overdueFlag, Integer dupNumber, RelateInstInfo relateInstInfo, Double instAmount, TaxPayer taxPayer, Party party, AttachDetail attachDetail, RelateParty relateParty, RelateContract relateContract, Payment payment) {
        this.id = id;
        this.contractNo = contractNo;
        this.creationDate = creationDate;
        this.effectiveDate = effectiveDate;
        this.expireDate = expireDate;
        this.expireDateText = expireDateText;
        this.receiveDate = receiveDate;
        this.sendFormType = sendFormType;
        this.filingNo = filingNo;
        this.overdueFlag = overdueFlag;
        this.dupNumber = dupNumber;
        this.relateInstInfo = relateInstInfo;
        this.instAmount = instAmount;
        this.taxPayer = taxPayer;
        this.party = party;
        this.attachDetail = attachDetail;
        this.relateParty = relateParty;
        this.relateContract = relateContract;
        this.payment = payment;
    }
}
