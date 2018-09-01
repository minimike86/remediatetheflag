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

import com.remediatetheflag.portal.utils.Constants;

public class AnonymousController extends ActionsController {

	@SuppressWarnings({ "serial", "rawtypes" })
	public AnonymousController() {		
		type2action.put("doLogin", com.remediatetheflag.portal.actions.unauth.DoLoginAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.actions.unauth.DoLoginAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class,
					
			}
					);
			put(Constants.ACTION_PARAM_PASSWORD, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("isInviteCodeValid", com.remediatetheflag.portal.actions.unauth.ValidateOrganizationInvitationCodeAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.actions.unauth.ValidateOrganizationInvitationCodeAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_ORG_CODE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class,
					
			}
					);
		}});
		type2action.put("isUsernameAvailable", com.remediatetheflag.portal.actions.unauth.CheckUsernameAvailableAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.actions.unauth.CheckUsernameAvailableAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class,
					
			}
					);
		}});

		type2action.put("doSignup", com.remediatetheflag.portal.actions.unauth.DoSignupUserAction.class);
		type2fieldValidator.put(com.remediatetheflag.portal.actions.unauth.DoSignupUserAction.class, new HashMap<String, Class[]>() {{
			put(Constants.ACTION_PARAM_EMAIL, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorEmail.class
			}
					);
			put(Constants.ACTION_PARAM_PASSWORD, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
			put(Constants.ACTION_PARAM_ORG_CODE, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
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
			put(Constants.ACTION_PARAM_USERNAME, new Class[]{
					com.remediatetheflag.portal.actions.validators.ValidatorStringNotEmpty.class
			}
					);
		}});
		type2action.put("getCountries", com.remediatetheflag.portal.actions.unauth.GetAllCountriesAction.class);
		csrfExclusion.add(com.remediatetheflag.portal.actions.unauth.CheckUsernameAvailableAction.class);
		csrfExclusion.add(com.remediatetheflag.portal.actions.unauth.ValidateOrganizationInvitationCodeAction.class);
		csrfExclusion.add(com.remediatetheflag.portal.actions.unauth.DoLoginAction.class);
		csrfExclusion.add(com.remediatetheflag.portal.actions.unauth.DoSignupUserAction.class);
		csrfExclusion.add(com.remediatetheflag.portal.actions.unauth.GetAllCountriesAction.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected boolean isGranted(HttpServletRequest request, HttpServletResponse response, Class actionClass) {
		return true;
	}
}
