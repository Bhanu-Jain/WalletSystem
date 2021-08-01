package com.fabhotels.dao.test;

import java.security.NoSuchAlgorithmException;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fabhotels.dao.RegistrationDAO;
import com.fabhotels.model.User;
import com.fabhotels.utility.HashingUtility;


/*
 * TEST CLASS THAT CONTAINS MULTIPLE TEST CASES TO UNIT TEST
 * THE DAO CLASS METHODS USED FOR USER REGISTRATION
 * 
 * JUNIT AND MOCKITO IS USED FOR UNIT TESTING
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class RegistrationDAOTest {
	
	@Autowired
	private RegistrationDAO registrationDAO;
	
	@Test
	public void addNewUser() throws NoSuchAlgorithmException{
		
		User user = new User();
		user.setName("Bhanu");
		user.setEmailId("b@gmail.com");
		user.setMobileNumber("7011631914");
		user.setPassword(HashingUtility.getHashValue("Bhanu123"));
		
		Integer userId = registrationDAO.registerNewUser(user);
		Assert.assertNotNull(userId);
	}
	
	@Test
	public void checkEmailAvailabilityValidEmail(){
		Boolean result = registrationDAO.checkEmailAvailability("james@gmail.com");
		Assert.assertTrue(result);
	}
	
	@Test
	public void checkEmailAvailabilityInvalidEmail(){
		Boolean result = registrationDAO.checkEmailAvailability("james.com");
		Assert.assertFalse(result);
	}
	
	@Test
	public void checkMobileNumberAvailabilityValidEmail(){
		Boolean result = registrationDAO.checkMobileNumberAvailability("9999999999");
		Assert.assertTrue(result);
	}
	
	@Test
	public void checkMobileNumberAvailabilityInvalidEmail(){
		Boolean result = registrationDAO.checkEmailAvailability("9711236040");
		Assert.assertFalse(result);
	}

}
