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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table( name = "availableExercisesInfo" )
public class AvailableExerciseInfo {

	@Id
	@Column(name = "idExerciseInfo")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@SerializedName("id")
    @Expose
    private Integer id;
    
	@Column(name = "infoOrder")
    @SerializedName("infoOrder")
    @Expose
    private Integer infoOrder;
  
    @SerializedName("title")
    @Expose
	@Column(name = "title")
    private String title;
    
    @SerializedName("text")
    @Expose
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<AvailableExerciseInfoText> text = new ArrayList<AvailableExerciseInfoText>();


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
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The text
     */
    public List<AvailableExerciseInfoText> getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(List<AvailableExerciseInfoText> text) {
        this.text = text;
    }

	public Integer getOrder() {
		return infoOrder;
	}

	public void setOrder(Integer order) {
		this.infoOrder = order;
	}

}
