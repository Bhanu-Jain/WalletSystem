package com.fabhotels.service;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabhotels.dao.RegistrationDAO;
import com.fabhotels.dao.UserLoginDAO;
import com.fabhotels.model.User;
import com.fabhotels.utility.BalanceCalculator;
import com.fabhotels.utility.HashingUtility;
import com.fabhotels.validator.RegistrationValidator;
import com.fabhotels.validator.UserLoginValidator;

/*============================================================
 *IMPLEMENTATION CLASS FOR SERVICE INTERFACE THAT HANDLES ALL THE BUSINESS LOGIC FOR USER LOGIN AND PROFILE UPDATE
 *============================================================
 *It overrides 7 methods from the interface:
 *		authenticateUser(User user):[
 *					This method accepts a User object as an input and validate the user details 
 *					and authenticates the user in database and returns tru if user is authenticated.
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
@Service(value="userLoginServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class UserLoginServiceImpl implements UserLoginService {

	
	@Autowired
	private UserLoginDAO userLoginDAO;
	
	@Autowired
	private RegistrationDAO regisrationDAO;
	
	
	@Override
	public boolean authenticateUser(User user) throws Exception {

		UserLoginValidator.validateUserLoginDetails(user);
		User userFromDAO = userLoginDAO.authenticateUser(user.getEmailId());
		
		if(userFromDAO==null){
			throw new Exception("UserLoginService.INVALID_EMAILID");
		}
		
		String hashedPassword = HashingUtility.getHashValue(user.getPassword());
		
		if(!hashedPassword.equals(userFromDAO.getPassword())){
			throw new Exception("UserLoginService.INVALID_PASSWORD");
		}
		
		return true;
	}
	
	@Override
	public User authorizeUser(String emailId) throws Exception {

		User userFromDAO = userLoginDAO.authenticateUser(emailId);
		
		if(userFromDAO==null){
			throw new Exception("UserLoginService.INVALID_EMAILID");
		}
		
		return userFromDAO;
	}
	

	@Override
	public String changeUserpassword(Integer userId, String password) throws Exception {

		RegistrationValidator.validatePassword(password);
		
		User userFromDAO = userLoginDAO.getUserByUserId(userId);
		
		if(userFromDAO==null){
			throw new Exception("UserLoginService.USER_NOT_FOUND");
		}
		
		String message = userLoginDAO.changeUserpassword(userId, HashingUtility.getHashValue(password));
		return message;
	}

	@Override
	public String changeUserEmail(Integer userId, String emailId) throws Exception {

		RegistrationValidator.validateEmail(emailId);
		
		User userFromDAO = userLoginDAO.getUserByUserId(userId);
		
		if(userFromDAO==null){
			throw new Exception("UserLoginService.USER_NOT_FOUND");
		}
		
		if(!regisrationDAO.checkEmailAvailability(emailId)){
			throw new Exception("UserLoginService.EMAIL_ALREADY_EXISTS");
		}
		
		String message = userLoginDAO.changeUserEmail(userId, emailId);
		return message;
	}

	@Override
	public String changeUserMobile(Integer userId, String mobileNumber) throws Exception {

		RegistrationValidator.validateMobileNumber(mobileNumber);
		
		User userFromDAO = userLoginDAO.getUserByUserId(userId);
		
		if(userFromDAO==null){
			throw new Exception("UserLoginService.USER_NOT_FOUND");
		}

		if(!regisrationDAO.checkMobileNumberAvailability(mobileNumber)){
			throw new Exception("UserLoginService.MOBILE_NUMBER_ALREADY_EXISTS");
		}

		String message = userLoginDAO.changeUserMobile(userId, mobileNumber);
		return message;
	}

	@Override
	public String changeUserName(Integer userId, String name) throws Exception {

		RegistrationValidator.validateName(name);
		
		User userFromDAO = userLoginDAO.getUserByUserId(userId);
		
		if(userFromDAO==null){
			throw new Exception("UserLoginService.USER_NOT_FOUND");
		}

		String message = userLoginDAO.changeUserName(userId, name);
		return message;
	}

	@Override
	public User getUserById(Integer userId) throws Exception {

		User userFromDAO = userLoginDAO.getUserByUserId(userId);
		if(userFromDAO==null){
			throw new Exception("UserLoginService.INVALID_USERID");
		}
		
		userFromDAO.setPassword(null);
		
		Double balance = BalanceCalculator.calculateBalance(userFromDAO.getUserTransactions());
		userFromDAO.setBalance(balance);
		
		
		return userFromDAO;
	}



	@Override
	public User getUserByEmailId(String emailId) throws Exception {

		
		User userFromDAO = userLoginDAO.getUserByEmailId(emailId);
		if(userFromDAO==null){
			throw new Exception("UserLoginService.INVALID_EMAILID");
		}
		
		userFromDAO.setPassword(null);
		
		Double balance = BalanceCalculator.calculateBalance(userFromDAO.getUserTransactions());
		userFromDAO.setBalance(balance);
		
		return userFromDAO;
	}

}
