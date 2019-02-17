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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.AchievedTrophy;
import com.remediatetheflag.global.model.Challenge;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.ExerciseResult;
import com.remediatetheflag.global.model.ExerciseResultFile;
import com.remediatetheflag.global.model.ExerciseResultStatus;
import com.remediatetheflag.global.model.ExerciseScoringMode;
import com.remediatetheflag.global.model.ExerciseStatus;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.AWSHelper;
import com.remediatetheflag.global.utils.Constants;
import com.remediatetheflag.global.utils.GatewayHelper;
import com.remediatetheflag.global.utils.GuacamoleHelper;
import com.remediatetheflag.global.utils.NotificationsHelper;

public class StopExerciseInstanceAction extends IAction {
	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private NotificationsHelper notificationsHelper = new NotificationsHelper();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_ATTRIBUTE_JSON);
		Integer exerciseInstanceId = json.get(Constants.ACTION_PARAM_ID).getAsInt();

		ExerciseInstance instance = hpc.getActiveExerciseInstanceForUserWithAWSInstanceAndGW(user.getIdUser(),exerciseInstanceId);
		if(null!=instance){	

			Thread stopExerciseThread = new Thread(new Runnable() {

				@Override
				public void run() {
					// get results
					GatewayHelper gwHelper = new GatewayHelper();

					ExerciseResultFile fr = gwHelper.getResultFile(instance);
					instance.setResultFile(fr);

					Challenge exerciseChallenge = null;
					if(null!=instance.getChallengeId()) {
						exerciseChallenge = hpc.getChallengeFromId(instance.getChallengeId());
						exerciseChallenge.setLastActivity(new Date());
						hpc.updateChallenge(exerciseChallenge);
					}

					List<ExerciseResult> results = gwHelper.getResultStatus(instance,exerciseChallenge);
					if(!instance.getResults().isEmpty()) {
						for(ExerciseResult origRes : instance.getResults()){
							for(ExerciseResult newRes : results){
								if(newRes.getName().equals(origRes.getName())){
									if(origRes.getStatus().equals(ExerciseResultStatus.VULNERABLE) && newRes.getStatus().equals(ExerciseResultStatus.NOT_VULNERABLE)) {
										origRes.setStatus(ExerciseResultStatus.NOT_VULNERABLE);
										origRes.setScore(newRes.getScore());
										origRes.setLastChange(new Date());
										origRes.setFirstForFlag(newRes.getFirstForFlag());
										origRes.setSecondForFlag(newRes.getSecondForFlag());
										origRes.setThirdForFlag(newRes.getThirdForFlag());
									}
									break;
								}
							}
						}
						hpc.updateExerciseInstance(instance);
					}
					else {
						instance.setResults(results);
					}

					Integer totalScore = 0;
					for(ExerciseResult r : instance.getResults()){
						totalScore += r.getScore();
					}
					instance.getScore().setResult(totalScore);
					GuacamoleHelper guacHelper = new GuacamoleHelper();
					try {
						Integer duration = guacHelper.getUserExerciseDuration(instance.getGuac());
						instance.setDuration(duration);
					}
					catch(Exception e) {
						logger.error("Could not get duration for instance "+instance.getIdExerciseInstance()+e.getMessage());
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

					if(instance.getScoring().equals(ExerciseScoringMode.MANUAL_REVIEW)) {
						instance.setStatus(ExerciseStatus.STOPPED);
					}
					else {
						instance.setStatus(ExerciseStatus.AUTOREVIEWED);
						instance.setResultsAvailable(true);
						notificationsHelper.addCompletedReviewNotification(instance.getUser(), instance);
						List<ExerciseInstance> userRunExercises = hpc.getCompletedExerciseInstancesForUser(user.getIdUser());

						boolean alreadyRun = false;
						for(ExerciseInstance runEx : userRunExercises) {
							if(runEx.getAvailableExercise().getId().equals(instance.getAvailableExercise().getId()) || runEx.getAvailableExercise().getUuid().equals(instance.getAvailableExercise().getUuid())) {
								alreadyRun = true;
								break;
							}
						}
						if(!alreadyRun) {
							User dbUser = hpc.getUserFromUserId(user.getIdUser());
							dbUser.setExercisesRun(dbUser.getExercisesRun() + 1);
							dbUser.setScore(dbUser.getScore() + instance.getScore().getResult());
							hpc.updateUserInfo(dbUser);
							
							if(instance.getScore().getResult() >= instance.getScore().getTotal()) {
								List<AchievedTrophy> userAchievedTrophy = hpc.getAllAchievedTropiesForUser(instance.getUser().getIdUser());
								boolean alreadyAchieved = false;
								for(AchievedTrophy trophy : userAchievedTrophy){
									if(trophy.getTrophy().getName().equals(instance.getAvailableExercise().getTrophy().getName())){
										alreadyAchieved = true;
										break;
									}
								}
								if(!alreadyAchieved){
									AchievedTrophy t = new AchievedTrophy();
									t.setDate(new Date());
									t.setUser(instance.getUser());
									t.setTrophy(instance.getAvailableExercise().getTrophy());
									hpc.managementAddAchievedTrophy(t);
								}
							}
							
						}
					}
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

		logger.error("Could not stop ExerciseInstance: "+exerciseInstanceId+" for user: "+user.getIdUser());
		MessageGenerator.sendErrorMessage("NotFound", response);
	}
}