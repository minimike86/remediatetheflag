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
package com.remediatetheflag.global.actions.auth.management.monitor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.Stats;
import com.remediatetheflag.global.model.Team;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;
import com.remediatetheflag.global.utils.StatsUtils;

public class GetStatsTeamAction extends IAction {

	private  HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private static StatsUtils statsUtils = new StatsUtils();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement jsonElement = json.get(Constants.ACTION_PARAM_NAME);
		String teamName = jsonElement.getAsString();
		List<User> users = hpc.getUsersForTeamName(teamName,sessionUser.getManagedOrganizations());

		if(sessionUser.getRole().equals(Constants.ROLE_TEAM_MANAGER)){
			Team team = hpc.getTeamFromName(teamName,sessionUser.getManagedOrganizations());
			boolean isManager = false;
			for(User u : team.getManagers()){
				if(u.getIdUser().equals(sessionUser.getIdUser())){
					isManager = true;
					break;
				}
			}
			if(!isManager){
				MessageGenerator.sendErrorMessage("NotFound", response);
				return;
			}
		}
		
		if(null==users || users.isEmpty()){
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		
		List<ExerciseInstance> exerciseInstances = hpc.getReviewedExerciseInstancesWithResultsFlagsUserForStats(users);
	
		Stats stats = new Stats();
		statsUtils.setTimePerCategory(exerciseInstances,stats);
		stats.setIssuesRemediationRate(statsUtils.getRemediationRatePerIssue(exerciseInstances));
		stats.setCategoriesRemediationRate(statsUtils.getRemediationRatePerIssueCategory(exerciseInstances));

		MessageGenerator.sendStatsMessage(stats, response);
	}


}
