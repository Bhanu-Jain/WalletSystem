package com.fabhotels.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

/*
 * MODEL CLASS TO REPRESENT USER TRANSACTION OBJECT
 *
 */

public class UserTransaction {

	private Long userTransactionId;
	private Double amount;
	
	@JsonFormat(pattern="yyyy-MM-dd hh:mm:ss a")
	private LocalDateTime transactionTimestamp;
	
	private String remarks;
	private String info;
	private PaymentType paymentType;
	private TransactionStatus transactionStatus;
	
	public Long getUserTransactionId() {
		return userTransactionId;
	}
	public void setUserTransactionId(Long userTransactionId) {
		this.userTransactionId = userTransactionId;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public LocalDateTime getTransactionTimestamp() {
		return transactionTimestamp;
	}
	public void setTransactionTimestamp(LocalDateTime transactionTimestamp) {
		this.transactionTimestamp = transactionTimestamp;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public PaymentType getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}
	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
}
