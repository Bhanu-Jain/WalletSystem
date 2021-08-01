package com.fabhotels.model;

import java.util.Collection;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/*
 * MODEL CLASS TO REPRESENT CUSTOM IMPLEMENTATION FOR UserDetails CLASS
 *
 */

public class MyUserDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	private String password;
	private String userName;
	
	public MyUserDetails() {

	}
	
	public MyUserDetails(String userName, String password) {
		this.password = password;
		this.userName = userName;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
