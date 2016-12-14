package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import messages.MessageGenerator;
import model.Constants;


/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * Retrieves the anti-CSRF token stored in the user's session (per-session token).
 * This is not currently used.
 */
public class GetCSRFTokenAction implements IAction {

	private MessageGenerator messageGenerator = MessageGenerator.getInstance();
	
	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) {

		// retrieve token from user's session, the token is created at login and stored in the user's session
		String token = (String) request.getSession().getAttribute(Constants.CSRF_TOKEN);
		PrintWriter out;
		try { // generates JSON message
			out = response.getWriter();
			out.write(messageGenerator.csrfToken(token));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
}
