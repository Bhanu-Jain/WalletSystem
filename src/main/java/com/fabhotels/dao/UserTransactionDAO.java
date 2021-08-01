package com.fabhotels.dao;

import java.util.List;

import com.fabhotels.model.UserTransaction;


/*============================================================
 *REPOSITORY INTERFACE THAT HANDLES ALL THE ACTUAL DB TRANSACTIONS FOR ALL USER MADE TRANSACTIONS
 *============================================================
 *It contains 4 methods:
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

public interface UserTransactionDAO {

	UserTransaction loadMoneyInWallet(UserTransaction userTransaction, Integer userId);
	UserTransaction debitMoneyFromWallet(UserTransaction userTransaction, Integer userId);
	UserTransaction creditMoneyToWallet(UserTransaction userTransaction, String EmailId);
	List<UserTransaction> getAllTransactions(Integer userId);
}
