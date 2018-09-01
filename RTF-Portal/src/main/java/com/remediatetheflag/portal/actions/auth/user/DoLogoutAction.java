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
package com.remediatetheflag.portal.actions.auth.user;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;

import com.remediatetheflag.portal.actions.IAction;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.model.UserAuthenticationEvent;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;

public class DoLogoutAction extends IAction{

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		
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
		try {
			response.sendRedirect(Constants.INDEX_PAGE);
		} catch (IOException e) {
			logger.error("Failed Logout Redirect: "+e.getMessage());
		}
	}
}
