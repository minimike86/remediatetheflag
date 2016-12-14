package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import messages.MessageGenerator;
import model.Constants;
import model.SessionUser;
import model.UserProfileInfo;

import org.json.simple.JSONObject;

import persistence.PersistenceController;


/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * Returns a JSON message with the session user profile.
 */
public class GetProfileInfoAction implements IAction {

	private PersistenceController persistenceController = PersistenceController.getInstance();
	private MessageGenerator messageGenerator = MessageGenerator.getInstance();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) {

		JSONObject json = (JSONObject) request.getAttribute(Constants.ATTRIBUTE_JSON);
		UserProfileInfo userProfile = null;
		String msg = null;

		if (json != null) {	

			// get current session user
			SessionUser currentUser = (SessionUser) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
			// get profile info from DB
			userProfile = persistenceController.getUserProfileInfo(currentUser.getIdUser());
			// create JSON message
			msg = messageGenerator.userProfileInfoMessage(userProfile, currentUser);

			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(msg);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}