package com.payvisor.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailSenderUtil {
	@Autowired
	private JavaMailSender mailSender;

	public String sendEmail(String toEmail, String subject, String body) {
		String status = "Failed to send email !!!";
		try {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(body, true);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        mailSender.send(mimeMessage);
        status = "Mail Sent Succesfully !!!";
		}catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}