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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.remediatetheflag.portal.messages.annotations.LeaderboardUser;

@Entity
@Table( name = "countries" )
public class Country {  
	    
    @SerializedName("id")
    @Expose
    @LeaderboardUser
    @Id
    @Column(name = "idCountry")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @SerializedName("name")
    @Expose
    @LeaderboardUser
    @Column(name = "name", unique = true)
    private String name;
    
    @SerializedName("short")
    @Expose
    @LeaderboardUser
    @Column(name = "short", unique = true)
    private String shortName;
    
    @SerializedName("timeZone")
	@Expose
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	@JoinColumn(name = "timezoneId")
	private Timezone timezone;

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
     *     The name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

	public Timezone getTimezone() {
		return timezone;
	}

	public void setTimezone(Timezone timezone) {
		this.timezone = timezone;
	}

}
