package com.payvisor.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "PaymentsData")
public class PaymentsData {

	@Id
	@Column(name = "PaymentsDataID")
	private int paymentsDataID;

	@Column(name = "TransactionID")
	private String transactionID;
	
	@Column(name = "FirstName")
	private String firstName;
	
	@Column(name = "LastName")
	private String lastName;
	
	@Column(name = "EmailID")
	private String emailID;
	
	@Column(name = "Address")
	private String address;
	
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
	
	@Column(name = "ErrorCode")
	private String errorCode;
	
	@Column(name = "TransactionDateTime")
	private Date transactionDateTime;

	public PaymentsData() {
		super();
	}

	public PaymentsData(int paymentsDataID, String transactionID, String firstName, String lastName, String emailID,
			String address, String country, float amount, String currency, String paymentMethod, String paymentLocation,
			String transactionStatus, String failureReason, String errorCode, Date transactionDateTime) {
		super();
		this.paymentsDataID = paymentsDataID;
		this.transactionID = transactionID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailID = emailID;
		this.address = address;
		this.country = country;
		this.amount = amount;
		this.currency = currency;
		this.paymentMethod = paymentMethod;
		this.paymentLocation = paymentLocation;
		this.transactionStatus = transactionStatus;
		this.failureReason = failureReason;
		this.errorCode = errorCode;
		this.transactionDateTime = transactionDateTime;
	}

	public int getPaymentsDataID() {
		return paymentsDataID;
	}

	public void setPaymentsDataID(int paymentsDataID) {
		this.paymentsDataID = paymentsDataID;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Date getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Date transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	@Override
	public String toString() {
		return "PaymentsData [paymentsDataID=" + paymentsDataID + ", transactionID=" + transactionID + ", firstName="
				+ firstName + ", lastName=" + lastName + ", emailID=" + emailID + ", address=" + address + ", country="
				+ country + ", amount=" + amount + ", currency=" + currency + ", paymentMethod=" + paymentMethod
				+ ", paymentLocation=" + paymentLocation + ", transactionStatus=" + transactionStatus
				+ ", failureReason=" + failureReason + ", errorCode=" + errorCode + ", transactionDateTime="
				+ transactionDateTime + "]";
	}
	
}
