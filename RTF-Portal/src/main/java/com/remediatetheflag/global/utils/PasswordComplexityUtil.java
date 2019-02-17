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
package com.remediatetheflag.global.utils;

public class PasswordComplexityUtil {

	public static boolean isPasswordComplex(String password) {
		boolean isAtLeast8   = password.length() >= 8;//Checks for at least 8 characters
		boolean hasSpecial   = !password.matches("[A-Za-z0-9 ]*");//Checks at least one char is not alpha numeric
		boolean hasUppercase = !password.equals(password.toLowerCase());
		boolean hasLowercase = !password.equals(password.toUpperCase());
		boolean hasNumber  = password.matches(".*\\d.*");  // "a digit with anything before or after"

		boolean isComplex = false;
		if(isAtLeast8){
			Integer count = 0;
			if(hasSpecial)
				count++;
			if(hasUppercase)
				count++;
			if(hasLowercase)
				count++;
			if(hasNumber)
				count++;
			if(count>=3)
				isComplex = true;
		}
		return isComplex;
	}
}
