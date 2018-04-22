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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table( name = "flags" )
public class Flag {

	@Id
	@Column(name = "idFlag")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@SerializedName("id")
    @Expose
    private Integer id;
	
    @SerializedName("title")
    @Expose
	@Column(name = "title")
    private String title;
    
    @SerializedName("category")
    @Expose
    @Column(name="category")
    private String category;

    @SerializedName("flagList")
    @Expose
    @Cascade({CascadeType.ALL})
    @OneToMany(fetch = FetchType.EAGER)
    private List<FlagQuestion> flagQuestionList = new ArrayList<FlagQuestion>();

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
     *     The flagList
     */
    public List<FlagQuestion> getFlagQuestionList() {
        return flagQuestionList;
    }

    /**
     * 
     * @param flagList
     *     The flagList
     */
    public void setFlagQuestionList(List<FlagQuestion> flagQuestionList) {
        this.flagQuestionList = flagQuestionList;
    }

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
