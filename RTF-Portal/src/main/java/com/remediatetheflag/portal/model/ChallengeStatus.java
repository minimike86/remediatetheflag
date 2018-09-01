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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public enum ChallengeStatus {
	
	NOT_STARTED(0),
	IN_PROGRESS(1),
	FINISHED(2)
	;

	public static final ChallengeStatus DEFAULT_STATUS = NOT_STARTED;

	private ChallengeStatus(Integer statusCode){
		this.statusCode = statusCode;
	}

	@SerializedName("name")
	@Expose
	private Integer statusCode;

	public Integer getName() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public ChallengeStatus getStatusFromStatusCode(Integer statusCode){
		for (ChallengeStatus status :ChallengeStatus.values()) {
			if (statusCode==status.getName()) {
				return status;
			}
		}
		throw new IllegalArgumentException("Cannot create enum from " + statusCode + " value!");
	}

}
