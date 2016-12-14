package persistence;

import java.util.List;

import model.MysqlUser;
import model.SessionUser;
import model.UserFeedback;
import model.UserProfileInfo;
import persistence.dao.FeedbackDAO;
import persistence.dao.UserDAO;

public class PersistenceController {

	private FeedbackDAO feedbackDAO = new FeedbackDAO();
	private UserDAO userDAO = new UserDAO();
	private static PersistenceController persistenceInstance = new PersistenceController();

	public static PersistenceController getInstance(){
		return persistenceInstance;
	}

	public SessionUser loginUser(String username, String password) {

		MysqlUser user;
		try {
			user = userDAO.loginUser(username, password);
			if(user != null) {	
				SessionUser sessionUser = new SessionUser();
				sessionUser.setIdUser(user.getIdUser());
				sessionUser.setEmail(user.getEmail());
				sessionUser.setUsername(user.getUsername());
				return sessionUser;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public MysqlUser getUser(Integer idUser) {
		try{
		return userDAO.getUser(idUser);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
	}

	public void changePassword(Integer idUser, String newPassword){
		try{
		 userDAO.changePassword(idUser, newPassword);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	public void changeEmail(Integer idUser, String newEmail){
		try{
		 userDAO.changeEmail(idUser, newEmail);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	public UserProfileInfo getUserProfileInfo(Integer idUser) {
		try{
		return userDAO.getUserProfileInfo(idUser);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
	}
	public void changeUserProfileInfo(Integer idUser, String param, String newValue) {
		try{
		userDAO.changeUserInfo(idUser, param, newValue);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	public void changeUserProfileInfo(Integer idUser, UserProfileInfo userProfile) {
		try{
		userDAO.changeUserProfileInfo(idUser, userProfile);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	public void storeFeedback(Integer idUser, String type, String message) {
		try{
		feedbackDAO.storeNewFeedback(idUser,type,message);
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	public List<UserFeedback> getAllFeedbacks(){
		try{
		return feedbackDAO.getAllFeedbacks();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
	}
/*	public List<UserFeedback> getMyFeedbacks(Integer idUser) {
		try{
			return feedbackDAO.getFeedbacksForUser(idUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	public List<UserFeedback> getFeedbacksFor(String whoFor) {
		try {
			return feedbackDAO.getFeedbacksFor(whoFor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void removeFeedback(Integer idUser){
		try{
			feedbackDAO.removeFeedback(idUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer idUserFromUsername(String username) {
		try{
			return userDAO.usernameToIdUser(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}