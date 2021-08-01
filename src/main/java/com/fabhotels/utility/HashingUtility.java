package com.fabhotels.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*============================================================
 *UTILITY CLASS TO CALCULATE ENCRYPTED USER PASSWORD
 *============================================================
 *It contains 1 static methods:
 *		getHashValue(String password):[
 *					This method accepts a password string as an input 
 *					and returns the corresponding encrypted value using SHA-256 algorithm.
 *
 *=============================================================
 */

public class HashingUtility {

	public static String getHashValue(String password) throws NoSuchAlgorithmException{
		
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update(password.getBytes());
		byte byteData[] = messageDigest.digest();
		
		StringBuffer encodedString = new StringBuffer();
		for(int i =0; i<byteData.length; i++){
			
			String hexValue = Integer.toHexString(0xff & byteData[i]);
			if(hexValue.length()==1){
				encodedString.append('0');
			}
			
			encodedString.append(hexValue);
		}
		
		return encodedString.toString();
	}
}
