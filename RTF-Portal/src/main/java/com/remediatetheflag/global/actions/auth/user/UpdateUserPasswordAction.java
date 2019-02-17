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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;
import com.remediatetheflag.global.utils.PasswordComplexityUtil;

public class UpdateUserPasswordAction extends IAction{

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement pwdElement = json.get(Constants.ACTION_PARAM_OLDPASSWORD);
		String oldPwd = pwdElement.getAsString();
		
		JsonElement newPwdElement = json.get(Constants.ACTION_PARAM_NEWPASSWORD);
		String newPwd = newPwdElement.getAsString();
	
		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		
		User user = hpc.getUser(sessionUser.getUsername(), oldPwd);
		if(null!=user && sessionUser.getIdUser() == user.getIdUser()){
			if(PasswordComplexityUtil.isPasswordComplex(newPwd)){
				hpc.updateUserPassword(user.getIdUser(),newPwd);
				sessionUser.setForceChangePassword(false);
				request.getSession().setAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT, sessionUser);
				user.setForceChangePassword(false);
				hpc.updateUserInfo(user);
				MessageGenerator.sendSuccessMessage(response);
			}
			else{
				logger.error("Password complexity not met for user: "+sessionUser.getIdUser());
				MessageGenerator.sendErrorMessage("PasswordComplexity", response);
			}
		}
		else{
			logger.error("Invalid old password for user: "+sessionUser.getIdUser());
			MessageGenerator.sendErrorMessage("InvalidUserPassword", response);
		}
	}
}
