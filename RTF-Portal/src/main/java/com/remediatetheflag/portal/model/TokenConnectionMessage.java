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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenConnectionMessage {

	@SerializedName("exInstanceId")
	@Expose
	private Integer exInstanceId;
	@SerializedName("fqdn")
	@Expose
	private String fqdn;
	@SerializedName("token")
	@Expose
	private String token;
	@SerializedName("user")
	@Expose
	private String user;
	@SerializedName("countdown")
	@Expose
	private Integer countdown;
	@SerializedName("preload")
	@Expose
	private Boolean preload;

	public TokenConnectionMessage(Integer idExerciseInstance, String fqdn, String username, String lastValidToken,
			Integer countdown, Boolean preload) {
		this.exInstanceId=idExerciseInstance;
		this.fqdn=fqdn;
		this.user=username;
		this.token=lastValidToken;
		this.countdown=countdown;
		this.preload=preload;
	}
	public Integer getExInstanceId() {
		return exInstanceId;
	}
	public void setExInstanceId(Integer exInstanceId) {
		this.exInstanceId = exInstanceId;
	}
	public String getFqdn() {
		return fqdn;
	}
	public void setFqdn(String fqdn) {
		this.fqdn = fqdn;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public Integer getCountdown() {
		return countdown;
	}
	public void setCountdown(Integer countdown) {
		this.countdown = countdown;
	}
	public Boolean getPreload() {
		return preload;
	}
	public void setPreload(Boolean preload) {
		this.preload = preload;
	}

}
