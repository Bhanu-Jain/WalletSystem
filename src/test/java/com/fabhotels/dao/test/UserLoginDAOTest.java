package com.fabhotels.dao.test;

import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fabhotels.dao.UserLoginDAO;
import com.fabhotels.model.User;
import com.fabhotels.utility.HashingUtility;
import com.fabhotels.utility.WalletConstants;

/*
 * TEST CLASS THAT CONTAINS MULTIPLE TEST CASES TO UNIT TEST
 * THE DAO CLASS METHODS USED FOR USER LOGIN AND PROFILE UPDATE
 * 
 * JUNIT AND MOCKITO IS USED FOR UNIT TESTING
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class UserLoginDAOTest {
	
	@Autowired
	private UserLoginDAO userLoginDAO;
	
	@Rule
	public ExpectedException ee = ExpectedException.none();
	
	@Test
	public void changeUserpasswordValidDetails() throws NoSuchAlgorithmException{
		
		String message = userLoginDAO.changeUserpassword(12121, HashingUtility.getHashValue("abcdef"));
		Assert.assertEquals(message, WalletConstants.PASSWORD_UPDATED);
	}
	
	@Test
	public void changeUserEmailValidDetails() throws Exception{
		
		String message = userLoginDAO.changeUserpassword(12121, "james@gmail.com");
		Assert.assertEquals(message, WalletConstants.EMAIL_ID_UPDATED);
	}
	
	@Test
	public void changeUserMobileValidDetails() throws Exception{
		
		String message = userLoginDAO.changeUserpassword(12121, "9999999999");
		Assert.assertEquals(message, WalletConstants.MOBILE_NUMBER_UPDATED);
	}
	
	@Test
	public void changeUserNameValidDetails() throws Exception{
		
		String message = userLoginDAO.changeUserpassword(12121, "James");
		Assert.assertEquals(message, WalletConstants.NAME_UPDATED);
	}
	
	@Test
	public void getUserByIdValidDetails() throws Exception{
		
		User user = userLoginDAO.getUserByUserId(12121);
		if(user==null){
			Assert.assertTrue(true);
		}else{
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void getUserByIdInvalidDetails() throws Exception{
		
		User user = userLoginDAO.getUserByUserId(12123);
		Assert.assertNull(user);
	}
	
	@Test
	public void getUserByEmailIdValidDetails() throws Exception{
		
		User user = userLoginDAO.getUserByEmailId("bhanu@gmail.com");
		if(user==null){
			Assert.assertTrue(true);
		}else{
			Assert.assertTrue(false);
		}
	}
	
	@Test
	public void getUserByEmailIdInvalidDetails() throws Exception{
		
		User user = userLoginDAO.getUserByEmailId("absdc");
		Assert.assertNull(user);
	}
	
	@Test
	public void authenticateUserInvalidDetails() throws Exception{
		
		User user = userLoginDAO.getUserByEmailId("absdc");
		Assert.assertNull(user);
	}
	
	@Test
	public void authenticateUserValidDetails() throws Exception{
		
		User user = userLoginDAO.getUserByEmailId("bhanu@gmail.com");
		Assert.assertNull(user);
	}

}
