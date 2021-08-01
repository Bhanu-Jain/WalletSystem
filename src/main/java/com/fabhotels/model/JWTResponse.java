package com.fabhotels.model;

/*
 * MODEL CLASS TO REPRESENT JWT RESPONSE OBJECT
 * TO BE SENT BACK AS RESPONSE ON SUCCESSFUL USER AUTHENTICATION
 *
 */

public class JWTResponse {
	
	private String jwtToken;

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
	

}
