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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompletedReview extends PendingReview {

	@Expose
	@SerializedName("score")
	private Integer score;
	@Expose
	@SerializedName("trophyAwarded")
	private Boolean trophyAwarded;
	@Expose
	@SerializedName("newIssuesIntroduced")
	private Boolean newIssueIntroduced;
	@Expose
	@SerializedName("status")
	private ExerciseStatus status;
	@Expose
	@SerializedName("feedback")	
	private Boolean feedback;
	@Expose
	@SerializedName("dateReviewed")	
	private Date date;
	@Expose
	@SerializedName("issuesReported")	
	private Boolean issuesReported;
	@Expose
	@SerializedName("issuesReportedAddressed")	
	private Boolean issuesReportedAddressed;

	@Expose
	@SerializedName("nrResRefresh")
	private Integer nrResRefresh;

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Boolean getTrophyAwarded() {
		return trophyAwarded;
	}

	public void setTrophyAwarded(Boolean trophyAwarded) {
		this.trophyAwarded = trophyAwarded;
	}

	public Boolean getNewIssueIntroduced() {
		return newIssueIntroduced;
	}

	public void setNewIssueIntroduced(Boolean newIssueIntroduce) {
		this.newIssueIntroduced = newIssueIntroduce;
	}

	public ExerciseStatus getStatus() {
		return this.status;
	}


	public void setStatus(ExerciseStatus status) {
		this.status = status;
	}

	public void setFeedback(Boolean feedback) {
		this.feedback = feedback;

	}
	public Boolean getFeedback() {
		return this.feedback;

	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Boolean getReportedScoringIssues() {
		return this.issuesReported;		
	}

	public void setReportedScoringIssues(boolean issuesReported) {
		this.issuesReported = issuesReported;

	}

	public Boolean getIssuesReported() {
		return issuesReported;
	}

	public void setIssuesReported(Boolean issuesReported) {
		this.issuesReported = issuesReported;
	}

	public Integer getNrResRefresh() {
		return nrResRefresh;
	}

	public void setNrResRefresh(Integer nrResRefresh) {
		this.nrResRefresh = nrResRefresh;
	}

	public Boolean getIssuesReportedAddressed() {
		return issuesReportedAddressed;
	}

	public void setIssuesReportedAddressed(Boolean issuesReportedAddressed) {
		this.issuesReportedAddressed = issuesReportedAddressed;
	}

}
