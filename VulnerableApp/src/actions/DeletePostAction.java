package actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Constants;
import model.SessionUser;
import utils.ConfigurationUtil;

/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * Deletes a user's XML post.
 */
public class DeletePostAction implements IAction {

	private static Logger logger = LoggerFactory.getLogger(DeletePostAction.class);

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) {
		String msg = Constants.FAILURE;
		JSONObject json = (JSONObject) request.getAttribute(Constants.ATTRIBUTE_JSON);

		if (json != null) {	
			SessionUser currentUser = (SessionUser) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

			// get the file's title
			String title = (String) json.get(Constants.JSON_FILE);
			// get file from user's directory
			File file = new File(ConfigurationUtil.getUsersXMLPath()+File.separator+currentUser.getIdUser()+File.separator+title+".xml");
			
			logger.debug("User "+currentUser.getUsername()+" deleting file "+file.getAbsolutePath());
			
			if(file.exists()){
				file.delete();
				msg = Constants.SUCCESS;
			}
		}
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
