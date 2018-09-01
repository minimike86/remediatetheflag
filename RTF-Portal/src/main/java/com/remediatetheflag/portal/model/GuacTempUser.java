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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table( name="guacTempUsers")
@Entity( name="GuacTempUser" )
public class GuacTempUser {

	@Id
	@Column(name = "idGuacTempUser")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idGuacTempUser;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gatewayId")
	private RTFGateway gateway;

	@Column(name="lastValidToken")
	private String lastValidToken;

	@Column(name="connectionId")
	private String connectionId;

	public Integer getIdGuacTempUser() {
		return idGuacTempUser;
	}
	public void setIdGuacTempUser(Integer idGuacTempUser) {
		this.idGuacTempUser = idGuacTempUser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public RTFGateway getGateway() {
		return gateway;
	}
	public void setGateway(RTFGateway gateway) {
		this.gateway = gateway;
	}
	public String getLastValidToken() {
		return lastValidToken;
	}
	public void setLastValidToken(String lastValidToken) {
		this.lastValidToken = lastValidToken;
	}
	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}
	public String getConnectionId() {
		return connectionId;
	}

}