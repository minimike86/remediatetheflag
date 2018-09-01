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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ColumnDefault;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.remediatetheflag.portal.messages.annotations.BriefDetails;
import com.remediatetheflag.portal.messages.annotations.ChallengeDetails;
import com.remediatetheflag.portal.messages.annotations.HistoryDetails;
import com.remediatetheflag.portal.messages.annotations.LazilySerialized;

@Entity( name = "AvailableExercise" )
@Table( name = "availableExercises" )
public class AvailableExercise implements Serializable{

	@Transient
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idExercise")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SerializedName("id")
	@Expose
	@BriefDetails
	private Integer id;
	
	@Column(name = "uuid")
	private String uuid;
	
	@Column(name = "version")
	@ColumnDefault("0")
	private Integer version;

	@Column(name = "title")
	@SerializedName("title")
	@Expose
	private String title;

	@Column(name = "subtitle")
	@SerializedName("subtitle")
	@Expose
	private String subtitle;
	
	@Column(name = "author")
	@SerializedName("author")
	@Expose
	private String author;

	@Column(name = "description", columnDefinition = "LONGTEXT")
	@SerializedName("description")
	@Expose
	private String description;

	@Column(name = "score")
	@SerializedName("score")
	@Expose
	private Integer score;

	@Column(name = "technology")
	@SerializedName("technology")
	@Expose
	private String technology;

	@SerializedName("trophy")
	@Expose
	@Cascade({CascadeType.ALL})
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "trophyId")
	private Trophy trophy;

	@Column(name = "duration")
	@SerializedName("duration")
	@Expose
	private Integer duration;

	@Column(name = "difficulty")
	@SerializedName("difficulty")
	@Expose
	private String difficulty;

	@SerializedName("resources")
	@Expose
	@Cascade({CascadeType.ALL})
    @ElementCollection(targetClass = String.class,fetch=FetchType.EAGER)
	@CollectionTable(name = "availableExercises_resources")
	@MapKeyColumn(name="resource")
	@Column(name="address")
	private Map<String, String> resources = new HashMap<String, String>();
	
	@SerializedName("info")
	@LazilySerialized
	@Expose
	@Cascade({CascadeType.ALL})
	@OneToMany(fetch = FetchType.LAZY)
	@OrderBy("infoOrder")
	private List<AvailableExerciseInfo> infoList = new ArrayList<AvailableExerciseInfo>();

	@SerializedName("flags")
	@LazilySerialized
	@HistoryDetails
	@ChallengeDetails
	@Expose
	@Cascade({CascadeType.ALL})
	@OneToMany(fetch = FetchType.LAZY)
	private List<Flag> questionsList = new ArrayList<Flag>();

	@SerializedName("referenceFile")
	@LazilySerialized
	@Expose
	@Cascade({CascadeType.ALL})
	@OneToOne(fetch = FetchType.LAZY)
	private AvailableExerciseReferenceFile referenceFile = new AvailableExerciseReferenceFile();

	@SerializedName("exerciseType")
	@Expose
	@Enumerated(EnumType.STRING)
	@Column(name = "exerciseType")
	private AvailableExerciseType exerciseType;
	
	@SerializedName("solutionFile")
	@LazilySerialized
	@Expose
	@Cascade({CascadeType.ALL})
	@OneToOne(fetch = FetchType.LAZY)
	private AvailableExerciseSolutionFile solutionFile = new AvailableExerciseSolutionFile();

	@SerializedName("status")
	@Expose
	@Column(name = "status")
	private AvailableExerciseStatus status;
	
	@SerializedName("tags")
	@Expose
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> tags = new ArrayList<String>();
	
	@SerializedName("createdByUser")
	@LazilySerialized
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "createdByUser")
	private User createdByUser;

	
	/**
	 * 
	 * @return
	 *     The id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *     The id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 *     The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 *     The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 
	 * @return
	 *     The subtitle
	 */
	public String getSubtitle() {
		return subtitle;
	}

	/**
	 * 
	 * @param subtitle
	 *     The subtitle
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	/**
	 * 
	 * @return
	 *     The description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *     The description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return
	 *     The score
	 */
	public Integer getScore() {
		return score;
	}

	/**
	 * 
	 * @param score
	 *     The score
	 */
	public void setScore(Integer score) {
		this.score = score;
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
	 *     The trophy
	 */
	public Trophy getTrophy() {
		return trophy;
	}

	/**
	 * 
	 * @param trophy
	 *     The trophy
	 */
	public void setTrophy(Trophy trophy) {
		this.trophy = trophy;
	}

	/**
	 * 
	 * @return
	 *     The duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * 
	 * @param duration
	 *     The duration
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}


	/**
	 * 
	 * @return
	 *     The flags
	 */
	public List<Flag> getFlags() {
		return questionsList;
	}

	/**
	 * 
	 * @param flags
	 *     The flags
	 */
	public void setFlags(List<Flag> flags) {
		this.questionsList = flags;
	}


	public List<AvailableExerciseInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<AvailableExerciseInfo> infoList) {
		this.infoList = infoList;
	}

	public List<Flag> getQuestionsList() {
		return questionsList;
	}

	public void setQuestionsList(List<Flag> questionsList) {
		this.questionsList = questionsList;
	}

	public AvailableExerciseReferenceFile getReferenceFile() {
		return referenceFile;
	}

	public void setReferenceFile(AvailableExerciseReferenceFile referenceFile) {
		this.referenceFile = referenceFile;
	}

	public AvailableExerciseSolutionFile getSolutionFile() {
		return solutionFile;
	}

	public void setSolutionFile(AvailableExerciseSolutionFile solutionFile) {
		this.solutionFile = solutionFile;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public Map<String, String> getResources() {
		return resources;
	}

	public void setResources(Map<String, String> resources) {
		this.resources = resources;
	}

	public AvailableExerciseType getExerciseType() {
		return exerciseType;
	}

	public void setExerciseType(AvailableExerciseType exerciseType) {
		this.exerciseType = exerciseType;
	}

	public AvailableExerciseStatus getStatus() {
		return status;
	}

	public void setStatus(AvailableExerciseStatus status) {
		this.status = status;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		AvailableExercise other = (AvailableExercise) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AvailableExercise [id=" + id + ", version=" + version + ", title=" + title + ", subtitle=" + subtitle
				+ ", author=" + author + ", description=" + description + ", score=" + score + ", technology="
				+ technology + ", duration=" + duration + ", difficulty=" + difficulty + ", exerciseType="
				+ exerciseType + ", status=" + status + "]";
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public User getCreatedByUser() {
		return createdByUser;
	}

	public void setCreatedByUser(User createdByUser) {
		this.createdByUser = createdByUser;
	}

}
