/*
 *  
 * REMEDIATE THE FLAG
 * Copyright 2018 - Andrea Scaduto 
 * remediatetheflag@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
/**
 *  Based on OWASP CryptoUtil.java and CSRFGuard.java written by Eric Sheriden for OWASP
 *
 *  This version by Rohit K. Sethi, Security Compass
 *  Created Aug. 9, 2007
 *
 */
package com.remediatetheflag.portal.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
public final class CSRFTokenUtils {
	private final static String DEFAULT_PRNG = "SHA1PRNG"; //algorithm to generate key
	private final static String NO_SESSION_ERROR = "No valid session found";
	private static Logger logger = LoggerFactory.getLogger(CSRFTokenUtils.class);

	/**
	 * Generates a random token to use in CSRF with the default 
	 * Crytopgrahically strong Pseudo-Number Random Generator
	 */
	
	private static String getToken() throws NoSuchAlgorithmException{
		return generateToken(DEFAULT_PRNG);
	}

	/**
	 * Generates a random token to use in CSRF with the default 
	 * Crytopgrahically strong Pseudo-Number Random Generator
	 * 
	 */
	
	private static String generateToken(String prng) throws NoSuchAlgorithmException{
		SecureRandom sr = SecureRandom.getInstance(prng);
		return "" + Math.abs(sr.nextLong());
	}


	/**
	 * Creates a token and adds it to the session.
	 */
	public static void setToken (HttpSession session) {
		if (session == null) {
			try {
				throw new ServletException(NO_SESSION_ERROR);
				
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		String token_val;
		try {
			token_val = getToken();
			session.setAttribute(Constants.ATTRIBUTE_SECURITY_CSRF_TOKEN, token_val);
		} catch (NoSuchAlgorithmException e) {
			logger.error("CSRF token generation error: "+e.getMessage());
		}
	}

	/**
	 * Tests whether or not the value of the CSRF_TOKEN parameter in the request 
	 * is equal to the value of the CSRF_TOKEN attribute in the session 
	 */
	public static boolean isValid (HttpSession session, JsonObject json)  {
		if (session== null) {
			try {
				throw new ServletException(NO_SESSION_ERROR);
			} catch (ServletException e) {
				logger.error("CSRF token validation error: "+e.getMessage());
			}
		}
		JsonElement tokenElement = json.get(Constants.ACTION_PARAM_CSRF_TOKEN);
		if(null==tokenElement)
			return false;
		
		String token = tokenElement.getAsString();
		return session.getAttribute(Constants.ATTRIBUTE_SECURITY_CSRF_TOKEN).equals(token);
	}
}