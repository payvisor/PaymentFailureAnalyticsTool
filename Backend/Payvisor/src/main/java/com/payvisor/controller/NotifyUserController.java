package com.payvisor.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payvisor.model.BucketEmailTemplates;
import com.payvisor.model.PaymentFailuresData;
import com.payvisor.model.PaymentsData;
import com.payvisor.repo.BucketEmailTemplatesRepo;
import com.payvisor.repo.PaymentFailuresDataRepo;
import com.payvisor.repo.PaymentsDataRepo;
import com.payvisor.util.EmailSenderUtil;

@RestController
@RequestMapping("/notifyUser")
public class NotifyUserController {
	
	@Autowired
	PaymentFailuresDataRepo paymentFailuresDataRepo;
	
	@Autowired
	PaymentsDataRepo paymentsDataRepo;
	
	@Autowired
	BucketEmailTemplatesRepo bucketEmailTemplatesRepo;
	
	@Autowired
	EmailSenderUtil emailSenderUtil;
	
	@GetMapping("/{txnid}")
	public String notifyUser(@PathVariable String txnid) {
		
		String status = "Missing transaction details for triggering email !!!";
		
		PaymentFailuresData paymentFailuresData= paymentFailuresDataRepo.findOneByTransactionID(txnid);
		if(null != paymentFailuresData && null != paymentFailuresData.getTransactionID() && !paymentFailuresData.getTransactionID().trim().isEmpty()) {
			status = sendEmailForFailureTransaction(paymentFailuresData);
		}else {
			PaymentsData paymentsData = paymentsDataRepo.findOneByTransactionID(txnid);
			if(null != paymentsData && null != paymentsData.getTransactionID() && !paymentsData.getTransactionID().trim().isEmpty()) {
				status = sendEmailForSuccessTransaction(paymentsData);
			}
		}
		return status;
	}
	
	private String sendEmailForFailureTransaction(PaymentFailuresData paymentFailuresData) {
		String status = "Missing user details for triggering email !!!";
		BucketEmailTemplates emailTemplate = bucketEmailTemplatesRepo.findOneByBucketName(paymentFailuresData.getBucketName());
		if(null != emailTemplate && emailTemplate.getSendEmail().equalsIgnoreCase("Yes") && paymentFailuresData.getEmailID() != null && !paymentFailuresData.getEmailID().trim().isEmpty()) {
			String body = buildEmailBodyAsHTML(paymentFailuresData,emailTemplate);
			String subject = "Your Payment of "+String.valueOf(paymentFailuresData.getAmount())+" "+paymentFailuresData.getCurrency()+" is Failed !!!";
			status = emailSenderUtil.sendEmail(paymentFailuresData.getEmailID(),subject,body);
		}
		return status;
	}
	
	private String buildEmailBodyAsHTML(PaymentFailuresData paymentFailuresData, BucketEmailTemplates emailTemplate) {
		String htmlContent = null;
		if(emailTemplate.getUseTemplateReasons().equalsIgnoreCase("Yes") && emailTemplate.getEmailTemplateReasons()!=null && !emailTemplate.getEmailTemplateReasons().trim().isEmpty()) {
			htmlContent = "<!DOCTYPE html>\n<html>\n\n<head>\n  <meta charset=\"UTF-8\">\n  <title>Transaction Failed</title>\n  <style>\n    /* Reset styles */\n    body,\n    body * {\n      font-family: Arial, sans-serif;\n      font-size: 14px;\n      line-height: 1.6;\n      color: #333333;\n    }\n\n    /* Email container */\n    .email-container {\n      max-width: 600px;\n      margin: 0 auto;\n      padding: 20px;\n      background-color: #f5f5f5;\n      border-radius: 4px;\n      box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);\n    }\n\n    /* Header section */\n    .header {\n      text-align: center;\n      margin-bottom: 20px;\n    }\n\n    .header h1 {\n      font-size: 24px;\n      margin: 0;\n      color: #c71414;\n    }\n\n    /* Content section */\n    .content {\n      margin-bottom: 20px;\n      background-color: #ffffff;\n      padding: 20px;\n      border-radius: 4px;\n      box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);\n    }\n\n    .content p {\n      margin-bottom: 10px;\n    }\n\n    .content ul {\n      padding-left: 20px;\n      margin: 10px 0;\n    }\n\n    .content li {\n      margin-bottom: 5px;\n    }\n\n    /* Call to action button */\n    .cta-button {\n      display: inline-block;\n      padding: 10px 20px;\n      background-color: #007bff;\n      color: #ffffff;\n      text-decoration: none;\n      border-radius: 4px;\n    }\n\n    /* Footer section */\n    .footer {\n      text-align: center;\n      color: #888888;\n    }\n\n    .footer p {\n      margin: 0;\n    }\n  </style>\n</head>\n\n<body>\n  <div class=\"email-container\">\n    <div class=\"header\">\n      <h1>Transaction Failed</h1>\n    </div>\n\n    <div class=\"content\">\n      <p>Dear "+paymentFailuresData.getFirstName()+" "+paymentFailuresData.getLastName()+",</p>\n      <p>We regret to inform you that your recent transaction has failed. Please review the details below:</p>\n\n      <ul>\n        <li><strong>Transaction ID:</strong> "+String.valueOf(paymentFailuresData.getTransactionID()+"</li>\n        <li><strong>Transaction Time:</strong> "+paymentFailuresData.getTransactionDateTime()+"</li>\n        <li><strong>Amount:</strong> "+String.valueOf(paymentFailuresData.getAmount())+" "+paymentFailuresData.getCurrency())+"</li>\n  "+frameHTMLForEmailTemplateReasons(emailTemplate)+"</ul>\n\n      <p>Please check and resolve the above reasons and re-initiate the transaction. If you believe this is an error or have any questions, please contact our support team for assistance.</p>\n    </div>\n\n    <div class=\"footer\">\n      <p>Thank you,</p>\n      <p>The PayVisor Team</p>\n    </div>\n  </div>\n</body>\n\n</html>";
		}
		else{
			htmlContent = "<!DOCTYPE html>\n<html>\n\n<head>\n  <meta charset=\"UTF-8\">\n  <title>Transaction Failed</title>\n  <style>\n    /* Reset styles */\n    body,\n    body * {\n      font-family: Arial, sans-serif;\n      font-size: 14px;\n      line-height: 1.6;\n      color: #333333;\n    }\n\n    /* Email container */\n    .email-container {\n      max-width: 600px;\n      margin: 0 auto;\n      padding: 20px;\n      background-color: #f5f5f5;\n      border-radius: 4px;\n      box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);\n    }\n\n    /* Header section */\n    .header {\n      text-align: center;\n      margin-bottom: 20px;\n    }\n\n    .header h1 {\n      font-size: 24px;\n      margin: 0;\n      color: #c71414;\n    }\n\n    /* Content section */\n    .content {\n      margin-bottom: 20px;\n      background-color: #ffffff;\n      padding: 20px;\n      border-radius: 4px;\n      box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);\n    }\n\n    .content p {\n      margin-bottom: 10px;\n    }\n\n    .content ul {\n      padding-left: 20px;\n      margin: 10px 0;\n    }\n\n    .content li {\n      margin-bottom: 5px;\n    }\n\n    /* Call to action button */\n    .cta-button {\n      display: inline-block;\n      padding: 10px 20px;\n      background-color: #007bff;\n      color: #ffffff;\n      text-decoration: none;\n      border-radius: 4px;\n    }\n\n    /* Footer section */\n    .footer {\n      text-align: center;\n      color: #888888;\n    }\n\n    .footer p {\n      margin: 0;\n    }\n  </style>\n</head>\n\n<body>\n  <div class=\"email-container\">\n    <div class=\"header\">\n      <h1>Transaction Failed</h1>\n    </div>\n\n    <div class=\"content\">\n      <p>Dear "+paymentFailuresData.getFirstName()+" "+paymentFailuresData.getLastName()+",</p>\n      <p>We regret to inform you that your recent transaction has failed. Please review the details below:</p>\n\n      <ul>\n        <li><strong>Transaction ID:</strong> "+String.valueOf(paymentFailuresData.getTransactionID()+"</li>\n        <li><strong>Transaction Time:</strong> "+paymentFailuresData.getTransactionDateTime()+"</li>\n        <li><strong>Amount:</strong> "+String.valueOf(paymentFailuresData.getAmount())+" "+paymentFailuresData.getCurrency())+"</li>\n        </ul>\n\n      <p>We are looking into the failure reason of the transaction. Kindly give us some time. Please contact our support team for any further assistance.</p>\n    </div>\n\n    <div class=\"footer\">\n      <p>Thank you,</p>\n      <p>The PayVisor Team</p>\n    </div>\n  </div>\n</body>\n\n</html>";
		}
		return htmlContent;
	}
	
	private String frameHTMLForEmailTemplateReasons(BucketEmailTemplates emailTemplate) {
		String reasonsTemplate = "<li><strong>The Possible Reasons of Failure are :</strong></li>\n <ul>\n";
		String[] reasons = emailTemplate.getEmailTemplateReasons().split(",");
		for(String reason : reasons) {
			reasonsTemplate += "<li> "+" "+reason+" "+"</li>\n";
		}
		reasonsTemplate += "\n </ul>\n";
		return reasonsTemplate;
	}

	private String sendEmailForSuccessTransaction(PaymentsData paymentsData) {
		String status = "Missing user details for triggering email !!!";
		if(paymentsData.getTransactionStatus().equalsIgnoreCase("Success") && paymentsData.getEmailID() != null && !paymentsData.getEmailID().trim().isEmpty()) {
			String subject = "Your Payment of "+String.valueOf(paymentsData.getAmount())+" "+paymentsData.getCurrency()+" is Successful !!!";
			String body = "<!DOCTYPE html>\n<html>\n\n<head>\n  <meta charset=\"UTF-8\">\n  <title>Transaction Success</title>\n  <style>\n    /* Reset styles */\n    body,\n    body * {\n      font-family: Arial, sans-serif;\n      font-size: 14px;\n      line-height: 1.6;\n      color: #333333;\n    }\n\n    /* Email container */\n    .email-container {\n      max-width: 600px;\n      margin: 0 auto;\n      padding: 20px;\n      background-color: #f5f5f5;\n      border-radius: 4px;\n      box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);\n    }\n\n    /* Header section */\n    .header {\n      text-align: center;\n      margin-bottom: 20px;\n    }\n\n    .header h1 {\n      font-size: 24px;\n      margin: 0;\n      color: #16a616;\n    }\n\n    /* Content section */\n    .content {\n      margin-bottom: 20px;\n      background-color: #ffffff;\n      padding: 20px;\n      border-radius: 4px;\n      box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);\n    }\n\n    .content p {\n      margin-bottom: 10px;\n    }\n\n    .content ul {\n      padding-left: 20px;\n      margin: 10px 0;\n    }\n\n    .content li {\n      margin-bottom: 5px;\n    }\n\n    /* Call to action button */\n    .cta-button {\n      display: inline-block;\n      padding: 10px 20px;\n      background-color: #007bff;\n      color: #ffffff;\n      text-decoration: none;\n      border-radius: 4px;\n    }\n\n    /* Footer section */\n    .footer {\n      text-align: center;\n      color: #888888;\n    }\n\n    .footer p {\n      margin: 0;\n    }\n  </style>\n</head>\n\n<body>\n  <div class=\"email-container\">\n    <div class=\"header\">\n      <h1>Transaction Success</h1>\n    </div>\n\n    <div class=\"content\">\n      <p>Dear "+paymentsData.getFirstName()+" "+paymentsData.getLastName()+",</p>\n      <p>Your recent transaction is success. Please review the details below:</p>\n\n      <ul>\n        <li><strong>Transaction ID:</strong> "+String.valueOf(paymentsData.getTransactionID()+"</li>\n        <li><strong>Transaction Time:</strong> "+paymentsData.getTransactionDateTime()+"</li>\n        <li><strong>Amount:</strong> "+String.valueOf(paymentsData.getAmount())+" "+paymentsData.getCurrency())+"</li>\n      </ul>\n\n      <p>Please contact our support team for any assistance required.</p>\n    </div>\n\n    <div class=\"footer\">\n      <p>Thank you,</p>\n      <p>The PayVisor Team</p>\n    </div>\n  </div>\n</body>\n\n</html>";
			status = emailSenderUtil.sendEmail(paymentsData.getEmailID(),subject,body);
		}
		return status;
	}

}
