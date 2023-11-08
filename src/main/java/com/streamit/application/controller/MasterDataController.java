package com.streamit.application.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.streamit.application.service.master.MasterDataService;

@SpringBootApplication
@RestController
@CrossOrigin
@RequestMapping("/master")
public class MasterDataController {
	
	@Autowired
	private MasterDataService masterDataService;

	@PostMapping("/{locale}/province")
    public ResponseEntity<Map<String,Object>> searchProvince(@PathVariable("locale")String locale,@RequestBody(required=false) Map<String, Object> req) throws Exception{
        return new ResponseEntity<>(masterDataService.searchProvince(locale,req), HttpStatus.OK) ;
    }
	
	@PostMapping("/{locale}/amphure")
    public ResponseEntity<Map<String,Object>> searchAmphure(@PathVariable("locale")String locale,@RequestBody Map<String, Object> req) throws Exception{
        return new ResponseEntity<>(masterDataService.searchAmphure(locale,req), HttpStatus.OK) ;
    }
	
	@PostMapping("/{locale}/tambon")
    public ResponseEntity<Map<String,Object>> searchTambon(@PathVariable("locale")String locale,@RequestBody Map<String, Object> req) throws Exception{
        return new ResponseEntity<>(masterDataService.searchTambon(locale,req), HttpStatus.OK) ;
    }
	
	@PostMapping("/{locale}/zipcode")
    public ResponseEntity<Map<String,Object>> searchByZipCode(@PathVariable("locale")String locale,@RequestBody Map<String, Object> req) throws Exception{
        return new ResponseEntity<>(masterDataService.searchByZipCode(locale,req), HttpStatus.OK) ;
    }
}
