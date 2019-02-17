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

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.AchievedTrophy;
import com.remediatetheflag.global.model.Challenge;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.ExerciseInstanceType;
import com.remediatetheflag.global.model.ExerciseResult;
import com.remediatetheflag.global.model.ExerciseStatus;
import com.remediatetheflag.global.model.Flag;
import com.remediatetheflag.global.model.FlagQuestion;
import com.remediatetheflag.global.model.SubmittedReview;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;
import com.remediatetheflag.global.utils.NotificationsHelper;

public class PostReviewAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private NotificationsHelper notificationsHelper = new NotificationsHelper();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_ATTRIBUTE_JSON);
		JsonObject jsonObj = json.getAsJsonObject(Constants.JSON_ATTRIBUTE_OBJ);

		Gson gson = new Gson();  
		SubmittedReview review = gson.fromJson(jsonObj, SubmittedReview.class);  

		if(review.getAwardTrophy()==null || review.getId()==null || review.getNewIssuesIntroduced()==null || review.getTotalScore()==null || review.getReview()==null || review.getReview().isEmpty()){
			logger.error("Invalid data supplied for review from user "+user.getIdUser());
			MessageGenerator.sendErrorMessage("InvalidData", response);
			return;
		}	

		Integer exerciseInstanceId = review.getId();
		Boolean awardTrophy = review.getAwardTrophy();
		Boolean newIssuesIntroduced = review.getNewIssuesIntroduced();
		String newIssuesIntroducedText = review.getNewIssuesIntroducedText();

		ExerciseInstance reviewedInstance = hpc.getManagementCompletedExerciseInstance(exerciseInstanceId,user.getManagedOrganizations());
		if(null==reviewedInstance){
			logger.error("Exercise "+exerciseInstanceId+" not found for user "+user.getIdUser());
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		if(user.getRole().equals(Constants.ROLE_TEAM_MANAGER)){
			List<User> users = hpc.getUsersInTeamManagedBy(user);
			if(!users.contains(reviewedInstance.getUser())){
				MessageGenerator.sendErrorMessage("NotFound", response);
				return;
			}
		}
		if(reviewedInstance.getStatus().equals(ExerciseStatus.REVIEWED) || reviewedInstance.getStatus().equals(ExerciseStatus.REVIEWED_MODIFIED) || reviewedInstance.getStatus().equals(ExerciseStatus.CANCELLED)){
			logger.error("Exercise "+exerciseInstanceId+" already reviewer for user "+user.getIdUser());
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		Integer totalScore = 0;
		if(reviewedInstance.getResults().isEmpty()){
			for(ExerciseResult rev : review.getReview()){
				ExerciseResult res = new ExerciseResult();
				res.setName(rev.getName());
				res.setComment(rev.getComment());
				res.setVerified(true);
				res.setStatus(rev.getStatus());
				res.setLastChange(new Date());
				res.setScore(rev.getScore());
				totalScore+=rev.getScore();
				res.setAutomated(false);
				res.setCategory(getCategoryForResult(reviewedInstance,rev.getName()));
				reviewedInstance.getResults().add(res);
			}
		}
		else {
			for(ExerciseResult orig : reviewedInstance.getResults()){
				for(ExerciseResult rev : review.getReview()){
					if(rev.getName().equals(orig.getName())){
						orig.setComment(rev.getComment());
						orig.setVerified(true);
						orig.setLastChange(new Date());
						orig.setStatus(rev.getStatus());
						orig.setScore(rev.getScore());
						totalScore+=rev.getScore();
						orig.setAutomated(false);
					}
				}
			}
		}


		//TODO compute total score from individual questions and remove from parameters
		reviewedInstance.getScore().setResult(totalScore);
		reviewedInstance.setResultsAvailable(true);
		reviewedInstance.setStatus(ExerciseStatus.REVIEWED);
		reviewedInstance.setTrophyAwarded(review.getAwardTrophy());
		reviewedInstance.setNewIssuesIntroduced(newIssuesIntroduced);
		if(newIssuesIntroduced){
			reviewedInstance.setNewIssuesIntroducedText(newIssuesIntroducedText);
		}
		reviewedInstance.setReviewer(user.getIdUser());
		reviewedInstance.setReviewedDate(new Date());
		hpc.updateExerciseInstance(reviewedInstance);
		if(reviewedInstance.getType().equals(ExerciseInstanceType.CHALLENGE)) {
			Challenge c = hpc.getChallengeFromId(reviewedInstance.getChallengeId());
			c.setLastActivity(new Date());
			hpc.updateChallenge(c);
		}
		notificationsHelper.addCompletedReviewNotification(reviewedInstance.getUser(), reviewedInstance);

		List<ExerciseInstance> userRunExercises = hpc.getCompletedExerciseInstancesForUser(reviewedInstance.getUser().getIdUser());
		boolean alreadyRun = false;
		for(ExerciseInstance runEx : userRunExercises) {
			if(runEx.getAvailableExercise().getId().equals(reviewedInstance.getAvailableExercise().getId()) || runEx.getAvailableExercise().getUuid().equals(reviewedInstance.getAvailableExercise().getUuid())) {
				alreadyRun = true;
				break;
			}
		}
		if(!alreadyRun) {
			User dbUser = hpc.getUserFromUserId(reviewedInstance.getUser().getIdUser());
			dbUser.setExercisesRun(dbUser.getExercisesRun() + 1);
			dbUser.setScore(dbUser.getScore() + reviewedInstance.getScore().getResult());
			hpc.updateUserInfo(dbUser);
			
			List<AchievedTrophy> userAchievedTrophy = hpc.getAllAchievedTropiesForUser(user.getIdUser());
			boolean alreadyAchieved = false;
			for(AchievedTrophy trophy : userAchievedTrophy){
				if(trophy.getTrophy().getName().equals(reviewedInstance.getAvailableExercise().getTrophy().getName())){
					alreadyAchieved = true;
					break;
				}
			}
			if(awardTrophy && !alreadyAchieved){
				AchievedTrophy t = new AchievedTrophy();
				t.setDate(new Date());
				t.setUser(reviewedInstance.getUser());
				t.setTrophy(reviewedInstance.getAvailableExercise().getTrophy());
				hpc.managementAddAchievedTrophy(t);
			}
		}
		
		logger.debug("Review submitted for exercise "+exerciseInstanceId+" from reviewer "+user.getIdUser());
		MessageGenerator.sendSuccessMessage(response);

	}

	private String getCategoryForResult(ExerciseInstance instance, String name) {
		for(Flag flag : instance.getAvailableExercise().getFlags()){
			for(FlagQuestion fq : flag.getFlagQuestionList()) {
				if(fq.getSelfCheckAvailable() && fq.getSelfCheckName().equals(name)) {
					return flag.getCategory();
				}
			}
		}
		return null;
	}

}