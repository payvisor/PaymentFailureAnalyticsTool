package com.payvisor.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payvisor.model.PaymentFailuresData;
import com.payvisor.repo.PaymentFailuresDataRepo;
import com.payvisor.util.ExcelGenerator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/reports")
public class ReportsController {
	
	@Autowired
	PaymentFailuresDataRepo paymentFailuresDataRepo;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@GetMapping("/getAllColumnNames")
	public List<String> getAllColumnNames(){
		
		List<String> allColumns = new ArrayList<>();
		
		allColumns.add("TransactionID");
		allColumns.add("FirstName");
		allColumns.add("LastName");
		allColumns.add("EmailID");
		allColumns.add("Country");
		allColumns.add("Amount");
		allColumns.add("Currency");
		allColumns.add("PaymentMethod");
		allColumns.add("PaymentLocation");
		allColumns.add("FailureReason");
		allColumns.add("BucketName");
		
		return allColumns;
	}
	
	@PostMapping("/previewReportByQuery")
	public List<PaymentFailuresData> getDataByQuery(@RequestBody Map<String, String> reqBody){
		String queryWhereClause = reqBody.get("queryWhereClause");
		String limit = reqBody.get("limit");
		String limitClause = "";
		if(null != limit && !limit.trim().isEmpty()) {
			limitClause = "limit "+limit;
		}
		String queryToExecute = "select PaymentFailuresDataID,TransactionID,FirstName,LastName,EmailID,Country,Amount,Currency,"
				+ "PaymentMethod,PaymentLocation,TransactionStatus,FailureReason,BucketName,TransactionDateTime from PaymentFailuresData "+queryWhereClause+" and TransactionID is not null order by TransactionID "+limitClause;
		Query query = entityManager.createNativeQuery(queryToExecute);
		List<Object[]> results = query.getResultList();
		List<PaymentFailuresData> finalList = new ArrayList<>();
		results.stream().forEach((record) -> {
		        int paymentFailuresDataID = (int) record[0];
		        String transactionID = (String) record[1];
		        String firstName = (String) record[2];
		        String lastName = (String) record[3];
		        String emailID = (String) record[4];
		        String country = (String) record[5];
		        float amount = Float.valueOf(String.valueOf(record[6]));
		        String currency = (String) record[7];
		        String paymentMethod = (String) record[8];
		        String paymentLocation = (String) record[9];
		        String transactionStatus = (String) record[10];
		        String failureReason = (String) record[11];
		        String bucketName = (String) record[12];
		        Date transactionDateTime = (Date) record[13];
		        finalList.add(new PaymentFailuresData(paymentFailuresDataID, transactionID, firstName, lastName, emailID, country, 
		        		amount, currency, paymentMethod, paymentLocation, 
		        		transactionStatus, failureReason, bucketName, transactionDateTime, null));
		});

		return finalList;
	}
	
	@GetMapping("/exportReportByQuery")
	public void exportReportByQueryGet(HttpServletResponse response) throws IOException {
		Map<String, String> reqBody = new HashMap<>();
		reqBody.put("queryWhereClause","where 1=1");
		reqBody.put("limit",null);
        
        List<PaymentFailuresData> paymentFailuresDataList = getDataByQuery(reqBody);
        
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Payments Failure Data.xlsx";
        response.setHeader(headerKey, headerValue);
    	ExcelGenerator generator = new ExcelGenerator(paymentFailuresDataList);
    	generator.generateExcelFile(response);
    	
	}
	
	@PostMapping("/exportReportByQuery")
	public void exportReportByQueryPost(HttpServletResponse response, @RequestBody Map<String, String> reqBody) throws IOException {
		reqBody.put("limit",null);
		String reportName = reqBody.get("reportName");
		if(null == reportName || (null != reportName && reportName.trim().isEmpty())){
			reportName = "Payments Failure Data";
		}
        
        List<PaymentFailuresData> paymentFailuresDataList = getDataByQuery(reqBody);
        
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename="+reportName+".xlsx";
        response.setHeader(headerKey, headerValue);
    	ExcelGenerator generator = new ExcelGenerator(paymentFailuresDataList);
    	generator.generateExcelFile(response);
    	
	}

}
