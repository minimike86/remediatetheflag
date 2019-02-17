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

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.AvailableExercise;
import com.remediatetheflag.global.model.ExerciseRegion;
import com.remediatetheflag.global.model.RTFECSTaskDefinition;
import com.remediatetheflag.global.model.RTFGateway;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;

public class GetAvailableRegionsForExerciseAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement jsonElement = json.get(Constants.ACTION_PARAM_ID);
		Integer idExercise = jsonElement.getAsInt();
		List<RTFGateway> regions = hpc.getAllActiveGateways();
		List<ExerciseRegion> availableRegions = new LinkedList<ExerciseRegion>();
		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);	

		for(RTFGateway gw : regions){
			if(null==gw.getRegion())
				continue;
			RTFECSTaskDefinition task = hpc.getTaskDefinitionForExerciseInRegion(idExercise, gw.getRegion(),sessionUser.getDefaultOrganization());
			if(null==task){
				AvailableExercise exercise = hpc.getAvailableExercise(idExercise,sessionUser.getDefaultOrganization());
				if(null!=exercise)
					task = hpc.getTaskDefinitionFromUUID(exercise.getUuid(),gw.getRegion());
			}
			if(null!=task){
				ExerciseRegion r = new ExerciseRegion();
				r.setFqdn(gw.getFqdn());
				r.setName(gw.getRegion().toString());
				r.setPing(-1);	
				availableRegions.add(r);
			}
			else{
				logger.warn("Exercise "+idExercise+" doesn't have a TaskDefinition in region "+gw.getRegion().getName());
			}
		}
		MessageGenerator.sendExerciseRegionsMessage(availableRegions,response);
	}
}