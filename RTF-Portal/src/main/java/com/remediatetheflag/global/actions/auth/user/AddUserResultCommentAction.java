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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.ExerciseResult;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.model.UserResultComment;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;
import com.remediatetheflag.global.utils.NotificationsHelper;

public class AddUserResultCommentAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private NotificationsHelper notificationsHelper = new NotificationsHelper();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		Integer exerciseInstanceId = json.get(Constants.ACTION_PARAM_ID).getAsInt();
		String selfCheckName = json.get(Constants.ACTION_PARAM_NAME).getAsString();
		String text = json.get(Constants.ACTION_PARAM_TEXT).getAsString();
		
		ExerciseInstance instance = hpc.getCompletedExerciseInstanceForUser(user.getIdUser(),exerciseInstanceId);
		if(null!=instance){
			for(ExerciseResult res : instance.getResults()) {
				if(res.getName().equals(selfCheckName)) {
					UserResultComment e = new UserResultComment();
					e.setDate(new Date());
					e.setFromAdmin(false);
					e.setFromUserId(user.getIdUser());
					e.setText(text);
					res.getUserReportedComplaints().add(e);
					res.setUserReportedIssueAddressed(false);
					instance.setIssuesReported(true);
					instance.setIssuesAddressed(false);
					break;
				}
			}
			hpc.updateExerciseInstance(instance);
			notificationsHelper.newScoringComment(user, instance);
			MessageGenerator.sendSuccessMessage(response);
			return;
		}
		MessageGenerator.sendErrorMessage("NOT_FOUND", response);
	}
}