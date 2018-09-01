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
package com.remediatetheflag.portal.auth.management;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.AchievedTrophy;
import com.remediatetheflag.portal.model.ExerciseInstance;
import com.remediatetheflag.portal.model.ExerciseResult;
import com.remediatetheflag.portal.model.ExerciseStatus;
import com.remediatetheflag.portal.model.SubmittedReview;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;
import com.remediatetheflag.portal.utils.NotificationsHelper;

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
		if(reviewedInstance.getStatus().equals(ExerciseStatus.REVIEWED) || reviewedInstance.getStatus().equals(ExerciseStatus.CANCELLED)){
			logger.error("Exercise "+exerciseInstanceId+" already reviewer for user "+user.getIdUser());
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		for(ExerciseResult orig : reviewedInstance.getResults()){
			for(ExerciseResult rev : review.getReview()){
				if(rev.getName().equals(orig.getName())){
					orig.setComment(rev.getComment());
					orig.setVerified(rev.getVerified());
					orig.setStatus(rev.getStatus());
					orig.setScore(rev.getScore());
				}
			}
		}
		if(reviewedInstance.getResults().isEmpty()){
			reviewedInstance.getResults().addAll(review.getReview());
		}

		reviewedInstance.getScore().setResult(review.getTotalScore());
		reviewedInstance.setResultsAvailable(true);
		reviewedInstance.setStatus(ExerciseStatus.REVIEWED);
		reviewedInstance.setTrophyAwarded(review.getAwardTrophy());
		reviewedInstance.setNewIssuesIntroduced(newIssuesIntroduced);
		if(newIssuesIntroduced){
			reviewedInstance.setNewIssuesIntroducedText(newIssuesIntroducedText);
		}
		reviewedInstance.setReviewer(user);
		reviewedInstance.setReviewedDate(new Date());
		hpc.updateExerciseInstance(reviewedInstance);
		notificationsHelper.addCompletedReviewNotification(reviewedInstance.getUser(), reviewedInstance);
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
		User dbUser = hpc.getUserFromUserId(reviewedInstance.getUser().getIdUser());
		dbUser.setExercisesRun(dbUser.getExercisesRun() + 1);
		dbUser.setScore(dbUser.getScore() + reviewedInstance.getScore().getResult());
		hpc.updateUserInfo(dbUser);

		logger.debug("Review submitted for exercise "+exerciseInstanceId+" from reviewer "+user.getIdUser());
		MessageGenerator.sendSuccessMessage(response);

	}

}