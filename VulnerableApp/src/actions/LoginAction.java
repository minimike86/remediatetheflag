package actions;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import messages.MessageGenerator;
import model.Constants;
import model.SessionUser;
import persistence.PersistenceController;


/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * Action to perform authentication.
 */
public class LoginAction implements IAction  {

	private PersistenceController persistenceController = PersistenceController.getInstance();
	private MessageGenerator messageGenerator = MessageGenerator.getInstance();

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) {

		String pwd = request.getParameter("j_password");
		String usr = request.getParameter("j_username");
		SessionUser user = persistenceController.loginUser(usr, pwd);
		String msg = "";
		try {
			if(user!=null) { // login is succssfull 
				request.getSession().setAttribute(Constants.CSRF_TOKEN, ""); // not used			
				request.getSession().setAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT, user); // set session data
				request.getSession().setAttribute(Constants.ATTRIBUTE_SECURITY_ROLE, Constants.ROLE_USER); // set user role
				msg = messageGenerator.redirectMessage("/logged/home.jsp?user=User&url=/logged/home.html%23profile"); // send JSON redirect
			} else {
				msg = messageGenerator.redirectMessage("/index.html");	 // send JSON redirect for login failure
			}
			PrintWriter out;
			out = response.getWriter();
			out.print(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}