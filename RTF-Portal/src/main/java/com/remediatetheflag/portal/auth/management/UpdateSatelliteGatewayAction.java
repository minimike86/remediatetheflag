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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.RTFGateway;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;

public class UpdateSatelliteGatewayAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);

		JsonElement idElement = json.get(Constants.ACTION_PARAM_ID);
		JsonElement nameElement = json.get(Constants.ACTION_PARAM_NAME);
		JsonElement fqdnElement = json.get(Constants.ACTION_PARAM_FQDN);
		JsonElement statusElement = json.get(Constants.ACTION_PARAM_STATUS);
		
		Integer id = idElement.getAsInt();
		String name = nameElement.getAsString();
		String fqdn = fqdnElement.getAsString();
		Boolean status = statusElement.getAsBoolean();
		
		RTFGateway g = new RTFGateway();
		g.setName(name);
		g.setFqdn(fqdn);
		g.setActive(status);
	
		if(hpc.updateGateway(id,g))
			MessageGenerator.sendSuccessMessage(response);
		else
			MessageGenerator.sendErrorMessage("Failed", response);
	}
}
