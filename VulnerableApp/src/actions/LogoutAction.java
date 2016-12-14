package actions;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * Action to perform logout. 
 */
public class LogoutAction implements IAction{

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) {
		Cookie cookie = new Cookie("JSESSIONID","LOGOFF");
		response.addCookie(cookie);
		try {
			response.sendRedirect("/index.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
