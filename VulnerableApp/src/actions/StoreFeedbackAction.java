package actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import model.Constants;
import model.SessionUser;
import persistence.PersistenceController;


/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * Stores a User's Feedback
 */
public class StoreFeedbackAction implements IAction {

	private PersistenceController persistenceController = PersistenceController.getInstance();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) {

		JSONObject json = (JSONObject) request.getAttribute(Constants.ATTRIBUTE_JSON);

		if (null != json) {	
			// get current user
			SessionUser currentUser = (SessionUser) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

			Integer idUser = currentUser.getIdUser(); // get user id
			String type = ""; // not used
			String message = (String) json.get(Constants.JSON_FEEDBACK_MESSAGE); // get message to be stored
			
			persistenceController.storeFeedback(idUser,type,message);
		}

	}
}
