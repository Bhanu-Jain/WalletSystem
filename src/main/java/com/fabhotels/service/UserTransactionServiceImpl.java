package com.fabhotels.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabhotels.dao.UserTransactionDAO;
import com.fabhotels.model.UserTransaction;
import com.fabhotels.utility.BalanceCalculator;
import com.fabhotels.utility.WalletConstants;


/*============================================================
 *IMPLEMENTATION CLASS FOR THE SERVICE INTERFACE THAT HANDLES ALL THE ACTUAL BUSINESS LOGIC FOR ALL USER MADE TRANSACTIONS
 *============================================================
 *It overrides 4 methods from the interface:
 *		loadMoneyInWallet(UserTransaction userTransaction, Integer userId):[
 *					This method accepts a userTransaction object and the userId as an input 
 *					and checks if any user exists in database for that userId and if it exists
 *					then it will add a new transaction to the database to add money to wallet for that userId.
 *
 *		transferMoneyFromWalletToWallet(Double amount, Integer senderUserId, String recieverEmailId, String remarks):
 *					This method accepts the amount, senderUserId, recieverUserId and transaction remarks as an input 
 *					and checks if any sender and receiver exists in database and if they exists
 *					then it will add a 2 new transaction to the database: One to debit money from wallet of sender
 *					and one to credit money to the wallet of receiver.
 *
 *		getAllTransactions(UserTransaction userTransaction, String EmailId):
 *					This method accepts a userId as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will return all a list of all the transactions made for that userId.
 *
 *		getUserBalance(Integer userId):
 *					This method accepts a userId as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will return the balance for wallet associated with that userId.
 *
 *
 *=============================================================
 */

@Service(value="userTransactionServiceImpl")
public class UserTransactionServiceImpl implements UserTransactionService {

	
	@Autowired
	private UserTransactionDAO userTransactionDAO;
	
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserTransaction loadMoneyInWallet(Double amount, Integer userId, String remarks) throws Exception {

		UserTransaction userTransaction = new UserTransaction();
		
		userTransaction.setAmount(amount);
		userTransaction.setRemarks(remarks);
		userTransaction.setInfo(WalletConstants.MONEY_LOADED_INTO_WALLET);
		
		userTransaction = userTransactionDAO.loadMoneyInWallet(userTransaction, userId);
		if(userTransaction==null){
			throw new Exception("UserTransactionService.USER_NOT_FOUND");
		}
		return userTransaction;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String transferMoneyFromWalletToWallet(Double amount, Integer userId,String recieverEmailId, String remarks) throws Exception{

		UserTransaction userTransactionSender = new UserTransaction();
		
		userTransactionSender.setAmount(amount);
		userTransactionSender.setRemarks(remarks);
		userTransactionSender.setInfo(WalletConstants.TRANSFER_MONEY_WALLET_TO_WALLET_DEBIT);
		
		userTransactionSender = userTransactionDAO.debitMoneyFromWallet(userTransactionSender, userId);
		if(userTransactionSender==null){
			throw new Exception("UserTransactionService.SENDER_NOT_FOUND");
		}
		
		UserTransaction userTransactionReciever = new UserTransaction();
		
		userTransactionReciever.setAmount(amount);
		userTransactionReciever.setRemarks(remarks);
		userTransactionReciever.setInfo(WalletConstants.TRANSFER_MONEY_WALLET_TO_WALLET_CREDIT);
		
		userTransactionReciever = userTransactionDAO.creditMoneyToWallet(userTransactionReciever, recieverEmailId);
		if(userTransactionReciever==null){
			throw new Exception("UserTransactionService.RECIEVER_NOT_FOUND");
		}
		
		
		return "AMOUNT OF INR" + amount + " IS TRANSFERED TO " + recieverEmailId + " SUCCESSFULLY";
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<UserTransaction> getAllTransactions(Integer userId) throws Exception{
		
		List<UserTransaction> userTransactions = userTransactionDAO.getAllTransactions(userId);
		if(userTransactions==null){
			throw new Exception("UserTransactionService.USER_NOT_FOUND");
		}
		return userTransactions;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Double getUserBalance(Integer userId) throws Exception {

		List<UserTransaction> userTransactions = getAllTransactions(userId);
		return BalanceCalculator.calculateBalance(userTransactions);
		
	}


}
