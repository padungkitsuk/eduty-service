package com.streamit.application.service.rd;

import com.google.gson.Gson;
import com.streamit.application.dto.*;
import com.streamit.application.service.jwt.JWebToken;
import com.streamit.application.service.jwt.JwtRsa;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public interface RdService {
    AuthRes SubmitFilingAuth() throws Exception;
    EInstrumentSubmit SubmitFiling() throws Exception;
    EInstrumentCheckFile CheckFilingStatus() throws Exception;
    EInstrumentCheckPay CheckPaymentStatus() throws Exception;
    EInstrumentReceipt ReqFormReceiptFile() throws Exception;
}

@Service
class RdServiceImp implements RdService {
    private Logger logger = LoggerFactory.getLogger(RdService.class);

    private static final int EXPIRY_DAYS = 90;
    @Value("${rd.host}")
    private String RD_HOST;
    @Value("${auth.jwt.header.x-token}")
    private String RD_X_TOKEN;
    @Value("${auth.jwt.payload.username}")
    private String RD_USERNAME;
    @Value("${auth.jwt.payload.password}")
    private String RD_PASSWORD;


    @Override
    public AuthRes SubmitFilingAuth() throws Exception{
        //https://<hostname>/rd-stamp-os9-service/SubmitFilingAuth

        String url = RD_HOST + "/submitFilingAuth";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Content-Type", "application/json");


        //"2018-12-10 15:45:00"
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String strDate = formatter.format(new Date());

        AuthReq req = new AuthReq(RD_USERNAME,RD_PASSWORD,strDate);

        HttpEntity<AuthReq> request = new HttpEntity<>(req, headers);
        //logger.info("authreq={}", new Gson().toJson(request));

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AuthRes> response = restTemplate.postForEntity(url, request, AuthRes.class);
        //logger.info("authres={}", new Gson().toJson(response.getBody()));


        return response.getBody();
    }

    @Override
    public EInstrumentSubmit SubmitFiling() throws Exception{
        //https://<hostname>/rd-stamp-os9-service/SubmitFiling
        String url = RD_HOST + "/submitFiling";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Content-Type", "application/json");
        headers.set("X-auth-token", RD_X_TOKEN);


        Map<String,Object> map = new HashMap<>();

        Map<String,Object> sender = new HashMap<>();
        sender.put("id","XYZ00001");
        sender.put("senderRole","2");//1 = เป็นผู้มีหน้าที่เสียอากร (Direct) 2 = ผู้ให้บริการตัวแทน

        Map<String,Object> eInstrument = new HashMap<>();
        eInstrument.put("sender",sender);

        Map<String,Object> payloadObj = new HashMap<>();
        payloadObj.put("status", 0);

        String payloadStr = new Gson().toJson(payloadObj);
        //String token = new JWebToken(payloadStr, null).getToken();
        //logger.info("token={}",token);


        ///////////////// TEST ///////////////////////
//        JSONObject jwtPayload = new JSONObject();
//        jwtPayload.put("status", 0);
//
//        JSONArray audArray = new JSONArray();
//        audArray.put("admin");
//        jwtPayload.put("sub", "John");
//
//        jwtPayload.put("aud", audArray);
//        LocalDateTime ldt = LocalDateTime.now().plusDays(EXPIRY_DAYS);
//        jwtPayload.put("exp", ldt.toEpochSecond(ZoneOffset.UTC)); //this needs to be configured
//
//        String tokenxxxxx = new JWebToken(jwtPayload).toString();
//        logger.info("{}",tokenxxxxx);
//
//        Boolean cccc = new JWebToken(tokenxxxxx).isValid();
//        logger.info("{}",cccc);
//
//        Boolean ttt = new JWebToken(token).isValid2();
//        logger.info("{}",ttt);

        ////////////////// TEST /////////////////////
        
        String tokens = new JwtRsa().generateJwtToken(payloadStr);
        System.out.println("jws="+tokens);
        new JwtRsa().printStructure(tokens);

        map.put("eInstrument",eInstrument);
        map.put("rdForm",tokens);

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<SubmitRes> response = restTemplate.postForEntity(url, request, SubmitRes.class);
        //logger.info("submitres ={}", new Gson().toJson(response.getBody()));

        return response.getBody().getEInstrument();
    }

    @Override
    public EInstrumentCheckFile CheckFilingStatus() throws Exception{
        //https://<hostname>/rd-stamp-os9-service/CheckFilingStatus
        String url = RD_HOST + "/checkFilingStatus";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Content-Type", "application/json");
        headers.set("X-auth-token", RD_X_TOKEN);


        Map<String,Object> map = new HashMap<>();

        Map<String,Object> sender = new HashMap<>();
        sender.put("id","XYZ00001");
        //sender.put("senderRole","1");

        Map<String,Object> eInstrument = new HashMap<>();
        eInstrument.put("sender",sender);

        map.put("eInstrument",eInstrument);
        map.put("rdForm","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlSZWZObyI6ImFzOTAwMDAxIiwicmVzcG9uc2VQYXltZW50VHlwZSI6IjIifQ.NdVm71vgI2IyQZ2mEFkZKyShWADoRYD5WzBnpnBS2TPGKCUWUr8ckILBbDFIr0CkRB88Ece2DFg5kp6jKeQrAId6TLKsk8yHR8JdO3Yt0f2Hb3UkaFmptYBeazZ02VavMcr8dwYVm2QRK1Bf_1CTJ_TPiYv78xr8VgJhQ-Povo");

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CheckFileRes> response = restTemplate.postForEntity(url, request, CheckFileRes.class);

        logger.info("auth res ={}", new Gson().toJson(response));


        return response.getBody().getEInstrument();
    }

    @Override
    public EInstrumentCheckPay CheckPaymentStatus() throws Exception{
        //https://<hostname>/rd-stamp-os9-service/CheckPaymentStatus
        String url = RD_HOST + "/checkPaymentStatus";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Content-Type", "application/json");
        headers.set("X-auth-token", RD_X_TOKEN);


        Map<String,Object> map = new HashMap<>();

        Map<String,Object> sender = new HashMap<>();
        sender.put("id","XYZ00001");
        //sender.put("senderRole","1");

        Map<String,Object> eInstrument = new HashMap<>();
        eInstrument.put("sender",sender);

        map.put("eInstrument",eInstrument);
        map.put("rdForm","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlSZWZObyI6ImFzOTAwMDAxIiwicmVzcG9uc2VQYXltZW50VHlwZSI6IjIifQ.NdVm71vgI2IyQZ2mEFkZKyShWADoRYD5WzBnpnBS2TPGKCUWUr8ckILBbDFIr0CkRB88Ece2DFg5kp6jKeQrAId6TLKsk8yHR8JdO3Yt0f2Hb3UkaFmptYBeazZ02VavMcr8dwYVm2QRK1Bf_1CTJ_TPiYv78xr8VgJhQ-Povo");

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<CheckPayRes> response = restTemplate.postForEntity(url, request, CheckPayRes.class);

        logger.info("auth res ={}", new Gson().toJson(response));

        return response.getBody().getEInstrument();
    }

    @Override
    public EInstrumentReceipt ReqFormReceiptFile() throws Exception{
        //https://<hostname>/rd-stamp-os9-service/ReqFormReceiptFile
        String url = RD_HOST + "/reqFormReceiptFile";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Content-Type", "application/json");
        headers.set("X-auth-token", RD_X_TOKEN);


        Map<String,Object> map = new HashMap<>();

        Map<String,Object> sender = new HashMap<>();
        sender.put("id","XYZ00001");
        //sender.put("senderRole","1");

        Map<String,Object> eInstrument = new HashMap<>();
        eInstrument.put("sender",sender);

        map.put("eInstrument",eInstrument);
        map.put("rdForm","eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhcGlSZWZObyI6ImFzOTAwMDAxIiwicmVzcG9uc2VQYXltZW50VHlwZSI6IjIifQ.NdVm71vgI2IyQZ2mEFkZKyShWADoRYD5WzBnpnBS2TPGKCUWUr8ckILBbDFIr0CkRB88Ece2DFg5kp6jKeQrAId6TLKsk8yHR8JdO3Yt0f2Hb3UkaFmptYBeazZ02VavMcr8dwYVm2QRK1Bf_1CTJ_TPiYv78xr8VgJhQ-Povo");

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(map, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ReceiptRes> response = restTemplate.postForEntity(url, request, ReceiptRes.class);

        logger.info("auth res ={}", new Gson().toJson(response));

        return response.getBody().getEInstrument();
    }
}
