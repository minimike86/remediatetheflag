package actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * Interface implemented by all actions.
 */
public interface IAction {

	public void doAction(HttpServletRequest request, HttpServletResponse response );
	
}
