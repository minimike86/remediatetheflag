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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Table( name="flagQuestionHints")
@Entity( name = "FlagQuestionHint")
public class FlagQuestionHint {

    @Id
	@Column(name = "idFlagQuestionHint")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@SerializedName("id")
    @Expose
    private Integer id;
	
	@SerializedName("text")
	@Expose
	@Column(name="text", columnDefinition = "LONGTEXT")
	private String text;
	
	@SerializedName("type")
	@Expose
	@Column(name="type")
	private String type;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
