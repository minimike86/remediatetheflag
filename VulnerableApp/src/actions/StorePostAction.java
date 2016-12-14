package actions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.JSONObject;
import org.xml.sax.SAXException;

import model.Constants;
import model.SessionUser;
import utils.ConfigurationUtil;


/**
 * @author Andrea Scaduto <andreascaduto@me.com>
 * Stores a new user's post (XML file) in the user's folder.
 */
public class StorePostAction implements IAction {

	public void doAction(HttpServletRequest request, HttpServletResponse response) {

		String msg = "";
		JSONObject json = (JSONObject) request.getAttribute(Constants.ATTRIBUTE_JSON);

		if (null==json)
			return;

		// get current user
		SessionUser currentUser = (SessionUser) request.getSession().getAttribute(Constants.ATTRIBUTE_SECURITY_CONTEXT);
		//get xml data
		String xml = (String) json.get("xml");
		//get file title
		String title = (String) json.get("title");

		// validates XML
		if(isValidXML(xml)){
			Writer writer = null;
			try { // if the XML is valid, it's written in the user's folder.
				writer = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(ConfigurationUtil.getUsersXMLPath()+File.separator+currentUser.getIdUser()+File.separator+title+".xml"), "utf-8"));
				writer.write(xml);
			} catch (IOException ex) {
			} finally {
				try {
					writer.close();
				} catch (Exception ex) {}
			}
			msg = Constants.SUCCESS;
		}
		else{
			msg = Constants.FAILURE;
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

	// parses an XML file, if no exceptions are thrown it is valid. File is then removed.
	private boolean isValidXML(String data) {

		String ts = System.currentTimeMillis()+"";
		// write to file
		try(  PrintWriter out = new PrintWriter(ConfigurationUtil.getUsersXMLPath()+File.separator+ts+".xml")  ){
			out.println( data );
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		// get file from tmp directory
		File fXmlFile = new File(ConfigurationUtil.getUsersXMLPath()+File.separator+ts+".xml");

		// parse XML
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			dBuilder.parse(fXmlFile);	
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// if exceptions are thrown the file is invalid
			e.printStackTrace();
			fXmlFile.delete();
			return false;
		}
		fXmlFile.delete();
		return true;
	}
}