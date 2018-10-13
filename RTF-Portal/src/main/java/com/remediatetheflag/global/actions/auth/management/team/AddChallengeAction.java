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
import java.util.LinkedList;
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
import com.remediatetheflag.global.model.Organization;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;

public class AddChallengeAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement nameElement = json.get(Constants.ACTION_PARAM_NAME);
		JsonElement detailsElement = json.get(Constants.ACTION_PARAM_DETAILS);
		JsonElement startElement = json.get(Constants.ACTION_PARAM_START_DATE);
		JsonElement endElement = json.get(Constants.ACTION_PARAM_END_DATE);
		JsonElement idOrgElement = json.get(Constants.ACTION_PARAM_ID_ORG);
		JsonElement scoringElement = json.get(Constants.ACTION_PARAM_SCORING_MODE);
		JsonElement firstPlaceElement = json.get(Constants.ACTION_PARAM_SCORING_FIRST_PLACE);
		JsonElement secondPlaceElement = json.get(Constants.ACTION_PARAM_SCORING_SECOND_PLACE);
		JsonElement thirdPlaceElement = json.get(Constants.ACTION_PARAM_SCORING_THIRD_PLACE);

		Type listIntegerType = new TypeToken<List<Integer>>() {}.getType();
		Type listStringType = new TypeToken<List<String>>() {}.getType();

		Gson gson = new Gson();
		Set<User> challengeUsers = new HashSet<User>();
		Set<AvailableExercise> challengeExercises = new HashSet<AvailableExercise>();

		Challenge existingChallenge = hpc.getChallengeFromName(nameElement.getAsString());
		if (existingChallenge != null) {
			MessageGenerator.sendErrorMessage("NameNotAvailable", response);
			return;
		} 

		Organization org = hpc.getOrganizationById(idOrgElement.getAsInt());
		if(!user.getManagedOrganizations().contains(org)) {
			MessageGenerator.sendErrorMessage("NotAuthorized", response);
			return;
		}
		try {
			List<String> users = gson.fromJson(json.get(Constants.ACTION_PARAM_USERID_LIST), listStringType);
			for(String username : users) {
				User tmpUser = hpc.getUserFromUsername(username);
				if(null!=tmpUser && tmpUser.getDefaultOrganization().equals(org))
					challengeUsers.add(tmpUser);
			}
			List<Integer> exercises = gson.fromJson(json.get(Constants.ACTION_PARAM_EXERCISE_LIST), listIntegerType);
			for(Integer idExercise : exercises) {
				AvailableExercise tmpExercise = hpc.getAvailableExercise(idExercise,org);
				if(null!=tmpExercise)
					challengeExercises.add(tmpExercise);
			}
		}catch(Exception e) {
			MessageGenerator.sendErrorMessage("ListsParseError", response);
			return;
		}

		Challenge challenge = new Challenge();
		challenge.setCreatedBy(user.getIdUser());
		challenge.setCompletion(0.0);
		challenge.setRunExercises(new LinkedList<ExerciseInstance>());
		challenge.setDetails(detailsElement.getAsString());
		
		challenge.setFirstInFlag(firstPlaceElement.getAsInt());
		challenge.setSecondInFlag(secondPlaceElement.getAsInt());
		challenge.setThirdInFlag(thirdPlaceElement.getAsInt());
		
		SimpleDateFormat parser=new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz");
		challenge.setEndDate(parser.parse(endElement.getAsString()));
		challenge.setStartDate(parser.parse(startElement.getAsString()));
		
		if(challenge.getEndDate().before(challenge.getStartDate())){
			MessageGenerator.sendErrorMessage("DatesError", response);
			return;
		}
		
		challenge.setExercises(challengeExercises);
		challenge.setLastActivity(new Date());
		challenge.setName(nameElement.getAsString());
		challenge.setOrganization(org);
		try {
			ExerciseScoringMode scoring = ExerciseScoringMode.getStatusFromStatusCode(scoringElement.getAsInt());
			challenge.setScoring(scoring);
		}catch(Exception e) {
			challenge.setScoring(ExerciseScoringMode.MANUAL_REVIEW);
		}
		challenge.setUsers(challengeUsers);

		if(challenge.getStartDate().before(new Date())) 
			challenge.setStatus(ChallengeStatus.IN_PROGRESS);
		else
			challenge.setStatus(ChallengeStatus.NOT_STARTED);

		Integer id = hpc.addChallenge(challenge);
		if(null!=id)
			MessageGenerator.sendSuccessMessage(response);
		else
			MessageGenerator.sendErrorMessage("ChallengeNotSaved", response);

	}
}