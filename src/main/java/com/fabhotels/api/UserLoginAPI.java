package com.fabhotels.api;


import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fabhotels.model.JWTResponse;
import com.fabhotels.model.User;
import com.fabhotels.security.JwtTokenUtil;
import com.fabhotels.service.UserLoginService;


/*============================================================
 *MAIN API CLASS THAT HANDLES ALL THE USER LOGIN AND PROFILE UPDATE RELATED APIs
 *============================================================
 *It contains 7 methods:
 *		authenticateUser():[POST -> /authenticateUser]
 *					This method accepts a User object as an input from the body 
 *					and returns a JWTResponse Object(with JWT authorization token)
 *					after successfully authenticating the provided user details.
 *
 *		getUserById():[GET -> /getUserById/{userId}]
 *					This method accepts a userId as a path variable input from the URL
 *					and returns a User Object fetched from Database(if any exists).
 *
 *		getUserByEmailId():[GET -> /getUserById/{emailId}]
 *					This method accepts a emailId as a path variable input from the URL
 *					and returns a User Object fetched from Database(if any exists).
 *
 *		changeUserpassword():[PUT -> /changeUserpassword]
 *					This method accepts a User object as an input from the body
 *					and returns a success message after successfully updating the user password.
 *
 *		changeUserEmail():[PUT -> /changeUserEmail]
 *					This method accepts a User object as an input from the body
 *					and returns a success message after successfully updating the user emailId.
 *
 *		changeUserMobile():[PUT -> /changeUserMobile]
 *					This method accepts a User object as an input from the body
 *					and returns a success message after successfully updating the user mobile number.
 *
 *		changeUserName():[PUT -> /changeUserName]
 *					This method accepts a User object as an input from the body
 *					and returns a success message after successfully updating the user name.
 *
 *=============================================================
 */
@RestController
@RequestMapping("/UserLoginAPI")
public class UserLoginAPI {

	@Autowired
	private Environment environment;
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	static Logger logger = LogManager.getLogger(UserLoginAPI.class.getName());
	
	@RequestMapping(value="/authenticateUser", method = RequestMethod.POST)
	public ResponseEntity<JWTResponse> authenticateUser(@RequestBody User user) throws Exception{
		
		ResponseEntity<JWTResponse> responseEntity = null;
		
		try{
			
			logger.info("USER TRYING TO AUTHENTICATE, VALIDATING CREDENTIALS -> EMAILID:" + user.getEmailId());
			
			boolean isUserAuthenticated = userLoginService.authenticateUser(user);
			if(!isUserAuthenticated){
				throw new Exception("UserLoginAPI.USER_NOT_AUTHENTICATED");
			}
			final String token = jwtTokenUtil.generateToken(user.getEmailId());
			JWTResponse jwtResponse = new JWTResponse();
			jwtResponse.setJwtToken(token);
			
			responseEntity = new ResponseEntity<JWTResponse>(jwtResponse, HttpStatus.OK);
			
			logger.info("USER AUTHENTICATION SUCCESS -> EMAILID:" + user.getEmailId());
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, environment.getProperty(e.getMessage()));
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/getUserById/{userId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable Integer userId) throws Exception{
		
		ResponseEntity<User> responseEntity = null;
		
		try{
			
			logger.info("FETCHING DATA FOR USERID: " + userId);
			
			User userFetched = userLoginService.getUserById(userId);
			
			responseEntity = new ResponseEntity<User>(userFetched, HttpStatus.OK);
			
			logger.info("DATA FETCHED SUCESSFULLY FOR USERID: " + userId);
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/getUserByEmailId/{emailId}", method = RequestMethod.GET)
	public ResponseEntity<User> getUserByEmailId(@PathVariable String emailId) throws Exception{
		
		ResponseEntity<User> responseEntity = null;
		
		try{
			
			logger.info("FETCHING DATA FOR EMAILID: " + emailId);
			
			User userFetched = userLoginService.getUserByEmailId(emailId);
			
			responseEntity = new ResponseEntity<User>(userFetched, HttpStatus.OK);
			
			logger.info("DATA FETCHED SUCESSFULLY FOR EMAILID: " + emailId);
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/changeUserpassword", method = RequestMethod.PUT)
	public ResponseEntity<String> changeUserpassword(@RequestBody User user) throws Exception{
		
		ResponseEntity<String> responseEntity = null;
		
		try{
			
			logger.info("TRYING TO CHANGE PASSWORD FOR USERID:" + user.getUserId());
			
			String message = userLoginService.changeUserpassword(user.getUserId(), user.getNewPassword());
			
			responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);
			
			logger.info("SUCCESSFULLY CHANGED PASSWORD FOR USERID:" + user.getUserId());
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
			
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/changeUserMobile", method = RequestMethod.PUT)
	public ResponseEntity<String> changeUserMobile(@RequestBody User user) throws Exception{
		
		ResponseEntity<String> responseEntity = null;
		
		try{
			
			logger.info("TRYING TO CHANGE MOBILE NUMBER FOR USERID:" + user.getUserId());
			
			String message = userLoginService.changeUserMobile(user.getUserId(), user.getMobileNumber());
			
			responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);
			
			logger.info("SUCCESSFULLY CHANGED MOBILE NUMBER FOR USERID:" + user.getUserId());
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
			
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/changeUserEmail", method = RequestMethod.PUT)
	public ResponseEntity<String> changeUserEmail(@RequestBody User user) throws Exception{
		
		ResponseEntity<String> responseEntity = null;
		
		try{
			
			logger.info("TRYING TO CHANGE EMAIL FOR USERID:" + user.getUserId());
			
			String message = userLoginService.changeUserEmail(user.getUserId(), user.getEmailId());
			
			responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);
			
			logger.info("SUCCESSFULLY CHANGED EMAIL FOR USERID:" + user.getUserId());
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
			
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/changeUserName", method = RequestMethod.PUT)
	public ResponseEntity<String> changeUserName(@RequestBody User user) throws Exception{
		
		ResponseEntity<String> responseEntity = null;
		
		try{
			
			logger.info("TRYING TO CHANGE USERNAME FOR USERID:" + user.getUserId());
			
			String message = userLoginService.changeUserName(user.getUserId(), user.getName());
			
			responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);
			
			logger.info("SUCCESSFULLY CHANGED USERNAME FOR USERID:" + user.getUserId());
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
			
		}
		
		return responseEntity;
	}
}
