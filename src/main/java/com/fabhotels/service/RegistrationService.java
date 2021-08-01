package com.fabhotels.service;

import com.fabhotels.model.User;

/*============================================================
 *SERVICE INTERFACE THAT HANDLES ALL THE BUSINESS LOGIC FOR USER REGISTRATION
 *============================================================
 *It contains 2 methods:
 *		validateUser(String emailId):[
 *					This method accepts a User object as an input 
 *					and checks if the emailId/mobile number provided already exists in Database for any user
 *					and it will throw exceptions if the details already exists.
 *
 *		registerUser(User user):
 *					This method accepts a User object as an input
 *					and returns the registration Id on successful user registration.
 *
 *=============================================================
 */
public interface RegistrationService {

	public void validateUser(User user) throws Exception;
	public Integer registerUser(User user) throws Exception;
	
}
