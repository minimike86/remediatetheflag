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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.remediatetheflag.global.messages.annotations.LazilyHint;

@Entity( name = "flagQuestion")
@Table( name = "flagQuestions" )
public class FlagQuestion {

	@Id
	@Column(name = "idFlagQuestion")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@SerializedName("id")
    @Expose
    private Integer id;
   
	@SerializedName("instructions")
    @Expose
    @Column(name = "instructions", columnDefinition = "LONGTEXT")
    private String instructions;
    
    @SerializedName("hintAvailable")
    @Expose
    @Column(name = "hintAvailable")
    private Boolean hintAvailable;
    
    @SerializedName("selfCheckAvailable")
    @Expose
    @Column(name = "selfCheckAvailable")
    private Boolean selfCheckAvailable;
    
    @SerializedName("optional")
    @Expose
    @Column(name = "optional")
    private Boolean optional;
    
    @SerializedName("selfCheckName")
    @Expose
    @Column(name = "selfCheckName")
    private String selfCheckName;
    
    @SerializedName("type")
    @Expose
    @Column(name = "type")
    private String type;
    
    @SerializedName("maxScore")
    @Expose
    @Column(name = "maxScore")
    private Integer maxScore;
    
    @LazilyHint
    @Cascade({CascadeType.ALL})
    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true)
    @Expose    
    private FlagQuestionHint hint;

    public FlagQuestionHint getHint() {
		return hint;
	}

	public void setHint(FlagQuestionHint hint) {
		this.hint = hint;
	}

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
     *     The instructions
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * 
     * @param instructions
     *     The instructions
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * 
     * @return
     *     The hintAvailable
     */
    public Boolean getHintAvailable() {
        return hintAvailable;
    }

    /**
     * 
     * @param hintAvailable
     *     The hintAvailable
     */
    public void setHintAvailable(Boolean hintAvailable) {
        this.hintAvailable = hintAvailable;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

	public String getSelfCheckName() {
		return selfCheckName;
	}

	public void setSelfCheckName(String selfCheckName) {
		this.selfCheckName = selfCheckName;
	}

	public Boolean getSelfCheckAvailable() {
		return selfCheckAvailable;
	}

	public void setSelfCheckAvailable(Boolean selfCheckAvailable) {
		this.selfCheckAvailable = selfCheckAvailable;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlagQuestion other = (FlagQuestion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(Integer maxScore) {
		this.maxScore = maxScore;
	}

	public Boolean getOptional() {
		return optional;
	}

	public void setOptional(Boolean optional) {
		this.optional = optional;
	}

}
