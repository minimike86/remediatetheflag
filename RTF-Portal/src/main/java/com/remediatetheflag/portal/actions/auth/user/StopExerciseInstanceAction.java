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

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.ExerciseInstance;
import com.remediatetheflag.portal.model.ExerciseResult;
import com.remediatetheflag.portal.model.ExerciseResultFile;
import com.remediatetheflag.portal.model.ExerciseStatus;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.AWSHelper;
import com.remediatetheflag.portal.utils.Constants;
import com.remediatetheflag.portal.utils.GatewayHelper;
import com.remediatetheflag.portal.utils.GuacamoleHelper;

public class StopExerciseInstanceAction extends IAction {
	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_ATTRIBUTE_JSON);

		List<ExerciseInstance> activeInstances = hpc.getActiveExerciseInstanceForUserWithAWSInstanceAndGW(user.getIdUser());
		Integer exerciseInstanceId = json.get(Constants.ACTION_PARAM_ID).getAsInt();
		for(ExerciseInstance instance : activeInstances){
			if(instance.getIdExerciseInstance().equals(exerciseInstanceId)){	

				Thread stopExerciseThread = new Thread(new Runnable() {

					@Override
					public void run() {
						// get results
						GatewayHelper gwHelper = new GatewayHelper();
						ExerciseResultFile fr = gwHelper.getResultFile(instance);
						instance.setResultFile(fr);

						List<ExerciseResult> results = gwHelper.getResultStatus(instance);
						instance.setResults(results);

						GuacamoleHelper guacHelper = new GuacamoleHelper();
						try {
							Integer duration = guacHelper.getUserExerciseDuration(instance.getGuac());
							instance.setDuration(duration);
						}
						catch(Exception e) {
							instance.setDuration(-1);
						}
						// stop instance
						AWSHelper awsHelper = new AWSHelper();
						if(null!=instance.getEcsInstance()){
							awsHelper.terminateTask(instance.getEcsInstance());
							instance.getEcsInstance().setStatus(Constants.STATUS_STOPPED);
						}
						else {
							MessageGenerator.sendErrorMessage("Error", response);
						}
						instance.setStatus(ExerciseStatus.STOPPED);
						instance.getEcsInstance().setStatus(Constants.STATUS_TERMINATED);
						Calendar cal = Calendar.getInstance();
						instance.setEndTime(cal.getTime());

						hpc.updateExerciseInstance(instance);
					}

				});
				stopExerciseThread.start();
				instance.setStatus(ExerciseStatus.STOPPING);
				hpc.updateExerciseInstance(instance);

				MessageGenerator.sendSuccessMessage(response);
				return;
			}
		}
		logger.error("Could not stop ExerciseInstance: "+exerciseInstanceId+" for user: "+user.getIdUser());
		MessageGenerator.sendErrorMessage("NotFound", response);
	}
}