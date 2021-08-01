package com.fabhotels.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fabhotels.model.TransactionStatus;

/*
 * ENTITY CLASS TO MAP USER TRANSACTION OBJECT TO 
 * USER_TRANSACTION RELATION IN THE DATABASE
 *
 */

@Entity
@Table(name="USER_TRANSACTION")
public class UserTransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="USER_TRANSACTION_ID")
	private Long userTransactionId;
	
	@Column(name="AMOUNT")
	private Double amount;
	
	@CreationTimestamp
	@Column(name="TRANSACTION_TIMESTAMP")
	private LocalDateTime transactionTimestamp;
	
	@Column(name="REMARKS")
	private String remarks;
	
	@Column(name="INFO")
	private String info;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="PAYMENT_TYPE_ID")
	private PaymentTypeEntity paymentTypeEntity;
	
	@Enumerated(EnumType.STRING)
	@Column(name="TRANSACTION_STATUS")
	private TransactionStatus transactionStatus;
	
	@Column(name="USER_ID")
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public PaymentTypeEntity getPaymentTypeEntity() {
		return paymentTypeEntity;
	}
	public void setPaymentTypeEntity(PaymentTypeEntity paymentTypeEntity) {
		this.paymentTypeEntity = paymentTypeEntity;
	}
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
	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}
	public void setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
	}
}
