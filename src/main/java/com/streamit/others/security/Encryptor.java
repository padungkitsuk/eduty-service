package com.streamit.others.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;

public class Encryptor {
	
	private static String TRANSFORMATION = "AES/CBC/PKCS5PADDING";
    private static String ALGORITHM = "AES";
    private static String DIGEST = "MD5";
     
    private static Cipher _cipher;
    private static SecretKey _password;
    private static IvParameterSpec _IVParamSpec;
    
    //16-byte private key
    private static byte[] IV = "y7d\"b)zC\"(tn@A'E".getBytes();
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    
    public static void main(String[]s) {
    	Encryptor encryptor = new Encryptor("$\\WcZ^33sbtj~V~?");
    	encryptor.encrypt("cxpinsureapi".getBytes());
    	encryptor.decrypt("pqXbeXJ0WVCkZiCnnRXzjA==");
	}
    
    
    /** Constructor  
    	@password Public key */
    
   public Encryptor(String password) {

       try { 
           //Encode digest
           MessageDigest digest;           
           digest = MessageDigest.getInstance(DIGEST);            
           _password = new SecretKeySpec(digest.digest(password.getBytes()), ALGORITHM);
            
           //Initialize objects
           _cipher = Cipher.getInstance(TRANSFORMATION);
           _IVParamSpec = new IvParameterSpec(IV);
            
       } catch (NoSuchAlgorithmException e) {
           logger.info( "No such algorithm " + ALGORITHM, e);
       } catch (NoSuchPaddingException e) {
       	logger.info( "No such padding PKCS7", e);
       }              
   }
   
   /** Encryptor.
		@text String to be encrypted
   		@return Base64 encrypted text */
   
   public String encrypt(byte[] text) {
        
       byte[] encryptedData;
        
       try {
            
           _cipher.init(Cipher.ENCRYPT_MODE, _password, _IVParamSpec);
           encryptedData = _cipher.doFinal(text);
            
       } catch (InvalidKeyException e) {
       	 logger.info( "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
           return null;
       } catch (InvalidAlgorithmParameterException e) {
       	 logger.info( "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
           return null;
       } catch (IllegalBlockSizeException e) {
       	 logger.info( "The length of data provided to a block cipher is incorrect", e);
           return null;
       } catch (BadPaddingException e) {
       	 logger.info( "The input data but the data is not padded properly.", e);
           return null;
       }               
        
       System.out.println(Base64.encodeBase64String(encryptedData));
       return Base64.encodeBase64String(encryptedData);
        
   }
    
   /** Decryptor.
		@text Base64 string to be decrypted
   		@return decrypted text */  
   
   public String decrypt(String text) {

       try {
           _cipher.init(Cipher.DECRYPT_MODE, _password, _IVParamSpec);
            
           byte[] decodedValue = Base64.decodeBase64(text.getBytes());
           byte[] decryptedVal = _cipher.doFinal(decodedValue);
           System.out.println(new String(decryptedVal));
           return new String(decryptedVal);            
            
            
       } catch (InvalidKeyException e) {
       	 logger.info( "Invalid key  (invalid encoding, wrong length, uninitialized, etc).", e);
           return null;
       } catch (InvalidAlgorithmParameterException e) {
       	 logger.info( "Invalid or inappropriate algorithm parameters for " + ALGORITHM, e);
           return null;
       } catch (IllegalBlockSizeException e) {
       	 logger.info( "The length of data provided to a block cipher is incorrect", e);
           return null;
       } catch (BadPaddingException e) {
       	 logger.info( "The input data but the data is not padded properly.", e);
           return null;
       }               
        
   }
}