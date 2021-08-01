package com.fabhotels.service;

import java.util.List;

import com.fabhotels.model.UserTransaction;

/*============================================================
 *SERVICE INTERFACE THAT HANDLES ALL THE ACTUAL BUSINESS LOGIC FOR ALL USER MADE TRANSACTIONS
 *============================================================
 *It contains 4 methods:
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
public interface UserTransactionService {

	UserTransaction loadMoneyInWallet(Double amount, Integer userId, String remarks) throws Exception;
	String transferMoneyFromWalletToWallet(Double amount, Integer senderUserId, String recieverEmailId, String remarks) throws Exception;
	List<UserTransaction> getAllTransactions(Integer userId) throws Exception;
	Double getUserBalance(Integer userId) throws Exception;
}
