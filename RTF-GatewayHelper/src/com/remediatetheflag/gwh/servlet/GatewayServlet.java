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
package com.remediatetheflag.gwh.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.remediatetheflag.gwh.actions.IAction;
import com.remediatetheflag.gwh.messages.MessageGenerator;
import com.remediatetheflag.gwh.model.Constants;

public class GatewayServlet  extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("rawtypes")
	protected Map<String, Class> type2action = new HashMap<>();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		executeAction(request,response);
	}

	public void executeAction(HttpServletRequest request, HttpServletResponse response) {

		type2action.put("getInstanceStatus", com.remediatetheflag.gwh.actions.GetInstanceStatusAction.class);
		type2action.put("getResultStatus", com.remediatetheflag.gwh.actions.GetResultStatusAction.class);
		type2action.put("getResultArchive", com.remediatetheflag.gwh.actions.GetResultZipAction.class);

		JsonObject jsonObject;
		String actionType;
		try {
			StringBuffer allLines = new StringBuffer();
			String output;
			while ((output = request.getReader().readLine()) != null) {
				allLines.append(output);
			}  
			jsonObject = (JsonObject) new JsonParser().parse(allLines.toString());
			JsonElement jActionType = jsonObject.get(Constants.JSON_ATTRIBUTE_ACTION);
			if (jActionType==null) {
				MessageGenerator.sendErrorMessage(Constants.JSON_VALUE_ERROR_TYPE_NOT_FOUND, response);
				return;
			}
			actionType = jActionType.getAsString();
		} catch (JsonSyntaxException | IOException e) {
			MessageGenerator.sendErrorMessage(Constants.JSON_VALUE_ERROR_JSON_PARSING, response);
			return;
		}
		// Find Action
		@SuppressWarnings("rawtypes")
		Class actionClass = type2action.get(actionType);
		if (null == actionClass) {
			MessageGenerator.sendErrorMessage(Constants.JSON_VALUE_ERROR_ACTION_NOT_FOUND, response);
			return;
		}
		// Execute request
		try {
			IAction action = ((IAction) actionClass.newInstance());
			request.setAttribute(Constants.REQUEST_ATTRIBUTE_JSON, jsonObject);
			action.doAction(request,response);	
		} catch ( Exception  e) {
			MessageGenerator.sendErrorMessage(Constants.JSON_VALUE_ERROR_ACTION_EXCEPTION, response);
		}
	}
}