package com.fabhotels.model;

import java.util.List;

/*
 * MODEL CLASS TO REPRESENT USER OBJECT
 *
 */
public class User {

	private Integer userId;
	private String emailId;
	private String mobileNumber;
	private String name;
	private Double balance;
	private List<UserTransaction> userTransactions;
	private String password;
	private String newPassword;
	private String jwtToken;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJwtToken() {
		return jwtToken;
	}
	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
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
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public List<UserTransaction> getUserTransactions() {
		return userTransactions;
	}
	public void setUserTransactions(List<UserTransaction> userTransactions) {
		this.userTransactions = userTransactions;
	}
	
	
}
