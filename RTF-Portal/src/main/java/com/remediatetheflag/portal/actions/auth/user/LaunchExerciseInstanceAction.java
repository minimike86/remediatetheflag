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
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.AvailableExercise;
import com.remediatetheflag.portal.model.AvailableExerciseStatus;
import com.remediatetheflag.portal.model.ExerciseInstance;
import com.remediatetheflag.portal.model.RTFECSTaskDefinition;
import com.remediatetheflag.portal.model.RTFGateway;
import com.remediatetheflag.portal.model.RTFInstanceReservation;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.AWSECSLaunchStrategy;
import com.remediatetheflag.portal.utils.AWSHelper;
import com.remediatetheflag.portal.utils.Constants;
import com.remediatetheflag.portal.utils.GuacamoleHelper;
import com.remediatetheflag.portal.utils.LaunchStrategy;
import com.remediatetheflag.portal.utils.RTFConfig;

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
		String region = json.get(Constants.ACTION_PARAM_REGION).getAsString();

		AWSHelper awsHelper = new AWSHelper();
		Regions awsRegion = null;
		try{
			awsRegion = Regions.valueOf(region);
		} catch(Exception e){
			MessageGenerator.sendErrorMessage("RegionNotFound", response);
			logger.error("Region "+region+" not found for user "+user.getIdUser());
			return;	
		}
		RTFGateway gw = hpc.getGatewayForRegion(awsRegion);
		if(null==gw || !gw.isActive()){
			MessageGenerator.sendErrorMessage("GWUnavailable", response);
			logger.error("User "+user.getIdUser()+" requested an unavailable gateway for region: "+awsRegion);
			return;
		}
		GuacamoleHelper guacHelper = new GuacamoleHelper();
		if(!guacHelper.isGuacOnline(gw)){
			MessageGenerator.sendErrorMessage("GWUnavailable", response);
			logger.error("User "+user.getIdUser()+" could not launch instance as Guac is not available in region: "+awsRegion);
			return;
		}
		AvailableExercise exercise = hpc.getAvailableExercise(exerciseId,user.getDefaultOrganization());
		if(null==exercise || (!exercise.getStatus().equals(AvailableExerciseStatus.AVAILABLE) && !exercise.getStatus().equals(AvailableExerciseStatus.UPDATED))){
			MessageGenerator.sendErrorMessage("ExerciseUnavailable", response);
			logger.error("User "+user.getIdUser()+" requested an unavailable exercise: "+exerciseId);
			return;
		}
		RTFECSTaskDefinition taskDefinition = hpc.getTaskDefinitionForExerciseInRegion(exerciseId,awsRegion);
		if(null==taskDefinition || !resourcesAvailable(Region.getRegion(awsRegion))) {
			MessageGenerator.sendErrorMessage("NoResourcesAvailable", response);
			logger.error("No resources for user "+user.getIdUser()+" launching exercise: "+exerciseId+" in region "+awsRegion);
			return;
		}
		else {
			String otp = UUID.randomUUID().toString().replaceAll("-", "");
			LaunchStrategy strategy = new AWSECSLaunchStrategy(user, exercise.getDuration(), RTFConfig.getExercisesCluster(), otp, taskDefinition, exercise);
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
