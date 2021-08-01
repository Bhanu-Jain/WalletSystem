package com.fabhotels.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fabhotels.model.MyUserDetails;
import com.fabhotels.model.User;

/*
 *==============================================================
 *SERVICE CLASS FOR USER CUSTOM UserDetailsService IMPLEMENTATION 
 *==============================================================
 *
 * This class extends the UserDetailsService to provide a 
 * custom implementation for the same to use for API request authentication.
 * 
 */

@Service(value="UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService{

	
	@Autowired
	private UserLoginService userLoginService;
	
	@Override
	public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
		
		try {
			User user = userLoginService.authorizeUser(emailId);
			return new MyUserDetails(user.getEmailId(), user.getPassword());
			
		} catch (Exception e) {

			System.out.println(e.getMessage()+ "/n" +  e);
		}
		return null;
	}

}
