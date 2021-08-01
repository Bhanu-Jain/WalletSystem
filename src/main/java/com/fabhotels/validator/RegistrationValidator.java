package com.fabhotels.validator;

import com.fabhotels.model.User;

/*============================================================
 *VALIDATOR CLASS TO VALIDATE USER REGISTRATION DETAILS
 *============================================================
 *It contains 5 methods:
 *		ValidateUserDetails(User user):[
 *					This method takes a user object as an input 
 *					and calls the other 4 methods in order to validate details 
 *					and throws appropriate exceptions if any detail is invalid
 *
 *		validateName(String name):[
 *					This method takes user name as input 
 *					any returns true if user name consists of only alphabets and spaces. 
 *					Otherwise, it returns false.
 *
 *		validateEmail(String emailId):[
 *					This method takes user emailId as input 
 *					any returns true if emailId is in valid format. 
 *					Otherwise, it returns false.
 *
 *		validateMobileNumber(String mobileNumber):[
 *					This method takes user mobileNumber as input 
 *					any returns true if mobileNumber consists of exactly 10 numeric digits. 
 *					Otherwise, it returns false.
 *
 *		validatePassword(String password):[
 *					This method takes user name as input 
 *					any returns true if password length is greater than or equal to 6. 
 *					Otherwise, it returns false.
 *
 *=============================================================
 */

public class RegistrationValidator {

	public static void ValidateUserDetails(User user) throws Exception{
		
		if(!validateName(user.getName())){
			throw new Exception("RegistrationValidator.INVALID_USER_NAME");
		}
		if(!validateEmail(user.getEmailId())){
			throw new Exception("RegistrationValidator.INVALID_USER_EMAIL_ID");
		}
		if(!validateMobileNumber(user.getMobileNumber())){
			throw new Exception("RegistrationValidator.INVALID_USER_MOBILE_NUMBER");
		}
		if(!validatePassword(user.getPassword())){
			throw new Exception("RegistrationValidator.INVALID_USER_PASSWORD");
		}
	}
	
	public static boolean validateName(String name){
		
		if(name!="" && name.matches("[A_Za-z ]+")){
			return true;
		}
		return false;
	}
	
	public static boolean validateEmail(String emailId){
		
		if(emailId.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")){
			return true;
		}
		
		return false;
	}
	
	public static boolean validateMobileNumber(String mobileNumber){
		
		if(mobileNumber.length()==10 && mobileNumber.matches("[0-9]*")){
			return true;
		}
		return false;
	}
	
	public static boolean validatePassword(String password){
		
		if(password.length()>=6){
			return true;
		}
		return false;
	}
}
