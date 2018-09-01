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

import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.Organization;
import com.remediatetheflag.portal.model.Team;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;

public class AddTeamAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@SuppressWarnings("serial")
	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement newTeamName = json.get(Constants.ACTION_PARAM_TEAM_NAME);
		String teamName = newTeamName.getAsString();

		JsonElement newTeamOrganization = json.get(Constants.ACTION_PARAM_ORG_NAME);
		Integer teamOrg = newTeamOrganization.getAsInt();

		//check if user can manage specified org
		Organization org = hpc.getOrganizationById(teamOrg);
		boolean isManager = false;
		for(Organization mOrg : sessionUser.getManagedOrganizations()){
			if(org.getId().equals(mOrg.getId())){
				isManager = true;
				break;
			}
		}
		if(null==org || !isManager){
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		//check if team already exists for specified org
		Team t = hpc.getTeamFromName(teamName,org);
		if(null!=t){
			MessageGenerator.sendErrorMessage("TeamExists", response);
			return;
		}
		
		Team team = new Team();
		team.setManagers(new HashSet<User>(){{add(sessionUser);}});
		team.setName(teamName);
		team.setCreatedByUser(sessionUser);
		team.setOrganization(org);
		if(null!=hpc.addTeam(team))
			MessageGenerator.sendSuccessMessage(response);
		else
			MessageGenerator.sendErrorMessage("Error", response);

	}
}
