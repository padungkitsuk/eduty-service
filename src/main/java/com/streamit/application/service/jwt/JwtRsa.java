package com.streamit.application.service.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class JwtRsa {

	private static final String JWS_HEADER = "{\"alg\":\"RS256\",\"typ\":\"JWT\"}";
    private String headerStripRegex = "(-{3,}([\\s\\S]*?)-{3,})|(\\r\\n)|\\n";


	public void testJWTWithRsa() throws Exception {
        //KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance("RSA");
        //keyGenerator.initialize(1024);

        //KeyPair kp = keyGenerator.genKeyPair();
        //PublicKey publicKey = (PublicKey) kp.getPublic();
        //PrivateKey privateKey = (PrivateKey) kp.getPrivate();
        //String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        //System.out.println("Public Key:" + convertToPublicKey(encodedPublicKey));
        
        //PublicKey publicKey = getPubliceKey();
        //PrivateKey privateKey = getPrivateKey();
        
        //System.out.println("publicKey=" + publicKey);

        //String token = generateJwtToken();
        //System.out.println("TOKEN:" + token);
        //printStructure(token, publicKey);
    }

    @SuppressWarnings("deprecation")
    public String generateJwtToken(String payloadStr) throws Exception {
    	
    	PrivateKey privateKey = getPrivateKey();
    	
        Map<String,Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "RS256");
        headers.put("x5c", new ArrayList<>(Arrays.asList(getX5C())));

        String token = Jwts.builder().setHeader(headers)
                //.setSubject("adam")
                //.setExpiration(new Date(2022, 1, 1))
                //.setIssuer("info@wstutorial.com")
                //.claim("groups", new String[] { "user", "admin" })
                .setPayload(payloadStr)
                // RS256 with privateKey
                .signWith(SignatureAlgorithm.RS256, privateKey).compact();
        return token;
    }

    //Print structure of JWT
    public void printStructure(String token) throws Exception {
    	PublicKey publicKey = getPubliceKey();
        Jws  parseClaimsJws = Jwts.parser().setSigningKey(publicKey)
                .parseClaimsJws(token);

        System.out.println("Header     : " + parseClaimsJws.getHeader());
        System.out.println("Body       : " + parseClaimsJws.getBody());
        System.out.println("Signature  : " + parseClaimsJws.getSignature());
    }


    // Add BEGIN and END comments
    private String convertToPublicKey(String key){
        StringBuilder result = new StringBuilder();
        //result.append("-----BEGIN PUBLIC KEY-----\n");
        result.append(key);
       // result.append("\n-----END PUBLIC KEY-----");
        return result.toString();
    }


    ///////////////////////////////////////////////
    private PublicKey getPubliceKey() throws Exception {

        String publicKeyString = null;
        PublicKey publicKey = null;

        try {
        	
        	//File file = new ClassPathResource("auth-keys/public-test").getFile();
            File file = new File("E:/EDUTY/auth-keys/public-test");
        	if (Objects.nonNull(file)) {
                Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name());
                try {
                    // Get String version.
                    publicKeyString = scanner.useDelimiter("\\A").next();
                    // Convert String version into PublicKey data type.
                    //publicKey = convertToPublicKey(publicKeyString);
                } finally {
                    scanner.close();
                }

            } else {
            	System.out.println("Auth token information file: " + file.getName() + " does not exist");
                throw new FileNotFoundException("File: " + file.getName() + " does not exist");
            }
        	
            //System.out.println("pubKeyStr="+publicKeyString);
            
            // Get String version.privKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "");
            publicKeyString = publicKeyString.replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll(headerStripRegex, "");
            //System.out.println("publicKeyString="+publicKeyString);
            
            byte[] publicBytes = Base64.getDecoder().decode(publicKeyString);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
            // Convert String version into PublicKey data type.

            KeyFactory kf;
            kf = KeyFactory.getInstance("RSA");
            publicKey = kf.generatePublic(keySpec);
            //publicKey = convertToPublicKey(publicKeyString);
        } catch (Exception e){
            System.out.println("get public error "+ e.getMessage());
        }



        return publicKey;
    }


    public PrivateKey getPrivateKey() throws Exception {

        String privateKeyString = null;
        PrivateKey privateKey = null;

        try {
        	//File file = new ClassPathResource("auth-keys/private-test").getFile();
            File file = new File("E:/EDUTY/auth-keys/private-test");

            if (Objects.nonNull(file)) {
                Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name());
                try {
                    // Get String version.
                    privateKeyString = scanner.useDelimiter("\\A").next();
                    // Convert String version into PublicKey data type.
                    //privateKey = convertToPrivateKey(privateKeyString);
                } finally {
                    scanner.close();
                }

            } else {
                throw new FileNotFoundException("File: " + file.getName() + " does not exist");
            }
            
            //System.out.println("priKeyStr="+privateKeyString);
            // Get String version.privKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "");
            privateKeyString = privateKeyString.replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replaceAll(headerStripRegex, "");
            //System.out.println("privateKeyString="+privateKeyString);
            
            // Convert String version into PublicKey data type.

            byte[] encodedKey = Base64.getDecoder().decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedKey);

            KeyFactory kf;
            kf = KeyFactory.getInstance("RSA");
            // Convert into PrivateKey type.
            privateKey = kf.generatePrivate(keySpec);
            //privateKey = convertToPrivateKey(privateKeyString);
        } catch (Exception e){
            System.out.println("get private error {} "+ e.getMessage());
        }



        return privateKey;
    }

    private String getX5C() throws Exception {

        String x5cString = null;

        try {
        	//File file = new ClassPathResource("auth-keys/rdsw.rd.go.th.cer").getFile();
            File file = new File("E:/EDUTY/auth-keys/rdsw.rd.go.th.cer");

            if (Objects.nonNull(file)) {
                Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name());
                try {
                    // Get String version.
                	x5cString = scanner.useDelimiter("\\A").next();
                    // Convert String version into PublicKey data type.
                    //privateKey = convertToPrivateKey(privateKeyString);
                } finally {
                    scanner.close();
                }

            } else {
                throw new FileNotFoundException("File: " + file.getName() + " does not exist");
            }
            
            //System.out.println("x5cStr="+x5cString);
            // Get String version.privKeyPEM.replace("-----BEGIN PUBLIC KEY-----", "");
            x5cString = x5cString.replace("-----BEGIN CERTIFICATE-----", "")
                    .replace("-----END CERTIFICATE-----", "")
                    .replaceAll(headerStripRegex, "");
            //System.out.println("x5cString="+x5cString);

        } catch (Exception e){
            System.out.println("get x5c error "+ e.getMessage());
        }



        return x5cString;
    }



}
