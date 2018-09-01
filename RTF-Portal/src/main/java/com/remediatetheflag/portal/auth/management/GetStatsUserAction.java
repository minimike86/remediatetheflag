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

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.ExerciseInstance;
import com.remediatetheflag.portal.model.ExerciseResult;
import com.remediatetheflag.portal.model.Stats;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;
import com.remediatetheflag.portal.utils.StatsUtils;

public class GetStatsUserAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private StatsUtils statsUtils = new StatsUtils();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement jsonElement = json.get(Constants.ACTION_PARAM_USERNAME);
		String username = jsonElement.getAsString();
		User user = hpc.getUserFromUsername(username,sessionUser.getManagedOrganizations());
		
		if(null==user){
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		
		Stats stats = new Stats();
		List<User> users = new LinkedList<User>();
		users.add(user);
		List<ExerciseResult> exerciseResults = new LinkedList<ExerciseResult>();
		List<ExerciseInstance> exerciseInstances = hpc.getReviewedExerciseInstancesWithResultsFlagsUserForStats(users);
		for(ExerciseInstance i : exerciseInstances){
			exerciseResults.addAll(i.getResults());
		}
		statsUtils.setTimePerCategory(exerciseInstances,stats);	
		stats.setAverageMinutesPerIssueCategory(null);
		stats.setIssuesRemediationRate(statsUtils.getRemediationRatePerIssue(exerciseInstances));
		stats.setCategoriesRemediationRate(statsUtils.getRemediationRatePerIssueCategory(exerciseInstances));

		MessageGenerator.sendStatsMessage(stats, response);
	}

}
