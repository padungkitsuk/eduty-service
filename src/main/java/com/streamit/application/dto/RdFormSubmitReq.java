package com.streamit.application.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RdFormSubmitReq {

    private DocumentDetail documentDetail;
    private ArrayList<InstInfoSubmitReq> instInfo;
    private Summary summary;


    public RdFormSubmitReq() {
	}


	public RdFormSubmitReq(DocumentDetail documentDetail, ArrayList<InstInfoSubmitReq> instInfo, Summary summary) {
		this.documentDetail = documentDetail;
		this.instInfo = instInfo;
		this.summary = summary;
	}




}
