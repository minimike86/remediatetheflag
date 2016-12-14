package actions;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Constants;
import model.SessionUser;
import utils.ConfigurationUtil;
import utils.OSDetection;

/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * This class implements two flows:
 * 1) Directory listing: returns a JSON message with the files stored in the user's directory.
 * 2) File read: returns the contents of the specified file.
 */
public class GetDocumentsAction implements IAction {

	private static Logger logger = LoggerFactory.getLogger(GetDocumentsAction.class);

	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = (JSONObject) request.getAttribute(Constants.ATTRIBUTE_JSON);

		if (json != null) {	
			//current session user
			SessionUser currentUser = (SessionUser) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

			String path = "", msg = "", file = "";
			String kind = (String) json.get("kind");

			if(kind.equals("check")){ // directory listing
				path = ConfigurationUtil.getUsersDataPath()+File.separator+currentUser.getIdUser();
				File folder = new File(path);    
				for (final File fileEntry : folder.listFiles()) {
					if (!fileEntry.isDirectory() && !fileEntry.isHidden()) {
						msg += fileEntry.getName()+"\n";
					} 
				}
			}
			else if(kind.equals("open")){ // file read
				file = (String) json.get("filename");
				path = ConfigurationUtil.getUsersDataPath()+File.separator+currentUser.getIdUser()+File.separator+file;
				try {
					msg = readFile(path);
					if(msg.contains("No such file or directory")){
						msg = "File not found";
					}
				} catch (Exception e) {
					e.printStackTrace();
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

	@SuppressWarnings("resource")
	public String readFile(String path) throws Exception{
		StringBuilder strOut = new StringBuilder();
		try {
			String[] command;
			if(OSDetection.getOperatingSystemType().equals(OSDetection.OSType.Windows))
				command = new String[]{"cmd /c", "type", path};
			else{
				command = new String[]{"sh", "-c", "cat " + path};
			}
			logger.debug("Executing OS command "+Arrays.toString(command));

			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);
			int result = proc.waitFor();
			if (result != 0) {
				System.out.println("process error: " + result);
			}
			InputStream in = (result == 0) ? proc.getInputStream() : proc.getErrorStream();
			int c;
			while ((c = in.read()) != -1) {
				strOut.append((char) c);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return strOut.toString();	
	}
}
