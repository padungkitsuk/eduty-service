package com.streamit.others.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWTVerifyException;

@Component
public class SimpleCORSFilter implements Filter {
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		
		log.info("REQUEST URL : "+request.getRequestURL());
		log.info("METHOD      : "+request.getMethod());
		
		//*** VERIFY TOKEN ***//
				log.info("api index "+request.getRequestURL().indexOf("/api/"));
				
				if(request.getRequestURL().indexOf("/")>0){
					try{
						log.info("AUTH_TOKEN  : "+request.getHeader("AUTH_TOKEN"));
						String authToken = request.getHeader("AUTH_TOKEN");
						
						if(!TokenGenarate.signToken(authToken)){
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401 UNAUTHORIZED");
						}
						
					} catch (InvalidKeyException | NoSuchAlgorithmException | IllegalStateException | SignatureException | JWTVerifyException e) {
						e.printStackTrace();
					}
				}
				
				response.setHeader("Access-Control-Allow-Origin", "*");
				response.setHeader("Access-Control-Allow-Credentials", "true");
				response.setHeader("Access-Control-Allow-Headers", "Content-Type");
				response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH,OPTIONS");
				response.setHeader("Access-Control-Max-Age", "3600");
				response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Cache-Control,Pragma");
				response.setCharacterEncoding("UTF-8"); 
				chain.doFilter(req, response);
	}

	@SuppressWarnings("unused")
	private Boolean validateToken(HttpServletRequest httpRequest){
		System.out.println("IP Address : "+httpRequest.getRemoteAddr());
		System.out.println("ACCESS URI : "+httpRequest.getRequestURL());
		System.out.println("tokenId : "+httpRequest.getHeader("X-Auth-Token"));

		String tokenId = httpRequest.getHeader("X-Auth-Token");
		if(tokenId == null)
			return false;

		else if(tokenId!=null && !tokenId.equals(""))
			return isTokenExpire(tokenId);

		
		return false;
	}
	
	private Boolean isTokenExpire(String tokenId){
		
		return true;
	}
	
	public void init(FilterConfig filterConfig) {}
	public void destroy() {}
}