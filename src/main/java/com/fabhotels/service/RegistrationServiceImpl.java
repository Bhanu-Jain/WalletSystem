package com.fabhotels.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabhotels.dao.RegistrationDAO;
import com.fabhotels.model.User;
import com.fabhotels.utility.HashingUtility;
import com.fabhotels.validator.RegistrationValidator;

/*============================================================
 *IMPLEMENTATION CLASS FOR THE SERVICE INTERFACE THAT HANDLES ALL THE BUSINESS LOGIC FOR USER REGISTRATION
 *============================================================
 *It overrides 2 methods from the interface:
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

@Service(value="registrationServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class RegistrationServiceImpl implements RegistrationService {
	
	@Autowired
	private RegistrationDAO registrationDAO;

	@Override
	public void validateUser(User user) throws Exception {

		RegistrationValidator.ValidateUserDetails(user);
		if(!registrationDAO.checkEmailAvailability(user.getEmailId())){
			throw new Exception("RegistrationService.EMAIL_ALREADY_EXISTS");
		}
		
		if(!registrationDAO.checkMobileNumberAvailability(user.getMobileNumber())){
			throw new Exception("RegistrationService.MOBILE_NUMBER_ALREADY_EXISTS");
		}
	}

	@Override
	public Integer registerUser(User user) throws Exception {

		user.setPassword(HashingUtility.getHashValue(user.getPassword()));
		user.setEmailId(user.getEmailId().toLowerCase());
		
		Integer userId = registrationDAO.registerNewUser(user);
		return userId;
	}

}
