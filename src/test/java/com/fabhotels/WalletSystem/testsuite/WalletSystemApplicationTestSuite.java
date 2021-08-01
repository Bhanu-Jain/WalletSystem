package com.fabhotels.WalletSystem.testsuite;

/*
 * TEST SUITE CLASS THAT ACTS HOLDS ALL 
 * THE SUB TEST CLASSESS FOR UNIT TESTING
 * 
 * JUNIT AND MOCKITO IS USED FOR UNIT TESTING
 * 
 */
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.fabhotels.dao.test.RegistrationDAOTest;
import com.fabhotels.dao.test.UserLoginDAOTest;
import com.fabhotels.dao.test.UserTransactionDAOTest;
import com.fabhotels.service.test.RegistrationServiceTest;
import com.fabhotels.service.test.UserLoginServiceTest;
import com.fabhotels.service.test.UserTransactionServiceTest;
import com.fabhotels.validator.test.RegistrationValidatorTest;
import com.fabhotels.validator.test.UserLoginValidatorTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	
	RegistrationDAOTest.class,
	UserLoginDAOTest.class,
	UserTransactionDAOTest.class,
	RegistrationServiceTest.class,
	UserLoginServiceTest.class,
	UserTransactionServiceTest.class,
	RegistrationValidatorTest.class,
	UserLoginValidatorTest.class
	
})
class WalletSystemApplicationTestSuite {


}
