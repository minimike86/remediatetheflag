package utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import model.Constants;

import org.json.simple.JSONObject;

/**
 *  Based on OWASP CryptoUtil.java and CSRFGuard.java written by Eric Sheriden for OWASP
 *
 *  This version by Rohit K. Sethi, Security Compass
 *  Created Aug. 9, 2007
 *
 */


public final class CSRFTokenUtils {
	private final static String DEFAULT_PRNG = "SHA1PRNG"; //algorithm to generate key
	private final static String NO_SESSION_ERROR = "No valid session found";
	
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
			session.setAttribute(Constants.CSRF_TOKEN, token_val);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tests whether or not the value of the CSRF_TOKEN parameter in the request 
	 * is equal to the value of the CSRF_TOKEN attribute in the session 
	 */
	public static boolean isValid (HttpSession session, JSONObject json)  {
		if (session== null) {
			try {
				throw new ServletException(NO_SESSION_ERROR);
			} catch (ServletException e) {
				e.printStackTrace();
			}
		}
		String token = (String) json.get(Constants.CSRF_TOKEN);
		
		return session.getAttribute(Constants.CSRF_TOKEN).equals(token);
	}
}