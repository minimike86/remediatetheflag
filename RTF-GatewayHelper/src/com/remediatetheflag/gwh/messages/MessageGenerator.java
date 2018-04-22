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
package com.remediatetheflag.gwh.messages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.remediatetheflag.gwh.model.Constants;

public class MessageGenerator {

	public static void sendErrorMessage(String error, HttpServletResponse response) {
		JsonObject msg = new JsonObject();
		msg.addProperty(Constants.JSON_ATTRIBUTE_RESULT, Constants.JSON_VALUE_ERROR);
		msg.addProperty(Constants.JSON_ATTRIBUTE_ERROR_MSG, error);
		send(msg.toString(),response);
	}

	public static void sendSuccessMessage(HttpServletResponse response) {
		JsonObject msg = new JsonObject();
		msg.addProperty(Constants.JSON_ATTRIBUTE_RESULT, Constants.JSON_VALUE_SUCCESS);
		send(msg.toString(),response);
	}

	private static void send(String msg, HttpServletResponse response){
		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(msg.toString());
			out.flush();
		} catch (IOException e) {
		}
	}

}
