package com.streamit.application.controller;

import com.streamit.application.dto.*;
import com.streamit.application.service.eduty.EDutyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@SpringBootApplication
@RestController
@CrossOrigin
@RequestMapping("")
public class EDutyController {

    @Autowired
    private EDutyService eDutyService;


    @PostMapping("/correct/{type}")
    public ResponseEntity<Map<String,Object>> correctData(@PathVariable("type") String type, @RequestBody CorrectReq req) throws Exception{
        return new ResponseEntity<>(eDutyService.CheckCorrectData(type,req), HttpStatus.OK) ;
    }

    @PutMapping("/correct/detail-form")
    public ResponseEntity<Map<String,Object>> correctDataPut(@RequestBody CorrectDetailForm req) throws Exception{
        return new ResponseEntity<>(eDutyService.CheckCorrectDataPut(req), HttpStatus.OK) ;
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

    @PostMapping("/correct/test-insert")
    public ResponseEntity<Map<String,Object>> testInsert(@RequestBody CorrectDetailForm req) throws Exception{
        return new ResponseEntity<>(eDutyService.TestInsertData(req), HttpStatus.OK) ;
    }

    @DeleteMapping("/correct/test-delete")
    public ResponseEntity<Map<String,Object>> testDelete(@RequestBody CorrectDetailForm req) throws Exception{
        return new ResponseEntity<>(eDutyService.TestDeleteData(req), HttpStatus.OK) ;
    }
}
