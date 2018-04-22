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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table( name = "timezones" )
public class Timezone {

    @SerializedName("id")
    @Expose
    @Id
    @Column(name = "idTimezone")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "timeFromGMT")
    @SerializedName("timeFromGMT")
    @Expose
    private Double timeFromGMT;
    
    @Column(name = "short", unique = true)
    @SerializedName("short")
    @Expose
    private String shortName;
   
    @Column(name = "name", unique = true)
    @SerializedName("name")
    @Expose
    private String name;

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
     *     The value
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return
     *     The value
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    
    public Double getTimeFromGMT(){
    	return timeFromGMT;
    }

	public void setTimeFromGMT(Double timeFromGMT) {
		this.timeFromGMT = timeFromGMT;		
	}


}
