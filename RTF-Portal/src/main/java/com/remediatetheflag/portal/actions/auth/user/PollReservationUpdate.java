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
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.ExerciseInstance;
import com.remediatetheflag.portal.model.ExerciseInstanceLaunchType;
import com.remediatetheflag.portal.model.ExerciseInstanceType;
import com.remediatetheflag.portal.model.ExerciseResult;
import com.remediatetheflag.portal.model.ExerciseScore;
import com.remediatetheflag.portal.model.ExerciseStatus;
import com.remediatetheflag.portal.model.GuacTempUser;
import com.remediatetheflag.portal.model.RTFGateway;
import com.remediatetheflag.portal.model.RTFInstanceReservation;
import com.remediatetheflag.portal.model.TokenConnectionMessage;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.AWSHelper;
import com.remediatetheflag.portal.utils.Constants;
import com.remediatetheflag.portal.utils.GuacamoleHelper;

public class PollReservationUpdate extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_ATTRIBUTE_JSON);
		Integer reservationId = json.get(Constants.ACTION_PARAM_ID).getAsInt();
		
		RTFInstanceReservation reservation = hpc.getReservation(reservationId);

		if(null==reservation || reservation.getError() || !reservation.getUser().getIdUser().equals(user.getIdUser())){
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		if(reservation.getFulfilled()){
			MessageGenerator.sendReservationMessage(reservation, response);
			return;
		}
		if(null!=reservation.getEcs()) {
			AWSHelper helper = new AWSHelper();
			RTFInstanceReservation updatedReservation = helper.pollReservation(reservation);
			if(reservation.getError()) {
				hpc.updateReservation(reservation);
				MessageGenerator.sendErrorMessage("Expired", response);
				return;
			}
			if(!updatedReservation.getFulfilled()) {
				MessageGenerator.sendReservationMessage(updatedReservation, response);
				return;
			}
			else {
				RTFGateway gw = hpc.getGatewayForRegion(reservation.getEcs().getRegion());
				if(null==gw || !gw.isActive()){
					MessageGenerator.sendErrorMessage("GWUnavailable", response);
					logger.error("User "+user.getIdUser()+" requested an unavailable gateway for region: "+reservation.getEcs().getRegion().toString());
					return;
				}
				GuacamoleHelper guacHelper = new GuacamoleHelper();
				if(!guacHelper.isGuacOnline(gw)){
					MessageGenerator.sendErrorMessage("GuacUnavailable", response);
					logger.error("User "+user.getIdUser()+" could not launch instance as Guac is not available in region: "+reservation.getEcs().getRegion().toString());
					return;
				}
				GuacTempUser guacUser = guacHelper.setupUser(user,reservation.getEcs(),gw,reservation.getTmpPassword());
				if(null==guacUser || null==guacUser.getLastValidToken()){
					MessageGenerator.sendErrorMessage("GuacUnavailable", response);
					logger.error("User "+user.getIdUser()+" could not contact guacamole for task: "+reservation.getEcs().getTaskArn()+" in gateway: "+gw.getName());
					return;
				}
				hpc.addGuacTempUser(guacUser);

				ExerciseInstance ei = new ExerciseInstance();
				reservation.getEcs().setStatus(Constants.STATUS_RUNNING);
				if(hpc.isExerciseInChallengeForUser(updatedReservation.getExercise().getId(),updatedReservation.getUser().getIdUser())) {
					ei.setType(ExerciseInstanceType.CHALLENGE);
				}
				else {
					ei.setType(ExerciseInstanceType.TRAINING);
				}				
				ei.setEcsInstance(reservation.getEcs());
				ei.setRegion(reservation.getEcs().getRegion());
				ei.setResults(new LinkedList<ExerciseResult>());
				ei.setStartTime(new Date());
				ei.setCountResultsReviewedByUser(0);
				ei.setAvailableExercise(reservation.getExercise());
				Calendar cal = Calendar.getInstance();
				cal.setTime(ei.getStartTime());
				cal.add(Calendar.MINUTE, ei.getAvailableExercise().getDuration());
				ei.setEndTime(cal.getTime());
				ei.getEcsInstance().setShutdownTime(cal.getTime());
				ei.setResultsAvailable(false);
				ei.setLaunchType(ExerciseInstanceLaunchType.ECS);
				ei.setOrganization(user.getDefaultOrganization());
				ExerciseScore score = new ExerciseScore();
				score.setTotal(reservation.getExercise().getScore());
				score.setResult(-1);
				ei.setScore(score);
				ei.setStatus(ExerciseStatus.RUNNING);
				ei.setTechnology(reservation.getExercise().getTechnology());
				ei.setTitle(reservation.getExercise().getTitle());
				ei.setUser(user);
				ei.setGuac(guacUser);
				hpc.addExerciseInstance(ei);
				Integer countdown = 0;
				TokenConnectionMessage token = new TokenConnectionMessage(ei.getIdExerciseInstance(), guacUser.getGateway().getFqdn(),guacUser.getUsername(), guacUser.getLastValidToken(), countdown, false);
				hpc.updateReservation(updatedReservation);
				updatedReservation.setToken(token);
				MessageGenerator.sendReservationMessage(updatedReservation, response);
			}
		}		
	}
}