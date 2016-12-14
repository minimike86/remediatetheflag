package actions;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

import messages.MessageGenerator;
import model.Constants;
import model.UserFeedback;
import persistence.PersistenceController;

/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * This class implements two flows:
 * 1) return all feedback
 * 2) filter feedback
 */
public class GetFeedbackAction implements IAction {

	private PersistenceController persistenceController = PersistenceController.getInstance();
	private MessageGenerator messageGenerator = MessageGenerator.getInstance();

	@Override
	public void doAction(HttpServletRequest request,HttpServletResponse response) {

		JSONObject json = (JSONObject) request.getAttribute(Constants.ATTRIBUTE_JSON);
		if (json != null) {
			String kind = (String) json.get("kind");
			List<UserFeedback> feedback = null;

			if(kind.equals("all")){  // return all feedback
				feedback = persistenceController.getAllFeedbacks();
			}
			else if(kind.equals("search")){ // filter by username
				String filter = (String) json.get(Constants.JSON_USR);
				feedback = persistenceController.getFeedbacksFor(filter);
			}
			PrintWriter out;
			try { // generate JSON message
				out = response.getWriter();
				out.write(messageGenerator.feedbackListMessage(feedback)); 
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}

}