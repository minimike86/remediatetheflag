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
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.remediatetheflag.portal.messages.annotations.ChallengeDetails;
import com.remediatetheflag.portal.messages.annotations.LazilySerialized;

@Table(name="challenges")
@Entity(name="Challenge")
public class Challenge {

	@SerializedName("id")
	@Id
	@Expose
	@Column(name = "idChallenge")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idChallenge;
	
	@SerializedName("name")
	@Expose
	@Column(name = "name")
	private String name;
	
	@SerializedName("createdBy")
	@LazilySerialized
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdBy")
	private User createdBy;
	
	@SerializedName("completion")
	@Expose
	@Column(name = "completion")
	private Double completion;
	
	@SerializedName("lastActivity")
	@Expose
	@Column(name = "lastActivity")
	private Date lastActivity;

	@Enumerated(EnumType.STRING)
	@Expose
	@Column(name="status")
	private ChallengeStatus status;
	
	@SerializedName("users")
	@Expose
	@ChallengeDetails
	@OneToMany(fetch=FetchType.EAGER)
	private Set<User> users;

	@SerializedName("exercises")
	@Expose
	@ChallengeDetails
	@LazilySerialized
	@OneToMany(fetch = FetchType.LAZY)
	private List<AvailableExercise> exercises;

	@SerializedName("runExercises")
	@Expose
	@ChallengeDetails
	@LazilySerialized
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private List<ExerciseInstance> runExercises;

	@SerializedName("organization")
	@Expose
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name = "organizationId" )
	private Organization organization;
	
	@Column(name="startDate")
	@Expose
	private Date startDate;

	@Column(name="endDate")
	@Expose
	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<ExerciseInstance> getRunExercises() {
		return runExercises;
	}
	public void setRunExercises(List<ExerciseInstance> runExercises) {
		this.runExercises = runExercises;
	}
	public List<AvailableExercise> getExercises() {
		return exercises;
	}
	public void setExercises(List<AvailableExercise> exercises) {
		this.exercises = exercises;
	}
	public Integer getIdChallenge() {
		return idChallenge;
	}
	public void setIdChallenge(Integer idChallenge) {
		this.idChallenge = idChallenge;
	}

	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getCompletion() {
		return completion;
	}
	public void setCompletion(Double completion) {
		this.completion = completion;
	}
	public ChallengeStatus getStatus() {
		return status;
	}
	public void setStatus(ChallengeStatus status) {
		this.status = status;
	}
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Date getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(Date lastActivity) {
		this.lastActivity = lastActivity;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
}
