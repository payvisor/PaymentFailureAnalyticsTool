package com.payvisor.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "BucketEmailTemplates")
public class BucketEmailTemplates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BucketEmailTemplateID")
	private int bucketEmailTemplateID;
	
	@Column(name = "EmailTemplateName")
	private String emailTemplateName;
	
	@Column(name = "BucketName")
	private String bucketName;
	
	@Column(name = "EmailTemplateReasons")
	private String emailTemplateReasons;
	
	@Column(name = "SendEmail")
	private String sendEmail;
	
	@Column(name = "UseTemplateReasons")
	private String useTemplateReasons;
	
	@Column(name = "CreatedDateTime")
	@CreationTimestamp
	private Date createdDateTime;
	
	@Column(name = "UpdatedDateTime")
	@UpdateTimestamp
	private Date updatedDateTime;

	public BucketEmailTemplates() {
		super();
	}

	public BucketEmailTemplates(int bucketEmailTemplateID, String emailTemplateName, String bucketName,
			String emailTemplateReasons, String sendEmail, String useTemplateReasons, Date createdDateTime,
			Date updatedDateTime) {
		super();
		this.bucketEmailTemplateID = bucketEmailTemplateID;
		this.emailTemplateName = emailTemplateName;
		this.bucketName = bucketName;
		this.emailTemplateReasons = emailTemplateReasons;
		this.sendEmail = sendEmail;
		this.useTemplateReasons = useTemplateReasons;
		this.createdDateTime = createdDateTime;
		this.updatedDateTime = updatedDateTime;
	}

	public BucketEmailTemplates(String bucketName) {
		super();
		this.bucketName = bucketName;
	}


	public int getBucketEmailTemplateID() {
		return bucketEmailTemplateID;
	}

	public void setBucketEmailTemplateID(int bucketEmailTemplateID) {
		this.bucketEmailTemplateID = bucketEmailTemplateID;
	}
	
	public String getEmailTemplateName() {
		return emailTemplateName;
	}

	public void setEmailTemplateName(String emailTemplateName) {
		this.emailTemplateName = emailTemplateName;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getEmailTemplateReasons() {
		return emailTemplateReasons;
	}

	public void setEmailTemplateReasons(String emailTemplateReasons) {
		this.emailTemplateReasons = emailTemplateReasons;
	}

	public String getSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(String sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getUseTemplateReasons() {
		return useTemplateReasons;
	}

	public void setUseTemplateReasons(String useTemplateReasons) {
		this.useTemplateReasons = useTemplateReasons;
	}

	public Date getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(Date createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}


	@Override
	public String toString() {
		return "BucketEmailTemplates [bucketEmailTemplateID=" + bucketEmailTemplateID + ", emailTemplateName="
				+ emailTemplateName + ", bucketName=" + bucketName + ", emailTemplateReasons=" + emailTemplateReasons
				+ ", sendEmail=" + sendEmail + ", useTemplateReasons=" + useTemplateReasons + ", createdDateTime="
				+ createdDateTime + ", updatedDateTime=" + updatedDateTime + "]";
	}

}
