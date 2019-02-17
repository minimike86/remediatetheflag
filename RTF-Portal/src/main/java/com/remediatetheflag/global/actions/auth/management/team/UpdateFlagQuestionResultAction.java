package com.remediatetheflag.global.actions.auth.management.team;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import com.remediatetheflag.global.actions.IAction;
import com.remediatetheflag.global.messages.MessageGenerator;
import com.remediatetheflag.global.model.ExerciseInstance;
import com.remediatetheflag.global.model.ExerciseResult;
import com.remediatetheflag.global.model.ExerciseResultStatus;
import com.remediatetheflag.global.model.ExerciseStatus;
import com.remediatetheflag.global.model.User;
import com.remediatetheflag.global.persistence.HibernatePersistenceFacade;
import com.remediatetheflag.global.utils.Constants;
import com.remediatetheflag.global.utils.NotificationsHelper;

public class UpdateFlagQuestionResultAction extends IAction {

	private HibernatePersistenceFacade hpc = new HibernatePersistenceFacade();
	private NotificationsHelper notificationsHelper = new NotificationsHelper();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) throws Exception {

		User user = (User) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		JsonObject json = (JsonObject) request.getAttribute(Constants.REQUEST_JSON);

		Integer exerciseInstanceId = json.get(Constants.ACTION_PARAM_ID).getAsInt();
		Integer updatedScore = json.get(Constants.ACTION_PARAM_SCORE).getAsInt();
		String selfCheckName = json.get(Constants.ACTION_PARAM_NAME).getAsString();
		String updatedComment = json.get(Constants.ACTION_PARAM_COMMENT).getAsString();
		Integer updatedStatus = json.get(Constants.ACTION_PARAM_STATUS).getAsInt();

		ExerciseInstance instance = hpc.getManagementExerciseInstance(exerciseInstanceId,user.getManagedOrganizations());
		if(null==instance){
			MessageGenerator.sendErrorMessage("NOT_FOUND", response);
			return;
		}
		if(user.getRole().equals(Constants.ROLE_TEAM_MANAGER)){
			List<User> users = hpc.getUsersInTeamManagedBy(user);
			if(!users.contains(instance.getUser())){
				MessageGenerator.sendErrorMessage("NotFound", response);
				return;
			}
		}
		Integer previousScore = instance.getScore().getResult();
		Integer updatedTotalScore = 0;
		for(ExerciseResult res : instance.getResults()) {
			if(res.getName().equals(selfCheckName)) {
				res.setComment(updatedComment);
				res.setLastChange(new Date());
				res.setVerified(true);
				res.setStatus(ExerciseResultStatus.getStatusFromStatusCode(updatedStatus));
				res.setScore(updatedScore);
			}
			updatedTotalScore += res.getScore();
		}
		instance.getScore().setResult(updatedTotalScore);
		if(!updatedTotalScore.equals(previousScore)) {
			instance.getUser().setScore(instance.getUser().getScore() - previousScore + instance.getScore().getTotal());
		}
		notificationsHelper.addUpdateReviewNotification(instance.getUser(), instance);

		instance.setStatus(ExerciseStatus.REVIEWED_MODIFIED);
		hpc.updateExerciseInstance(instance);
		MessageGenerator.sendSuccessMessage(response);
		return;
	}
}