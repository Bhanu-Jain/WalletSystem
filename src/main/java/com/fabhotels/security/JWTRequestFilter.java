package com.fabhotels.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fabhotels.model.MyUserDetails;
import com.fabhotels.model.User;
import com.fabhotels.service.UserLoginService;

import io.jsonwebtoken.ExpiredJwtException;

/*
 *==============================================================
 *JWT REQUEST FILTER CLASS FOR AUTHENTICATION API REQUESTS
 *==============================================================
 *
 * This class extends the OncePerRequestFilter class top override its method to define logic 
 * for filtering all authorized and unauthorized requests.
 * 
 */

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	static Logger logger = org.apache.logging.log4j.LogManager.getLogger(JWTRequestFilter.class.getName());
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
	throws ServletException, IOException {
	
		final String requestTokenHeader = request.getHeader("Authorization");
		
		String emailId = null;
		String jwtToken = null;
		
		
		// JWT Token is in the form "Bearer token". Remove Bearer word and get
		// only the Token
		if (requestTokenHeader != null) {
			jwtToken = requestTokenHeader;
			try {
				emailId = jwtTokenUtil.getEmailIdFromToken(jwtToken);
				
			} catch (IllegalArgumentException e) {
				
				logger.warn("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				
				logger.warn("JWT Token has expired");
			}
		}else{
			logger.warn("No Authorization header with JWT token");
		}
			
		// Once we get the token validate it.
		if (emailId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			MyUserDetails userDetails = null;
			try {
				User user = userLoginService.authorizeUser(emailId);
				userDetails = new MyUserDetails(user.getEmailId(), user.getPassword());
			} catch (Exception e) {

				logger.error(e.getMessage(), e);
			}
			
			// if token is valid configure Spring Security to manually set
			// authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, new ArrayList<GrantedAuthority>());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}

}