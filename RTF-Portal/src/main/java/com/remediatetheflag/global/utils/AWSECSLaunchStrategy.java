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

import com.remediatetheflag.global.model.AvailableExercise;
import com.remediatetheflag.global.model.ExerciseInstanceLaunchType;
import com.remediatetheflag.global.model.RTFECSContainerTask;
import com.remediatetheflag.global.model.RTFECSTaskDefinition;
import com.remediatetheflag.global.model.RTFInstanceReservation;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;

public class AWSECSLaunchStrategy implements LaunchStrategy {

	private User user;
	private Integer durationMinutes;
	private String instanceName;
	private String clusterName;
	private AvailableExercise exercise;
	private RTFECSTaskDefinition taskDefinition;
	private String tmpPassword;
	private Integer challengeId;

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private AWSHelper helper = new AWSHelper();

	public AWSECSLaunchStrategy(User user, Integer durationMinutes, String clusterName, String password, RTFECSTaskDefinition taskDefinition, AvailableExercise exercise, Integer challengeId) {
		this.user = user;
		this.clusterName = clusterName;
		this.tmpPassword = password;
		this.taskDefinition = taskDefinition;
		this.exercise = exercise;
		this.durationMinutes = durationMinutes;
		this.instanceName  = "RTF-"+this.user.getIdUser()+"-"+System.currentTimeMillis();
		this.challengeId = challengeId;
	}

	@Override
	public RTFInstanceReservation launch() {
		RTFECSContainerTask rtfInstance = helper.createInstance(clusterName, instanceName, tmpPassword, taskDefinition, durationMinutes, user);
		RTFInstanceReservation reservation = new RTFInstanceReservation();
		if(null!=rtfInstance){
			reservation.setError(false);
			reservation.setFulfilled(false);
			reservation.setExercise(exercise);
			reservation.setType(ExerciseInstanceLaunchType.ECS);
			reservation.setOrganization(user.getDefaultOrganization());
			reservation.setUser(user);
			reservation.setWaitSeconds(5);
			reservation.setEcs(rtfInstance);
			reservation.setTmpPassword(tmpPassword);
			reservation.setChallengeId(challengeId);
		}
		else {
			reservation.setError(true);
			reservation.setFulfilled(false);
			reservation.setExercise(exercise);
			reservation.setType(ExerciseInstanceLaunchType.ECS);
			reservation.setOrganization(user.getDefaultOrganization());
			reservation.setUser(user);
			reservation.setWaitSeconds(0);
			reservation.setEcs(null);
			reservation.setTmpPassword(tmpPassword);
			reservation.setChallengeId(challengeId);
		}
		Integer id = hpc.addReservation(reservation);
		reservation.setId(id);
		return reservation;
	}
}