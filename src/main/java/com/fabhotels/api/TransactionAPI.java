package com.fabhotels.api;

import java.util.List;


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

import com.fabhotels.model.UserTransaction;
import com.fabhotels.service.UserTransactionService;


/*============================================================
 *MAIN API CLASS THAT HANDLES ALL THE TRANSACTION RELATED APIs
 *============================================================
 *It contains 4 methods:
 *		loadMoneyInWallet():[POST -> /loadMoneyInWallet/{userId}]
 *					This method accepts a UserTransaction object as an input from the body 
 *					along with the userId as a path variable input from the URL 
 *					and returns a success message after loading money in User Wallet.
 *
 *		transferMoneyFromWalletToWallet():[POST -> /transferMoneyFromWalletToWallet/{senderUserId}/{recieverEmailId}]
 *					This method accepts a UserTransaction object as an input from the body 
 *					along with the Sender's userId and Reciever's emailId as a path variable input from the URL 
 *					and returns a success message after transferring money from Sender's wallet to Reciever's Wallet.
 *
 *		getAllTransactions():[GET -> /getAllTransactions/{userId}]
 *					This method accepts the userId as a path variable input from the URL and
 *					returns a list of all transactions made by the user.
 *
 *		getUserBalance():[GET -> /getUserBalance/{userId}]
 *					This method accepts userId as a path variable input from the URL and
 *					returns the balance of user wallet.
 *
 *=============================================================
 */
@RestController
@RequestMapping("/TransactionAPI")
public class TransactionAPI {

	@Autowired
	private Environment environment;
	
	@Autowired
	private UserTransactionService transactionService;
	
	static Logger logger = LogManager.getLogger(TransactionAPI.class.getName());
	
	@RequestMapping(value="/loadMoneyInWallet/{userId}", method = RequestMethod.POST)
	public ResponseEntity<String> loadMoneyInWallet(@RequestBody UserTransaction userTransaction, @PathVariable Integer userId){
		
		ResponseEntity<String> responseEntity = null;
		
		try{
			logger.info("USER TRYING TO LOAD MONEY TO WALLET WITH USER-ID: " + userId);
			
			userTransaction = transactionService.loadMoneyInWallet(userTransaction.getAmount(), userId, userTransaction.getRemarks());
			String message = environment.getProperty("TransactionAPI.MONEY_LOADED_TO_WALLET_SUCCESSFULLY") + userTransaction.getUserTransactionId();
			responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);
			
			logger.info("SUCCESSFULLY LOADED MONEY TO WALLET WITH TRANSACTION ID: " + userTransaction.getUserTransactionId());
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/transferMoneyWalletToWallet/{senderUserId}/{recieverEmailId}", method = RequestMethod.POST)
	public ResponseEntity<String> transferMoneyFromWalletToWallet(@RequestBody UserTransaction userTransaction, @PathVariable Integer senderUserId, @PathVariable String recieverEmailId){
		
		ResponseEntity<String> responseEntity = null;
		
		try{
			logger.info("USER WITH USERID: " + senderUserId + " TRYING TO TRANSFER MONEY TO WALLET WITH EMAIL-ID: " + recieverEmailId);
			
			String message = transactionService.transferMoneyFromWalletToWallet(userTransaction.getAmount(), senderUserId, recieverEmailId, userTransaction.getRemarks());
			responseEntity = new ResponseEntity<String>(message, HttpStatus.OK);
			
			logger.info("AMOUNT OF INR " + userTransaction.getAmount() + "TRANSFERED FROM WALLET OF USERID: " + senderUserId + " TO WALLET WITH EMAIL-ID: " + recieverEmailId);
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/getAllTransactions/{userId}", method = RequestMethod.GET)
	public ResponseEntity<List<UserTransaction>> getAllTransactions(@PathVariable Integer userId){
		
		ResponseEntity<List<UserTransaction>> responseEntity = null;
		
		try{
			logger.info("FETCHING TRANSACTIONS FOR USERID: " + userId);
			
			List<UserTransaction> userTransactions = transactionService.getAllTransactions(userId);
			responseEntity = new ResponseEntity<List<UserTransaction>>(userTransactions, HttpStatus.OK);
			
			logger.info("SUCCESFULLY FETCHED TRANSACTIONS FOR USERID: " + userId);
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
		
		return responseEntity;
	}
	
	@RequestMapping(value="/getUserBalance/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Double> getUserBalance(@PathVariable Integer userId){
		
		ResponseEntity<Double> responseEntity = null;
		
		try{
			logger.info("FETCHING BALANCE FOR USERID: " + userId);
			
			Double balance = transactionService.getUserBalance(userId);
			responseEntity = new ResponseEntity<Double>(balance, HttpStatus.OK);
			
			logger.info("SUCCESFULLY FETCHED BALANCE FOR USERID: " + userId);
			
		}catch(Exception e){
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, environment.getProperty(e.getMessage()));
		}
		
		return responseEntity;
	}
	
}
