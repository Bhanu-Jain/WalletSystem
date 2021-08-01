package com.fabhotels.service;

import com.fabhotels.model.User;

/*============================================================
 *SERVICE INTERFACE THAT HANDLES ALL THE BUSINESS LOGIC FOR USER LOGIN AND PROFILE UPDATE
 *============================================================
 *It contains 7 methods:
 *		authenticateUser(User user):[
 *					This method accepts a User object as an input and validate the user details 
 *					and authenticates the user in database and returns true if user is authenticated.
 *					Otherwise, returns false.
 *
 *		changeUserpassword(Integer userId, String password):
 *					This method accepts a userId and new password as an input 
 *					and returns a success message after successfully updating the user password.
 *
 *		changeUserEmail(Integer userId, String emailId):
 *					This method accepts a userId and new emailId as an input 
 *					and returns a success message after successfully updating the user emailId.
 *
 *		changeUserMobile(Integer userId, String mobileNumber):
 *					This method accepts a userId and new mobileNumber as an input 
 *					and returns a success message after successfully updating the user mobileNumber.
 *
 * 		changeUserName(Integer userId, String name):
 *					This method accepts a userId and new UserName as an input 
 *					and returns a success message after successfully updating the user name.
 * 
 * 		getUserByUserId(Integer userId):
 *					This method accepts a userId as an input 
 *					and checks if any user exists in database for that userId
 *					and it will return the user object from database if it exists.
 *
 *		getUserByEmailId(String emailId):
 *					This method accepts a emailId as an input 
 *					and checks if any user exists in database for that emailId
 *					and it will return the user object from database if it exists.
 *
 *=============================================================
 */

public interface UserLoginService {

	public boolean authenticateUser(User user) throws Exception;
	public String changeUserpassword(Integer userId, String password) throws Exception;
	public String changeUserEmail(Integer userId, String emailId) throws Exception;
	public String changeUserMobile(Integer userId, String mobileNumber) throws Exception;
	public String changeUserName(Integer userId, String name) throws Exception;
	public User getUserById(Integer UserId) throws Exception;
	public User getUserByEmailId(String emailId) throws Exception;
	public User authorizeUser(String emailId) throws Exception;
}
