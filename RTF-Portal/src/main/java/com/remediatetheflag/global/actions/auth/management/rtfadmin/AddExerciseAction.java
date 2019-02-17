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
package com.remediatetheflag.global.actions.auth.management.rtfadmin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.AvailableExercise;
import com.remediatetheflag.global.model.AvailableExerciseInfo;
import com.remediatetheflag.global.model.AvailableExerciseReferenceFile;
import com.remediatetheflag.global.model.AvailableExerciseSolutionFile;
import com.remediatetheflag.global.model.AvailableExerciseStatus;
import com.remediatetheflag.global.model.AvailableExerciseType;
import com.remediatetheflag.global.model.Flag;
import com.remediatetheflag.global.model.FlagQuestion;
import com.remediatetheflag.global.model.FlagQuestionHint;
import com.remediatetheflag.global.model.Trophy;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;

public class AddExerciseAction extends IAction{

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);
		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		JsonElement titleElement = json.get(Constants.ACTION_PARAM_TITLE);
		JsonElement topicsElement = json.get(Constants.ACTION_PARAM_TOPICS);
		JsonElement descriptionElement = json.get(Constants.ACTION_PARAM_DESCRIPTION);
		JsonElement difficultyElement = json.get(Constants.ACTION_PARAM_DIFFICULTY);
		JsonElement technologyElement = json.get(Constants.ACTION_PARAM_TECHNOLOGY);
		JsonElement durationElement = json.get(Constants.ACTION_PARAM_DURATION);
		JsonElement trophyTitleElement = json.get(Constants.ACTION_PARAM_TROPHY_TITLE);
		JsonElement trophyDescriptionElement = json.get(Constants.ACTION_PARAM_TROPHY_DESCRIPTION);
		JsonElement statusElement = json.get(Constants.ACTION_PARAM_STATUS);
		JsonElement typeElement = json.get(Constants.ACTION_PARAM_TYPE);
		JsonElement authorElement = json.get(Constants.ACTION_PARAM_AUTHOR);

		AvailableExercise exercise = new AvailableExercise();
		exercise.setDescription(descriptionElement.getAsString());
		exercise.setDifficulty(difficultyElement.getAsString());
		exercise.setDuration(durationElement.getAsInt());
		exercise.setTitle(titleElement.getAsString());
		exercise.setSubtitle(topicsElement.getAsString());
		exercise.setTechnology(technologyElement.getAsString());
		exercise.setStatus(AvailableExerciseStatus.getStatusFromStatusCode(statusElement.getAsInt()));
		exercise.setAuthor(authorElement.getAsString());
		exercise.setUuid(UUID.randomUUID().toString());
		exercise.setVersion(0);

		AvailableExerciseType validType = AvailableExerciseType.getStatusFromName(typeElement.getAsString());
		
		if(validType==null)
			validType = AvailableExerciseType.BOTH;
		exercise.setExerciseType(validType);

		Trophy trophy = new Trophy();
		trophy.setDescription(trophyDescriptionElement.getAsString());
		trophy.setName(trophyTitleElement.getAsString());
		trophy.setTechnology(exercise.getTechnology());
		exercise.setTrophy(trophy);

		JsonElement flags = json.get(Constants.ACTION_PARAM_FLAGS_LIST);
		JsonElement infos = json.get(Constants.ACTION_PARAM_INFO_LIST);
		JsonElement resources = json.get(Constants.ACTION_PARAM_RESOURCE_LIST);

		JsonElement referenceFile = json.get(Constants.ACTION_PARAM_REFERENCE_FILE);
		if(null!=referenceFile) {
			JsonObject referenceFileObj = referenceFile.getAsJsonObject();
			String tmpReferenceFileString = referenceFileObj.get(Constants.ACTION_PARAM_DATA).getAsString();
			byte[] tmpReferenceFile = null;
			try {
				tmpReferenceFileString = tmpReferenceFileString.replaceFirst("(.*);base64,", "");
				tmpReferenceFile = Base64.decodeBase64(tmpReferenceFileString);
			}catch(Exception e) {
				logger.warn("ReferenceFile Parsing error for user "+sessionUser.getIdUser()+" in adding exercise "+titleElement.getAsString()+" due to: "+e.getMessage(), response);
			}
			if(null!=tmpReferenceFile && tmpReferenceFile.length!=0) {
				AvailableExerciseReferenceFile refFile = new AvailableExerciseReferenceFile();
				refFile.setFile(tmpReferenceFile);
				refFile.setFilename(referenceFileObj.get(Constants.ACTION_PARAM_NAME).getAsString());
				exercise.setReferenceFile(refFile);
				exercise.setReferenceFileAvailable(true);
			}else {
				exercise.setReferenceFileAvailable(false);
			}
		}else {
			exercise.setReferenceFileAvailable(false);
		}

		JsonElement solutionFile = json.get(Constants.ACTION_PARAM_SOLUTION_FILE);
		JsonObject solutionFileObj = solutionFile.getAsJsonObject();
		String tmpSolutionFileString = solutionFileObj.get(Constants.ACTION_PARAM_DATA).getAsString();
		byte[] tmpSolutioneFile = null;
		try {
			tmpSolutionFileString = tmpSolutionFileString.replaceFirst("(.*);base64,", "");
			tmpSolutioneFile = Base64.decodeBase64(tmpSolutionFileString);
		}catch(Exception e) {
			MessageGenerator.sendErrorMessage("solutioneFileParsing", response);
			return;
		}
		if(null==tmpSolutioneFile || tmpSolutioneFile.length==0) {
			MessageGenerator.sendErrorMessage("solutioneFileEmpty", response);
			return;
		}

		AvailableExerciseSolutionFile solFile = new AvailableExerciseSolutionFile();
		solFile.setFilename(solutionFileObj.get(Constants.ACTION_PARAM_NAME).getAsString());
		solFile.setFile(tmpSolutioneFile);
		exercise.setSolutionFile(solFile);

		Map<String,String> resourceMap = new HashMap<String,String>();
		for(JsonElement resourceElem : resources.getAsJsonArray()) {
			JsonObject tmpResource = resourceElem.getAsJsonObject();
			resourceMap.put(tmpResource.get(Constants.ACTION_PARAM_TITLE).getAsString(), tmpResource.get(Constants.ACTION_PARAM_URL).getAsString());
		}
		exercise.setResources(resourceMap);

		LinkedList<AvailableExerciseInfo> infoList = new LinkedList<AvailableExerciseInfo>();
		int n = 0;
		for(JsonElement infoElem : infos.getAsJsonArray()) {
			JsonObject tmpInfo = infoElem.getAsJsonObject();
			AvailableExerciseInfo tmpExInfo = new AvailableExerciseInfo();
			tmpExInfo.setTitle(tmpInfo.get(Constants.ACTION_PARAM_TITLE).getAsString());
			tmpExInfo.setDescription(tmpInfo.get(Constants.ACTION_PARAM_DESCRIPTION).getAsString());
			tmpExInfo.setInfoOrder(n);
			JsonObject tmpImage = tmpInfo.get(Constants.ACTION_PARAM_IMAGE).getAsJsonObject();
			String imageString = tmpImage.get(Constants.ACTION_PARAM_DATA).getAsString();
			byte[] tmpImageFile = null;
			try {
				imageString = imageString.replaceFirst("(.*);base64,", "");
				tmpImageFile = Base64.decodeBase64(imageString);
			}catch(Exception e) {
				MessageGenerator.sendErrorMessage("infoImageParsing", response);
				return;
			}
			tmpExInfo.setImage(tmpImageFile);
			infoList.add(tmpExInfo);
			n++;
		}
		exercise.setInfoList(infoList);
		if(infoList.isEmpty()) {
			MessageGenerator.sendErrorMessage("infoListEmpty", response);
			return;
		}

		Integer totalScore = 0;
		LinkedList<Flag> flagList = new LinkedList<Flag>();
		for(JsonElement flagElem : flags.getAsJsonArray()) {
			Flag flag = new Flag();

			JsonObject tmpFlag = flagElem.getAsJsonObject();
			JsonElement flagTitle = tmpFlag.get(Constants.ACTION_PARAM_TITLE);
			JsonElement flagCategory = tmpFlag.get(Constants.ACTION_PARAM_CATEGORY);
			JsonElement flagQuestions = tmpFlag.get(Constants.ACTION_PARAM_FLAG_QUESTIONS);

			flag.setCategory(flagCategory.getAsString());
			flag.setTitle(flagTitle.getAsString());
			LinkedList<FlagQuestion> questionList = new LinkedList<FlagQuestion>();

			for(JsonElement questionElem : flagQuestions.getAsJsonArray()) {
				FlagQuestion tmpQuestion = new FlagQuestion();
				JsonObject qEl = questionElem.getAsJsonObject();
				tmpQuestion.setType(qEl.get(Constants.ACTION_PARAM_TYPE).getAsString());
				tmpQuestion.setOptional(qEl.get(Constants.ACTION_PARAM_OPTIONAL).getAsBoolean());
				if(!tmpQuestion.getOptional()) {
					tmpQuestion.setMaxScore(qEl.get(Constants.ACTION_PARAM_MAX_SCORE).getAsInt());
					totalScore += tmpQuestion.getMaxScore();
				}
				tmpQuestion.setSelfCheckAvailable(qEl.get(Constants.ACTION_PARAM_SELF_CHECK_AVAILABLE).getAsBoolean());
				if(tmpQuestion.getSelfCheckAvailable())
					tmpQuestion.setSelfCheckName(qEl.get(Constants.ACTION_PARAM_SELF_CHECK).getAsString());
				else
					tmpQuestion.setSelfCheckName(null);
				tmpQuestion.setInstructions(qEl.get(Constants.ACTION_PARAM_INSTRUCTIONS).getAsString());
				tmpQuestion.setHintAvailable(qEl.get(Constants.ACTION_PARAM_HINT_AVAILABLE).getAsBoolean());
				if(tmpQuestion.getHintAvailable()) {
					FlagQuestionHint tmpQuestionHint = new FlagQuestionHint();
					tmpQuestionHint.setType(tmpQuestion.getType());
					Gson gson = new Gson();  
					FlagQuestionHint newHint = gson.fromJson(qEl.get(Constants.ACTION_PARAM_HINT).getAsJsonObject(),FlagQuestionHint.class);
					if(null!=newHint) {
						tmpQuestionHint.setText(newHint.getText());
						if(!tmpQuestion.getOptional())
							tmpQuestionHint.setScoreReducePercentage(newHint.getScoreReducePercentage());
						tmpQuestion.setHint(tmpQuestionHint);
					}else {
						MessageGenerator.sendErrorMessage("hintEmpty", response);
						return;
					}
				}
				else {
					tmpQuestion.setHint(null);
				}
				questionList.add(tmpQuestion);
			}
			flag.setFlagQuestionList(questionList);
			flagList.add(flag);
		}
		exercise.setFlags(flagList);
		if(flagList.isEmpty()) {
			MessageGenerator.sendErrorMessage("flagListEmpty", response);
			return;
		}
		exercise.setScore(totalScore);

		Integer id = hpc.addAvailableExercise(exercise);
		if(null!=id)
			MessageGenerator.sendSuccessMessage(response);
		else
			MessageGenerator.sendErrorMessage("Error", response);
	}
}