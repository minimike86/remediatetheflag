package com.remediatetheflag.global.actions.auth.management.team;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;

public class GetRunningExercisesAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		User sessionUser = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		List<ExerciseInstance> runningExercises = hpc.getAllRunningExerciseInstances(sessionUser.getManagedOrganizations());
		
		if(sessionUser.getRole().equals(Constants.ROLE_TEAM_MANAGER)){
			List<User> users = hpc.getUsersInTeamManagedBy(sessionUser);
			users.add(sessionUser);
			LinkedList<ExerciseInstance> teamEx = new LinkedList<ExerciseInstance>();
			for(ExerciseInstance instance : runningExercises) {
				if(users.contains(instance.getUser())){
					teamEx.add(instance);
				}
			}
			MessageGenerator.sendRunningExercisesMessage(teamEx,response);
			return;
		}
		MessageGenerator.sendRunningExercisesMessage(runningExercises,response);
	}

}
