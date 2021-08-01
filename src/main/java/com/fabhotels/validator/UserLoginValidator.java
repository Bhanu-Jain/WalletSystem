package com.fabhotels.validator;

import com.fabhotels.model.User;

/*============================================================
 *VALIDATOR CLASS TO VALIDATE USER LOGIN DETAILS
 *============================================================
 *It contains 2 methods:
 *		validateUserEmail(String emailId):[
 *					This method takes user emailId as input 
 *					any returns true if emailId is in valid format. 
 *					Otherwise, it returns false.
 *
 *		validateUserPassword(String password):[
 *					This method takes user name as input 
 *					any returns true if password length is greater than or equal to 6. 
 *					Otherwise, it returns false.
 *
 *=============================================================
 */

public class UserLoginValidator {

	
		public static void validateUserLoginDetails(User user) throws Exception{
			
			if(!validateUserEmail(user.getEmailId())){
				throw new Exception("UserLoginValidator.INVALID_CREDENTIALS");
			}
			if(!validateUserPassword(user.getPassword())){
				throw new Exception("UserLoginValidator.INVALID_CREDENTIALS");
			}
			
		}
	
		public static boolean validateUserEmail(String emailId){
			
			if(emailId.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
				return true;
			}
			
			return false;
		}
		
		public static boolean validateUserPassword(String password){
			
			if(password.length()>=6){
				return true;
			}
			return false;
		}
}
