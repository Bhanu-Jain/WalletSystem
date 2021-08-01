package com.fabhotels.dao;

import com.fabhotels.model.User;

/*============================================================
 *REPOSITORY INTERFACE THAT HANDLES ALL THE ACTUAL DB TRANSACTIONS FOR USER REGISTRATION
 *============================================================
 *It contains 3 methods:
 *		checkEmailAvailability(String emailId):[
 *					This method accepts a user emailId as an input 
 *					and checks if the email already exists in Database for any user
 *					and it will return a true if email does not exists and return false if email exists.
 *
 *		checkMobileNumberAvailability(String mobileNumber):
 *					This method accepts a user mobile number as an input 
 *					and checks if the mobile number already exists in Database for any user
 *					and it will return a true if mobile number does not exists and return false if mobile number exists.
 *
 *		registerNewUser(User user):
 *					This method accepts a User object as an input
 *					and returns a success message after successfully updating the user password.
 *
 *=============================================================
 */
public interface RegistrationDAO {

	public Boolean checkEmailAvailability(String emailId);
	public Boolean checkMobileNumberAvailability(String mobileNumber);
	public Integer registerNewUser(User user);

}
