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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.remediatetheflag.global.messages.annotations.LazilySerialized;

@Entity( name = "Feedback" )
@Table( name = "feedback" )
public class Feedback {

	@Id
	@Column(name = "idFeedback")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer idFeedback;

	@Expose
	@LazilySerialized
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	private User user;

	@Expose
	@LazilySerialized
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "exerciseInstanceId")
	private ExerciseInstance instance;

	@SerializedName("id")
	@Expose
	@Column(name="feedback")
	@Lob
	private String feedback;
	
	@SerializedName("date")
	@Expose
	@Column(name="date")
	Date date;

	public Integer getIdFeedback() {
		return idFeedback;
	}
	public void setIdFeedback(Integer idFeedback) {
		this.idFeedback = idFeedback;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public ExerciseInstance getInstance() {
		return instance;
	}
	public void setInstance(ExerciseInstance instance) {
		this.instance = instance;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public Date getDate() {
		return date;		
	}
	public void setDate(Date date) {
		this.date = date;

	}
}
