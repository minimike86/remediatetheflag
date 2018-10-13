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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(name = "RTFInstanceReservation")
@Table( name = "rtfInstanceReservations" )
public class RTFInstanceReservation {

	@Id
	@Expose
	@SerializedName("idReservation")
	@Column(name = "idReservation")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@SerializedName("fulfilled")
	@Expose
	@Column(name="fulfilled")
	private Boolean fulfilled;

	@SerializedName("error")
	@Expose
	@Column(name="error")
	private Boolean error;

	@SerializedName("waitSeconds")
	@Expose
	@Column(name="waitSeconds")
	private Integer waitSeconds;

	@Column(name="type")
	private ExerciseInstanceLaunchType type;

	@Column(name="tmpPassword")
	private String tmpPassword;

	@Transient
	@Expose
	@SerializedName("connection")
	private TokenConnectionMessage token;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	private User user;

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name = "organizationId" )
	private Organization organization;

	@ManyToOne(fetch = FetchType.EAGER)
	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "exerciseId")
	private AvailableExercise exercise;
	
	@SerializedName("challengeId")
	@Expose
	@Column(name="challengeId")
	private Integer challengeId;

	@org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ecs")
	private RTFECSContainerTask ecs;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getFulfilled() {
		return fulfilled;
	}

	public void setFulfilled(Boolean fulfilled) {
		this.fulfilled = fulfilled;
	}

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public Integer getWaitSeconds() {
		return waitSeconds;
	}

	public void setWaitSeconds(Integer waitSeconds) {
		this.waitSeconds = waitSeconds;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public RTFECSContainerTask getEcs() {
		return ecs;
	}

	public void setEcs(RTFECSContainerTask ecs) {
		this.ecs = ecs;
	}

	public String getTmpPassword() {
		return tmpPassword;
	}

	public void setTmpPassword(String tmpPassword) {
		this.tmpPassword = tmpPassword;
	}

	public AvailableExercise getExercise() {
		return exercise;
	}

	public void setExercise(AvailableExercise exercise) {
		this.exercise = exercise;
	}

	public ExerciseInstanceLaunchType getType() {
		return type;
	}

	public void setType(ExerciseInstanceLaunchType type) {
		this.type = type;
	}

	public TokenConnectionMessage getToken() {
		return token;
	}

	public void setToken(TokenConnectionMessage token) {
		this.token = token;
	}

	public Integer getChallengeId() {
		return challengeId;
	}

	public void setChallengeId(Integer challenge) {
		this.challengeId = challenge;
	}
}
