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
package com.remediatetheflag.gwh.actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.gwh.messages.MessageGenerator;
import com.remediatetheflag.gwh.model.Constants;
import com.remediatetheflag.gwh.utils.HTTPUtils;

public class GetResultStatusAction extends IAction{

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String jsonAction = "{\"action\":\"getResultStatus\"}";
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement jsonElement = json.get(Constants.INSTANCE_IP);
		String responseString = HTTPUtils.sendPost(jsonElement.getAsString(), jsonAction);
		if(null==responseString){
			MessageGenerator.sendErrorMessage("CouldNotFetch", response);
			return;
		}
		try {
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(responseString);
			out.flush();
		} catch (IOException e) {
			//TODO Logging
		}
	}

}
