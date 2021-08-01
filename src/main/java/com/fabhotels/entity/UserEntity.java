package com.fabhotels.entity;

import java.time.LocalDateTime;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/*
 * ENTITY CLASS TO MAP USER OBJECT TO 
 * WALLET_USER RELATION IN THE DATABASE
 *
 */

@Entity
@Table(name="WALLET_USER")
public class UserEntity {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="USER_ID")
	private Integer userId;
	
	@Column(name="EMAIL_ID")
	private String emailId;
	
	@Column(name="MOBILE_NUMBER")
	private String mobileNumber;
	
	@Column(name="NAME")
	private String Name;
	
	@Column(name="PASSWORD")
	private String passsword;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name="USER_TRANSACTION_MAPPING", 
	joinColumns={@JoinColumn(name="USER_ID", referencedColumnName="USER_ID")}, 
	inverseJoinColumns={@JoinColumn(name="USER_TRANSACTION_ID", referencedColumnName="USER_TRANSACTION_ID")})
	private List<UserTransactionEntity> userTransactionsEntities;
	
	@CreationTimestamp
	@Column(name="CREATED_TIMESTAMP")
	private LocalDateTime createdTimestamp;
	
	@UpdateTimestamp
	@Column(name="MODIFIED_TIMESTAMP")
	private LocalDateTime modifiedTimestamp;
	
	public List<UserTransactionEntity> getUserTransactionsEntities() {
		return userTransactionsEntities;
	}
	public void setUserTransactionsEntities(List<UserTransactionEntity> userTransactionsEntity) {
		this.userTransactionsEntities = userTransactionsEntity;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getPasssword() {
		return passsword;
	}
	public void setPasssword(String passsword) {
		this.passsword = passsword;
	}
}
