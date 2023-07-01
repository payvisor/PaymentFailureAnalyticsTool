package com.payvisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payvisor.model.BucketsData;
import com.payvisor.repo.BucketsDataRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/bucketsData")
public class BucketsDataController {
	
	@Autowired
	BucketsDataRepo bucketsDataRepo;
	
	@Autowired
	EntityManager entityManager;
	
	@PostMapping("/addBucket")
	@Transactional
	public ResponseEntity<String> addBucket(@RequestBody BucketsData bucketsData) {
		int statusCode = 500;
		String status = "Failed !!";
		if(null != bucketsDataRepo.save(bucketsData)) {
			status = "Inserted into BucketsData table and ";
			String queryToExecute = "INSERT INTO BucketEmailTemplates (BucketName, CreatedDateTime) VALUES ('"+bucketsData.getBucketName()+"',sysdate())";
			Query query = entityManager.createNativeQuery(queryToExecute);
			int result = query.executeUpdate();
			if(result==1) {
				status += "Inserted into BucketEmailTemplates table";
			}else {
				status += "Failed to insert into BucketEmailTemplates table";
			}
			statusCode = 201;
		}
		return new ResponseEntity<String>(status, HttpStatusCode.valueOf(statusCode));
	}

}
