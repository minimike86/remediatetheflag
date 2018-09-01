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

import com.google.gson.JsonObject;

public class ValidatedData {

	private JsonObject json;
	private boolean withErrors;
	private List<String> errors;

	public ValidatedData(JsonObject json, boolean withErrors, List<String> errors) {
		this.json = json;
		this.withErrors = withErrors;
		this.errors = errors;
	}
	public JsonObject getJson() {
		return json;
	}
	public void setJson(JsonObject json) {
		this.json = json;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public void addError(String error) {
		this.errors.add(error);
	}
	public boolean isWithErrors() {
		return withErrors;
	}
	public void setWithErrors(boolean withErrors) {
		this.withErrors = withErrors;
	}
}
