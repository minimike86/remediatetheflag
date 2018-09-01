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
package com.remediatetheflag.portal.actions.auth.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.ExerciseInstance;
import com.remediatetheflag.portal.model.Stats;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;
import com.remediatetheflag.portal.utils.StatsUtils;

public class GetStatsTeamAction extends IAction {

	private  HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private static StatsUtils statsUtils = new StatsUtils();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		List<User> users = hpc.getUsersForTeamName(user.getTeam().getName(),user.getDefaultOrganization());

		Stats stats = new Stats();
		List<ExerciseInstance> exerciseInstances = hpc.getReviewedExerciseInstancesWithResultsFlagsUserForStats(users);
	
		statsUtils.setTimePerRegion(exerciseInstances,stats);
		statsUtils.setTimePerCategory(exerciseInstances,stats);
		
		stats.setIssuesRemediationRate(statsUtils.getRemediationRatePerIssue(exerciseInstances));
		stats.setCategoriesRemediationRate(statsUtils.getRemediationRatePerIssueCategory(exerciseInstances));
		stats.setRegionsRemediationRate(statsUtils.getRemediationRatePerRegion(exerciseInstances));

		MessageGenerator.sendStatsMessage(stats, response);
	}
}