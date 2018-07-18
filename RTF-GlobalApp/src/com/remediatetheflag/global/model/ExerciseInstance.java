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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.amazonaws.regions.Regions;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.remediatetheflag.global.messages.annotations.ChallengeDetails;
import com.remediatetheflag.global.messages.annotations.HistoryDetails;
import com.remediatetheflag.global.messages.annotations.LazilySerialized;

@Entity( name = "ExerciseInstance")
@Table( name = "exerciseInstances" )
public class ExerciseInstance {

	@Id
	@Column(name = "idExerciseInstance")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@SerializedName("id")
	@Expose
	private Integer idExerciseInstance;
	
	@SerializedName("organization")
	@Expose
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name = "organizationId" )
	private Organization organization;

	@SerializedName("title")
	@Expose
	@Column(name = "title")
	private String title;

	@SerializedName("technology")
	@Expose
	@Column(name = "technology")
	private String technology;

	@SerializedName("startTime")
	@Expose
	@Column(name = "startTime")
	private Date startTime;

	@SerializedName("endTime")
	@Expose
	@Column(name = "endTime")
	private Date endTime;

	@SerializedName("region")
	@Expose
	@Enumerated(EnumType.STRING)
	@Column(name = "region")
	private Regions region;
	
	@SerializedName("resultsAvailable")
	@Expose
	@Column(name = "resultsAvailable")
	private boolean resultsAvailable;

	@SerializedName("status")
	@Expose
	@Enumerated(EnumType.ORDINAL)
	@Column(name="status")
	private ExerciseStatus status;
	
	@SerializedName("type")
	@Expose
	@Enumerated(EnumType.ORDINAL)
	@Column(name="type")
	private ExerciseInstanceType type;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name="launchType")
	private ExerciseInstanceLaunchType launchType;

	@SerializedName("exercise")
	@Expose
	@ManyToOne(fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@JoinColumn(name = "exerciseId")
	private AvailableExercise exercise;

	@SerializedName("score")
	@Expose
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private ExerciseScore score;
	
	@SerializedName("guac")
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	private GuacTempUser guac;
	
	@Column
	@Expose
	@SerializedName("duration")
	private Integer duration;
	
	@Expose
	@SerializedName("user")
	@LazilySerialized
	@ChallengeDetails
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@SerializedName("ecsInstance")
	@Expose
	@LazilySerialized
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ecsInstanceId")
	private RTFECSContainerTask ecsInstance;
	
	@SerializedName("results")
	@Expose
	@LazilySerialized
	@HistoryDetails
	@ChallengeDetails
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ExerciseResult> results = new ArrayList<ExerciseResult>();
	
	@SerializedName("resultsFile")
	@LazilySerialized
	@Expose
    @org.hibernate.annotations.Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	@OneToOne(fetch = FetchType.LAZY)
	private ExerciseResultFile resultFile = new ExerciseResultFile();
	
	@SerializedName("usedHints")
	@Expose
	@HistoryDetails
	@LazilySerialized
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FlagQuestionHint> usedHints;

	@Expose
	@SerializedName("trophyAwarded")
	@Column(name = "trophyAwarded")
	private Boolean trophyAwarded;
	
	@Expose
	@SerializedName("newIssuesIntroduced")
	@Column(name = "newIssuesIntroduced")
	private Boolean newIssuesIntroduced;
	
	@Expose
	@SerializedName("newIssuesIntroducedText")
	@Column(name = "newIssuesIntroducedText", columnDefinition = "LONGTEXT")
	private String newIssuesIntroducedText;
	
	@Expose
	@SerializedName("reviewer")
	@LazilySerialized
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reviwerId")
	private User reviewer;
	
	@Expose
	@SerializedName("reviewedDate")
	@Column(name = "reviewedDate")
	private Date reviewedDate;
	
	@Expose
	@SerializedName("feedback")
	@Column(name = "feedback")
	private Boolean feedback;
	
	public List<FlagQuestionHint> getUsedHints() {
		return usedHints;
	}

	public void setUsedHints(List<FlagQuestionHint> usedHints) {
		this.usedHints = usedHints;
	}

	public ExerciseResultFile getResultFile() {
		return resultFile;
	}

	public void setResultFile(ExerciseResultFile resultFiles) {
		this.resultFile = resultFiles;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public ExerciseStatus getStatus() {
		return status;
	}

	public void setStatus(ExerciseStatus status) {
		this.status = status;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 
	 * @return
	 *     The technology
	 */
	public String getTechnology() {
		return technology;
	}

	/**
	 * 
	 * @param technology
	 *     The technology
	 */
	public void setTechnology(String technology) {
		this.technology = technology;
	}

	/**
	 * 
	 * @return
	 *     The dateTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	/**
	 * 
	 * @param dateTime
	 *     The dateTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}


	/**
	 * 
	 * @param dateTime
	 *     The dateTime
	 */
	public void setEndTime(Date dateTime) {
		this.endTime = dateTime;
	}
	
	/**
	 * 
	 * @return
	 *     The gw
	 */
	public Regions getRegion() {
		return region;
	}

	/**
	 * 
	 * @param gw
	 *     The gw
	 */
	public void setRegion(Regions region) {
		this.region = region;
	}

	/**
	 * 
	 * @return
	 *     The score
	 */
	public ExerciseScore getScore() {
		return score;
	}

	/**
	 * 
	 * @param score
	 *     The score
	 */
	public void setScore(ExerciseScore score) {
		this.score = score;
	}

	/**
	 * 
	 * @return
	 *     The exercise
	 */
	public AvailableExercise getAvailableExercise() {
		return exercise;
	}

	/**
	 * 
	 * @param exercise
	 *     The exercise
	 */
	public void setAvailableExercise(AvailableExercise exercise) {
		this.exercise = exercise;
	}

	/**
	 * 
	 * @return
	 *     The results
	 */
	public List<ExerciseResult> getResults() {
		return results;
	}

	/**
	 * 
	 * @param results
	 *     The results
	 */
	public void setResults(List<ExerciseResult> results) {
		this.results = results;
	}
	public Integer getIdExerciseInstance() {
		return idExerciseInstance;
	}

	public void setIdExerciseInstance(Integer idExerciseInstance) {
		this.idExerciseInstance = idExerciseInstance;
	}

	public boolean isResultsAvailable() {
		return resultsAvailable;
	}

	public void setResultsAvailable(boolean resultsAvailable) {
		this.resultsAvailable = resultsAvailable;
	}

	public GuacTempUser getGuac() {
		return guac;
	}

	public void setGuac(GuacTempUser guac) {
		this.guac = guac;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idExerciseInstance == null) ? 0 : idExerciseInstance.hashCode());
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
		ExerciseInstance other = (ExerciseInstance) obj;
		if (idExerciseInstance == null) {
			if (other.idExerciseInstance != null)
				return false;
		} else if (!idExerciseInstance.equals(other.idExerciseInstance))
			return false;
		return true;
	}

	public AvailableExercise getExercise() {
		return exercise;
	}

	public void setExercise(AvailableExercise exercise) {
		this.exercise = exercise;
	}

	public void setTrophyAwarded(Boolean awardTrophy) {
		trophyAwarded = awardTrophy;
	}
	public Boolean getTrophyAwarded() {
		return trophyAwarded;
		
	}

	public Boolean getNewIssuesIntroduced() {
		return newIssuesIntroduced;
	}

	public void setNewIssuesIntroduced(Boolean newIssuesIntroduced) {
		this.newIssuesIntroduced = newIssuesIntroduced;
	}

	public String getNewIssuesIntroducedText() {
		return newIssuesIntroducedText;
	}

	public void setNewIssuesIntroducedText(String newIssuesIntroducedText) {
		this.newIssuesIntroducedText = newIssuesIntroducedText;
	}

	public User getReviewer() {
		return reviewer;
	}

	public void setReviewer(User reviewer) {
		this.reviewer = reviewer;
	}

	public Date getReviewedDate() {
		return reviewedDate;
	}

	public void setReviewedDate(Date reviewedDate) {
		this.reviewedDate = reviewedDate;
	}

	public Boolean getFeedback() {
		return feedback;
	}

	public void setFeedback(Boolean feedback) {
		this.feedback = feedback;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public ExerciseInstanceType getType() {
		return type;
	}

	public void setType(ExerciseInstanceType type) {
		this.type = type;
	}

	public ExerciseInstanceLaunchType getLaunchType() {
		return launchType;
	}

	public void setLaunchType(ExerciseInstanceLaunchType launchType) {
		this.launchType = launchType;
	}

	public RTFECSContainerTask getEcsInstance() {
		return ecsInstance;
	}

	public void setEcsInstance(RTFECSContainerTask ecsInstance) {
		this.ecsInstance = ecsInstance;
	}
}
