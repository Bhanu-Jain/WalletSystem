package com.fabhotels.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

/*
 *==============================================================
 *JWT AUTHENTICATION CONTROLLER CLASS FOR USER AUTHENTICATION 
 *==============================================================
 *
 * This class is used to authenticate the user 
 * details extracted from the incoming JWT token.
 * 
 */

@Component
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	public void authenticate(String emailId, String password) throws Exception {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(emailId, password));
		} catch (DisabledException e) {
			
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
		
				throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}