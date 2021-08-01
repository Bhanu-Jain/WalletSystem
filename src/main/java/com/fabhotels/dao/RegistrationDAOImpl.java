package com.fabhotels.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fabhotels.entity.UserEntity;
import com.fabhotels.model.User;


/*============================================================
 *IMPLEMENTATION CLASS FOR THE REPOSITORY INTERFACE THAT HANDLES ALL THE ACTUAL DB TRANSACTIONS FOR USER REGISTRATION
 *============================================================
 *It overrides 3 methods from the interface:
 *		checkEmailAvailability(String emailId):[
 *					This method accepts a user emailId as an input 
 *					and checks if the email already exists in Database for any user
 *					and it will return a true if email does not exists and return false if email exists.
 *
 *		checkMobileNumberAvailability(String mobileNumber):
 *					This method accepts a user mobile number as an input 
 *					and checks if the mobile number already exists in Database for any user
 *					and it will return a true if mobile number does not exists and return false if mobile number exists.
 *
 *		registerNewUser(User user):
 *					This method accepts a User object as an input from the body
 *					and returns a success message after successfully updating the user password.
 *
 *=============================================================
 */
@Repository(value="registrationDAOImpl")
public class RegistrationDAOImpl implements RegistrationDAO {
	
	@Autowired
	EntityManager entityManager;

	@Override
	public Boolean checkEmailAvailability(String emailId) {

		Query query = entityManager.createQuery("select count(userId) from UserEntity where emailId=:emailId");
		query.setParameter("emailId", emailId.toLowerCase());
		Long value = (Long) query.getSingleResult();
		
		if(value==0){
			return true;
		}
		return false;
	}

	@Override
	public Boolean checkMobileNumberAvailability(String mobileNumber) {

		Query query = entityManager.createQuery("select count(userId) from UserEntity where mobileNumber=:mobileNumber");
		query.setParameter("mobileNumber", mobileNumber);
		Long value = (Long) query.getSingleResult();
		
		if(value==0){
			return true;
		}
		return false;
	}

	@Override
	public Integer registerNewUser(User user) {

		UserEntity userEntity = new UserEntity();
		userEntity.setEmailId(user.getEmailId());
		userEntity.setName(user.getName());
		userEntity.setMobileNumber(user.getMobileNumber());
		userEntity.setPasssword(user.getPassword());
		
		entityManager.persist(userEntity);
		Integer userId = userEntity.getUserId();
		
		return userId;
	}

}
