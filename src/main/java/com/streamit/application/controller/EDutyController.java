package com.streamit.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamit.application.dto.AuthRes;
import com.streamit.application.dto.EInstrumentCheckFile;
import com.streamit.application.dto.EInstrumentCheckPay;
import com.streamit.application.dto.EInstrumentReceipt;
import com.streamit.application.dto.EInstrumentSubmit;
import com.streamit.application.service.rd.RdService;

@SpringBootApplication
@RestController
@CrossOrigin
@RequestMapping("/e-duty")
public class EDutyController {

    @Autowired
    private RdService rdService;

    @PostMapping("/submitFilingAuth")
    public ResponseEntity<AuthRes> SubmitFilingAuth() throws Exception{
        return new ResponseEntity<>(rdService.SubmitFilingAuth(), HttpStatus.OK) ;
    }

    @PostMapping("/submitFiling")
    public ResponseEntity<EInstrumentSubmit> SubmitFiling() throws Exception{
        return new ResponseEntity<>(rdService.SubmitFiling(), HttpStatus.OK) ;
    }

    @PostMapping("/checkFilingStatus")
    public ResponseEntity<EInstrumentCheckFile> CheckFilingStatus() throws Exception{
        return new ResponseEntity<>(rdService.CheckFilingStatus(), HttpStatus.OK) ;
    }

    @PostMapping("/checkPaymentStatus")
    public ResponseEntity<EInstrumentCheckPay> CheckPaymentStatus() throws Exception{
        return new ResponseEntity<>(rdService.CheckPaymentStatus(), HttpStatus.OK) ;
    }

    @PostMapping("/reqFormReceiptFile")
    public ResponseEntity<EInstrumentReceipt> ReqFormReceiptFile() throws Exception{
        return new ResponseEntity<>(rdService.ReqFormReceiptFile(), HttpStatus.OK) ;
    }
}
