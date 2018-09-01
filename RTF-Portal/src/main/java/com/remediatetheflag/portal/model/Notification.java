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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(name = "Notification")
@Table( name = "notifications" )
public class Notification {

	@Id
	@Column(name = "idNotification")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@SerializedName("id")
	@Expose
	private Integer idNotification;

	@Column(name="text", columnDefinition = "LONGTEXT")
	@SerializedName("text")
	@Expose
	private String text;
	
	@Column(name="link", columnDefinition = "LONGTEXT")
	@SerializedName("link")
	@Expose
	private String link;
	
	@Column(name = "idUser")
	private Integer idUser;
	
	@Column(name = "userRead")
	private Boolean userRead;

	@Column(name = "dateStart")
	private Date dateStart;

	@Column(name = "dateEnd")
	private Date dateEnd;
	
	@Column(name = "dateRead")
	private Date dateRead;

	public Integer getId() {
		return idNotification;
	}

	public void setId(Integer id) {
		this.idNotification = id;
	}

	public String getText() {
		return text;
	}
	
	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Boolean getUserRead() {
		return userRead;
	}

	public void setUserRead(Boolean userRead) {
		this.userRead = userRead;
	}

	public Date getDateRead() {
		return dateRead;
	}

	public void setDateRead(Date dateRead) {
		this.dateRead = dateRead;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setText(String text) {
		this.text = text;
	}
}
