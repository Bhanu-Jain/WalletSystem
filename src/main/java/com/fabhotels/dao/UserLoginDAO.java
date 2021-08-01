package com.fabhotels.dao;

import com.fabhotels.model.User;

/*============================================================
 *REPOSITORY INTERFACE THAT HANDLES ALL THE ACTUAL DB TRANSACTIONS FOR USER LOGIN AND PROFILE UPDATE
 *============================================================
 *It contains 7 methods:
 *		getUserByEmailId(String emailId):[
 *					This method accepts a user emailId as an input 
 *					and checks if any user exists in database for that emailId
 *					and it will return the user object from database if it exists.
 *
 *		changeUserpassword(Integer userId, String password):
 *					This method accepts a userId and new password as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will update the user password.
 *
 *		changeUserEmail(Integer userId, String emailId):
 *					This method accepts a userId and new emailId as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will update the user emailId.
 *
 *		changeUserMobile(Integer userId, String mobileNumber):
 *					This method accepts a userId and new mobileNumber as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will update the user mobileNumber.
 *
 * 		changeUserName(Integer userId, String name):
 *					This method accepts a userId and new UserName as an input 
 *					and checks if user with the provided userId exists in Database and if it exists
 *					then it will update the user UserName.
 * 
 * 		getUserByUserId(Integer userId):
 *					This method accepts a userId as an input 
 *					and checks if any user exists in database for that userId
 *					and it will return the user object from database if it exists.
 *
 *		authenticateUser(String emailId):
 *					This method accepts a user emailId as an input 
 *					and checks if any user exists in database for that emailId
 *					and it will return the user object from database if it exists.
 *
 *=============================================================
 */
public interface UserLoginDAO {

	public User getUserByEmailId(String emailId) throws Exception;
	public String changeUserpassword(Integer userId, String password);
	public String changeUserEmail(Integer userId, String emailId);
	public String changeUserMobile(Integer userId, String mobileNumber);
	public String changeUserName(Integer userId, String name);
	public User getUserByUserId(Integer userId);
	public User authenticateUser(String emailId);
	
}
