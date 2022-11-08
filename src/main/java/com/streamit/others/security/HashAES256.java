package com.streamit.others.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class HashAES256 {



	// private static final String SECRET_KEY = "my_super_secret_key_ho_ho_ho";
	private static final String SECRET_KEY = "$TRE@MCXP2022";
	private static final byte[] SALT;
	private static final SecureRandom random;
	private static final IvParameterSpec ivspec;
	static {
		random = new SecureRandom();

		// SALT = new byte[16];
		// random.nextBytes(SALT);

		byte[] byteArrray = { 82, 124, -65, -86, -15, -12, -92, 59, 60, 39, -99, 90, -94, 120, -88, -114 };
		SALT = byteArrray;

		// byte[] bytesIV = new byte[16];
		// random.nextBytes(bytesIV);

		byte[] byteArrray2 = { 98, 88, 88, -31, 29, 99, 60, 36, 31, -33, 84, 13, -57, 32, 55, -112 };
		byte[] bytesIV = byteArrray2;
		ivspec = new IvParameterSpec(bytesIV);

	}

	public  String encrypt(String strToEncrypt) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT, 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());
		}
		return null;
	}

	public  String decrypt(String strToDecrypt) {
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT, 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
			return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}

}
