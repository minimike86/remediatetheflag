package com.remediatetheflag.global.controllers;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.utils.Constants;

public class RTFAdminController extends ActionsController {

	@SuppressWarnings({ "rawtypes", "serial" })
	public RTFAdminController(){
		
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
			put(Constants.ACTION_PARAM_TROPHY_TITLE, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_TROPHY_DESCRIPTION, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorStringNotEmpty.class
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
		type2action.put("deleteGateway", com.remediatetheflag.global.actions.auth.management.rtfadmin.RemoveSatelliteGatewayAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.RemoveSatelliteGatewayAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ID, new Class[]{
					com.remediatetheflag.global.actions.validators.ValidatorInteger.class
			});
		}});
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
		type2action.put("checkExerciseNameAvailable", com.remediatetheflag.global.actions.auth.management.rtfadmin.CheckExerciseNameAvailableAction.class);
		type2fieldValidator.put(com.remediatetheflag.global.actions.auth.management.rtfadmin.CheckExerciseNameAvailableAction.class, new HashMap<String, Class[]>() {{
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