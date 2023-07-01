package com.payvisor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payvisor.model.BucketEmailTemplates;
import com.payvisor.repo.BucketEmailTemplatesRepo;

@RestController
@RequestMapping("/bucketEmailTemplates")
public class BucketEmailTemplatesController {
	
	@Autowired
	BucketEmailTemplatesRepo bucketEmailTemplatesRepo;
	
	@GetMapping("/getEmailTemplateReasons/{bucketName}")
	public ResponseEntity<String> getRecordByTransactionID(@PathVariable String bucketName) {
		String emailTemplateReasons = null;
		int statusCode = 404;
		BucketEmailTemplates bucketEmailTemplate = bucketEmailTemplatesRepo.findOneByBucketName(bucketName);
		if(null != bucketEmailTemplate) {
			emailTemplateReasons = bucketEmailTemplate.getEmailTemplateReasons();
			statusCode = 200;
		}
		return new ResponseEntity<String>(emailTemplateReasons, HttpStatusCode.valueOf(statusCode));
	}

}
