package com.fabhotels.dao;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fabhotels.entity.PaymentTypeEntity;
import com.fabhotels.entity.UserEntity;
import com.fabhotels.entity.UserTransactionEntity;
import com.fabhotels.model.PaymentType;
import com.fabhotels.model.User;
import com.fabhotels.model.UserTransaction;
import com.fabhotels.utility.WalletConstants;



/*============================================================
 *IMPLEMENTATION CLASS FOR THE REPOSITORY INTERFACE THAT HANDLES ALL THE ACTUAL DB TRANSACTIONS
 *FOR USER LOGIN AND PROFILE UPDATE
 *============================================================
 *It overrides 7 methods from the interface:
 *		getUserByEmailId(String emailId):[
 *					This method accepts a user emailId as an input 
 *					and checks if any user exists in database for that emailId
 *					and it will return the user object from database if it exists.
 *
 *		changeUserpassword(Integer userId, String password):
 *					This method accepts a userId and new password as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will update the user password.
 *
 *		changeUserEmail(Integer userId, String emailId):
 *					This method accepts a userId and new emailId as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will update the user emailId.
 *
 *		changeUserMobile(Integer userId, String mobileNumber):
 *					This method accepts a userId and new mobileNumber as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will update the user mobileNumber.
 *
 * 		changeUserName(Integer userId, String name):
 *					This method accepts a userId and new UserName as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will update the user UserName.
 * 
 * 		getUserByUserId(Integer userId):
 *					This method accepts a userId as an input 
 *					and checks if any user exists in database for that userId
 *					and it will return the user object from database if it exists.
 *
 *		authenticateUser(String emailId):
 *					This method accepts a user emailId as an input 
 *					and checks if any user exists in database for that emailId
 *					and it will return the user object from database if it exists.
 *
 *=============================================================
 */

@Repository(value="userLoginDAOImpl")
public class UserLoginDAOImpl implements UserLoginDAO {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public User getUserByEmailId(String emailId) throws Exception {

		Query query = entityManager.createQuery("from UserEntity where emailId=:emailId");
		query.setParameter("emailId", emailId.toLowerCase());
		
		UserEntity userFromDB = (UserEntity) query.getSingleResult();
		
		if(userFromDB==null){
			return null;
		}
		
		User user = new User();
		
		user.setName(userFromDB.getName());
		user.setEmailId(userFromDB.getEmailId());
		user.setMobileNumber(userFromDB.getMobileNumber());
		user.setUserId(userFromDB.getUserId());
		user.setPassword(userFromDB.getPasssword());
		
		List<UserTransactionEntity> userTransactionEntities = userFromDB.getUserTransactionsEntities();
		List<UserTransaction> userTransactions = null;
		
		if(userTransactionEntities!=null && !userTransactionEntities.isEmpty()){
			
			userTransactions = new ArrayList<>();
			
			for(UserTransactionEntity userTransactionEntity: userTransactionEntities){
				
				UserTransaction userTransaction = new UserTransaction();
				userTransaction.setAmount(userTransactionEntity.getAmount());
				userTransaction.setInfo(userTransactionEntity.getInfo());
				
				PaymentTypeEntity paymentTypeEntity = userTransactionEntity.getPaymentTypeEntity();
				PaymentType paymentType = new PaymentType();
				paymentType.setPaymentFrom(paymentTypeEntity.getPaymentFrom());
				paymentType.setPaymentTo(paymentTypeEntity.getPaymentTo());
				paymentType.setPaymentType(paymentTypeEntity.getPaymentType());
				paymentType.setPaymentTypeId(paymentTypeEntity.getPaymentTypeId());
				userTransaction.setPaymentType(paymentType);
				
				userTransaction.setRemarks(userTransactionEntity.getRemarks());
				userTransaction.setTransactionStatus(userTransactionEntity.getTransactionStatus());
				userTransaction.setTransactionTimestamp(userTransactionEntity.getTransactionTimestamp());
				userTransaction.setUserTransactionId(userTransactionEntity.getUserTransactionId());
				
				userTransactions.add(userTransaction);
			}
		}
		user.setUserTransactions(userTransactions);
		
		
		return user;
	}

	@Override
	public String changeUserpassword(Integer userId, String password) {
		
		UserEntity userFromDB = entityManager.find(UserEntity.class, userId);
		userFromDB.setPasssword(password);
		
		return WalletConstants.PASSWORD_UPDATED;
		
	}

	@Override
	public User getUserByUserId(Integer userId) {

		
		UserEntity userFromDB = entityManager.find(UserEntity.class, userId);
		if(userFromDB==null){
			return null;
		}
		User user = new User();
		
		user.setName(userFromDB.getName());
		user.setEmailId(userFromDB.getEmailId());
		user.setMobileNumber(userFromDB.getMobileNumber());
		user.setUserId(userFromDB.getUserId());
		user.setPassword(userFromDB.getPasssword());
		
		List<UserTransactionEntity> userTransactionEntities = userFromDB.getUserTransactionsEntities();
		List<UserTransaction> userTransactions = null;
		
		if(userTransactionEntities!=null && !userTransactionEntities.isEmpty()){
			userTransactions = new ArrayList<>();
			
			for(UserTransactionEntity userTransactionEntity: userTransactionEntities){
				
				UserTransaction userTransaction = new UserTransaction();
				userTransaction.setAmount(userTransactionEntity.getAmount());
				userTransaction.setInfo(userTransactionEntity.getInfo());
				
				PaymentTypeEntity paymentTypeEntity = userTransactionEntity.getPaymentTypeEntity();
				PaymentType paymentType = new PaymentType();
				paymentType.setPaymentFrom(paymentTypeEntity.getPaymentFrom());
				paymentType.setPaymentTo(paymentTypeEntity.getPaymentTo());
				paymentType.setPaymentType(paymentTypeEntity.getPaymentType());
				paymentType.setPaymentTypeId(paymentTypeEntity.getPaymentTypeId());
				userTransaction.setPaymentType(paymentType);
				
				userTransaction.setRemarks(userTransactionEntity.getRemarks());
				userTransaction.setTransactionStatus(userTransactionEntity.getTransactionStatus());
				userTransaction.setTransactionTimestamp(userTransactionEntity.getTransactionTimestamp());
				userTransaction.setUserTransactionId(userTransactionEntity.getUserTransactionId());
				
				userTransactions.add(userTransaction);
			}
		}
		user.setUserTransactions(userTransactions);
		
		
		return user;
	}

	@Override
	public String changeUserEmail(Integer userId, String emailId) {


		UserEntity userFromDB = entityManager.find(UserEntity.class, userId);
		userFromDB.setEmailId(emailId);
		
		return WalletConstants.EMAIL_ID_UPDATED;
	}

	@Override
	public String changeUserMobile(Integer userId, String mobileNumber) {


		UserEntity userFromDB = entityManager.find(UserEntity.class, userId);
		userFromDB.setMobileNumber(mobileNumber);
		
		return WalletConstants.MOBILE_NUMBER_UPDATED;
	}

	@Override
	public String changeUserName(Integer userId, String name) {


		UserEntity userFromDB = entityManager.find(UserEntity.class, userId);
		userFromDB.setName(name);
		
		return WalletConstants.NAME_UPDATED;
	}

	@Override
	public User authenticateUser(String emailId) {

		Query query = entityManager.createQuery("from UserEntity where emailId=:emailId");
		query.setParameter("emailId", emailId.toLowerCase());
		
		UserEntity userFromDB = (UserEntity) query.getSingleResult();
		
		if(userFromDB==null){
			return null;
		}
		
		User user = new User();
		
		user.setEmailId(userFromDB.getEmailId());
		user.setUserId(userFromDB.getUserId());
		user.setPassword(userFromDB.getPasssword());
		
		
		return user;
	}

}
