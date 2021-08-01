package com.fabhotels.validator.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fabhotels.model.User;
import com.fabhotels.validator.RegistrationValidator;

/*
 * TEST CLASS THAT CONTAINS MULTIPLE TEST CASES TO UNIT TEST
 * THE VALIDATOR CLASS METHODS USED FOR VALIDATING USER REGISTRATION DETAILS
 * 
 * JUNIT AND MOCKITO IS USED FOR UNIT TESTING
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegistrationValidatorTest {

	@Rule
	ExpectedException ee = ExpectedException.none();
	
	@Test
	public void validateUserdetailsValid() throws Exception{
		
		User user = new User();
		user.setName("Bhanu");
		user.setPassword("Bhanu#123");
		user.setMobileNumber("7011236040");
		user.setEmailId("bhanu123@gmail.com");
		
		RegistrationValidator.ValidateUserDetails(user);
		Assert.assertTrue(true);
	}
	
	@Test
	public void validateUserdetailsInvalidEmail() throws Exception{
		
		User user = new User();
		user.setName("Bhanu");
		user.setPassword("Bhanu#123");
		user.setMobileNumber("7011236040");
		user.setEmailId("b.com");
		
		ee.expect(Exception.class);
		ee.expectMessage("RegistrationValidator.INVALID_USER_EMAIL_ID");
		
		RegistrationValidator.ValidateUserDetails(user);
	}
	
	@Test
	public void validateUserdetailsInvalidPassword() throws Exception{
		
		User user = new User();
		user.setName("Bhanu");
		user.setPassword("Bhan");
		user.setMobileNumber("7011236040");
		user.setEmailId("b@gmail.com");
		
		ee.expect(Exception.class);
		ee.expectMessage("RegistrationValidator.INVALID_USER_PASSWORD");
		
		RegistrationValidator.ValidateUserDetails(user);
	}
	
	@Test
	public void validateUserdetailsInvalidMobile() throws Exception{
		
		User user = new User();
		user.setName("Bhanu");
		user.setPassword("Bhanu#123");
		user.setMobileNumber("701123604045");
		user.setEmailId("b@gmail.com");
		
		ee.expect(Exception.class);
		ee.expectMessage("RegistrationValidator.INVALID_USER_MOBILE_NUMBER");
		
		RegistrationValidator.ValidateUserDetails(user);
	}
	@Test
	public void validateUserdetailsInvalidName() throws Exception{
		
		User user = new User();
		user.setName("Bha");
		user.setPassword("Bhanu#123");
		user.setMobileNumber("7011236040");
		user.setEmailId("b@gmail.com");
		
		ee.expect(Exception.class);
		ee.expectMessage("RegistrationValidator.INVALID_USER_NAME");
		
		RegistrationValidator.ValidateUserDetails(user);
	}
	
	
}
