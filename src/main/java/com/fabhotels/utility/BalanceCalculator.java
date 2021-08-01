package com.fabhotels.utility;

import java.util.List;

import com.fabhotels.model.UserTransaction;

/*============================================================
 *UTILITY CLASS TO CALCULATE USER WALLET BALANCE
 *============================================================
 *It contains 1 static methods:
 *		calculateBalance(List<UserTransaction> userTransactions):[
 *					This method accepts a List of UserTransaction objects for any user as an input 
 *					and calculates the wallet balance by checking the amount and payment type for each transaction made.
 *
 *=============================================================
 */
public class BalanceCalculator {

	public static Double calculateBalance(List<UserTransaction> userTransactions){
		
		Double balance = 0.0;
		
		if(userTransactions==null || userTransactions.isEmpty()){
			return 0.0;
		}
		
		for(UserTransaction userTransaction: userTransactions){
			if(userTransaction.getPaymentType().getPaymentType().toString().equals(WalletConstants.PAYMENT_TYPE_CREDIT)){
				balance += userTransaction.getAmount();
			}else if(userTransaction.getPaymentType().getPaymentType().toString().equals(WalletConstants.PAYMENT_TYPE_DEBIT)){
				
				balance -= userTransaction.getAmount();
			}
		}
		
		return balance;
	}
}
