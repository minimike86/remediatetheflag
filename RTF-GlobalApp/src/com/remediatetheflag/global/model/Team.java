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
package com.remediatetheflag.global.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.remediatetheflag.global.messages.annotations.LeaderboardUser;
import com.remediatetheflag.global.messages.annotations.TeamManager;

@Entity( name = "Team" )
@Table( name = "teams" )
public class Team {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="idTeam")
	@SerializedName("id")
	@TeamManager
    @LeaderboardUser
	@Expose
	private Integer idTeam;

	@SerializedName("name")
	@Expose
    @LeaderboardUser
	@Column(name="name", unique = true)
	private String name;
	
	@SerializedName("organization")
	@Expose
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name = "organizationId" )
	private Organization organization;
	
	@TeamManager
	@ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
	private Set<User> managers = new HashSet<User>();
	
	public Integer getIdTeam() {
		return idTeam;
	}
	public void setIdTeam(Integer idTeam) {
		this.idTeam = idTeam;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<User> getManagers() {
		return managers;
	}
	public void setManagers(Set<User> managers) {
		this.managers = managers;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}
