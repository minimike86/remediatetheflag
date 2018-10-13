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

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Achievements {

    @SerializedName("score")
    @Expose
    private Integer score;
    
    @SerializedName("exercisesRun")
    @Expose
    private Integer exercisesRun;
    
    @SerializedName("trophies")
    @Expose
    private List<AchievedTrophy> trophies = new ArrayList<AchievedTrophy>();

    /**
     * 
     * @return
     *     The score
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 
     * @param score
     *     The score
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 
     * @return
     *     The trophies
     */
    public List<AchievedTrophy> getTrophies() {
        return trophies;
    }

    /**
     * 
     * @param trophies
     *     The trophies
     */
    public void setTrophies(List<AchievedTrophy> trophies) {
        this.trophies = trophies;
    }

	public Integer getExercisesRun() {
		return exercisesRun;
	}

	public void setExercisesRun(Integer exercisesRun) {
		this.exercisesRun = exercisesRun;
	}


}
