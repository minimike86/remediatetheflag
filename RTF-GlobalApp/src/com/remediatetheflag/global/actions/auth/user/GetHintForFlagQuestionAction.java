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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.ExerciseStatus;
import com.remediatetheflag.global.model.Flag;
import com.remediatetheflag.global.model.FlagQuestion;
import com.remediatetheflag.global.model.FlagQuestionHint;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;

public class GetHintForFlagQuestionAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		JsonElement idQuestionElement = json.get(Constants.ACTION_PARAM_ID);
		Integer idQuestion = idQuestionElement.getAsInt();
		JsonElement idExerciseInstanceElement = json.get(Constants.ACTION_PARAM_ID_EXERCISE_INSTANCE);
		Integer idExerciseInstance = idExerciseInstanceElement.getAsInt();
		Integer idFlag = hpc.getFlagIdFromQuestionId(idQuestion);
		if(null==idFlag){
			User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
			logger.error("Flag not found for questionId "+idQuestion+" for user "+user.getIdUser());
			MessageGenerator.sendErrorMessage("HintUnavailable", response);
			return;
		}
		Flag f =  hpc.getFlagWithHints(idFlag);	

		FlagQuestionHint hint = null;
		for(FlagQuestion fq : f.getFlagQuestionList()){
			if(fq.getId().equals(idQuestion)){
				hint = fq.getHint();
				break;
			}
		}
		if(null!=hint){
			ExerciseInstance ei = hpc.getExerciseInstanceWithHints(idExerciseInstance);
			if(null!=ei && null!=ei.getStatus() && !ei.getStatus().equals(ExerciseStatus.RUNNING)){
				User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
				logger.error("Refused to provide hint "+hint.getId()+" for ExerciseInstance "+ei.getIdExerciseInstance()+" with status "+ei.getStatus().toString()+" for user "+user.getIdUser());
				MessageGenerator.sendErrorMessage("HintUnavailable", response);
				return;
			}
			boolean alreadyIn = false;
			for(FlagQuestionHint fqh : ei.getUsedHints()){
				if(fqh.getId().equals(hint.getId())){
					alreadyIn = true;
					break;
				}
			}
			if(!alreadyIn){
				ei.getUsedHints().add(hint);
				hpc.updateExerciseInstance(ei);
			}
			MessageGenerator.sendHintMessage(hint,response);
		}
		else{
			User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
			logger.error("Hint not found for questionId "+idQuestion+" for user "+user.getIdUser());
			MessageGenerator.sendErrorMessage("HintUnavailable", response);
		}
	}
}