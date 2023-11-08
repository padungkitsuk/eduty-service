package com.streamit.application.controller;

import com.google.gson.Gson;
import com.streamit.application.dto.*;
import com.streamit.application.service.jwt.JWebToken;
import com.streamit.application.simulate.data.RDSimulate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
@RestController
@CrossOrigin
@RequestMapping("/rd-test")
public class RDTestController {

    @Autowired
    private RDSimulate rdSimulate;

    @PostMapping("/submitFilingAuth")
    public ResponseEntity<AuthRes> SubmitFilingAuth(@RequestBody AuthReq req) throws Exception{
        //System.out.println(req);
        AuthRes res = new AuthRes();

        //moc data test
        if("USER0001".equals(req.getUsername().toUpperCase().trim())){
            String token = new JWebToken(new Gson().toJson(req), null).getToken();
            res = new AuthRes("I07000","สำเร็จ","USER0001",token,new AuthResSender("abc1234"));
        }else{
            res = new AuthRes("E07103","ไม่สามารถเข้าสู่ระบบได้ เนื่องจากรหัสผู้ใช้ หรือรหัสผ่านไม่ถูกต้อง",null,null,null);
        }

        return new ResponseEntity<>(res, HttpStatus.OK) ;
    }

    @PostMapping("/submitFiling")
    public ResponseEntity<SubmitRes> SubmitFiling() throws Exception{

        SubmitRes res = new SubmitRes(
                new EInstrumentSubmit(
                        "I07000",
                        "สำเร็จ",
                        new RdFormSubmit(
                                "Test123400003",
                                "20190916000000007181",
                                new ArrayList<InstInfo>(
                                        Arrays.asList(
                                                new InstInfo(
                                                        "Test123400001",
                                                        "P0900022091",
                                                        new SpecifiedTaxRegistration("1111111111119")
                                                )
                                        )
                                ),
                                new PaymentInfo(
                                        "1111111111119",
                                        "160051308330972",
                                        new SimpleDateFormat("yyyy-MM-dd").parse("2019-09-16"),
                                        20000.0,
                                        20000.0,
                                        0.0,
                                        0.0,
                                        "",
                                        ""
                                )
                        )
                )
        );

        return new ResponseEntity<>(res, HttpStatus.OK) ;
    }

    @PostMapping("/checkFilingStatus")
    public ResponseEntity<CheckFileRes> CheckFilingStatus() throws Exception{
        CheckFileRes res = new CheckFileRes(
                new EInstrumentCheckFile(
                        "I07000",
                        "สำเร็จ",
                        new RdFormCheckFile(
                                "20190916000000007182",
                                new ArrayList<InstInfo>(
                                        Arrays.asList(
                                                new InstInfo(
                                                        "Test123400001",
                                                        "P0900022091",
                                                        new SpecifiedTaxRegistration("1111111111119")
                                                )
                                        )
                                ),
                                new PaymentInfo(
                                        "1111111111119",
                                        "160051308330972",
                                        new SimpleDateFormat("yyyy-MM-dd").parse("2019-09-16"),
                                        20000.0,
                                        20000.0,
                                        0.0,
                                        0.0,
                                        "ZUluc3RydW1lbnQKICBzZW5kZXJDb2RlID0gYWJjMTIzNAogIHNwZWNpZmllZFRheFJlZ2lzdHJhdGlvbgogICAgaWQgPSAzWDEwMDM2NTQwMTBYCiAgcmRGb3JtCiAgICBhcGlSZWZObyA9IGFzOTAwMDAxCiAgICByZXNwb25zZVBheW1lbnRUeXBlID0gMg==",
                                        ""
                                )
                        )
                )
        );
        return new ResponseEntity<>(res, HttpStatus.OK) ;
    }

    @PostMapping("/checkPaymentStatus")
    public ResponseEntity<CheckPayRes> CheckPaymentStatus() throws Exception{
        CheckPayRes res = new CheckPayRes(
                new EInstrumentCheckPay(
                        "I07000",
                        "สำเร็จ",
                        new RdFormCheckPay(
                                2,
                                new ArrayList<InstInfoCheckPay>(
                                        Arrays.asList(
                                                new InstInfoCheckPay(

                                                        "P0900022091",
                                                        "2"
                                                )
                                        )
                                )
                        )
                )
        );

        return new ResponseEntity<>(res, HttpStatus.OK) ;
    }

    @PostMapping("/reqFormReceiptFile")
    public ResponseEntity<ReceiptRes> ReqFormReceiptFile() throws Exception{
        ReceiptRes res = new ReceiptRes(
                new EInstrumentReceipt(
                        "I07000",
                        "สำเร็จ",
                        new RdFormReceipt(

                                new ArrayList<InstInfoReceipt>(
                                        Arrays.asList(
                                                new InstInfoReceipt(

                                                        "P0900022092",
                                                        new SpecifiedTaxRegistration("1111111111119")
                                                )
                                        )
                                )
                        )
                )
        );

        return new ResponseEntity<>(res, HttpStatus.OK) ;
    }

    @PostMapping("/simulate")
    public ResponseEntity<Map<String,Object>> simulate() throws Exception{


        return new ResponseEntity<>(rdSimulate.simulateDuty(), HttpStatus.OK) ;
    }
    
    @PostMapping("/simulate/address")
    public ResponseEntity<String> simulateaddress() throws Exception{


        return new ResponseEntity<>(rdSimulate.simulateAddress(), HttpStatus.OK) ;
    }
}
