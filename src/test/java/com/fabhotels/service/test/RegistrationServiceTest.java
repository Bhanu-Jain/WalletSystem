package com.fabhotels.service.test;

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
import com.fabhotels.model.User;
import com.fabhotels.service.RegistrationService;

/*
 * TEST CLASS THAT CONTAINS MULTIPLE TEST CASES TO UNIT TEST
 * THE SERVICE CLASS METHODS USED FOR USER REGISTRATION
 * 
 * JUNIT AND MOCKITO IS USED FOR UNIT TESTING
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationServiceTest {

	@Rule
	ExpectedException ee = ExpectedException.none();
	
	@MockBean
	private RegistrationDAO registrationDAO;
	
	@Autowired
	private RegistrationService registrationService;
	
	@Test
	public void validateUserValidDetails() throws Exception{
		
		User user = new User();
		user.setName("Bhanu");
		user.setPassword("Bhanu#123");
		user.setMobileNumber("7011236040");
		user.setEmailId("bhanu123@gmail.com");
		
		Mockito.when(registrationDAO.checkEmailAvailability(Mockito.anyString())).thenReturn(true);
		Mockito.when(registrationDAO.checkMobileNumberAvailability(Mockito.anyString())).thenReturn(true);
		
		registrationService.validateUser(user);
		Assert.assertTrue(true);
	}
	
	@Test
	public void validateUserExistingEmail() throws Exception{
		
		User user = new User();
		user.setName("Bhanu");
		user.setPassword("Bhanu#123");
		user.setMobileNumber("7011236040");
		user.setEmailId("bhanu@gmail.com");
		
		Mockito.when(registrationDAO.checkEmailAvailability(Mockito.anyString())).thenReturn(false);
		Mockito.when(registrationDAO.checkMobileNumberAvailability(Mockito.anyString())).thenReturn(true);
		
		ee.expect(Exception.class);
		ee.expectMessage("RegistrationService.EMAIL_ALREADY_EXISTS");
		
		registrationService.validateUser(user);
	}
	
	@Test
	public void validateUserNewEmailExistingMobile() throws Exception{
		
		User user = new User();
		user.setName("Bhanu");
		user.setPassword("Bhanu#123");
		user.setMobileNumber("9711236040");
		user.setEmailId("bhanu123@gmail.com");
		
		Mockito.when(registrationDAO.checkEmailAvailability(Mockito.anyString())).thenReturn(true);
		Mockito.when(registrationDAO.checkMobileNumberAvailability(Mockito.anyString())).thenReturn(false);
		
		ee.expect(Exception.class);
		ee.expectMessage("RegistrationService.MOBILE_NUMBER_ALREADY_EXISTS");
		
		registrationService.validateUser(user);
	}
	
	@Test
	public void registerUserValidDetails() throws Exception{
		
		User user = new User();
		user.setName("Bhanu");
		user.setPassword("Bhanu#123");
		user.setMobileNumber("7011236040");
		user.setEmailId("bhanu123@gmail.com");
		
		Mockito.when(registrationDAO.registerNewUser(Mockito.any(User.class))).thenReturn(123456);
		
		Integer userId = registrationService.registerUser(user);
		Assert.assertEquals(userId.intValue(), 123456);
	}
	
}
