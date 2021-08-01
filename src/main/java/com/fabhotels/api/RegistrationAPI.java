package com.fabhotels.api;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fabhotels.model.User;
import com.fabhotels.service.RegistrationService;


/*============================================================
 *MAIN API CLASS THAT HANDLES ALL THE USER REGISTRATION RELATED APIs
 *============================================================
 *It contains 2 methods:
 *		validateUserForRegistration():[POST -> /validateUserForRegistration]
 *					This method accepts a User object as an input from the body  
 *					and returns a success message if the provided user details are valid for registration.
 *
 *		userRegistration():[POST -> /userRegistration]
 *					This method accepts a User object as an input from the body  
 *					and returns a success message(along with registration Id) after registering the new user.
 *
 *=============================================================
 */
@RestController
@RequestMapping("/RegistrationAPI")
public class RegistrationAPI {
	
	@Autowired
	private Environment environment;
	
	@Autowired
	private RegistrationService registrationService;
	
	static Logger logger = LogManager.getLogger(RegistrationAPI.class.getName());
	
	@RequestMapping(value="/validateForRegistration", method = RequestMethod.POST)
	public ResponseEntity<String> validateUserForRegistration(@RequestBody User user){
		
		ResponseEntity<String> responseEntity = null;
		
		try{
			logger.info("VALIDATING USER DETAILS FOR USERNAME: " + user.getName() + " EMAIL-ID: " + user.getEmailId() + " pass:" + user.getPassword());
			
			registrationService.validateUser(user);
			responseEntity = new ResponseEntity<String>(environment.getProperty("RegistrationAPI.VALIDATED_SUCCESSFULLY"), HttpStatus.ACCEPTED);
			
			logger.info("SUCCESSFULLY VALIDATED USER DETAILS FOR USERNAME: " + user.getName() + " EMAIL-ID: " + user.getEmailId());
			
		}catch(Exception e){
			
			if(e!=null && e.getMessage()!=null && e.getMessage().contains("Validator")){
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, environment.getProperty(e.getMessage()));
			}
			throw new ResponseStatusException(HttpStatus.CONFLICT, environment.getProperty(e.getMessage()));
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/registerUser", method = RequestMethod.POST)
	public ResponseEntity<String> userRegistration(@RequestBody User user){
		
		ResponseEntity<String> responseEntity = null;
		
		try{
			
			logger.info("USER TRYING TO REGISTER -> USEREMAIL: " + user.getEmailId());
			registrationService.validateUser(user);
			
			Integer userRegistrationId = registrationService.registerUser(user);
			
			String message = environment.getProperty("RegistrationAPI.REGISTERED_SUCCESSFULLY") + userRegistrationId;
			responseEntity = new ResponseEntity<String>(message, HttpStatus.CREATED);
			
			logger.info("USER WITH EMAIL: " + user.getEmailId() + " REGISTERED WITH REGISTRATION ID: " + userRegistrationId) ;
			
		}catch(Exception e){
			
			if(e!=null && e.getMessage()!=null && e.getMessage().contains("Validator")){
				throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, environment.getProperty(e.getMessage()));
			}
			throw new ResponseStatusException(HttpStatus.CONFLICT, environment.getProperty(e.getMessage()));
		}
		
		return responseEntity;
	}

}
