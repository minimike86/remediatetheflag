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
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.regions.Regions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.AvailableExercise;
import com.remediatetheflag.portal.model.ExerciseInstance;
import com.remediatetheflag.portal.model.Flag;
import com.remediatetheflag.portal.model.Organization;
import com.remediatetheflag.portal.model.RTFGateway;
import com.remediatetheflag.portal.model.Stats;
import com.remediatetheflag.portal.model.Team;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;
import com.remediatetheflag.portal.utils.StatsUtils;

public class GetStatsGlobalAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private StatsUtils statsUtils = new StatsUtils();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement jsonElement = json.get(Constants.ACTION_PARAM_FILTER);
		int [] filterOrgIds;
		try{
			Gson parser = new Gson();
			filterOrgIds = parser.fromJson(jsonElement, int[].class);
		} catch( Exception e ){
			filterOrgIds = new int[0];
		}
		Set<Organization> filteredOrg = new HashSet<Organization>();
		if(filterOrgIds.length==0){
			filteredOrg.addAll(user.getManagedOrganizations());
		}
		else{
			for(Integer id : filterOrgIds){
				for(Organization org : user.getManagedOrganizations()){
					if(id.equals(org.getId())){
						filteredOrg.add(org);
					}
				}
			}
		}
		
		Stats stats = new Stats();
		List<ExerciseInstance> exerciseInstances = hpc.getReviewedExerciseInstancesWithResultsFlagsUserForStats(filteredOrg);
		
		List<ExerciseInstance> runningInstances = hpc.getAllRunningExerciseInstances(filteredOrg);
		List<ExerciseInstance> pendingInstances = hpc.getManagegementPendingExerciseInstances(filteredOrg);
		List<ExerciseInstance> cancelledInstances = hpc.getManagementCancelledExerciseInstances(filteredOrg);
		List<AvailableExercise> availableExercises = hpc.getAllAvailableExercisesWithFlags(filteredOrg);
		List<User> users = hpc.getManagementAllUsers(filteredOrg);

		stats.setTotalUsers(users.size());
		stats.setTotalExercisesRun(pendingInstances.size() + cancelledInstances.size() + exerciseInstances.size());
		stats.setExercisesRunning(runningInstances.size());
		stats.setAvailableExercises(availableExercises.size());
		Integer issuesNr = 0;
		Set<String> technologies = new HashSet<String>();
		Set<String> issueCategories = new HashSet<String>();
		List<Team> teams = hpc.getManagementAllTeams(filteredOrg);

		for(AvailableExercise exercise : availableExercises){
			issuesNr += exercise.getFlags().size();
			technologies.add(exercise.getTechnology());
			for(Flag flag : exercise.getFlags()){
				issueCategories.add(flag.getCategory());
			}
		}
		Integer feedbackNr = 0;
		Integer totalMinutes = 0;
		Integer awardedTrophies = 0;
		Integer issuesIntroduced = 0;

		for(ExerciseInstance instance : exerciseInstances){
			totalMinutes += instance.getDuration();
			if(null!=instance.getFeedback() && instance.getFeedback()){
				feedbackNr++;
			}
			if(null!=instance.getTrophyAwarded() && instance.getTrophyAwarded()){
				awardedTrophies++;
			}
			if(null!=instance.getNewIssuesIntroduced() && instance.getNewIssuesIntroduced()){
				issuesIntroduced++;
			}	
		}

		stats.setIssues(issuesNr);
		stats.setTechnologies(technologies.size());
		stats.setTeams(teams.size());
		stats.setAwardedTrophies(awardedTrophies);
		stats.setIssueCategories(issueCategories.size());
		stats.setPendingReviews(pendingInstances.size());
		stats.setSubmittedReviews(exerciseInstances.size());
		stats.setCancelledReviews(cancelledInstances.size());
		stats.setTotalFeedback(feedbackNr);
		List<RTFGateway> activeGws = hpc.getAllActiveGateways();
		Set<Regions> regions = new HashSet<Regions>();
		for(RTFGateway gw : activeGws) {
			regions.add(gw.getRegion());
		}
		stats.setActiveRegions(regions.size());
		stats.setActiveGateways(activeGws.size());

		stats.setNewIssuesIntroduced(issuesIntroduced);
		stats.setMinutes(totalMinutes);
		if(totalMinutes == 0 || exerciseInstances.isEmpty())
			stats.setAvgExerciseDuration(0.0);
		else
			stats.setAvgExerciseDuration((double) (totalMinutes/(exerciseInstances.size())));

		statsUtils.setTimePerRegion(exerciseInstances,stats);
		statsUtils.setTimePerCategory(exerciseInstances,stats);
		statsUtils.setTimePerTeam(exerciseInstances,stats);

		stats.setIssuesRemediationRate(statsUtils.getRemediationRatePerIssue(exerciseInstances));
		stats.setCategoriesRemediationRate(statsUtils.getRemediationRatePerIssueCategory(exerciseInstances));
		stats.setRegionsRemediationRate(statsUtils.getRemediationRatePerRegion(exerciseInstances));
		stats.setTeamRemediationRate(statsUtils.getRemediationRatePerTeam(exerciseInstances));

		MessageGenerator.sendStatsMessage(stats, response);
	}
}