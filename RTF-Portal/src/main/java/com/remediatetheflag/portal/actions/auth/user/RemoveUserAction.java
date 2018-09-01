package com.remediatetheflag.portal.actions.auth.user;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.model.UserAuthenticationEvent;
import com.remediatetheflag.portal.model.UserStatus;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;

public class RemoveUserAction extends IAction {

	private  HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		long time = System.currentTimeMillis();
		Date date = new Date();
		user.setEmail("removed"+time+"@remediatetheflag.com");
		user.setEmailVerified(false);
		user.setForceChangePassword(true);
		user.setFirstName("Removed");
		user.setLastName("User");
		user.setInstanceLimit(0);
		user.setTeam(null);
		user.setCredits(0);
		user.setDefaultOrganization(null);
		user.setManagedOrganizations(null);
		user.setPersonalDataAnonymisedDateTime(date);
		user.setUsername("removed"+time);
		user.setStatus(UserStatus.REMOVED);

		Boolean result = hpc.updateUserInfo(user);
		if(!result) {
			logger.error("Could not update info for removal of user: "+user.getIdUser() );
			MessageGenerator.sendErrorMessage("UpdateFailed", response);
			return;
		}

		UserAuthenticationEvent attempt = new UserAuthenticationEvent();
		attempt.setLogoutDate(new Date());
		attempt.setUsername(user.getUsername());
		attempt.setSessionIdHash(DigestUtils.sha256Hex(request.getSession().getId()));
		hpc.addLogoutEvent(attempt);

		request.getSession().removeAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		request.getSession().removeAttribute(Constants.ATTRIBUTE_SECURITY_ROLE);	
		request.getSession().invalidate();
		request.getSession(true);

		logger.debug("Logout successful for : "+user.getIdUser());
		MessageGenerator.sendRedirectMessage(Constants.INDEX_PAGE, response);
	}
}