package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import messages.MessageGenerator;
import model.Constants;
import model.SessionUser;
import model.UserProfileInfo;
import persistence.PersistenceController;


/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * Updates user's profile
 */
public class StoreProfileInfoAction implements IAction {

	private PersistenceController persistenceController = PersistenceController.getInstance();
	private MessageGenerator messageGenerator = MessageGenerator.getInstance();
	private static Logger logger = LoggerFactory.getLogger(GetPostsAction.class);

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) {

		JSONObject json = (JSONObject) request.getAttribute(Constants.ATTRIBUTE_JSON);

		if (json != null ) {	
			// get current user
			SessionUser currentUser = (SessionUser) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

			// get username
			String username = (String) json.get(Constants.JSON_USERNAME);
			
			// get id user from username
			Integer idUser = persistenceController.idUserFromUsername(username);
			
			// get password, city, country, email and work
			String password = (String) json.get(Constants.JSON_PASSWORD);
			String city = (String) json.get(Constants.JSON_CITY);
			String country = (String) json.get(Constants.JSON_COUNTRY);
			String email = (String) json.get(Constants.JSON_EMAIL);
			String work = (String) json.get(Constants.JSON_WORK);
			
			// build user profile object
			UserProfileInfo userProfile = new UserProfileInfo();
			userProfile.setCity(city);
			userProfile.setCountry(country);
			userProfile.setProfession(work);

			// update user's profile
			persistenceController.changeUserProfileInfo(idUser, userProfile);
			// update email
			persistenceController.changeEmail(idUser, email);
			// update password if not null
			if(null!=password && !password.equals("")){
				persistenceController.changePassword(idUser, password);
			}
			// update session
			logger.debug("User "+currentUser.getUsername()+" updated profile for user "+username);
			currentUser.setEmail(email);
			request.getSession().setAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT, currentUser);
			PrintWriter out;
			try {
				out = response.getWriter();
				// generate update result message
				out.print(messageGenerator.profileInfoHasChanged());
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}