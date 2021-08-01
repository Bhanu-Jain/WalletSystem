package com.fabhotels.service.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.fabhotels.dao.UserTransactionDAO;
import com.fabhotels.model.PaymentType;
import com.fabhotels.model.UserTransaction;
import com.fabhotels.service.UserTransactionService;
import com.fabhotels.utility.BalanceCalculator;
import com.fabhotels.utility.WalletConstants;

/*
 * TEST CLASS THAT CONTAINS MULTIPLE TEST CASES TO UNIT TEST
 * THE SERVICE CLASS METHODS USED FOR MAKING USER TRANSACTION
 * 
 * JUNIT AND MOCKITO IS USED FOR UNIT TESTING
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTransactionServiceTest {

	@Rule
	ExpectedException ee = ExpectedException.none();
	
	@MockBean
	private UserTransactionDAO userTransactionDAO;
	
	@Autowired
	private UserTransactionService userTransactionService;
	
	@Test
	public void loadMoneyInWalletValid() throws Exception{
		
		UserTransaction userTransaction = new UserTransaction();
		
		Mockito.when(userTransactionDAO.loadMoneyInWallet(Mockito.any(UserTransaction.class), Mockito.anyInt())).thenReturn(userTransaction);
		
		UserTransaction userTransactionFromService = userTransactionService.loadMoneyInWallet(23.00, 12121, "");
		Assert.assertNotNull(userTransactionFromService);
	}
	
	@Test
	public void loadMoneyInWalletInvalidUser() throws Exception{
		
		Mockito.when(userTransactionDAO.loadMoneyInWallet(Mockito.any(UserTransaction.class), Mockito.anyInt())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserTransactionService.USER_NOT_FOUND");
		userTransactionService.loadMoneyInWallet(23.00, 121207, "");
		
	}
	
	@Test
	public void transferMoneyFromWalletToWalletValidDetails() throws Exception{
		
		UserTransaction userTransactionSender = new UserTransaction();
		UserTransaction userTransactionReciever = new UserTransaction();
		
		Mockito.when(userTransactionDAO.debitMoneyFromWallet(Mockito.any(UserTransaction.class), Mockito.anyInt())).thenReturn(userTransactionSender);
		Mockito.when(userTransactionDAO.creditMoneyToWallet(Mockito.any(UserTransaction.class), Mockito.anyString())).thenReturn(userTransactionReciever);
		
		String message = userTransactionService.transferMoneyFromWalletToWallet(23.00, 12121, "bhanu@gmail.com", "");
		Assert.assertEquals(message, "AMOUNT OF INR23.00 IS TRANSFERED TO bhanu@gmail.com SUCCESSFULLY");
	}
	
	@Test
	public void transferMoneyFromWalletToWalletInvalidSender() throws Exception{
		
		Mockito.when(userTransactionDAO.debitMoneyFromWallet(Mockito.any(UserTransaction.class), Mockito.anyInt())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserTransactionService.SENDER_NOT_FOUND");
		userTransactionService.transferMoneyFromWalletToWallet(23.00, 12121, "bhanu@gmail.com", "");
	}
	
	@Test
	public void transferMoneyFromWalletToWalletValidSenderInvalidReciever() throws Exception{
		UserTransaction userTransactionSender = new UserTransaction();
		
		Mockito.when(userTransactionDAO.debitMoneyFromWallet(Mockito.any(UserTransaction.class), Mockito.anyInt())).thenReturn(userTransactionSender);
		Mockito.when(userTransactionDAO.creditMoneyToWallet(Mockito.any(UserTransaction.class), Mockito.anyString())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserTransactionService.RECIEVER_NOT_FOUND");
		userTransactionService.transferMoneyFromWalletToWallet(23.00, 12121, "bhanu@gmail.com", "");
	}
	
	@Test
	public void getAllTransactions() throws Exception{
		
		List<UserTransaction> userTransactions = new ArrayList<UserTransaction>();
		
		Mockito.when(userTransactionDAO.getAllTransactions(Mockito.anyInt())).thenReturn(userTransactions);
		
		userTransactions = userTransactionService.getAllTransactions(12121);
		Assert.assertNotNull(userTransactions);
		
	}
	
	@Test
	public void getAllTransactionsInvalidUser() throws Exception{
		
		Mockito.when(userTransactionDAO.getAllTransactions(Mockito.anyInt())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserTransactionService.USER_NOT_FOUND");
		
		userTransactionService.getAllTransactions(12121);
		
	}
	
	@Test
	public void getUserBalanceValidUser() throws Exception{
		
		List<UserTransaction> userTransactions = new ArrayList<>();
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setAmount(100.00);
		
		PaymentType paymentType = new PaymentType();
		paymentType.setPaymentType(WalletConstants.PAYMENT_TYPE_CREDIT.charAt(0));
		userTransaction.setPaymentType(paymentType);
		
		userTransactions.add(userTransaction);
		
		Mockito.when(userTransactionDAO.getAllTransactions(Mockito.anyInt())).thenReturn(userTransactions);
		
		List<UserTransaction> userTransactionsFromService = userTransactionService.getAllTransactions(12121);
		Boolean flag = false;
		if(BalanceCalculator.calculateBalance(userTransactionsFromService).doubleValue()==100.00){
			flag = true;
		}
		
		Assert.assertTrue(flag);
		
	}
}
