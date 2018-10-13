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
package com.remediatetheflag.global.actions.auth.user;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import  com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.Challenge;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.ExerciseResult;
import com.remediatetheflag.global.model.ExerciseSelfCheckResult;
import com.remediatetheflag.global.model.Flag;
import com.remediatetheflag.global.model.FlagQuestion;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;
import com.remediatetheflag.global.utils.ExerciseUtils;
import com.remediatetheflag.global.utils.GatewayHelper;

public class GetAutomatedResultsAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		Integer exerciseInstanceId = json.get(Constants.ACTION_PARAM_ID).getAsInt();

		ExerciseInstance instance = hpc.getActiveExerciseInstanceForUserWithECSGuacExerciseAndResult(user.getIdUser(),exerciseInstanceId);
		if(null!=instance){			
			GatewayHelper gw = new GatewayHelper();
			ExerciseSelfCheckResult res = gw.getSelfcheckResult(instance);
			res.setFlagList(res.getFlagList());
			MessageGenerator.sendExerciseSelfCheckStatus(res,response);

			Challenge exerciseChallenge = null;
			if(null!=instance.getChallengeId()) {
				exerciseChallenge = hpc.getChallengeFromId(instance.getChallengeId());
				exerciseChallenge.setLastActivity(new Date());
				hpc.updateChallenge(exerciseChallenge);
			}

			Integer totalScore = 0;
			if(instance.getResults().isEmpty()){
				for(Flag flag : instance.getAvailableExercise().getFlags()){
					for(FlagQuestion fq : flag.getFlagQuestionList()) {
						if(fq.getSelfCheckAvailable()) {
							ExerciseResult result = ExerciseUtils.getExerciseResult(instance,flag,fq,res.getFlagList(),exerciseChallenge);
							if(null!=result) {
								totalScore+=result.getScore();
								instance.getResults().add(result);
							}
						}
					}
				}
			}
			else {	
				totalScore = instance.getScore().getResult() + ExerciseUtils.updateExerciseResults(instance,res.getFlagList(),exerciseChallenge);
			}
			instance.getScore().setResult(totalScore);

			if(res.getFlagList().size()>0) {
				if(null==instance.getCountSelfCheckByUser())
					instance.setCountSelfCheckByUser(1);
				else
					instance.setCountSelfCheckByUser(instance.getCountSelfCheckByUser()+1);
			}
			hpc.updateExerciseInstance(instance);
			return;
		}

		logger.error("Could not retrieve selfcheck results for exerciseInstance: "+exerciseInstanceId+" for user: "+user.getIdUser());
		MessageGenerator.sendErrorMessage("NotFound", response);
	}
}