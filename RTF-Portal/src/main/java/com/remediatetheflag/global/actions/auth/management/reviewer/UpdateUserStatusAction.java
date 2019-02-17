package com.remediatetheflag.global.actions.auth.management.reviewer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.model.UserStatus;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;

public class UpdateUserStatusAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement usernameElement = json.get(Constants.ACTION_PARAM_USERNAME);
		String username = usernameElement.getAsString();
		JsonElement statusElement = json.get(Constants.ACTION_PARAM_STATUS);
		String status = statusElement.getAsString();
		UserStatus newStatus = UserStatus.getStatusFromName(status);
	
		User user = hpc.getUserFromUsername(username,sessionUser.getManagedOrganizations());
		if(null!=user && null!=newStatus){
			user.setStatus(newStatus);
			hpc.updateUserInfo(user);
			hpc.setFailedLoginAttemptsForUser(username,0);
			MessageGenerator.sendSuccessMessage(response);
		}
		else{
			MessageGenerator.sendErrorMessage("NotFound",response);
		}

	}

}
