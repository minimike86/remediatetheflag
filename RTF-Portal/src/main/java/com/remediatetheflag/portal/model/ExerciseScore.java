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

@Entity( name = "ExerciseScore")
@Table( name = "exerciseScores" )
public class ExerciseScore {

	@Id
	@Column(name = "idExerciseScore")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@SerializedName("id")
	private Integer idExerciseScore;

	@SerializedName("total")
    @Expose
    @Column(name="total")
    private Integer total;

	@SerializedName("result")
    @Expose
    @Column(name="result")
    private Integer result;

	/**
     * 
     * @return
     *     The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The result
     */
    public Integer getResult() {
        return result;
    }

    /**
     * 
     * @param result
     *     The result
     */
    public void setResult(Integer result) {
        this.result = result;
    }
/*
	public ExerciseInstance getIdExercise() {
		return exerciseInstance;
	}

	public void setIdExercise(ExerciseInstance exerciseInstance) {
		this.exerciseInstance = exerciseInstance;
	}
*/
	public Integer getIdExerciseScore() {
		return idExerciseScore;
	}

	public void setIdExerciseScore(Integer idExerciseScore) {
		this.idExerciseScore = idExerciseScore;
	}

}
