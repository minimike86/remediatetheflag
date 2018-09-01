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

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmittedReview {

	@SerializedName("review")
	@Expose
	private List<ExerciseResult> review;
	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("totalScore")
	@Expose
	private Integer totalScore;
	@SerializedName("awardTrophy")
	@Expose
	private Boolean awardTrophy;
	@SerializedName("newIssuesIntroduced")
	@Expose
	private Boolean newIssuesIntroduced;
	@SerializedName("newIssuesIntroducedText")
	@Expose
	private String newIssuesIntroducedText;
	
	
	public List<ExerciseResult> getReview() {
		return review;
	}
	public void setReview(List<ExerciseResult> review) {
		this.review = review;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	public Boolean getAwardTrophy() {
		return awardTrophy;
	}
	public void setAwardTrophy(Boolean awardTrophy) {
		this.awardTrophy = awardTrophy;
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

}
