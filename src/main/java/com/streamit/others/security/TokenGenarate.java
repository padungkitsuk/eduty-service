package com.streamit.others.security;

import com.auth0.jwt.Algorithm;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.Map;


public class TokenGenarate {
	private static final String SIGGER = "STREAM_EDTA_WEBVALIDATION";
	
	public static void main(String[]s) throws Exception {
		//System.out.println("GENERATE TOKEN : "+generateToken());
		//System.out.println("VERIFY TOKEN   : "+signToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJodW1hbmZseSIsInVzZXJHcm91cCI6IkcwMDEiLCJleHBpcmUiOiIyMDE2LzExLzI1IDE3OjMwIn0.C-xoWZfHgp2fqvdpRjWOR7oF1oBrLaLZiaHj9g-Q7kc"));
    }	
	
	public static String generateToken(Map<String, Object> payload){
		JWTSigner signer = new JWTSigner(SIGGER);
		        
        String token = signer.sign(payload, new JWTSigner.Options().setAlgorithm(Algorithm.HS512));    
        
        return token;
	}
	
	public static Boolean signToken(String token) throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, SignatureException, IOException, JWTVerifyException{
		boolean tokenValid = true;
		
		try{
			System.out.println("SIGN TOKEN START TIME "+new Date());
			JWTVerifier verifier = new JWTVerifier(SIGGER);
			Map<String, Object> playload = verifier.verify(token);
	        System.out.println("PLAYLOAD : "+playload);
	        System.out.println("SIGN TOKEN END TIME   "+new Date());
        
		}catch(Exception e){
			e.printStackTrace();
			tokenValid = false;
		}
        
        return tokenValid;
	}
	
	public static Map<String, Object> getToken(String token) throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, SignatureException, IOException, JWTVerifyException{
			
			Map<String, Object> playload = null;
			try{
				//System.out.println("SIGN TOKEN START TIME "+new Date());
				JWTVerifier verifier = new JWTVerifier(SIGGER);
				playload = verifier.verify(token);
		        //System.out.println("SIGN TOKEN END TIME   "+new Date());
	        
			}catch(Exception e){
				e.printStackTrace();
				playload = null;
			}
	        
	        return playload;
		}	
	@SuppressWarnings("unused")
	private Boolean validateTokenExpire(){
		
		
		return false;
	}
}

