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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.ExerciseInstance;
import com.remediatetheflag.portal.model.GuacTempUser;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;
import com.remediatetheflag.portal.utils.GuacamoleHelper;

public class GetGuacTokenForExerciseAction extends IAction{
	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private GuacamoleHelper guacHelper = new GuacamoleHelper();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement jsonElement = json.get(Constants.ACTION_PARAM_ID);
		Integer idExercise = jsonElement.getAsInt();
		Integer eiId = -1;
		List<ExerciseInstance> exercises =  hpc.getActiveExerciseInstanceForUserWithAWSInstanceAndGW(sessionUser.getIdUser());	
		GuacTempUser guacTemp = null;
		for(ExerciseInstance ei : exercises){
			if(ei.getIdExerciseInstance().equals(idExercise)){
				guacTemp = ei.getGuac();
				eiId = ei.getIdExerciseInstance();
				break;
			}
		}
		String token = null;
		if(guacTemp!=null){
			token = guacHelper.getFreshToken( guacTemp.getGateway(), guacTemp.getUsername(), guacTemp.getPassword());
		}
		else{
			logger.error("Could not find active ExerciseInstance "+idExercise+" for user: "+sessionUser.getIdUser());
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		
		if(null!=token){
			MessageGenerator.sendTokenMessage(eiId,guacTemp.getGateway().getFqdn(), guacTemp.getUsername(), token, 0, response);
		}
		else{
			logger.error("Could not get GuacToken for ExerciseInstance "+idExercise+" for user: "+sessionUser.getIdUser());
			MessageGenerator.sendErrorMessage("CouldNotRetrieveToken", response);
		}	
	}
}