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
package com.remediatetheflag.portal.actions.unauth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.Organization;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CheckUsernameAvailableAction extends IAction {
	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		
		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);	
		if(user==null || null==user.getIdUser()){
			JsonElement orgCodeElement = json.get(Constants.ACTION_PARAM_ORG_CODE);
			String orgInviteCode = orgCodeElement.getAsString();
			Organization org = hpc.getOrganizationFromInvitationCode(orgInviteCode);
			if (org == null) {
				MessageGenerator.sendErrorMessage("NotAuthorized", response);
				return;
			} 
		}
		
		JsonElement usernameElement = json.get(Constants.ACTION_PARAM_USERNAME);
		String username = usernameElement.getAsString();

		User existingUser = hpc.getUserFromUsername(username);
		if (existingUser != null) {
			MessageGenerator.sendAvailable(false, response);
		} else {
			MessageGenerator.sendAvailable(true, response);
		}
	}
}