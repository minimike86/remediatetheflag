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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public enum OrganizationStatus {

	ACTIVE(1),
	INACTIVE(0),
	;

	public static final OrganizationStatus DEFAULT_STATUS = ACTIVE;

	private OrganizationStatus(Integer statusCode){
		this.code = statusCode;
	}

	@SerializedName("code")
	@Expose
	private Integer code;

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}

}
