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
import com.fabhotels.model.TransactionStatus;
import com.fabhotels.model.UserTransaction;
import com.fabhotels.utility.WalletConstants;


/*============================================================
 *IMPLEMENTATION CLASS FOR REPOSITORY INTERFACE THAT HANDLES ALL THE ACTUAL DB TRANSACTIONS FOR ALL USER MADE TRANSACTIONS
 *============================================================
 *It overrides 4 methods from the interface:
 *		loadMoneyInWallet(UserTransaction userTransaction, Integer userId):[
 *					This method accepts a userTransaction object and the userId as an input 
 *					and checks if any user exists in database for that userId and if it exists
 *					then it will add a new transaction to the database to add money to wallet for that userId.
 *
 *		debitMoneyFromWallet(UserTransaction userTransaction, Integer userId):
 *					This method accepts a userTransaction object and the userId as an input 
 *					and checks if any user exists in database for that userId and if it exists
 *					then it will add a new transaction to the database to debit money from wallet for that userId.
 *
 *		creditMoneyToWallet(UserTransaction userTransaction, String EmailId):
 *					This method accepts a userTransaction object and the emailId as an input 
 *					and checks if any user exists in database for that userId and if it exists
 *					then it will add a new transaction to the database to credit money to wallet for that userId.
 *
 *		getAllTransactions(Integer userId):
 *					This method accepts a userId as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will return all a list of all the transactions made for that userId.
 *
 *
 *=============================================================
 */

@Repository(value="userTransactionDAOImpl")
public class UserTransactionDAOImpl implements UserTransactionDAO {

	@Autowired
	EntityManager entityManager;
	
	@Override
	public UserTransaction loadMoneyInWallet(UserTransaction userTransaction, Integer userId) {

		UserEntity userEntity = entityManager.find(UserEntity.class, userId);
		if(userEntity==null){
			return null;
		}
		
		List<UserTransactionEntity> userTransactionEntities = userEntity.getUserTransactionsEntities();
		
		UserTransactionEntity userTransactionEntity = new UserTransactionEntity();
		userTransactionEntity.setAmount(userTransaction.getAmount());
		userTransactionEntity.setInfo(userTransaction.getInfo());
		userTransactionEntity.setRemarks(userTransaction.getRemarks());
		userTransactionEntity.setTransactionStatus(TransactionStatus.SUCCESS);

		Query query = entityManager.createQuery("from PaymentTypeEntity where paymentFrom=:paymentFrom and paymentTo=:paymentTo and paymentType=:paymentType");
		query.setParameter("paymentType", WalletConstants.PAYMENT_TYPE_CREDIT.charAt(0));
		query.setParameter("paymentTo", WalletConstants.PAYMENT_TO_WALLET.charAt(0));
		query.setParameter("paymentFrom", WalletConstants.PAYMENT_FROM_BANK.charAt(0));
		PaymentTypeEntity paymentTypeEntity = (PaymentTypeEntity) query.getSingleResult();
		
		userTransactionEntity.setPaymentTypeEntity(paymentTypeEntity);
		userTransactionEntity.setUserId(userEntity.getUserId());
		userTransactionEntities.add(userTransactionEntity);
		entityManager.persist(userTransactionEntity);
		userEntity.setUserTransactionsEntities(userTransactionEntities);
		entityManager.persist(userEntity);
		userTransaction.setUserTransactionId(userTransactionEntity.getUserTransactionId());
		
		if(paymentTypeEntity!=null){
			
			PaymentType paymentType = new PaymentType();
			paymentType.setPaymentFrom(paymentTypeEntity.getPaymentFrom());
			paymentType.setPaymentTo(paymentTypeEntity.getPaymentTo());
			paymentType.setPaymentType(paymentTypeEntity.getPaymentType());
			paymentType.setPaymentTypeId(paymentTypeEntity.getPaymentTypeId());
			
			userTransaction.setPaymentType(paymentType);
		}
		
		userTransaction.setTransactionStatus(userTransactionEntity.getTransactionStatus());
		
		return userTransaction;
	}

	@Override
	public UserTransaction debitMoneyFromWallet(UserTransaction userTransaction, Integer userId){

		UserEntity userEntity = entityManager.find(UserEntity.class, userId);
		if(userEntity==null){
			return null;
		}
		
		
		
		List<UserTransactionEntity> userTransactionEntities = userEntity.getUserTransactionsEntities();
		
		UserTransactionEntity userTransactionEntity = new UserTransactionEntity();
		userTransactionEntity.setAmount(userTransaction.getAmount());
		userTransactionEntity.setInfo(userTransaction.getInfo());
		userTransactionEntity.setRemarks(userTransaction.getRemarks());
		userTransactionEntity.setTransactionStatus(TransactionStatus.SUCCESS);

		Query query2 = entityManager.createQuery("from PaymentTypeEntity where paymentFrom=:paymentFrom and paymentTo=:paymentTo and paymentType=:paymentType");
		query2.setParameter("paymentType", WalletConstants.PAYMENT_TYPE_DEBIT.charAt(0));
		query2.setParameter("paymentTo", WalletConstants.PAYMENT_TO_WALLET.charAt(0));
		query2.setParameter("paymentFrom", WalletConstants.PAYMENT_FROM_WALLET.charAt(0));
		PaymentTypeEntity paymentTypeEntity = (PaymentTypeEntity) query2.getSingleResult();
		
		userTransactionEntity.setPaymentTypeEntity(paymentTypeEntity);
		userTransactionEntity.setUserId(userEntity.getUserId());
		
		userTransactionEntities.add(userTransactionEntity);
		entityManager.persist(userTransactionEntity);
		userEntity.setUserTransactionsEntities(userTransactionEntities);
		entityManager.persist(userEntity);
		userTransaction.setUserTransactionId(userTransactionEntity.getUserTransactionId());
		
		if(paymentTypeEntity!=null){
			
			PaymentType paymentType = new PaymentType();
			paymentType.setPaymentFrom(paymentTypeEntity.getPaymentFrom());
			paymentType.setPaymentTo(paymentTypeEntity.getPaymentTo());
			paymentType.setPaymentType(paymentTypeEntity.getPaymentType());
			paymentType.setPaymentTypeId(paymentTypeEntity.getPaymentTypeId());
			
			userTransaction.setPaymentType(paymentType);
		}
		
		userTransaction.setTransactionStatus(userTransactionEntity.getTransactionStatus());
		
		
		return userTransaction;
	}

	@Override
	public UserTransaction creditMoneyToWallet(UserTransaction userTransaction, String emailId){

		
		Query query = entityManager.createQuery("from UserEntity where emailId=:emailId");
		query.setParameter("emailId", emailId.toLowerCase());
		
		UserEntity userEntity = (UserEntity) query.getSingleResult();
		
		if(userEntity==null){
			return null;
		}
		
		List<UserTransactionEntity> userTransactionEntities = userEntity.getUserTransactionsEntities();
		
		UserTransactionEntity userTransactionEntity = new UserTransactionEntity();
		userTransactionEntity.setAmount(userTransaction.getAmount());
		userTransactionEntity.setInfo(userTransaction.getInfo());
		userTransactionEntity.setRemarks(userTransaction.getRemarks());
		userTransactionEntity.setTransactionStatus(TransactionStatus.SUCCESS);

		Query query2 = entityManager.createQuery("from PaymentTypeEntity where paymentFrom=:paymentFrom and paymentTo=:paymentTo and paymentType=:paymentType");
		query2.setParameter("paymentType", WalletConstants.PAYMENT_TYPE_CREDIT.charAt(0));
		query2.setParameter("paymentTo", WalletConstants.PAYMENT_TO_WALLET.charAt(0));
		query2.setParameter("paymentFrom", WalletConstants.PAYMENT_FROM_WALLET.charAt(0));
		PaymentTypeEntity paymentTypeEntity = (PaymentTypeEntity) query2.getSingleResult();
		
		userTransactionEntity.setPaymentTypeEntity(paymentTypeEntity);
		userTransactionEntity.setUserId(userEntity.getUserId());
		
		userTransactionEntities.add(userTransactionEntity);
		entityManager.persist(userTransactionEntity);
		userEntity.setUserTransactionsEntities(userTransactionEntities);
		entityManager.persist(userEntity);
		userTransaction.setUserTransactionId(userTransactionEntity.getUserTransactionId());
		
		if(paymentTypeEntity!=null){
			
			PaymentType paymentType = new PaymentType();
			paymentType.setPaymentFrom(paymentTypeEntity.getPaymentFrom());
			paymentType.setPaymentTo(paymentTypeEntity.getPaymentTo());
			paymentType.setPaymentType(paymentTypeEntity.getPaymentType());
			paymentType.setPaymentTypeId(paymentTypeEntity.getPaymentTypeId());
			
			userTransaction.setPaymentType(paymentType);
		}
		
		userTransaction.setTransactionStatus(userTransactionEntity.getTransactionStatus());
		
		
		return userTransaction;
	}

	@Override
	public List<UserTransaction> getAllTransactions(Integer userId) {
		
		UserEntity userEntity = entityManager.find(UserEntity.class, userId);
		if(userEntity==null){
			return null;
		}
		List<UserTransactionEntity> userTransactionEntities = userEntity.getUserTransactionsEntities();
		List<UserTransaction> userTransactions = new ArrayList<>();
		if(userTransactionEntities!=null && !userTransactionEntities.isEmpty()){
			
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
		
		return userTransactions;
	}
	
	

}
