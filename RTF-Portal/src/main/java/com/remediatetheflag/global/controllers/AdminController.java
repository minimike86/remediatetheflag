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

public class AdminController extends ActionsController {

	@SuppressWarnings({ "rawtypes", "serial" })
	public AdminController(){
		
		type2action.put("getInvitationCodes", com.remediatetheflag.global.actions.auth.management.admin.GetInvitationCodesAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.GetInvitationCodesAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("generateInvitation", com.remediatetheflag.global.actions.auth.management.admin.GenerateInvitationCodeAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.GenerateInvitationCodeAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_MAX_USERS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("removeChallenge", com.remediatetheflag.global.actions.auth.management.admin.RemoveChallengeAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.RemoveChallengeAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("removeInvitation", com.remediatetheflag.global.actions.auth.management.admin.RemoveInvitationCodeAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.RemoveInvitationCodeAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_ORG_CODE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("updateUser", com.remediatetheflag.global.actions.auth.management.admin.UpdateUserAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.UpdateUserAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_EMAIL, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorEmail.class
			}
					);
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_CREDITS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_COUNTRY, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_FIRST_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_LAST_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_ROLE_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_CONCURRENT_EXERCISE_LIMIT, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		
		
		type2action.put("updateExercise", com.remediatetheflag.global.actions.auth.management.rtfadmin.UpdateExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.UpdateExerciseAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TOPICS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DIFFICULTY, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TITLE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TYPE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_AUTHOR, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DESCRIPTION, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TECHNOLOGY, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DURATION, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_SCORE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_TITLE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_DESCRIPTION, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("addExercise", com.remediatetheflag.global.actions.auth.management.rtfadmin.AddExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.AddExerciseAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TOPICS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DIFFICULTY, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TITLE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TYPE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_AUTHOR, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DESCRIPTION, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TECHNOLOGY, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_DURATION, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_SCORE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_TITLE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_DESCRIPTION, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("removeUser", com.remediatetheflag.global.actions.auth.management.admin.RemoveUserAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.RemoveUserAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("removeOrganization", com.remediatetheflag.global.actions.auth.management.admin.RemoveOrganizationAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.RemoveOrganizationAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		type2action.put("removeExercise", com.remediatetheflag.global.actions.auth.management.rtfadmin.RemoveExerciseAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.RemoveExerciseAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
		}});
		
		type2action.put("addTaskDefinition", com.remediatetheflag.global.actions.auth.management.rtfadmin.AddExerciseInRegionAction.class);
		type2classValidator.put("addTaskDefinition", com.remediatetheflag.global.actions.auth.management.rtfadmin.validators.AddExerciseInRegionValidator.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.AddExerciseInRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_REGION, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TASK_DEFINITION_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_CONTAINER_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_SOFT_MEMORY, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_HARD_MEMORY, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_REPO_URL, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorURL.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorBoolean.class
			}
					);
		}});
		type2action.put("getAWSRegions", com.remediatetheflag.global.actions.auth.management.admin.GetAWSRegionsAction.class);
		type2action.put("deleteGateway", com.remediatetheflag.global.actions.auth.management.rtfadmin.RemoveSatelliteGatewayAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.RemoveSatelliteGatewayAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("getGateways", com.remediatetheflag.global.actions.auth.management.admin.GetSatelliteGatewaysAction.class);
		type2action.put("addGateway", com.remediatetheflag.global.actions.auth.management.rtfadmin.AddSatelliteGatewayAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.AddSatelliteGatewayAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_REGION, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_FQDN, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorBoolean.class
			}
					);
		}});
		type2action.put("updateGateway", com.remediatetheflag.global.actions.auth.management.rtfadmin.UpdateSatelliteGatewayAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.UpdateSatelliteGatewayAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_FQDN, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_STATUS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorBoolean.class
			}
					);
		}});
		type2action.put("getOrganizations", com.remediatetheflag.global.actions.auth.management.admin.GetOrganizationsAction.class);
		
		type2action.put("checkExerciseNameAvailable", com.remediatetheflag.global.actions.auth.management.rtfadmin.CheckExerciseNameAvailableAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.CheckExerciseNameAvailableAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		
		type2action.put("checkOrganizationNameAvailable", com.remediatetheflag.global.actions.auth.management.admin.CheckOrganizationNameAvailableAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.CheckOrganizationNameAvailableAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		
		type2action.put("removeExerciseInRegion", com.remediatetheflag.global.actions.auth.management.rtfadmin.RemoveExerciseFromRegionAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.RemoveExerciseFromRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			});
			put(Constants.ACTION_PARAM_TASK_DEFINITION_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("enableExerciseInRegion", com.remediatetheflag.global.actions.auth.management.rtfadmin.EnableExerciseInRegionAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.EnableExerciseInRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			});
			put(Constants.ACTION_PARAM_TASK_DEFINITION_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("disableExerciseInRegion", com.remediatetheflag.global.actions.auth.management.rtfadmin.DisableExerciseInRegionAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.DisableExerciseInRegionAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EXERCISE_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			});
			put(Constants.ACTION_PARAM_TASK_DEFINITION_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			});
		}});
		
		type2action.put("enableExerciseForOrg", com.remediatetheflag.global.actions.auth.management.admin.EnableAvailableExerciseForOrganizationAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.EnableAvailableExerciseForOrganizationAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_EXERCISE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("disableExerciseForOrg", com.remediatetheflag.global.actions.auth.management.admin.DisableAvailableExerciseForOrganizationAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.DisableAvailableExerciseForOrganizationAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_EXERCISE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			});
		}});
		type2action.put("updateOrganization", com.remediatetheflag.global.actions.auth.management.admin.UpdateOrganizationAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.UpdateOrganizationAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_MAX_USERS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_EMAIL, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("addOrganization", com.remediatetheflag.global.actions.auth.management.admin.AddOrganizationAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.AddOrganizationAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_MAX_USERS, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_NAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_EMAIL, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("removeTeamManager", com.remediatetheflag.global.actions.auth.management.admin.RemoveTeamManagerAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.RemoveTeamManagerAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TEAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			}
					);
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("resetUserPassword", com.remediatetheflag.global.actions.auth.management.admin.ResetUserPasswordAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.ResetUserPasswordAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_PASSWORD, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("deleteTeam", com.remediatetheflag.global.actions.auth.management.admin.RemoveTeamAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.admin.RemoveTeamAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_TEAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
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
