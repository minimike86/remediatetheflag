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
package com.remediatetheflag.portal.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.remediatetheflag.portal.messages.MessageGenerator;
import com.remediatetheflag.portal.model.User;
import com.remediatetheflag.portal.utils.Constants;

public class ManagementController extends ActionsController {

	@SuppressWarnings({ "rawtypes", "serial" })
	public ManagementController(){
		
		type2action.put("getInvitationCodes", com.remediatetheflag.portal.auth.management.GetInvitationCodesAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetInvitationCodesAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("generateInvitation", com.remediatetheflag.portal.auth.management.GenerateInvitationCodeAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GenerateInvitationCodeAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_MAX_USERS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("removeInvitation", com.remediatetheflag.portal.auth.management.RemoveInvitationCodeAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveInvitationCodeAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_ORG_CODE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("updateUser", com.remediatetheflag.portal.auth.management.UpdateUserAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.UpdateUserAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_EMAIL, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorEmail.class
			}
					);
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_CREDITS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_COUNTRY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_FIRST_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_LAST_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_ROLE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_CONCURRENT_EXERCISE_LIMIT, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		
		
		type2action.put("updateExercise", com.remediatetheflag.portal.auth.management.UpdateExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.UpdateExerciseAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TOPICS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DIFFICULTY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TITLE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TYPE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_AUTHOR, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DESCRIPTION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TECHNOLOGY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DURATION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_SCORE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_TITLE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_DESCRIPTION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("addExercise", com.remediatetheflag.portal.auth.management.AddExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.AddExerciseAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TOPICS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DIFFICULTY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TITLE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TYPE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_AUTHOR, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DESCRIPTION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TECHNOLOGY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DURATION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_SCORE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_TITLE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_DESCRIPTION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("removeUser", com.remediatetheflag.portal.auth.management.RemoveUserAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveUserAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("removeOrganization", com.remediatetheflag.portal.auth.management.RemoveOrganizationAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveOrganizationAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("removeExercise", com.remediatetheflag.portal.auth.management.RemoveExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveExerciseAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		
		type2action.put("addTaskDefinition", com.remediatetheflag.portal.auth.management.AddExerciseInRegionAction.class);
		type2classValidator.put("addTaskDefinition", com.remediatetheflag.portal.auth.management.validators.AddExerciseInRegionValidator.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.AddExerciseInRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_REGION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TASK_DEFINITION_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_CONTAINER_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_SOFT_MEMORY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_HARD_MEMORY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_REPO_URL, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorURL.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorBoolean.class
			}
					);
		}});
		type2action.put("getAWSRegions", com.remediatetheflag.portal.auth.management.GetAWSRegionsAction.class);
		type2action.put("deleteGateway", com.remediatetheflag.portal.auth.management.RemoveSatelliteGatewayAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveSatelliteGatewayAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("getGateways", com.remediatetheflag.portal.auth.management.GetSatelliteGatewaysAction.class);
		type2action.put("addGateway", com.remediatetheflag.portal.auth.management.AddSatelliteGatewayAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.AddSatelliteGatewayAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_REGION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_FQDN, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorBoolean.class
			}
					);
		}});
		type2action.put("updateGateway", com.remediatetheflag.portal.auth.management.UpdateSatelliteGatewayAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.UpdateSatelliteGatewayAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_FQDN, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorBoolean.class
			}
					);
		}});
		type2action.put("getOrganizations", com.remediatetheflag.portal.auth.management.GetOrganizationsAction.class);
		
		type2action.put("checkExerciseNameAvailable", com.remediatetheflag.portal.auth.management.CheckExerciseNameAvailableAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.CheckExerciseNameAvailableAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		
		type2action.put("checkOrganizationNameAvailable", com.remediatetheflag.portal.auth.management.CheckOrganizationNameAvailableAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.CheckOrganizationNameAvailableAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		
		type2action.put("removeExerciseInRegion", com.remediatetheflag.portal.auth.management.RemoveExerciseFromRegionAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveExerciseFromRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
			put(Constants.ACTION_PARAM_TASK_DEFINITION_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("enableExerciseInRegion", com.remediatetheflag.portal.auth.management.EnableExerciseInRegionAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.EnableExerciseInRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
			put(Constants.ACTION_PARAM_TASK_DEFINITION_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("disableExerciseInRegion", com.remediatetheflag.portal.auth.management.DisableExerciseInRegionAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.DisableExerciseInRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
			put(Constants.ACTION_PARAM_TASK_DEFINITION_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
		}});
		
		type2action.put("enableExerciseForOrg", com.remediatetheflag.portal.auth.management.EnableAvailableExerciseForOrganizationAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.EnableAvailableExerciseForOrganizationAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_EXERCISE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("disableExerciseForOrg", com.remediatetheflag.portal.auth.management.DisableAvailableExerciseForOrganizationAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.DisableAvailableExerciseForOrganizationAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_EXERCISE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("updateOrganization", com.remediatetheflag.portal.auth.management.UpdateOrganizationAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.UpdateOrganizationAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_MAX_USERS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_EMAIL, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("addOrganization", com.remediatetheflag.portal.auth.management.AddOrganizationAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.AddOrganizationAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_MAX_USERS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_EMAIL, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("removeTeamManager", com.remediatetheflag.portal.auth.management.RemoveTeamManagerAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveTeamManagerAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TEAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("resetUserPassword", com.remediatetheflag.portal.auth.management.ResetUserPasswordAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.ResetUserPasswordAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_PASSWORD, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("deleteTeam", com.remediatetheflag.portal.auth.management.RemoveTeamAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveTeamAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TEAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("getChallenges", com.remediatetheflag.portal.auth.management.GetChallengesAction.class);
		type2action.put("getChallengeDetails", com.remediatetheflag.portal.auth.management.GetChallengeDetailsAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetChallengeDetailsAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("sendNotification", com.remediatetheflag.portal.auth.management.SendNotificationToUser.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.SendNotificationToUser.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_NOTIFICATION_TEXT, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("getGlobalStats", com.remediatetheflag.portal.auth.management.GetStatsGlobalAction.class);		
		type2action.put("getTeamDetails", com.remediatetheflag.portal.auth.management.GetTeamDetails.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetTeamDetails.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("getUserStats", com.remediatetheflag.portal.auth.management.GetStatsUserAction.class);		
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetStatsUserAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("getUserAchievements", com.remediatetheflag.portal.auth.management.GetUserAchievementsAction.class);		
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetUserAchievementsAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("getUserExercises", com.remediatetheflag.portal.auth.management.GetUserCompletedExercisesAction.class);		
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetUserCompletedExercisesAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("getTeamMembers", com.remediatetheflag.portal.auth.management.GetTeamMembersAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetTeamMembersAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("getTeams", com.remediatetheflag.portal.auth.management.GetTeamsAction.class);
		type2action.put("getTeamStats", com.remediatetheflag.portal.auth.management.GetStatsTeamAction.class);		
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetStatsTeamAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("getUserDetails", com.remediatetheflag.portal.auth.management.GetUserDetailsAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetUserDetailsAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});	
		type2action.put("getUsers", com.remediatetheflag.portal.auth.management.GetUsersAction.class);
		type2action.put("checkTeamNameAvailable", com.remediatetheflag.portal.auth.management.CheckTeamNameAvailable.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.CheckTeamNameAvailable.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		
		type2action.put("unlockUserAccount", com.remediatetheflag.portal.auth.management.UnlockUserAccountAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.UnlockUserAccountAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class,
			}
					);
		}});
		type2action.put("addUser", com.remediatetheflag.portal.auth.management.AddUserAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.AddUserAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_EMAIL, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorEmail.class
			}
					);
			put(Constants.ACTION_PARAM_PASSWORD, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_CREDITS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_COUNTRY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_FIRST_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_LAST_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_ROLE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_CONCURRENT_EXERCISE_LIMIT, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_FORCE_PASSWORD_CHANGE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorBoolean.class
			}
					);
		}});
		type2action.put("addTeam", com.remediatetheflag.portal.auth.management.AddTeamAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.AddTeamAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TEAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class,
			}
					);
			put(Constants.ACTION_PARAM_ORG_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("updateExercise", com.remediatetheflag.portal.auth.management.UpdateExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.UpdateExerciseAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TOPICS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DIFFICULTY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TITLE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TYPE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_AUTHOR, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DESCRIPTION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TECHNOLOGY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DURATION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_SCORE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_TITLE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_DESCRIPTION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("addExercise", com.remediatetheflag.portal.auth.management.AddExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.AddExerciseAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TOPICS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DIFFICULTY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TITLE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TYPE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_AUTHOR, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DESCRIPTION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TECHNOLOGY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DURATION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_SCORE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_TITLE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_DESCRIPTION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("removeExercise", com.remediatetheflag.portal.auth.management.RemoveExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveExerciseAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("addTaskDefinition", com.remediatetheflag.portal.auth.management.AddExerciseInRegionAction.class);
		type2classValidator.put("addTaskDefinition", com.remediatetheflag.portal.auth.management.validators.AddExerciseInRegionValidator.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.AddExerciseInRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_REGION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TASK_DEFINITION_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_CONTAINER_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_SOFT_MEMORY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_HARD_MEMORY, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_REPO_URL, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorURL.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorBoolean.class
			}
					);
		}});
		type2action.put("deleteGateway", com.remediatetheflag.portal.auth.management.RemoveSatelliteGatewayAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveSatelliteGatewayAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("addGateway", com.remediatetheflag.portal.auth.management.AddSatelliteGatewayAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.AddSatelliteGatewayAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_REGION, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_FQDN, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorBoolean.class
			}
					);
		}});
		type2action.put("updateGateway", com.remediatetheflag.portal.auth.management.UpdateSatelliteGatewayAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.UpdateSatelliteGatewayAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_FQDN, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorBoolean.class
			}
					);
		}});
		type2action.put("checkExerciseNameAvailable", com.remediatetheflag.portal.auth.management.CheckExerciseNameAvailableAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.CheckExerciseNameAvailableAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("removeExerciseInRegion", com.remediatetheflag.portal.auth.management.RemoveExerciseFromRegionAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveExerciseFromRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
			put(Constants.ACTION_PARAM_TASK_DEFINITION_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("enableExerciseInRegion", com.remediatetheflag.portal.auth.management.EnableExerciseInRegionAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.EnableExerciseInRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
			put(Constants.ACTION_PARAM_TASK_DEFINITION_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("disableExerciseInRegion", com.remediatetheflag.portal.auth.management.DisableExerciseInRegionAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.DisableExerciseInRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
			put(Constants.ACTION_PARAM_TASK_DEFINITION_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			});
		}});
		HashMap<String, Class[]> idNotNullInteger = new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}};
		
		type2action.put("getAllRunningExercises", com.remediatetheflag.portal.auth.management.GetRunningExercisesAction.class);

		type2action.put("getExerciseDetails", com.remediatetheflag.portal.auth.management.GetAvailableExerciseDetailsAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetAvailableExerciseDetailsAction.class, idNotNullInteger);
		type2action.put("getRegionsForExercise", com.remediatetheflag.portal.auth.management.GetAvailableRegionsForExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetAvailableRegionsForExerciseAction.class, idNotNullInteger);
		type2action.put("getExercises", com.remediatetheflag.portal.auth.management.GetAllAvailableExercisesAction.class);
		type2action.put("getReferenceFile", com.remediatetheflag.portal.auth.management.GetAvailableExerciseReferenceFileAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.actions.auth.user.GetAvailableExerciseReferenceFileAction.class, idNotNullInteger);
		type2action.put("getSolutionFile", com.remediatetheflag.portal.auth.management.GetAvailableExerciseSolutionFileAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.actions.auth.user.GetAvailableExerciseSolutionFileAction.class, idNotNullInteger);
		type2action.put("addUsersToTeam", com.remediatetheflag.portal.auth.management.AddUsersToTeamAction.class);
		
		type2action.put("getPendingReviews", com.remediatetheflag.portal.auth.management.GetPendingReviewsAction.class);
		type2action.put("getCompletedReviews", com.remediatetheflag.portal.auth.management.GetCompletedExercisesAction.class);
		type2action.put("getAvailableUsersForTeam", com.remediatetheflag.portal.auth.management.GetUsersWithoutTeamAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetUsersWithoutTeamAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TEAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("renameTeam", com.remediatetheflag.portal.auth.management.RenameTeamAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RenameTeamAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TEAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("addTeamManager", com.remediatetheflag.portal.auth.management.MakeUserTeamManagerAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.MakeUserTeamManagerAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TEAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("removeFromTeam", com.remediatetheflag.portal.auth.management.RemoveUserFromTeamAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.RemoveUserFromTeamAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TEAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("getReviewDetails", com.remediatetheflag.portal.auth.management.GetReviewDetailsAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetReviewDetailsAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("getUserFeedback", com.remediatetheflag.portal.auth.management.GetUserFeedbackForExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetUserFeedbackForExerciseAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("getReviewFile", com.remediatetheflag.portal.auth.management.GetReviewFileAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.GetReviewFileAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("postReview", com.remediatetheflag.portal.auth.management.PostReviewAction.class);
		type2action.put("markAsCancelled", com.remediatetheflag.portal.auth.management.MarkExerciseAsCancelledAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.auth.management.MarkExerciseAsCancelledAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorNotNull.class,
					com.remediatetheflag.portal.actions.validators.ValidatorInteger.class
			}
					);
		}});
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected boolean isGranted(HttpServletRequest request, HttpServletResponse response, Class actionClass) {

		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

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
