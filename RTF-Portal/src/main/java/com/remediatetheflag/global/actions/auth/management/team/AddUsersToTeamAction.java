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
package com.remediatetheflag.global.actions.auth.management.team;

import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.Team;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;

public class AddUsersToTeamAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement jsonElement = json.get(Constants.ACTION_PARAM_TEAM_NAME);
		String teamName = jsonElement.getAsString();
		Team team = hpc.getTeamFromName(teamName,user.getManagedOrganizations());
		if(null==team){
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		
		Type listType = new TypeToken<List<String>>() {}.getType();
		Gson gson = new Gson();
		List<String> usernames = gson.fromJson(json.get(Constants.ACTION_PARAM_USERNAME_LIST), listType);
		
		if(user.getRole().equals(Constants.ROLE_TEAM_MANAGER)){
			boolean isManager = false;
			for(User u : team.getManagers()){
				if(u.getIdUser().equals(user.getIdUser())){
					isManager = true;
					break;
				}
			}
			if(!isManager){
				MessageGenerator.sendErrorMessage("NotFound", response);
				return;
			}
		}
		Integer nrAdded = 0;
		for(String u : usernames){
			User usr = hpc.getUserFromUsername(u,user.getManagedOrganizations());
			if(usr.getTeam()==null){
				hpc.addUserToTeam(usr,team);
				nrAdded++;
			}
		}
		MessageGenerator.sendNumberUpdatedMessage(nrAdded, response);
	}
}