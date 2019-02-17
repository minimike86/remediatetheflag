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
package com.remediatetheflag.global.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.regions.Region;
import com.remediatetheflag.global.model.AchievedTrophy;
import com.remediatetheflag.global.model.Challenge;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.ExerciseResult;
import com.remediatetheflag.global.model.ExerciseResultFile;
import com.remediatetheflag.global.model.ExerciseResultStatus;
import com.remediatetheflag.global.model.ExerciseScoringMode;
import com.remediatetheflag.global.model.ExerciseStatus;
import com.remediatetheflag.global.model.RTFGateway;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;

public class RTFInstancesAutoShutdown implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(RTFInstancesAutoShutdown.class);
	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private GatewayHelper gwHelper = new GatewayHelper();
	private AWSHelper awsHelper = new AWSHelper();
	private NotificationsHelper notificationsHelper = new NotificationsHelper();

	@Override
	public void run() {
		logger.debug("Running shutdown task");

		//get running exercise instances from db, returns ExerciseInstance
		List<ExerciseInstance> exerciseInstances = hpc.getAllRunningExerciseInstancesWithAwsGWFlag();
		logger.debug("Returned "+exerciseInstances.size()+" running exercise instances from db");

		// get active regions
		List<Region> activeRegions = new LinkedList<Region>();
		for(RTFGateway gw : hpc.getAllActiveGateways()){
			activeRegions.add(Region.getRegion(gw.getRegion()));
		}
		logger.debug("Returned "+activeRegions.size()+" active regions from db");

		// get running ecs instances from AWS for active regions
		List<String> runningTasks = awsHelper.getRunningECSTasks(activeRegions);
		logger.debug("Returned "+runningTasks.size()+" running tasks for aws active regions");		

		Date now = new Date();
		for(String taskArn : runningTasks){
			boolean found = false;
			for(ExerciseInstance ei : exerciseInstances){
				if(ei.getEcsInstance().getTaskArn().equals(taskArn)){
					found = true;
					if(ei.getEndTime().getTime() < now.getTime()) {
						logger.debug("Exercise "+ei.getIdExerciseInstance()+" needs to be shutdown");
						Thread stopExerciseThread = new Thread(new Runnable() {
							@Override
							public void run() {
								if(!ei.getStatus().equals(ExerciseStatus.REVIEWED) && !ei.getStatus().equals(ExerciseStatus.AUTOREVIEWED) && !ei.getStatus().equals(ExerciseStatus.REVIEWED_MODIFIED)){
									logger.debug("Pulling exercise result file for exercise instance "+ei.getIdExerciseInstance());
									ExerciseResultFile fr = gwHelper.getResultFile(ei);
									ei.setResultFile(fr);

									logger.debug("Pulling exercise results for exercise instance "+ei.getIdExerciseInstance());
									Challenge exerciseChallenge = null;
									if(null!=ei.getChallengeId()) {
										exerciseChallenge = hpc.getChallengeFromId(ei.getChallengeId());
										exerciseChallenge.setLastActivity(new Date());
										hpc.updateChallenge(exerciseChallenge);
									}
									
									List<ExerciseResult> results = gwHelper.getResultStatus(ei,exerciseChallenge);
									if(!ei.getResults().isEmpty() ) {
										for(ExerciseResult origRes : ei.getResults()){
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
										hpc.updateExerciseInstance(ei);
									}
									else {
										ei.setResults(results);
									}
									
									Integer totalScore = 0;
									for(ExerciseResult r : ei.getResults()){
										totalScore += r.getScore();
									}
									ei.getScore().setResult(totalScore);
									
									if(ei.getScoring().equals(ExerciseScoringMode.MANUAL_REVIEW)) {
										ei.setStatus(ExerciseStatus.STOPPED);
									}
									else {
										ei.setStatus(ExerciseStatus.AUTOREVIEWED);
										List<ExerciseInstance> userRunExercises = hpc.getCompletedExerciseInstancesForUser(ei.getUser().getIdUser());
										ei.setResultsAvailable(true);
										notificationsHelper.addCompletedReviewNotification(ei.getUser(), ei);

										boolean alreadyRun = false;
										for(ExerciseInstance runEx : userRunExercises) {
											if(runEx.getAvailableExercise().getId().equals(ei.getAvailableExercise().getId()) || runEx.getAvailableExercise().getUuid().equals(ei.getAvailableExercise().getUuid())) {
												alreadyRun = true;
												break;
											}
										}
										if(!alreadyRun) {
											User dbUser = hpc.getUserFromUserId(ei.getUser().getIdUser());
											dbUser.setExercisesRun(dbUser.getExercisesRun() + 1);
											dbUser.setScore(dbUser.getScore() + ei.getScore().getResult());
											hpc.updateUserInfo(dbUser);
											
											if(ei.getScore().getResult() >= ei.getScore().getTotal()) {
												List<AchievedTrophy> userAchievedTrophy = hpc.getAllAchievedTropiesForUser(ei.getUser().getIdUser());
												boolean alreadyAchieved = false;
												for(AchievedTrophy trophy : userAchievedTrophy){
													if(trophy.getTrophy().getName().equals(ei.getAvailableExercise().getTrophy().getName())){
														alreadyAchieved = true;
														break;
													}
												}
												if(!alreadyAchieved){
													AchievedTrophy t = new AchievedTrophy();
													t.setDate(new Date());
													t.setUser(ei.getUser());
													t.setTrophy(ei.getAvailableExercise().getTrophy());
													hpc.managementAddAchievedTrophy(t);
												}
											}

											
										}
										
									}

									ei.getEcsInstance().setStatus(Constants.STATUS_STOPPED);

									Calendar cal = Calendar.getInstance();
									ei.setEndTime(cal.getTime());
									ei.getEcsInstance().setShutdownTime(cal.getTime());
									GuacamoleHelper guacHelper = new GuacamoleHelper();
									try {
										Integer duration = guacHelper.getUserExerciseDuration(ei.getGuac());
										ei.setDuration(duration);
									}
									catch(Exception e) {
										logger.error("Could not get duration for instance "+ei.getIdExerciseInstance()+e.getMessage());
										ei.setDuration(-1);
									}
									hpc.updateExerciseInstance(ei);
								}
								awsHelper.terminateTask(ei.getEcsInstance());
							}});
						stopExerciseThread.start();
						ei.setStatus(ExerciseStatus.STOPPING);
						hpc.updateExerciseInstance(ei);
					}
				}
			}

			if(!found && RTFConfig.getEnvironment().equals(Constants.PROD_ENVIRONMENT)){ //orphans
				Date createdTime = awsHelper.getRunningECSTaskStartTime(taskArn);
				long time = System.currentTimeMillis();
				if(time-createdTime.getTime() >= 1200000) {
					logger.warn("Exercise "+taskArn+" is orphan and needs to be shutdown");
					awsHelper.terminateTask(taskArn);		
				}
				else {
					logger.debug("Fullfilled Reservation "+(time-createdTime.getTime())+" ms old");
				}
			}
		}
		logger.debug("End of shutdown task");
	}
}