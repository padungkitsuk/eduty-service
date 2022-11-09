package com.streamit.application.controller;

import com.streamit.application.dto.*;
import com.streamit.application.service.eduty.EDutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.streamit.application.service.rd.RdService;

import java.util.Map;

@SpringBootApplication
@RestController
//@CrossOrigin
@RequestMapping("/e-duty")
public class EDutyController {

    @Autowired
    private EDutyService eDutyService;
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

    @PostMapping("/correct/{type}")
    public ResponseEntity<Map<String,Object>> correctData(@PathVariable("type") String type, @RequestBody CorrectReq req) throws Exception{
        return new ResponseEntity<>(eDutyService.CheckCorrectData(type,req), HttpStatus.OK) ;
    }

    @PostMapping("/send-rd/{type}")
    public ResponseEntity<Map<String,Object>> sendData(@PathVariable("type") String type, @RequestBody CorrectReq req) throws Exception{
        return new ResponseEntity<>(eDutyService.CheckSendData(type,req), HttpStatus.OK) ;
    }

    @PostMapping("/invoice/{type}")
    public ResponseEntity<Map<String,Object>> invoiceData(@PathVariable("type") String type, @RequestBody CorrectReq req) throws Exception{
        return new ResponseEntity<>(eDutyService.CheckInvoiceData(type,req), HttpStatus.OK) ;
    }

    @PostMapping("/receipt/{type}")
    public ResponseEntity<Map<String,Object>> receiptData(@PathVariable("type") String type, @RequestBody CorrectReq req) throws Exception{
        return new ResponseEntity<>(eDutyService.CheckReceiptFormData(type,req), HttpStatus.OK) ;
    }
}
