package actions;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import model.Constants;
import model.SessionUser;
import utils.ConfigurationUtil;

public class GetPostsAction implements IAction {

	private static Logger logger = LoggerFactory.getLogger(GetPostsAction.class);

	/**
	 * @author Andrea Scaduto <andreascaduto@me.com>
	 * This class implements two flows:
	 * 1) Directory listing: returns a JSON message with the Posts (XML files) stored in the user's directory.
	 * 2) Posts read: returns the contents of the specified Post (XML file).
	 */
	@Override
	public void doAction(HttpServletRequest request, HttpServletResponse response) {

		JSONObject json = (JSONObject) request.getAttribute(Constants.ATTRIBUTE_JSON);

		if (json != null) {	
			//current session user
			SessionUser currentUser = (SessionUser) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);

			String path = "", msg = "", file = "";
			String kind = (String) json.get("kind");

			if(kind.equals("list")){  // directory listing
				path = ConfigurationUtil.getUsersXMLPath()+File.separator+currentUser.getIdUser();
				File folder = new File(path);    
				for (final File fileEntry : folder.listFiles()) {
				        if (!fileEntry.isDirectory() && !fileEntry.isHidden()) {
				        	msg += fileEntry.getName()+"\n"; // create a list of file names separated by a new line
				        } 
				}
			}
			else if(kind.equals("open")){
				file = (String) json.get(Constants.JSON_FILE); // get file name
				msg = readXMLFile(ConfigurationUtil.getUsersXMLPath()+File.separator+currentUser.getIdUser()+File.separator+file);
			}
			logger.debug("Read XML file: "+file+" Contents: "+msg);
			
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

	@SuppressWarnings("unchecked")
	private String readXMLFile(String path){
		
		JSONObject jsn = new JSONObject();
		try {
			File fXmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("Post");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					jsn.put("title", eElement.getElementsByTagName("title").item(0).getTextContent());
					jsn.put("content", eElement.getElementsByTagName("content").item(0).getTextContent());
					jsn.put("timestamp", eElement.getElementsByTagName("timestamp").item(0).getTextContent());						
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsn.toJSONString();
	}
}

