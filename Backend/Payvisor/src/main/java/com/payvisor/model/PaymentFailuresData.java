package com.payvisor.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PaymentFailuresData")
public class PaymentFailuresData {

	@Id
	@Column(name = "PaymentFailuresDataID")
	private int paymentFailuresDataID;

	@Column(name = "TransactionID")
	private String transactionID;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "EmailID")
	private String emailID;
	
	@Column(name = "Country")
	private String country;
	
	@Column(name = "Amount")
	private float amount;
	
	@Column(name = "Currency")
	private String currency;
	
	@Column(name = "PaymentMethod")
	private String paymentMethod;
	
	@Column(name = "PaymentLocation")
	private String paymentLocation;
	
	@Column(name = "TransactionStatus")
	private String transactionStatus;
	
	@Column(name = "FailureReason")
	private String failureReason;
	
	@Column(name = "BucketName")
	private String bucketName;
	
	@Column(name = "TransactionDateTime")
	private Date transactionDateTime;
	
	@Column(name = "Timestamp")
	private Date timestamp;
	
	public PaymentFailuresData() {
		super();
	}
	public PaymentFailuresData(int paymentFailuresDataID, String transactionID, String firstName, String lastName,
			String emailID, String country, float amount, String currency, String paymentMethod, String paymentLocation,
			String transactionStatus, String failureReason, String bucketName, Date transactionDateTime,
			Date timestamp) {
		super();
		this.paymentFailuresDataID = paymentFailuresDataID;
		this.transactionID = transactionID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailID = emailID;
		this.country = country;
		this.amount = amount;
		this.currency = currency;
		this.paymentMethod = paymentMethod;
		this.paymentLocation = paymentLocation;
		this.transactionStatus = transactionStatus;
		this.failureReason = failureReason;
		this.bucketName = bucketName;
		this.transactionDateTime = transactionDateTime;
		this.timestamp = timestamp;
	}
	public int getPaymentFailuresDataID() {
		return paymentFailuresDataID;
	}
	public void setPaymentFailuresDataID(int paymentFailuresDataID) {
		this.paymentFailuresDataID = paymentFailuresDataID;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailID() {
		return emailID;
	}
	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentLocation() {
		return paymentLocation;
	}
	public void setPaymentLocation(String paymentLocation) {
		this.paymentLocation = paymentLocation;
	}
	public String getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
	public String getFailureReason() {
		return failureReason;
	}
	public void setFailureReason(String failureReason) {
		this.failureReason = failureReason;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public Date getTransactionDateTime() {
		return transactionDateTime;
	}
	public void setTransactionDateTime(Date transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "PaymentFailuresData [paymentFailuresDataID=" + paymentFailuresDataID + ", transactionID="
				+ transactionID + ", firstName=" + firstName + ", lastName=" + lastName + ", emailID=" + emailID
				+ ", country=" + country + ", amount=" + amount + ", currency=" + currency + ", paymentMethod="
				+ paymentMethod + ", paymentLocation=" + paymentLocation + ", transactionStatus=" + transactionStatus
				+ ", failureReason=" + failureReason + ", bucketName=" + bucketName + ", transactionDateTime="
				+ transactionDateTime + ", timestamp=" + timestamp + "]";
	}
	
}
