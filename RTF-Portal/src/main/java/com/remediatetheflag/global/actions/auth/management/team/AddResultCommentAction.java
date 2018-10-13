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

import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.ExerciseResult;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.model.UserResultComment;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;

public class AddResultCommentAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		Integer exerciseInstanceId = json.get(Constants.ACTION_PARAM_ID).getAsInt();
		String selfCheckName = json.get(Constants.ACTION_PARAM_NAME).getAsString();
		String text = json.get(Constants.ACTION_PARAM_TEXT).getAsString();
		
		ExerciseInstance instance = hpc.getManagementExerciseInstance(exerciseInstanceId,user.getManagedOrganizations());
		if(null!=instance){
			if(user.getRole().equals(Constants.ROLE_TEAM_MANAGER)){
				List<User> users = hpc.getUsersInTeamManagedBy(user);
				if(!users.contains(instance.getUser())){
					MessageGenerator.sendErrorMessage("NotFound", response);
					return;
				}
			}
			for(ExerciseResult res : instance.getResults()) {
				if(res.getName().equals(selfCheckName)) {
					UserResultComment e = new UserResultComment();
					e.setDate(new Date());
					e.setFromAdmin(true);
					e.setFromUserId(user.getIdUser());
					e.setText(text);
					res.getUserReportedComplaints().add(e);
					res.setUserReportedIssueAddressed(true);
					instance.setIssuesReported(true);
					instance.setIssuesAddressed(true);
					break;
				}
			}
			hpc.updateExerciseInstance(instance);
			MessageGenerator.sendSuccessMessage(response);
			return;
		}
		MessageGenerator.sendErrorMessage("NOT_FOUND", response);
	}
}