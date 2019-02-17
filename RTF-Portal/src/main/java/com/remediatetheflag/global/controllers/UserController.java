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
package com.remediatetheflag.global.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.utils.Constants;

public class UserController extends ActionsController{

	@SuppressWarnings({ "serial", "rawtypes" })
	public UserController() {		

		HashMap<String, Class[]> idNotNullInteger = new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}};
		type2action.put("removeUser", com.remediatetheflag.global.actions.auth.user.RemoveUserAction.class);

		type2action.put("addScoringComplaint", com.remediatetheflag.global.actions.auth.user.AddUserResultCommentAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.AddUserResultCommentAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TEXT, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		
		
		type2action.put("getReferenceFile", com.remediatetheflag.global.actions.auth.user.GetAvailableExerciseReferenceFileAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.GetAvailableExerciseReferenceFileAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("getSolutionFile", com.remediatetheflag.global.actions.auth.user.GetAvailableExerciseSolutionFileAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.GetAvailableExerciseSolutionFileAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("getUserReservations", com.remediatetheflag.global.actions.auth.user.GetReservationsUserAction.class);

		type2action.put("isExerciseInChallenge", com.remediatetheflag.global.actions.auth.user.IsExerciseInChallengeAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.IsExerciseInChallengeAction.class, idNotNullInteger);
		type2action.put("getReservationUpdate", com.remediatetheflag.global.actions.auth.user.PollReservationUpdateAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.PollReservationUpdateAction.class, idNotNullInteger);
		type2action.put("getNotifications", com.remediatetheflag.global.actions.auth.user.GetUreadNotificationsAction.class);
		type2action.put("markNotificationRead", com.remediatetheflag.global.actions.auth.user.MarkNotificationReadAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.MarkNotificationReadAction.class, idNotNullInteger);
		type2action.put("getChallenges", com.remediatetheflag.global.actions.auth.user.GetChallengesAction.class);
		type2action.put("getChallengeDetails", com.remediatetheflag.global.actions.auth.user.GetChallengeDetailsAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.GetChallengeDetailsAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("getLeaderboard", com.remediatetheflag.global.actions.auth.user.GetTeamLeaderboardAction.class);
		type2action.put("getExercises", com.remediatetheflag.global.actions.auth.user.GetAllAvailableExercisesAction.class);
		type2action.put("getExerciseDetails", com.remediatetheflag.global.actions.auth.user.GetAvailableExerciseDetailsAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.GetAvailableExerciseDetailsAction.class, idNotNullInteger);
		type2action.put("getRegionsForExercise", com.remediatetheflag.global.actions.auth.user.GetAvailableRegionsForExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.GetAvailableRegionsForExerciseAction.class, idNotNullInteger);
		type2action.put("getHint", com.remediatetheflag.global.actions.auth.user.GetHintForFlagQuestionAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.GetHintForFlagQuestionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_ID_EXERCISE_INSTANCE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("getUserCToken", com.remediatetheflag.global.actions.auth.user.GetCSRFTokenAction.class);
		type2action.put("getUserAchievements", com.remediatetheflag.global.actions.auth.user.GetAchievementsAction.class);
		type2action.put("getUserHistory", com.remediatetheflag.global.actions.auth.user.GetExerciseHistoryAction.class);
		type2action.put("getUserHistoryDetails", com.remediatetheflag.global.actions.auth.user.GetExerciseHistoryDetailsAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.GetExerciseHistoryDetailsAction.class, idNotNullInteger);
		type2action.put("getUserHistoryDetailsFile", com.remediatetheflag.global.actions.auth.user.GetExerciseHistoryZipFileAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.GetExerciseHistoryZipFileAction.class, idNotNullInteger);
		type2action.put("getUserInfo", com.remediatetheflag.global.actions.auth.user.GetInfoUserAction.class);
		type2action.put("doLogout", com.remediatetheflag.global.actions.auth.user.DoLogoutAction.class);
		type2action.put("getRunningExercises", com.remediatetheflag.global.actions.auth.user.GetRunningExercisesAction.class);
		type2action.put("setUserInfo",com.remediatetheflag.global.actions.auth.user.UpdateUserInfoAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.UpdateUserInfoAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_FIRST_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_LAST_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_EMAIL, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorEmail.class
			}
					);
			put(Constants.ACTION_PARAM_COUNTRY, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});	
		type2action.put("setUserPassword", com.remediatetheflag.global.actions.auth.user.UpdateUserPasswordAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.UpdateUserPasswordAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_OLDPASSWORD, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_NEWPASSWORD, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("launchExerciseInstance", com.remediatetheflag.global.actions.auth.user.LaunchExerciseInstanceAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.LaunchExerciseInstanceAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("getResultStatus", com.remediatetheflag.global.actions.auth.user.GetAutomatedResultsAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.GetAutomatedResultsAction.class, idNotNullInteger);
		type2action.put("refreshGuacToken", com.remediatetheflag.global.actions.auth.user.GetGuacTokenForExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.GetGuacTokenForExerciseAction.class, idNotNullInteger);
		type2action.put("stopExerciseInstance", com.remediatetheflag.global.actions.auth.user.StopExerciseInstanceAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.StopExerciseInstanceAction.class, idNotNullInteger);
		type2action.put("addFeedback", com.remediatetheflag.global.actions.auth.user.LeaveFeedbackAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.user.LeaveFeedbackAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_FEEDBACK, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("getTeamStats", com.remediatetheflag.global.actions.auth.user.GetStatsTeamAction.class);		
		type2action.put("getMyStats", com.remediatetheflag.global.actions.auth.user.GetStatsUserAction.class);		
		csrfExclusion.add(com.remediatetheflag.global.actions.auth.user.GetCSRFTokenAction.class);
		csrfExclusion.add(com.remediatetheflag.global.actions.auth.user.UpdateUserPasswordAction.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected boolean isGranted(HttpServletRequest request, HttpServletResponse response, Class actionClass) {
		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

		if(actionClass == com.remediatetheflag.global.actions.auth.user.UpdateUserPasswordAction.class)
			return true;

		if(null!=sessionUser.getEmailVerified() && !sessionUser.getEmailVerified()){
			MessageGenerator.sendErrorMessage("VerifyEmail", response);
			return false;
		}
		if(null!=sessionUser.getForceChangePassword() && sessionUser.getForceChangePassword()){
			MessageGenerator.sendErrorMessage("ChangePassword", response);
			return false;
		}
		return true;
	}
}