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
package com.remediatetheflag.global.actions.auth.management.admin;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.Country;
import com.remediatetheflag.global.model.Organization;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.model.UserStatus;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;

public class UpdateUserAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonObject json = (JsonObject)request.getAttribute("json");

		User sessionUser = (User)request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		JsonElement idUser = json.get(Constants.ACTION_PARAM_ID);
		JsonElement usernameElement = json.get(Constants.ACTION_PARAM_USERNAME);
		JsonElement firstNameElement = json.get(Constants.ACTION_PARAM_FIRST_NAME);
		JsonElement lastNameElement = json.get(Constants.ACTION_PARAM_LAST_NAME);
		JsonElement emailElement = json.get(Constants.ACTION_PARAM_EMAIL);
		JsonElement countryElement = json.get(Constants.ACTION_PARAM_COUNTRY);
		JsonElement orgElement = json.get(Constants.ACTION_PARAM_ORG_ID);

		JsonElement roleElement = json.get(Constants.ACTION_PARAM_ROLE_ID);
		JsonElement concurrentExerciseLimitElement = json.get(Constants.ACTION_PARAM_CONCURRENT_EXERCISE_LIMIT);
		JsonElement creditsElement = json.get(Constants.ACTION_PARAM_CREDITS);

		String username = usernameElement.getAsString();
		String firstName = firstNameElement.getAsString();
		String lastName = lastNameElement.getAsString();
		String email = emailElement.getAsString();
		String country = countryElement.getAsString();
		Integer orgId = orgElement.getAsInt();
		Integer credits = creditsElement.getAsInt();

		Integer roleId = roleElement.getAsInt();
		Integer concurrentExercisesLimit = concurrentExerciseLimitElement.getAsInt();
		Boolean emailVerified = true;
		Boolean forcePasswordChange = false;

		Integer usrRole = -1;
		switch (roleId){
		case -1: 
			usrRole = Constants.ROLE_RTF_ADMIN;
			break;
		case 0: 
			usrRole = Constants.ROLE_ADMIN;
			break;
		case 1: 
			usrRole = Constants.ROLE_REVIEWER;
			break;
		case 3: 
			usrRole = Constants.ROLE_TEAM_MANAGER;
			break;
		case 4: 
			usrRole = Constants.ROLE_STATS;
			break;
		case 7: 
			usrRole = Constants.ROLE_USER;
			break;

		default: {
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		}
		if (usrRole.intValue() < sessionUser.getRole().intValue()){
			MessageGenerator.sendErrorMessage("NotAuthorized", response);
			return;
		}
		Organization o = hpc.getOrganizationById(orgId);
		if(null==o){
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		boolean isManager = false;
		for(Organization organization : sessionUser.getManagedOrganizations()){
			if(o.getId().equals(organization.getId())){
				isManager = true;
				break;
			}
		}
		if(!isManager){
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		@SuppressWarnings("serial")
		List<User> organizationUsers = hpc.getManagementAllUsers(new HashSet<Organization>(){{add(o);}});
		if (organizationUsers.size()>=o.getMaxUsers()) {
			MessageGenerator.sendErrorMessage("MaxUserLimit", response);
			return;
		}
		User dbUser = hpc.getUserFromUserId(idUser.getAsInt());
		if(!dbUser.getUsername().equals(username)) {
			User existingUser = hpc.getUserFromUsername(username);
			if (existingUser != null) {
				MessageGenerator.sendErrorMessage("UserExists", response);
				return;
			}
		}
		Country c = hpc.getCountryFromCode(country);
		if(null==c){
			MessageGenerator.sendErrorMessage("NotFound", response);
			return;
		}
		dbUser.setEmail(email);
		dbUser.setLastName(lastName);
		dbUser.setUsername(username);
		dbUser.setFirstName(firstName);
		dbUser.setRole(usrRole);
		dbUser.setStatus(UserStatus.ACTIVE);
		dbUser.setCountry(c);
		dbUser.setEmailVerified(emailVerified);
		dbUser.setForceChangePassword(forcePasswordChange);
		dbUser.setInstanceLimit(concurrentExercisesLimit);
		dbUser.setCredits(credits);
		if(null!=dbUser.getTeam()) {
			if(!dbUser.getTeam().getOrganization().getId().equals(o.getId())) {
				dbUser.setTeam(null);
			}
		}
		dbUser.setDefaultOrganization(o);
		dbUser.setPersonalDataUpdateDateTime(new Date());
		Boolean alreadyManaging = false; 
		if (dbUser.getRole().intValue() < 7) {
			for(Organization uManagedOrg : dbUser.getManagedOrganizations()) {
				if(uManagedOrg.getId().equals(o.getId())) {
					alreadyManaging = true;
					break;
				}
			}
			if(!alreadyManaging)
				dbUser.getManagedOrganizations().add(o);
		} else {
			dbUser.setManagedOrganizations(null);
		}
		Boolean result = hpc.updateUserInfo(dbUser);
		if(result){
			MessageGenerator.sendSuccessMessage(response);
		}
		else{
			logger.error("Update failed at DB-end for email: "+email);
			MessageGenerator.sendErrorMessage("UpdateFailed", response);
		}
	}
}