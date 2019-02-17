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
package com.remediatetheflag.global.actions.auth.management.team;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.AvailableExercise;
import com.remediatetheflag.global.model.Challenge;
import com.remediatetheflag.global.model.ExerciseScoringMode;
import com.remediatetheflag.global.model.ChallengeStatus;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;

public class UpdateChallengeAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		
		JsonElement idElement = json.get(Constants.ACTION_PARAM_ID);
		JsonElement nameElement = json.get(Constants.ACTION_PARAM_NAME);
		JsonElement detailsElement = json.get(Constants.ACTION_PARAM_DETAILS);
		JsonElement startElement = json.get(Constants.ACTION_PARAM_START_DATE);
		JsonElement endElement = json.get(Constants.ACTION_PARAM_END_DATE);
		JsonElement scoringElement = json.get(Constants.ACTION_PARAM_SCORING_MODE);
		JsonElement firstPlaceElement = json.get(Constants.ACTION_PARAM_SCORING_FIRST_PLACE);
		JsonElement secondPlaceElement = json.get(Constants.ACTION_PARAM_SCORING_SECOND_PLACE);
		JsonElement thirdPlaceElement = json.get(Constants.ACTION_PARAM_SCORING_THIRD_PLACE);

		
		Challenge c = hpc.getChallengeWithDetails(idElement.getAsInt(), user.getManagedOrganizations());
		if(null==c) {
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		if(!nameElement.getAsString().equals(c.getName())) {
			Challenge existingChallenge = hpc.getChallengeFromName(nameElement.getAsString());
			if (existingChallenge != null) {
				MessageGenerator.sendErrorMessage("NameNotAvailable", response);
				return;
			} 
		}
		Type listIntegerType = new TypeToken<List<Integer>>() {}.getType();
		Type listStringType = new TypeToken<List<String>>() {}.getType();

		Gson gson = new Gson();
		Set<User> challengeUsers = new HashSet<User>();
		Set<AvailableExercise> challengeExercises = new HashSet<AvailableExercise>();

		try {
			List<String> users = gson.fromJson(json.get(Constants.ACTION_PARAM_USERID_LIST), listStringType);
			for(String username : users) {
				User tmpUser = hpc.getUserFromUsername(username);
				if(null!=tmpUser && tmpUser.getDefaultOrganization().equals(c.getOrganization()))
					challengeUsers.add(tmpUser);
			}
			List<Integer> exercises = gson.fromJson(json.get(Constants.ACTION_PARAM_EXERCISE_LIST), listIntegerType);
			for(Integer idExercise : exercises) {
				AvailableExercise tmpExercise = hpc.getAvailableExerciseDetails(idExercise,c.getOrganization());
				if(null!=tmpExercise)
					challengeExercises.add(tmpExercise);
			}
		}catch(Exception e) {
			MessageGenerator.sendErrorMessage("ListsParseError", response);
			return;
		}

		c.setDetails(detailsElement.getAsString());

		SimpleDateFormat parser=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz");
		c.setEndDate(parser.parse(endElement.getAsString()));
		c.setStartDate(parser.parse(startElement.getAsString()));

		
		c.setFirstInFlag(firstPlaceElement.getAsInt());
		c.setSecondInFlag(secondPlaceElement.getAsInt());
		c.setThirdInFlag(thirdPlaceElement.getAsInt());
		
		if(c.getEndDate().before(c.getStartDate())){
			MessageGenerator.sendErrorMessage("DatesError", response);
			return;
		}

		c.setExercises(challengeExercises);
		c.setLastActivity(new Date());
		c.setName(nameElement.getAsString());
		try {
			ExerciseScoringMode scoring = ExerciseScoringMode.getStatusFromStatusCode(scoringElement.getAsInt());
			c.setScoring(scoring);
		}catch(Exception e) {
			c.setScoring(ExerciseScoringMode.MANUAL_REVIEW);
		}
		c.setUsers(challengeUsers);

		if(c.getStartDate().before(new Date())) 
			c.setStatus(ChallengeStatus.IN_PROGRESS);
		else
			c.setStatus(ChallengeStatus.NOT_STARTED);
		
		Double completion = 0.0;
		
		Integer nrFlags = 0;
		for(AvailableExercise e : c.getExercises()) {
			nrFlags += e.getFlags().size();
		}
		Integer runFlags = 0;
		for(ExerciseInstance i : c.getRunExercises()) {
			runFlags += i.getResults().size();
		}
		
		if(null!=c.getExercises() && !c.getExercises().isEmpty() && null!=c.getUsers() && !c.getUsers().isEmpty() && null!=c.getRunExercises() && !c.getRunExercises().isEmpty())
			completion = (double) ( (double) runFlags / ((double) nrFlags * (double) c.getUsers().size())) * 100.0;
		c.setCompletion(completion);

		Boolean result = hpc.updateChallenge(c);
		if(result)
			MessageGenerator.sendSuccessMessage(response);
		else
			MessageGenerator.sendErrorMessage("ChallengeNotUpdate", response);

	}

}
