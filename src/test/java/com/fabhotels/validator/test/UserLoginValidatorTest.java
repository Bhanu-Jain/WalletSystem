package com.fabhotels.validator.test;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fabhotels.model.User;
import com.fabhotels.validator.UserLoginValidator;

/*
 * TEST CLASS THAT CONTAINS MULTIPLE TEST CASES TO UNIT TEST
 * THE VALIDATOR CLASS METHODS USED FOR VALIDATING USER LOGIN DETAILS
 * 
 * JUNIT AND MOCKITO IS USED FOR UNIT TESTING
 * 
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserLoginValidatorTest {


	@Rule
	ExpectedException ee = ExpectedException.none();

	@Test
	public void validateUserLoginDetailsValid() throws Exception{
		
		User user = new User();
		user.setPassword("Bhanu#123");
		user.setEmailId("bhanu123@gmail.com");
		
		UserLoginValidator.validateUserLoginDetails(user);
		Assert.assertTrue(true);
	}
	
	@Test
	public void validateUserLoginDetailsInvalidEmail() throws Exception{
		
		User user = new User();
		user.setPassword("Bhanu#123");
		user.setEmailId("b.com");
		
		ee.expect(Exception.class);
		ee.expectMessage("RegistrationValidator.INVALID_CREDENTIALS");
		
		UserLoginValidator.validateUserLoginDetails(user);
	}
	
	@Test
	public void validateUserLoginDetailsInvalidPassword() throws Exception{
		
		User user = new User();
		user.setPassword("Bha");
		user.setEmailId("bhanu123@gmail.com");
		
		ee.expect(Exception.class);
		ee.expectMessage("RegistrationValidator.INVALID_CREDENTIALS");
		
		UserLoginValidator.validateUserLoginDetails(user);
	}
}
