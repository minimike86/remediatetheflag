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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity( name = "ExerciseResult")
@Table( name = "exerciseResults" )
public class ExerciseResult {

	@Id
	@Column(name = "idResult")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SerializedName("id")
	private Integer idResult;

	@SerializedName("name")
	@Expose
	@Column(name = "name")
	private String name;

	@SerializedName("status")
	@Expose
	@Enumerated(EnumType.ORDINAL)
	@Column(name="status")
	private ExerciseResultStatus status;

	@SerializedName("category")
	@Expose
	@Column(name="category")
	private String category;

	@SerializedName("verified")
	@Expose
	@Column(name = "verified")
	private Boolean verified;

	@SerializedName("comment")
	@Expose
	@Lob
	@Column(name = "comment")
	private String comment;

	@SerializedName("score")
	@Expose
	@Column(name = "score")
	Integer score;


	public Integer getIdResult() {
		return idResult;
	}

	public void setIdResult(Integer idResult) {
		this.idResult = idResult;
	}


	/**
	 * 
	 * @return
	 *     The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *     The name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 *     The status
	 */
	public ExerciseResultStatus getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *     The status
	 */
	public void setStatus(ExerciseResultStatus status) {
		this.status = status;
	}

	/**
	 * 
	 * @return
	 *     The comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 
	 * @param comment
	 *     The comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getVerified() {
		return verified;
	}

	public void setVerified(Boolean verified) {
		this.verified = verified;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


}
