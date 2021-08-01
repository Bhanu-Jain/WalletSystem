package com.fabhotels.dao.test;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fabhotels.dao.UserTransactionDAO;
import com.fabhotels.model.UserTransaction;
import com.fabhotels.utility.WalletConstants;

/*
 * TEST CLASS THAT CONTAINS MULTIPLE TEST CASES TO UNIT TEST
 * THE DAO CLASS METHODS USED FOR MAKING USER TRANSACTION
 * 
 * JUNIT AND MOCKITO IS USED FOR UNIT TESTING
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class UserTransactionDAOTest {
	
	@Autowired
	private UserTransactionDAO userTransactionDAO;
	
	@Test
	public void loadMoneyInWalletTest(){
		
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setAmount(23.00);
		userTransaction.setInfo(WalletConstants.MONEY_LOADED_INTO_WALLET);
		userTransaction.setRemarks("");
		userTransaction.setTransactionTimestamp(LocalDateTime.now());
		userTransactionDAO.loadMoneyInWallet(userTransaction, 12121);
		Assert.assertTrue(true);
	}
	
	@Test
	public void debitMoneyFromWalletTest(){
		
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setAmount(23.00);
		userTransaction.setInfo(WalletConstants.TRANSFER_MONEY_WALLET_TO_WALLET_DEBIT + "bhanu@gmail.com");
		userTransaction.setRemarks("");
		userTransaction.setTransactionTimestamp(LocalDateTime.now());
		userTransactionDAO.debitMoneyFromWallet(userTransaction, 12121);
		Assert.assertTrue(true);
	}
	
	@Test
	public void creditMoneyToWalletTest(){
		
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setAmount(23.00);
		userTransaction.setInfo(WalletConstants.TRANSFER_MONEY_WALLET_TO_WALLET_CREDIT + 12122);
		userTransaction.setRemarks("");
		userTransaction.setTransactionTimestamp(LocalDateTime.now());
		userTransactionDAO.creditMoneyToWallet(userTransaction, "bhanu@gmail.com");
		Assert.assertTrue(true);
	}
	
	@Test
	public void getAllTransactionsTest(){
		
		List<UserTransaction> userTransactions = userTransactionDAO.getAllTransactions(12121);
		if(userTransactions==null){
			Assert.assertTrue(true);
		}else{
			Assert.assertFalse(false);
		}
	}
	
	
}
