package handlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import persistence.PersistenceController;
import utils.ConfigurationUtil;

public class TestHandler extends HttpServlet  {

	private final String USER_AGENT = "Mozilla/5.0";
	private static final long serialVersionUID = -6437243621014815307L;
	private PersistenceController persistenceController = PersistenceController.getInstance();
	private Map<String,Boolean> vulnerabilities = new HashMap<String,Boolean>();
	private static String baseHost = ConfigurationUtil.getBaseHost();
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			runTest(request,response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sqliTest(String cookie) throws Exception{
		boolean sqli_login = sendPost(baseHost, "/auth", "j_username=test';#&j_password=notTheActualPassword", "/logged/home.jsp?user=User&url=/logged/home.html%23profile", cookie);
		boolean sqli_feedback = sendPost(baseHost, "/logged/userHandler", "{\"type\":\"getFeedbacks\",\"kind\":\"search\",\"usr\":\"' OR 1=1;#\"}", "Beautiful website", cookie);

		vulnerabilities.put("SQLi - Login Page", sqli_login);
		vulnerabilities.put("SQLi - Feedback Search", sqli_feedback);
	}

	private void xssTest(String cookie) throws Exception{
		boolean xss_reflected_user = sendGet(baseHost, "/logged/home.jsp?user=<script>alert(1)</script>&url=/logged/home.html%23profile","<script>alert(1)</script>",cookie);
		boolean xss_reflected_url = sendGet(baseHost, "/logged/home.jsp?user=User&url=%22%20onclick=alert(1)","\" onclick=alert(1)",cookie);

		sendPost(baseHost, "/logged/userHandler", "{\"type\":\"storeFeedback\",\"msg\":\"<script>alert(1)</script>\"}", "", cookie);
		boolean xss_stored = sendPost(baseHost, "/logged/userHandler", "{\"type\":\"getFeedbacks\",\"kind\":\"all\"}", "<script>alert(1)</script>", cookie);
		persistenceController.removeFeedback(9999);

		vulnerabilities.put("XSS Reflected - User Parameter", xss_reflected_user);
		vulnerabilities.put("XSS Reflected - Url Parameter", xss_reflected_url);
		vulnerabilities.put("XSS Stored", xss_stored);
	}
	private void xxeTest(String cookie) throws Exception {
		long ts = System.currentTimeMillis();
		sendPost(baseHost, "/logged/userHandler", "{\"type\":\"storePost\",\"xml\":\"<?xml version=\\\"1.0\\\" encoding=\\\"utf-8\\\" standalone=\\\"no\\\"?><!DOCTYPE Post [<!ENTITY xxe \\\"XXE-Test-Payload\\\" >]><Post><title>"+ts+"</title><content>&xxe;</content><timestamp>"+ts+"</timestamp></Post>\",\"title\":\""+ts+"\"}", "", cookie);
		boolean xxe = sendPost(baseHost, "/logged/userHandler", "{\"type\":\"getPosts\",\"kind\":\"open\",\"file\":\""+ts+".xml\"}", "XXE-Test-Payload", cookie);
		vulnerabilities.put("XML Entity Expansion", xxe);
		sendPost(baseHost, "/logged/userHandler", "{\"type\":\"deletePost\",\"file\":\""+ts+"\"}", "", cookie);

	}
	private void rceTest(String cookie) throws Exception {
		boolean rce = sendPost(baseHost, "/logged/userHandler", "{\"type\":\"getDocs\",\"kind\":\"open\",\"filename\":\";echo hello\"}", "hello", cookie);
		vulnerabilities.put("Remote Code Execution", rce);
	}

	protected void runTest(HttpServletRequest request, HttpServletResponse response){
		String cookie;
		try {
			cookie = doLogin();
			xssTest(cookie);
			sqliTest(cookie);
			rceTest(cookie);
			xxeTest(cookie);
			pathTraversalTest(cookie);
			//authZBypassTest(cookie);
			//arbitraryFileUploadTest(cookie);
			
			//AuthZ Bypass			
			//File Upload

			Gson gson = new Gson();
			String msg = gson.toJson(vulnerabilities);

			PrintWriter out;
			try {
				out = response.getWriter();
				out.print(msg);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void arbitraryFileUploadTest(String cookie) {
		// TODO Auto-generated method stub
		
	}
	@SuppressWarnings("unused")
	private void authZBypassTest(String cookie) {
		// TODO Auto-generated method stub
		
	}
	private void pathTraversalTest(String cookie) throws Exception {
		boolean pathTraversal = sendPost(baseHost,"/logged/userHandler","{\"type\":\"getDocs\",\"kind\":\"open\",\"filename\":\"../../../../../../../../../../etc/passwd\"}", "User Database", cookie);
		vulnerabilities.put("Path Traversal", pathTraversal);
	}
	private String doLogin() throws Exception{
		String payload = "j_username=test&j_password=password";		
		URL obj = new URL(baseHost+"/auth");
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(payload);
		wr.flush();
		wr.close();
		return con.getHeaderField("Set-Cookie").split(" ")[0];		
	}

	private boolean sendGet(String baseHost, String url, String condition, String cookie) throws Exception {
		URL obj = new URL(baseHost + url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Cookie", cookie);
		System.out.println("#Sending 'GET' request to URL : " + baseHost + url);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		boolean isMatch = response.toString().indexOf(condition)>-1;
		System.out.println("Condition match: " + isMatch);
		return isMatch;
	}

	private boolean sendPost(String baseHost, String url, String body, String condition, String cookie) throws Exception {
		URL obj = new URL(baseHost+url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Cookie", cookie);
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(body);
		wr.flush();
		wr.close();
		System.out.println("#Sending 'POST' request to URL : " + baseHost + url);
		System.out.println("Post parameters : " + body);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		System.out.println(response.toString());
		boolean isMatch = response.toString().indexOf(condition)>-1;
		System.out.println("Condition match: " + isMatch);
		return isMatch;
	}
}