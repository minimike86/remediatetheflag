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
package com.remediatetheflag.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "UserAuthenticationEvent")
@Table( name = "userAuthenticationEvents" )
public class UserAuthenticationEvent {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idUserAuthenticationEvent")
	private Integer idUserAuthenticationEvent;
	
	@Column(name="sessionIdHash")
	private String sessionIdHash;
	
	@Column(name = "username")
	private String username;
	
	@Column(name="loginDate")
	private Date loginDate;
	
	@Column(name="logoutDate")
	private Date logoutDate;
	
	@Column(name="loginSuccessful")
	private boolean loginSuccessful;
	
	@Column(name="sessionTimeMinutes")
	private Integer sessionTimeMinutes;

	public Integer getIdUserAuthenticationEvent() {
		return idUserAuthenticationEvent;
	}

	public void setIdUserAuthenticationEvent(Integer idUserAuthenticationAttempt) {
		this.idUserAuthenticationEvent = idUserAuthenticationAttempt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public Date getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}

	public void setLoginSuccessful(boolean loginSuccessful) {
		this.loginSuccessful = loginSuccessful;
	}

	public Integer getSessionTimeMinutes() {
		return sessionTimeMinutes;
	}

	public void setSessionTimeMinutes(Integer sessionTimeMinutes) {
		this.sessionTimeMinutes = sessionTimeMinutes;
	}

	public String getSessionIdHash() {
		return sessionIdHash;
	}

	public void setSessionIdHash(String sessionIdHash) {
		this.sessionIdHash = sessionIdHash;
	}
	
}
