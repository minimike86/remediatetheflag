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
package com.remediatetheflag.global.actions.auth.user;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.AvailableExercise;
import com.remediatetheflag.global.model.AvailableExerciseStatus;
import com.remediatetheflag.global.model.Challenge;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.ExerciseRegion;
import com.remediatetheflag.global.model.RTFECSTaskDefinition;
import com.remediatetheflag.global.model.RTFGateway;
import com.remediatetheflag.global.model.RTFInstanceReservation;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.AWSECSLaunchStrategy;
import com.remediatetheflag.global.utils.AWSHelper;
import com.remediatetheflag.global.utils.Constants;
import com.remediatetheflag.global.utils.GuacamoleHelper;
import com.remediatetheflag.global.utils.LaunchStrategy;
import com.remediatetheflag.global.utils.RTFConfig;

public class LaunchExerciseInstanceAction extends IAction{

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_ATTRIBUTE_JSON);

		if(user.getCredits()==0) {
			MessageGenerator.sendErrorMessage("CreditsLimit", response);
			logger.warn("No credits left for user "+user.getIdUser());
			return;	
		}
		List<ExerciseInstance> activeInstances = hpc.getActiveExerciseInstanceForUser(user.getIdUser());
		if(activeInstances.size()>0 && activeInstances.size() >= user.getInstanceLimit()){
			MessageGenerator.sendErrorMessage("InstanceLimit", response);
			logger.warn("Instance limit reached for user "+user.getIdUser()+" current: "+activeInstances.size()+" limit:"+user.getInstanceLimit());
			return;	
		}

		Integer exerciseId = json.get(Constants.ACTION_PARAM_ID).getAsInt();
		JsonElement challengeElement = json.get(Constants.ACTION_PARAM_CHALLENGE_ID);

		AvailableExercise exercise = hpc.getAvailableExercise(exerciseId,user.getDefaultOrganization());
		if(null==exercise || exercise.getStatus().equals(AvailableExerciseStatus.INACTIVE) || exercise.getStatus().equals(AvailableExerciseStatus.COMING_SOON)){
			MessageGenerator.sendErrorMessage("ExerciseUnavailable", response);
			logger.error("User "+user.getIdUser()+" requested an unavailable exercise: "+exerciseId);
			return;
		}

		Integer validatedChallengeId = null;

		List<Challenge> userChallengesWithExercise = hpc.getChallengesForUserExercise(exercise.getId(), user.getIdUser());

		if(!userChallengesWithExercise.isEmpty()) {
			Boolean inChallenge = false;
			if(null!=challengeElement && (challengeElement.getAsInt()!=-1)) {
				Integer challengeId = challengeElement.getAsInt();
				Challenge c = hpc.getChallengeWithDetailsForUser(challengeId, user.getIdUser());
				if(null!=c) {
					for(AvailableExercise avE : c.getExercises()) {
						if(avE.getId().equals(exerciseId)){
							validatedChallengeId = c.getIdChallenge();
							inChallenge = true;
							break;
						}
					}
				}
				if(!inChallenge) {
					logger.error("Exercise "+exerciseId+" is NOT in Challenge: "+challengeId+", but a challenge id was provided by user: "+user.getIdUser());
					validatedChallengeId = null;
				}
				for(ExerciseInstance e : c.getRunExercises()) {
					if(e.getUser().getIdUser().equals(user.getIdUser()) && e.getAvailableExercise().getId().equals(exerciseId)) {
						logger.warn("Exercise "+exerciseId+" is in Challenge: "+challengeId+", but user: "+user.getIdUser()+" already run it");
						validatedChallengeId = null;
						break;
					}
				}
			}
			else {
				logger.warn("Exercise "+exerciseId+" is in Challenge, but no challenge id was provided by user: "+user.getIdUser());
				Boolean run = false;
				for(Challenge dbChallenges :userChallengesWithExercise) {
					for(ExerciseInstance dbChallengeExercise : dbChallenges.getRunExercises()) {
						if(dbChallengeExercise.getUser().getIdUser().equals(user.getIdUser()) && dbChallengeExercise.getAvailableExercise().getId().equals(exerciseId)) {
							run = true;
						}
					}
					if(!run) {
						validatedChallengeId = userChallengesWithExercise.get(0).getIdChallenge();
						break;
					}
				}
			}	
		}

		JsonElement exerciseRegionsElement = json.getAsJsonArray(Constants.ACTION_PARAM_REGION);
		Type listType = new TypeToken<ArrayList<ExerciseRegion>>(){}.getType();
		List<ExerciseRegion> regList  = null;
		try {
			regList = new Gson().fromJson(exerciseRegionsElement, listType);

		}catch(Exception e) {
			MessageGenerator.sendErrorMessage("NoRegionsPing", response);
			logger.error("User "+user.getIdUser()+" requested an exercise: "+exerciseId+" without supplying regions pings");
			return;
		}
		Collections.sort(regList, new Comparator<ExerciseRegion>() {
			public int compare(ExerciseRegion p1, ExerciseRegion p2) {
				return Integer.valueOf(p1.getPing()).compareTo(p2.getPing());
			}
		});
		AWSHelper awsHelper = new AWSHelper();
		RTFECSTaskDefinition taskDefinition = null;
		Regions awsRegion = null;

		Boolean satisfied = false;
		for(ExerciseRegion r : regList) {
			if(satisfied)
				break;
			try{
				awsRegion = Regions.valueOf(r.getName());
			} catch(Exception e){
				logger.warn("Region "+r.getName()+" not found for user "+user.getIdUser()+" for launching exercise: "+exerciseId);
				continue;	
			}

			RTFGateway gw = hpc.getGatewayForRegion(awsRegion);
			if(null==gw || !gw.isActive()){
				logger.warn("User "+user.getIdUser()+" requested an unavailable gateway for region: "+awsRegion+" for launching exercise: "+exerciseId);
				continue;
			}
			GuacamoleHelper guacHelper = new GuacamoleHelper();
			if(!guacHelper.isGuacOnline(gw)){
				logger.warn("User "+user.getIdUser()+" could not launch instance as Guac is not available in region: "+awsRegion+" for launching exercise: "+exerciseId);
				continue;
			}
			taskDefinition = hpc.getTaskDefinitionForExerciseInRegion(exerciseId,awsRegion);
			if(null==taskDefinition ) {
				taskDefinition = hpc.getTaskDefinitionFromUUID(exercise.getUuid(),awsRegion);
			}
			if(null==taskDefinition || !resourcesAvailable(Region.getRegion(awsRegion))) {
				logger.warn("No resources for user "+user.getIdUser()+" launching exercise: "+exerciseId+" in region "+awsRegion);
				continue;
			}
			satisfied = true;
		}

		if(!satisfied) {
			MessageGenerator.sendErrorMessage("Unavailable", response);
			logger.error("User "+user.getIdUser()+" could not launch instance as there is no available region/gateway/guac/task def for exercise id "+exerciseId);
			return;
		}

		String otp = UUID.randomUUID().toString().replaceAll("-", "");
		LaunchStrategy strategy = new AWSECSLaunchStrategy(user, exercise.getDuration(), RTFConfig.getExercisesCluster(), otp, taskDefinition, exercise, validatedChallengeId);
		RTFInstanceReservation reservation = awsHelper.createRTFInstance(strategy);
		if(!reservation.getError()) {
			logger.info("Reservation "+reservation.getId()+" for user "+user.getIdUser()+"  launching exercise: "+exerciseId+" in region "+awsRegion);
			if(user.getCredits()!=-1) {
				user.setCredits(user.getCredits()-1);
				hpc.updateUserInfo(user);
				request.getSession().setAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT, user);
				logger.info("Reducing 1 credit for user "+user.getIdUser()+" to: "+user.getCredits());
			}
			MessageGenerator.sendReservationMessage(reservation, response);
			return;
		}
		else {
			MessageGenerator.sendErrorMessage("InstanceUnavailable", response);
		}

	}
	private boolean resourcesAvailable(Region region) {
		AWSHelper aws = new AWSHelper();
		if(aws.getClusterContainerInstances(region)>0) {
			if(aws.getClusterMemoryReservation(region)<90) {
				return true;
			}
			return false;
		}
		return false;
	}
}
