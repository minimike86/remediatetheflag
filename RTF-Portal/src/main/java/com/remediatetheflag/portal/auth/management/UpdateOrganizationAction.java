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
package com.remediatetheflag.portal.auth.management;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.Organization;
import com.remediatetheflag.portal.model.OrganizationStatus;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;

public class UpdateOrganizationAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);

		JsonElement idOrgElement = json.get(Constants.ACTION_PARAM_ID);

		JsonElement nameElement = json.get(Constants.ACTION_PARAM_NAME);
		JsonElement contactEmailElement = json.get(Constants.ACTION_PARAM_EMAIL);
		JsonElement maxUsersElement = json.get(Constants.ACTION_PARAM_MAX_USERS);
		
		String name = nameElement.getAsString();
		String contactEmail = contactEmailElement.getAsString();
		Integer maxUsers = maxUsersElement.getAsInt();

		Organization o = new Organization();
		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		o.setDateJoined(c.getTime());
		o.setName(name);
		o.setEmail(contactEmail);
		o.setMaxUsers(maxUsers);
		o.setStatus(OrganizationStatus.ACTIVE);
		
		Boolean result = hpc.updateOrganization(idOrgElement.getAsInt(), o);
		if(result){
			MessageGenerator.sendSuccessMessage(response);
		}else{
			MessageGenerator.sendErrorMessage("Failed", response);
		}
	}

}
