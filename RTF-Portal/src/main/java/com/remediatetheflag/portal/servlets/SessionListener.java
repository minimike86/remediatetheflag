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
package com.remediatetheflag.portal.servlets;

import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.model.UserAuthenticationEvent;
import com.remediatetheflag.portal.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.portal.utils.Constants;

public class SessionListener implements HttpSessionListener {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private static Logger logger = LoggerFactory.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent sessionEvent) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent sessionEvent) {
		User user = (User) sessionEvent.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		if(null!=user){
			logger.debug("Session Listener: session destroyed for user: "+user.getUsername());
			UserAuthenticationEvent attempt = new UserAuthenticationEvent();
			attempt.setLogoutDate(new Date());
			attempt.setUsername(user.getUsername());
			attempt.setSessionIdHash(DigestUtils.sha256Hex(sessionEvent.getSession().getId()));
			hpc.addLogoutEvent(attempt);
		}      
	}
}
