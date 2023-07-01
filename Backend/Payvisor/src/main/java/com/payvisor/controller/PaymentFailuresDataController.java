package com.payvisor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.payvisor.model.DateWiseFailCount;
import com.payvisor.model.GroupByAndCount;
import com.payvisor.model.GroupByCountWithSeverity;
import com.payvisor.model.PaymentFailuresData;
import com.payvisor.model.WeekWiseFailCount;
import com.payvisor.repo.PaymentFailuresDataRepo;
import org.springframework.http.HttpStatusCode;

@RestController
@RequestMapping("/paymentFailuresData")
public class PaymentFailuresDataController {
	
	@Autowired
	PaymentFailuresDataRepo paymentFailuresDataRepo;
	
	@Autowired
	NotifyUserController notifyUserController;
	
	@GetMapping("/byTransactionId/{id}")
	public PaymentFailuresData getRecordByTransactionID(@PathVariable String txnId) {
		return paymentFailuresDataRepo.findOneByTransactionID(txnId);
	}
	
	@GetMapping("/byTransactionStatus/{status}")
	public List<PaymentFailuresData> getRecordsByTransactionStatus(@PathVariable String status) {
		return paymentFailuresDataRepo.findAllByTransactionStatus(status);
	}
	
	@GetMapping("/byEmailId/{emailId}")
	public List<PaymentFailuresData> getRecordsByEmailID(@PathVariable String emailId) {
		return paymentFailuresDataRepo.findAllByEmailID(emailId);
	}
	
	@GetMapping("/countByAllBuckets")
	public List<GroupByCountWithSeverity> countByAllBuckets(){
		return paymentFailuresDataRepo.countByAllBuckets();
	}
	
	@GetMapping("/countByAllBucketsFromNMonths/{noOfMonths}")
	public List<GroupByCountWithSeverity> countByAllBucketsFromNMonths(@PathVariable int noOfMonths){
		return paymentFailuresDataRepo.countByAllBucketsFromNMonths(noOfMonths);
	}
	
	@GetMapping("/countByAllFailureReasonsFromNMonths/{noOfMonths}")
	public List<GroupByAndCount> countByAllFailureReasonsFromNMonths(@PathVariable int noOfMonths){
		return paymentFailuresDataRepo.countByAllFailureReasonsFromNMonths(noOfMonths);
	}
	
	@GetMapping("/countByAllFailureReasonsByBucketFromNMonths/{bucketName}/{noOfMonths}")
	public List<GroupByAndCount> countByAllFailureReasonsByBucketFromNMonths(@PathVariable String bucketName,@PathVariable int noOfMonths){
		return paymentFailuresDataRepo.countByAllFailureReasonsByBucketFromNMonths(bucketName,noOfMonths);
	}
	
	@GetMapping("/weekWiseCountByAllBucketsFromLastNMonths/{noOfMonths}")
	public List<WeekWiseFailCount> weekWiseCountByAllBucketsFromLastNMonths(@PathVariable int noOfMonths){
		return paymentFailuresDataRepo.weekWiseCountByAllBucketsFromLastNMonths(noOfMonths);
	}
	
	@GetMapping("/dateWiseCountByAllBucketsFromLastNMonths/{noOfMonths}")
	public List<DateWiseFailCount> dateWiseCountByAllBucketsFromLastNMonths(@PathVariable int noOfMonths){
		return paymentFailuresDataRepo.dateWiseCountByAllBucketsFromLastNMonths(noOfMonths);
	}
	
	@GetMapping("/weekWiseCountByBucketFromLastNMonths/{bucketName}/{noOfMonths}")
	public List<WeekWiseFailCount> weekWiseCountByBucketFromLastNMonths(@PathVariable String bucketName,@PathVariable int noOfMonths){
		return paymentFailuresDataRepo.weekWiseCountByBucketFromLastNMonths(bucketName,noOfMonths);
	}
	
	@GetMapping("/dateWiseCountByBucketFromLastNMonths/{bucketName}/{noOfMonths}")
	public List<DateWiseFailCount> dateWiseCountByBucketFromLastNMonths(@PathVariable String bucketName,@PathVariable int noOfMonths){
		return paymentFailuresDataRepo.dateWiseCountByBucketFromLastNMonths(bucketName,noOfMonths);
	}
	
	@PostMapping("/addAndNotify")
	public ResponseEntity<String> addpaymentFailuresDataAndNotify(@RequestBody PaymentFailuresData paymentFailuresData) {
		String status = "Operation Failed !!!";
		if(null!= paymentFailuresData.getTransactionID()&& !paymentFailuresData.getTransactionID().trim().isEmpty()) {
			PaymentFailuresData record = paymentFailuresDataRepo.findOneByTransactionID(paymentFailuresData.getTransactionID());
			if(null!=record) {
				System.out.println("A record is already present with the TransactionID :"+paymentFailuresData.getTransactionID());
				System.out.println("Updating the record now....");
				paymentFailuresData.setPaymentFailuresDataID(record.getPaymentFailuresDataID());
			}
		}
		if(null != paymentFailuresDataRepo.save(paymentFailuresData)){
			status = "Record Added";
			if(null != paymentFailuresData.getTransactionID() && !paymentFailuresData.getTransactionID().trim().isEmpty())
			status += " and "+notifyUserController.notifyUser(paymentFailuresData.getTransactionID());
		}
		if(status.equalsIgnoreCase("Operation Failed !!!")) {
			return new ResponseEntity<String>(status, HttpStatusCode.valueOf(500));
		}else {
			return new ResponseEntity<String>(status, HttpStatusCode.valueOf(200));
		}
	}
	
}
