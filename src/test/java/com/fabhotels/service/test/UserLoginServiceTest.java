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

import com.fabhotels.dao.RegistrationDAO;
import com.fabhotels.dao.UserLoginDAO;
import com.fabhotels.model.PaymentType;
import com.fabhotels.model.User;
import com.fabhotels.model.UserTransaction;
import com.fabhotels.service.UserLoginService;
import com.fabhotels.utility.HashingUtility;
import com.fabhotels.utility.WalletConstants;

/*
 * TEST CLASS THAT CONTAINS MULTIPLE TEST CASES TO UNIT TEST
 * THE SERVICE CLASS METHODS USED FOR USER LOGIN AND PROFILE UPDATE
 * 
 * JUNIT AND MOCKITO IS USED FOR UNIT TESTING
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLoginServiceTest {

	@Rule
	ExpectedException ee = ExpectedException.none();
	
	@MockBean
	private UserLoginDAO userLoginDAO;
	
	@MockBean
	private RegistrationDAO registrationDAO;
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Test
	public void authenticateUserValidDetails() throws Exception{
		
		User user = new User();
		user.setPassword("Bhanu123");
		user.setEmailId("bhanu123@gmail.com");
		
		User userFromDAO = new User();
		userFromDAO.setPassword(HashingUtility.getHashValue("Bhanu#123"));
		userFromDAO.setEmailId("bhanu123@gmail.com");
		
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setAmount(100.00);
		
		PaymentType paymentType = new PaymentType();
		paymentType.setPaymentType(WalletConstants.PAYMENT_TYPE_CREDIT.charAt(0));
		userTransaction.setPaymentType(paymentType);
		
		List<UserTransaction> userTransactions = new ArrayList<UserTransaction>();
		userTransactions.add(userTransaction);
		
		Mockito.when(userLoginDAO.authenticateUser(Mockito.anyString())).thenReturn(userFromDAO);
		
		boolean isUserAuthenticated = userLoginService.authenticateUser(user);
		Assert.assertTrue(isUserAuthenticated);
	}
	
	@Test
	public void authenticateUserInvalidUser() throws Exception{
		
		User user = new User();
		user.setPassword("Bhanu#123");
		user.setEmailId("bhanu123@gmail.com");
		
		
		Mockito.when(userLoginDAO.authenticateUser(Mockito.anyString())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserLoginService.INVALID_USERNAME");

		userLoginService.authenticateUser(user);
	}
	
	@Test
	public void authenticateUserInvalidPassword() throws Exception{
		
		User user = new User();
		user.setPassword("Bhanu#123");
		user.setEmailId("bhanu123@gmail.com");
		
		User userFromDAO = new User();
		userFromDAO.setPassword(HashingUtility.getHashValue("Bhan123"));
		userFromDAO.setEmailId("bhanu123@gmail.com");
		
		Mockito.when(userLoginDAO.authenticateUser(Mockito.anyString())).thenReturn(userFromDAO);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserLoginService.INVALID_PASSWORD");

		userLoginService.authenticateUser(user);
	}
	
	@Test
	public void getuserByIdValidUserId() throws Exception{
		
		User userFromDAO = new User();
		userFromDAO.setPassword(HashingUtility.getHashValue("Bhanu#123"));
		userFromDAO.setEmailId("bhanu123@gmail.com");
		
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setAmount(100.00);
		
		PaymentType paymentType = new PaymentType();
		paymentType.setPaymentType(WalletConstants.PAYMENT_TYPE_CREDIT.charAt(0));
		userTransaction.setPaymentType(paymentType);
		
		List<UserTransaction> userTransactions = new ArrayList<UserTransaction>();
		userTransactions.add(userTransaction);
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(userFromDAO);
		
		User userFromService = userLoginService.getUserById(12121);
		Assert.assertTrue(userFromService.getBalance().equals(100.00));
	}
	
	@Test
	public void getUserByIdInvalidUserId() throws Exception{
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserLoginService.INVALID_CREDENTIALS");

		userLoginService.getUserById(123456);
	}
	
	@Test
	public void getuserByEmailIdValidUserId() throws Exception{
		
		User userFromDAO = new User();
		userFromDAO.setPassword(HashingUtility.getHashValue("Bhanu#123"));
		userFromDAO.setEmailId("bhanu@gmail.com");
		
		UserTransaction userTransaction = new UserTransaction();
		userTransaction.setAmount(100.00);
		
		PaymentType paymentType = new PaymentType();
		paymentType.setPaymentType(WalletConstants.PAYMENT_TYPE_CREDIT.charAt(0));
		userTransaction.setPaymentType(paymentType);
		
		List<UserTransaction> userTransactions = new ArrayList<UserTransaction>();
		userTransactions.add(userTransaction);
		
		Mockito.when(userLoginDAO.getUserByEmailId(Mockito.anyString())).thenReturn(userFromDAO);
		
		User userFromService = userLoginService.getUserById(12121);
		Assert.assertTrue(userFromService.getBalance().equals(100.00));
	}
	
	@Test
	public void getUserByEmailIdInvalidUserId() throws Exception{
		
		Mockito.when(userLoginDAO.getUserByEmailId(Mockito.anyString())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserLoginService.INVALID_CREDENTIALS");

		userLoginService.getUserById(123456);
	}
	
	@Test
	public void changeUserPaswordValidUser() throws Exception{
		
		User userFromDAO = new User();
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(userFromDAO);
		Mockito.when(userLoginDAO.changeUserpassword(Mockito.anyInt(), Mockito.anyString())).thenReturn(WalletConstants.PASSWORD_UPDATED);
		

		String message = userLoginService.changeUserpassword(12121, "bhanu123");
		Assert.assertEquals(message, WalletConstants.PASSWORD_UPDATED);
	}
	
	@Test
	public void changeUserPaswordInvalidUser() throws Exception{
		
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserLoginService.USER_NOT_FOUND");

		userLoginService.changeUserpassword(121266, "bhanu123");
	}
	
	@Test
	public void changeUserNameValidUser() throws Exception{
		
		User userFromDAO = new User();
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(userFromDAO);
		Mockito.when(userLoginDAO.changeUserName(Mockito.anyInt(), Mockito.anyString())).thenReturn(WalletConstants.NAME_UPDATED);
		

		String message = userLoginService.changeUserName(12121, "bhanu");
		Assert.assertEquals(message, WalletConstants.NAME_UPDATED);
	}
	
	@Test
	public void changeUserNameInvalidUser() throws Exception{
		
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserLoginService.USER_NOT_FOUND");

		userLoginService.changeUserpassword(121266, "bhanu");
	}
	
	@Test
	public void changeUserEmailValidUser() throws Exception{
		
		User userFromDAO = new User();
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(userFromDAO);
		Mockito.when(registrationDAO.checkEmailAvailability(Mockito.anyString())).thenReturn(true);
		Mockito.when(userLoginDAO.changeUserName(Mockito.anyInt(), Mockito.anyString())).thenReturn(WalletConstants.NAME_UPDATED);
		
		

		String message = userLoginService.changeUserEmail(12121, "bhanu123@gmail.com");
		Assert.assertEquals(message, WalletConstants.NAME_UPDATED);
	}
	
	@Test
	public void changeUserEmailInvalidUser() throws Exception{
		
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserLoginService.USER_NOT_FOUND");

		userLoginService.changeUserEmail(21266, "bhanu123@gmail.com");
	}
	
	@Test
	public void changeUserEmailValidUserInvalidEmailId() throws Exception{
		
		User userFromDAO = new User();
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(userFromDAO);
		Mockito.when(registrationDAO.checkEmailAvailability(Mockito.anyString())).thenReturn(false);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserLoginService.EMAIL_ALREADY_EXISTS");

		userLoginService.changeUserEmail(12121, "b.com");
	}
	
	@Test
	public void changeUserMobileValidUser() throws Exception{
		
		User userFromDAO = new User();
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(userFromDAO);
		Mockito.when(registrationDAO.checkMobileNumberAvailability(Mockito.anyString())).thenReturn(true);
		Mockito.when(userLoginDAO.changeUserMobile(Mockito.anyInt(), Mockito.anyString())).thenReturn(WalletConstants.MOBILE_NUMBER_UPDATED);
		
		

		String message = userLoginService.changeUserMobile(12121, "9999999999");
		Assert.assertEquals(message, WalletConstants.MOBILE_NUMBER_UPDATED);
	}
	
	@Test
	public void changeUserMobileInvalidUser() throws Exception{
		
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(null);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserLoginService.USER_NOT_FOUND");

		userLoginService.changeUserMobile(21266, "9999999999");
	}
	
	@Test
	public void changeUserMobileValidUserInvalidMobileId() throws Exception{
		
		User userFromDAO = new User();
		
		Mockito.when(userLoginDAO.getUserByUserId(Mockito.anyInt())).thenReturn(userFromDAO);
		Mockito.when(registrationDAO.checkMobileNumberAvailability(Mockito.anyString())).thenReturn(false);
		
		ee.expect(Exception.class);
		ee.expectMessage("UserLoginService.MOBILE_NUMBER_ALREADY_EXISTS");

		userLoginService.changeUserMobile(12121, "999999");
	}
	
	
}
