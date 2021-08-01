package com.fabhotels.utility;


import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/*============================================================
 *UTILITY CLASS TO IMPLEMET AOP FOR LOGGING EXCEPTIONS FROM DIFFERENT CLASES
 *============================================================
 *It contains 4 methods:
 *		logDAOExceptions(Exception exception):[
 *					This method will be called automatically whenever 
 *					any exception is thrown from any DAO class and this class receives 
 *					the thrown exception as an input -> logs it and re-throws the exception.
 *
 *		logServiceExceptions(Exception exception):[
 *					This method will be called automatically whenever 
 *					any exception is thrown from any SERVICE class and this class receives 
 *					the thrown exception as an input -> logs it and re-throws the exception.
 *
 *		logValidatorExceptions(Exception exception):[
 *					This method will be called automatically whenever 
 *					any exception is thrown from any VALIDATOR class and this class receives 
 *					the thrown exception as an input -> logs it and re-throws the exception.
 *
 *		logException(Exception exception):[
 *					This method will be called by the above 3 classes for logging exceptions.
 *					This class instantiates the Logger and uses it to log exceptions.
 *					LOGGER USED:- LOG4J
 *=============================================================
 */


@Aspect
@Component
public class LoggingAspect {

	@AfterThrowing(pointcut = "execution(* com.fabhotels.dao.*Impl.*(..))", throwing= "exception")
	public void logDAOExceptions(Exception exception) throws Exception{
		
		logException(exception);
		
	}
	
	@AfterThrowing(pointcut = "execution(* com.fabhotels.service.*Impl.*(..))", throwing= "exception")
	public void logServiceExceptions(Exception exception) throws Exception{

		if(exception.getMessage()!=null && exception.getMessage().contains("Service")){
			logException(exception);
		}
	}
	
	@AfterThrowing(pointcut = "execution(* com.fabhotels.validator.*.*(..))", throwing= "exception")
	public void logValidatorExceptions(Exception exception) throws Exception{

		if(exception.getMessage()!=null && exception.getMessage().contains("Validator")){
			logException(exception);
		}
	}
	
	public void logException(Exception exception){
		
		Logger logger = LogManager.getLogger(LoggingAspect.class.getName());
		logger.error(exception.getMessage(), exception);
	}
	
}
