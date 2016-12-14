package handlers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Constants;
import model.SessionUser;
import utils.ConfigurationUtil;


/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * This servlet receives uploaded files and stores them in the user's directory.
 */
public class UploadHandler extends HttpServlet {

	private static final long serialVersionUID = -8692843440999053947L;
	private static Logger logger = LoggerFactory.getLogger(UploadHandler.class);

	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		
		// get user
		SessionUser currentUser = (SessionUser) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		// get path
		final String path = ConfigurationUtil.getUsersDataPath()+File.separator+currentUser.getIdUser();
		
		final Part filePart = request.getPart("0");
		final String fileName = getFileName(filePart);
		OutputStream out = null;
		InputStream filecontent = null;
		final PrintWriter writer = response.getWriter();

		try {
			// store file
			logger.debug("User "+currentUser.getUsername()+" is uploading " + fileName + " to " + path);
			out = new FileOutputStream(new File(path + File.separator + fileName));
			filecontent = filePart.getInputStream();

			int read = 0;
			final byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			writer.println("New file " + fileName + " created at " + path);
			
		} catch (FileNotFoundException fne) {
			writer.println("You either did not specify a file to upload or are "
					+ "trying to upload a file to a protected or nonexistent "
					+ "location.");
			writer.println("<br/> ERROR: " + fne.getMessage());
		
		} finally {
			if (out != null) {
				out.close();
			}
			if (filecontent != null) {
				filecontent.close();
			}
			if (writer != null) {
				writer.close();
			}
		}
	}

	private String getFileName(final Part part) {
		for (String content : part.getHeader("Content-Disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(
						content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
