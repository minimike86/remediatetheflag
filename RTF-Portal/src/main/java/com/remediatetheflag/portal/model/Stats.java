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
package com.remediatetheflag.portal.model;

import java.util.Map;

import com.google.gson.annotations.Expose;

public class Stats {
	
	@Expose
	private Integer totalUsers;
	@Expose
	private Integer loggedUsers;
	@Expose
	private Integer totalExercisesRun;
	@Expose
	private Integer exercisesRunning;
	@Expose
	private Integer availableExercises;
	@Expose
	private Integer issues;
	@Expose
	private Integer technologies;
	@Expose
	private Integer teams;
	@Expose
	private Integer awardedTrophies;
	@Expose
	private Integer issueCategories;
	@Expose
	private Integer pendingReviews;
	@Expose
	private Integer submittedReviews;
	@Expose
	private Integer cancelledReviews;
	@Expose
	private Integer totalFeedback;
	@Expose
	private Integer activeRegions;
	@Expose
	private Integer newIssuesIntroduced;
	@Expose
	private Integer minutes;
	@Expose
	private Double avgExerciseDuration;
	@Expose
	private Integer activeGateways;
	
	@Expose
	private Map<String, Integer> exercisesRunPerRegion;
	@Expose
	private Map<String, Integer> technologiesRunPerRegion;
	@Expose
	private Map<String, Integer> issueCategoryRunPerRegion;
	@Expose
	private Map<String, Integer> availableExercisesPerTechnology;
	@Expose
	private Map<String, Integer> runExercisesPerTechnology;
	@Expose
	private Map<String, Integer> availableExercisesPerIssueCategory;
	@Expose
	private Map<String, Integer> runExercisesPerIssueCategory;
	@Expose
	private Map<String, Integer> avgMinutesPerRegion;
	@Expose
	private Map<String, Integer> avgMinutesPerTechnology;
	@Expose
	private Map<String, Integer> avgMinutesPerIssueCategory;
	@Expose
	private Map<String, Integer> avgMinutesPerTeam;
	@Expose
	private Map<String, Long> totalMinutesPerRegion;
	@Expose
	private Map<String, Long> totalMinutesPerTechnology;
	@Expose
	private Map<String, Long> totalMinutesPerIssueCategory;
	@Expose
	private Map<String, Long> totalMinutesPerTeam;
	@Expose
	private Map<String,Map<String, Integer>> teamRemediationRate;
	@Expose
	private Map<String, Integer> globalAwardedTrophies;
	@Expose
	private Map<String, Integer> teamAwardedTrophies;
	@Expose
	private Map<String,Map<String, Integer>> regionsRemediationRate;
	@Expose
	private Map<String,Map<String, Integer>> issuesRemediationRate;
	@Expose
	private Map<String,Map<String, Integer>> categoriesRemediationRate;
	
	public Integer getTotalUsers() {
		return totalUsers;
	}
	public void setTotalUsers(Integer totalUsers) {
		this.totalUsers = totalUsers;
	}
	public Integer getLoggedUsers() {
		return loggedUsers;
	}
	public void setLoggedUsers(Integer loggedUsers) {
		this.loggedUsers = loggedUsers;
	}
	public Integer getTotalExercisesRun() {
		return totalExercisesRun;
	}
	public void setTotalExercisesRun(Integer totalExercisesRun) {
		this.totalExercisesRun = totalExercisesRun;
	}
	public Integer getExercisesRunning() {
		return exercisesRunning;
	}
	public void setExercisesRunning(Integer exercisesRunning) {
		this.exercisesRunning = exercisesRunning;
	}
	public Integer getTechnologies() {
		return technologies;
	}
	public void setTechnologies(Integer technologies) {
		this.technologies = technologies;
	}
	public Integer getTeams() {
		return teams;
	}
	public void setTeams(Integer teams) {
		this.teams = teams;
	}

	public Integer getAwardedTrophies() {
		return awardedTrophies;
	}
	public void setAwardedTrophies(Integer awardedTrophies) {
		this.awardedTrophies = awardedTrophies;
	}
	public Integer getIssueCategories() {
		return issueCategories;
	}
	public void setIssueCategories(Integer issueCategories) {
		this.issueCategories = issueCategories;
	}
	public Integer getPendingReviews() {
		return pendingReviews;
	}
	public void setPendingReviews(Integer pendingReviews) {
		this.pendingReviews = pendingReviews;
	}
	public Integer getSubmittedReviews() {
		return submittedReviews;
	}
	public void setSubmittedReviews(Integer submittedReviews) {
		this.submittedReviews = submittedReviews;
	}
	public Integer getCancelledReviews() {
		return cancelledReviews;
	}
	public void setCancelledReviews(Integer cancelledReviews) {
		this.cancelledReviews = cancelledReviews;
	}
	public Integer getTotalFeedback() {
		return totalFeedback;
	}
	public void setTotalFeedback(Integer totalFeedback) {
		this.totalFeedback = totalFeedback;
	}
	public Integer getActiveRegions() {
		return activeRegions;
	}
	public void setActiveRegions(Integer activeRegions) {
		this.activeRegions = activeRegions;
	}
	public Integer getNewIssuesIntroduced() {
		return newIssuesIntroduced;
	}
	public void setNewIssuesIntroduced(Integer newIssuesIntroduced) {
		this.newIssuesIntroduced = newIssuesIntroduced;
	}
	public Integer getMinutes() {
		return minutes;
	}
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	public Double getAvgExerciseDuration() {
		return avgExerciseDuration;
	}
	public void setAvgExerciseDuration(Double i) {
		this.avgExerciseDuration = i;
	}
	public Integer getAvailableExercises() {
		return availableExercises;
	}
	public void setAvailableExercises(Integer availableExercises) {
		this.availableExercises = availableExercises;
	}
	public Integer getIssues() {
		return issues;
	}
	public void setIssues(Integer issues) {
		this.issues = issues;
	}
	
	
	
	
	
	
	public Map<String, Integer> getExercisesRunPerRegion() {
		return exercisesRunPerRegion;
	}
	public void setExercisesRunPerRegion(Map<String, Integer> exercisesRunPerRegion) {
		this.exercisesRunPerRegion = exercisesRunPerRegion;
	}
	public Map<String, Integer> getTechnologiesRunPerRegion() {
		return technologiesRunPerRegion;
	}
	public void setTechnologiesRunPerRegion(Map<String, Integer> technologiesRunPerRegion) {
		this.technologiesRunPerRegion = technologiesRunPerRegion;
	}
	public Map<String, Integer> getIssueCategoryRunPerRegion() {
		return issueCategoryRunPerRegion;
	}
	public void setIssueCategoryRunPerRegion(Map<String, Integer> issueCategoryRunPerRegion) {
		this.issueCategoryRunPerRegion = issueCategoryRunPerRegion;
	}
	public Map<String, Integer> getAvailableExercisesPerTechnology() {
		return availableExercisesPerTechnology;
	}
	public void setAvailableExercisesPerTechnology(Map<String, Integer> availableExercisesPerTechnology) {
		this.availableExercisesPerTechnology = availableExercisesPerTechnology;
	}
	public Map<String, Integer> getRunExercisesPerTechnology() {
		return runExercisesPerTechnology;
	}
	public void setRunExercisesPerTechnology(Map<String, Integer> runExercisesPerTechnology) {
		this.runExercisesPerTechnology = runExercisesPerTechnology;
	}
	public Map<String, Integer> getAvailableExercisesPerIssueCategory() {
		return availableExercisesPerIssueCategory;
	}
	public void setAvailableExercisesPerIssueCategory(Map<String, Integer> availableExercisesPerIssueCategory) {
		this.availableExercisesPerIssueCategory = availableExercisesPerIssueCategory;
	}
	public Map<String, Integer> getRunExercisesPerIssueCategory() {
		return runExercisesPerIssueCategory;
	}
	public void setRunExercisesPerIssueCategory(Map<String, Integer> runExercisesPerIssueCategory) {
		this.runExercisesPerIssueCategory = runExercisesPerIssueCategory;
	}
	public Map<String, Integer> getAverageMinutesPerRegion() {
		return avgMinutesPerRegion;
	}
	public void setAverageMinutesPerRegion(Map<String, Integer> minutesPerRegion) {
		this.avgMinutesPerRegion = minutesPerRegion;
	}

	public Map<String, Integer> getAverageMinutesPerIssueCategory() {
		return avgMinutesPerIssueCategory;
	}
	public void setAverageMinutesPerIssueCategory(Map<String, Integer> minutesPerIssueCategory) {
		this.avgMinutesPerIssueCategory = minutesPerIssueCategory;
	}
	public Map<String,Map<String, Integer>> getTeamRemediationRate() {
		return teamRemediationRate;
	}
	public void setTeamRemediationRate(Map<String,Map<String, Integer>> teamRemediationRate) {
		this.teamRemediationRate = teamRemediationRate;
	}
	public Map<String, Integer> getGlobalAwardedTrophies() {
		return globalAwardedTrophies;
	}
	public void setGlobalAwardedTrophies(Map<String, Integer> globalAwardedTrophies) {
		this.globalAwardedTrophies = globalAwardedTrophies;
	}
	public Map<String, Integer> getTeamAwardedTrophies() {
		return teamAwardedTrophies;
	}
	public void setTeamAwardedTrophies(Map<String, Integer> regionAwardedTrophies) {
		this.teamAwardedTrophies = regionAwardedTrophies;
	}
	public Map<String,Map<String, Integer>> getIssuesRemediationRate() {
		return issuesRemediationRate;
	}
	public void setIssuesRemediationRate(Map<String,Map<String, Integer>> issuesRemediationRate) {
		this.issuesRemediationRate = issuesRemediationRate;
	}
	public Map<String, Map<String, Integer>> getCategoriesRemediationRate() {
		return categoriesRemediationRate;
	}
	public void setCategoriesRemediationRate(Map<String, Map<String, Integer>> categoriesRemediationRate) {
		this.categoriesRemediationRate = categoriesRemediationRate;
	}
	public Map<String,Map<String, Integer>> getRegionsRemediationRate() {
		return regionsRemediationRate;
	}
	public void setRegionsRemediationRate(Map<String,Map<String, Integer>> regionsRemediationRate) {
		this.regionsRemediationRate = regionsRemediationRate;
	}
	public Map<String, Integer> getAverageMinutesPerTechnology() {
		return avgMinutesPerTechnology;
	}
	public void setAverageMinutesPerTechnology(Map<String, Integer> minutesPerTechnology) {
		this.avgMinutesPerTechnology = minutesPerTechnology;
	}
	public Map<String, Integer> getAverageMinutesPerTeam() {
		return avgMinutesPerTeam;
	}
	public void setAverageMinutesPerTeam(Map<String, Integer> minutesPerTeam) {
		this.avgMinutesPerTeam = minutesPerTeam;
		
	}
	public Map<String, Long> getTotalMinutesPerRegion() {
		return totalMinutesPerRegion;
	}
	public void setTotalMinutesPerRegion(Map<String, Long> totalMinutesPerRegion) {
		this.totalMinutesPerRegion = totalMinutesPerRegion;
	}
	public Map<String, Long> getTotalMinutesPerTechnology() {
		return totalMinutesPerTechnology;
	}
	public void setTotalMinutesPerTechnology(Map<String, Long> totalMinutesPerTechnology) {
		this.totalMinutesPerTechnology = totalMinutesPerTechnology;
	}
	public Map<String, Long> getTotalMinutesPerIssueCategory() {
		return totalMinutesPerIssueCategory;
	}
	public void setTotalMinutesPerIssueCategory(Map<String, Long> totalMinutesPerIssueCategory) {
		this.totalMinutesPerIssueCategory = totalMinutesPerIssueCategory;
	}
	public Map<String, Long> getTotalMinutesPerTeam() {
		return totalMinutesPerTeam;
	}
	public void setTotalMinutesPerTeam(Map<String, Long> totalMinutesPerTeam) {
		this.totalMinutesPerTeam = totalMinutesPerTeam;
	}
	public Integer getActiveGateways() {
		return activeGateways;	
	}
	public void setActiveGateways(Integer allActiveGateways) {
		this.activeGateways = allActiveGateways;
	}

	
}
