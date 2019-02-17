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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.remediatetheflag.global.messages.annotations.ChallengeDetails;
import com.remediatetheflag.global.messages.annotations.LazilySerialized;

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
	@Column(name = "createdBy")
	private Integer createdBy;
	
	@SerializedName("completion")
	@Expose
	@Column(name = "completion")
	private Double completion;
	
	@SerializedName("lastActivity")
	@Expose
	@Column(name = "lastActivity")
	private Date lastActivity;

	@Enumerated(EnumType.ORDINAL)
	@Expose
	@Column(name="status")
	private ChallengeStatus status;
	
	@SerializedName("users")
	@Expose
	@ChallengeDetails
	@ManyToMany(fetch=FetchType.EAGER)
	private Set<User> users;
	
	@SerializedName("details")
	@Expose
	@Lob
	private String details;
	
	@SerializedName("scoring")
	@Expose
	@Enumerated(EnumType.ORDINAL)
	private ExerciseScoringMode scoring;

	@SerializedName("exercises")
	@Expose
	@ChallengeDetails
	@LazilySerialized
	@ManyToMany(fetch = FetchType.LAZY)
	private Set<AvailableExercise> exercises;

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
	
	@Column(name="firstInFlag",columnDefinition="int default 0")
	@Expose
	private Integer firstInFlag;
	
	@Column(name="secondInFlag",columnDefinition="int default 0")
	@Expose
	private Integer secondInFlag;
	
	@Column(name="thirdInFlag",columnDefinition="int default 0")
	@Expose
	private Integer thirdInFlag;

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
	public Set<AvailableExercise> getExercises() {
		return exercises;
	}
	public void setExercises(Set<AvailableExercise> exercises) {
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
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public ExerciseScoringMode getScoring() {
		return scoring;
	}
	public void setScoring(ExerciseScoringMode scoring) {
		this.scoring = scoring;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idChallenge == null) ? 0 : idChallenge.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Challenge other = (Challenge) obj;
		if (idChallenge == null) {
			if (other.idChallenge != null)
				return false;
		} else if (!idChallenge.equals(other.idChallenge))
			return false;
		return true;
	}
	public Integer getFirstInFlag() {
		return firstInFlag;
	}
	public void setFirstInFlag(Integer firstInFlag) {
		this.firstInFlag = firstInFlag;
	}
	public Integer getSecondInFlag() {
		return secondInFlag;
	}
	public void setSecondInFlag(Integer secondInFlag) {
		this.secondInFlag = secondInFlag;
	}
	public Integer getThirdInFlag() {
		return thirdInFlag;
	}
	public void setThirdInFlag(Integer thirdInFlag) {
		this.thirdInFlag = thirdInFlag;
	}
}
