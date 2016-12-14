package actions;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import messages.MessageGenerator;
import model.Constants;

/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * This class is used to return a JSON message with input validation errors to the user.
 * This is not currently used.
 */
public class InputErrorAction implements IAction{
	
	private MessageGenerator messageGenerator = MessageGenerator.getInstance();
	
	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) {
		@SuppressWarnings("unchecked")
		List<String> errors = (List<String>) request.getAttribute(Constants.ATTRIBUTE_VALIDATION_ERRORS);
		
		PrintWriter out;
		if(errors != null && !errors.isEmpty()){
			try {
				out = response.getWriter();
				out.write(messageGenerator.inputErrorsMessage(errors));
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
